package com.zhkj.nettyserver.message.domain.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Des:解散群参数
 * ClassName: DissolutionGroupParams
 * Author: dengyi
 * Date: 2019-06-19 16:48
 */
public class DissolutionGroupParams {

    @ApiModelProperty(value = "", notes = "退出群的用户Uuid/t", example = "")
    @NotNull(message = "请指定退出群的用户")
    private Long cgusSuseUuid;

    @ApiModelProperty(value = "", notes = "群号码Uuid/t", example = "")
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
