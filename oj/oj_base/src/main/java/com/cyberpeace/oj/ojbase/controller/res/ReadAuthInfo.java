package com.cyberpeace.oj.ojbase.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 读取身份验证信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ReadAuthInfo` 的 Java 类，用于表示读取身份验证信息。以下是该类的详细分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.ojbase.controller.res` 
 * - **导入语句**： 
 * - `io.swagger.annotations.ApiModelProperty`：用于 Swagger 文档注解。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * - `java.io.Serializable`：接口，表明该类的对象可以序列化。 
 * ### 类声明 
 * - **类名**：`ReadAuthInfo` 
 * - **实现接口**：`Serializable` 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成 getter 和 setter 方法。 
 * - `@ApiModelProperty`：用于 Swagger 文档注解，描述字段的用途。 
 * ### 字段 
 * 1. **readAuthType** 
 * - **类型**：`Integer` 
 * - **注释**：授权访问类型，值为 1 表示共享，值为 2 表示自定义。 
 * - **Swagger 注解**：`@ApiModelProperty("授权访问类型 1=共享 2=自定义")` 
 * 2. **readAuthIds** 
 * - **类型**：`List<String>` 
 * - **注释**：授权访问组织编号。 
 * - **Swagger 注解**：`@ApiModelProperty("授权访问组织编号")` 
 * ### 总结 
 * - **功能**：该类主要用于存储和传输读取身份验证的信息，包括授权访问类型和相应的组织编号。 
 * - **特点**： 
 * - 使用了 Lombok 注解以简化代码。 
 * - 使用了 Swagger 注解以便于生成 API 文档。 
 * - 实现了 `Serializable` 接口，支持对象的序列化。 
 * 这个类的设计简洁明了，符合现代 Java 开发的最佳实践，如使用 Lombok 减少样板代码、使用 Swagger 注解增强文档可读性等。 
**/
@Getter
@Setter
public class ReadAuthInfo implements Serializable {

	/**
	 * 读取身份验证类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("授权访问类型 1=共享 2=自定义")
	private Integer readAuthType;

	/**
	 * 读认证id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("授权访问组织编号")
	private List<String> readAuthIds;

}

