package com.zhkj.nettyserver.netty;


import com.alibaba.fastjson.JSON;
import com.zhkj.nettyserver.message.controller.ChatController;
import com.zhkj.nettyserver.message.domain.ChatGroupUser;
import com.zhkj.nettyserver.message.domain.ChatUser;
import com.zhkj.nettyserver.message.domain.request.*;
import com.zhkj.nettyserver.message.domain.respone.*;
import com.zhkj.nettyserver.message.service.MessageService;
import com.zhkj.nettyserver.util.SpringUtil;
import com.zhkj.nettyserver.util.token.TokenUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.Date;
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

    // private  final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

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

            handshaker.handshake(ctx.channel(), request);
            // 记录管道处理上下文
            this.ctx = ctx;
            //如果当前管道没有映射
            //得到token信息
            //token解析
            if (sessionUtil.getSession(ctx.channel()) == null) {
                String token = request.getUri().substring(request.getUri().indexOf("=") + 1, request.getUri().length());
                String uuid = TokenUtil.getToken(token).getUuid();
                if (uuid == null) {
                    throw new Exception("用户登录异常");
                }
                session.setSuseUuid(Long.valueOf(uuid));
                //放入到session中保存 设置登录信息
                sessionUtil.bindSession(session, ctx.channel());
                System.out.println(uuid);
                System.out.println(sessionUtil.getSession(ctx.channel()).getSuseUuid());
                //将管道组加入
                // subscribeMessage.setChannelGroup(sessionUtil.getChannelGroup(ctx));
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

        //当前管道用户uuid
        Long useUuid = sessionUtil.getSession(ctx.channel()).getSuseUuid();
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
            //判断是否是后台消息
            if (message.getMessAction() == 0) {

                //保存数据库会话信息
                //创建会话
//                messageService.insertChat();
                //如果是系统群发消息，则从session中拿出所有的管道
                ChannelGroup channelGroup1 = sessionUtil.getChannelGroup(ctx);

                System.out.println(channelGroup1.size());
                channelGroup1.writeAndFlush(new TextWebSocketFrame((JSON.toJSONString(message))));
            }
            //开启会话  所有消息基于会话
            if (message.getMessAction() == 1) {
                String messMain = message.getMessMain();
                OpenParams params = JSON.parseObject(messMain, OpenParams.class);

                OpenVO openVO = chatController.openChat(params, useUuid);
                //把历史聊天记录加入到 会话中
                SearchMessageParams messageParams = new SearchMessageParams();
                messageParams.setMessChatUuid(openVO.getChatUuid());
                messageParams.setMessEndDate(params.getMessEndDate());
                messageParams.setMessStartDate(params.getMessStartDate());
                List<SearchMessageVO> voList = chatController.listMsg(messageParams);
                openVO.setMessageVOList(voList);
                sendMessage.setSuseUuid(params.getsSuseUuid());
                sendMessage.setMessAction(1);
                sendMessage.setMessObject(openVO);
                List<ChatUser> userList = messageService.selectChatUserByChatUuid(openVO.getChatUuid());
                //返回会话开启消息
//                if (openVO != null) {
//
//                    //返回会话消息
//                    sendWebSocket(sendMessage, ctx.channel());
//                }
                for (ChatUser user : userList) {
                    Channel channel = sessionUtil.getChannel(user.getCuseSuseUuid());
                    if (channel == null) {
                        return;
                    }
                    sendWebSocket(sendMessage, channel);
                }
            }
            //消息发送
            if (message.getMessAction() == 2) {
//                //保存数据库会话信息 查询会话信息
//                //创建会话
//                System.out.println(text);
//                Channel channel2 = sessionUtil.getChannel(message.getMessSuseUuid());
//                channel2.writeAndFlush(new TextWebSocketFrame((JSON.toJSONString(message))));

                //消息发送
                String messMain = message.getMessMain();
                Date currTime = new Date(System.currentTimeMillis());
                MessageParams params = JSON.parseObject(messMain, MessageParams.class);
                //保存消息到数据库
                messageService.insertMessageAndUpdateChat(params, currTime);
                MessageVO vo = new MessageVO();
                vo.setMessStatus(com.zhkj.nettyserver.message.domain.Message.SUCCESS);
                vo.setMessChatUuid(params.getMessChatUuid());
                vo.setMessContent(params.getMessContent());
                vo.setMessContentType(params.getMessContentType());
                vo.setMessDate(currTime.getTime());
                vo.setMessSuseUuid(params.getMessSuseUuid());
                ChatUser sChatUser = this.messageService.selectChatUserByChatUuidAndSuseUuid(params.getMessChatUuid(), params.getMessSuseUuid());
                vo.setMessSuseName(sChatUser == null ? "未知" : sChatUser.getCuseName());
                sendMessage.setMessObject(vo);
                sendMessage.setMessAction(2);
                List<ChatUser> userList = messageService.selectChatUserByChatUuid(params.getMessChatUuid());
                //发送消息给此会话中的每个人
                for (ChatUser user : userList) {
//                    System.out.println(sessionUtil.getChannelGroup(ctx).size() + "===============================================");
//
//                    System.out.println(user.getCuseUuid() + "====================user.getCuseUuid()");
                    Channel channel = sessionUtil.getChannel(user.getCuseSuseUuid());
                    sendWebSocket(sendMessage, channel);
                }
            }
            //获取最近会话
            if (message.getMessAction() == 3) {
                List<ChatVO> list = chatController.list(message.getSuseUuid());
                sendMessage.setMessObject(list);
                System.out.println(sessionUtil.getSession(ctx.channel()).getSuseUuid() + "====================================");
                ChatUser sChatUser = this.messageService.selectChatUserByChatUuidAndSuseUuid(list.get(0).getChatUuid(), sessionUtil.getSession(ctx.channel()).getSuseUuid());
                System.out.println(sChatUser.getCuseName());
                sendMessage.setMessAction(3);
                sendWebSocket(sendMessage, sessionUtil.getChannel(message.getSuseUuid()));
            }
            //拉取好友
            if (message.getMessAction() == 4) {
                List<UserVO> listFriend = chatController.listFriend(message.getSuseUuid());
                System.out.println(listFriend.get(0).getCreateTime() + "=================" + listFriend.get(0).getUpdateTime());
                sendMessage.setMessObject(listFriend);
                sendMessage.setMessAction(4);
                sendWebSocket(sendMessage, sessionUtil.getChannel(message.getSuseUuid()));
            }
            //拉取群信息
            if (message.getMessAction() == 5) {
                List<ChatGroupVO> groupList = chatController.listGroup2(message.getSuseUuid());
                sendMessage.setMessObject(groupList);
                sendMessage.setMessAction(5);
                sendWebSocket(sendMessage, sessionUtil.getChannel(message.getSuseUuid()));
            }
//            //拉取历史聊天记录
//            if (message.getMessAction() == 6) {
//                String messMain = message.getMessMain();
//                SearchMessageParams params = JSON.parseObject(messMain, SearchMessageParams.class);
//                List<SearchMessageVO> voList = chatController.listMsg(params);
//                sendMessage.setMessObject(voList);
//                sendMessage.setMessAction(6);
//                sendWebSocket(sendMessage, ctx.channel());
//            }
            //修改群名片
            if (message.getMessAction() == 7) {
                EditChatGroupUserParams params = JSON.parseObject(message.getMessMain(), EditChatGroupUserParams.class);
                String retu = chatController.editGroupUser(params);
                sendMessage.setMessAction(8);
                sendMessage.setMessObject(retu);
                sendWebSocket(sendMessage, ctx.channel());
            }
            //修改群
            if (message.getMessAction() == 8) {
                //当前管道的用户uuid
                Long suseUuid = sessionUtil.getSession(ctx.channel()).getSuseUuid();
                EditChatGroupParams params = JSON.parseObject(message.getMessMain(), EditChatGroupParams.class);
                String resu = chatController.editGroup(params, suseUuid);
                sendMessage.setMessObject(resu);
                sendMessage.setMessAction(8);
                sendWebSocket(sendMessage, ctx.channel());
            }
            //创建群
            if (message.getMessAction() == 9) {
                OpenGroupParams2 params2 = JSON.parseObject(message.getMessMain(), OpenGroupParams2.class);
                OpenGroupVO vo = chatController.openGroup2(params2);
                sendMessage.setMessAction(9);
                sendMessage.setMessObject(vo);
                sendWebSocket(sendMessage, ctx.channel());
            }
            //退出群
            if (message.getMessAction() == 10) {
                OutGroupParams params = JSON.parseObject(message.getMessMain(), OutGroupParams.class);
                OutGroupVo outGroupVo = chatController.outGroup(params);
                sendMessage.setMessObject(outGroupVo);
                sendMessage.setMessAction(10);
                sendWebSocket(sendMessage, ctx.channel());
            }
            //删除群成员
            if (message.getMessAction() == 11) {
                DelChatGroupUserParams params = JSON.parseObject(message.getMessMain(), DelChatGroupUserParams.class);
                //获取群成员 通知
                List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(params.getCgroUuid());

                List<ChatGroupVO> voList = chatController.delGroup(params);
                sendMessage.setMessAction(5);
                sendMessage.setMessObject(voList);
                for (ChatGroupUser item : chatGroupUserList) {
                    Channel channel = sessionUtil.getChannel(item.getCgusSuseUuid());
                    sendWebSocket(sendMessage, channel);
                }
            }
            //增加群成员
            if (message.getMessAction() == 12) {
                AddChatGroupUserParams params=JSON.parseObject(message.getMessMain(), AddChatGroupUserParams.class);
                List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(params.getCgusCgroUuid());
                List<ChatGroupVO> voList = chatController.addGroup(params);
                sendMessage.setMessAction(5);
                sendMessage.setMessObject(voList);
                for (ChatGroupUser item : chatGroupUserList) {
                    Channel channel = sessionUtil.getChannel(item.getCgusSuseUuid());
                    sendWebSocket(sendMessage, channel);
                }
            }
        }
    }

    /**
     * WebSocket返回
     */
    public void sendWebSocket(Message message, Channel channel) throws Exception {
        //发送消息  如果管道组中没有
        if (channel == null) {
            ctx.channel().writeAndFlush(new TextWebSocketFrame("连接异常"));
        }
        channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));
    }

}
