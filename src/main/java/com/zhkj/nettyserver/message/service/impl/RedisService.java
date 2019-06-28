/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.message.service.impl;

import com.zhkj.nettyserver.common.util.StringUtil;
import com.zhkj.nettyserver.common.util.redis.RedisUtil;
import com.zhkj.nettyserver.message.domain.MessageTopic.Topic;
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

    @Autowired
    private TopicService topicService;

    /**
     * redis消息监听
     */
    @Async("taskExecutor")
    public void msgMonitor() {
        while (true) {
            Topic topic = redisUtil.rpop("Topic", Topic.class);
            if (topic != null && StringUtil.isNotBlank(topic.getParams())) {
                if (Topic.getTopicWs().equals(topic.getAction())) {
                    topicService.MsgToWebScoket(topic);
                } else if (Topic.getTopicWx().equals(topic.getAction())) { //调用微信服务等 其他业务
                    topicService.MsgToWX(topic);
                } else if (false) {//发布到移动端

                }
            }
        }
    }
}
