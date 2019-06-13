package com.zhkj.nettyserver.netty;


import com.alibaba.fastjson.JSON;
import com.zhkj.nettyserver.message.controller.ChatController;
import com.zhkj.nettyserver.message.domain.respone.ChatGroupVO;
import com.zhkj.nettyserver.message.domain.respone.ChatVO;
import com.zhkj.nettyserver.message.domain.respone.UserVO;
import com.zhkj.nettyserver.message.service.MessageService;
import com.zhkj.nettyserver.util.SpringUtil;
import com.zhkj.nettyserver.util.token.TokenUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author dengyi
 * time:2019年6月11日 10:31:17
 * 自定义的Handler
 */
public class MyHandler extends SimpleChannelInboundHandler<Object> {

    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    //private static final SubscribeMessage subscribeMessage = SpringUtil.getBean(SubscribeMessage.class);

    private static MessageService messageService = (MessageService) SpringUtil.getBean("messageService");

    private static ChatController chatController = (ChatController) SpringUtil.getBean("chatController");
    private static final SessionUtil sessionUtil = new SessionUtil();
    private ChannelGroup channelGroup;
    private WebSocketServerHandshaker handshaker;
    private ChannelHandlerContext ctx;
    private static final Session session = new Session();

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object o) throws Exception {
        if (o instanceof FullHttpRequest) { // 添加传统的HTTP接入
            handleHttpRequest(ctx, (FullHttpRequest) o);
        } else if (o instanceof WebSocketFrame) { // WebSocket接入
            handleWebSocketFrame(ctx, (WebSocketFrame) o);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        super.close(ctx, promise);
    }

    /**
     * 处理Http请求，完成WebSocket握手<br/>
     * 注意：WebSocket连接第一次请求使用的是Http
     * <p>
     * 第一次连接中做token校验，将
     *
     * @param ctx
     * @param request
     * @throws Exception
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        // 如果HTTP解码失败，返回HTTP异常
        if (!request.getDecoderResult().isSuccess() || (!"websocket".equals(request.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }
        // 正常WebSocket的Http连接请求，构造握手响应返回
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("http://" + request.headers().get(HttpHeaders.Names.HOST), null, false);
        handshaker = wsFactory.newHandshaker(request);
        if (handshaker == null) { // 无法处理的websocket版本
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else { // 向客户端发送websocket握手,完成握手
            //如果当前管道没有映射
            if (sessionUtil.getSession(ctx.channel()) == null) {
                //得到token信息
                //token解析
                String token = request.getUri().substring(request.getUri().indexOf("=") + 1, request.getUri().length());
                String uuid = TokenUtil.getToken(token).getUuid();
                if (uuid == null) {
                    throw new Exception("用户登录异常");
                }
                session.setSuseUuid(Long.valueOf(uuid));
                System.out.println(session.getSuseUuid());
                //放入到session中保存 设置登录信息
                sessionUtil.bindSession(session, ctx.channel());
                //将管道组加入
                // subscribeMessage.setChannelGroup(sessionUtil.getChannelGroup(ctx));
            }
            handshaker.handshake(ctx.channel(), request);
            // 记录管道处理上下文，便于服务器推送数据到客户端
            this.ctx = ctx;
        }
    }

    /**
     * Http返回
     *
     * @param ctx
     * @param request
     * @param response
     */
    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response) {
        System.out.println("sendHttpResponse:7");
        // 返回应答给客户端
        if (response.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(response.getStatus().toString(), CharsetUtil.UTF_8);
            response.content().writeBytes(buf);
            buf.release();
            //允许跨域访问 设置头部信息
            response.headers().set(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
            response.headers().set(ACCESS_CONTROL_ALLOW_METHODS, "GET,POST,PUT,DELETE");
            response.headers().set(ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            response.headers().set(ACCESS_CONTROL_ALLOW_HEADERS, "Origin, X-Requested-With, Content-Type, Accept");
            HttpHeaders.setContentLength(response, response.content().readableBytes());
        }

        // 如果是非Keep-Alive，关闭连接 保持Keep-Alive
        ChannelFuture f = ctx.channel().writeAndFlush(response);
        if (!HttpHeaders.isKeepAlive(request) || response.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    /**
     * 处理Socket请求
     *
     * @param ctx
     * @param frame
     * @throws Exception
     */
    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
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
//            //throw new UnsupportedOperationException("当前只支持文本消息，不支持二进制消息");
//            //获取发来的消息
//            String text = ((TextWebSocketFrame) frame).text();
//            System.out.println("mage : " + text);
//            //消息转成Mage
//            Mage mage = Mage.strJson2Mage(text);
//            //判断是以存在用户信息
//            if (InformationOperateMap.isNo(mage)) {
//                //判断是否有这个聊天室
//                if (InformationOperateMap.map.containsKey(mage.getTable())) {
//                    //判断是否有其他用户
//                    if (InformationOperateMap.map.get(mage.getTable()).size() > 0) {
//                        InformationOperateMap.map.get(mage.getTable()).forEach((id, iom) -> {
//                            try {
//                                Mage mag = iom.getMage();
//                                mag.setMessage("30003");
//                                //发送其他用户信息给要注册用户
//                                this.sendWebSocket(mag.toJson());
//                            } catch (Exception e) {
//                                System.err.println(e);
//                            }
//                        });
//                    }
//                }
//                //添加用户
//                InformationOperateMap.add(ctx, mage);
//                System.out.println("add : " + mage.toJson());
//            }
//            //将用户发送的消息发给所有在同一聊天室内的用户
//            InformationOperateMap.map.get(mage.getTable()).forEach((id, iom) -> {
//                try {
//                    iom.sead(mage);
//                } catch (Exception e) {
//                    System.err.println(e);
//                }
//            });
//            //记录id和table 当页面刷新或浏览器关闭时，注销掉此链路
//            this.sessionId = mage.getId();
//            this.table = mage.getTable();
//            this.name = mage.getName();
//        } else {
//            System.err.println("------------------error--------------------------");
//        }
        // 判断是否是关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            //删除管道映射
            sessionUtil.unBindSession(ctx.channel());
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            return;
        }
        // 判断是否是Ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if (this.handshaker == null || this.ctx == null || this.ctx.isRemoved()) {
            throw new Exception("尚未握手成功，无法向客户端发送WebSocket消息");
        }
        //判断是否当前用户是否登录
        if (sessionUtil.hasLogin(ctx.channel())) {
            throw new Exception("当前用户未登录");
        }

        if ((frame instanceof TextWebSocketFrame)) {
            String text = ((TextWebSocketFrame) frame).text();
            Message message = JSON.parseObject(text, Message.class);
            System.out.println(text);
            //创建消息返回对象
            Message sendMessage = new Message();
            //判断是否是系统群发消息
            if (message.getMessAction() == 0) {

                //保存数据库会话信息
                //创建会话
//                messageService.insertChat();
                //如果是系统群发消息，则从session中拿出所有的管道
                ChannelGroup channelGroup1 = sessionUtil.getChannelGroup(ctx);

                System.out.println(channelGroup1.size());
                channelGroup1.writeAndFlush(new TextWebSocketFrame((JSON.toJSONString(message))));
            }
            //如果是发送群组消息
            if (message.getMessAction() == 1) {
                //保存数据库会话信息 查询群组人员
                message.getMessMain();
                //建立一个管道分组
                channelGroup = new DefaultChannelGroup(ctx.executor());
                //拿到群组中的人的Uuid

                //拿到对应的管道

            }
            //点对点消息
            if (message.getMessAction() == 2) {
                //保存数据库会话信息 查询会话信息
                //创建会话

                System.out.println(text);
                Channel channel2 = sessionUtil.getChannel(message.getMessSuseUuid());
                channel2.writeAndFlush(new TextWebSocketFrame((JSON.toJSONString(message))));
            }
            //获取最近会话
            if (message.getMessAction() == 3) {
                List<ChatVO> list = chatController.list(message.getSuseUuid());
                sendMessage.setMessObject(list);
                sendMessage.setMessAction(3);
                sendWebSocket(sendMessage, sessionUtil.getChannel(message.getSuseUuid()));
            }
            //拉取好友
            if (message.getMessAction() == 4) {
                List<UserVO> listFriend = chatController.listFriend(message.getSuseUuid());
                sendMessage.setMessObject(listFriend);
                sendMessage.setMessAction(4);
                sendWebSocket(sendMessage, sessionUtil.getChannel(message.getSuseUuid()));
            }
            //拉取群
            if (message.getMessAction() == 5) {
                List<ChatGroupVO> groupList = chatController.listGroup2(message.getSuseUuid());
                sendMessage.setMessObject(groupList);
                sendMessage.setMessAction(5);
                sendWebSocket(sendMessage, sessionUtil.getChannel(message.getSuseUuid()));
            }

        }

        //业务逻辑

    }

    /**
     * WebSocket返回
     */
    public void sendWebSocket(Message message, Channel channel) throws Exception {
        //发送消息
        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
    }

}
