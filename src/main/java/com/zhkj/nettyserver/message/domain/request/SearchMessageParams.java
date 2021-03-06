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
 * ClassName: SearchMessageParams
 * Author: biqiang2017@163.com
 * Date: 2018/11/11
 * Time: 14:24
 */
public class SearchMessageParams {
    /**
     * 会话Uuid
     */
    private Long messChatUuid;

    /**
     * 消息开始时间
     */
    private Long messStartDate;

    /**
     * 消息结束时间
     */
    private Long messEndDate;

    public Long getMessChatUuid() {
        return messChatUuid;
    }

    public void setMessChatUuid(Long messChatUuid) {
        this.messChatUuid = messChatUuid;
    }

    public Long getMessStartDate() {
        return messStartDate;
    }

    public void setMessStartDate(Long messStartDate) {
        this.messStartDate = messStartDate;
    }

    public Long getMessEndDate() {
        return messEndDate;
    }

    public void setMessEndDate(Long messEndDate) {
        this.messEndDate = messEndDate;
    }
}
