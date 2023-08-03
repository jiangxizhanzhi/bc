package com.cyberpeace.config.aop;

import java.lang.reflect.Method;
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
			Class<?> targetCls=joinPoint.getTarget().getClass();
			//获取方法签名(通过此签名获取目标方法信息)
			MethodSignature ms=(MethodSignature)joinPoint.getSignature();
			//获取目标方法上的注解指定的操作名称
			Method targetMethod=
					targetCls.getDeclaredMethod(
							ms.getName(),
							ms.getParameterTypes());
			System.out.println("targetMethod="+targetMethod);
			//获取目标方法名(目标类型+方法名)
			String targetClsName=targetCls.getName();
			String targetObjectMethodName=targetClsName+"."+ms.getName();
			if (("com.cyberpeace.cr.evaluation.controller.EvalAlgorithmAdminController".equals(targetClsName) ||
					"com.cyberpeace.cr.evaluation.controller.EvalIndicatorAdminController".equals(targetClsName) ||
					"com.cyberpeace.cr.evaluation.controller.EvalSystemIndexAdminController".equals(targetClsName))
					&& (targetObjectMethodName.contains("edit") || targetObjectMethodName.contains("batchDelete"))){
				return;
			}
			Set<String> permissionOperations = UserInfoContext.getUserContext()
					.getPermissionOperations();

			AuthContextUtils.validateBuiltin(
					permissionOperations.contains(requirespermissionsFilter.value()));

		}

	}
}
