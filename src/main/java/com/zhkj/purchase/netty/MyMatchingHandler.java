//package com.zhkj.purchase.netty;
//
//
//import io.netty.buffer.ByteBuf;
//import io.netty.buffer.Unpooled;
//import io.netty.channel.*;
//import io.netty.handler.codec.http.*;
//import io.netty.handler.codec.http.websocketx.*;
//import io.netty.util.CharsetUtil;
//
//import java.util.UUID;
//import java.util.concurrent.ConcurrentMap;
//
//import static com.dream.springbootframe.config.netty.InformationOperateMapMatching.queue;
//
///**
// *
// * @author Dream
// *
// *  自定义的Handler
// */
//public class MyMatchingHandler extends SimpleChannelInboundHandler<Object> {
//
//    private WebSocketServerHandshaker handshaker;
//    private ChannelHandlerContext ctx;
//    private String sessionId;
//    private String name;
//
//    @Override
//    protected void messageReceived(ChannelHandlerContext ctx, Object o) throws Exception {
//        if (o instanceof FullHttpRequest) { // 传统的HTTP接入
//            handleHttpRequest(ctx, (FullHttpRequest) o);
//        } else if (o instanceof WebSocketFrame) { // WebSocket接入
//            handleWebSocketFrame(ctx, (WebSocketFrame) o);
//        }
//    }
//
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.flush();
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        ctx.close();
//    }
//
//    @Override
//    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
//        super.close(ctx, promise);
//        //关闭连接将移除该用户消息
//        Mage mage = new Mage();
//        mage.setName(this.name);
//        mage.setMessage("20002");
//        //将用户下线信息发送给为下线用户
//        String table = InformationOperateMapMatching.login.get(this.sessionId);
//        ConcurrentMap<String, InformationOperateMapMatching> cmap = InformationOperateMapMatching.map.get(table);
//        if (cmap != null) {
//            cmap.forEach((id, iom) -> {
//                try {
//                    if (id != this.sessionId) iom.sead(mage);
//                } catch (Exception e) {
//                    System.err.println(e);
//                }
//            });
//        }
//        InformationOperateMapMatching.login.remove(this.sessionId);
//        InformationOperateMapMatching.map.remove(table);
//    }
//
//    /**
//     * 处理Http请求，完成WebSocket握手<br/>
//     * 注意：WebSocket连接第一次请求使用的是Http
//     * @param ctx
//     * @param request
//     * @throws Exception
//     */
//    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
//        // 如果HTTP解码失败，返回HTTP异常
//
//        if (!request.getDecoderResult().isSuccess() || (!"websocket".equals(request.headers().get("Upgrade")))) {
//            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
//            return;
//        }
//
//        // 正常WebSocket的Http连接请求，构造握手响应返回
//        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://" + request.headers().get(HttpHeaders.Names.HOST), null, false);
//        handshaker = wsFactory.newHandshaker(request);
//        if (handshaker == null) { // 无法处理的websocket版本
//            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
//        } else { // 向客户端发送websocket握手,完成握手
//            handshaker.handshake(ctx.channel(), request);
//            // 记录管道处理上下文，便于服务器推送数据到客户端
//            this.ctx = ctx;
//        }
//
//    }
//
//    /**
//     * Http返回
//     * @param ctx
//     * @param request
//     * @param response
//     */
//    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response) {
//        // 返回应答给客户端
//        if (response.getStatus().code() != 200) {
//            ByteBuf buf = Unpooled.copiedBuffer(response.getStatus().toString(), CharsetUtil.UTF_8);
//            response.content().writeBytes(buf);
//            buf.release();
//            HttpHeaders.setContentLength(response, response.content().readableBytes());
//        }
//
//        // 如果是非Keep-Alive，关闭连接
//        ChannelFuture f = ctx.channel().writeAndFlush(response);
//        if (!HttpHeaders.isKeepAlive(request) || response.getStatus().code() != 200) {
//            f.addListener(ChannelFutureListener.CLOSE);
//        }
//    }
//
//    /**
//     * 处理Socket请求
//     * @param ctx
//     * @param frame
//     * @throws Exception
//     */
//    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
//        // 判断是否是关闭链路的指令
//        if (frame instanceof CloseWebSocketFrame) {
//            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
//            return;
//        }
//        // 判断是否是Ping消息
//        if (frame instanceof PingWebSocketFrame) {
//            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
//            return;
//        }
//        // 当前只支持文本消息，不支持二进制消息
//        if ((frame instanceof TextWebSocketFrame)) {
//            //获取发来的消息
//            String text =((TextWebSocketFrame)frame).text();
//            System.out.println("mage : " + text);
//            //消息转成Mage
//            Mage mage = Mage.strJson2Mage(text);
//            if (mage.getMessage().equals("10001")) {
//                if (!InformationOperateMapMatching.login.containsKey(mage.getId())) {
//                    InformationOperateMapMatching.login.put(mage.getId(), "");
//                    InformationOperateMapMatching.offer(ctx, mage);
//                    if (queue.size() >= 2) {
//                        String tableId = UUID.randomUUID().toString();
//                        InformationOperateMapMatching iom1 = queue.poll().setTableId(tableId);
//                        InformationOperateMapMatching iom2 = queue.poll().setTableId(tableId);
//                        InformationOperateMapMatching.add(iom1.getChannelHandlerContext(), iom1.getMage());
//                        InformationOperateMapMatching.add(iom2.getChannelHandlerContext(), iom2.getMage());
//                        iom1.sead(iom2.getMage());
//                        iom2.sead(iom1.getMage());
//                    }
//                } else {//用户已登录
//                    mage.setMessage("-10001");
//                    sendWebSocket(mage.toJson());
//                    ctx.close();
//                }
//            } else {
//                //将用户发送的消息发给所有在同一聊天室内的用户
//                InformationOperateMapMatching.map.get(mage.getTable()).forEach((id, iom) -> {
//                    try {
//                        iom.sead(mage);
//                    } catch (Exception e) {
//                        System.err.println(e);
//                    }
//                });
//            }
//            //记录id 当页面刷新或浏览器关闭时，注销掉此链路
//            this.sessionId = mage.getId();
//            this.name = mage.getName();
//        } else {
//            System.err.println("------------------error--------------------------");
//        }
//    }
//
//    /**
//     * WebSocket返回
//     */
//    public void sendWebSocket(String msg) throws Exception {
//        if (this.handshaker == null || this.ctx == null || this.ctx.isRemoved()) {
//            throw new Exception("尚未握手成功，无法向客户端发送WebSocket消息");
//        }
//        //发送消息
//        this.ctx.channel().write(new TextWebSocketFrame(msg));
//        this.ctx.flush();
//    }
//}
