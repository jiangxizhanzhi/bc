package com.cyberpeace.common.baseinterface;



import com.cyberpeace.common.model.EnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 枚举有效
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Annotation
 * @since 1.0.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValidator.class})
public @interface EnumValid {

	/**
	 * 消息
	 *
	 * @return {@code String }
	 * @throws
	 * @see String
	 */
	String message() default "";

	/**
	 * 组
	 *
	 * @return {@code Class<?>[] }
	 * @throws
	 * @see Class<?>[]
	 */ // 作用参考@Validated和@Valid的区别
    Class<?>[] groups() default {};

	/**
	 * 有效载荷
	 *
	 * @return {@code Class<? extends Payload>[] }
	 * @throws
	 * @see Class<? extends Payload>[]
	 */
	Class<? extends Payload>[] payload() default {};

	/**
	 * 目标枚举类
	 *
	 * @return {@code Class<?> }
	 * @throws
	 * @see Class<?>
	 */
	Class<?> target() default Class.class;

	/**
	 * 是否忽略空值
	 *
	 * @return boolean
	 * @throws
	 * @see boolean
	 */
	boolean ignoreEmpty() default true;
}