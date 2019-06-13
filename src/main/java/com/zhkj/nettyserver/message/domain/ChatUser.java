package com.zhkj.nettyserver.message.domain;

import javax.persistence.*;
import java.util.Date;

@Table(name = "chat_user")
public class ChatUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 会话成员Uuid
     */
    @Column(name = "cuse_uuid")
    private Long cuseUuid;

    /**
     * 会话参与用户Uuid
     */
    @Column(name = "cuse_suse_uuid")
    private Long cuseSuseUuid;

    /**
     * 会话Uuid
     */
    @Column(name = "cuse_chat_uuid")
    private Long cuseChatUuid;

    /**
     * 加入顺序
     */
    @Column(name = "cuse_order")
    private Integer cuseOrder;

    /**
     * 会话成员名片
     */
    @Column(name = "cuse_name")
    private String cuseName;

    /**
     * 数据状态0：正常 1：删除
     */
    @Column(name = "cuse_data_stutus")
    private Integer cuseDataStutus;

    /**
     * 最近会话状态
     */
    @Column(name = "cuse_chat_status")
    private Integer cuseChatStatus;

    /**
     * 是否在线0：在线 1：没有在线
     */
    @Column(name = "cuse_line")
    private Integer cuseLine;

    /**
     * 会话成员加入时间
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
     * 获取会话成员Uuid
     *
     * @return cuse_uuid - 会话成员Uuid
     */
    public Long getCuseUuid() {
        return cuseUuid;
    }

    /**
     * 设置会话成员Uuid
     *
     * @param cuseUuid 会话成员Uuid
     */
    public void setCuseUuid(Long cuseUuid) {
        this.cuseUuid = cuseUuid;
    }

    /**
     * 获取会话参与用户Uuid
     *
     * @return cuse_suse_uuid - 会话参与用户Uuid
     */
    public Long getCuseSuseUuid() {
        return cuseSuseUuid;
    }

    /**
     * 设置会话参与用户Uuid
     *
     * @param cuseSuseUuid 会话参与用户Uuid
     */
    public void setCuseSuseUuid(Long cuseSuseUuid) {
        this.cuseSuseUuid = cuseSuseUuid;
    }

    /**
     * 获取会话Uuid
     *
     * @return cuse_chat_uuid - 会话Uuid
     */
    public Long getCuseChatUuid() {
        return cuseChatUuid;
    }

    /**
     * 设置会话Uuid
     *
     * @param cuseChatUuid 会话Uuid
     */
    public void setCuseChatUuid(Long cuseChatUuid) {
        this.cuseChatUuid = cuseChatUuid;
    }

    /**
     * 获取加入顺序
     *
     * @return cuse_order - 加入顺序
     */
    public Integer getCuseOrder() {
        return cuseOrder;
    }

    /**
     * 设置加入顺序
     *
     * @param cuseOrder 加入顺序
     */
    public void setCuseOrder(Integer cuseOrder) {
        this.cuseOrder = cuseOrder;
    }

    /**
     * 获取会话成员名片
     *
     * @return cuse_name - 会话成员名片
     */
    public String getCuseName() {
        return cuseName;
    }

    /**
     * 设置会话成员名片
     *
     * @param cuseName 会话成员名片
     */
    public void setCuseName(String cuseName) {
        this.cuseName = cuseName == null ? null : cuseName.trim();
    }

    /**
     * 获取数据状态0：正常 1：删除
     *
     * @return cuse_data_stutus - 数据状态0：正常 1：删除
     */
    public Integer getCuseDataStutus() {
        return cuseDataStutus;
    }

    /**
     * 设置数据状态0：正常 1：删除
     *
     * @param cuseDataStutus 数据状态0：正常 1：删除
     */
    public void setCuseDataStutus(Integer cuseDataStutus) {
        this.cuseDataStutus = cuseDataStutus;
    }

    /**
     * 获取最近会话状态
     *
     * @return cuse_chat_status - 最近会话状态
     */
    public Integer getCuseChatStatus() {
        return cuseChatStatus;
    }

    /**
     * 设置最近会话状态
     *
     * @param cuseChatStatus 最近会话状态
     */
    public void setCuseChatStatus(Integer cuseChatStatus) {
        this.cuseChatStatus = cuseChatStatus;
    }

    /**
     * 获取是否在线0：在线 1：没有在线
     *
     * @return cuse_line - 是否在线0：在线 1：没有在线
     */
    public Integer getCuseLine() {
        return cuseLine;
    }

    /**
     * 设置是否在线0：在线 1：没有在线
     *
     * @param cuseLine 是否在线0：在线 1：没有在线
     */
    public void setCuseLine(Integer cuseLine) {
        this.cuseLine = cuseLine;
    }

    /**
     * 获取会话成员加入时间
     *
     * @return create_time - 会话成员加入时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置会话成员加入时间
     *
     * @param createTime 会话成员加入时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}