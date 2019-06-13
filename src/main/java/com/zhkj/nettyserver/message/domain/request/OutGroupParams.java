/*
 * *
 *  * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 *  * This software is the confidential and proprietary information of
 *  * ZHONGHENG, Inc. You shall not disclose such Confidential
 *  * Information and shall use it only in accordance with the terms of the
 *  * license agreement you entered into with ZHONGHENG.
 *
 */
package com.zhkj.nettyserver.message.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Des:
 * ClassName: OutGroupParams
 * Author: Administrator
 * Date: 2018/11/13
 * Time: 08:56
 */
@ApiModel(value = "OutGroupParams", description = "退出群参数")
public class OutGroupParams {

    @ApiModelProperty(value = "",notes = "退出群的用户Uuid/t",example = "")
    @NotNull(message = "请指定退出群的用户")
    private Long cgusSuseUuid;

    @ApiModelProperty(value = "",notes = "群号码Uuid/t",example = "")
    @NotNull(message = "请指定群号码")
    private Long cgusCgroUuid;

    public Long getCgusSuseUuid() {
        return cgusSuseUuid;
    }

    public void setCgusSuseUuid(Long cgusSuseUuid) {
        this.cgusSuseUuid = cgusSuseUuid;
    }

    public Long getCgusCgroUuid() {
        return cgusCgroUuid;
    }

    public void setCgusCgroUuid(Long cgusCgroUuid) {
        this.cgusCgroUuid = cgusCgroUuid;
    }
}
