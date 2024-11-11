package com.cyberpeace.config.auth;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * Shiro配置类，用于定义Shiro相关的Bean和配置
 */
@Configuration
public class ShiroConfig {

    /**
     * 创建DefaultWebSecurityManager实例并配置Realm
     *
     * @param realm 自定义的Realm实例，用于用户身份验证和授权
     * @return 配置好的DefaultWebSecurityManager实例
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(BasicValidationRealm realm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自己的realm
        manager.setRealm(realm);

        // 禁用会话存储，根据实际需求配置
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }

    /**
     * 创建ShiroFilterFactoryBean实例，用于定义过滤器规则
     *
     * @param securityManager SecurityManager实例，用于管理安全策略
     * @return 配置好的ShiroFilterFactoryBean实例
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        // 定义过滤器映射，可以根据需要添加其他过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        // filterMap.put("anon", new AnonymousFilter());
        filterMap.put("jwt", new JwtFilter());
        factoryBean.setFilters(filterMap);

        factoryBean.setSecurityManager(securityManager);
        factoryBean.setUnauthorizedUrl("/401");

        // 定义过滤器规则，不需要校验登陆态的接口在这边配置
        Map<String, String> filterRuleMap = new HashMap<>();
        // filterRuleMap.put("/swagger-ui", "anon");
        filterRuleMap.put("/**", "jwt");

        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    /**
     * 创建DefaultAdvisorAutoProxyCreator实例，用于自动代理
     *
     * @return 配置好的DefaultAdvisorAutoProxyCreator实例
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator;
        defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    /**
     * 创建LifecycleBeanPostProcessor实例，用于管理Shiro Bean的生命周期
     *
     * @return LifecycleBeanPostProcessor实例
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 创建AuthorizationAttributeSourceAdvisor实例，用于集成Shiro和Spring AOP进行权限控制
     *
     * @param securityManager SecurityManager实例，用于管理安全策略
     * @return 配置好的AuthorizationAttributeSourceAdvisor实例
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor
            authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}

