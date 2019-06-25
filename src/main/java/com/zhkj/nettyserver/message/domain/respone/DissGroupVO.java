package com.zhkj.nettyserver.message.domain.respone;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;

/**
 * Des:解散群VO
 * ClassName: DissGroupVO
 * Author: dengyi
 * Date: 2019-06-25 11:27
 */
public class DissGroupVO {

    /**
     * 群Uuid
     */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long cgroUuid;

    /**
     * 群名字
     */
    private String cgroName;

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long cgroChatUuid;

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

    public Long getCgroChatUuid() {
        return cgroChatUuid;
    }

    public void setCgroChatUuid(Long cgroChatUuid) {
        this.cgroChatUuid = cgroChatUuid;
    }
}
