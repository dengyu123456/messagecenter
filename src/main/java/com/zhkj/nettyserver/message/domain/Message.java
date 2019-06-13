package com.zhkj.nettyserver.message.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Message {
    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 消息Uuid
     */
    @Column(name = "mess_uuid")
    private Long messUuid;

    /**
     * 会话Uuid
     */
    @Column(name = "mess_chat_uuid")
    private Long messChatUuid;

    /**
     * 创建消息用户Uuid
     */
    @Column(name = "mess_suse_uuid")
    private Long messSuseUuid;

    /**
     * 消息时间戳
     */
    @Column(name = "mess_date")
    private Long messDate;

    /**
     * 消息类型0：普通消息
     */
    @Column(name = "mess_content_type")
    private Integer messContentType;

    /**
     * 消息
     */
    @Column(name = "mess_content")
    private String messContent;

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
     * 获取消息Uuid
     *
     * @return mess_uuid - 消息Uuid
     */
    public Long getMessUuid() {
        return messUuid;
    }

    /**
     * 设置消息Uuid
     *
     * @param messUuid 消息Uuid
     */
    public void setMessUuid(Long messUuid) {
        this.messUuid = messUuid;
    }

    /**
     * 获取会话Uuid
     *
     * @return mess_chat_uuid - 会话Uuid
     */
    public Long getMessChatUuid() {
        return messChatUuid;
    }

    /**
     * 设置会话Uuid
     *
     * @param messChatUuid 会话Uuid
     */
    public void setMessChatUuid(Long messChatUuid) {
        this.messChatUuid = messChatUuid;
    }

    /**
     * 获取创建消息用户Uuid
     *
     * @return mess_suse_uuid - 创建消息用户Uuid
     */
    public Long getMessSuseUuid() {
        return messSuseUuid;
    }

    /**
     * 设置创建消息用户Uuid
     *
     * @param messSuseUuid 创建消息用户Uuid
     */
    public void setMessSuseUuid(Long messSuseUuid) {
        this.messSuseUuid = messSuseUuid;
    }

    /**
     * 获取消息时间戳
     *
     * @return mess_date - 消息时间戳
     */
    public Long getMessDate() {
        return messDate;
    }

    /**
     * 设置消息时间戳
     *
     * @param messDate 消息时间戳
     */
    public void setMessDate(Long messDate) {
        this.messDate = messDate;
    }

    /**
     * 获取消息类型0：普通消息
     *
     * @return mess_content_type - 消息类型0：普通消息
     */
    public Integer getMessContentType() {
        return messContentType;
    }

    /**
     * 设置消息类型0：普通消息
     *
     * @param messContentType 消息类型0：普通消息
     */
    public void setMessContentType(Integer messContentType) {
        this.messContentType = messContentType;
    }

    /**
     * 获取消息
     *
     * @return mess_content - 消息
     */
    public String getMessContent() {
        return messContent;
    }

    /**
     * 设置消息
     *
     * @param messContent 消息
     */
    public void setMessContent(String messContent) {
        this.messContent = messContent == null ? null : messContent.trim();
    }
}