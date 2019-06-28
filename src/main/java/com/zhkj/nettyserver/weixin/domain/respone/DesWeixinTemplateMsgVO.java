package com.zhkj.nettyserver.weixin.domain.respone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel(value = "DesWeixinTemplateMsgVO",description = "微信模板信息详情VO")
public class DesWeixinTemplateMsgVO {
    /**
     * 微信模板消息Uuid
     */
    @ApiModelProperty(value = "",notes = "微信模板消息Uuid",example = "")
    private Long wtmsUuid;

    /**
     * 公司微信Uuid
     */
    @ApiModelProperty(value = "",notes = "公司微信Uuid",example = "")
    private Long wtmsEwxiUuid;

    /**
     * 微信模板消息类型1：订单增加 2：订单修改 4：订单取消 8：订单发货 16:出库单出库
     */
    @ApiModelProperty(value = "",notes = "微信模板消息类型1：订单增加 2：订单修改 4：订单取消 8：订单发货 16:出库单出库",example = "")
    private Integer wtmsType;


    /**
     * 微信模板消息模板id
     */
    @ApiModelProperty(value = "",notes = "微信模板消息模板id",example = "")
    private String wtmsTemplateId;

    /**
     * 微信模板消息通知1~10人
     */
    @ApiModelProperty(value = "",notes = "微信模板消息通知<1~10人>",example = "")
    private List<String> wtmsToUser;

    public Long getWtmsUuid() {
        return wtmsUuid;
    }

    public void setWtmsUuid(Long wtmsUuid) {
        this.wtmsUuid = wtmsUuid;
    }

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