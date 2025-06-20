package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 训练计划新增请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `TrainPlanAddReq` 的 Java 类，该类用于表示训练计划新增请求体。以下是对代码的简要分析： 
 * ### 包声明和导入 
 * - **包声明**：`package com.cyberpeace.oj.course.controller.req;` 
 * - 该类位于 `com.cyberpeace.oj.course.controller.req` 包中。 
 * - **导入语句**： 
 * - `import java.io.Serializable;`：引入了 `Serializable` 接口，表明这个类实现了序列化接口。 
 * - `import io.swagger.annotations.ApiModelProperty;`：引入了 Swagger 注解，用于生成 API 文档。 
 * - `import lombok.Getter;` 和 `import lombok.Setter;`：引入了 Lombok 注解，用于简化 getter 和 setter 方法的生成。 
 * ### 类定义 
 * - **类名**：`TrainPlanAddReq` 
 * - 这是一个实现 `Serializable` 接口的 Java 类，用于表示训练计划新增请求体。 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动生成属性的 getter 和 setter 方法。 
 * - `@ApiModelProperty("资源名称")`：Swagger 注解，用于描述 API 中该字段的用途。 
 * ### 类属性 
 * - **属性**：`private String name;` 
 * - `name` 是一个字符串类型的属性，用于存储训练计划的名称。 
 * - `@ApiModelProperty("资源名称")` 注解用于描述该字段在 API 文档中的用途。 
 * ### 总结 
 * - **功能**：`TrainPlanAddReq` 类主要用于接收客户端提交的训练计划新增请求，并包含一个 `name` 属性用于存储训练计划的名称。 
 * - **特性**：使用 Lombok 注解简化了 getter 和 setter 方法的编写，并且通过 Swagger 注解提高了 API 文档的可读性和维护性。 
 * - **适用场景**：适用于需要接收和处理训练计划新增请求的场景，特别是在使用 Swagger 生成 API 文档时。 
 * 该类简洁明了，符合面向对象的设计原则，并且通过引入 Lombok 和 Swagger 提高了代码的可读性和维护性。 
**/
@Getter
@Setter
public class TrainPlanAddReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("资源名称")
	private String name;
}

