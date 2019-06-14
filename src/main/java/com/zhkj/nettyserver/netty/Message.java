package com.zhkj.nettyserver.netty;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

/**
 * Des:消息对象
 * ClassName: Message
 * Author: dengyi
 * Date: 2019-06-10 16:52
 */
public class Message {

    //判断是否群发 0：系统消息 1群聊 2点对点 （3创建会话，4拉取最近通话，5，拉取群组 6，）
    private Integer messAction;

    //收到的消息
    private String messMain;

    //发消息的用户
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long suseUuid;

    //收消息的用户
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long messSuseUuid;

    //群组Uuid
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long messCgroUuid;

    //返回消息体
    private Object messObject;


    public Integer getMessAction() {
        return messAction;
    }

    public void setMessAction(Integer messAction) {
        this.messAction = messAction;
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

    public Long getMessCgroUuid() {
        return messCgroUuid;
    }

    public void setMessCgroUuid(Long messCgroUuid) {
        this.messCgroUuid = messCgroUuid;
    }

    public Object getMessObject() {
        return messObject;
    }

    public void setMessObject(Object messObject) {
        this.messObject = messObject;
    }

    public String getMessMain() {
        return messMain;
    }

}
