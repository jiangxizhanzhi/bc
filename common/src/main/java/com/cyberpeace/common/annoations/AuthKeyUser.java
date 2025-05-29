package com.cyberpeace.common.annoations;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 身份验证密钥用户
 *
 * @author chenzhuo@date 2024/11/08@see Annotation
 * @version 1.0.0
 * @date 2024/12/04
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个自定义注解 `@AuthKeyUser`，以下是简要分析： 
 * 1. **注解目标**： 
 * - `@Target({ElementType.METHOD, ElementType.TYPE})`：表明该注解可以用于方法或类级别。 
 * 2. **生命周期**： 
 * - `@Retention(RetentionPolicy.RUNTIME)`：表示该注解在运行时可用（通过反射获取）。 
 * 3. **文档化**： 
 * - `@Documented`：表示该注解会被包含在生成的 JavaDoc 文档中。 
 * 4. **继承其他注解**： 
 * - `@AuthUser` 和 `@AuthOjKey`：表明 `@AuthKeyUser` 继承了这两个注解的功能。 
 * 5. **属性定义**： 
 * - `String name() default "userId";`：定义了一个名为 `name` 的属性，默认值为 `"userId"`。该属性通过 `@AliasFor` 注解与 `@AuthUser` 的 `name` 属性别名关联。 
 * 总结：这是一个用于身份验证的自定义注解，主要用于方法或类上，支持运行时反射，并继承了其他注解的功能，同时提供了可配置的 `name` 属性。 
**/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthUser
@AuthOjKey
public @interface AuthKeyUser {

    @AliasFor(annotation = AuthUser.class)
    String name() default "userId";

}

