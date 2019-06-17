/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.message.service.impl;

import com.zhkj.nettyserver.common.util.redis.RedisUtil;
import com.zhkj.nettyserver.netty.ChannelUtil;
import com.zhkj.nettyserver.netty.Message;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


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
    @Async
    public void msgMonitor(){
        while (true){
            Message message =  redisUtil.rpop("",Message.class);
            if (message != null && message.getMessCgroUuid() != null){
                Channel channel = ChannelUtil.getInstance().getChannel(123L);
                if (channel != null){
                    channel.writeAndFlush(message);
                }
            }
        }
    }
}
