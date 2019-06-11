/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.util.redis.utils;

import org.springframework.util.Base64Utils;

/**
 * Des:
 * ClassName: Base64Util
 * Author: biqiang2017@163.com
 * Date: 2018/7/3
 * Time: 16:24
 */
public class Base64Util extends Base64Utils {
//    private static final Logger LOGGER = LoggerFactory.getLogger(Base64Util.class);
//
//    private static final String CODE = "UTF-8";
//    private static Base64Util base64Util;
//    private BASE64Encoder encoder;
//    private BASE64Decoder decoder;
//
//    private Base64Util() {
//        if (encoder == null) {
//            encoder = new BASE64Encoder();
//        }
//        if (decoder == null) {
//            decoder = new BASE64Decoder();
//        }
//    }
//
//    private static synchronized void init() {
//        base64Util = new Base64Util();
//    }
//
//    /**
//     * Base64加密
//     * @param enStr
//     * @return
//     * @throws NullPointerException
//     */
//    public static String encrytor(String enStr) throws NullPointerException {
//        String result = null;
//        try {
//            result = encrytor(enStr.getBytes(CODE));
//        } catch (UnsupportedEncodingException e) {
//            LOGGER.error(e.toString());
//        }
//        return result;
//    }
//
//    /**
//     * Base64加密
//     * @param enByte
//     * @return
//     * @throws NullPointerException
//     */
//    public static String encrytor(byte[] enByte) throws NullPointerException {
//        if (StringUtils.isEmpty(enByte)) {
//            throw new NullPointerException(" base64 加密字段不能为空 ");
//        }else{
//            if (base64Util == null || base64Util.encoder == null) {
//                init();
//                return base64Util.encoder.encode(enByte);
//            } else {
//                return base64Util.encoder.encode(enByte);
//            }
//        }
//    }
//
//    /**
//     * Base64解密
//     * @param deStr
//     * @return
//     * @throws NullPointerException
//     */
//    public static byte [] decryptor(String deStr) throws NullPointerException{
//        if (StringUtils.isEmpty(deStr)) {
//            throw new NullPointerException(" base64 解密字段不能为空 ");
//        }else{
//            byte [] result = null;
//            if (base64Util == null || base64Util.decoder == null) {
//                init();
//                try {
//                    result = base64Util.decoder.decodeBuffer(deStr);
//                } catch (IOException e) {
//                    LOGGER.error(e.toString());
//                }
//            }
//            return result;
//        }
//    }
}
