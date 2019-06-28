package com.zhkj.nettyserver.message.domain.MessageTopic;


/**
 * Des:发布订阅参数
 * ClassName: Topic
 * Author: dengyi
 * Date: 2019-06-27 11:17
 */
public class Topic {

    //发送后台消息
    static final String TOPIC_WS = "TOPIC_WS";

    //调用微信服务
    static final String TOPIC_WX ="TOPIC_WX";

    //
    static final String TOPIC_EM="TOPIC_EM";

    //指令
    private String Action;

    //消息体
    private String params;


    public static String getTopicWs() {
        return TOPIC_WS;
    }

    public static String getTopicWx() {
        return TOPIC_WX;
    }

    public static String getTopicEm() {
        return TOPIC_EM;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }
}
