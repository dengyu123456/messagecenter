/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.common.util.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Des:
 * ClassName: RedisConfiguration
 * Author: biqiang2017@163.com
 * Date: 2018/8/25
 * Time: 1:05
 */
@Configuration
@EnableCaching
public class RedisConfiguration {
    @Value("${spring.redis.host}")
    private String host = "127.0.0.1";

    @Value("${spring.redis.port}")
    private int port = 6379;

    // 0 - never expire
    private int expire = 0;

    @Value("${spring.redis.timeout}")
    private int timeout = 0;

    @Value("${spring.redis.password}")
    private String password = "";

    @Bean
    public JedisPool redisPoolFactory() {
        if (password != null && !"".equals(password)) {
            return new JedisPool(new JedisPoolConfig(), host, port, timeout, password);
        } else if (timeout != 0) {
            return new JedisPool(new JedisPoolConfig(), host, port, timeout);
        } else {
            return new JedisPool(new JedisPoolConfig(), host, port);
        }
    }
}
