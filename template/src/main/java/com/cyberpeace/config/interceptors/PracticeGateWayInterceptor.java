package com.cyberpeace.config.interceptors;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.cyberpeace.common.annoations.AuthOjKey;
import com.cyberpeace.common.annoations.AuthKeyUser;
import com.cyberpeace.common.annoations.AuthUser;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.integratation.service.RemoteUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * 练习门道路拦截 器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see HandlerInterceptor
 * @since 1.0.0
 */
@Slf4j
@Component
public class PracticeGateWayInterceptor implements HandlerInterceptor {

	/**
	 * 远程用户Service
	 *
	 * @see RemoteUserService
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 前置处理
	 *
	 * @param request  请求
	 * @param response 响应
	 * @param handler  处理器
	 * @return boolean
	 * @throws @throws Exception 例外
	 * @see HttpServletRequest
	 * @see HttpServletResponse
	 * @see Object
	 * @see boolean
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {

		if (handler instanceof HandlerMethod) {
			Method method = ((HandlerMethod) handler).getMethod();
			AuthOjKey authKey = method.getAnnotation(AuthOjKey.class);
			AuthUser authUser = method.getAnnotation(AuthUser.class);
			AuthKeyUser authKeyUser = null;
			if (null == authKey && null == authUser) {
				authKeyUser = method.getAnnotation(AuthKeyUser.class);
			}

			if (null == authKeyUser && authKey == null && authUser == null) {
				authKeyUser = method.getDeclaringClass().getAnnotation(AuthKeyUser.class);
			}

			if (null == authKeyUser) {
				authKey = method.getDeclaringClass().getAnnotation(AuthOjKey.class);
				authUser = method.getDeclaringClass().getAnnotation(AuthUser.class);
			} else {
				authKey = authKeyUser.annotationType().getAnnotation(AuthOjKey.class);
				authUser = authKeyUser.annotationType().getAnnotation(AuthUser.class);
			}

			if (null != authKey) {
				validateKey(request);
			}

			if (null != authUser) {
				UserInfo userInfo = validateUser(authUser, request);
				UserInfoContext.setUserContext(userInfo);
			}
		}

		return true;
	}

	/**
	 * 接口认证(key获取方式后续可能调整)
	 *
	 * @param request 请求
	 * @throws
	 * @see HttpServletRequest
	 */
	private void validateKey(HttpServletRequest request) {
		String key = request.getHeader(WebConstant.OJ_HEADER_KEY);
		if (StringUtils.isBlank(key) || !key.equals(WebConstant.OJ_HEADER_KEY_VALUE)) {
			log.error("key [{}] is not authenticated", key);
			throw new UnauthenticatedException();
		}
	}

	/**
	 * 用户认证
	 *
	 * @param authUser
	 * @param request
	 * @return
	 * @throws
	 * @see AuthUser
	 * @see HttpServletRequest
	 * @see UserInfo
	 */
	private UserInfo validateUser(AuthUser authUser, HttpServletRequest request) {
		String authName = authUser.name();
		String authParam = request.getParameter(authName);
		if (StringUtils.isBlank(authParam)) {
			log.error("Parameter not found [{}]", authName);
			throw new UnauthenticatedException();
		}

		UserInfo userInfo = remoteUserService.getById(authParam);
		if (null == userInfo) {
			log.error("User does not exist！user_id=[{}]", authParam);
			throw new BusinessException(ErrorCode.USER_INFO_MISSING);
		}

		if (StringUtils.isBlank(userInfo.getOrganizationId())) {
			log.error("User organization is blank! user_id=[{}], user_name=[{}]",
					userInfo.getUserId(), userInfo.getUserName());
			throw new BusinessException(OjBusinessErrorCode.ORG_INFORMATION_NOT_NULL);
		}

		return userInfo;
	}
}
