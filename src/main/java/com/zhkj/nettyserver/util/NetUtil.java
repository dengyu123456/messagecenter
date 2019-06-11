/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Des:
 * ClassName: NetUtil
 * Author: biqiang2017@163.com
 * Date: 2018/8/22
 * Time: 17:54
 */
public class NetUtil {
    /**
     * 判断是否是ie浏览器
     * @param request
     * @return
     */
    public static boolean isMSBrowser(HttpServletRequest request) {
        String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};
        String userAgent = request.getHeader("User-Agent");
        for (String signal : IEBrowserSignals) {
            if (userAgent.contains(signal)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取客户端数据
     * @param request
     * @return
     */
    public static Map<String,String> getClient(HttpServletRequest request){
        Map<String,String> result = new HashMap<String,String>();
        result.put("browser",request.getHeader("user-agent"));
        result.put("osname",request.getHeader("os.name"));
        result.put("osversion",request.getHeader("os.version"));
        result.put("osarch",request.getHeader("os.arch"));
        return result;
    }

}
