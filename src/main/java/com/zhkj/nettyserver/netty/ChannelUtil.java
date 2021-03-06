package com.zhkj.nettyserver.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Des: 会话管理工具类
 * ClassName: ChannelUtil
 * Author: dengyi
 * Date: 2019-06-11 15:24
 */
public class ChannelUtil {

    //公司层封装
    private Map<Long, ConcurrentHashMap<Long, Channel>> echaMap = null;

    private ChannelUtil() {
        this.echaMap = new HashMap<>();
    }

//    private static class Holder {
//        private static final ChannelUtil instance = new ChannelUtil();
//    }
//
//    public static ChannelUtil getInstance() {
//        return Holder.instance;
//    }

    private static volatile ChannelUtil instance = null;

    public static ChannelUtil getInstance() {
        if (instance == null) {
            synchronized (ChannelUtil.class) {
                instance = new ChannelUtil();
                return instance;
            }
        }
        return instance;
    }

    //建立会话，保存连接映射
    public void bindChannel(Long enteUuid, Long suseUuid, Channel channel) {
        //如果当前公司层级没有
        if (this.echaMap.get(enteUuid) == null) {
            ConcurrentHashMap<Long, Channel> chalMap = new ConcurrentHashMap<>();
            chalMap.put(suseUuid, channel);
            echaMap.put(enteUuid, chalMap);
            channel.attr(Attributes.SESSION).set(suseUuid);
            channel.attr(Attributes.ENTE).set(enteUuid);

        } else if (suseUuid != null && channel != null) { //如果公司层级有
            echaMap.get(enteUuid).put(suseUuid, channel);
            channel.attr(Attributes.SESSION).set(suseUuid);
            channel.attr(Attributes.ENTE).set(enteUuid);
        }
    }

//    //移除管道
//    public void unBindChannel(Channel channel) {
//        chalMap.remove(getSuseUuid(channel));
//    }

    //移除管道
    public void unBindChannel(Channel channel, Long suseUuid) {
        Long enteUuid = channel.attr(Attributes.ENTE).get();
        this.echaMap.get(enteUuid).remove(suseUuid);
    }

    //是否登錄
    public boolean hasLogin(Channel channel) {
        return channel.attr(Attributes.SESSION).get() == null;
    }

    //用戶Uuid
    public Long getSuseUuid(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public Long getEnteUuid(Channel channel) {
        return channel.attr(Attributes.ENTE).get();
    }

    //获取管道
    public Channel getChannel(Channel channel, Long suseUuid) {
        Long enteUuid = channel.attr(Attributes.ENTE).get();
        return this.echaMap.get(enteUuid).get(suseUuid);
    }

    public Channel getChannel(Long suseUuid) {
        for (ConcurrentHashMap<Long, Channel> chalMap : echaMap.values()) {
            for (Channel item : chalMap.values()) {
//                if (item.attr(Attributes.SESSION).get() == suseUuid) {
//                    return item;
//                }
                return chalMap.get(suseUuid);
            }
        }
        return null;
    }

    //获取所有管道组
    public ChannelGroup getChannelGroup(ChannelHandlerContext ctx) {
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        for (ConcurrentHashMap<Long, Channel> chalMap : echaMap.values()) {
            for (Channel item : chalMap.values()) {
                channelGroup.add(item);
            }
        }
        return channelGroup;
    }


    //获取公司下的所有管道映射
    public ConcurrentHashMap<Long, Channel> getChalMap(Long enteUuid) {
        return this.echaMap.get(enteUuid);
    }

    public List<Channel> getAllChannel() {
        List<Channel> channelList = new ArrayList<Channel>();
        for (ConcurrentHashMap<Long, Channel> chalMap : echaMap.values()) {
            for (Channel item : chalMap.values()) {
                if (item != null && item.isActive()) {
                    channelList.add(item);
                }
            }
        }
        return channelList;
    }
}
