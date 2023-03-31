package com.cyberpeace.config.aop;

import java.util.Set;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.annoations.PermissionOperation;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class PermissionOperationAspect {

	@Pointcut("execution(* com.cyberpeace..*.controller..*.*(..))")
	public void requirePermissionOperation() {}

	@Before(value = "requirePermissionOperation()")
	public void requirePermissionOperation(JoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		PermissionOperation requirespermissionsFilter = signature.getMethod()
				.getAnnotation(PermissionOperation.class);

		if (requirespermissionsFilter != null) {
			// 权限校验,包含黑名单抛出异常

			Set<String> permissionOperations = UserInfoContext.getUserContext()
					.getPermissionOperations();

			AuthContextUtils.validateBuiltin(
					permissionOperations.contains(requirespermissionsFilter.value()));

		}

	}
}
