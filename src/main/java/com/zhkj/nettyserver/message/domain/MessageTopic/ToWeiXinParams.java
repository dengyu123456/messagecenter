package com.zhkj.nettyserver.message.domain.MessageTopic;

import com.zhkj.nettyserver.weixin.domain.SendTemplateMsgParams;

import java.util.Map;

/**
 * Des:发送到微信端参数
 * ClassName: ToWeiXinParams
 * Author: dengyi
 * Date: 2019-06-28 16:14
 */
public class ToWeiXinParams {

    private Long enteUuid;

    private Integer wtmsType;

    private Map<String, SendTemplateMsgParams.Data> data;

    public Long getEnteUuid() {
        return enteUuid;
    }

    public void setEnteUuid(Long enteUuid) {
        this.enteUuid = enteUuid;
    }

    public Integer getWtmsType() {
        return wtmsType;
    }

    public void setWtmsType(Integer wtmsType) {
        this.wtmsType = wtmsType;
    }

    public Map<String, SendTemplateMsgParams.Data> getData() {
        return data;
    }

    public void setData(Map<String, SendTemplateMsgParams.Data> data) {
        this.data = data;
    }
}
