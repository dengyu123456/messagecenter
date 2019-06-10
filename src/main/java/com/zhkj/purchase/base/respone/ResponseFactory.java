/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.purchase.base.respone;

import com.github.pagehelper.PageInfo;

import java.math.BigDecimal;

/**
 * Des: 返回值工厂类
 * ClassName: ResponseFactory
 * Author: biqiang2017@163.com
 * Date: 2018/7/4
 * Time: 14:54
 */
public class ResponseFactory {

    public static Response createOk(Object params){
        return new Response(Response.OK, "", params, null, null, null);
    }

    public static Response createOk(PageInfo pageInfo){
        return new Response(Response.OK, "", pageInfo.getList(), pageInfo.getPageSize(), pageInfo.getPageNum(),pageInfo.getTotal());
    }

    public static Response createOk(Object params,Integer pageSize,Integer page,Long total){
        return new Response(Response.OK, "", params, page, pageSize,total);
    }

    public static Response createOk(Object params, Integer pageSize, Integer page, Long total, BigDecimal totalPrice){
        return new Response(Response.OK, "", params, page, pageSize,total,totalPrice);
    }



    public static Response createBad(String code,String msg){
        return new Response(code, msg, null, null, null, null);
    }

    public static Response createBad(String msg){
        return new Response(Response.BADREQUEST, msg, null, null, null, null);
    }

    public static Response createUnAuth(String msg){
        return new Response(Response.UNAUTHORIZED, msg, null, null, null, null);
    }
}
