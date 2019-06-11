package com.zhkj.nettyserver.netty;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Des:会话管理类
 * ClassName: SessionUtil
 * Author: dengyi
 * Date: 2019-06-11 15:24
 */
public class SessionUtil {

    // userId -> channel 的映射
    private static final Map<Long, Channel> userIdChannelMap = new ConcurrentHashMap<>();


    //建立会话，保存连接映射
    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getSuseUuid(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getSuseUuid());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {

        return channel.attr(Attributes.SESSION).get()==null;
    }

    public static Session getSession(Channel channel) {

        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {

        return userIdChannelMap.get(userId);
    }
}
