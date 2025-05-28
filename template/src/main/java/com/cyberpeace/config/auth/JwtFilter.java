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
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

	@Override
	public void setAuthzScheme(String authzScheme) {
		super.setAuthzScheme("JWT");
	}

	@Override
	protected String[] getPrincipalsAndCredentials(String scheme, String realJwt) {
		// 默认实现会对jwttoken进行解码
		// 这个工作通过自定义realm交给中台
		return new String[] { "", realJwt };
	}

	/**
	 *
	 */
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response)
			throws Exception {
		// 提交给realm进行登入，如果错误他会抛出异常并被捕获
		getSubject(request, response).login(createToken(request, response));
		// 如果没有抛出异常则代表登入成功，返回true
		return true;
	}

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

	@Override
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {

		return super.isLoginAttempt(request, response);
	}
}