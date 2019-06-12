/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.common.base.respone;


/**
 * Des: 返回值工厂类
 * ClassName: ResponseFactory
 * Author: biqiang2017@163.com
 * Date: 2018/7/4
 * Time: 14:54
 */
public class ResponseStompFactory {

    public static ResponseStomp createOk(Object params,String action){
        return new ResponseStomp(ResponseStomp.OK, "",action, params);
    }

    public static ResponseStomp createBad(String msg,String action){
        return new ResponseStomp(ResponseStomp.ERROR, msg,action,"");
    }
}
