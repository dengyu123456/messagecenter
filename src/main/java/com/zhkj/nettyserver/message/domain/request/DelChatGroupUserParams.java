package com.zhkj.nettyserver.message.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(value = "DelChatGroupUserParams",description = "删除群成员参数")
public class DelChatGroupUserParams {
//    通过群号码和群成员UUID删除
    @ApiModelProperty(value = "",notes = "群号码/t",example = "")
    @NotNull(message = "请指定群号码Uuid/t")
    private Long cgroUuid;

    @ApiModelProperty(value = "",notes = "群成员Uuid/t",example = "")
    @NotNull(message = "请指定群成员Uuid")
    private Long cgusUuid[];

    public Long getCgroUuid() {
        return cgroUuid;
    }

    public void setCgroUuid(Long cgroUuid) {
        this.cgroUuid = cgroUuid;
    }

    public Long[] getCgusUuid() {
        return cgusUuid;
    }

    public void setCgusUuid(Long[] cgusUuid) {
        this.cgusUuid = cgusUuid;
    }
}
