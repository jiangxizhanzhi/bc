package com.cyberpeace.common.annoations;

import java.lang.annotation.*;

/**
 *
 auth oj key
 *
 *

 @author chenzhuo
 @date 2024/11/07


 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthOjKey {

}
