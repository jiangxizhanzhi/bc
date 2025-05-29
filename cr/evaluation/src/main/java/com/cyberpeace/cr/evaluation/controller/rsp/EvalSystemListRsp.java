package com.cyberpeace.cr.evaluation.controller.rsp;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估系统列表响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalSystemListRsp` 的 Java 类，该类用于表示评估系统的列表响应。以下是详细的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.cr.evaluation.controller.rsp` 
 * - **类名**：`EvalSystemListRsp` 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：来自 Lombok 库，自动生成 getter 和 setter 方法。 
 * - `@ApiModelProperty`：来自 Swagger 库，用于生成 API 文档。 
 * ### 类成员变量 
 * - **evalSystemId**：评估系统 ID，字符串类型，必须提供。 
 * - **name**：评估系统的名称，字符串类型，必须提供。 
 * - **description**：评估系统的描述，字符串类型，可选。 
 * - **firstIndexNum**：一级指标数量，整数类型，必须提供。 
 * - **secondIndexNum**：二级指标数量，整数类型，必须提供。 
 * - **thirdIndexNum**：三级指标数量，整数类型，必须提供。 
 * ### 成员变量的注解 
 * - **@NotNull**：确保变量不为空。 
 * - **@ApiModelProperty**：提供关于字段的详细信息，如字段名称和是否必需。 
 * ### 总结 
 * - 这个类主要用于表示评估系统的列表响应数据。 
 * - 所有字段都是必需的，除了 `description` 可以为空。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来简化代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来生成 API 文档。 
 * 这个类可以作为 RESTful API 的响应对象，返回评估系统的信息。 
**/
@Getter
@Setter
public class EvalSystemListRsp {

	/**
	 * 评估系统id
	 */
	@ApiModelProperty(value = "评估体系Id", required = true)
	@NotNull
	private String evalSystemId;

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
	 * 第一指数数量
	 */
	@ApiModelProperty(value = "一级指标数量", required = true)
	@NotNull
	private Integer firstIndexNum;

	/**
	 * 第二指数数量
	 */
	@ApiModelProperty(value = "二级指标数量", required = true)
	@NotNull
	private Integer secondIndexNum;

	/**
	 * 第三指数数量
	 */
	@ApiModelProperty(value = "三级指标数量", required = true)
	@NotNull
	private Integer thirdIndexNum;

}
