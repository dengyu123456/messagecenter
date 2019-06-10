/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.purchase.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * Des:
 * ClassName: MD5Util
 * Author: biqiang2017@163.com
 * Date: 2018/7/4
 * Time: 10:36
 */
public class MD5Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(MD5Util.class);
//
//    private static final String CODE = "UTF-8";
//    private static MD5Util INSTANCE;
//    private MessageDigest messageDigest;
//    private char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
//            'a', 'b', 'c', 'd', 'e', 'f'};
//
//    private MD5Util() {
//        if (messageDigest == null) {
//            try {
//                messageDigest = MessageDigest.getInstance("MD5");
//            } catch (NoSuchAlgorithmException e) {
//                LOGGER.error(e.toString());
//            }
//        }
//    }
//
//    private synchronized static void init() {
//        if (INSTANCE == null) {
//            INSTANCE = new MD5Util();
//        }
//    }
//
//    /**
//     * MD5加密
//     *
//     * @param enStr
//     * @return
//     * @throws NullPointerException
//     */
//    public static String encrytor(String enStr) throws NullPointerException {
//        //根据MD5算法生成MessageDigest对象
//        if (StringUtils.isEmpty(enStr)) {
//            throw new NullPointerException(" MD5加密参数不能为空 ");
//        } else {
//            if (INSTANCE == null) {
//                init();
//                return encrytorP(enStr);
//            } else {
//                return encrytorP(enStr);
//            }
//        }
//
//        String newPassword = new SimpleHash(ALGORITH_NAME, pswd, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
//        return newPassword;
//    }
//
//    /**
//     * MD5加盐加密
//     * @param enStr
//     * @param salt
//     * @return
//     * @throws NullPointerException
//     */
//    public static String encrytor(String enStr, String salt) throws NullPointerException {
//        if (StringUtils.isEmpty(enStr)) {
//            throw new NullPointerException(" MD5加密参数不能为空 ");
//        }  else if (StringUtils.isEmpty(salt)) {
//            throw new NullPointerException(" MD5加盐加密，盐不能为空 ");
//        } else{
//            if (INSTANCE == null) {
//                init();
//                return encrytorP(enStr+salt);
//            } else {
//                return encrytorP(enStr+salt);
//            }
//        }
//    }
//
//    private static String encrytorP(String enStr) {
//        String result = null;
//        try {
//            //使用srcBytes更新摘要
//            INSTANCE.messageDigest.update(enStr.getBytes(CODE));
//            //完成哈希计算，得到result
//            result = HexUtil.bytesToHexString(INSTANCE.messageDigest.digest());
//        } catch (UnsupportedEncodingException e) {
//            LOGGER.error(e.toString());
//        }
//        return result;
//    }

    private static final String SALT = "Qrasgd4s5e";

    private static final String ALGORITH_NAME = "md5";

    private static final int HASH_ITERATIONS = 2;

    public static String encrytor(String enStr) {
        String newPassword = new SimpleHash(ALGORITH_NAME, enStr, ByteSource.Util.bytes(SALT), HASH_ITERATIONS).toHex();
        return newPassword;
    }

    public static String encrytor(String enStr, String salt) {
        String newPassword = new SimpleHash(ALGORITH_NAME, enStr, ByteSource.Util.bytes(StringUtils.isEmpty(salt)?SALT:salt),
                HASH_ITERATIONS).toHex();
        return newPassword;
    }

    public static void main(String[] args) {
        System.out.println( MD5Util.encrytor("123456"));
    }

}
