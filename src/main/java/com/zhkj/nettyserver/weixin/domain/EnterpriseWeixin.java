package com.zhkj.nettyserver.weixin.domain;

import javax.persistence.*;

@Table(name = "enterprise_weixin")
public class EnterpriseWeixin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 公司微信Uuid
     */
    @Column(name = "ewxi_uuid")
    private Long ewxiUuid;

    /**
     * 公司Uuid
     */
    @Column(name = "ewxi_ente_uuid")
    private Long ewxiEnteUuid;

    /**
     * 微信appid
     */
    @Column(name = "ewxi_appid")
    private String ewxiAppid;

    /**
     * 微信秘钥
     */
    @Column(name = "ewxi_secret")
    private String ewxiSecret;

    /**
     * 微信接入token
     */
    @Column(name = "ewxi_access_token")
    private String ewxiAccessToken;

    /**
     * 公司微信开启的通知功能1：订单增加 2：订单修改 3：订单取消 4：订单发货 5:出库单出库
     */
    @Column(name = "ewxi_wtms_allow")
    private Integer ewxiWtmsAllow;

    /**
     * token最后更新时间
     */
    @Column(name = "ewxi_last_time")
    private Long ewxiLastTime;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取公司微信Uuid
     *
     * @return ewxi_uuid - 公司微信Uuid
     */
    public Long getEwxiUuid() {
        return ewxiUuid;
    }

    /**
     * 设置公司微信Uuid
     *
     * @param ewxiUuid 公司微信Uuid
     */
    public void setEwxiUuid(Long ewxiUuid) {
        this.ewxiUuid = ewxiUuid;
    }

    /**
     * 获取公司Uuid
     *
     * @return ewxi_ente_uuid - 公司Uuid
     */
    public Long getEwxiEnteUuid() {
        return ewxiEnteUuid;
    }

    /**
     * 设置公司Uuid
     *
     * @param ewxiEnteUuid 公司Uuid
     */
    public void setEwxiEnteUuid(Long ewxiEnteUuid) {
        this.ewxiEnteUuid = ewxiEnteUuid;
    }

    /**
     * 获取微信appid
     *
     * @return ewxi_appid - 微信appid
     */
    public String getEwxiAppid() {
        return ewxiAppid;
    }

    /**
     * 设置微信appid
     *
     * @param ewxiAppid 微信appid
     */
    public void setEwxiAppid(String ewxiAppid) {
        this.ewxiAppid = ewxiAppid == null ? null : ewxiAppid.trim();
    }

    /**
     * 获取微信秘钥
     *
     * @return ewxi_secret - 微信秘钥
     */
    public String getEwxiSecret() {
        return ewxiSecret;
    }

    /**
     * 设置微信秘钥
     *
     * @param ewxiSecret 微信秘钥
     */
    public void setEwxiSecret(String ewxiSecret) {
        this.ewxiSecret = ewxiSecret == null ? null : ewxiSecret.trim();
    }

    /**
     * 获取微信接入token
     *
     * @return ewxi_access_token - 微信接入token
     */
    public String getEwxiAccessToken() {
        return ewxiAccessToken;
    }

    /**
     * 设置微信接入token
     *
     * @param ewxiAccessToken 微信接入token
     */
    public void setEwxiAccessToken(String ewxiAccessToken) {
        this.ewxiAccessToken = ewxiAccessToken == null ? null : ewxiAccessToken.trim();
    }

    public Integer getEwxiWtmsAllow() {
        return ewxiWtmsAllow;
    }

    public void setEwxiWtmsAllow(Integer ewxiWtmsAllow) {
        this.ewxiWtmsAllow = ewxiWtmsAllow;
    }

    /**
     * @return ewxi_last_time
     */
    public Long getEwxiLastTime() {
        return ewxiLastTime;
    }

    /**
     * @param ewxiLastTime
     */
    public void setEwxiLastTime(Long ewxiLastTime) {
        this.ewxiLastTime = ewxiLastTime;
    }
}