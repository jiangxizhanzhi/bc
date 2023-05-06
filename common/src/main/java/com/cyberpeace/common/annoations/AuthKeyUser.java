package com.cyberpeace.common.annoations;

import java.lang.annotation.*;

import org.springframework.core.annotation.AliasFor;

/**
 * @author chenzhuo
 * @date 2023/05/06
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthUser
@AuthOjKey
public @interface AuthKeyUser {

	@AliasFor(annotation = AuthUser.class)
	String name() default "userId";

}
