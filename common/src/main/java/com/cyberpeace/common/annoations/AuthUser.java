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
/**
 * 这段代码定义了一个自定义注解 `@AuthUser`，主要用于权限或用户相关的场景。以下是简要分析： 
 * 1. **注解作用范围**： 
 * - 使用了 `@Target({ElementType.METHOD, ElementType.TYPE})`，表示该注解可以用于类或方法上。 
 * 2. **注解生命周期**： 
 * - 使用了 `@Retention(RetentionPolicy.RUNTIME)`，表明该注解在运行时可用（通过反射获取）。 
 * 3. **文档化支持**： 
 * - 使用了 `@Documented`，表示该注解会被包含在生成的 Javadoc 文档中。 
 * 4. **属性定义**： 
 * - 定义了两个属性： 
 * - `name()`：默认值为 `"userId"`，可能用于标识用户 ID 的名称。 
 * - `method()`：默认值为 `"validateUser"`，用于指定需要调用的拦截器校验方法，但目前未实现。 
 * 5. **用途**： 
 * - 该注解可能是为了标记需要用户认证的方法或类，并通过配置的属性实现特定的用户验证逻辑。 
 * 总结：这是一个功能性的自定义注解，主要用于标识需要用户认证的类或方法，并预留了扩展校验逻辑的能力。 
**/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthUser {

    String name() default "userId";

    /** 可定义方法 调用拦截器的方法进行校验 未实现*/
    String method() default "validateUser";

}

