/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.purchase.base.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Des: 请求参数的统一对象
 * ClassName: Request
 * Author: biqiang2017@163.com
 * Date: 2018/7/4
 * Time: 15:28
 */
public class Request<T> {
    //请求参数
    @Valid
    @NotNull(message = "参数不能为空")
    private T params;
    //版本号
    private String version;
    //请求时间
    private Long time;
    //加盐校验码
    private String check;
    /*关于分页*/
    //分页大小
    private Integer pageSize;
    //页码
    private Integer page;

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
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

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
