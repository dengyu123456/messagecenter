/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.weixin.service;

import com.zhkj.nettyserver.common.base.service.BaseService;
import com.zhkj.nettyserver.weixin.domain.EnterpriseWeixin;
import com.zhkj.nettyserver.weixin.domain.WeixinTemplateMsg;
import com.zhkj.nettyserver.weixin.domain.request.AddWeixinTemplateMsgParams;
import com.zhkj.nettyserver.weixin.domain.request.EditEnterpriseWeixinParams;
import com.zhkj.nettyserver.weixin.domain.request.EditWeixinTemplateMsgParams;

import java.util.List;

/**
 * Des:
 * ClassName: WeixinService
 * Author: biqiang2017@163.com
 * Date: 2019/6/24
 * Time: 16:13
 */
public interface WeixinService extends BaseService {
    /**
     * 获取所有的公司微信
     * @return
     */
    List<EnterpriseWeixin> selectAll();

    /**
     * 更新公司微信账号
     * @param uEwxiP
     * @return
     */
    int update(EnterpriseWeixin uEwxiP);

    /**
     * 更新公司微信账号
     * @param params
     * @return
     */
    int updateByEnteUuid(EditEnterpriseWeixinParams params);

    /**
     * 获取公司微信账号
     * @param enteUuid
     * @return
     */
    EnterpriseWeixin selectOneByEnteUuid(Long enteUuid);

    /**
     * 获取公司模板消息列表
     * @param ewxiUuid
     * @return
     */
    List<WeixinTemplateMsg> selectTmsgByEwxiUuid(Long ewxiUuid);

    /**
     * 获取微信消息模板
     * @param wtmsUuid
     * @return
     */
    WeixinTemplateMsg selectTmsgOne(Long wtmsUuid);

    /**
     * 更新微信消息模板
     * @param params
     * @return
     */
    int updateWtms(EditWeixinTemplateMsgParams params);

    /**
     * 增加微信消息模板
     * @param params
     * @return
     */
    int insertWtms(AddWeixinTemplateMsgParams params);

    /**
     * 获取微信模板消息
     * @param rWtmsP
     * @return
     */
    WeixinTemplateMsg selectTmsgOne(WeixinTemplateMsg rWtmsP);

    /**
     * 增加微信模板消息
     * @param cWtmsP
     * @return
     */
    int insertTmsg(WeixinTemplateMsg cWtmsP);
}
