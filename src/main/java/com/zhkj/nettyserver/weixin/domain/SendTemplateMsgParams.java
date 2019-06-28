/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.weixin.domain;

import java.util.Map;

/**
 * Des:
 * ClassName: SendTemplateMsgParams
 * Author: biqiang2017@163.com
 * Date: 2019/6/21
 * Time: 11:15
 */
public class SendTemplateMsgParams {
    /**
     * 接收用户
     */
    private String touser;

    /**
     * 模板Uuid
     */
    private String template_id;

    /**
     * 跳转连接
     */
    private String url;

    /**
     * 跳小程序所需数据，不需跳小程序可不用传该数据
     */
    private Miniprogram miniprogram;

    /**
     * 数据
     */
    private Map<String,Data> data;

    /**
     * 跳小程序所需数据
     */
    public static class Miniprogram{
        /**
         * 所需跳转到的小程序appid
         */
        private String appid;

        /**
         * 所需跳转到小程序的具体页面路径
         */
        private String pagepath;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getPagepath() {
            return pagepath;
        }

        public void setPagepath(String pagepath) {
            this.pagepath = pagepath;
        }
    }

    /**
     * 模板数据
     */
    public static class Data{
        /**
         * 模板内容
         */
        private String value;

        /**
         * 模板内容字体颜色
         */
        private String color;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Miniprogram getMiniprogram() {
        return miniprogram;
    }

    public void setMiniprogram(Miniprogram miniprogram) {
        this.miniprogram = miniprogram;
    }

    public Map<String, Data> getData() {
        return data;
    }

    public void setData(Map<String, Data> data) {
        this.data = data;
    }
}
