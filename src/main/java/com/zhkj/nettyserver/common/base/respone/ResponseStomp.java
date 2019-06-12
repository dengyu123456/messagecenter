/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.common.base.respone;

/**
 * Des:
 * ClassName: ResponseStomp
 * Author: biqiang2017@163.com
 * Date: 2018/11/11
 * Time: 19:22
 */
public class ResponseStomp {
    public static final String OK = "200";
    public static final String ERROR = "400";//错误的请求
    //返回代码
    private String code;
    //返回错误信息
    private String error;
    //操作方法action
    private String action;
    //返回参数
    private Object params;

    /**
     * 构造函数
     * @param code
     * @param error
     * @param action
     * @param params
     */
    public ResponseStomp(String code, String error,String action, Object params) {
        this.code = code;
        this.error = error;
        this.action = action;
        this.params = params;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }
}
