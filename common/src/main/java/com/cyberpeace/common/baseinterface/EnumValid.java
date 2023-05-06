package com.cyberpeace.common.baseinterface;

import java.lang.annotation.*;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.cyberpeace.common.model.EnumValidator;

/**
 * 枚举有效
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = { EnumValidator.class })
public @interface EnumValid {

	String message() default "";

	// 作用参考@Validated和@Valid的区别
	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	/**
	 * 目标
	 * 目标枚举类
	 *
	 * @return {@link Class}<{@link ?}>
	 */
	Class<?> target() default Class.class;

	/**
	 * 忽略空
	 * 是否忽略空值
	 *
	 * @return boolean
	 */
	boolean ignoreEmpty() default true;
}