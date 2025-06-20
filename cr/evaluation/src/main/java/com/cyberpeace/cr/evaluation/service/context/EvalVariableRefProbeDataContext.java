package com.cyberpeace.cr.evaluation.service.context;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * EVAL 变量 Ref 探针数据上下文
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalVariableRefProbeDataContext` 的 Java 类，该类实现了 `Serializable` 接口。这个类主要用于存储某种探针数据的上下文信息。以下是对代码的详细分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.cr.evaluation.service.context` 
 * - 该类位于 `com.cyberpeace.cr.evaluation.service.context` 包中。 
 * - **类名**：`EvalVariableRefProbeDataContext` 
 * - 这个类的名字表明它用于存储评估变量引用探针的数据上下文。 
 * - **注解**： 
 * - `@Getter` 和 `@Setter` 注解来自 Lombok 库，自动生成属性的 getter 和 setter 方法。 
 * - `implements Serializable` 表示该类的对象可以序列化，便于在网络上传输或持久化存储。 
 * ### 属性 
 * - **formattedValue** 
 * - 数据类型：`String` 
 * - 注释：格式化的值。 
 * - **status** 
 * - 数据类型：`Integer` 
 * - 注释：状态。 
 * - **time** 
 * - 数据类型：`Date` 
 * - 注释：时间。 
 * ### 总结 
 * - **用途**：`EvalVariableRefProbeDataContext` 类用于存储与探针相关的上下文数据，包括格式化的值、状态和时间。 
 * - **特性**：通过 Lombok 的 `@Getter` 和 `@Setter` 注解，减少了样板代码，提高了代码的可读性和简洁性。 
 * - **序列化**：由于实现了 `Serializable` 接口，对象可以在网络上传输或持久化存储。 
 * ### 示例 
 * ```java 
 * EvalVariableRefProbeDataContext context = new EvalVariableRefProbeDataContext(); 
 * context.setFormattedValue("123.45"); 
 * context.setStatus(1); 
 * context.setTime(new Date()); 
 * // 获取属性值 
 * System.out.println(context.getFormattedValue()); // 输出 "123.45" 
 * System.out.println(context.getStatus());          // 输出 1 
 * System.out.println(context.getTime());           // 输出当前时间 
 * ``` 
 * 这段代码是典型的 Java 类设计，适用于需要存储特定上下文数据的场景。 
**/
@Getter
@Setter
public class EvalVariableRefProbeDataContext implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;    /**
     * 格式 化值
     */
    private String formattedValue;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 时间
     */
    private Date time;
}

