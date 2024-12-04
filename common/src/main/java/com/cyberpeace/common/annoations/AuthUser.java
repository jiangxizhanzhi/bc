package com.cyberpeace.common.annoations;

import java.lang.annotation.*;

/**
 * auth 用户
 *
 * @author chenzhuo@see Annotation
 * @version 1.0.0
 * @date 2024/12/04
 * @since 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthUser {

    String name() default "userId";

    /** 可定义方法 调用拦截器的方法进行校验 未实现*/
    String method() default "validateUser";

}
