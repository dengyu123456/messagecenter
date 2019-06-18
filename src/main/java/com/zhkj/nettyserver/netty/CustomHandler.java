package com.zhkj.nettyserver.netty;


import com.alibaba.fastjson.JSON;
import com.zhkj.nettyserver.common.base.TokenUtil;
import com.zhkj.nettyserver.common.base.TokenVO;
import com.zhkj.nettyserver.common.base.request.Request;
import com.zhkj.nettyserver.common.base.respone.ResponseStomp;
import com.zhkj.nettyserver.common.base.respone.ResponseStompFactory;
import com.zhkj.nettyserver.common.util.BeanUtil;
import com.zhkj.nettyserver.common.util.SpringUtil;
import com.zhkj.nettyserver.common.util.StringUtil;
import com.zhkj.nettyserver.message.domain.*;
import com.zhkj.nettyserver.message.domain.request.*;
import com.zhkj.nettyserver.message.domain.respone.*;
import com.zhkj.nettyserver.message.service.MessageService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.apache.commons.collections.CollectionUtils;

import java.util.*;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author dengyi
 * time:2019年6月11日 10:31:17
 * 自定义的Handler  多个channel共享handler
 */
@ChannelHandler.Sharable
public class CustomHandler extends SimpleChannelInboundHandler<Object> {

    private MessageService messageService;

    private static CustomHandler INSTANCE = new CustomHandler();

    public static CustomHandler getInstance() {
        return INSTANCE ;
    }

    //单例模式
    private CustomHandler() {
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
        ctx.close();
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

        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("http://" + request.headers().get(HttpHeaders.Names.HOST), null, false);
        WebSocketServerHandshaker handshaker = wsFactory.newHandshaker(request);
        if (handshaker == null) { // 无法处理的websocket版本
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else { // 向客户端发送websocket握手,完成握手
            String tokenStr = request.headers().get(HttpHeaders.Names.AUTHORIZATION);
            if (StringUtil.isBlank(tokenStr)) {//token 不存在
                WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
            } else {
                TokenVO vo = TokenUtil.getToken(tokenStr);
                if (vo == null) {
                    WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
                } else if (vo.getOt() - System.currentTimeMillis() > 24 * 60 * 60 * 1000 * 7) {
                    WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
                } else {//没有问题
                    ChannelUtil.getInstance().bindChannel(Long.valueOf(vo.getUuid()), ctx.channel());
                }
            }
           // 正常WebSocket的Http连接请求，构造握手响应返回
            handshaker.handshake(ctx.channel(), request);

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
        //当前管道用户uuid
        Long useUuid = ChannelUtil.getInstance().getSuseUuid(ctx.channel());
        if (frame instanceof CloseWebSocketFrame) {//关闭管道
            ChannelUtil.getInstance().unBindChannel(useUuid);
        } else if (frame instanceof PingWebSocketFrame) {//判断是否是Ping消息
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
        } else if (frame instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) frame).text();

//        if (this.handshaker == null || ctx == null || ctx.isRemoved()) {
//            throw new Exception("尚未握手成功，无法向客户端发送WebSocket消息");
//        }
            Request reqOb = JSON.parseObject(text, Request.class);
            System.out.println(reqOb.getAction());
            //发送消息
            if ("chat".equals(reqOb.getAction())) {
                chat(reqOb.getParams(), ctx.channel());
            } else if ("chatOpen".equals(reqOb.getAction())) {//开启会话
                openChat(reqOb.getParams(), ctx.channel());
            } else if ("chatList".equals(reqOb.getAction())) {//获取最近会话
                chatList(reqOb.getParams(), ctx.channel());
            } else if ("listFriend".equals(reqOb.getAction())) {//获取好友
                listFriend(reqOb.getParams(), ctx.channel());
            } else if ("openGroup2".equals(reqOb.getAction())) {//创建一个群
                openGroup2(reqOb.getParams(), ctx.channel());
            } else if ("listGroup".equals(reqOb.getAction())) {//获取群
                listGroup(reqOb.getParams(), ctx.channel());
            } else if ("outGroup".equals(reqOb.getAction())) {//退出群
                outGroup(reqOb.getParams(), ctx.channel());
            } else if ("outGroup".equals(reqOb.getAction())) {//增加群成员
                addGroup(reqOb.getParams(), ctx.channel());
            } else if ("delGroup".equals(reqOb.getAction())) {//删除群成员
                delGroup(reqOb.getParams(), ctx.channel());
            } else if ("editGroup".equals(reqOb.getAction())) {//修改群
                editGroup(reqOb.getParams(), ctx.channel());
            } else if ("editGroupUser".equals(reqOb.getAction())) {//修改群名片
                editGroupUser(reqOb.getParams(), ctx.channel());
            } else if ("listMsg".equals(reqOb.getAction())) {//获取会话消息列表
                listMsg(reqOb.getParams(), ctx.channel());
            }
        }
    }

