package com.cyberpeace.config.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.annoations.PermissionOperation;
import com.cyberpeace.common.util.AuthContextUtils;

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
			String loginUserPermission = "oj-comment-add\n" + "cms_course_dump\n cms_courses_category"
					+ "cms_course_load\n" + "cms_exercises_dump\n" + "cms_exercises_load\n"
					+ "cms_theory_import\n" + "cms_practice_category_add\n"
					+ "cms_practice_category_update\n" + "cms_practice_category_delete\n"
					+ "cms_writeup_public\n" + "cms_cr_mission_scheme_dump\n"
					+ "cms_cr_mission_scheme_load\n" + "cms_cr_set_share\n" + "cms_cr_load\n"
					+ "cms_cr_dump\n" + "cms_tool_category\n" + "cms_rtks_frame\n"
					+ "cms_rtks_knowledge\n" + "cms_rtks_skill\n" + "cms_rtks_task\n"
					+ "cms_rtks_role" + "cms_lesson_import" + "cms_attack_frame\n"
					+ "cms_engage_frame cms_cr_evaluation_algorithm cms_cr_evaluation_indicator cms_cr_evaluation_indicator_system cms_tool";

			AuthContextUtils.validateBuiltin(
					loginUserPermission.contains(requirespermissionsFilter.value()));

		}

	}
}
