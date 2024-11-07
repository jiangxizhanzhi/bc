package com.cyberpeace.common.annoations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 身份验证密钥用户
 *
 * @author chenzhuo
 * @date 2024/11/07
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthUser
@AuthOjKey
public @interface AuthKeyUser {

    /**
     * 名称
     *
     * @return {@code String }
     */
    @AliasFor(annotation = AuthUser.class)
    String name() default "userId";

}
