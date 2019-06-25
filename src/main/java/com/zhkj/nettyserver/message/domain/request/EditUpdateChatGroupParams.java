package com.zhkj.nettyserver.message.domain.request;

/**
 * Des:编辑群成员
 * ClassName: EditUpdateChatGroupParams
 * Author: dengyi
 * Date: 2019-06-20 09:27
 */
public class EditUpdateChatGroupParams {
    /**
     * 群参与用户Uuid
     */
    private Long cgusSuseUuid;

    /**
     * 指定群Uuid
     */
    private Long cgusCgroUuid;

    /**
     * 请指定编辑的群成员
     */
    private Long userUuid[];

    public Long getCgusSuseUuid() {
        return cgusSuseUuid;
    }

    public void setCgusSuseUuid(Long cgusSuseUuid) {
        this.cgusSuseUuid = cgusSuseUuid;
    }

    public Long getCgusCgroUuid() {
        return cgusCgroUuid;
    }

    public void setCgusCgroUuid(Long cgusCgroUuid) {
        this.cgusCgroUuid = cgusCgroUuid;
    }

    public Long[] getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(Long[] userUuid) {
        this.userUuid = userUuid;
    }
}
