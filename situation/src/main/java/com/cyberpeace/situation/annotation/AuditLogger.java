package com.cyberpeace.situation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cyberpeace.integratation.model.manage.ServiceLogContext;

/**
 * 审计日志记录器
 *
 * @author chenhy
 * @date 2023/05/30
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuditLogger {

	ServiceLogContext.SubModule subModule();

	ServiceLogContext.Type type();
}
