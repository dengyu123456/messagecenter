package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.message.domain.request.OutGroupParams;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Des:退出群Handler
 * ClassName: OutGroupHandler
 * Author: dengyi
 * Date: 2019-06-18 17:17
 */
public class OutGroupHandler extends SimpleChannelInboundHandler<OutGroupParams> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, OutGroupParams params) throws Exception {

    }
}
