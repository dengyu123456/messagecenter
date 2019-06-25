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
public class OutGroupParams {

    /**
     * 退出群的用户Uuid
     */
    private Long cgusSuseUuid;

    /**
     * 群号码Uuid
     */
    private Long cgusCgroUuid;

    /**
     * 新群主 如果是群主退群，指定一个新的群主 不指定默认按照加群顺序指定群主
     */
    private Long newGroupOwnerUuid;

    public Long getNewGroupOwnerUuid() {
        return newGroupOwnerUuid;
    }

    public void setNewGroupOwnerUuid(Long newGroupOwnerUuid) {
        this.newGroupOwnerUuid = newGroupOwnerUuid;
    }

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
