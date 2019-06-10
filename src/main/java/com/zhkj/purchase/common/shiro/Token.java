/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.purchase.common.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Des:
 * ClassName: TokenVO
 * Author: biqiang2017@163.com
 * Date: 2018/7/3
 * Time: 15:23
 */
public class Token implements AuthenticationToken {
    //秘钥
    private String token;
    private Object principal;
    private Object credentials;


    public Token() {
        this("","","");
    }

    public Token(String token) {
        this(token,token,token);
    }

    public Token(String token,Object principal,Object credentials) {
        this.token = token;
        this.principal = principal;
        this.credentials = credentials;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

}
