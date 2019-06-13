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
 * ClassName: CloseParams
 * Author: biqiang2017@163.com
 * Date: 2018/11/8
 * Time: 0:56
 */
public class CloseParams {
    private Long suseUuid;

    public Long getSuseUuid() {
        return suseUuid;
    }

    public void setSuseUuid(Long suseUuid) {
        this.suseUuid = suseUuid;
    }
}
