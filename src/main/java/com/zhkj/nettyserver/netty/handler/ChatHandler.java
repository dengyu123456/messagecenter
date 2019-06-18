package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.message.domain.request.MessageParams;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Des:会话Handler
 * ClassName: ChatHandler
 * Author: dengyi
 * Date: 2019-06-18 16:43
 */
public class ChatHandler extends SimpleChannelInboundHandler<MessageParams> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageParams params) throws Exception {

    }
}
