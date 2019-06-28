package com.zhkj.nettyserver.weixin.domain;

import javax.persistence.*;

@Table(name = "weixin_template_msg")
public class WeixinTemplateMsg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 微信模板消息Uuid
     */
    @Column(name = "wtms_uuid")
    private Long wtmsUuid;

    /**
     * 公司微信Uuid
     */
    @Column(name = "wtms_ewxi_uuid")
    private Long wtmsEwxiUuid;

    /**
     * 微信模板消息类型1：订单增加 2：订单修改 4：订单取消 8：订单发货 16:出库单出库
     */
    @Column(name = "wtms_type")
    private Integer wtmsType;

    /**
     * 微信模板消息模板id
     */
    @Column(name = "wtms_template_id")
    private String wtmsTemplateId;

    /**
     * 微信模板消息通知最多10个人
     */
    @Column(name = "wtms_to_user")
    private String wtmsToUser;

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
     * 获取微信模板消息Uuid
     *
     * @return wtms_uuid - 微信模板消息Uuid
     */
    public Long getWtmsUuid() {
        return wtmsUuid;
    }

    /**
     * 设置微信模板消息Uuid
     *
     * @param wtmsUuid 微信模板消息Uuid
     */
    public void setWtmsUuid(Long wtmsUuid) {
        this.wtmsUuid = wtmsUuid;
    }

    /**
     * 获取公司微信Uuid
     *
     * @return wtms_ewxi_uuid - 公司微信Uuid
     */
    public Long getWtmsEwxiUuid() {
        return wtmsEwxiUuid;
    }

    /**
     * 设置公司微信Uuid
     *
     * @param wtmsEwxiUuid 公司微信Uuid
     */
    public void setWtmsEwxiUuid(Long wtmsEwxiUuid) {
        this.wtmsEwxiUuid = wtmsEwxiUuid;
    }

    public Integer getWtmsType() {
        return wtmsType;
    }

    public void setWtmsType(Integer wtmsType) {
        this.wtmsType = wtmsType;
    }

    /**
     * 获取微信模板消息模板id
     *
     * @return wtms_template_id - 微信模板消息模板id
     */
    public String getWtmsTemplateId() {
        return wtmsTemplateId;
    }

    /**
     * 设置微信模板消息模板id
     *
     * @param wtmsTemplateId 微信模板消息模板id
     */
    public void setWtmsTemplateId(String wtmsTemplateId) {
        this.wtmsTemplateId = wtmsTemplateId == null ? null : wtmsTemplateId.trim();
    }

    /**
     * 获取微信模板消息通知最多10个人
     *
     * @return wtms_to_user - 微信模板消息通知最多10个人
     */
    public String getWtmsToUser() {
        return wtmsToUser;
    }

    /**
     * 设置微信模板消息通知最多10个人
     *
     * @param wtmsToUser 微信模板消息通知最多10个人
     */
    public void setWtmsToUser(String wtmsToUser) {
        this.wtmsToUser = wtmsToUser == null ? null : wtmsToUser.trim();
    }
}