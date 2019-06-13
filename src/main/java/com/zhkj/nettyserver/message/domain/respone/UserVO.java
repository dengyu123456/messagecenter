package com.zhkj.nettyserver.message.domain.respone;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "UserVO",description = "用户VO")
public class UserVO {
    /**
     * 用户uuid
     */
    @ApiModelProperty(value = "",notes = "用户uuid",example = "")
    private Long userUuid;

    /**
     * 用户名字
     */
    @ApiModelProperty(value = "",notes = "用户名字",example = "")
    private String userName;

    /**
     * 是否是后端操作员 0：是 1：否
     */
    @ApiModelProperty(value = "",notes = "是否是后端操作员 0：是 1：否",example = "")
    private Integer userType;

    /**
     * 头像地址
     */
    @ApiModelProperty(value = "",notes = "头像地址",example = "")
    private String userHead;


    /**
     * 创建时间
     */
    @ApiModelProperty(value = "",notes = "创建时间yyyy-MM-dd HH:mm:ss",example = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "",notes = "更新时间yyyy-MM-dd HH:mm:ss",example = "")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    public Long getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(Long userUuid) {
        this.userUuid = userUuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}