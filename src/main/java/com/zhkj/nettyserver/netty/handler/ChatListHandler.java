package com.zhkj.nettyserver.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Des:获取最近会话Handler
 * ClassName: ChatListHandler
 * Author: dengyi
 * Date: 2019-06-18 17:24
 */
public class ChatListHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long suseUuid) throws Exception {

    }
}
