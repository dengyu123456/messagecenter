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

import java.util.Date;
import java.util.List;

/**
 * Des:
 * ClassName: OpenParams
 * Author: biqiang2017@163.com
 * Date: 2018/11/8
 * Time: 0:56
 */
@ApiModel(value = "OpenVO",description = "开启会话VO")
public class OpenVO {
    /**
     * 会话Uuid
     */
    @ApiModelProperty(value = "",notes = "会话Uuid",example = "")
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long chatUuid;

    /**
     * 会话名字64
     */
    @ApiModelProperty(value = "",notes = "会话名字64",example = "")
    private String chatName;

    /**
     * 会话创建者Uuid
     */
    @ApiModelProperty(value = "",notes = "会话创建者Uuid",example = "")
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long chatCsuseUuid;

    /**
     * 会话创建者用户
     */
    @ApiModelProperty(value = "",notes = "会话创建者用户",example = "")
    private String chatCsuseName;

    /**
     * 会话是否公开 0：公开 1：不公开
     */
    @ApiModelProperty(value = "",notes = "会话是否公开 0：公开 1：不公开",example = "")
    private Integer chatPublic;

    /**
     * 会话类型0：系统会话 1：一对一会话 2：多人会话
     */
    @ApiModelProperty(value = "",notes = "会话类型0：系统会话 1：一对一会话 2：多人会话",example = "")
    private Integer chatType;

    /**
     * 会话成员数
     */
    @ApiModelProperty(value = "",notes = "会话成员数",example = "")
    private Integer chatCount;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "",notes = "创建时间yyyy-MM-dd HH:mm:ss",example = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    //历史聊天记录
    private List<SearchMessageVO> messageVOList;

    public List<SearchMessageVO> getMessageVOList() {
        return messageVOList;
    }

    public void setMessageVOList(List<SearchMessageVO> messageVOList) {
        this.messageVOList = messageVOList;
    }

    public Long getChatUuid() {
        return chatUuid;
    }

    public void setChatUuid(Long chatUuid) {
        this.chatUuid = chatUuid;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public Long getChatCsuseUuid() {
        return chatCsuseUuid;
    }

    public void setChatCsuseUuid(Long chatCsuseUuid) {
        this.chatCsuseUuid = chatCsuseUuid;
    }

    public String getChatCsuseName() {
        return chatCsuseName;
    }

    public void setChatCsuseName(String chatCsuseName) {
        this.chatCsuseName = chatCsuseName;
    }

    public Integer getChatPublic() {
        return chatPublic;
    }

    public void setChatPublic(Integer chatPublic) {
        this.chatPublic = chatPublic;
    }

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }

    public Integer getChatCount() {
        return chatCount;
    }

    public void setChatCount(Integer chatCount) {
        this.chatCount = chatCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
