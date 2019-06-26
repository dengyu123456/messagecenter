package com.zhkj.nettyserver.netty.handler;

import com.zhkj.nettyserver.common.base.respone.ResponseStomp;
import com.zhkj.nettyserver.common.base.respone.ResponseStompFactory;
import com.zhkj.nettyserver.common.util.SpringUtil;
import com.zhkj.nettyserver.message.domain.request.SChatParams;
import com.zhkj.nettyserver.message.service.MessageService;
import com.zhkj.nettyserver.netty.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Des:系统会话Handler
 * ClassName: SchatHandler
 * Author: dengyi
 * Date: 2019-06-26 10:29
 */
public class SchatHandler extends SimpleChannelInboundHandler<SChatParams> {
    MessageService messageService = null;

    public SchatHandler() {
        messageService = (MessageService) SpringUtil.getBean("messageService");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SChatParams params) throws Exception {
        schat(params, ctx);
    }

    private void schat(SChatParams scpa, ChannelHandlerContext ctx) {
        if (scpa == null) {
            ctx.channel().writeAndFlush(ResponseStompFactory.createBad("请指定系统会话参数", "schat"));
            return;
        }

        Long[] userUuidList = scpa.getMessEuseUuid();
        Long[] enteUuidList = scpa.getMessEnteUuid();
        if ((userUuidList == null || userUuidList.length == 0) && (enteUuidList == null || enteUuidList.length == 0)) {
            //发送给所有人
            ResponseStomp schat = ResponseStompFactory.createOk(scpa.getMessContent(), "schat");
            ChannelUtil.getInstance().getChannelGroup(ctx).writeAndFlush(schat);
        }
        if (userUuidList != null && userUuidList.length > 0) {
            for (Long item : userUuidList) {
                //发送给需要接收的人
                Channel otherChannel = ChannelUtil.getInstance().getChannel(ctx.channel(), item);
                if (otherChannel != null && otherChannel.isActive()) {
                    otherChannel.writeAndFlush(ResponseStompFactory.createOk(scpa.getMessContent(), "schat"));
                }
            }

        } else if (enteUuidList != null && enteUuidList.length > 0) {
            //发送给公司
            ResponseStomp responseStomp = ResponseStompFactory.createOk(scpa.getMessContent(), "schat");
            for (Long enteUuid : enteUuidList) {
                ConcurrentHashMap<Long, Channel> chalMap = ChannelUtil.getInstance().getChalMap(enteUuid);
                if (chalMap == null || chalMap.isEmpty()) {
                    continue;
                }
                for (Channel channel : chalMap.values()) {
                    channel.writeAndFlush(responseStomp);
                }
            }
        }

    }
}
