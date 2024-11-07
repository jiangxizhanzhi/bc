package com.cyberpeace.common.annoations;

import java.lang.annotation.*;

/**
 * auth 用户
 *
 * @author chenzhuo
 * @date 2024/11/07
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthUser {

	/**
	 * 名称
	 *
	 * @return {@code String }
	 */
	String name() default "userId";

	/**
	 * 可定义方法 调用拦截器的方法进行校验 未实现
	 *
	 * @return {@code String }
	 */
	String method() default "validateUser";

}
