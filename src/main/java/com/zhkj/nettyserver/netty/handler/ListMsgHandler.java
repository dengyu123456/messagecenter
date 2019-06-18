package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.message.domain.request.SearchMessageParams;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Des:历史聊天记录Handler
 * ClassName: ListMsgHandler
 * Author: dengyi
 * Date: 2019-06-18 17:10
 */
public class ListMsgHandler extends SimpleChannelInboundHandler<SearchMessageParams> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SearchMessageParams params) throws Exception {

    }

}
