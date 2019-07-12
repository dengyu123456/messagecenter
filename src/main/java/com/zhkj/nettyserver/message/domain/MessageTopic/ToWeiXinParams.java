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
//
//    private Long enteUuid;
//
//    private Integer wtmsType;
//
//    private Map<String, SendTemplateMsgParams.Data> data;
//
//    public Long getEnteUuid() {
//        return enteUuid;
//    }
//
//    public void setEnteUuid(Long enteUuid) {
//        this.enteUuid = enteUuid;
//    }
//
//    public Integer getWtmsType() {
//        return wtmsType;
//    }
//
//    public void setWtmsType(Integer wtmsType) {
//        this.wtmsType = wtmsType;
//    }
//
//    public Map<String, SendTemplateMsgParams.Data> getData() {
//        return data;
//    }
//
//    public void setData(Map<String, SendTemplateMsgParams.Data> data) {
//        this.data = data;
//    }

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
    private SendTemplateMsgParams.Miniprogram miniprogram;

    /**
     * 数据
     */
    private Map<String, SendTemplateMsgParams.Data> data;

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

    public SendTemplateMsgParams.Miniprogram getMiniprogram() {
        return miniprogram;
    }

    public void setMiniprogram(SendTemplateMsgParams.Miniprogram miniprogram) {
        this.miniprogram = miniprogram;
    }

    public Map<String, SendTemplateMsgParams.Data> getData() {
        return data;
    }

    public void setData(Map<String, SendTemplateMsgParams.Data> data) {
        this.data = data;
    }

}
