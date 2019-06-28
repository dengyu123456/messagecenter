package com.zhkj.nettyserver.weixin.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@ApiModel(value = "EditWeixinTemplateMsgParams",description = "修改微信模板信息参数")
public class EditWeixinTemplateMsgParams {
    /**
     * 微信模板消息Uuid
     */
    @ApiModelProperty(value = "",notes = "微信模板消息Uuid",example = "")
    @NotNull(message = "请指定微信模板消息")
    private Long wtmsUuid;


    /**
     * 微信模板消息模板ID1~128位
     */
    @ApiModelProperty(value = "",notes = "微信模板消息模板ID1~128位",example = "")
    @Length(min = 1,max = 128,message = "微信模板消息模板ID<1~128位>")
    private String wtmsTemplateId;

    /**
     * 微信模板消息通知1~10人
     */
    @ApiModelProperty(value = "",notes = "微信模板消息通知1~10人",example = "")
    @Size(min = 1,max = 10,message = "微信模板消息通知<1~10人>")
    private List<String> wtmsToUser;

    public Long getWtmsUuid() {
        return wtmsUuid;
    }

    public void setWtmsUuid(Long wtmsUuid) {
        this.wtmsUuid = wtmsUuid;
    }

    public String getWtmsTemplateId() {
        return wtmsTemplateId;
    }

    public void setWtmsTemplateId(String wtmsTemplateId) {
        this.wtmsTemplateId = wtmsTemplateId;
    }

    public List<String> getWtmsToUser() {
        return wtmsToUser;
    }

    public void setWtmsToUser(List<String> wtmsToUser) {
        this.wtmsToUser = wtmsToUser;
    }
}