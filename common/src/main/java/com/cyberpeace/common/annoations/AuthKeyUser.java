package com.cyberpeace.common.annoations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 身份验证密钥用户
 *
 * @author chenzhuo@date 2024/11/08@see Annotation
 * @version 1.0.0
 * @date 2024/12/04
 * @since 1.0.0
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
