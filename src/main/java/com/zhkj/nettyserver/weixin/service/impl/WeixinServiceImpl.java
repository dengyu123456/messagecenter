/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.weixin.service.impl;

import com.zhkj.nettyserver.common.base.service.impl.BaseServiceImpl;
import com.zhkj.nettyserver.common.util.AESUtil;
import com.zhkj.nettyserver.common.util.BeanUtil;
import com.zhkj.nettyserver.common.util.CollectionUtil;
import com.zhkj.nettyserver.common.util.StringUtil;
import com.zhkj.nettyserver.weixin.WeiXin;
import com.zhkj.nettyserver.weixin.dao.EnterpriseWeixinMapper;
import com.zhkj.nettyserver.weixin.dao.WeixinTemplateMsgMapper;
import com.zhkj.nettyserver.weixin.domain.EnterpriseWeixin;
import com.zhkj.nettyserver.weixin.domain.WeixinTemplateMsg;
import com.zhkj.nettyserver.weixin.domain.request.AddWeixinTemplateMsgParams;
import com.zhkj.nettyserver.weixin.domain.request.EditEnterpriseWeixinParams;
import com.zhkj.nettyserver.weixin.domain.request.EditWeixinTemplateMsgParams;
import com.zhkj.nettyserver.weixin.service.WeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.List;

/**
 * Des:
 * ClassName: WeixinServiceImpl
 * Author: biqiang2017@163.com
 * Date: 2019/6/24
 * Time: 16:14
 */
@Service("weixinService")
public class WeixinServiceImpl extends BaseServiceImpl implements WeixinService {
    @Autowired
    private EnterpriseWeixinMapper enterpriseWeixinMapper;
    @Autowired
    private WeixinTemplateMsgMapper weixinTemplateMsgMapper;
    @Autowired
    private WeiXin weiXin;

    /**
     * 获取所有公司微信
     * @return
     */
    @Override
    public List<EnterpriseWeixin> selectAll() {
        return this.enterpriseWeixinMapper.selectAll();
    }

    /**
     * 更新公司微信账号
     * @param uEwxiP
     * @return
     */
    @Override
    public int update(EnterpriseWeixin uEwxiP) {
        return this.enterpriseWeixinMapper.updateByExampleSelective(uEwxiP, Example.builder(EnterpriseWeixin.class)
                .andWhere(Sqls.custom().andEqualTo("ewxiUuid",uEwxiP.getEwxiUuid())).build());
    }

    /**
     * 更新公司微信账号
     * @param params
     * @return
     */
    @Override
    public int updateByEnteUuid(EditEnterpriseWeixinParams params) {
        EnterpriseWeixin tmp = this.selectOneByEnteUuid(params.getEwxiEnteUuid());
        if (tmp == null){
            EnterpriseWeixin cEweiP = new EnterpriseWeixin();
            cEweiP.setEwxiEnteUuid(params.getEwxiEnteUuid());
            cEweiP.setEwxiWtmsAllow(params.getEwxiWtmsAllow());
            if (StringUtil.isNotBlank(params.getEwxiAppid())){
                cEweiP.setEwxiAppid(AESUtil.encrytor(params.getEwxiAppid()));
            }
            if (StringUtil.isNotBlank(params.getEwxiSecret())){
                cEweiP.setEwxiSecret(AESUtil.encrytor(params.getEwxiSecret()));
            }
            return this.enterpriseWeixinMapper.insert(cEweiP);
        }else{
            boolean change = false;
            EnterpriseWeixin uEweiP = new EnterpriseWeixin();
            uEweiP.setEwxiEnteUuid(params.getEwxiEnteUuid());
            uEweiP.setEwxiWtmsAllow(params.getEwxiWtmsAllow());
            String appid = null;
            String secret = null;
            if (StringUtil.isNotBlank(params.getEwxiAppid())){
                uEweiP.setEwxiAppid(AESUtil.encrytor(params.getEwxiAppid()));
                if (!uEweiP.getEwxiAppid().equals(tmp.getEwxiAppid())){
                    change = true;
                }
                appid = params.getEwxiAppid();
            }else{
                appid = AESUtil.decryptor(tmp.getEwxiAppid());
            }
            if (StringUtil.isNotBlank(params.getEwxiSecret())){
                uEweiP.setEwxiSecret(AESUtil.encrytor(params.getEwxiSecret()));
                if (!uEweiP.getEwxiSecret().equals(tmp.getEwxiSecret())){
                    change = true;
                }
                secret = params.getEwxiSecret();
            }else{
                secret = AESUtil.decryptor(tmp.getEwxiSecret());
            }
            if (change){
                String token = this.weiXin.getAccessToken(appid,secret);
                if (StringUtil.isNotBlank(token)){
                    uEweiP.setEwxiAccessToken(token);
                    uEweiP.setEwxiLastTime(System.currentTimeMillis());
                }
            }
            return this.enterpriseWeixinMapper.updateByExampleSelective(uEweiP, Example.builder(EnterpriseWeixin.class)
                    .andWhere(Sqls.custom().andEqualTo("ewxiEnteUuid",uEweiP.getEwxiEnteUuid())).build());

        }
    }

