package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.message.domain.request.AddChatGroupUserParams;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Des:拉人进群Handler
 * ClassName: AddGroupHandler
 * Author: dengyi
 * Date: 2019-06-18 17:16
 */
public class AddGroupHandler extends SimpleChannelInboundHandler<AddChatGroupUserParams> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AddChatGroupUserParams params) throws Exception {

    }
}
