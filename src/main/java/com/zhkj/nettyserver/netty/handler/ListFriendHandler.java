package com.zhkj.nettyserver.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Des:拉取好友Handler
 * ClassName: ListFriendHandler
 * Author: dengyi
 * Date: 2019-06-18 17:22
 */
public class ListFriendHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Long suseUuid) throws Exception {

    }
}
