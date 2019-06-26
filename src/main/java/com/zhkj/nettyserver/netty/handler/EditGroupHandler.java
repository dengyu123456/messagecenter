package com.zhkj.nettyserver.netty.handler;

import com.alibaba.fastjson.JSON;
import com.zhkj.nettyserver.common.base.respone.ResponseStompFactory;
import com.zhkj.nettyserver.common.util.BeanUtil;
import com.zhkj.nettyserver.common.util.SpringUtil;
import com.zhkj.nettyserver.common.util.StringUtil;
import com.zhkj.nettyserver.message.domain.ChatGroup;
import com.zhkj.nettyserver.message.domain.ChatGroupUser;
import com.zhkj.nettyserver.message.domain.User;
import com.zhkj.nettyserver.message.domain.request.EditChatGroupParams;
import com.zhkj.nettyserver.message.domain.respone.ChatGroupVO;
import com.zhkj.nettyserver.message.service.MessageService;
import com.zhkj.nettyserver.netty.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * Des:修改群Handler
 * ClassName: EditGroupHandler
 * Author: dengyi
 * Date: 2019-06-18 17:14
 */
public class EditGroupHandler extends SimpleChannelInboundHandler<EditChatGroupParams> {
    MessageService messageService = null;

    public EditGroupHandler() {
        messageService = (MessageService) SpringUtil.getBean("messageService");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EditChatGroupParams params) throws Exception {
        editGroup(params, ctx.channel());
    }

    private void editGroup(EditChatGroupParams ecgp, Channel channel) {
        if (ecgp == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定修改群信息", "editGroup"));
            return;
        }
        if (ecgp.getCgroUuid() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定修改群Uuid", "editGroup"));
            return;
        }
        this.messageService.updateChatGroup(ecgp, ChannelUtil.getInstance().getSuseUuid(channel).toString());
        List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(ecgp.getCgroUuid());
        ChatGroup chatGroup = this.messageService.selectChatGroupByCgroUuid(ecgp.getCgroUuid());
        User user = this.messageService.selectUserByUserUuid(chatGroup.getCgroCsuseUuid());
        ChatGroupVO vo = new ChatGroupVO();
        BeanUtil.copyProperties(chatGroup, vo);
        vo.setCgroCsuseName(user.getUserName());
        vo.setOldCgroName(ecgp.getOldCgroName());
        vo.setChatGroupUserList(chatGroupUserList);
        for (ChatGroupUser chatGroupUser : chatGroupUserList) {
            Channel otherChannel = ChannelUtil.getInstance().getChannel(channel, chatGroupUser.getCgusSuseUuid());
            if (otherChannel != null && otherChannel.isActive()) {
                otherChannel.writeAndFlush(ResponseStompFactory.createOk(vo, "editGroup"));
            }
        }
    }
}
