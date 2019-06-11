/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.util.redis.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Des: 时间工具
 * ClassName: DateUtil
 * Author: biqiang2017@163.com
 * Date: 2018/8/11
 * Time: 10:49
 */
public class DateUtil {

    /**
     * LocalDate转Date
     * @param localDate
     * @return
     */
    public static Date covertLocalDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.of("GMT+8")).toInstant());
    }

    /**
     * Date转LocalDate
     * @param date
     * @return
     */
    public static LocalDate covertDateToLocalDate(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("GMT+8"));
        return localDateTime.toLocalDate();
    }

    /**
     * 开始结束时间相差天数(大于等于true)
     * @param startTime
     * @param endTime
     * @param day
     * @return
     */
    public static boolean exceedDay(Date startTime,Date endTime,int day){
        LocalDate start = covertDateToLocalDate(startTime);
        LocalDate end = covertDateToLocalDate(endTime);
        if (start.compareTo(end) < 0){
            return false;
        }
        start.plusDays(day);
        if (start.compareTo(end) < 0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 开始结束时间相差月
     * @param startTime
     * @param endTime
     * @param week
     * @return
     */
    public static boolean exceedWeek(Date startTime,Date endTime,int week){
        LocalDate start = covertDateToLocalDate(startTime);
        LocalDate end = covertDateToLocalDate(endTime);
        if (start.compareTo(end) < 0){
            return false;
        }
        start.plusWeeks(week);
        if (start.compareTo(end) < 0){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 开始结束时间相差月
     * @param startTime
     * @param endTime
     * @param month
     * @return
     */
    public static boolean exceedMonth(Date startTime,Date endTime,int month){
        LocalDate start = covertDateToLocalDate(startTime);
        LocalDate end = covertDateToLocalDate(endTime);
        if (start.compareTo(end) < 0){
            return false;
        }
        start.plusMonths(month);
        if (start.compareTo(end) < 0){
            return false;
        }else{
            return true;
        }
    }
}
