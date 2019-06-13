package com.zhkj.nettyserver.message.domain;

import java.security.Principal;
import java.util.Date;

public class PublicUser implements Principal{
    private Long id;

    /**
     * 用户唯一id
     */
    private Long suseUuid;

    /**
     * 所属店铺
     */
    private Long suseMallUuid;

    /**
     * 所属部门
     */
    private Long suseDepaUuid;

    /**
     * 管理仓库uuid最多一个人只能管理10个仓库
     */
    private String suseAdminRepeUuid;

    /**
     * 用户名
     */
    private String suseUserName;

    /**
     * 正式姓名
     */
    private String suseRealName;

    /**
     * 昵称
     */
    private String suseNikeName;

    /**
     * 密码
     */
    private String susePassword;

    /**
     * 在线标识
     */
    private String suseOnlineTag;

    /**
     * 客户端盐
     */
    private String suseClientSalt;

    /**
     * 头像地址
     */
    private String suseHead;

    /**
     * 0:未知 1：男 2：女
     */
    private Integer suseSex;

    /**
     * 生日
     */
    private Date suseBirthday;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSuseUuid() {
        return suseUuid;
    }

    public void setSuseUuid(Long suseUuid) {
        this.suseUuid = suseUuid;
    }

    public Long getSuseMallUuid() {
        return suseMallUuid;
    }

    public void setSuseMallUuid(Long suseMallUuid) {
        this.suseMallUuid = suseMallUuid;
    }

    public Long getSuseDepaUuid() {
        return suseDepaUuid;
    }

    public void setSuseDepaUuid(Long suseDepaUuid) {
        this.suseDepaUuid = suseDepaUuid;
    }

    public String getSuseAdminRepeUuid() {
        return suseAdminRepeUuid;
    }

    public void setSuseAdminRepeUuid(String suseAdminRepeUuid) {
        this.suseAdminRepeUuid = suseAdminRepeUuid;
    }

    public String getSuseUserName() {
        return suseUserName;
    }

    public void setSuseUserName(String suseUserName) {
        this.suseUserName = suseUserName;
    }

    public String getSuseRealName() {
        return suseRealName;
    }

    public void setSuseRealName(String suseRealName) {
        this.suseRealName = suseRealName;
    }

    public String getSuseNikeName() {
        return suseNikeName;
    }

    public void setSuseNikeName(String suseNikeName) {
        this.suseNikeName = suseNikeName;
    }

    public String getSusePassword() {
        return susePassword;
    }

    public void setSusePassword(String susePassword) {
        this.susePassword = susePassword;
    }

    public String getSuseOnlineTag() {
        return suseOnlineTag;
    }

    public void setSuseOnlineTag(String suseOnlineTag) {
        this.suseOnlineTag = suseOnlineTag;
    }

    public String getSuseClientSalt() {
        return suseClientSalt;
    }

    public void setSuseClientSalt(String suseClientSalt) {
        this.suseClientSalt = suseClientSalt;
    }

    public String getSuseHead() {
        return suseHead;
    }

    public void setSuseHead(String suseHead) {
        this.suseHead = suseHead;
    }

    public Integer getSuseSex() {
        return suseSex;
    }

    public void setSuseSex(Integer suseSex) {
        this.suseSex = suseSex;
    }

    public Date getSuseBirthday() {
        return suseBirthday;
    }

    public void setSuseBirthday(Date suseBirthday) {
        this.suseBirthday = suseBirthday;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getName() {
        return String.valueOf(suseUuid);
    }
}