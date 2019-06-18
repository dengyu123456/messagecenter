package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.message.domain.request.OpenParams;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Des:开启会话Handler
 * ClassName: ChatOpenHandler
 * Author: dengyi
 * Date: 2019-06-18 16:44
 */
public class ChatOpenHandler extends SimpleChannelInboundHandler<OpenParams> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, OpenParams params) throws Exception {

    }
}
