/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.message.domain.request;

import java.util.List;

/**
 * Des:
 * ClassName: OpenParams
 * Author: biqiang2017@163.com
 * Date: 2018/11/8
 * Time: 0:56
 */

public class OpenGroupParams {
    /**
     * 会话名字64位
     */
    private String chatName;

    /**
     * 发起会话方Uuid
     */
    private Long sSuseUuid;

    /**
     * 接受会话方Uuid
     */
    private List<Long> eSuseUuid;

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public Long getsSuseUuid() {
        return sSuseUuid;
    }

    public void setsSuseUuid(Long sSuseUuid) {
        this.sSuseUuid = sSuseUuid;
    }

    public List<Long> geteSuseUuid() {
        return eSuseUuid;
    }

    public void seteSuseUuid(List<Long> eSuseUuid) {
        this.eSuseUuid = eSuseUuid;
    }
}
