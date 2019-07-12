package com.zhkj.nettyserver.message.domain.MessageTopic;

/**
 * Des:发送到webscoket参数
 * ClassName: ToWebScoketParams
 * Author: dengyi
 * Date: 2019-06-28 15:50
 */
public class ToWebScoketParams {
    //返回代码
    private String code = "200";

    //返回错误信息
    private String error;

    //操作方法action
    private String action;

    //返回参数
    private Object params;

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
