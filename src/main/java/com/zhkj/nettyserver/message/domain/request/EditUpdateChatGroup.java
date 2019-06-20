package com.zhkj.nettyserver.message.domain.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * Des:编辑群成员
 * ClassName: EditUpdateChatGroup
 * Author: dengyi
 * Date: 2019-06-20 09:27
 */
public class EditUpdateChatGroup {
    @ApiModelProperty(value = "",notes = "群参与用户Uuid/t",example = "")
    @NotNull
    private Long cgusSuseUuid;

    @ApiModelProperty(value = "",notes = "群Uuid/t",example = "")
    @NotNull(message = "请指定群Uuid")
    private Long cgusCgroUuid;

    @ApiModelProperty(value = "",notes = "被拉人Uuid/t",example = "")
    @NotNull(message = "请指定编辑的群成员")
    private Long userUuid[];

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

    public Long[] getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(Long[] userUuid) {
        this.userUuid = userUuid;
    }
}
