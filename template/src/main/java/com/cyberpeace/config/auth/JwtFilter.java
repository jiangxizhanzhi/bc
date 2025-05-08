package com.cyberpeace.config.auth;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cyberpeace.common.model.user.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;

import com.cyberpeace.common.constant.WebConstant;

import lombok.extern.slf4j.Slf4j;

/**
 * JWT过滤器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BasicHttpAuthenticationFilter
 * @since 1.0.0
 */
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
	 * @return {@code String[] }
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
		String token = ((HttpServletRequest) request).getHeader(WebConstant.AUTHORIZATION_HEADER);
		// 提交给realm进行登入，如果错误他会抛出异常并被捕获
		getSubject(request, response).login(createToken(request, response));
		// 如果没有抛出异常则代表登入成功，返回true
		// 获取UserInfo对象并设置token
		Object principal = SecurityUtils.getSubject().getPrincipal();
		if (principal instanceof UserInfo) {
			UserInfo userInfo = (UserInfo) principal;
			userInfo.setToken(token);
		}
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