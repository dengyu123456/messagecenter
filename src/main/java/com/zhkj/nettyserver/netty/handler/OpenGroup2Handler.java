package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.message.domain.request.OpenGroupParams2;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Des:开启群Handler
 * ClassName: OpenGroup2Handler
 * Author: dengyi
 * Date: 2019-06-18 17:21
 */
public class OpenGroup2Handler extends SimpleChannelInboundHandler<OpenGroupParams2> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, OpenGroupParams2 params) throws Exception {

    }
}
