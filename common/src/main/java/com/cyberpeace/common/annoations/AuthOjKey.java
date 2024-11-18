package com.cyberpeace.common.annoations;

import java.lang.annotation.*;

/**
 * auth oj key
 *
 * @author chenzhuo
 * @version 1.0.0
 * @date 2024/11/07
 * @see Annotation
 * @since 1.0.0
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthOjKey {

}
