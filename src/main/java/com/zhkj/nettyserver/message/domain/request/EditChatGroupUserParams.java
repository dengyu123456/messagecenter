package com.zhkj.nettyserver.message.domain.request;

public class EditChatGroupUserParams {
    /**
     * 群成员Uuid
     */
    private Long cgusUuid;

    /**
     * 请指定群的Uuid
     */
    private Long cgusCgroUuid;

    /**
     * 请指定会话成员名片
     */
    private String cgusName;

    public Long getCgusCgroUuid() {
        return cgusCgroUuid;
    }

    public void setCgusCgroUuid(Long cgusCgroUuid) {
        this.cgusCgroUuid = cgusCgroUuid;
    }

    public Long getCgusUuid() {
        return cgusUuid;
    }

    public void setCgusUuid(Long cgusUuid) {
        this.cgusUuid = cgusUuid;
    }

    public String getCgusName() {
        return cgusName;
    }

    public void setCgusName(String cgusName) {
        this.cgusName = cgusName;
    }
}
