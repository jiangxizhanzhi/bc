/**
 * 该代码定义了一个自定义注解 `@CallCount`，用于标记方法或类并记录调用次数。以下是详细分析： 
 * ### 主要内容： 
 * 1. **注解定义**： 
 * - 使用了 `@interface` 关键字定义了一个注解 `@CallCount`。 
 * - 注解的作用目标为 `ElementType.METHOD` 和 `ElementType.TYPE`，即可以标注在方法或类上。 
 * 2. **元注解配置**： 
 * - `@Target`：限制注解只能用于方法或类。 
 * - `@Retention`：设置注解的生命周期为 `RetentionPolicy.RUNTIME`，表示注解在运行时可用（可以通过反射获取）。 
 * - `@Documented`：将注解包含在生成的 Javadoc 文档中。 
 * 3. **属性定义**： 
 * - 注解有一个名为 `value` 的属性，默认值为 `"none"`。 
 * - 属性类型为 `String`，用于指定某种类型的标识。 
 * ### 总结： 
 * 该注解的主要用途是为方法或类添加调用计数的功能，并通过 `value` 属性传递额外信息。由于其运行时可用，可以在运行时通过反射机制实现相关的逻辑处理。 
**/
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
