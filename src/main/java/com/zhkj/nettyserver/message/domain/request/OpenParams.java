/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.message.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * Des:
 * ClassName: OpenParams
 * Author: biqiang2017@163.com
 * Date: 2018/11/8
 * Time: 0:56
 */
@ApiModel(value = "OpenParams", description = "创建会话参数")
public class OpenParams {
    /**
     * 会话名字64位
     */
    @ApiModelProperty(value = "",notes = "会话名字64位",example = "")
    @Length(min = 1,max = 64,message = "会话名字<1~64位>")
    private String chatName;

    /**
     * 会话类型0：系统会话 1：一对一会话 2：多人会话
     */
    @ApiModelProperty(value = "",notes = " 会话类型0：系统会话 1：一对一会话 2：多人会话",example = "")
    @NotNull(message = "请指定会话类型")
    @Range(min = 0,max = 2,message = "会话类型<0~2>")
    private Integer chatType;

    /**
     * 群Uuid
     */
    @ApiModelProperty(value = "",notes = "群Uuid",example = "")
    private Long cgroUuid;

    /**
     * 发起会话方Uuid
     */
    @ApiModelProperty(value = "",notes = "发起会话方Uuid/t",example = "")
    @NotNull(message = "请指定发起会话方")
    private Long sSuseUuid;

    /**
     * 接受会话方Uuid
     */
    @ApiModelProperty(value = "",notes = "接受会话方Uuid",example = "")
    private Long eSuseUuid;

//    /**
//     * 消息开始时间
//     */
//    @ApiModelProperty(value = "",notes = "消息开始时间/t",example = "")
//    @NotNull(message = "请指定消息开始时间")
//    private Long messStartDate;
//
//    /**
//     * 消息结束时间
//     */
//    @ApiModelProperty(value = "",notes = "消息结束时间/t",example = "")
//    @NotNull(message = "请指定消息结束时间")
//    private Long messEndDate;

//    public Long getMessStartDate() {
//        return messStartDate;
//    }
//
//    public void setMessStartDate(Long messStartDate) {
//        this.messStartDate = messStartDate;
//    }
//
//    public Long getMessEndDate() {
//        return messEndDate;
//    }
//
//    public void setMessEndDate(Long messEndDate) {
//        this.messEndDate = messEndDate;
//    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public Integer getChatType() {
        return chatType;
    }

    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }

    public Long getCgroUuid() {
        return cgroUuid;
    }

    public void setCgroUuid(Long cgroUuid) {
        this.cgroUuid = cgroUuid;
    }

    public Long getsSuseUuid() {
        return sSuseUuid;
    }

    public void setsSuseUuid(Long sSuseUuid) {
        this.sSuseUuid = sSuseUuid;
    }

    public Long geteSuseUuid() {
        return eSuseUuid;
    }

    public void seteSuseUuid(Long eSuseUuid) {
        this.eSuseUuid = eSuseUuid;
    }

}
