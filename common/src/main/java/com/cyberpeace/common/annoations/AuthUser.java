package com.cyberpeace.common.annoations;

import java.lang.annotation.*;

/**
 * @author chenzhuo
 * @date 2023/05/06
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthUser {

	String name() default "userId";

	/** 可定义方法 调用拦截器的方法进行校验 未实现*/
	String method() default "validateUser";

}