    /**
     * 获取公司微信账号
     * @param enteUuid
     * @return
     */
    @Override
    public EnterpriseWeixin selectOneByEnteUuid(Long enteUuid) {
        EnterpriseWeixin rEweiP = new EnterpriseWeixin();
        rEweiP.setEwxiEnteUuid(enteUuid);
        return this.enterpriseWeixinMapper.selectOne(rEweiP);
    }

    /**
     * 获取公司微信模板消息
     * @param ewxiUuid
     * @return
     */
    @Override
    public List<WeixinTemplateMsg> selectTmsgByEwxiUuid(Long ewxiUuid) {
        WeixinTemplateMsg rWtmsP = new WeixinTemplateMsg();
        rWtmsP.setWtmsEwxiUuid(ewxiUuid);
        return this.weixinTemplateMsgMapper.select(rWtmsP);
    }

    /**
     * 获取微信消息模板
     * @param wtmsUuid
     * @return
     */
    @Override
    public WeixinTemplateMsg selectTmsgOne(Long wtmsUuid) {
        WeixinTemplateMsg rWtmsP = new WeixinTemplateMsg();
        rWtmsP.setWtmsUuid(wtmsUuid);
        return this.weixinTemplateMsgMapper.selectOne(rWtmsP);
    }

    /**
     * 更新微信消息模板
     * @param params
     * @return
     */
    @Override
    public int updateWtms(EditWeixinTemplateMsgParams params) {
        WeixinTemplateMsg uWtmsP = new WeixinTemplateMsg();
        uWtmsP.setWtmsUuid(params.getWtmsUuid());
        uWtmsP.setWtmsTemplateId(params.getWtmsTemplateId());
        if (CollectionUtil.isNotEmpty(params.getWtmsToUser())){
            uWtmsP.setWtmsToUser(String.join("#",params.getWtmsToUser()));
        }
        return this.weixinTemplateMsgMapper.updateByExampleSelective(uWtmsP,Example.builder(WeixinTemplateMsg.class)
                .andWhere(Sqls.custom().andEqualTo("wtmsUuid", params.getWtmsUuid())).build());
    }

    /**
     * 增加微信消息模板
     * @param params
     * @return
     */
    @Override
    public int insertWtms(AddWeixinTemplateMsgParams params) {
        WeixinTemplateMsg rWtmsP = new WeixinTemplateMsg();
        rWtmsP.setWtmsEwxiUuid(params.getWtmsEwxiUuid());
        rWtmsP.setWtmsType(params.getWtmsType());
        WeixinTemplateMsg tmp = this.selectTmsgOne(rWtmsP);
        int count = 0;
        if (tmp == null){
            WeixinTemplateMsg cWtmsP = new WeixinTemplateMsg();
            BeanUtil.copyProperties(params,cWtmsP);
            count = this.insertTmsg(cWtmsP);
        }else{
            throw new RuntimeException("增加微信消息模板失败，存在该类型微信信息模板");
        }
        return count;
    }

    /**
     * 获取微信模板消息
     * @param rWtmsP
     * @return
     */
    @Override
    public WeixinTemplateMsg selectTmsgOne(WeixinTemplateMsg rWtmsP) {
        return this.weixinTemplateMsgMapper.selectOne(rWtmsP);
    }

    /**
     * 增加微信模板消息
     * @param cWtmsP
     * @return
     */
    @Override
    public int insertTmsg(WeixinTemplateMsg cWtmsP) {
        return this.weixinTemplateMsgMapper.insert(cWtmsP);
    }
}
