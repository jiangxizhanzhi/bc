package com.cyberpeace.config.auth;

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

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * shiro 配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Configuration
public class ShiroConfig {

	/**
	 * 获取经理
	 *
	 * @param realm 领域
	 * @return {@code DefaultWebSecurityManager }
	 * @throws
	 * @see BasicValidationRealm
	 * @see DefaultWebSecurityManager
	 */
	@Bean("securityManager")
	public DefaultWebSecurityManager getManager(BasicValidationRealm realm) {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		// 使用自己的realm
		manager.setRealm(realm);

		DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
		DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
		defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
		subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
		manager.setSubjectDAO(subjectDAO);

		return manager;
	}

	/**
	 * 厂
	 *
	 * @param securityManager 安全管理器
	 * @return {@code ShiroFilterFactoryBean }
	 * @throws
	 * @see DefaultWebSecurityManager
	 * @see ShiroFilterFactoryBean
	 */
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

		Map<String, Filter> filterMap = new HashMap<>();
		filterMap.put("jwt", new JwtFilter());
		factoryBean.setFilters(filterMap);

		factoryBean.setSecurityManager(securityManager);
		factoryBean.setUnauthorizedUrl("/401");

		Map<String, String> filterRuleMap = new HashMap<>();
		// 不需要校验登陆态的接口在这边配置
		filterRuleMap.put("/**", "jwt");

		factoryBean.setFilterChainDefinitionMap(filterRuleMap);
		return factoryBean;
	}

	/**
	 * 默认顾问自动代理造物主
	 *
	 * @return {@code DefaultAdvisorAutoProxyCreator }
	 * @throws
	 * @see DefaultAdvisorAutoProxyCreator
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
	 * 生命周期 Bean 后处理器
	 *
	 * @return {@code LifecycleBeanPostProcessor }
	 * @throws
	 * @see LifecycleBeanPostProcessor
	 */
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 授权属性源顾问
	 *
	 * @param securityManager 安全管理器
	 * @return {@code AuthorizationAttributeSourceAdvisor }
	 * @throws
	 * @see DefaultWebSecurityManager
	 * @see AuthorizationAttributeSourceAdvisor
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor
			authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
}
