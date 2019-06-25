/*
 *
 *   Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 *   This software is the confidential and proprietary information of
 *   ZHONGHENG, Inc. You shall not disclose such Confidential
 *   Information and shall use it only in accordance with the terms of the
 *   license agreement you entered into with ZHONGHENG.
 *
 */

package com.zhkj.nettyserver.message.domain.respone;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Des:
 * ClassName: OutGroupVo
 * Author: 1873130966@qq.com
 * Date: 2018/11/20
 * Time: 11:32
 */
public class OutGroupVo {

    /**
     * 用户Uuid
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long cgusSuseUuid;

    /**
     * 用户姓名
     */
    private String cgroSuseName;

    /**
     * 群号码Uuid
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long cgroUuid;

    /**
     * 群名字
     */
    private String cgroName;

    /**
     * 群会话Uuid
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long cgroChatUuid;

    public Long getCgroChatUuid() {
        return cgroChatUuid;
    }

    public void setCgroChatUuid(Long cgroChatUuid) {
        this.cgroChatUuid = cgroChatUuid;
    }

    public Long getCgusSuseUuid() {
        return cgusSuseUuid;
    }

    public void setCgusSuseUuid(Long cgusSuseUuid) {
        this.cgusSuseUuid = cgusSuseUuid;
    }

    public Long getCgroUuid() {
        return cgroUuid;
    }

    public void setCgroUuid(Long cgroUuid) {
        this.cgroUuid = cgroUuid;
    }

    public String getCgroSuseName() {
        return cgroSuseName;
    }

    public String getCgroName() {
        return cgroName;
    }

    public void setCgroName(String cgroName) {
        this.cgroName = cgroName;
    }

    public void setCgroSuseName(String cgroSuseName) {
        this.cgroSuseName = cgroSuseName;
    }
}
