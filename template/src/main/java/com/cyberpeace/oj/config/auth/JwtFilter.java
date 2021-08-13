package com.cyberpeace.oj.config.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cyberpeace.oj.common.constant.WebConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

	/**
	 * 判断用户是否想要登入。
	 * 检测header里面是否包含Authorization字段即可
	 */
	@Override
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
		String url = ((HttpServletRequest) request).getServletPath();

		return needAuthentication(url);
	}

	/**
	 *
	 */
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response)
			throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String jwtToken = httpServletRequest.getHeader(WebConstant.AUTHORIZATION_HEADER);

		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(jwtToken, jwtToken);

		// 提交给realm进行登入，如果错误他会抛出异常并被捕获
		getSubject(request, response).login(usernamePasswordToken);
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

	/**
	 * 对跨域提供支持
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-control-Allow-Origin",
				httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods",
				"GET,POST,OPTIONS,PUT,DELETE");
		httpServletResponse.setHeader("Access-Control-Allow-Headers",
				httpServletRequest.getHeader("Access-Control-Request-Headers"));

		// 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		return super.preHandle(request, response);
	}

	private boolean needAuthentication(String url) {
		boolean result = true;

		result = result && !StringUtils.startsWith(url, "/swagger");

		return result;
	}
}