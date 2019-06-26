package com.zhkj.nettyserver.netty.handler;

import com.alibaba.fastjson.JSON;
import com.zhkj.nettyserver.common.base.respone.ResponseStompFactory;
import com.zhkj.nettyserver.common.util.SpringUtil;
import com.zhkj.nettyserver.common.util.StringUtil;
import com.zhkj.nettyserver.message.domain.ChatGroupUser;
import com.zhkj.nettyserver.message.domain.request.EditChatGroupUserParams;
import com.zhkj.nettyserver.message.service.MessageService;
import com.zhkj.nettyserver.netty.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * Des:修改群名片Handler
 * ClassName: EditGroupUserHandler
 * Author: dengyi
 * Date: 2019-06-18 17:11
 */
public class EditGroupUserHandler extends SimpleChannelInboundHandler<EditChatGroupUserParams> {
    MessageService messageService = null;

    public EditGroupUserHandler() {
        messageService = (MessageService) SpringUtil.getBean("messageService");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EditChatGroupUserParams params) throws Exception {
        editGroupUser(params, ctx.channel());
    }

    private void editGroupUser(EditChatGroupUserParams egup, Channel channel) {
        if (egup == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定修改群名片信息", "editGroupUser"));
            return;
        }

        if (egup.getCgusCgroUuid() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定群的Uuid", "editGroupUser"));
            return;
        }
        if (egup.getCgusName() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定成员名片", "editGroupUser"));
            return;
        }
        if (egup.getCgusUuid() == null) {
            channel.writeAndFlush(ResponseStompFactory.createBad("请指定群成员Uuid", "editGroupUser"));
            return;
        }

        List<ChatGroupUser> chatGroupUserList = this.messageService.selectChatGroupUser(egup.getCgusCgroUuid());
        if (this.messageService.updateChatGroupUserByCgusUuid(egup) < 1) {
            channel.writeAndFlush(ResponseStompFactory.createBad("修改群名片失败", "editGroupUser"));
        } else {
            channel.writeAndFlush(ResponseStompFactory.createOk("修改群名片成功", "editGroupUser"));
        }
        ChannelUtil channelUtil = ChannelUtil.getInstance();
        for (ChatGroupUser chatGroupUser : chatGroupUserList) {
            channelUtil.getChannel(channel, chatGroupUser.getCgusSuseUuid()).writeAndFlush(ResponseStompFactory.createOk("修改群名片成功", "editGroupUser"));
        }
    }


}
