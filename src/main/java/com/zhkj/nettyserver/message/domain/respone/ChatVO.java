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

/**
 * Des:
 * ClassName: ChatVO
 * Author: biqiang2017@163.com
 * Date: 2018/11/9
 * Time: 17:02
 */
@ApiModel(value = "ChatVO",description = "会话VO")
public class ChatVO {
    /**
     * 会话Uuid
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long chatUuid;

    /**
     * 会话名字64
     */
    private String chatName;

    /**
     * 会话创建者Uuid
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long chatCsuseUuid;

    /**
     * 会话是否公开 0：公开 1：不公开
     */
    private Integer chatPublic;

    /**
     * 会话类型0：系统会话 1：一对一会话 2：多人会话
     */
    private Integer chatType;

    /**
     * 会话成员数
     */
    private Integer chatCount;

    /**
     * 最后一条消息时间
     */
    private Date chatLastTime;

    /**
     * 最后一条消息VO
     */
    private MessageVO chatLastMessage;

    /**
     * 会话对方姓名
     */
    private String rSuseName;

    /**
     * 会话群组Uuid
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long cgroUuid;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public static class MessageVO{

        /**
         * 消息发出者Uuid
         */
        @JSONField(serializeUsing = ToStringSerializer.class)
        private Long messSuseUuid;

        /**
         * 消息发出者用户名
         */
        private String messSuseName;

        /**
         * 消息类型0：普通消息
         */
        private Integer messContentType;

        /**
         * 消息内容
         */
        private String messContent;

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

    public Date getChatLastTime() {
        return chatLastTime;
    }

    public void setChatLastTime(Date chatLastTime) {
        this.chatLastTime = chatLastTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public MessageVO getChatLastMessage() {
        return chatLastMessage;
    }

    public void setChatLastMessage(MessageVO chatLastMessage) {
        this.chatLastMessage = chatLastMessage;
    }

    public String getrSuseName() {
        return rSuseName;
    }

    public void setrSuseName(String rSuseName) {
        this.rSuseName = rSuseName;
    }

    public Long getCgroUuid() {
        return cgroUuid;
    }

    public void setCgroUuid(Long cgroUuid) {
        this.cgroUuid = cgroUuid;
    }
}
