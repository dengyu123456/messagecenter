package com.zhkj.nettyserver.message.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户uuid
     */
    @Column(name = "user_uuid")
    private Long userUuid;

    @Column(name = "user_name")
    private String userName;

    /**
     * 是否是后端操作员 0：是 1：否
     */
    @Column(name = "user_type")
    private Integer userType;

    /**
     * 头像地址
     */
    @Column(name = "user_head")
    private String userHead;

    /**
     * 公司/企业Uuid
     */
    @Column(name = "user_ente_uuid")
    private Long userEnteUuid;

    /**
     * 数据状态0：正常 1：禁用
     */
    @Column(name = "user_status")
    private Integer userStatus;

    /**
     * 数据状态0：正常 1：删除
     */
    @Column(name = "user_data_status")
    private Integer userDataStatus;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

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
     * 获取用户uuid
     *
     * @return user_uuid - 用户uuid
     */
    public Long getUserUuid() {
        return userUuid;
    }

    /**
     * 设置用户uuid
     *
     * @param userUuid 用户uuid
     */
    public void setUserUuid(Long userUuid) {
        this.userUuid = userUuid;
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取是否是后端操作员 0：是 1：否
     *
     * @return user_type - 是否是后端操作员 0：是 1：否
     */
    public Integer getUserType() {
        return userType;
    }

    /**
     * 设置是否是后端操作员 0：是 1：否
     *
     * @param userType 是否是后端操作员 0：是 1：否
     */
    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    /**
     * 获取头像地址
     *
     * @return user_head - 头像地址
     */
    public String getUserHead() {
        return userHead;
    }

    /**
     * 设置头像地址
     *
     * @param userHead 头像地址
     */
    public void setUserHead(String userHead) {
        this.userHead = userHead == null ? null : userHead.trim();
    }

    /**
     * 获取公司/企业Uuid
     *
     * @return user_ente_uuid - 公司/企业Uuid
     */
    public Long getUserEnteUuid() {
        return userEnteUuid;
    }

    /**
     * 设置公司/企业Uuid
     *
     * @param userEnteUuid 公司/企业Uuid
     */
    public void setUserEnteUuid(Long userEnteUuid) {
        this.userEnteUuid = userEnteUuid;
    }

    /**
     * 获取数据状态0：正常 1：禁用
     *
     * @return user_status - 数据状态0：正常 1：禁用
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     * 设置数据状态0：正常 1：禁用
     *
     * @param userStatus 数据状态0：正常 1：禁用
     */
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * 获取数据状态0：正常 1：删除
     *
     * @return user_data_status - 数据状态0：正常 1：删除
     */
    public Integer getUserDataStatus() {
        return userDataStatus;
    }

    /**
     * 设置数据状态0：正常 1：删除
     *
     * @param userDataStatus 数据状态0：正常 1：删除
     */
    public void setUserDataStatus(Integer userDataStatus) {
        this.userDataStatus = userDataStatus;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}