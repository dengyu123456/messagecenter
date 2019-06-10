/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.purchase.common.shiro;

import com.alibaba.fastjson.JSON;
import com.zhkj.purchase.base.respone.Response;
import com.zhkj.purchase.base.respone.ResponseFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Des:
 * ClassName: StatelessHttpAuthenticationFilter
 * Author: biqiang2017@163.com
 * Date: 2018/7/3
 * Time: 15:16
 */
public class StatelessHttpAuthenticationFilter extends BasicHttpAuthenticationFilter  {//
    private static final Logger LOGGER = LoggerFactory.getLogger(StatelessHttpAuthenticationFilter.class);

    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization) || authorization.contains("Basic")){
            authorization = request.getParameter("token");
        }
        return authorization != null;
    }

    /**
     *
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
//        String token = this.getAuthzHeader(respone);
//        Token tokenOb = new Token(token);
//        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
//        getSubject(respone, request).login(tokenOb);
//        // 如果没有抛出异常则代表登入成功，返回true
//        return true;

        String token = this.getAuthzHeader(request);
        if (StringUtils.isEmpty(token) || token.contains("Basic")){
            token =  request.getParameter("token");
        }
        Token tokenOb = new Token(token);
        try {
            Subject subject = this.getSubject(request, response);
            subject.login(tokenOb);
            return this.onLoginSuccess(tokenOb, subject, request, response);
        } catch (AuthenticationException var5) {
            return this.onLoginFailure(tokenOb, var5, request, response);
        }
    }

    /**
     * 这里我们详细说明下为什么最终返回的都是true，即允许访问
     * 例如我们提供一个地址 GET /article
     * 登入用户和游客看到的内容是不同的
     * 如果在这里返回了false，请求会被直接拦截，用户看不到任何东西
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        LOGGER.info("isAccessAllowed");
//        boolean is = true;
//        if (isLoginAttempt(respone, request)) {
//            try {
//                executeLogin(respone, request);
//            } catch (Exception e) {
//                LOGGER.error("Exception",e);
//                is = false;
//            }
//        }
//        return is;
        return false;
    }

    /**
     * 当访问拒绝时是否已经处理了；
     * 如果返回true表示需要继续处理；
     * 如果返回false表示该拦截器实例已经处理完成了，将直接返回即可。
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        LOGGER.info("onAccessDenied");
        if (isLoginAttempt(request, response)) {
            return executeLogin(request,response);
        } else {
            onLoginFail(response);
            return false;
        }
    }

    /**
     * 鉴定失败，返回错误信息
     *
     * @param token
     * @param ae
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request,
                                     ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        httpResponse.setStatus(HttpStatus.OK.value());
        Response responseOb = ResponseFactory.createBad(Response.UNAUTHORIZED, ae.getMessage());
        try {
            httpResponse.getWriter().print(JSON.toJSONString(responseOb));
        } catch (IOException e) {
            LOGGER.error("IOException", e);
        }
        return false;
    }

    /**
     * token 认证失败
     *
     * @param response
     */
    private void onLoginFail(ServletResponse response) {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        httpResponse.setStatus(HttpStatus.OK.value());
        Response responseOb = ResponseFactory.createBad(Response.UNAUTHORIZED, "token无效");
        try {
            httpResponse.getWriter().print(JSON.toJSONString(responseOb));
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }


    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
