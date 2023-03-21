package com.cyberpeace.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.annoations.PermissionOperation;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class PermissionOperationAspect {

	@Pointcut("execution(* com.cyberpeace..*.controller..*.*(..))")
	public void Requirespermissions() {}

	@Before(value = "Requirespermissions()")
	public void Requirespermissions(JoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		PermissionOperation requirespermissionsFilter = signature.getMethod()
				.getAnnotation(PermissionOperation.class);

		if (requirespermissionsFilter != null) {
			// 权限校验,包含黑名单抛出异常
			String loginUserPermission = "lesson edit lesson add cms_tool_category";
			if (loginUserPermission.contains(requirespermissionsFilter.value())) {
				throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
				// todo 抛出试用异常
			}
		}

	}
}
