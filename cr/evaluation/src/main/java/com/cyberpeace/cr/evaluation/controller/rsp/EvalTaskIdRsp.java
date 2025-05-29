package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估任务id响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTaskIdRsp` 的 Java 类，该类用于表示评估任务ID的响应。以下是详细的分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.cr.evaluation.controller.rsp` 
 * - **导入的库**： 
 * - `java.io.Serializable`：表示该类可以序列化。 
 * - `io.swagger.annotations.ApiModelProperty`：用于 Swagger 文档注解。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * ### 类注释 
 * - 类名为 `EvalTaskIdRsp`，描述为“评估任务id响应”。 
 * - 作者为“SN”，日期为“2024/11/08”。 
 * ### 类属性 
 * - `taskId`：一个字符串类型的属性，表示任务ID。 
 * - 使用了 `@ApiModelProperty` 注解，值为“测评靶场模板id”。 
 * ### 构造函数 
 * - `EvalTaskIdRsp(String taskId)`：构造函数，接受一个 `taskId` 参数并将其赋值给类的 `taskId` 属性。 
 * ### Getter 和 Setter 方法 
 * - `@Getter` 和 `@Setter` 注解由 Lombok 自动生成，因此无需手动编写这些方法。 
 * ### 总结 
 * 这个类的主要目的是封装评估任务的ID，并提供相应的序列化能力。通过 Swagger 注解，可以方便地在 API 文档中展示该类的结构。使用 Lombok 可以简化代码，减少样板代码的编写。 
**/
@Getter
@Setter
public class EvalTaskIdRsp implements Serializable {

	/**
	 * 任务id
	 */
	@ApiModelProperty(value = "测评靶场模板id")
	private String taskId;

	public EvalTaskIdRsp(String taskId) {
		this.taskId = taskId;
	}

}

