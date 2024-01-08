package com.cyberpeace.auth.security;

import javax.annotation.Resource;

import com.cyberpeace.common.util.ConfigUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import com.cyberpeace.auth.security.handler.*;
import com.cyberpeace.auth.security.service.CustomizeAuthenticationServiceImpl;


/**
 *
 * spring-security配置类
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * 登录成功处理逻辑
	 */
    @Resource
    private CustomizeAuthenticationSuccessHandler authenticationSuccessHandler;

	/**
	 * 登录失败处理逻辑
	 */
    @Resource
    private CustomizeAuthenticationFailureHandler authenticationFailureHandler;

	/**
	 *    权限拒绝处理逻辑
	 */
    @Resource
    private CustomizeAccessDeniedHandler accessDeniedHandler;

	/**
	 * 匿名用户访问无权限资源时的异常
	 */
    @Resource
    private CustomizeAuthenticationEntryPoint authenticationEntryPoint;

	/**
	 * 会话失效(账号被挤下线)处理逻辑
	 */
    @Resource
    private CustomizeSessionInformationExpiredStrategy sessionInformationExpiredStrategy;

	/**
	 * 登出成功处理逻辑登出成功处理逻辑
	 */
    @Resource
    private CustomizeLogoutSuccessHandler logoutSuccessHandler;

	/**
	 * 自定义登录认证
	 */
    @Resource
    private CustomizeAuthenticationServiceImpl userDetailsService;

	/**
	 * 访问决策管理器
	 */
	@Resource
	private CustomizeAccessDecisionManager accessDecisionManager;

	private static final Integer maxNumSession = Integer.parseInt(ConfigUtil.getContextProperty("login.count.max"));

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //配置自定义认证方式
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
		// 自定义登录认证
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		// 加密方式
		daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http相关的配置，包括登入登出、异常处理、会话管理等
        http.cors().and().csrf().disable();
		http.authorizeRequests()
				// 白名单
				.antMatchers(
                        "/ws/*",
                        "/ws/**",
                        "/captcha",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
						"/favicon.ico",
						"/license/**",
						"/auth/user",
						"/system_config/query",
						"/oj/watermark/**",
						"/doc.html")
				.permitAll()
				// 其他的都需要认证
				.anyRequest().authenticated()
				.accessDecisionManager(accessDecisionManager)
                //登出
				.and().logout()
				// 允许所有用户
				.permitAll()
				// 登出成功处理逻辑
				.logoutSuccessHandler(logoutSuccessHandler)
                //登入
				.and().formLogin()
				// 允许所有用户
				.permitAll()
				// 登录成功处理逻辑
				.successHandler(authenticationSuccessHandler)
				// 登录失败处理逻辑
				.failureHandler(authenticationFailureHandler)
                //异常处理(权限拒绝、登录失效等)
				.and().exceptionHandling()
				// 权限拒绝处理逻辑
				.accessDeniedHandler(accessDeniedHandler)
				// 匿名用户访问无权限资源时的异常处理
				.authenticationEntryPoint(authenticationEntryPoint)
                //会话管理
				 .and().sessionManagement()
				// 同一个账号支持同时登录的最大设备数, 如果超过最大设备数，会将最早的登录设备被挤下线
				 .maximumSessions(maxNumSession)
				// true,则阻止用户在达到最大值会话 （int） 时进行身份验证,false:则（默认），允许进行身份验证的用户访问，并且现有用户的会话已过期
//				 maxSessionsPreventsLogin(true).
				// 会话失效(账号被挤下线)处理逻辑
				 .expiredSessionStrategy(sessionInformationExpiredStrategy);
    }

//    /**
//     * 当同一个账号只能同n个设备登录时，超过n的不能登录需要该配置
//     * @return
//     */
//    @Bean
//    HttpSessionEventPublisher httpSessionEventPublisher() {
//        return new HttpSessionEventPublisher();
//    }

    @Bean
    public HttpSessionIdResolver sessionIdResolver() {
        return new HeaderHttpSessionIdResolver("Authorization");
    }
}
