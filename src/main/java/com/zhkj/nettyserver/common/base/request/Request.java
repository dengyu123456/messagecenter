/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.common.base.request;

/**
 * Des: 请求参数的统一对象
 * ClassName: Request
 * Author: biqiang2017@163.com
 * Date: 2018/7/4
 * Time: 15:28
 */
public class Request {
    //请求参数
    private String params;
    //版本号
    private String version;
    //请求时间
    private Long time;
    //请求方法
    private String action;

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
