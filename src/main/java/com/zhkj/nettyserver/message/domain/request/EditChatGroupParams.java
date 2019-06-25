package com.zhkj.nettyserver.message.domain.request;

public class EditChatGroupParams {

    /**
     * 群Uuid
     */
    private Long cgroUuid;

    /**
     * 群名字
     */
    private String cgroName;

    /**
     * 群是否公开 0：公开 1：不公开
     */
    private Integer cgroPublic;

    /**
     * 数据是否正常0：正常1：删除
     */
    private Integer cgroDataStatus;

    /**
     * 群修改之前的名字
     */
    private String oldCgroName;

    public String getOldCgroName() {
        return oldCgroName;
    }

    public void setOldCgroName(String oldCgroName) {
        this.oldCgroName = oldCgroName;
    }

    public Long getCgroUuid() {
        return cgroUuid;
    }

    public void setCgroUuid(Long cgroUuid) {
        this.cgroUuid = cgroUuid;
    }

    public String getCgroName() {
        return cgroName;
    }

    public void setCgroName(String cgroName) {
        this.cgroName = cgroName;
    }

    public Integer getCgroPublic() {
        return cgroPublic;
    }

    public void setCgroPublic(Integer cgroPublic) {
        this.cgroPublic = cgroPublic;
    }

    public Integer getCgroDataStatus() {
        return cgroDataStatus;
    }

    public void setCgroDataStatus(Integer cgroDataStatus) {
        this.cgroDataStatus = cgroDataStatus;
    }
}
