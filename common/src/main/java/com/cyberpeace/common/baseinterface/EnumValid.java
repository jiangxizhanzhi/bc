package com.cyberpeace.common.baseinterface;



import com.cyberpeace.common.model.EnumValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 枚举有效
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/12/04
 * @see Annotation
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个自定义的 Java 注解 `@EnumValid`，用于验证某个字段是否为指定枚举类中的有效值。以下是代码的简要分析： 
 * 1. **注解用途**： 
 * - `@EnumValid` 是一个自定义注解，主要用于校验某个字段的值是否属于指定的枚举类。 
 * - 它通过 `EnumValidator` 类来实现具体的校验逻辑。 
 * 2. **注解属性**： 
 * - `message()`：定义校验失败时的提示信息，默认为空字符串。 
 * - `groups()` 和 `payload()`：与标准的 JSR 380（Bean Validation）规范一致，用于分组校验和负载信息。 
 * - `target()`：指定需要校验的目标枚举类，默认值为 `Class.class`。 
 * - `ignoreEmpty()`：布尔值，表示是否忽略空值，默认为 `true`。 
 * 3. **注解元信息**： 
 * - `@Target`：定义该注解可以应用的位置，包括字段、方法和嵌套注解。 
 * - `@Retention`：指定注解在运行时可用。 
 * - `@Documented`：将注解标记为文档化的注解。 
 * - `@Constraint`：表明这是一个自定义约束注解，并指定其验证器为 `EnumValidator`。 
 * 4. **适用场景**： 
 * - 适用于需要确保字段值属于特定枚举类的场景，同时支持灵活配置如忽略空值等。 
 * 总结：`@EnumValid` 是一个功能强大的自定义注解，结合 `EnumValidator` 可以方便地对枚举类型进行校验，符合 Java Bean Validation 的规范。 
**/
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValidator.class})
public @interface EnumValid {

    String message() default "";

    // 作用参考@Validated和@Valid的区别
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 目标枚举类
     */
    Class<?> target() default Class.class;

    /**
     * 是否忽略空值
     */
    boolean ignoreEmpty() default true;
}
