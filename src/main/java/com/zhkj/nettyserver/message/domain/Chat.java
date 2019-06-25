package com.zhkj.nettyserver.message.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFilter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.util.Date;

public class Chat {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 会话Uuid
     */
    @Column(name = "chat_uuid")
    private Long chatUuid;

    /**
     * 会话名字64
     */
    @Column(name = "chat_name")
    private String chatName;

    /**
     * 会话创建者Uuid
     */
    @Column(name = "chat_csuse_uuid")
    private Long chatCsuseUuid;

    /**
     * 一对一会话参与者Uuid
     */
    @Column(name = "chat_esuse_uuid")
    protected Long chatEsuseUuid;

    /**
     * 会话是否公开 0：公开 1：不公开
     */
    @Column(name = "chat_public")
    private Integer chatPublic;

    /**
     * 会话类型0：系统会话 1：一对一会话 2：多人会话
     */
    @Column(name = "chat_type")
    private Integer chatType;

    /**
     * 会话成员数zui多256
     */
    @Column(name = "chat_count")
    private Integer chatCount;

    /**
     * 最后一条消息时间
     */
    @Column(name = "chat_last_time")
    private Date chatLastTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取会话Uuid
     *
     * @return chat_uuid - 会话Uuid
     */
    public Long getChatUuid() {
        return chatUuid;
    }

    /**
     * 设置会话Uuid
     *
     * @param chatUuid 会话Uuid
     */
    public void setChatUuid(Long chatUuid) {
        this.chatUuid = chatUuid;
    }

    /**
     * 获取会话名字64
     *
     * @return chat_name - 会话名字64
     */
    public String getChatName() {
        return chatName;
    }

    /**
     * 设置会话名字64
     *
     * @param chatName 会话名字64
     */
    public void setChatName(String chatName) {
        this.chatName = chatName == null ? null : chatName.trim();
    }

    /**
     * 获取会话创建者Uuid
     *
     * @return chat_csuse_uuid - 会话创建者Uuid
     */
    public Long getChatCsuseUuid() {
        return chatCsuseUuid;
    }

    /**
     * 设置会话创建者Uuid
     *
     * @param chatCsuseUuid 会话创建者Uuid
     */
    public void setChatCsuseUuid(Long chatCsuseUuid) {
        this.chatCsuseUuid = chatCsuseUuid;
    }

    /**
     * 获取一对一会话参与者Uuid
     *
     * @return chat_esuse_uuid - 一对一会话参与者Uuid
     */
    public Long getChatEsuseUuid() {
        return chatEsuseUuid;
    }

    /**
     * 设置一对一会话参与者Uuid
     *
     * @param chatEsuseUuid 一对一会话参与者Uuid
     */
    public void setChatEsuseUuid(Long chatEsuseUuid) {
        this.chatEsuseUuid = chatEsuseUuid;
    }

    /**
     * 获取会话是否公开 0：公开 1：不公开
     *
     * @return chat_public - 会话是否公开 0：公开 1：不公开
     */
    public Integer getChatPublic() {
        return chatPublic;
    }

    /**
     * 设置会话是否公开 0：公开 1：不公开
     *
     * @param chatPublic 会话是否公开 0：公开 1：不公开
     */
    public void setChatPublic(Integer chatPublic) {
        this.chatPublic = chatPublic;
    }

    /**
     * 获取会话类型0：系统会话 1：一对一会话 2：多人会话
     *
     * @return chat_type - 会话类型0：系统会话 1：一对一会话 2：多人会话
     */
    public Integer getChatType() {
        return chatType;
    }

    /**
     * 设置会话类型0：系统会话 1：一对一会话 2：多人会话
     *
     * @param chatType 会话类型0：系统会话 1：一对一会话 2：多人会话
     */
    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }

    /**
     * 获取会话成员数zui多256
     *
     * @return chat_count - 会话成员数zui多256
     */
    public Integer getChatCount() {
        return chatCount;
    }

    /**
     * 设置会话成员数zui多256
     *
     * @param chatCount 会话成员数zui多256
     */
    public void setChatCount(Integer chatCount) {
        this.chatCount = chatCount;
    }

    /**
     * 获取最后一条消息时间
     *
     * @return chat_last_time - 最后一条消息时间
     */
    public Date getChatLastTime() {
        return chatLastTime;
    }

    /**
     * 设置最后一条消息时间
     *
     * @param chatLastTime 最后一条消息时间
     */
    public void setChatLastTime(Date chatLastTime) {
        this.chatLastTime = chatLastTime;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}