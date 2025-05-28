/**
 * 这段代码定义了一个名为 `EvalTemplateListRsp` 的 Java 类，用于表示评估模板列表的响应。以下是对其主要特性和功能的简要分析： 
 * ### 包与导入 
 * - **包声明**：该类位于 `com.cyberpeace.cr.evaluation.controller.rsp` 包中。 
 * - **导入的库**： 
 * - `java.io.Serializable`：确保对象可以序列化。 
 * - `javax.validation.constraints.NotNull`：用于验证属性不能为空。 
 * - `io.swagger.annotations.ApiModelProperty`：用于生成 API 文档。 
 * - `lombok.Getter` 和 `lombok.Setter`：通过 Lombok 注解自动生成 getter 和 setter 方法。 
 * ### 类声明 
 * - **类名**：`EvalTemplateListRsp` 
 * - **实现接口**：`Serializable` 
 * - **注解**：使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，简化了属性的访问和修改方法的生成。 
 * ### 属性 
 * - **resourceId**（资源 ID）：字符串类型，表示测评靶场模板的 ID。 
 * - **name**（名称）：字符串类型，表示测评名称，并且必须非空（使用 `@NotNull` 注解）。 
 * - **description**（描述）：字符串类型，表示测评的描述信息。 
 * - **logoPath**（图标路径）：字符串类型，表示 Logo 文件的路径。 
 * - **logoId**（图标 ID）：字符串类型，表示 Logo 文件的 ID。 
 * - **categoryCode**（类别代号）：整数类型，表示测评的类型。 
 * ### 方法 
 * - **setLogo**：设置 Logo 的方法，实际上是将传入的 `logo` 值赋给 `logoId`。 
 * ### 总结 
 * - **用途**：该类主要用于构建评估模板列表的响应对象，通常在 RESTful API 中作为返回的数据结构。 
 * - **验证**：`name` 属性被标记为 `@NotNull`，确保客户端提供的名称字段不能为空。 
 * - **文档生成**：使用 Swagger 注解（如 `@ApiModelProperty`）来帮助生成 API 文档，方便开发者理解和使用该接口。 
 * - **Lombok 简化**：通过 Lombok 注解减少样板代码，使得类更加简洁易读。 
 * 总体来说，这个类是一个数据传输对象（DTO），用于封装评估模板列表的详细信息并提供给前端或外部系统。 
**/
package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估模板列表响应
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalTemplateListRsp implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "测评靶场模板id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "测评名称")
	@NotNull
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "测评描述")
	private String description;

	/**
	 * 图标路径
	 */
	@ApiModelProperty(value = "Logo文件路径")
	private String logoPath;

	/**
	 * 图标id
	 */
	@ApiModelProperty(value = "Logo文件Id")
	private String logoId;

	/**
	 * 类别代号
	 */
	@ApiModelProperty(value = "测评类型")
	private Integer categoryCode;

	public void setLogo(String logo) {
		this.logoId = logo;
	}

}
