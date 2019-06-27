package com.zhkj.nettyserver.message.domain;

import java.util.List;

/**
 * Des:发布订阅参数
 * ClassName: Topic
 * Author: dengyi
 * Date: 2019-06-27 11:17
 */
public class Topic<T> {

    //发送后台消息
    static final String REDISA = "A";

    //调用微信服务
    static final String REDISB="B";

    //订阅消息方
    private List<T> consumerList;

    //指令
    private String Action;

    //消息体
    private String content;


    public static String getREDISA() {
        return REDISA;
    }

    public static String getREDISB() {
        return REDISB;
    }


    public List<T> getConsumerList() {
        return consumerList;
    }

    public void setConsumerList(List<T> consumerList) {
        this.consumerList = consumerList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }
}
