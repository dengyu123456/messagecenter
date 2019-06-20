package com.zhkj.nettyserver.common.util;

import java.util.*;

/**
 * Des:拿到数据交集工具类
 * ClassName: ArrayUtil
 * Author: dengyi
 * Date: 2019-06-20 10:05
 */
public class ArrayUtil {

    //两个数组的交集
    public static Long[] getJ(Long[] m, Long[] n) {
        List<Long> rs = new ArrayList<Long>();

        // 将较长的数组转换为set
        Set<Long> set = new HashSet<Long>(Arrays.asList(m.length > n.length ? m : n));

        // 遍历较短的数组，实现最少循环
        for (Long i : m.length > n.length ? n : m) {
            if (set.contains(i)) {
                rs.add(i);
            }
        }

        Long[] arr = {};
        return rs.toArray(arr);
    }

    //两个数组的差集
    public static Long[] getC(Long[] m, Long[] n) {
        // 将较长的数组转换为set
        Set<Long> set = new HashSet<Long>(Arrays.asList(m.length > n.length ? m : n));
        // 遍历较短的数组，实现最少循环
        for (Long i : m.length > n.length ? n : m) {
            // 如果集合里有相同的就删掉，如果没有就将值添加到集合
            if (set.contains(i)) {
                set.remove(i);
            } else {
                set.add(i);
            }
        }
        Long[] arr = {};
        return set.toArray(arr);
    }
}
