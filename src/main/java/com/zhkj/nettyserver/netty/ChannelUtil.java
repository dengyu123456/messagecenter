package com.zhkj.nettyserver.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Des: 会话管理工具类
 * ClassName: ChannelUtil
 * Author: dengyi
 * Date: 2019-06-11 15:24
 */
public class ChannelUtil {
    private ConcurrentHashMap<Long, Channel> chalMap = null;

    private ChannelUtil() {
        this.chalMap = new ConcurrentHashMap<>();
    }

    private static class Holder {
        private static final ChannelUtil instance = new ChannelUtil();
    }

    public static ChannelUtil getInstance() {
        return Holder.instance;
    }

    //建立会话，保存连接映射
    public void bindChannel(Long suseUuid, Channel channel) {
        if (suseUuid != null && channel != null) {
            chalMap.put(suseUuid, channel);
            channel.attr(Attributes.SESSION).set(suseUuid);
        }
    }

    //移除管道
    public void unBindChannel(Channel channel) {
        chalMap.remove(getSuseUuid(channel));
    }

    //移除管道
    public void unBindChannel(Long suseUuid) {
        chalMap.remove(suseUuid);
    }

    //是否登錄
    public boolean hasLogin(Channel channel) {
        return channel.attr(Attributes.SESSION).get() != null;
    }

    //用戶Uuid
    public Long getSuseUuid(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    //获取管道
    public Channel getChannel(Long suseUuid) {
        return chalMap.get(suseUuid);
    }

    //获取管道组
    public ChannelGroup getChannelGroup(ChannelHandlerContext ctx) {
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        for (Channel item : chalMap.values()){
            channelGroup.add(item);
        }
        return channelGroup;
    }
}
