package com.cyberpeace.common.annoations;

import java.lang.annotation.*;

/**
 * 调用计数
 *
 * @author SN
 * @date 2024/11/12
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
