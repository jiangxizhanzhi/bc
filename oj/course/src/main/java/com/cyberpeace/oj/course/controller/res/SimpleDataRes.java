/**
 * 这段代码定义了一个名为 `SimpleDataRes` 的 Java 类，该类用于表示一个简单的数据结果。以下是对代码的详细分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.course.controller.res` 
 * - 这个类位于 `com.cyberpeace.oj.course.controller.res` 包中。 
 * - **导入语句** 
 * - `io.swagger.annotations.ApiModelProperty`：用于 Swagger 文档注解，标记字段为 API 模型属性。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，自动生成 getter 和 setter 方法。 
 * - `java.io.Serializable`：接口，表示该类的对象可以序列化。 
 * ### 类结构 
 * - **类名**：`SimpleDataRes` 
 * - 实现了 `Serializable` 接口，表明该类的对象可以被序列化。 
 * - **注释** 
 * - 类注释说明这是一个简单的数据结果类，作者是 `SN`，版本 `1.0.0`。 
 * - 该类实现了 `Serializable` 接口。 
 * ### 字段 
 * - **resourceId** 
 * - 类型：`String` 
 * - 注释说明这是一个课程编号。 
 * - Swagger 注解 `@ApiModelProperty("课程编号")` 表明在生成的文档中，这个字段将显示为“课程编号”。 
 * - **name** 
 * - 类型：`String` 
 * - 注释说明这是一个名称。 
 * - Swagger 注解 `@ApiModelProperty("资源名称")` 表明在生成的文档中，这个字段将显示为“资源名称”。 
 * ### 特性 
 * - **Lombok 注解** 
 * - `@Getter` 和 `@Setter` 自动生成 getter 和 setter 方法，简化代码。 
 * ### 总结 
 * - `SimpleDataRes` 是一个简单的数据传输对象（DTO），主要用于在控制器层与外部系统或客户端之间传递数据。 
 * - 它包含两个字段：`resourceId` 和 `name`，分别表示课程编号和资源名称。 
 * - 使用 Lombok 注解自动生成 getter 和 setter 方法，使代码更简洁。 
 * - 通过 Swagger 注解，方便生成 API 文档。 
**/
package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 简单数据结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class SimpleDataRes implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("资源名称")
	private String name;

}
