package com.cyberpeace.situation.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyberpeace.integratation.model.manage.ServiceLogContext;
import com.cyberpeace.integratation.service.RemoteManageService;

@Aspect
@Component
public class AuditLoggerAspect {

	@Autowired
	private RemoteManageService remoteManageService;

	@AfterReturning("@annotation(AuditLogger)")
	public void writeLog(JoinPoint joinPoint) {

		// 提取注解的参数
		AuditLogger auditLogger = ((MethodSignature) joinPoint.getSignature()).getMethod()
				.getAnnotation(AuditLogger.class);

		// 异步输出审计日志
		remoteManageService
				.writeLog(new ServiceLogContext(auditLogger.subModule(), auditLogger.type()));
	}
}
