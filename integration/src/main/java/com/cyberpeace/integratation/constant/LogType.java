package com.cyberpeace.integratation.constant;

import lombok.Getter;

/**
 * 日志类型
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `LogType` 的枚举类，用于表示不同类型的日志。以下是详细的分析和总结： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.integratation.constant` 
 * - **类名**: `LogType` 
 * - **注释**: 该枚举类用于表示日志类型，并提供了作者和日期信息。 
 * ### 枚举常量 
 * - `SYSTEM`: 系统日志，代码为 `0`，描述为 `"系统日志"`。 
 * - `USER`: 用户日志，代码为 `1`，描述为 `"用户日志"`。 
 * - `SCENE`: 场景日志，代码为 `2`，描述为 `"场景日志"`。 
 * - `BUSINESS`: 业务日志，代码为 `3`，描述为 `"业务日志"`。 
 * ### 成员变量 
 * - `code`: 表示日志类型的代码，使用 `@Getter` 注解表明该字段可以被外部访问。 
 * - `desc`: 表示日志类型的描述，同样使用 `@Getter` 注解。 
 * ### 构造方法 
 * - `LogType(Integer code, String desc)`: 枚举构造方法，用于初始化 `code` 和 `desc` 字段。 
 * ### 总结 
 * 这个枚举类 `LogType` 定义了四种不同类型的日志，并且通过 `@Getter` 注解使得 `code` 和 `desc` 可以被外部访问。这种设计方式有助于提高代码的可读性和可维护性，同时方便在其他地方引用这些日志类型。 
**/
public enum LogType {
    /**
     * 操作日志模块
     */

    SYSTEM(0, "系统日志"),
    /**
     * 用户
     */
    USER(1, "用户日志"),
    /**
     * 场景
     */
    SCENE(2, "场景日志"),
    /**
     * 业务
     */
    BUSINESS(3, "业务日志");

    /**
     * 代号
     */
    @Getter
    private Integer code;

    /**
     * 描述
     */
    @Getter
    private String desc;

    LogType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}

