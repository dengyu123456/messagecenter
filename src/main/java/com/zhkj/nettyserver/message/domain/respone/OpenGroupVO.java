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
public class OpenGroupVO {
    /**
     * 群Uuid
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long cgroUuid;

    /**
     * 群名字64
     */
    private String cgroName;

    /**
     * 群创建者Uuid
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long cgroCsuseUuid;

    /**
     * 群创建者名字
     */
    private String cgroCsuseName;

    /**
     * 群是否公开 0：公开 1：不公开
     */
    private Integer cgroPublic;

    /**
     * 群成员数最多256
     */
    private Integer cgroCount;

    /**
     * 群成员列表
     */
    List<ChatGroupUser> chatGroupUserList;

    /**
     * 创建时间
     */
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

    public String getCgroCsuseName() {
        return cgroCsuseName;
    }

    public void setCgroCsuseName(String cgroCsuseName) {
        this.cgroCsuseName = cgroCsuseName;
    }

    public List<ChatGroupUser> getChatGroupUserList() {
        return chatGroupUserList;
    }

    public void setChatGroupUserList(List<ChatGroupUser> chatGroupUserList) {
        this.chatGroupUserList = chatGroupUserList;
    }

}
