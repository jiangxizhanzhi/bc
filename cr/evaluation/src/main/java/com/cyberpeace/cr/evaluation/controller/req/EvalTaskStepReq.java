package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估任务步请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTaskStepReq` 的 Java 类，用于表示评估任务步骤的请求体。以下是对其主要特性的简要分析： 
 * ### 类定义与注解 
 * - **包声明**：该类位于 `com.cyberpeace.cr.evaluation.controller.req` 包中。 
 * - **导入语句**：引入了 `javax.validation.constraints.NotNull` 和 `io.swagger.annotations.ApiModelProperty` 注解，以及 Lombok 库的 `@Getter` 和 `@Setter` 注解。 
 * ### 类功能 
 * - **Lombok 注解**： 
 * - `@Getter`：为所有字段自动生成 getter 方法。 
 * - `@Setter`：为所有字段自动生成 setter 方法。 
 * - **文档注释**： 
 * - 类的文档注释说明了这是一个评估任务步骤的请求体，并指出了作者和日期。 
 * ### 字段定义 
 * - **stepId**： 
 * - **类型**：`String` 
 * - **注解**： 
 * - `@ApiModelProperty(value = "测评步骤Id", required = true)`：标记该字段在 Swagger 文档中显示为“测评步骤Id”，并且是必需的。 
 * - `@NotNull`：确保该字段不能为空（即必须提供一个值）。 
 * ### 总结 
 * - **用途**：`EvalTaskStepReq` 类用于封装评估任务步骤请求的数据模型。 
 * - **验证**：通过 `@NotNull` 注解确保了 `stepId` 字段的必填性。 
 * - **文档化**：使用 Swagger 注解来描述字段的含义和要求，便于 API 文档生成。 
 * - **简洁性**：借助 Lombok 注解简化了代码，减少了样板代码。 
 * 这个类的设计考虑到了数据验证和文档化的需求，适用于 RESTful API 的请求体定义。 
**/
@Getter
@Setter
public class EvalTaskStepReq {

	/**
	 * 步id
	 */
	@ApiModelProperty(value = "测评步骤Id", required = true)
	@NotNull
	private String stepId;

}
