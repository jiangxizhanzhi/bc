package com.cyberpeace.common.annoations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author chenzhuo
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthUser
@AuthOjKey
public @interface AuthKeyUser {

    @AliasFor(annotation = AuthUser.class)
    String name() default "userId";

}
