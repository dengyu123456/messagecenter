/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.common.exception;

import com.zhkj.nettyserver.common.base.respone.Response;
import com.zhkj.nettyserver.common.base.respone.ResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Des:
 * ClassName: ExceptionController
 * Author: biqiang2017@163.com
 * Date: 2018/7/4
 * Time: 16:20
 */
//@RestControllerAdvice
@ControllerAdvice
public class ExceptionController {

    // 捕获运行时异常
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.OK)
    public Response runtimeException(HttpServletRequest request, Throwable ex) {
        return ResponseFactory.createBad(String.valueOf(getStatus(request).value()), StringUtils.isEmpty(ex
                .getMessage()) ? "未知错误" : ex.getMessage());
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    public Response globalException(HttpServletRequest request, Throwable ex) {
        return ResponseFactory.createBad(String.valueOf(getStatus(request).value()), StringUtils.isEmpty(ex
                .getMessage()) ? "未知错误" : ex.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

