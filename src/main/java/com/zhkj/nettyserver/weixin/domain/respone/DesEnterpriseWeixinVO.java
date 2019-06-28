package com.zhkj.nettyserver.weixin.domain.respone;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "DesEnterpriseWeixinVO", description = "公司微信账号详情VO")
public class DesEnterpriseWeixinVO {
    /**
     * 公司微信账号Uuid
     */
    @ApiModelProperty(value = "", notes = "公司微信账号Uuid", example = "")
    private Long ewxiUuid;

    /**
     * 公司Uuid
     */
    @ApiModelProperty(value = "", notes = "公司Uuid", example = "")
    private Long ewxiEnteUuid;

    /**
     * 微信appid1~128位
     */
    @ApiModelProperty(value = "", notes = "微信appid1~128位", example = "")
    private String ewxiAppid;

    /**
     * 微信秘钥1~128位
     */
    @ApiModelProperty(value = "", notes = "微信秘钥1~128位", example = "")
    private String ewxiSecret;

    /**
     * 微信接入token
     */
    @ApiModelProperty(value = "", notes = "微信接入token", example = "")
    private String ewxiAccessToken;

    /**
     * 公司微信开启的通知功能1：订单增加 2：订单修改 3：订单取消 4：订单发货 5:出库单出库
     */
    @ApiModelProperty(value = "", notes = " 公司微信开启的通知功能1：订单增加 2：订单修改 3：订单取消 4：订单发货 5:出库单出库", example = "")
    private Integer ewxiWtmsAllow;

    public Long getEwxiUuid() {
        return ewxiUuid;
    }

    public void setEwxiUuid(Long ewxiUuid) {
        this.ewxiUuid = ewxiUuid;
    }

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
        this.ewxiAppid = ewxiAppid;
    }

    public String getEwxiSecret() {
        return ewxiSecret;
    }

    public void setEwxiSecret(String ewxiSecret) {
        this.ewxiSecret = ewxiSecret;
    }

    public String getEwxiAccessToken() {
        return ewxiAccessToken;
    }

    public void setEwxiAccessToken(String ewxiAccessToken) {
        this.ewxiAccessToken = ewxiAccessToken;
    }

    public Integer getEwxiWtmsAllow() {
        return ewxiWtmsAllow;
    }

    public void setEwxiWtmsAllow(Integer ewxiWtmsAllow) {
        this.ewxiWtmsAllow = ewxiWtmsAllow;
    }
}