/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.message.domain.respone;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhkj.nettyserver.message.domain.ChatGroupUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * Des:
 * ClassName: OpenGroupVO
 * Author: biqiang2017@163.com
 * Date: 2018/11/8
 * Time: 0:56
 */
@ApiModel(value = "OpenGroupVO",description = "创建群VO")
public class OpenGroupVO {
    /**
     * 群Uuid
     */
    @ApiModelProperty(value = "",notes = "群Uuid",example ="" )
    private Long cgroUuid;

    /**
     * 群名字64
     */
    @ApiModelProperty(value = "",notes = "群名字64",example ="" )
    private String cgroName;

    /**
     * 群创建者Uuid
     */
    @ApiModelProperty(value = "",notes = "群创建者Uuid",example ="" )
    private Long cgroCsuseUuid;

    /**
     * 群是否公开 0：公开 1：不公开
     */
    @ApiModelProperty(value = "",notes = " 群是否公开 0：公开 1：不公开",example ="" )
    private Integer cgroPublic;

    /**
     * 群成员数最多256
     */
    @ApiModelProperty(value = "",notes = "群成员数最多256",example ="" )
    private Integer cgroCount;

    @ApiModelProperty(value = "",notes = "群成员数列表",example = "")
    List<ChatGroupUser> chatGroupUserList;

    public List<ChatGroupUser> getChatGroupUserList() {
        return chatGroupUserList;
    }

    public void setChatGroupUserList(List<ChatGroupUser> chatGroupUserList) {
        this.chatGroupUserList = chatGroupUserList;
    }

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "",notes = "创建时间yyyy-MM-dd HH:mm:ss",example ="" )
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getCgroUuid() {
        return cgroUuid;
    }

    public void setCgroUuid(Long cgroUuid) {
        this.cgroUuid = cgroUuid;
    }

    public String getCgroName() {
        return cgroName;
    }

    public void setCgroName(String cgroName) {
        this.cgroName = cgroName;
    }

    public Long getCgroCsuseUuid() {
        return cgroCsuseUuid;
    }

    public void setCgroCsuseUuid(Long cgroCsuseUuid) {
        this.cgroCsuseUuid = cgroCsuseUuid;
    }

    public Integer getCgroPublic() {
        return cgroPublic;
    }

    public void setCgroPublic(Integer cgroPublic) {
        this.cgroPublic = cgroPublic;
    }

    public Integer getCgroCount() {
        return cgroCount;
    }

    public void setCgroCount(Integer cgroCount) {
        this.cgroCount = cgroCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
