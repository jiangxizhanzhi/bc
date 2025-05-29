package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 评估算法裁判请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalAlgorithmRefReq` 的 Java 类，用于表示评估算法裁判请求体。以下是详细的分析和总结： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.cr.evaluation.controller.req` 
 * - **类名**：`EvalAlgorithmRefReq` 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 库提供，自动生成 getter 和 setter 方法。 
 * - `@ApiModelProperty`：由 Swagger 注解库提供，用于生成 API 文档。 
 * ### 字段定义 
 * 1. **resourceId** 
 * - **类型**：`String` 
 * - **注解**： 
 * - `@ApiModelProperty(value = "算法Id", required = true)`：标明该字段为必填项，并描述其用途。 
 * 2. **code** 
 * - **类型**：`String` 
 * - **注解**： 
 * - `@ApiModelProperty(value = "代码", required = true)`：标明该字段为必填项，并描述其用途。 
 * - `@NotNull`：确保该字段不为空。 
 * 3. **mode** 
 * - **类型**：`Integer` 
 * - **注解**： 
 * - `@ApiModelProperty(value = "算法模型-0-其他算法-1-层次分析法", required = true)`：标明该字段为必填项，并描述其用途。 
 * - `@NotEmpty`：确保该字段不为空（注意：`@NotEmpty` 通常用于 `Collection` 类型，这里可能用错，应该使用 `@NotNull` 或 `@Min(0)`）。 
 * 4. **matrixData** 
 * - **类型**：`String` 
 * - **注解**： 
 * - `@ApiModelProperty(value = "矩阵数据内容", required = true)`：标明该字段为必填项，并描述其用途。 
 * - `@NotNull`：确保该字段不为空。 
 * ### 总结 
 * - **功能**：该类主要用于封装评估算法裁判请求所需的数据，包括算法 ID、代码、模式以及矩阵数据。 
 * - **验证**：所有字段均为必填项，并且通过注解进行了验证。 
 * - **改进点**：`mode` 字段的 `@NotEmpty` 注解可能不合适，建议使用 `@NotNull` 或 `@Min(0)` 以确保正确的验证逻辑。 
 * 这段代码简洁明了，适合作为一个请求体类来使用。 
**/
@Getter
@Setter
public class EvalAlgorithmRefReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "算法Id", required = true)
	private String resourceId;

	/**
	 * 代号
	 */
	@ApiModelProperty(value = "代码", required = true)
	@NotNull
	private String code;

	/**
	 * 模式
	 */
	@ApiModelProperty(value = "算法模型-0-其他算法-1-层次分析法", required = true)
	@NotEmpty
	private Integer mode;

	/**
	 * 矩阵数据
	 */
	@ApiModelProperty(value = "矩阵数据内容", required = true)
	@NotNull
	private String matrixData;


}

