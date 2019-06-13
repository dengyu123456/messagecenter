/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.message.domain.request;

/**
 * Des: 接受消息
 * ClassName: MessageParams
 * Author: biqiang2017@163.com
 * Date: 2018/11/8
 * Time: 0:52
 */
public class MessageParamsTest {
    /**
     * 消息发出者Uuid
     */
    private String messSuseUuid;

    /**
     * 会话Uuid
     */
    private String messChatUuid;

    /**
     * 消息类型0：普通消息
     */
    private Integer messContentType;

    /**
     * 消息
     */
    private String messContent;

    public String getMessSuseUuid() {
        return messSuseUuid;
    }

    public void setMessSuseUuid(String messSuseUuid) {
        this.messSuseUuid = messSuseUuid;
    }

    public String getMessChatUuid() {
        return messChatUuid;
    }

    public void setMessChatUuid(String messChatUuid) {
        this.messChatUuid = messChatUuid;
    }

    public Integer getMessContentType() {
        return messContentType;
    }

    public void setMessContentType(Integer messContentType) {
        this.messContentType = messContentType;
    }

    public String getMessContent() {
        return messContent;
    }

    public void setMessContent(String messContent) {
        this.messContent = messContent;
    }
}
