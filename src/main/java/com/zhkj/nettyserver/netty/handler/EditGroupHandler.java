package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.message.domain.request.EditChatGroupParams;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Des:修改群Handler
 * ClassName: EditGroupHandler
 * Author: dengyi
 * Date: 2019-06-18 17:14
 */
public class EditGroupHandler extends SimpleChannelInboundHandler<EditChatGroupParams> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, EditChatGroupParams params) throws Exception {

    }
}
