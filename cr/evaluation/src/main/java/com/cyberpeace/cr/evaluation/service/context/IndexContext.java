/**
 * 这段代码定义了一个名为 `IndexContext` 的 Java 类，该类用于表示索引上下文。以下是对其主要特性的简要分析： 
 * ### 类定义与注解 
 * - **包声明**：该类位于 `com.cyberpeace.cr.evaluation.service.context` 包中。 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：这两个注解自动生成类属性的 getter 和 setter 方法。 
 * - **Swagger 注解**： 
 * - `@ApiModelProperty`：用于生成 API 文档，说明每个字段的含义及其是否为必填。 
 * ### 类属性 
 * - **indexId**：字符串类型，表示指数 ID，必须提供。 
 * - **name**：字符串类型，表示名称，必须提供。 
 * - **description**：字符串类型，表示描述，非必填。 
 * - **algorithmId**：字符串类型，表示算法 ID，必须提供。 
 * - **evalIndicatorId**：字符串类型，表示评估指示器 ID，必须提供。 
 * - **evalSystemId**：字符串类型，表示评估系统 ID，必须提供。 
 * - **params**：字符串类型，表示算法参数，必须提供。 
 * - **childrenIds**：字符串列表，表示子级 ID 列表，必须提供。 
 * - **matrixData**：字符串类型，表示矩阵数据（层次分析法 JSON 数组），非必填。 
 * ### 特性总结 
 * - **验证**：所有标注为 `@NotNull` 的字段都是必填项。 
 * - **API 文档**：通过 Swagger 注解提供了清晰的字段描述，有助于生成文档。 
 * - **可读性和维护性**：使用 Lombok 的 `@Getter` 和 `@Setter` 减少了样板代码，提高了代码的可读性和维护性。 
 * 这个类主要用于在某个评估系统中表示和传递关于索引的信息，包括其 ID、名称、描述、算法信息等。 
**/
package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 索引上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class IndexContext {

	/**
	 * 指数id
	 */
	@ApiModelProperty(value = "指标Id", required = true)
	@NotNull
	private String indexId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述", required = false)
	private String description;

	/**
	 * 算法id
	 */
	@ApiModelProperty(value = "算法id", required = true)
	@NotNull
	private String algorithmId;

	/**
	 * 评估指示器id
	 */
	@ApiModelProperty(value = "指标项id", required = true)
	@NotNull
	private String evalIndicatorId;

	/**
	 * 评估系统id
	 */
	@ApiModelProperty(value = "体系id", required = true)
	@NotNull
	private String evalSystemId;

	/**
	 * 参数
	 */
	@ApiModelProperty(value = "算法参数", required = true)
	@NotNull
	private String params;

	/**
	 * 子级id列表
	 */
	@ApiModelProperty(value = "子指标列表", required = true)
	@NotNull
	private List<String> childrenIds;

	/**
	 * 矩阵数据
	 */
	@ApiModelProperty(value = "层次分析法json数组", required = false)
	private String matrixData;

}