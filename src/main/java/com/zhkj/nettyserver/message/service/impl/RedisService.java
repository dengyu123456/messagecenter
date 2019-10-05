/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.message.service.impl;

import ch.qos.logback.core.status.OnPrintStreamStatusListenerBase;
import com.alibaba.fastjson.JSON;
import com.zhkj.nettyserver.common.util.redis.CustomPubSub;
import com.zhkj.nettyserver.common.util.redis.RedisUtil;
import com.zhkj.nettyserver.message.MessageTopic.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.plugins.DocumentationPluginsManager;

import javax.websocket.OnMessage;


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

    @Autowired
    private TopicService topicService;

    /**
     * redis消息监听
     */
    @Async("taskExecutor")
    public void msgMonitor() {
        /**
         * 这里使用redis有个很让人绝望的缺点，数据传输稳定性  断线期间的数据会丢失
         *
         * 线程会阻塞在这里，直到onPMessage处理完才能读取下一条数据，
         * 所以这里一定不能有过多的业务逻辑处理，这里只能做的是仅把消息发送出去
         */


        redisUtil.psubscribe("topic", new CustomPubSub() {
            @Override
            public void onPMessage(String pattern, String channel, String message) {
                super.onPMessage(pattern, channel, message);
                Topic topic = JSON.parseObject(message, Topic.class);
                if (Topic.TOPIC_WS.equals(topic.getAction())) {
                    topicService.msgToWebScoket(topic);
                } else if (Topic.TOPIC_WX.equals(topic.getAction())) { //调用微信服务等 其他业务
//                    topicService.MsgToWX(topic);
                }
            }
        });
    }

}

