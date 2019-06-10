/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.purchase.common.shiro;

import com.alibaba.fastjson.JSON;
import com.zhkj.purchase.base.TokenUtil;
import com.zhkj.purchase.base.TokenVO;
import com.zhkj.purchase.common.redis.RedisUtil;
import com.zhkj.purchase.common.utils.MD5Util;
import com.zhkj.purchase.config.PurchaseConfig;
import com.zhkj.purchase.system.domain.SystemUser;
import com.zhkj.purchase.system.service.SystemUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Des: 无状态realm
 * ClassName: CustomAuthorizingRealm
 * Author: biqiang2017@163.com
 * Date: 2018/7/3
 * Time: 15:11
 */
@Component
public class StatelessAuthorizingRealm extends AuthorizingRealm {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatelessAuthorizingRealm.class);
    @Autowired
    private SystemUserService systemUserService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private PurchaseConfig purchaseConfig;

    @Override
    public String getName() {
        return super.getName();
    }

    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof Token;
    }

    /**
     * 当需要检验权限的时候调用改方法
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        LOGGER.info("doGetAuthorizationInfo----->权限认证");
        return null;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        LOGGER.info("doGetAuthenticationInfo----->认证");
        LOGGER.info("doGetAuthenticationInfo----->" + JSON.toJSONString(authenticationToken));
        Token tokenOb = (Token) authenticationToken;

        if (!StringUtils.isEmpty(tokenOb.getToken())) {//token接口验证
            String token = tokenOb.getToken();
            // 解密获得username，用于和数据库进行对比
            TokenVO tokenVO = TokenUtil.getToken(token);
            if (tokenVO == null || StringUtils.isEmpty(tokenVO.getUuid())) {
                throw new AuthenticationException("无效Token，请重新登陆");
            }

            SystemUser user = redisUtil.getObject(tokenVO.getUuid(), SystemUser.class);
            if (user == null) {
                throw new AuthenticationException("无效Token，请重新登陆");
            } else if (StringUtils.isEmpty(user.getSuseOnlineTag()) || !user.getSuseOnlineTag().equals(tokenVO.getTag())) {
                throw new AuthenticationException("账号在其他地方登陆");
            } else {
                redisUtil.updateExpire(tokenVO.getUuid(), purchaseConfig.getExpireTime());
                return new SimpleAuthenticationInfo(user, token, "custom_realm");
            }
        } else {//basic接口验证
            SystemUser userParams = new SystemUser();
            userParams.setSuseUserName(String.valueOf(tokenOb.getPrincipal()));
            String pwd = new String((char[]) tokenOb.getCredentials());
            userParams.setSusePassword(MD5Util.encrytor(pwd));
            SystemUser user = this.systemUserService.selectOne(userParams);
            if (user == null) {
                throw new AuthenticationException("用户不存在");
            }
            return new SimpleAuthenticationInfo(tokenOb.getPrincipal(), tokenOb.getCredentials(), "custom_realm");
        }
    }
}
