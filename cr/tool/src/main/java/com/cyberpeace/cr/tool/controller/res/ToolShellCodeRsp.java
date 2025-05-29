package com.cyberpeace.cr.tool.controller.res;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 工具壳代号响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ToolShellCodeRsp` 的 Java 类，用于表示工具壳代号的响应数据。以下是详细的分析和总结： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.cr.tool.controller.res` 
 * - **类名**：`ToolShellCodeRsp` 
 * - **功能**：表示工具壳代号响应的数据模型 
 * ### 类注释 
 * - 注释说明了这个类是用于表示“工具壳代号响应”的。 
 * ### 类属性 
 * 该类包含以下三个属性： 
 * 1. **author** 
 * - **类型**：`String` 
 * - **注释**：表示工具壳的作者。 
 * - **注解**：`@ApiModelProperty(value = "作者")` 用于 Swagger 文档生成，表明该字段在 API 文档中的描述为“作者”。 
 * 2. **platform** 
 * - **类型**：`String` 
 * - **注释**：表示工具壳运行的平台。 
 * - **注解**：`@ApiModelProperty(value = "平台")` 用于 Swagger 文档生成，表明该字段在 API 文档中的描述为“平台”。 
 * 3. **edbVerified** 
 * - **类型**：`Boolean` 
 * - **注释**：表示工具壳是否经过 EDB 验证。 
 * - **注解**：`@ApiModelProperty(name = "edb_verified", value = "是否验证")` 用于 Swagger 文档生成，表明该字段在 API 文档中的名称为“edb_verified”，描述为“是否验证”。 
 * ### 类注解 
 * - **`@Getter`** 和 **`@Setter`**：这两个注解来自 Lombok 库，用于自动生成 getter 和 setter 方法，简化了代码编写。 
 * ### 总结 
 * - **用途**：`ToolShellCodeRsp` 类用于封装工具壳代号响应的相关信息，如作者、平台以及是否经过 EDB 验证等。 
 * - **框架支持**：通过使用 Lombok 的 `@Getter` 和 `@Setter` 注解，减少了样板代码的编写。 
 * - **文档支持**：通过使用 Swagger 的 `@ApiModelProperty` 注解，使得该类在生成的 API 文档中更加清晰易懂。 
 * 这个类的设计简洁明了，符合现代 Java 开发的最佳实践。 
**/
@Getter
@Setter
public class ToolShellCodeRsp {

	/**
	 * 作者
	 */
	@ApiModelProperty(value = "作者")
	private String author;

	/**
	 * 平台
	 */
	@ApiModelProperty(value = "平台")
	private String platform;

	/**
	 * EDB 验证
	 */
	@ApiModelProperty(name = "edb_verified", value = "是否验证")
	private Boolean edbVerified;
}

