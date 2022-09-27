package com.cyberpeace.common.annoations;

import java.lang.annotation.*;

/**
 * @author chenzhuo
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AuthKey {

}
