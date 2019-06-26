package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.common.base.respone.ResponseStompFactory;
import com.zhkj.nettyserver.common.util.BeanUtil;
import com.zhkj.nettyserver.common.util.SpringUtil;
import com.zhkj.nettyserver.common.util.StringUtil;
import com.zhkj.nettyserver.message.domain.Chat;
import com.zhkj.nettyserver.message.domain.Message;
import com.zhkj.nettyserver.message.domain.User;
import com.zhkj.nettyserver.message.domain.respone.ChatVO;
import com.zhkj.nettyserver.message.service.MessageService;
import com.zhkj.nettyserver.netty.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.collections.CollectionUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Des:获取最近会话Handler
 * ClassName: ChatListHandler
 * Author: dengyi
 * Date: 2019-06-18 17:24
 */
public class ChatListHandler extends SimpleChannelInboundHandler<Long> {

    MessageService messageService = null;

    public ChatListHandler() {
        messageService = (MessageService) SpringUtil.getBean("messageService");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long suseUuid) throws Exception {
        chatList(suseUuid, ctx.channel());
    }

    //获取最近会话
    private void chatList(Long suseUuid, Channel channel) {
        if (suseUuid == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定用户", "chatList"));
            return;
        }
        boolean isAll = false;
        List<Chat> chatList = this.messageService.selectChatBySuseUuid(suseUuid, isAll);
        List<ChatVO> voList = new ArrayList<ChatVO>();
        if (!CollectionUtils.isEmpty(chatList)) {
            Message message = null;
            for (Chat chat : chatList) {
                ChatVO vo = new ChatVO();
                BeanUtil.copyProperties(chat, vo);
                //      如果是一对一会话     设置会话名字为对方姓名
                if (chat.getChatType() == 1) {
                    User user0 = this.messageService.selectUserByUserUuid(chat.getChatCsuseUuid());
                    User user1 = this.messageService.selectUserByUserUuid(chat.getChatEsuseUuid());
                    if (ChannelUtil.getInstance().getSuseUuid(channel).equals(user0.getUserUuid())) {
                        vo.setChatName(user1 == null ? "未知" : user1.getUserName());
                    } else {
                        vo.setChatName(user0 == null ? "未知" : user0.getUserName());
                    }
                }
                message = this.messageService.selectLastMessage(chat.getChatUuid());
                if (message != null) {//如果有最后一条消息
                    ChatVO.MessageVO msgVO = new ChatVO.MessageVO();
                    BeanUtil.copyProperties(message, msgVO);
                    vo.setChatLastMessage(msgVO);

                }
                voList.add(vo);
            }
        }
        channel.writeAndFlush(ResponseStompFactory.createOk(voList, "chatList"));
    }
}
