/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.common.base;

/**
 * Des:
 * ClassName: PageBean
 * Author: biqiang2017@163.com
 * Date: 2018/7/17
 * Time: 17:21
 */
public class PageBean<T> {
    private T params;
    private int page;
    private int pageSize;

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