    private void listMsg(String params, Channel channel) {
        if (StringUtil.isNotBlank(params)) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定获取会话列表参数", "listMsg"));
            return;
        }
        SearchMessageParams smpa = JSON.parseObject(params, SearchMessageParams.class);

        //参数校验
        if (smpa.getMessChatUuid() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定会话", "listMsg"));
            return;
        }
        if (smpa.getMessEndDate() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定消息结束时间", "listMsg"));
            return;
        }
        if (smpa.getMessStartDate() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定消息开始时间", "listMsg"));
            return;
        }

        List<Message> messageList = this.messageService.selectMessage(smpa);
        List<SearchMessageVO> voList = new ArrayList<SearchMessageVO>();
        if (!CollectionUtils.isEmpty(messageList)) {
            List<ChatUser> chatUserList = this.messageService.selectChatUserByChatUuid(smpa.getMessChatUuid());
            Map<Long, String> chatUserMap = new HashMap<Long, String>();
            for (ChatUser cItem : chatUserList) {
                chatUserMap.put(cItem.getCuseSuseUuid(), cItem.getCuseName());
            }
            for (Message item : messageList) {
                SearchMessageVO vo = new SearchMessageVO();
                BeanUtil.copyProperties(item, vo);
                vo.setMessSuseName(chatUserMap.containsKey(vo.getMessSuseUuid()) ? chatUserMap.get(vo.getMessSuseUuid()) : "未知");
                voList.add(vo);
            }
        }
        sendWebSocket(channel, ResponseStompFactory.createOk(voList, "listMsg"));
    }

    private void editGroupUser(String params, Channel channel) {
        if (StringUtil.isNotBlank(params)) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定修改群名片信息", "editGroupUser"));
            return;
        }
        EditChatGroupUserParams egup = JSON.parseObject(params, EditChatGroupUserParams.class);

        if (egup.getCgusCgroUuid() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定群的Uuid", "editGroupUser"));
            return;
        }
        if (egup.getCgusName() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定成员名片", "editGroupUser"));
            return;
        }
        if (egup.getCgusUuid() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定群成员Uuid", "editGroupUser"));
            return;
        }

        List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(egup.getCgusCgroUuid());
        if (this.messageService.updateChatGroupUserByCgusUuid(egup) < 1) {
            sendWebSocket(channel, ResponseStompFactory.createBad("修改群名片失败", "editGroupUser"));
        } else {
            sendWebSocket(channel, ResponseStompFactory.createOk("修改群名片成功", "editGroupUser"));
        }
        for (ChatGroupUser chatGroupUser : chatGroupUserList) {
            sendWebSocket(ChannelUtil.getInstance().getChannel(chatGroupUser.getCgusSuseUuid()), ResponseStompFactory.createOk("修改群名片成功", "editGroupUser"));
        }
    }

    private void editGroup(String params, Channel channel) {
        if (StringUtil.isNotBlank(params)) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定修改群信息", "editGroup"));
            return;
        }
        EditChatGroupParams ecgp = JSON.parseObject(params, EditChatGroupParams.class);

        if (ecgp.getCgroUuid() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定修改群Uuid", "editGroup"));
            return;
        }

        this.messageService.updateChatGroup(ecgp, ChannelUtil.getInstance().getSuseUuid(channel).toString());
        List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(ecgp.getCgroUuid());
        ChatGroup chatGroup = this.messageService.selectChatGroupByCgroUuid(ecgp.getCgroUuid());
        ChatGroup chat = chatGroup;
        ChatGroupVO vo = new ChatGroupVO();
        BeanUtil.copyProperties(chat, vo);
        vo.setChatGroupUserList(chatGroupUserList);
        for (ChatGroupUser chatGroupUser : chatGroupUserList) {
            sendWebSocket(ChannelUtil.getInstance().getChannel(chatGroupUser.getCgusSuseUuid()), ResponseStompFactory.createOk(vo, "editGroup"));
        }
    }

    private void delGroup(String params, Channel channel) {
        if (StringUtil.isNotBlank(params)) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定删除群信息", "delGroup"));
            return;
        }
        DelChatGroupUserParams dgup = JSON.parseObject(params, DelChatGroupUserParams.class);

        if (dgup.getCgroUuid() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定群号码Uuid", "delGroup"));
            return;
        }
        if (dgup.getCgusUuid() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定群成员Uuid", "delGroup"));
            return;
        }

