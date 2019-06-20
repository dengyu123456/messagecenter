/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.common.factory;


import com.zhkj.nettyserver.common.util.StringUtil;

import java.math.BigDecimal;

/**
 * Des: 商品修改对照
 * ClassName: DiffFactory
 * Author: biqiang2017@163.com
 * Date: 2019/3/21
 * Time: 9:18
 */
public class DiffFactory {
    /**
     * 创建不同
     *
     * @param goodName
     * @param oldNumber
     * @param newNumber
     * @param oldUnitPrice
     * @param newUnitPrice
     * @return
     */
    public static String createDiff(String goodName, BigDecimal oldNumber, BigDecimal newNumber, BigDecimal
            oldUnitPrice, BigDecimal newUnitPrice) {
        Diff diff = null;
        if (oldNumber.compareTo(newNumber) != 0 || oldUnitPrice.compareTo(newUnitPrice) != 0) {
            diff = new Diff();
            diff.setGoodName(goodName);
            diff.setNumber(oldNumber.toString() + "->" + newNumber.toString());
            diff.setPrice(oldUnitPrice.toString() + "->" + newUnitPrice.toString());
            diff.setAction("修改");
        }
        return diff == null ? "" : diff.toString();
    }

    /**
     * 司机送达
     */
    public static String createDiff(String goodName,BigDecimal oldNumber ,BigDecimal oldActualNumber, BigDecimal newActualNumber
            , BigDecimal oldBackNumber, BigDecimal newBackNumber) {
        Diff diff = new Diff();
        diff.setGoodName(goodName);
        diff.setNumber(oldNumber.toString());
        //设置实收数量
        if (oldActualNumber != null && newActualNumber != null && oldActualNumber.compareTo(newActualNumber) != 0) {
            diff.setActualNumber(oldActualNumber.toString() + "->" + newActualNumber.toString());
        }
        //设置退回数量
        if (oldBackNumber != null && newBackNumber != null && oldBackNumber.compareTo(newBackNumber) != 0) {
            diff.setBackNumber(oldBackNumber.toString() + "->" + newBackNumber.toString());
        }
        diff.setAction("司机送达");
        return diff == null ? "" : diff.toString();
    }

    /**
     * 创建不同
     *
     * @param goodName
     * @param oldNumber
     * @param newNumber
     * @param oldPrice
     * @param newPrice
     * @param oldRealPrice
     * @param newRealPrice
     * @return
     */
    public static String createDiff(String goodName, BigDecimal oldNumber, BigDecimal newNumber, BigDecimal
            oldPrice, BigDecimal newPrice, BigDecimal oldRealPrice, BigDecimal newRealPrice) {
        Diff diff = null;
        if (oldNumber.compareTo(newNumber) != 0 || oldPrice.compareTo(newPrice) != 0 || oldRealPrice.compareTo
                (newRealPrice) != 0) {
            diff = new Diff();
            diff.setGoodName(goodName);
            diff.setNumber(oldNumber.toString() + "->" + newNumber.toString());
            diff.setPrice(oldPrice.toString() + "->" + newPrice.toString());
            diff.setRealPrice(oldRealPrice.toString() + "->" + newRealPrice.toString());
            diff.setAction("修改");
        }
        return diff == null ? "" : diff.toString();
    }

    /**
     * 创建不同
     *
     * @param goodName
     * @param oldNumber
     * @param newNumber
     * @param oldUnitPrice
     * @param newUnitPrice
     * @return
     */
    public static String createDiff(String goodName, BigDecimal oldNumber, BigDecimal newNumber
            , BigDecimal oldOrderNumber, BigDecimal newOrderNumber, BigDecimal oldBackNumber, BigDecimal newBackNumber
            , BigDecimal oldActualNumber, BigDecimal newActualNumber, BigDecimal oldSortingNumber, BigDecimal
                                            newSortingNumber, BigDecimal oldUnitPrice, BigDecimal newUnitPrice) {
        Diff diff = null;
        if (oldNumber.compareTo(newNumber) != 0 || oldUnitPrice.compareTo(newUnitPrice) != 0) {
            diff = new Diff();
            diff.setGoodName(goodName);
            diff.setNumber(oldNumber.toString() + "->" + newNumber.toString());
            diff.setPrice(oldUnitPrice.toString() + "->" + newUnitPrice.toString());
        }
        if (oldOrderNumber != null && newOrderNumber != null && oldOrderNumber.compareTo(newOrderNumber) != 0) {
            diff.setOrderNumber(oldOrderNumber.toString() + "->" + newOrderNumber.toString());
        }
        if (oldBackNumber != null && newBackNumber != null && oldBackNumber.compareTo(newBackNumber) != 0) {
            diff.setBackNumber(oldBackNumber.toString() + "->" + newBackNumber.toString());
        }
        if (oldActualNumber != null && newActualNumber != null && oldActualNumber.compareTo(newActualNumber) != 0) {
            diff.setActualNumber(oldActualNumber.toString() + "->" + newActualNumber.toString());
        }
        if (oldSortingNumber != null && newSortingNumber != null && oldSortingNumber.compareTo(newSortingNumber) != 0) {
            diff.setSortingNumber(oldSortingNumber.toString() + "->" + newSortingNumber.toString());
        }
        if (diff != null) {
            diff.setAction("修改");
        }
        return diff == null ? "" : diff.toString();
    }

