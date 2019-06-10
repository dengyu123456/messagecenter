/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.purchase.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhkj.purchase.base.PageBean;
import com.zhkj.purchase.base.request.Request;
import com.zhkj.purchase.common.utils.MD5Util;

//import com.zhkj.purchase.system.domain.SystemUser;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Des:
 * ClassName: BaseController
 * Author: biqiang2017@163.com
 * Date: 2018/7/4
 * Time: 10:34
 */
public abstract class BaseController {
//    @Autowired
//    private EnterpriseService enterpriseService;

    public final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    /**
     * 对象获取加盐MD5
     *
     * @param enOb
     * @return
     */
    public String getMd5(Object enOb, String salt) {
        if (enOb instanceof String) {
            return MD5Util.encrytor(enOb.toString(), salt);
        } else {
            return MD5Util.encrytor(JSONObject.toJSONString(enOb), salt);
        }
    }

    /**
     * 对象获取MD5
     *
     * @param enOb
     * @return
     */
    public String getMd5(Object enOb) {
        if (enOb instanceof String) {
            return MD5Util.encrytor(enOb.toString());
        } else {
            return MD5Util.encrytor(JSONObject.toJSONString(enOb));
        }
    }

//    /**
//     * 通过shiro获取当前登录用户
//     *
//     * @return
//     */
//    public SystemUser getUser() {
//        return (SystemUser) SecurityUtils.getSubject().getPrincipal();
//    }

    public PageBean getPage(Request reqOb) {
        PageBean pageBean = new PageBean();
        pageBean.setPage(reqOb.getPage() == null || reqOb.getPage().equals(0) ? 1 : reqOb.getPage());
        pageBean.setPageSize(reqOb.getPageSize() == null || reqOb.getPageSize().equals(0) ? 10 : reqOb.getPageSize());
        pageBean.setParams(reqOb.getParams());
        return pageBean;
    }
}
