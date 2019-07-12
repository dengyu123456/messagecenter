package com.zhkj.nettyserver.message.domain.MessageTopic;


import java.util.List;

/**
 * Des:发布订阅参数
 * ClassName: Topic
 * Author: dengyi
 * Date: 2019-06-27 11:17
 */
public class Topic {

    //发送后台消息
    public static final String TOPIC_WS = "TOPIC_WS";

    //调用微信服务
    public static final String TOPIC_WX = "TOPIC_WX";

    //em
    public static final String TOPIC_EM = "TOPIC_EM";

    //指令
    private String action;

    //消息体
    private String params;

    //消息接收人
    private List<String> userList;

    //消息接收公司
    private List<String> enteList;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public List<String> getUserList() {
        return userList;
    }

    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    public List<String> getEnteList() {
        return enteList;
    }

    public void setEnteList(List<String> enteList) {
        this.enteList = enteList;
    }
}
