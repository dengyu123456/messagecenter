/**
 * Copyright (c) 2017 ZHONGHENG, Inc. All rights reserved.
 * This software is the confidential and proprietary information of
 * ZHONGHENG, Inc. You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with ZHONGHENG.
 */
package com.zhkj.purchase.common.shiro;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Des: shiro 配置类
 * ClassName: ShiroConfig
 * Author: biqiang2017@163.com
 * Date: 2018/7/3
 * Time: 15:18
 */
@Configuration
public class ShiroConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroConfig.class);

    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(StatelessAuthorizingRealm realm, StatelessDefaultSubjectFactory subjectFactory) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //配置自定义的realm
        manager.setRealm(realm);

        //配置shiro session关闭
        manager.setSubjectFactory(subjectFactory);

        //配置主体dao
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        //添加自定义过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        //添加系统权限过滤器
        filterMap.put("custom_shiro_filter", new StatelessHttpAuthenticationFilter());
        //添加swagger过滤器
        filterMap.put("custom_swagger_filter", new StatelessBasicHttpAuthenticationFilter());
        factoryBean.setFilters(filterMap);

        factoryBean.setSecurityManager(securityManager);

        Map<String, String> filterRuleMap = new HashMap<>();
        //系统验证过滤地址
        filterRuleMap.put("/**", "custom_shiro_filter");
        //swagger验证过滤地址
        filterRuleMap.put("/swagger-ui.html", "custom_swagger_filter");
        filterRuleMap.put("/v2/api-docs", "custom_swagger_filter");

        //配置无需过滤的访问地址
        //系统
        filterRuleMap.put("/api/system/login","anon");
        filterRuleMap.put("/api/system/ref/login","anon");
        filterRuleMap.put("/code/image","anon");
        //swagger
        filterRuleMap.put("/swagger-resources/**", "anon");
        filterRuleMap.put("/webjars/springfox-swagger-ui/**", "anon");

        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
