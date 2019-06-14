package com.zhkj.nettyserver.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Des:会话管理类
 * ClassName: SessionUtil
 * Author: dengyi
 * Date: 2019-06-11 15:24
 */
@Component
public class SessionUtil {

    // suseUuid -> channel 的映射
    private static final Map<Long, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    private static final Session session = new Session();

    //建立会话，保存连接映射
    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getSuseUuid(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    //删除映射
    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getSuseUuid());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {
        return channel.attr(Attributes.SESSION).get() == null;
    }

    public static Session getSession(Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    //通过用户uuid拿到管道
    public static Channel getChannel(Long userId) {
//        session.setSuseUuid(userId);
        return userIdChannelMap.get(userId);
    }

    //得到所有通道
    public static ChannelGroup getChannelGroup(ChannelHandlerContext ctx) {
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        Set<Long> keySet = userIdChannelMap.keySet();
        for (Iterator<Long> iterator = keySet.iterator(); iterator.hasNext(); ) {
            Long key = iterator.next();
            channelGroup.add(userIdChannelMap.get(key));
        }
        return channelGroup;
    }
}
