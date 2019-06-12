/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.common.base.respone;

/**
 * Des: 返回值对象
 * ClassName: Response
 * Author: biqiang2017@163.com
 * Date: 2018/7/4
 * Time: 14:54
 */
public class Response {
    public static final String OK = "200";
    public static final String BADREQUEST = "400";//错误的请求
    public static final String UNAUTHORIZED = "401";//未授权 无效token
    public static final String TOKENOUT = "402";//token过期
    //返回代码
    private String code;
    //返回错误信息
    private String error;
    //返回参数
    private Object params;
    /*分页相关*/
    //分页大小
    private Integer pageSize;
    //页码 1开始
    private Integer page;
    //数据总数
    private Long total;

    /**
     * 构造函数
     * @param code
     * @param error
     * @param params
     * @param pageSize
     * @param page
     * @param total
     */
    public Response(String code, String error, Object params, Integer pageSize, Integer page, Long total) {
        this.code = code;
        this.error = error;
        this.params = params;
        this.pageSize = pageSize;
        this.page = page;
        this.total = total;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
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

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