//        获取所有成员来通知删除
        List<ChatGroupUser> chatGroupUserList0 = this.messageService.selectChatGroupUser(dgup.getCgroUuid());
//        通过主键删除群成员
        int count[] = this.messageService.deleteChatGroupUserByCgusUuid(dgup);
        ChatGroup cGroup = this.messageService.selectChatGroupByCgroUuid(dgup.getCgroUuid());
        List<ChatGroupVO> voList = new ArrayList<ChatGroupVO>();
        if (cGroup != null) {
            User user = null;
            ChatGroup chat = cGroup;
            ChatGroupVO vo = new ChatGroupVO();
            BeanUtil.copyProperties(chat, vo);
//                获取群成员的并返回
            List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(chat.getCgroUuid());
            vo.setChatGroupUserList(chatGroupUserList);
            user = this.messageService.selectBySuseUuid(chat.getCgroCsuseUuid(), true);
            vo.setCgroCsuseName(user == null ? "未知" : user.getUserName());
            voList.add(vo);
            for (ChatGroupUser chatGroupUser : chatGroupUserList0) {
                sendWebSocket(ChannelUtil.getInstance().getChannel(chatGroupUser.getCgusSuseUuid()), ResponseStompFactory.createOk(voList, "delGroup"));
            }
        }
    }

    private void addGroup(String params, Channel channel) {
        /**
         * 增加人
         * 群和增加人在同一个圈里面
         * 被增加人必须激活
         * 群人数需要增加
         * 增加会话人
         * 会话人数需要增加
         */
        if (StringUtil.isNotBlank(params)) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请群增加人信息", "addGroup"));
            return;
        }
        AddChatGroupUserParams agup = JSON.parseObject(params, AddChatGroupUserParams.class);

        if (agup.getCgusSuseUuid() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定群参与用户Uuid", "addGroup"));
            return;
        }
        if (agup.getUserUuid() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定被拉人Uuid", "addGroup"));
            return;
        }
        if (agup.getCgusCgroUuid() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定群Uuid", "addGroup"));
            return;
        }

        try {
            int count = this.messageService.addChatGroupUser(agup);
        } catch (Exception e) {
            //   LOGGER.error(e.getMessage());
            sendWebSocket(channel, ResponseStompFactory.createOk("新增失败", "addGroup"));
        } finally {
            ChatGroup cGroup = this.messageService.selectChatGroupByCgroUuid(agup.getCgusCgroUuid());
            List<ChatGroupVO> voList = new ArrayList<ChatGroupVO>();
            if (cGroup != null) {
                User user = null;
                ChatGroup chat = cGroup;
                ChatGroupVO vo = new ChatGroupVO();
                BeanUtil.copyProperties(chat, vo);
//                获取群成员的并返回
                List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(chat.getCgroUuid());
                vo.setChatGroupUserList(chatGroupUserList);
                user = this.messageService.selectBySuseUuid(chat.getCgroCsuseUuid(), true);
                vo.setCgroCsuseName(user == null ? "未知" : user.getUserName());
                voList.add(vo);
                for (ChatGroupUser item : chatGroupUserList) {
                    sendWebSocket(ChannelUtil.getInstance().getChannel(item.getCgusSuseUuid()), ResponseStompFactory.createOk(voList, "addGroup"));
                }
            }
        }
