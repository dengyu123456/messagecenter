package com.zhkj.nettyserver.netty;

/**
 * Des:消息对象
 * ClassName: Message
 * Author: dengyi
 * Date: 2019-06-10 16:52
 */
public class Message {


    //判断是否群发
    private Integer messAction;

    //消息
    private String messMain;

    //发消息
    private Long suseUuid;

    //收消息的人
    private Long messSuseUuid;

    //第一次验证
    private String messToken;

    public String getMessToken() {
        return messToken;
    }

    public void setMessToken(String messToken) {
        this.messToken = messToken;
    }

    public Integer getMessAction() {
        return messAction;
    }

    public void setMessAction(Integer messAction) {
        this.messAction = messAction;
    }

    public String getMessMain() {
        return messMain;
    }

    public void setMessMain(String messMain) {
        this.messMain = messMain;
    }

    public Long getSuseUuid() {
        return suseUuid;
    }

    public void setSuseUuid(Long suseUuid) {
        this.suseUuid = suseUuid;
    }

    public Long getMessSuseUuid() {
        return messSuseUuid;
    }

    public void setMessSuseUuid(Long messSuseUuid) {
        this.messSuseUuid = messSuseUuid;
    }
}
