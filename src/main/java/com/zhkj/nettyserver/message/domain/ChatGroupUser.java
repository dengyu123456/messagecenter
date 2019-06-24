package com.zhkj.nettyserver.message.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

import javax.persistence.*;
import java.util.Date;

@Table(name = "chat_group_user")
public class ChatGroupUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 群成员Uuid
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    @Column(name = "cgus_uuid")
    private Long cgusUuid;

    /**
     * 群参与用户Uuid
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    @Column(name = "cgus_suse_uuid")
    private Long cgusSuseUuid;

    /**
     * 群Uuid
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    @Column(name = "cgus_cgro_uuid")
    private Long cgusCgroUuid;

    /**
     * 加入顺序
     */
    @Column(name = "cgus_order")
    private Integer cgusOrder;

    /**
     * 会话成员名片
     */
    @Column(name = "cgus_name")
    private String cgusName;

    /**
     * 数据状态0：正常 1：删除
     */
    @Column(name = "cgus_data_stutus")
    private Integer cgusDataStutus;

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
     * 获取群成员Uuid
     *
     * @return cgus_uuid - 群成员Uuid
     */
    public Long getCgusUuid() {
        return cgusUuid;
    }

    /**
     * 设置群成员Uuid
     *
     * @param cgusUuid 群成员Uuid
     */
    public void setCgusUuid(Long cgusUuid) {
        this.cgusUuid = cgusUuid;
    }

    /**
     * 获取群参与用户Uuid
     *
     * @return cgus_suse_uuid - 群参与用户Uuid
     */
    public Long getCgusSuseUuid() {
        return cgusSuseUuid;
    }

    /**
     * 设置群参与用户Uuid
     *
     * @param cgusSuseUuid 群参与用户Uuid
     */
    public void setCgusSuseUuid(Long cgusSuseUuid) {
        this.cgusSuseUuid = cgusSuseUuid;
    }

    /**
     * 获取群Uuid
     *
     * @return cgus_cgro_uuid - 群Uuid
     */
    public Long getCgusCgroUuid() {
        return cgusCgroUuid;
    }

    /**
     * 设置群Uuid
     *
     * @param cgusCgroUuid 群Uuid
     */
    public void setCgusCgroUuid(Long cgusCgroUuid) {
        this.cgusCgroUuid = cgusCgroUuid;
    }

    /**
     * 获取加入顺序
     *
     * @return cgus_order - 加入顺序
     */
    public Integer getCgusOrder() {
        return cgusOrder;
    }

    /**
     * 设置加入顺序
     *
     * @param cgusOrder 加入顺序
     */
    public void setCgusOrder(Integer cgusOrder) {
        this.cgusOrder = cgusOrder;
    }

    /**
     * 获取会话成员名片
     *
     * @return cgus_name - 会话成员名片
     */
    public String getCgusName() {
        return cgusName;
    }

    /**
     * 设置会话成员名片
     *
     * @param cgusName 会话成员名片
     */
    public void setCgusName(String cgusName) {
        this.cgusName = cgusName == null ? null : cgusName.trim();
    }

    /**
     * 获取数据状态0：正常 1：删除
     *
     * @return cgus_data_stutus - 数据状态0：正常 1：删除
     */
    public Integer getCgusDataStutus() {
        return cgusDataStutus;
    }

    /**
     * 设置数据状态0：正常 1：删除
     *
     * @param cgusDataStutus 数据状态0：正常 1：删除
     */
    public void setCgusDataStutus(Integer cgusDataStutus) {
        this.cgusDataStutus = cgusDataStutus;
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