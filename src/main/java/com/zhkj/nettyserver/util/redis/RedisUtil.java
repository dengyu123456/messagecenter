/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.util.redis;

import com.alibaba.fastjson.JSON;
import com.zhkj.nettyserver.util.redis.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

/**
 * Des:
 * ClassName: RedisUtil
 * Author: biqiang2017@163.com
 * Date: 2018/8/25
 * Time: 1:08
 */
@Component
public class RedisUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);
    @Autowired
    private JedisPool jedisPool;

    /**
     * 添加key value seconds<1没有时限
     *
     * @param key
     * @param seconds
     * @param value
     * @return
     */
    public void setValue(String key, int seconds, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (seconds < 1){
                jedis.set(key,value);
            }else{
                jedis.setex(key,seconds,value);
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e.getStackTrace());
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 添加key value seconds<1没有时限
     *
     * @param key
     * @param seconds
     * @param value
     * @return
     */
    public void setObject(String key, int seconds, Object value) {
        this.setValue(key,seconds,JSON.toJSONString(value));
    }

    /**
     * 续期Key值
     * @param key
     * @param seconds
     */
    public void updateExpire(String key,int seconds){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.expire(key,seconds);
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e.getStackTrace());
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 获取value
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e.getStackTrace());
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        return value;
    }

    /**
     * 获取object
     *
     * @param key
     * @return
     */
    public <T> T getObject(String key,Class<T> t) {
        String value = this.getValue(key);
        if (StringUtil.isBlank(value)){
            return null;
        }else{
            return JSON.parseObject(value,t);
        }
    }

    /**
     * 删除value
     *
     * @param key
     * @return
     */
    public void del(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e.getStackTrace());
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }
    /**
     * 增加队列value
     *
     * @param key
     * @param value
     * @return
     */
    public void rpush(String key,String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.rpush(key,value);
        }catch (Exception e){
            LOGGER.error(e.getMessage(),e.getStackTrace());
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 获取阻塞队列value
     *
     * @param key
     * @param t
     * @return
     */
    public <T> T rpop(String key,Class<T> t) {
        Jedis jedis = null;
        String resulStr = null;
        try {
            jedis = jedisPool.getResource();
            List<String> result = jedis.brpop(10,key);
            resulStr = result.get(1);

        }catch (Exception e){
            LOGGER.error(e.getMessage(),e.getStackTrace());
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
        if (resulStr.equals("nil")){
            return null;
        }else{
            return JSON.parseObject(resulStr,t);
        }
    }

}
