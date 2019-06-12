/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.common.base;

import com.alibaba.fastjson.JSON;
import com.zhkj.msgcenter.common.utils.AESUtil;
import com.zhkj.msgcenter.common.utils.Base64Util;

/**
 * Des:
 * ClassName: TokenUtil
 * Author: biqiang2017@163.com
 * Date: 2018/7/16
 * Time: 16:27
 */
public class TokenUtil {
    /**
     * 通过token获取uuid
     * @param token
     * @return
     */
    public static Long getUuid(String token){
        String tokenJson = AESUtil.decryptor(token);
        TokenVO tokenVOOb = JSON.parseObject(tokenJson, TokenVO.class);
        return Long.valueOf(tokenVOOb.getUuid());
    }

    /**
     * 通过token 字符串获取token
     * @param token
     * @return
     */
    public static TokenVO getToken(String token){
        String tokenJson = new String(AESUtil.decryptor(new String(Base64Util.decodeFromString(token))));
        return JSON.parseObject(tokenJson, TokenVO.class);
    }
}
