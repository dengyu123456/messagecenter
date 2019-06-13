package com.zhkj.nettyserver.netty;

import com.zhkj.nettyserver.util.redis.RedisUtil;
import io.netty.channel.group.ChannelGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Des:订阅消息，在程序启动的时候启动该线程监听后台发布的消息
 * ClassName: SubscribeMessage
 * Author: dengyi
 * Date: 2019-06-12 09:20
 */
@Component
public class SubscribeMessage {
    @Autowired
    private RedisUtil redisUtil;

    private ChannelGroup channelGroup;
    /**
     * 读取后台消息
     */

    @Async("taskExecutor")
    void readRedis() {
        SessionUtil sessionUtil = new SessionUtil();
        String msg = null;
        while (true) {
            System.out.println("监听线程启动");
            msg = redisUtil.rpop("nil", String.class);
            if (msg != null) {
                channelGroup.writeAndFlush(msg);
            }
        }
    }

    public ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    public void setChannelGroup(ChannelGroup channelGroup) {
        readRedis();
        this.channelGroup = channelGroup;
    }
}
