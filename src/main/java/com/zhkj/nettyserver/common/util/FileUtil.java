/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.common.util;

import java.io.File;

/**
 * Des: 文件操作工具
 * ClassName: FileUtil
 * Author: biqiang2017@163.com
 * Date: 2018/7/21
 * Time: 23:39
 */
public class FileUtil {

    /**
     * 生成文件名字
     * @param ofilename 原文件名
     * @return
     */
    public static String genFileName(String ofilename){
        return "00"+RandomUtil.getUuid()+ofilename.substring(ofilename.lastIndexOf("."));
    }

    /**
     * 文件名转路径名 00uuid
     * @param root
     * @param fileName 文件全名
     * @return
     */
    public static String fileNameToFullPath(String root,String fileName){
        StringBuilder sb =new StringBuilder(root);
        sb.append(File.separator);
        sb.append(fileName.substring(2,4));
        sb.append(File.separator);
        sb.append(fileName.substring(4,6));
        sb.append(File.separator);
        sb.append(fileName.substring(6,8));
        return sb.toString();
    }

    /**
     * 创建文件
     * @param filePath
     */
    public static void createPath(String filePath){
        File file = new File(filePath);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    /**
     * 创建文件
     * @param filePath
     */
    public static boolean delFile(String filePath){
        File file = new File(filePath);
        if (file.exists() && file.isFile()){
            if (file.delete()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
}
