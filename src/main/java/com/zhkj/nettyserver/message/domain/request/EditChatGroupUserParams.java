package com.zhkj.nettyserver.message.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(value = "EditChatGroupUserParams",description = "修改群名片参数")
public class EditChatGroupUserParams {
    /**
     * 群成员Uuid
     */
    @ApiModelProperty(value = "",notes = "群成员Uuid",example = "")
    @NotNull(message = "请指定群成员Uuid")
    private Long cgusUuid;

//    @ApiModelProperty(value = "",notes = "群参与用户Uuid",example = "")
//    private Long cgusSuseUuid;
//
//
    @ApiModelProperty(value = "",notes = "群Uuid",example = "")
    @NotNull(message = "请指定群的Uuid")
    private Long cgusCgroUuid;

    @ApiModelProperty(value = "",notes = "会话成员名片",example = "")
    @NotNull(message = "请指定会话成员名片")
    private String cgusName;

//    /**
//     * 数据状态0：正常 1：删除
//     */
//    @ApiModelProperty(value = "",notes = "数据状态0：正常 1：删除",example = "")
//    private Integer cgusDataStutus;

//    /**
//     * 会话成员加入时间
//     */
//    @ApiModelProperty(value = "",notes = "",example = "")
//    private Date createTime;

    public Long getCgusCgroUuid() {
        return cgusCgroUuid;
    }

    public void setCgusCgroUuid(Long cgusCgroUuid) {
        this.cgusCgroUuid = cgusCgroUuid;
    }

    public Long getCgusUuid() {
        return cgusUuid;
    }

    public void setCgusUuid(Long cgusUuid) {
        this.cgusUuid = cgusUuid;
    }

    public String getCgusName() {
        return cgusName;
    }

    public void setCgusName(String cgusName) {
        this.cgusName = cgusName;
    }
}
