/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.weixin;

import com.alibaba.fastjson.JSONObject;
import com.zhkj.nettyserver.common.util.HttpUtils;
import com.zhkj.nettyserver.common.util.StringUtil;
import com.zhkj.nettyserver.weixin.domain.EnterpriseWeixin;
import com.zhkj.nettyserver.weixin.domain.SendTemplateMsgParams;
import com.zhkj.nettyserver.weixin.domain.WeixinTemplateMsg;
import com.zhkj.nettyserver.weixin.service.WeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Des:
 * ClassName: WeiXin
 * Author: biqiang2017@163.com
 * Date: 2019/6/21
 * Time: 11:13
 */
@Component
public class WeiXin {
    /**
     * 发送模板消息Url
     */
    public static final String TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";

    /**
     * 获取token Url
     */
    public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&";
    @Autowired
    private WeixinService weixinService;

    /**
     * 发送模板消息
     * @param token
     * @param params
     */
    public void sendTemplate(String token, SendTemplateMsgParams params){
        HttpUtils.getInstance().post(TEMPLATE_URL+token,params);
    }

    /**
     * 发送模板消息
     * @param enteUuid
     * @param wtmsType
     * @param data
     */
    public void sendTemplate(Long enteUuid, Integer wtmsType, Map<String,SendTemplateMsgParams.Data> data){
        EnterpriseWeixin ewei = this.weixinService.selectOneByEnteUuid(enteUuid);
        if (ewei != null && StringUtil.isNotBlank(ewei.getEwxiAccessToken()) && ((wtmsType&ewei.getEwxiWtmsAllow())>0)){
            WeixinTemplateMsg rWtmsP = new WeixinTemplateMsg();
            rWtmsP.setWtmsEwxiUuid(ewei.getEwxiUuid());
            rWtmsP.setWtmsType(wtmsType);
            WeixinTemplateMsg wtms = this.weixinService.selectTmsgOne(rWtmsP);
            if(wtms != null){
                SendTemplateMsgParams params = new SendTemplateMsgParams();
                params.setTemplate_id(wtms.getWtmsTemplateId());
                String[] toUser = wtms.getWtmsToUser().split("#");
                for (String item : toUser){
                    params.setTouser(item);
                    params.setData(data);
                    this.sendTemplate(ewei.getEwxiAccessToken(),params);
                }
            }
        }
    }

    /**
     * 获取token
     * @param appid
     * @param secret
     * @return
     */
    public String getAccessToken(String appid,String secret){
        Map<String,String> params = new HashMap<String,String>();
        params.put("appid",appid);
        params.put("secret",secret);
        String result = HttpUtils.getInstance().get(ACCESS_TOKEN_URL,params);
        System.out.println(result);
        JSONObject jobj = JSONObject.parseObject(result);
        if (jobj.containsKey("access_token")){
            return jobj.getString("access_token");
        }else{
            return null;
        }
    }

//    public static void main(String[] args) {
//        WeiXin test = new WeiXin();
//        SendTemplateMsgParams params = new SendTemplateMsgParams();
//        params.setTouser("OPENID");
//        params.setTemplate_id("ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY");
//        params.setUrl("http://weixin.qq.com/download");
//        Map<String,SendTemplateMsgParams.Data> dataMap = new HashMap<String,SendTemplateMsgParams.Data>();
//        SendTemplateMsgParams.Data data0 = new SendTemplateMsgParams.Data();
//        data0.setValue("恭喜你购买成功！");
//        data0.setColor("#173177");
//        dataMap.put("first",data0);
//        SendTemplateMsgParams.Data data1 = new SendTemplateMsgParams.Data();
//        data1.setValue("巧克力");
//        data1.setColor("#173177");
//        dataMap.put("keyword1",data1);
//        SendTemplateMsgParams.Data data2 = new SendTemplateMsgParams.Data();
//        data2.setValue("39.8元");
//        data2.setColor("#173177");
//        dataMap.put("keyword2",data2);
//        SendTemplateMsgParams.Data data3 = new SendTemplateMsgParams.Data();
//        data3.setValue("2014年9月22日");
//        data3.setColor("#173177");
//        dataMap.put("keyword3",data3);
//        SendTemplateMsgParams.Data data4 = new SendTemplateMsgParams.Data();
//        data4.setValue("欢迎再次购买！");
//        data4.setColor("#173177");
//        dataMap.put("remark",data4);
//        params.setData(dataMap);
//
//        test.sendTemplate("ACCESS_TOKEN",params);
//        String token= test.getAccessToken("wxe690bf28d73c2d13","22d199e4c160c33b0eda1839a80fa78f");
//        System.out.println(token);
//
//
//    }

    public static void main(String[] args) {
        WeiXin weiXin = new WeiXin();
//        String appid = "wxe690bf28d73c2d13";
//        String se = "22d199e4c160c33b0eda1839a80fa78f";
//        String token = weiXin.getAccessToken(appid,se);
//        System.out.println(token);
        String token = "22_o08aCR8kbWH4BY96TEnI5Qaa4o8onckWb55" +
                "-y_3aKuJ2nRSQiRM_g1kbY1ZnvonUG96ROe9jOjk4xH5Isi7jzd77ms6QdWVsQxsLwmmr5LVC2DffaILI0KS3LYguV47SPaatiTqzGn-BudzCEYHcABANHQ";
        String userId = "obAhA1W7GL8cOIm5rYE4u9mGftDI";
        String huserId = "obAhA1R19GCpjOfhpwNGVgccNRO0";
        SendTemplateMsgParams params = new SendTemplateMsgParams();
        params.setTouser(huserId);
        params.setTemplate_id("YUhinKL3i7APTestfDqfUaz4sWVOQBAEkEKJTSsi4E4");
//        params.setUrl("http://weixin.qq.com/download");
        Map<String,SendTemplateMsgParams.Data> dataMap = new HashMap<String,SendTemplateMsgParams.Data>();
        SendTemplateMsgParams.Data data0 = new SendTemplateMsgParams.Data();
        data0.setValue("测试");
        data0.setColor("#173177");
        dataMap.put("first",data0);
        SendTemplateMsgParams.Data data1 = new SendTemplateMsgParams.Data();
        data1.setValue("0.00元");
        data1.setColor("#173177");
        dataMap.put("orderMoneySum",data1);
        SendTemplateMsgParams.Data data2 = new SendTemplateMsgParams.Data();
        data2.setValue("测试");
        data2.setColor("#173177");
        dataMap.put("orderProductName",data2);
        SendTemplateMsgParams.Data data3 = new SendTemplateMsgParams.Data();
        data3.setValue("如有问题请致电400-828-1878或直接在微信留言，小易将第一时间为您服务！");
        data3.setColor("#173177");
        dataMap.put("Remark",data3);
        params.setData(dataMap);
        weiXin.sendTemplate(token,params);
    }
}
