package com.zhkj.nettyserver.weixin.domain.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@ApiModel(value = "EditEnterpriseWeixinParams", description = "修改公司微信账号")
public class EditEnterpriseWeixinParams {
    /**
     * 公司Uuid/t
     */
    @ApiModelProperty(value = "", notes = "公司Uuid/t", example = "")
    @NotNull(message = "请指定公司")
    private Long ewxiEnteUuid;

    /**
     * 微信appid1~128位
     */
    @ApiModelProperty(value = "", notes = "微信appid1~128位", example = "")
    @Length(min = 1, max = 128, message = "微信appid<1~128位>")
    private String ewxiAppid;

    /**
     * 微信秘钥1~128位
     */
    @ApiModelProperty(value = "", notes = "微信秘钥1~128位", example = "")
    @Length(min = 1, max = 128, message = "微信秘钥<1~128位>")
    private String ewxiSecret;

    /**
     * 公司微信开启的通知功能1：订单增加 2：订单修改 3：订单取消 4：订单发货 5:出库单出库
     */
    @ApiModelProperty(value = "", notes = "微信秘钥1：订单增加 2：订单修改 3：订单取消 4：订单发货 5:出库单出库", example = "")
    @Length(min = 1, max = 31, message = "微信开启的通知功能<1~31>")
    private Integer ewxiWtmsAllow;

    public Long getEwxiEnteUuid() {
        return ewxiEnteUuid;
    }

    public void setEwxiEnteUuid(Long ewxiEnteUuid) {
        this.ewxiEnteUuid = ewxiEnteUuid;
    }

    public String getEwxiAppid() {
        return ewxiAppid;
    }

    public void setEwxiAppid(String ewxiAppid) {
        this.ewxiAppid = ewxiAppid == null ? null : ewxiAppid.trim();
    }

    public String getEwxiSecret() {
        return ewxiSecret;
    }

    public void setEwxiSecret(String ewxiSecret) {
        this.ewxiSecret = ewxiSecret == null ? null : ewxiSecret.trim();
    }

    public Integer getEwxiWtmsAllow() {
        return ewxiWtmsAllow;
    }

    public void setEwxiWtmsAllow(Integer ewxiWtmsAllow) {
        this.ewxiWtmsAllow = ewxiWtmsAllow;
    }
}