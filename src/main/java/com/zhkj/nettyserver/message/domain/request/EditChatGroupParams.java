package com.zhkj.nettyserver.message.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(value = "EditChatGroupParams",description = "修改群信息参数")
public class EditChatGroupParams {

    @ApiModelProperty(value = "",notes = "群Uuid/t",example = "")
    @NotNull
    private Long cgroUuid;

    @ApiModelProperty(value = "",notes = "群名字64",example = "")
    private String cgroName;

    @ApiModelProperty(value = "",notes = "群是否公开 0：公开 1：不公开",example = "")
    private Integer cgroPublic;

    @ApiModelProperty(value = "",notes = "数据是否正常0：正常1：删除",example = "")
    private Integer cgroDataStatus;

    //修改之前的名字
    private String oldCgroName;

    public String getOldCgroName() {
        return oldCgroName;
    }

    public void setOldCgroName(String oldCgroName) {
        this.oldCgroName = oldCgroName;
    }

    public Long getCgroUuid() {
        return cgroUuid;
    }

    public void setCgroUuid(Long cgroUuid) {
        this.cgroUuid = cgroUuid;
    }

    public String getCgroName() {
        return cgroName;
    }

    public void setCgroName(String cgroName) {
        this.cgroName = cgroName;
    }

    public Integer getCgroPublic() {
        return cgroPublic;
    }

    public void setCgroPublic(Integer cgroPublic) {
        this.cgroPublic = cgroPublic;
    }

    public Integer getCgroDataStatus() {
        return cgroDataStatus;
    }

    public void setCgroDataStatus(Integer cgroDataStatus) {
        this.cgroDataStatus = cgroDataStatus;
    }
}
