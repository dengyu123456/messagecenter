package com.zhkj.nettyserver.message.domain.MessageTopic;

import java.util.List;

/**
 * Des:发送到webscoket参数
 * ClassName: ToWebScoketParams
 * Author: dengyi
 * Date: 2019-06-28 15:50
 */
public class ToWebScoketParams {
    //消息接收人
    private List<Long> userList;

    private List<Long> enteList;

    private String content;

    public List<Long> getUserList() {
        return userList;
    }

    public void setUserList(List<Long> userList) {
        this.userList = userList;
    }

    public List<Long> getEnteList() {
        return enteList;
    }

    public void setEnteList(List<Long> enteList) {
        this.enteList = enteList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
