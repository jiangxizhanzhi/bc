package com.cyberpeace.config.aspects;

import java.util.Date;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cs.service.SystemPropertyService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@Aspect
public class ExpirationAspect {

	@Autowired
	private SystemPropertyService systemPropertyService;

	@Pointcut("execution(* com.cyberpeace.scene.controller.*Controller.*(..))")
	public void sceneApi() {

	}

	@Before(value = "sceneApi()")
	public void checkExpiration() {
		if (new Date().after(systemPropertyService.getExpirationDate())) {
			throw new BusinessException(ErrorCode.PLATFORM_EXPIRE);
		}
	}
}
