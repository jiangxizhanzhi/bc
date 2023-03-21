package com.cyberpeace.common.annoations;

import java.lang.annotation.*;

/**
 * @author zhangjb
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PermissionOperation {

    String value() default "";



}
