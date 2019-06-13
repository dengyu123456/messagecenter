package com.zhkj.nettyserver.message.domain;

import javax.persistence.*;
import java.util.Date;

@Table(name = "chat_group")
public class ChatGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 群Uuid
     */
    @Column(name = "cgro_uuid")
    private Long cgroUuid;

    /**
     * 群会话Uuid
     */
    @Column(name = "cgro_chat_uuid")
    private Long cgroChatUuid;

    /**
     * 群名字64
     */
    @Column(name = "cgro_name")
    private String cgroName;

    /**
     * 群创建者Uuid
     */
    @Column(name = "cgro_csuse_uuid")
    private Long cgroCsuseUuid;

    /**
     * 群是否公开 0：公开 1：不公开
     */
    @Column(name = "cgro_public")
    private Integer cgroPublic;

    /**
     * 群类型0：系统会话 1：一对一会话 2：多人会话
     */
    @Column(name = "cgro_type")
    private Integer cgroType;

    /**
     * 群成员数最多256
     */
    @Column(name = "cgro_count")
    private Integer cgroCount;

    /**
     * 数据是否正常0：正常1：删除
     */
    @Column(name = "cgro_data_status")
    private Integer cgroDataStatus;

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
     * 获取群Uuid
     *
     * @return cgro_uuid - 群Uuid
     */
    public Long getCgroUuid() {
        return cgroUuid;
    }

    /**
     * 设置群Uuid
     *
     * @param cgroUuid 群Uuid
     */
    public void setCgroUuid(Long cgroUuid) {
        this.cgroUuid = cgroUuid;
    }

    /**
     * 获取群会话Uuid
     *
     * @return cgro_chat_uuid - 群会话Uuid
     */
    public Long getCgroChatUuid() {
        return cgroChatUuid;
    }

    /**
     * 设置群会话Uuid
     *
     * @param cgroChatUuid 群会话Uuid
     */
    public void setCgroChatUuid(Long cgroChatUuid) {
        this.cgroChatUuid = cgroChatUuid;
    }

    /**
     * 获取群名字64
     *
     * @return cgro_name - 群名字64
     */
    public String getCgroName() {
        return cgroName;
    }

    /**
     * 设置群名字64
     *
     * @param cgroName 群名字64
     */
    public void setCgroName(String cgroName) {
        this.cgroName = cgroName == null ? null : cgroName.trim();
    }

    /**
     * 获取群创建者Uuid
     *
     * @return cgro_csuse_uuid - 群创建者Uuid
     */
    public Long getCgroCsuseUuid() {
        return cgroCsuseUuid;
    }

    /**
     * 设置群创建者Uuid
     *
     * @param cgroCsuseUuid 群创建者Uuid
     */
    public void setCgroCsuseUuid(Long cgroCsuseUuid) {
        this.cgroCsuseUuid = cgroCsuseUuid;
    }

    /**
     * 获取群是否公开 0：公开 1：不公开
     *
     * @return cgro_public - 群是否公开 0：公开 1：不公开
     */
    public Integer getCgroPublic() {
        return cgroPublic;
    }

    /**
     * 设置群是否公开 0：公开 1：不公开
     *
     * @param cgroPublic 群是否公开 0：公开 1：不公开
     */
    public void setCgroPublic(Integer cgroPublic) {
        this.cgroPublic = cgroPublic;
    }

    /**
     * 获取群类型0：系统会话 1：一对一会话 2：多人会话
     *
     * @return cgro_type - 群类型0：系统会话 1：一对一会话 2：多人会话
     */
    public Integer getCgroType() {
        return cgroType;
    }

    /**
     * 设置群类型0：系统会话 1：一对一会话 2：多人会话
     *
     * @param cgroType 群类型0：系统会话 1：一对一会话 2：多人会话
     */
    public void setCgroType(Integer cgroType) {
        this.cgroType = cgroType;
    }

    /**
     * 获取群成员数最多256
     *
     * @return cgro_count - 群成员数最多256
     */
    public Integer getCgroCount() {
        return cgroCount;
    }

    /**
     * 设置群成员数最多256
     *
     * @param cgroCount 群成员数最多256
     */
    public void setCgroCount(Integer cgroCount) {
        this.cgroCount = cgroCount;
    }

    /**
     * 获取数据是否正常0：正常1：删除
     *
     * @return cgro_data_status - 数据是否正常0：正常1：删除
     */
    public Integer getCgroDataStatus() {
        return cgroDataStatus;
    }

    /**
     * 设置数据是否正常0：正常1：删除
     *
     * @param cgroDataStatus 数据是否正常0：正常1：删除
     */
    public void setCgroDataStatus(Integer cgroDataStatus) {
        this.cgroDataStatus = cgroDataStatus;
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