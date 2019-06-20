package com.zhkj.nettyserver.message.domain.request;

/**
 * Des:系统消息参数
 * ClassName: SChatParams
 * Author: dengyi
 * Date: 2019-06-20 11:52
 */
public class SChatParams {

    /**
     * 消息接收者Uuid
     */
    private Long[] messEuseUuid;

    /**
     * 公司Uuid
     */
    private Long[] messEnteUuid;

    /**
     * 消息
     */
    private String messContent;

    public Long[] getMessEuseUuid() {
        return messEuseUuid;
    }

    public void setMessEuseUuid(Long[] messEuseUuid) {
        this.messEuseUuid = messEuseUuid;
    }

    public Long[] getMessEnteUuid() {
        return messEnteUuid;
    }

    public void setMessEnteUuid(Long[] messEnteUuid) {
        this.messEnteUuid = messEnteUuid;
    }

    public String getMessContent() {
        return messContent;
    }

    public void setMessContent(String messContent) {
        this.messContent = messContent;
    }
}