//        this.send(principal.getName(), ResponseStompFactory.createOk("新增成功", "addGroup"));

    }


    private void outGroup(String params, Channel channel) {
        /**
         * 本身才能退出
         * 群人数需要减少
         * 退出会话
         * 会话总人数减少
         */
        if (StringUtil.isNotBlank(params)) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定退出群信息", "outGroup"));
            return;
        }
        OutGroupParams ogpa = JSON.parseObject(params, OutGroupParams.class);

        if (ogpa.getCgusSuseUuid() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定退出群的用户", "outGroup"));
            return;
        }
        if (ogpa.getCgusCgroUuid() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定群号码", "outGroup"));
            return;
        }

        Long chatGroupChatUuid = null;
        List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(ogpa.getCgusCgroUuid());
        try {
            User user = this.messageService.selectUserByUserUuid(ogpa.getCgusSuseUuid());
            chatGroupChatUuid = this.messageService.outGroup(ogpa);
        } catch (Exception e) {
            //  LOGGER.info(e.getMessage());
            sendWebSocket(channel, ResponseStompFactory.createOk("退群失败", "outGroup"));
        }
        OutGroupVo vo = new OutGroupVo();
        BeanUtil.copyProperties(params, vo);
        vo.setGroupChatUuid(chatGroupChatUuid);
        for (ChatGroupUser item : chatGroupUserList) {
            sendWebSocket(ChannelUtil.getInstance().getChannel(item.getCgusSuseUuid()), ResponseStompFactory.createOk(params, "outGroup"));
        }
    }

    private void listGroup(String params, Channel channel) {
        if (StringUtil.isNotBlank(params)) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定用户", "listGroup"));
            return;
        }
        Long suseUuid = Long.valueOf(params);
        List<ChatGroup> cGroupList = this.messageService.selectChatGroup(suseUuid);
        List<ChatGroupVO> voList = new ArrayList<ChatGroupVO>();
        if (!CollectionUtils.isEmpty(cGroupList)) {
            User user = null;
            for (ChatGroup chat : cGroupList) {
                ChatGroupVO vo = new ChatGroupVO();
                BeanUtil.copyProperties(chat, vo);
//                获取群成员的并返回
                List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(chat.getCgroUuid());
                vo.setChatGroupUserList(chatGroupUserList);
                user = this.messageService.selectBySuseUuid(chat.getCgroCsuseUuid(), true);
                vo.setCgroCsuseName(user == null ? "未知" : user.getUserName());
                voList.add(vo);
            }
        }
        sendWebSocket(channel, ResponseStompFactory.createOk(voList, "listGroup"));
    }

    private void openGroup2(String params, Channel channel) {
        if (StringUtil.isNotBlank(params)) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定创建群的参数", "openGroup2"));
            return;
        }
        OpenGroupParams2 ogp2 = JSON.parseObject(params, OpenGroupParams2.class);
        if (!StringUtil.isBlank(ogp2.getCgorName())) {
            if (ogp2.getCgorName().length() < 1 || ogp2.getCgorName().length() > 64) {
                sendWebSocket(channel, ResponseStompFactory.createBad("会话名字<1~64位>", "openGroup2"));
                return;
            }
        }
        if (ogp2.getsSuseUuid() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定创建群用户", "openGroup2"));
            return;
        }
        if (ogp2.geteSuseUuid().isEmpty() || ogp2.geteSuseUuid().size() < 1 || ogp2.geteSuseUuid().size() > 255) {
            sendWebSocket(channel, ResponseStompFactory.createBad("参与群用户<1~255人>", "openGroup2"));
            return;
        }

        ChatGroup chatGroup = this.messageService.insertGroup(ogp2);
        List<Long> userList = ogp2.geteSuseUuid();
        OpenGroupVO vo = new OpenGroupVO();
        BeanUtil.copyProperties(chatGroup, vo);
        List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(chatGroup.getCgroUuid());
        vo.setChatGroupUserList(chatGroupUserList);
        for (Long user : userList) {
            sendWebSocket(ChannelUtil.getInstance().getChannel(user), ResponseStompFactory.createOk(vo, "openGroup2"));

        }
        sendWebSocket(channel, ResponseStompFactory.createOk(vo, "openGroup2"));
    }

    private void listFriend(String params, Channel channel) {
        if (StringUtil.isNotBlank(params)) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定用户", "listFriend"));
            return;
        }
        Long suseUuid = Long.valueOf(params);
        User user = this.messageService.selectBySuseUuid(suseUuid, false);
        if (user == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("获取好友列表失败，该用户不存在", "listFriend"));
        }
        List<User> userList = this.messageService.selectUserByEnteUuid(user.getUserEnteUuid(), false, user.getUserUuid());
        List<UserVO> voList = new ArrayList<UserVO>();
        if (!CollectionUtils.isEmpty(userList)) {
            for (User item : userList) {
                UserVO vo = new UserVO();
                BeanUtil.copyProperties(item, vo);
                voList.add(vo);
            }
        }
        sendWebSocket(channel, ResponseStompFactory.createOk(voList, "listFriend"));
    }

    private void chat(String params, Channel channel) {
        if (StringUtil.isNotBlank(params)) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定会话", "chat"));
            return;
        }
        MessageParams mpar = JSON.parseObject(params, MessageParams.class);
        if (mpar.getMessContentType() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定消息类型", "chat"));
            return;
        }
        if (mpar.getMessSuseUuid() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定会话用户Uuid", "chat"));
            return;
        }
        if (mpar.getMessChatUuid() == null) {//没有会话
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定会话", "chat"));
            return;
        } else {//有会话Uuid
            Chat chatParams = new Chat();
            chatParams.setChatUuid(mpar.getMessChatUuid());
            Chat chat = this.messageService.selectChatOne(chatParams);
            if (chat == null) {//没有chat
                sendWebSocket(channel, ResponseStompFactory.createBad("会话不存在", "chat"));
            } else {
                List<ChatUser> chatUserList = this.messageService.selectChatUserByChatUuid(mpar.getMessChatUuid());
                Date currTime = new Date(System.currentTimeMillis());
                if (1 < this.messageService.insertMessageAndUpdateChat(mpar, currTime)) {//添加成功
                    //更新chat消息时间
                    MessageVO vo = new MessageVO();
                    vo.setMessStatus(Message.SUCCESS);
                    vo.setMessChatUuid(mpar.getMessChatUuid());
                    vo.setMessContent(mpar.getMessContent());
                    vo.setMessContentType(mpar.getMessContentType());
                    vo.setMessDate(currTime.getTime());
                    vo.setMessSuseUuid(mpar.getMessSuseUuid());
                    ChatUser sChatUser = this.messageService.selectChatUserByChatUuidAndSuseUuid(mpar.getMessChatUuid(),
                            mpar.getMessSuseUuid());
                    vo.setMessSuseName(sChatUser == null ? "未知" : sChatUser.getCuseName());
                    for (ChatUser item : chatUserList) {
                        sendWebSocket(ChannelUtil.getInstance().getChannel(item.getCuseSuseUuid()), ResponseStompFactory.createOk(vo, "chat"));
                    }
                } else {
                    sendWebSocket(channel, ResponseStompFactory.createBad("消息发送失败", "chat"));
                }
            }
        }
    }

    private void openChat(String params, Channel channel) {
        if (StringUtil.isNotBlank(params)) {
            sendWebSocket(channel, ResponseStompFactory.createBad("开启会话失败", "chatOpen"));
            return;
        }
        OpenParams opar = JSON.parseObject(params, OpenParams.class);

        if (!StringUtil.isBlank(opar.getChatName())) {
            if (opar.getChatName().length() <= 1 || opar.getChatName().length() > 64) {
                sendWebSocket(channel, ResponseStompFactory.createBad("会话名字<1~64位>", "chatOpen"));
                return;
            }
        }
        if (opar.getChatType() == null || opar.getChatType() < 0 || opar.getChatType() > 2) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定会话类型<0~2>", "chatOpen"));
            return;
        }
        if (opar.getsSuseUuid() == null) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定发起会话方", "chatOpen"));
            return;
        }

        if (opar.getChatType() == 0) {//系统会话
            Chat tmp = new Chat();
            tmp.setChatCsuseUuid(opar.getsSuseUuid());
            tmp.setChatType(0);
            tmp.setChatName("系统通知");
            Chat chat = this.messageService.selectChatOne(tmp);
            if (chat == null) {//如果会话不存在创建
                chat = this.messageService.insertChat(opar);
            }
            OpenVO vo = new OpenVO();
            BeanUtil.copyProperties(chat, vo);
            sendWebSocket(channel, ResponseStompFactory.createOk(vo, "chatOpen"));
        } else if (opar.getChatType() == 1) {//一对一
            Chat chat = this.messageService.selectChatOneToOne(opar.getsSuseUuid(), opar.geteSuseUuid());
            if (chat == null) {//对方创建
                chat = this.messageService.selectChatOneToOne(opar.geteSuseUuid(), opar.getsSuseUuid());
            }
            if (chat == null) {//如果会话不存在创建
                chat = this.messageService.insertChat(opar);
            }
//            List<User> user = this.messageService.selectUserByEnteUuid(params.getsSuseUuid(),true);
            User user0 = this.messageService.selectUserByUserUuid(chat.getChatCsuseUuid());
            User user1 = this.messageService.selectUserByUserUuid(chat.getChatEsuseUuid());
            OpenVO vo = new OpenVO();
//            设置会话会话创建者用户
            vo.setChatCsuseName(user0 == null ? "未知" : user0.getUserName());
//            设置会话名字为对方姓名


            BeanUtil.copyProperties(chat, vo);
            //创建者姓名
            if (ChannelUtil.getInstance().getSuseUuid(channel).equals(user0.getUserUuid().toString())) {
                vo.setChatName(user1 == null ? "未知" : user1.getUserName());
            } else {
                vo.setChatName(user0 == null ? "未知" : user0.getUserName());
            }
            sendWebSocket(channel, ResponseStompFactory.createOk(vo, "chatOpenS"));
            if (opar.geteSuseUuid().equals(user0.getUserUuid())) {
                vo.setChatName(user1 == null ? "未知" : user1.getUserName());
            } else {
                vo.setChatName(user0 == null ? "未知" : user0.getUserName());
            }
            sendWebSocket(ChannelUtil.getInstance().getChannel(opar.geteSuseUuid()), ResponseStompFactory.createOk(vo, "chatOpenE"));
        } else if (opar.getChatType() == 2) {
            if (opar.getCgroUuid() == null) {
                sendWebSocket(channel, ResponseStompFactory.createBad("创建会话失败", "chatOpen"));
            }
            ChatGroup chatGroup = this.messageService.selectChatGroupByCgroUuid(opar.getCgroUuid());
            if (chatGroup == null) {
                sendWebSocket(channel, ResponseStompFactory.createBad("创建会话失败,不存在该群", "chatOpen"));
            }
            Chat chat = null;
            if (chatGroup.getCgroChatUuid() == null) {
                chat = this.messageService.insertChat(opar);
                chatGroup.setCgroChatUuid(chat.getChatUuid());
                this.messageService.updateChatGroup(chatGroup);
            } else {
                Chat tmpParams = new Chat();
                tmpParams.setChatUuid(chatGroup.getCgroChatUuid());
                chat = this.messageService.selectChatOne(tmpParams);
            }
            if (chat == null) {
                sendWebSocket(channel, ResponseStompFactory.createBad("创建会话失败", "chatOpen"));
            }
            OpenVO vo = new OpenVO();
            BeanUtil.copyProperties(chat, vo);
            List<ChatUser> chatUserList = this.messageService.selectChatUserByChatUuid(chat.getChatUuid());
            Long sUserUuid = ChannelUtil.getInstance().getSuseUuid(channel);
            for (ChatUser item : chatUserList) {
                sendWebSocket(ChannelUtil.getInstance().getChannel(item.getCuseSuseUuid()), ResponseStompFactory.createOk(vo, sUserUuid.equals(item.getCuseSuseUuid()) ? "chatOpenS" : "chatOpenE"));
            }
        } else {
            sendWebSocket(channel, ResponseStompFactory.createBad("创建会话失败", "chatOpen"));
        }
    }

    //获取最近会话
    private void chatList(String params, Channel channel) {
        if (StringUtil.isNotBlank(params)) {
            sendWebSocket(channel, ResponseStompFactory.createBad("请指定用户", "chatList"));
            return;
        }
        Long suseUuid = Long.valueOf(params);
        boolean isAll = false;
        List<Chat> chatList = this.messageService.selectChatBySuseUuid(suseUuid, isAll);
        List<ChatVO> voList = new ArrayList<ChatVO>();

        if (!CollectionUtils.isEmpty(chatList)) {
            Message message = null;
            for (Chat chat : chatList) {
                ChatVO vo = new ChatVO();
                BeanUtil.copyProperties(chat, vo);
                message = this.messageService.selectLastMessage(chat.getChatUuid());
                if (message != null) {//如果有最后一条消息
                    ChatVO.MessageVO msgVO = new ChatVO.MessageVO();
                    BeanUtil.copyProperties(message, msgVO);
                    vo.setChatLastMessage(msgVO);
                }

                User user0 = this.messageService.selectUserByUserUuid(chat.getChatCsuseUuid());
                User user1 = this.messageService.selectUserByUserUuid(chat.getChatEsuseUuid());
                //           设置会话名字为对方姓名
                if (ChannelUtil.getInstance().getSuseUuid(channel).equals(user0.getUserUuid().toString())) {
                    vo.setChatName(user1 == null ? "未知" : user1.getUserName());
                } else {
                    vo.setChatName(user0 == null ? "未知" : user0.getUserName());
                }

                voList.add(vo);
            }
        }
        sendWebSocket(channel, ResponseStompFactory.createOk(voList, "chatList"));
    }

    public void sendWebSocket(Channel channel, ResponseStomp res) {
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(res)));
        }
    }

}
