package com.zhkj.nettyserver.message.domain.request;

public class DelChatGroupUserParams {
    /**
     * 通过群号码和群成员UUID删除
     */
    private Long cgroUuid;

    /**
     * 群成员Uuid数组
     */
    private Long cgusUuid[];

    public Long getCgroUuid() {
        return cgroUuid;
    }

    public void setCgroUuid(Long cgroUuid) {
        this.cgroUuid = cgroUuid;
    }

    public Long[] getCgusUuid() {
        return cgusUuid;
    }

    public void setCgusUuid(Long[] cgusUuid) {
        this.cgusUuid = cgusUuid;
    }
}
