package com.cyberpeace.config.interceptors;

import com.cyberpeace.common.annoations.AuthKeyUser;
import com.cyberpeace.common.annoations.AuthOjKey;
import com.cyberpeace.common.annoations.AuthUser;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 日志注解，用于简化日志记录的配置
 * Spring组件注解，标识该类为Spring管理的组件
 */
@Slf4j
@Component
public class PracticeGateWayInterceptor implements HandlerInterceptor {

	/**
	 * 自动注入远程用户服务，用于用户信息的获取
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 在请求处理之前进行拦截处理
	 *
	 * @param request  HTTP请求
	 * @param response HTTP响应
	 * @param handler  处理请求的方法
	 * @return boolean 表示是否继续执行其他拦截器和处理器
	 *
	 * 该方法主要用于认证和授权，通过检查请求中的key和用户信息来确保请求的合法性
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {

		// 判断处理方法是否为HandlerMethod类型
		if (handler instanceof HandlerMethod) {
			Method method = ((HandlerMethod) handler).getMethod();
			// 获取方法级别的认证注解
			AuthOjKey authKey = method.getAnnotation(AuthOjKey.class);
			AuthUser authUser = method.getAnnotation(AuthUser.class);
			AuthKeyUser authKeyUser = null;
			// 如果方法级别没有认证注解，则尝试获取类级别的认证注解
			if (null == authKey && null == authUser) {
				authKeyUser = method.getAnnotation(AuthKeyUser.class);
			}

			// 如果既没有方法级别的认证注解也没有类级别的认证注解，则尝试获取声明类的认证注解
			if (null == authKeyUser && authKey == null && authUser == null) {
				authKeyUser = method.getDeclaringClass().getAnnotation(AuthKeyUser.class);
			}

			// 根据认证注解的获取结果进行不同的处理
			if (null == authKeyUser) {
				authKey = method.getDeclaringClass().getAnnotation(AuthOjKey.class);
				authUser = method.getDeclaringClass().getAnnotation(AuthUser.class);
			} else {
				authKey = authKeyUser.annotationType().getAnnotation(AuthOjKey.class);
				authUser = authKeyUser.annotationType().getAnnotation(AuthUser.class);
			}

			// 如果存在AuthOjKey注解，则进行key的验证
			if (null != authKey) {
				validateKey(request);
			}

			// 如果存在AuthUser注解，则进行用户的验证
			if (null != authUser) {
				UserInfo userInfo = validateUser(authUser, request);
				UserInfoContext.setUserContext(userInfo);
			}
		}

		// 继续执行其他拦截器和处理器
		return true;
	}

	/**
	 * 接口认证(key获取方式后续可能调整)
	 *
	 * @param request HTTP请求
	 */
	private void validateKey(HttpServletRequest request) {
		String key = request.getHeader(WebConstant.OJ_HEADER_KEY);
		// 验证key是否为空或与预期值匹配
		if (StringUtils.isBlank(key) || !key.equals(WebConstant.OJ_HEADER_KEY_VALUE)) {
			log.error("key [{}] is not authenticated", key);
			throw new UnauthenticatedException();
		}
	}

	/**
	 * 用户认证
	 *
	 * @param authUser AuthUser注解
	 * @param request  HTTP请求
	 * @return UserInfo 用户信息
	 */
	private UserInfo validateUser(AuthUser authUser, HttpServletRequest request) {
		String authName = authUser.name();
		String authParam = request.getParameter(authName);
		// 验证用户认证参数是否存在
		if (StringUtils.isBlank(authParam)) {
			log.error("Parameter not found [{}]", authName);
			throw new UnauthenticatedException();
		}

		// 通过远程用户服务获取用户信息
		UserInfo userInfo = remoteUserService.getById(authParam);
		// 验证用户信息是否存在
		if (null == userInfo) {
			log.error("User does not exist！user_id=[{}]", authParam);
			throw new BusinessException(ErrorCode.USER_INFO_MISSING);
		}

		// 验证用户组织信息是否为空
		if (StringUtils.isBlank(userInfo.getOrganizationId())) {
			log.error("User organization is blank! user_id=[{}], user_name=[{}]",
					userInfo.getUserId(), userInfo.getUserName());
			throw new BusinessException(OjBusinessErrorCode.ORG_INFORMATION_NOT_NULL);
		}

		// 返回用户信息
		return userInfo;
	}
}

