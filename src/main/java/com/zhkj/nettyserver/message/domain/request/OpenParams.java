/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.message.domain.request;

/**
 * Des:
 * ClassName: OpenParams
 * Author: biqiang2017@163.com
 * Date: 2018/11/8
 * Time: 0:56
 */
public class OpenParams {
    /**
     * 会话名字64位
     */
    private String chatName;

    /**
     * 会话类型0：系统会话 1：一对一会话 2：多人会话
     */
    private Integer chatType;

    /**
     * 群Uuid
     */
    private Long cgroUuid;

    /**
     * 发起会话方Uuid
     */
    private Long sSuseUuid;

    /**
     * 接受会话方Uuid
     */
    private Long eSuseUuid;

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
