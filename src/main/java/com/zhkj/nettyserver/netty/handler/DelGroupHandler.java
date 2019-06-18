package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.message.domain.request.DelChatGroupUserParams;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Des:删除群Handler
 * ClassName: DelGroupHandler
 * Author: dengyi
 * Date: 2019-06-18 17:15
 */
public class DelGroupHandler extends SimpleChannelInboundHandler<DelChatGroupUserParams> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DelChatGroupUserParams params) throws Exception {

    }
}