    /**
     * 创建删除
     *
     * @param goodName
     * @param oldNumber
     * @param newUnitPrice
     * @return
     */
    public static String createDel(String goodName, BigDecimal oldNumber, BigDecimal newUnitPrice) {
        Diff diff = new Diff();
        diff.setGoodName(goodName);
        diff.setPrice(newUnitPrice.toString());
        diff.setNumber(oldNumber.toString());
        diff.setAction("删除");
        return diff.toString();
    }

    /**
     * 增加
     *
     * @param goodName
     * @param oldNumber
     * @param newUnitPrice
     * @return
     */
    public static String createCreate(String goodName, BigDecimal oldNumber, BigDecimal newUnitPrice) {
        Diff diff = new Diff();
        diff.setGoodName(goodName);
        diff.setPrice(newUnitPrice.toString());
        diff.setNumber(oldNumber.toString());
        diff.setAction("新增");
        return diff.toString();
    }

    /**
     * 增加
     *
     * @param goodName
     * @param price
     * @param realPrice
     * @return
     */
    public static String createCreate(String goodName, BigDecimal number, BigDecimal price, BigDecimal realPrice) {
        Diff diff = new Diff();
        diff.setGoodName(goodName);
        diff.setPrice(price.toString());
        diff.setRealPrice(realPrice.toString());
        diff.setNumber(number.toString());
        diff.setAction("新增");
        return diff.toString();
    }

    public static class Diff {
        /**
         * 商品名称
         */
        private String goodName;

        /**
         * 商品数量
         */
        private String number;

        /**
         * 订单数量
         */
        private String orderNumber;

        /**
         * 退回数量
         */
        private String backNumber;

        /**
         * 实收数量
         */
        private String actualNumber;

        /**
         * 分拣数量
         */
        private String sortingNumber;

        /**
         * 商品单价
         */
        private String price;

        /**
         * 商品实际单价
         */
        private String realPrice;

        /**
         * 操作
         */
        private String action;

        public String getGoodName() {
            return goodName;
        }

        public void setGoodName(String goodName) {
            this.goodName = goodName;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getBackNumber() {
            return backNumber;
        }

        public void setBackNumber(String backNumber) {
            this.backNumber = backNumber;
        }

        public String getActualNumber() {
            return actualNumber;
        }

        public void setActualNumber(String actualNumber) {
            this.actualNumber = actualNumber;
        }

        public String getSortingNumber() {
            return sortingNumber;
        }

        public void setSortingNumber(String sortingNumber) {
            this.sortingNumber = sortingNumber;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRealPrice() {
            return realPrice;
        }

        public void setRealPrice(String realPrice) {
            this.realPrice = realPrice;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("<div>{</div>");
            sb.append("<div>商品：");
            sb.append(goodName);
            sb.append(",</div>");
            sb.append("<div>数量：");
            sb.append(number);
            sb.append(",</div>");
            if (StringUtil.isNotBlank(orderNumber)) {
                sb.append("<div>订单数量：");
                sb.append(orderNumber);
                sb.append(",</div>");
            }
            if (StringUtil.isNotBlank(backNumber)) {
                sb.append("<div>退回数量：");
                sb.append(backNumber);
                sb.append(",</div>");
            }
            if (StringUtil.isNotBlank(actualNumber)) {
                sb.append("<div>实收数量：");
                sb.append(actualNumber);
                sb.append(",</div>");
            }
            if (StringUtil.isNotBlank(sortingNumber)) {
                sb.append("<div>分拣数量：");
                sb.append(sortingNumber);
                sb.append(",</div>");
            }
            sb.append("<div>价格：");
            sb.append(price);
            sb.append(",</div>");
            if (StringUtil.isNotBlank(realPrice)) {
                sb.append("<div>实际价格：");
                sb.append(realPrice);
                sb.append(",</div>");
            }
            sb.append("<div>动作：");
            sb.append(action);
            sb.append(",</div>");
            sb.append("<div>}</div>");
            return sb.toString();
        }
    }
}
