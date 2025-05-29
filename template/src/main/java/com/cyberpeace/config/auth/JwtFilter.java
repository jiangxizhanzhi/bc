package com.cyberpeace.config.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;

import com.cyberpeace.common.constant.WebConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * JWT过滤器
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see BasicHttpAuthenticationFilter
 * @since 1.0.0
 */
/**
 * ### 分析 
 * #### 类概述 
 * - **类名**: `JwtFilter` 
 * - **功能**: 该类继承自`BasicHttpAuthenticationFilter`，用于处理基于JWT（JSON Web Token）的身份验证。 
 * - **日志**: 使用了Lombok的`@Slf4j`注解，方便记录日志。 
 * #### 主要方法分析 
 * 1. **`setAuthzScheme(String authzScheme)`** 
 * - **功能**: 设置认证方案为"JWT"。 
 * - **覆盖父类方法**: 覆盖了`BasicHttpAuthenticationFilter`中的同名方法。 
 * - **用途**: 指定当前过滤器使用的认证方案。 
 * 2. **`getPrincipalsAndCredentials(String scheme, String realJwt)`** 
 * - **功能**: 获取用户名和JWT令牌。 
 * - **实现**: 返回一个包含空字符串和真实JWT的数组。 
 * - **注意**: 默认实现中并未对JWT进行解码，而是将解码工作交给自定义Realm。 
 * 3. **`executeLogin(ServletRequest request, ServletResponse response)`** 
 * - **功能**: 执行用户登录操作。 
 * - **实现**: 
 * - 调用`getSubject`获取当前用户主体。 
 * - 使用`createToken`创建认证令牌并提交给Realm进行身份验证。 
 * - **返回值**: 如果登录成功，返回`true`；否则会抛出异常。 
 * 4. **`isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)`** 
 * - **功能**: 判断请求是否允许访问。 
 * - **逻辑**: 
 * - 如果是登录尝试，调用`executeLogin`执行登录。 
 * - 如果登录失败，记录错误日志并设置HTTP状态码为`401 Unauthorized`。 
 * - **返回值**: 总是返回`true`，表示允许访问。 
 * 5. **`isLoginAttempt(ServletRequest request, ServletResponse response)`** 
 * - **功能**: 判断当前请求是否是登录尝试。 
 * - **实现**: 调用父类的同名方法，未做任何修改。 
 * #### 总结 
 * - **核心功能**: 该过滤器主要用于处理JWT认证，确保只有经过身份验证的用户才能访问受保护的资源。 
 * - **优点**: 
 * - 使用了Shiro框架的`BasicHttpAuthenticationFilter`作为基础。 
 * - 日志记录便于调试和监控。 
 * - **改进点**: 
 * - 当前实现中，`executeLogin`总是返回`true`，可能需要根据实际业务需求调整。 
 * - JWT解码和验证逻辑可以进一步增强，例如添加更多的校验规则。 
**/
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

	/**
	 * 设置授权方案
	 *
	 * @param authzScheme Authz 方案
	 * @throws
	 * @see String
	 */
	@Override
	public void setAuthzScheme(String authzScheme) {
		super.setAuthzScheme("JWT");
	}

	/**
	 * 获取校长和凭据
	 *
	 * @param scheme  方案
	 * @param realJwt 真实JWT
	 * @return 字符串[]
	 * @throws
	 * @see String
	 * @see String
	 * @see String[]
	 */
	@Override
	protected String[] getPrincipalsAndCredentials(String scheme, String realJwt) {
		// 默认实现会对jwttoken进行解码
		// 这个工作通过自定义realm交给中台
		return new String[] { "", realJwt };
	}

	/**
	 * 执行登录
	 *
	 * @param request  请求
	 * @param response 响应
	 * @return boolean
	 * @throws @throws Exception 例外
	 * @see ServletRequest
	 * @see ServletResponse
	 * @see boolean
	 */
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response)
			throws Exception {
		// 提交给realm进行登入，如果错误他会抛出异常并被捕获
		getSubject(request, response).login(createToken(request, response));
		// 如果没有抛出异常则代表登入成功，返回true
		return true;
	}

	/**
	 * 是否访问允许
	 *
	 * @param request     请求
	 * @param response    响应
	 * @param mappedValue 映射值
	 * @return boolean
	 * @throws
	 * @see ServletRequest
	 * @see ServletResponse
	 * @see Object
	 * @see boolean
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
			Object mappedValue) {
		if (isLoginAttempt(request, response)) {
			try {
				executeLogin(request, response);
			} catch (Exception e) {
				log.error("user login failed, token={}",
						((HttpServletRequest) request).getHeader(WebConstant.AUTHORIZATION_HEADER));

				((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());
			}
		}

		return true;
	}

	/**
	 * 是否登录尝试
	 *
	 * @param request  请求
	 * @param response 响应
	 * @return boolean
	 * @throws
	 * @see ServletRequest
	 * @see ServletResponse
	 * @see boolean
	 */
	@Override
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {

		return super.isLoginAttempt(request, response);
	}
}
