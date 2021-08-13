package com.cyberpeace.oj.config.auth;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cyberpeace.oj.integratation.service.RemoteUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.cyberpeace.oj.common.constant.WebConstant;
import com.cyberpeace.oj.common.model.UserInfo;
import com.cyberpeace.oj.common.model.UserInfoContext;
import com.cyberpeace.oj.common.util.SpringContextUtil;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String url = ((HttpServletRequest) request).getServletPath();

		if (needAuthentication(url)) {
			// 1、用户认证
			AuthenticationRsp authenticationRsp = doAuthentication(request, response);
			if (!authenticationRsp.isAuthenticationResult()) {
				((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());

				return;
			}

			// 2、认证成功的用户返回
			setUserContext(authenticationRsp.getUserInfo());
		}

		// 3、执行下一个过滤器
		chain.doFilter(request, response);

		System.out.println("gaa");
	}

	@Override
	public void destroy() {
		Filter.super.destroy();
	}

	private boolean needAuthentication(String url) {
		boolean result = true;

		result = result && !StringUtils.startsWith(url, "swagger");

		return result;
	}

	/**
	 * 用户认证
	 * @param request
	 * @return 认证成功返回true
	 */
	private AuthenticationRsp doAuthentication(ServletRequest request, ServletResponse response) {
		AuthenticationRsp authenticationRsp = new AuthenticationRsp();

		String token = ((HttpServletRequest) request).getHeader(WebConstant.AUTHORIZATION_HEADER);
		// if (StringUtils.isBlank(token)) {
		// authenticationRsp.setAuthenticationResult(false);
		// return authenticationRsp;
		// }

		RemoteUserService remoteUserService = SpringContextUtil
				.getBeanByClass(RemoteUserService.class);

		UserInfo userInfo = null;
		try {
			userInfo = remoteUserService.doAuthentication(token);
		} catch (Exception e) {
			log.error("remoteUserService.doAuthentication error, param={}", token, e);

			authenticationRsp.setAuthenticationResult(false);
			return authenticationRsp;
		}

		authenticationRsp.setAuthenticationResult(true);
		authenticationRsp.setUserInfo(userInfo);
		return authenticationRsp;
	}

	private void setUserContext(UserInfo userInfo) {
		UserInfoContext.setUserContext(userInfo);
	}

}

@Data
class AuthenticationRsp {

	private boolean authenticationResult = false;

	private UserInfo userInfo;
}
