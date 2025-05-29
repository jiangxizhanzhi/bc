package com.cyberpeace.common.model;


import com.cyberpeace.common.baseinterface.EnumValid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 枚举验证器
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * ### 代码分析 
 * #### 功能描述 
 * 该代码实现了一个自定义的约束验证器 `EnumValidator`，用于验证某个字段是否属于指定的枚举类型。它通过注解 `@EnumValid` 来配置验证规则。 
 * #### 主要组成部分 
 * 1. **注解 `@EnumValid`** 
 * - 这是一个自定义注解，用于标记需要验证的字段。 
 * - 它包含两个关键属性： 
 * - `target`: 指定需要验证的枚举类。 
 * - `ignoreEmpty`: 是否忽略空值（即允许字段为 `null`）。 
 * 2. **类 `EnumValidator`** 
 * - 实现了 `ConstraintValidator<EnumValid, Integer>` 接口，表示这是一个用于验证 `@EnumValid` 注解的验证器，支持 `Integer` 类型的字段。 
 * - 提供了两个核心方法： 
 * - `initialize(EnumValid constraintAnnotation)`：初始化注解配置。 
 * - `isValid(Integer value, ConstraintValidatorContext context)`：执行具体的验证逻辑。 
 * 3. **验证逻辑** 
 * - 在 `isValid` 方法中： 
 * - 获取注解中的 `target` 和 `ignoreEmpty` 配置。 
 * - 如果目标类是枚举类型且值不为 `null` 或者未忽略空值，则遍历枚举常量，检查是否存在与值匹配的枚举项。 
 * - 如果匹配成功，返回 `true`；否则返回 `false`。 
 * #### 特点 
 * - **灵活性**：可以通过注解动态指定需要验证的枚举类及其行为（如是否忽略空值）。 
 * - **扩展性**：适用于任何实现了 `Enum` 接口的类，且要求枚举类的 `toString()` 方法返回期望的值。 
 * - **容错性**：如果目标类不是枚举或值为 `null` 且忽略空值，则直接返回 `true`。 
 * #### 可能的改进点 
 * 1. **异常处理**：如果 `annotation.target()` 返回的不是枚举类，可以抛出异常或记录日志以提示开发者。 
 * 2. **泛型支持**：当前只支持 `Integer` 类型，可以扩展为支持更多类型（如 `String` 或其他基本类型）。 
 * 3. **性能优化**：对于频繁使用的枚举类，可以缓存其 `EnumConstants` 以避免每次重复获取。 
 * #### 总结 
 * 该代码实现了一个简单但功能完善的枚举验证器，能够满足常见的枚举值验证需求。通过灵活的注解配置和清晰的逻辑结构，提升了代码的可维护性和扩展性。 
**/
public class EnumValidator implements ConstraintValidator<EnumValid, Integer> {

    /**
     * 注解
     */ // 枚举校验注解
    private EnumValid annotation;

    /**
     * 初始化
     *
     * @param constraintAnnotation 约束注释
     */
    @Override
    public void initialize(EnumValid constraintAnnotation) {

        annotation = constraintAnnotation;
    }

    /**
     * 有效
     *
     * @param value                      值
     * @param constraintValidatorContext 约束验证器上下文
     * @return boolean
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;

        Class<?> cls = annotation.target();
        boolean ignoreEmpty = annotation.ignoreEmpty();

        // target为枚举，并且value有值，或者不忽视空值，才进行校验
        if (cls.isEnum() && (value != null || !ignoreEmpty)) {

            Object[] objects = cls.getEnumConstants();
            for (Object obj : objects) {
                // 使用此注解的枚举类需要重写toString方法，改为需要验证的值
                if (obj.toString().equals(String.valueOf(value))) {
                    result = true;
                    break;
                }
            }
        } else {
            result = true;
        }
        return result;
    }
}
