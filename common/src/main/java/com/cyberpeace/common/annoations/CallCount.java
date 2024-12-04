package com.cyberpeace.common.annoations;

import java.lang.annotation.*;

/**
 * 调用计数
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/12
 * @see Annotation
 * @since 1.0.0
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CallCount {

    /**
     * 类型
     *
     * @return {@code String }
     */
    String value() default "none";
}
