/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.message.service.impl;

import com.zhkj.nettyserver.common.base.respone.ResponseStompFactory;
import com.zhkj.nettyserver.common.util.StringUtil;
import com.zhkj.nettyserver.common.util.redis.RedisUtil;
import com.zhkj.nettyserver.message.domain.Topic;
import com.zhkj.nettyserver.netty.ChannelUtil;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Des:
 * ClassName: RedisService
 * Author: biqiang2017@163.com
 * Date: 2019/6/17
 * Time: 10:58
 */
@Service("redisService")
public class RedisService {
    @Autowired
    private RedisUtil redisUtil;

    /**
     * redis消息监听
     */
    @Async("taskExecutor")
    public void msgMonitor() {
        while (true) {
            Topic topic = redisUtil.rpop("test", com.zhkj.nettyserver.message.domain.Topic.class);
            if (topic != null && StringUtil.isNotBlank(topic.getContent())) {
                if (Topic.getREDISA().equals(topic.getAction())) {
                    List<Long> consumerList = topic.getConsumerList();
                    if (consumerList == null || consumerList.size() <= 0) {
                        //发送消息给所有人  拿到所有管道
                        List<Channel> channelList = ChannelUtil.getInstance().getAllChannel();
                        for (Channel item : channelList) {
                            item.writeAndFlush(ResponseStompFactory.createOk(topic.getContent(), topic.getAction()));
                        }
                    } else {
                        for (Long item : consumerList) {
                            Channel channel = ChannelUtil.getInstance().getChannel(item);
                            if (channel != null && channel.isActive()) {
                                channel.writeAndFlush(ResponseStompFactory.createOk(topic.getContent(), topic.getAction()));
                            }
                        }
                    }
                } else if (Topic.getREDISB().equals(topic.getAction())) { //调用微信服务等 其他业务

                }
            }
        }
    }
}
