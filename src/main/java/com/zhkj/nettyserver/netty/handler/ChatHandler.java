package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.common.base.respone.ResponseStompFactory;
import com.zhkj.nettyserver.common.util.SpringUtil;
import com.zhkj.nettyserver.message.domain.Chat;
import com.zhkj.nettyserver.message.domain.ChatUser;
import com.zhkj.nettyserver.message.domain.Message;
import com.zhkj.nettyserver.message.domain.request.MessageParams;
import com.zhkj.nettyserver.message.domain.respone.MessageVO;
import com.zhkj.nettyserver.message.service.MessageService;
import com.zhkj.nettyserver.netty.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.List;

/**
 * Des:会话Handler
 * ClassName: ChatHandler
 * Author: dengyi
 * Date: 2019-06-18 16:43
 */
public class ChatHandler extends SimpleChannelInboundHandler<MessageParams> {
    MessageService messageService = null;

    public ChatHandler() {
        messageService = (MessageService) SpringUtil.getBean("messageService");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageParams params) throws Exception {
        chat(params, ctx.channel());
    }

    private void chat(MessageParams mpar, Channel channel) {
        if (mpar==null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定会话", "chat"));
            return;
        }
        if (mpar.getMessContentType() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定消息类型", "chat"));
            return;
        }
        if (mpar.getMessSuseUuid() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定会话用户Uuid", "chat"));
            return;
        }
        if (mpar.getMessChatUuid() == null) {//没有会话
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定会话", "chat"));
            return;
        } else {//有会话Uuid
            Chat rChatP = new Chat();
            rChatP.setChatUuid(mpar.getMessChatUuid());
            Chat chat = this.messageService.selectChatOne(rChatP);
            if (chat == null) {//没有chat
                channel.writeAndFlush(ResponseStompFactory.createBad("会话不存在", "chat"));
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
                    vo.setMessChatName(chat.getChatName());
                    vo.setMessChatType(chat.getChatType());
                    vo.setMessChatCount(chat.getChatCount());
                    ChatUser sChatUser = this.messageService.selectChatUserByChatUuidAndSuseUuid(mpar.getMessChatUuid(),
                            mpar.getMessSuseUuid());
                    vo.setMessSuseName(sChatUser == null ? "未知" : sChatUser.getCuseName());
                    for (ChatUser item : chatUserList) {
                        Channel otherChannel = ChannelUtil.getInstance().getChannel(channel, item.getCuseSuseUuid());
                        if (otherChannel!=null&&otherChannel.isActive()) {
                            otherChannel.writeAndFlush(ResponseStompFactory.createOk(vo, "chat"));
                        }
                    }
                } else {
                    channel.writeAndFlush(ResponseStompFactory.createBad("消息发送失败", "chat"));
                }
            }
        }
    }

}

