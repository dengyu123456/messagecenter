package com.zhkj.nettyserver.message.domain.request;

public class AddChatGroupUserParams {

    /**
     * 群参与用户Uuid
     */
    private Long cgusSuseUuid;

    /**
     * 群Uuid
     */
    private Long cgusCgroUuid;

    /**
     * 被拉人Uuid列表
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
