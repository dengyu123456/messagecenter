package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.common.base.TokenUtil;
import com.zhkj.nettyserver.common.base.TokenVO;
import com.zhkj.nettyserver.common.util.SpringUtil;
import com.zhkj.nettyserver.common.util.StringUtil;
import com.zhkj.nettyserver.message.domain.User;
import com.zhkj.nettyserver.message.service.MessageService;
import com.zhkj.nettyserver.netty.ChannelUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.Names.ACCESS_CONTROL_ALLOW_HEADERS;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Des:登录处理器，处理第一次http请求
 * ClassName: LoginHandler
 * Author: dengyi
 * Date: 2019-06-26 09:11
 */
public class LoginHandler extends SimpleChannelInboundHandler<Object> {
    private WebSocketServerHandshaker handshaker;
    private MessageService messageService;
//    //单例模式
//    private static CustomHandler INSTANCE = new CustomHandler();
//
//    public static CustomHandler getInstance() {
//        return INSTANCE ;
//    }

    public LoginHandler() {
        messageService = (MessageService) SpringUtil.getBean("messageService");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) { // 添加传统的HTTP接入
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) { // WebSocket接入

             handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        ctx.close();
    }

    /**
     * 处理Http请求，完成WebSocket握手<br/>
     * 注意：WebSocket连接第一次请求使用的是Http
     * <p>
     * 第一次连接中做token校验
     *
     * @param ctx
     * @param request
     * @throws Exception
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        // 如果HTTP解码失败，返回HTTP异常  升级握手
        if (!request.decoderResult().isSuccess() || (!"websocket".equals(request.headers().get("Upgrade")))) {//
            sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }
        //写什么都无所谓，WS协议消息的接收不受这里控制
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://" + request.headers().get(HttpHeaders.Names.HOST), null, false);
        handshaker = wsFactory.newHandshaker(request);
        if (handshaker == null) { // 无法处理的websocket版本
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else { // 向客户端发送websocket握手,完成握手
            String tokenStr = request.uri().substring(request.uri().indexOf("=") + 1, request.uri().length());
            if (StringUtil.isBlank(tokenStr)) {//token 不存在
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            } else if (ChannelUtil.getInstance().getSuseUuid(ctx.channel()) == null) {
                TokenVO vo = TokenUtil.getToken(tokenStr);
                if (vo == null) {
                    sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UNAUTHORIZED));
                } else if (vo.getOt() < System.currentTimeMillis() || (vo.getOt()-System.currentTimeMillis()) > 604800000) {
                    sendHttpResponse(ctx, request, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
                } else {//没有问题
                    User user = messageService.selectUserByUserUuid(Long.valueOf(vo.getUuid()));
                    ChannelUtil.getInstance().bindChannel(user == null ? null : user.getUserEnteUuid(), Long.valueOf(vo.getUuid()), ctx.channel());
                    handshaker.handshake(ctx.channel(), request);
                }
            }
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
        // 返回应答给客户端
        if (response.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(response.status().toString(), CharsetUtil.UTF_8);
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
        if (!HttpHeaders.isKeepAlive(request) || response.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }
    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
        //当前管道用户uuid
        Long useUuid = ChannelUtil.getInstance().getSuseUuid(ctx.channel());
        System.out.println(useUuid);
        if (frame instanceof CloseWebSocketFrame) {//关闭管道
            ChannelUtil.getInstance().unBindChannel(ctx.channel(), useUuid);
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
        } else if (frame instanceof PingWebSocketFrame) {//判断是否是Ping消息
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
        } else if (frame instanceof TextWebSocketFrame) {
            ctx.fireChannelRead(frame);
        }
}
}
