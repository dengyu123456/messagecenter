package com.zhkj.nettyserver.message.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(value = "AddChatGroupUserParams",description = "新增群成员参数")
public class AddChatGroupUserParams {
    @ApiModelProperty(value = "",notes = "群参与用户Uuid/t",example = "")
    @NotNull
    private Long cgusSuseUuid;

    @ApiModelProperty(value = "",notes = "群Uuid/t",example = "")
    @NotNull(message = "请指定群Uuid")
    private Long cgusCgroUuid;

    @ApiModelProperty(value = "",notes = "被拉人Uuid/t",example = "")
    @NotNull(message = "请指定被拉人Uuid")
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
