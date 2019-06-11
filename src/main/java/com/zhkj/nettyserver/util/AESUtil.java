/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

/**
 * Des: AES加解密工具
 * ClassName: AESUtil
 * Author: biqiang2017@163.com
 * Date: 2018/7/3
 * Time: 15:38
 */
public class AESUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AESUtil.class);

    private static AESUtil INSTANCE = null;


    //KeyGenerator 提供对称密钥生成器的功能，支持各种算法
    private KeyGenerator keygen;
    //SecretKey 负责保存对称密钥
    private SecretKey deskey;
    //Cipher负责完成加密或解密工作
    private Cipher cipher;
    private String salt = "qyQaz14Y258";
    private static final String CODE = "UTF-8";

    private AESUtil() {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        //实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
        try {
            keygen = KeyGenerator.getInstance("AES");
            //初始化秘钥
            keygen.init(128, new SecureRandom(salt.getBytes(CODE)));
            //生成密钥
            deskey = keygen.generateKey();
            //生成Cipher对象,指定其支持的DES算法
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.toString());
        } catch (NoSuchPaddingException e) {
            LOGGER.error(e.toString());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.toString());
        }
    }

    private synchronized static void init() {
        //如果为空才需要创建
        if (INSTANCE == null) {
            INSTANCE = new AESUtil();
        }
    }

    /**
     * 对字符串加密
     *
     * @param enStr
     * @return
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String encrytor(String enStr) throws NullPointerException {
        if (StringUtils.isEmpty(enStr)) {
            throw new NullPointerException(" AES 加密参数不能为空 ");
        } else {
            if (INSTANCE == null) {
                init();
                return INSTANCE.encrytorP(enStr);
            } else {
                return INSTANCE.encrytorP(enStr);
            }
        }
    }

    private String encrytorP(String enStr) {
        String result = null;
        try {
            // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
            cipher.init(Cipher.ENCRYPT_MODE, deskey);
            result = Base64Util.encodeToString(cipher.doFinal(enStr.getBytes(CODE)));
        } catch (UnsupportedEncodingException e) {//编码格式不对
            LOGGER.error(e.toString());
        } catch (BadPaddingException e) {
            LOGGER.error(e.toString());
        } catch (IllegalBlockSizeException e) {
            LOGGER.error(e.toString());
        } catch (InvalidKeyException e) {
            LOGGER.error(e.toString());
        }
        return result;
    }

    /**
     * 解密
     * @param deStr
     * @return
     */
    public static  String decryptor(String deStr) {
        if (StringUtils.isEmpty(deStr)){
            throw new NullPointerException(" AES 解密参数不能为空 ");
        }else{
            if (INSTANCE == null){
                init();
                return INSTANCE.decryptorP(deStr);
            }else{
                return INSTANCE.decryptorP(deStr);
            }
        }
    }

    private String decryptorP(String deStr) {
        byte[] deByte = Base64Util.decodeFromString(deStr);
        String result = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, deskey);
            result = new String( cipher.doFinal(deByte),CODE);
        } catch (InvalidKeyException e) {
            LOGGER.error(e.toString());
        } catch (BadPaddingException e) {
            LOGGER.error(e.toString());
        } catch (IllegalBlockSizeException e) {
            LOGGER.error(e.toString());
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.toString());
        }
        return result;

    }

    /**
     * @param args
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     * @throws InvalidKeyException
     */
    public static void main(String[] args) throws Exception {
        String msg = "admin";
        String encontent = AESUtil.encrytor(msg);
        String decontent = AESUtil.decryptor(encontent);
        System.out.println("明文是:" + msg);
        System.out.println("加密后:" + encontent);
        System.out.println("解密后:" + decontent);
    }

}
