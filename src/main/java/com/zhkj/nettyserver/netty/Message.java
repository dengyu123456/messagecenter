package com.zhkj.nettyserver.netty;

/**
 * Des:消息对象
 * ClassName: Message
 * Author: dengyi
 * Date: 2019-06-10 16:52
 */
public class Message {

    private String MOOjaidsjioas;

    //判断是否群发
    private String messAction;

    //消息
    private String messMain;

    //发消息
    private Long suseUuid;

    //收消息
    private String messSuseUuid;

    public String getMOOjaidsjioas() {
        return MOOjaidsjioas;
    }

    public void setMOOjaidsjioas(String MOOjaidsjioas) {
        this.MOOjaidsjioas = MOOjaidsjioas;
    }

    public String getMessAction() {
        return messAction;
    }

    public void setMessAction(String messAction) {
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

    public String getMessSuseUuid() {
        return messSuseUuid;
    }

    public void setMessSuseUuid(String messSuseUuid) {
        this.messSuseUuid = messSuseUuid;
    }
}
