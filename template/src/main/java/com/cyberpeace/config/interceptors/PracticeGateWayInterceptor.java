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
 * @date 2025/05/28
 * @see HandlerInterceptor
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个Spring MVC拦截器`PracticeGateWayInterceptor`，用于在请求处理之前进行权限验证。以下是代码的主要功能和结构分析： 
 * ### 主要功能： 
 * 1. **权限验证**：拦截HTTP请求，根据方法或类上的注解（如`@AuthOjKey`、`@AuthUser`、`@AuthKeyUser`）对请求进行不同的权限检查。 
 * 2. **键值验证**：通过请求头中的特定键值来验证请求的有效性。 
 * 3. **用户信息验证**：通过用户参数从远程服务中获取并验证用户信息。 
 * ### 代码结构与实现细节： 
 * 1. **注解解析**： 
 * - 拦截器首先判断处理器是否为`HandlerMethod`类型，如果是，则解析方法级别的注解。 
 * - 如果方法上没有相关注解，则尝试解析类级别的注解。 
 * - 根据注解类型（`AuthOjKey`、`AuthUser`、`AuthKeyUser`），执行相应的验证逻辑。 
 * 2. **键值验证**： 
 * - `validateKey`方法从请求头中提取指定键值，并与预设的默认值进行比较。如果键值为空或不匹配，则抛出`UnauthenticatedException`异常。 
 * 3. **用户信息验证**： 
 * - `validateUser`方法根据`AuthUser`注解提取请求参数，调用远程服务获取用户信息。 
 * - 如果用户信息不存在或组织ID为空，则抛出对应的业务异常。 
 * 4. **日志记录**： 
 * - 使用`slf4j`记录错误日志，便于排查问题。 
 * ### 总结： 
 * 该拦截器的核心作用是基于注解的权限控制，确保只有合法的请求能够继续处理。它通过多种方式（键值验证、用户信息验证）保障了系统的安全性，同时结合日志记录增强了可维护性和调试能力。 
**/
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
	 * 前置handle
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

