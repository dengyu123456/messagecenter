package com.zhkj.nettyserver.weixin.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@ApiModel(value = "AddWeixinTemplateMsgParams",description = "增加微信模板信息参数")
public class AddWeixinTemplateMsgParams {
    /**
     * 公司微信Uuid/t
     */
    @ApiModelProperty(value = "",notes = "公司微信Uuid/t",example = "")
    @NotNull(message = "请指定公司微信")
    private Long wtmsEwxiUuid;

    /**
     * 微信模板消息类型1：订单增加 2：订单修改 4：订单取消 8：订单发货 16:出库单出库/t
     */
    @ApiModelProperty(value = "",notes = "微信模板消息类型1：订单增加 2：订单修改 4：订单取消 8：订单发货 16:出库单出库/t",example = "")
    @NotNull(message = "请指定微信模板消息类型")
    @Range(min = 1,max = 16,message = "微信模板消息类型<1~16>")
    private Integer wtmsType;


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

    public Long getWtmsEwxiUuid() {
        return wtmsEwxiUuid;
    }

    public void setWtmsEwxiUuid(Long wtmsEwxiUuid) {
        this.wtmsEwxiUuid = wtmsEwxiUuid;
    }

    public Integer getWtmsType() {
        return wtmsType;
    }

    public void setWtmsType(Integer wtmsType) {
        this.wtmsType = wtmsType;
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