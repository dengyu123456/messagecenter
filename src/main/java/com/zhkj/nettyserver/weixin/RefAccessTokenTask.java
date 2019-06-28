/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.nettyserver.weixin;

import com.zhkj.nettyserver.common.util.AESUtil;
import com.zhkj.nettyserver.common.util.CollectionUtil;
import com.zhkj.nettyserver.common.util.StringUtil;
import com.zhkj.nettyserver.weixin.domain.EnterpriseWeixin;
import com.zhkj.nettyserver.weixin.service.WeixinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Des:
 * ClassName: RefAccessTokenTask
 * Author: biqiang2017@163.com
 * Date: 2019/6/24
 * Time: 15:54
 */
@Component
public class RefAccessTokenTask {
    @Autowired
    private WeixinService weixinService;
    @Autowired
    private WeiXin weiXin;

    @Scheduled(cron = "0 */100 * * * ?")
    public void rrfAccessToken(){
        List<EnterpriseWeixin> eweiList = this.weixinService.selectAll();
        if (CollectionUtil.isNotEmpty(eweiList)){
            EnterpriseWeixin uEwxiP = new EnterpriseWeixin();
            for (EnterpriseWeixin item : eweiList){
                String accessToken = this.weiXin.getAccessToken(AESUtil.decryptor(item.getEwxiAppid()),AESUtil
                        .decryptor(item.getEwxiSecret()));
                if (StringUtil.isNotBlank(accessToken)){
                    uEwxiP.setEwxiUuid(item.getEwxiUuid());
                    uEwxiP.setEwxiLastTime(System.currentTimeMillis());
                    uEwxiP.setEwxiAccessToken(accessToken);
                    this.weixinService.update(uEwxiP);
                }
            }
        }
    }

}
