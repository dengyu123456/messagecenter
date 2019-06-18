package com.zhkj.nettyserver.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Des:获取群Handler
 * ClassName: ListGroupHandler
 * Author: dengyi
 * Date: 2019-06-18 17:19
 */
public class ListGroupHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long suseUuid) throws Exception {

    }
}
