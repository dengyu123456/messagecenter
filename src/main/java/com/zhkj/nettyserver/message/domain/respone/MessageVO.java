/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.message.domain.respone;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Des: 接受消息
 * ClassName: MessageParams
 * Author: biqiang2017@163.com
 * Date: 2018/11/8
 * Time: 0:52
 */
@ApiModel(value = "MessageVO",description = "消息VO")
public class MessageVO {
    /**
     * 消息状态
     */
    @ApiModelProperty(value = "",notes = "消息状态",example = "")
    private Integer messStatus;
    /**
     * 会话Uuid
     */
    @ApiModelProperty(value = "",notes = "会话Uuid",example = "")
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long messChatUuid;

    /**
     * 消息发出者Uuid
     */
    @ApiModelProperty(value = "",notes = "消息发出者Uuid",example = "")
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long messSuseUuid;

    /**
     * 消息发出者用户名
     */
    @ApiModelProperty(value = "",notes = "消息发出者用户名",example = "")
    private String messSuseName;

    /**
     * 消息时间戳yyyy-MM-dd HH:mm:ss
     */
    @ApiModelProperty(value = "",notes = "消息时间戳yyyy-MM-dd HH:mm:ss",example = "")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long messDate;

    /**
     * 消息类型0：普通消息
     */
    @ApiModelProperty(value = "",notes = "消息类型0：普通消息",example = "")
    private Integer messContentType;

    /**
     * 消息内容
     */
    @ApiModelProperty(value = "",notes = "消息内容",example = "")
    private String messContent;

    public Integer getMessStatus() {
        return messStatus;
    }

    public void setMessStatus(Integer messStatus) {
        this.messStatus = messStatus;
    }

    public Long getMessChatUuid() {
        return messChatUuid;
    }

    public void setMessChatUuid(Long messChatUuid) {
        this.messChatUuid = messChatUuid;
    }

    public Long getMessSuseUuid() {
        return messSuseUuid;
    }

    public void setMessSuseUuid(Long messSuseUuid) {
        this.messSuseUuid = messSuseUuid;
    }

    public String getMessSuseName() {
        return messSuseName;
    }

    public void setMessSuseName(String messSuseName) {
        this.messSuseName = messSuseName;
    }

    public Long getMessDate() {
        return messDate;
    }

    public void setMessDate(Long messDate) {
        this.messDate = messDate;
    }

    public Integer getMessContentType() {
        return messContentType;
    }

    public void setMessContentType(Integer messContentType) {
        this.messContentType = messContentType;
    }

    public String getMessContent() {
        return messContent;
    }

    public void setMessContent(String messContent) {
        this.messContent = messContent;
    }
}
