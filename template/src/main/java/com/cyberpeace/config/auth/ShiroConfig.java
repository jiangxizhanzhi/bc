/**
 * 该代码是一个用于配置 Apache Shiro 安全框架的 Spring 配置类 `ShiroConfig`。以下是对其主要功能和结构的简要分析： 
 * 1. **核心功能**： 
 * - 配置了 Shiro 的安全管理器 (`DefaultWebSecurityManager`) 和过滤器工厂 (`ShiroFilterFactoryBean`)。 
 * - 自定义了过滤规则，支持基于 JWT 的认证（通过 `JwtFilter`）。 
 * - 禁用了会话存储（`sessionStorageEnabled=false`），以适应无状态的 RESTful API 场景。 
 * 2. **关键组件**： 
 * - **安全管理器 (`DefaultWebSecurityManager`)**：设置自定义的 Realm (`BasicValidationRealm`) 并禁用会话存储。 
 * - **过滤器工厂 (`ShiroFilterFactoryBean`)**：定义了过滤规则和自定义过滤器（如 JWT 过滤器）。 
 * - **JWT 过滤器**：处理基于 JWT 的身份验证逻辑。 
 * - **Spring 集成**： 
 * - `DefaultAdvisorAutoProxyCreator`：用于 AOP 代理支持。 
 * - `LifecycleBeanPostProcessor`：管理 Shiro 组件的生命周期。 
 * - `AuthorizationAttributeSourceAdvisor`：提供授权逻辑支持。 
 * 3. **设计特点**： 
 * - **无状态设计**：通过禁用会话存储，适合微服务架构下的无状态认证机制。 
 * - **扩展性**：支持灵活配置过滤规则，便于添加新的过滤器或修改现有规则。 
 * - **模块化**：各组件职责清晰，易于维护和扩展。 
 * 4. **适用场景**： 
 * - 适用于基于 JWT 的分布式系统或微服务架构，需要实现无状态的安全认证和授权。 
 * 总结：该配置类实现了 Shiro 在 Spring 环境中的集成，重点在于 JWT 身份验证和无状态会话管理，适合现代 Web 应用的安全需求。 
**/
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
 * shiro 配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Configuration
public class ShiroConfig {

	/**
	 * 获取经理
	 *
	 * @param realm 领域
	 * @return 默认Websecurity经理
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
	 * @return 白过滤器厂bean
	 * @throws
	 * @see DefaultWebSecurityManager
	 * @see ShiroFilterFactoryBean
	 */
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

		Map<String, Filter> filterMap = new HashMap<>();
		// filterMap.put("anon", new AnonymousFilter());
		filterMap.put("jwt", new JwtFilter());
		factoryBean.setFilters(filterMap);

		factoryBean.setSecurityManager(securityManager);
		factoryBean.setUnauthorizedUrl("/401");

		Map<String, String> filterRuleMap = new HashMap<>();
		// 不需要校验登陆态的接口在这边配置
		// filterRuleMap.put("/swagger-ui", "anon");
		filterRuleMap.put("/**", "jwt");

		factoryBean.setFilterChainDefinitionMap(filterRuleMap);
		return factoryBean;
	}

	/**
	 * 默认顾问自动代理造物主
	 *
	 * @return 默认顾问自动代理造物主
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
	 * @return 生命周期 Bean 后处理器
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
	 * @return 授权属性源顾问
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
