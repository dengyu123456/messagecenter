package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.message.domain.request.EditChatGroupUserParams;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Des:修改群名片Handler
 * ClassName: EditGroupUserHandler
 * Author: dengyi
 * Date: 2019-06-18 17:11
 */
public class EditGroupUserHandler extends SimpleChannelInboundHandler<EditChatGroupUserParams> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EditChatGroupUserParams params) throws Exception {

    }
}
