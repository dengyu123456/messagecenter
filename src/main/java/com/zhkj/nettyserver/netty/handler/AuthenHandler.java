package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.netty.ChannelUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Des:认证处理类
 * ClassName: AuthenHandler
 * Author: dengyi
 * Date: 2019-06-27 08:48
 */
public class AuthenHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object o) throws Exception {
        //如果没有登录 关闭管道
        if (ChannelUtil.getInstance().hasLogin(ctx.channel())) {
            ctx.channel().close();
        } else {  //如果有管道 移除当前处理类
            ctx.pipeline().remove(this);
            super.channelRead(ctx, o);
        }
    }


}
