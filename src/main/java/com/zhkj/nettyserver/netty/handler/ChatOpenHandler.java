package com.zhkj.nettyserver.netty.handler;

import com.alibaba.fastjson.JSON;
import com.zhkj.nettyserver.common.base.respone.ResponseStompFactory;
import com.zhkj.nettyserver.common.util.BeanUtil;
import com.zhkj.nettyserver.common.util.SpringUtil;
import com.zhkj.nettyserver.common.util.StringUtil;
import com.zhkj.nettyserver.message.domain.Chat;
import com.zhkj.nettyserver.message.domain.ChatGroup;
import com.zhkj.nettyserver.message.domain.ChatUser;
import com.zhkj.nettyserver.message.domain.User;
import com.zhkj.nettyserver.message.domain.request.OpenParams;
import com.zhkj.nettyserver.message.domain.respone.OpenVO;
import com.zhkj.nettyserver.message.service.MessageService;
import com.zhkj.nettyserver.netty.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * Des:开启会话Handler
 * ClassName: ChatOpenHandler
 * Author: dengyi
 * Date: 2019-06-18 16:44
 */
public class ChatOpenHandler extends SimpleChannelInboundHandler<OpenParams> {

    MessageService messageService = null;

    public ChatOpenHandler() {
        messageService = (MessageService) SpringUtil.getBean("messageService");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, OpenParams params) throws Exception {
        openChat(params, ctx.channel());
    }

    private void openChat(OpenParams opar, Channel channel) {
        if (opar == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("开启会话失败", "chatOpen"));
            return;
        }

        if (!StringUtil.isBlank(opar.getChatName())) {
            if (opar.getChatName().length() <= 1 || opar.getChatName().length() > 64) {
                channel.writeAndFlush(ResponseStompFactory.createBad("会话名字<1~64位>", "chatOpen"));
                return;
            }
        }
        if (opar.getChatType() == null || opar.getChatType() < 0 || opar.getChatType() > 2) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定会话类型<0~2>", "chatOpen"));
            return;
        }
        if (opar.getsSuseUuid() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定发起会话方", "chatOpen"));
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
            channel.writeAndFlush(ResponseStompFactory.createOk(vo, "chatOpen"));
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
            if (ChannelUtil.getInstance().getSuseUuid(channel).equals(user0.getUserUuid())) {
                vo.setChatName(user1 == null ? "未知" : user1.getUserName());
            } else {
                vo.setChatName(user0 == null ? "未知" : user0.getUserName());
            }
            channel.writeAndFlush(ResponseStompFactory.createOk(vo, "chatOpenS"));
            if (opar.geteSuseUuid().equals(user0.getUserUuid())) {
                vo.setChatName(user1 == null ? "未知" : user1.getUserName());
            } else {
                vo.setChatName(user0 == null ? "未知" : user0.getUserName());
            }
            Channel otherChannel = ChannelUtil.getInstance().getChannel(channel, opar.geteSuseUuid());
            if (otherChannel!=null&&otherChannel.isActive()) {
                otherChannel.writeAndFlush(ResponseStompFactory.createOk(vo, "chatOpenE"));
            }
        } else if (opar.getChatType() == 2) {
            if (opar.getCgroUuid() == null) {
                channel.writeAndFlush(ResponseStompFactory.createBad("创建会话失败", "chatOpen"));
            }
            ChatGroup chatGroup = this.messageService.selectChatGroupByCgroUuid(opar.getCgroUuid());
            if (chatGroup == null) {
                channel.writeAndFlush(ResponseStompFactory.createBad("创建会话失败,不存在该群", "chatOpen"));
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
                channel.writeAndFlush(ResponseStompFactory.createBad("创建会话失败", "chatOpen"));
            }
            OpenVO vo = new OpenVO();
            BeanUtil.copyProperties(chat, vo);
            List<ChatUser> chatUserList = this.messageService.selectChatUserByChatUuid(chat.getChatUuid());
            Long sUserUuid = ChannelUtil.getInstance().getSuseUuid(channel);
            for (ChatUser item : chatUserList) {
                Channel otherChannel = ChannelUtil.getInstance().getChannel(channel, item.getCuseSuseUuid());
                if (otherChannel!=null&&otherChannel.isActive()) {
                    otherChannel.writeAndFlush(ResponseStompFactory.createOk(vo, sUserUuid.equals(item.getCuseSuseUuid()) ? "chatOpenS" : "chatOpenE"));
                }
            }
        } else {
            channel.writeAndFlush(ResponseStompFactory.createBad("创建会话失败", "chatOpen"));
        }
    }
}
