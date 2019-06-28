/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Des:
 * ClassName: HttpUtils
 * Author: biqiang2017@163.com
 * Date: 2019/6/21
 * Time: 10:45
 */
public class HttpUtils {
    private CloseableHttpClient httpClient;

    public HttpUtils() {
        this.httpClient = HttpClients.createDefault();
    }

    private static class Holder{
        private static HttpUtils instance = new HttpUtils();
    }

    public static HttpUtils getInstance(){
        return Holder.instance;
    }

    /**
     * post请求
     * @param url
     * @param params
     */
    public void post(String url,Object params){
        HttpPost post = new HttpPost(url);
        StringEntity entity = new StringEntity(JSON.toJSONString(params), ContentType.create("text/plain", "UTF-8"));
        post.setEntity(entity);
        CloseableHttpResponse response = null;
        String result = null;
        try {
            response = httpClient.execute(post);
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
            EntityUtils.consume(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(result);
    }

    /**
     * get请求
     * @param url
     * @param params
     */
    public String get(String url, Map<String,String> params){
        // 创建参数队列
        List<NameValuePair> baseParams = new ArrayList<NameValuePair>();
        for (Map.Entry<String,String> item : params.entrySet()){
            baseParams.add(new BasicNameValuePair(item.getKey(), item.getValue()));
        }
        CloseableHttpResponse response = null;
        String result = null;
        try {
            HttpGet get = new HttpGet(url+EntityUtils.toString(new UrlEncodedFormEntity(baseParams, "UTF-8")));
            response = httpClient.execute(get);
            result = EntityUtils.toString(response.getEntity(), "UTF-8");
            EntityUtils.consume(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response != null){
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}
