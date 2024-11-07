package com.cyberpeace.rtks.controller;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 非空字符串
 *
 * @author SN
 * @date 2024/11/07
 */
@Documented
@Constraint(validatedBy = NotEmptyStringValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface NotEmptyString {

	/**
	 * 消息
	 *
	 * @return {@code String }
	 */
	String message() default "String field cannot be empty string, but null is accepted";

	/**
	 * 组
	 *
	 * @return {@code Class<?>[] }
	 */
	Class<?>[] groups() default {};

	/**
	 * 有效载荷
	 *
	 * @return {@code Class<? extends Payload>[] }
	 */
	Class<? extends Payload>[] payload() default {};

}
