package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估任务停请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTaskStopReq` 的 Java 类，用于表示评估任务停止请求的数据结构。以下是对该类的简要分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.cr.evaluation.controller.req` 
 * - **类名**: `EvalTaskStopReq` 
 * - **注解**: 
 * - `@Getter` 和 `@Setter`: 由 Lombok 库提供，自动生成属性的 getter 和 setter 方法。 
 * - `@ApiModelProperty`: 由 Swagger 库提供，用于生成 API 文档。 
 * ### 属性 
 * 1. **templateId** 
 * - **类型**: `String` 
 * - **注解**: 
 * - `@NotNull`: 表示该字段不能为空。 
 * - `@ApiModelProperty`: 描述为“靶场测评模板Id”，并且是必需的（required = true）。 
 * 2. **taskId** 
 * - **类型**: `String` 
 * - **注解**: 
 * - `@NotNull`: 表示该字段不能为空。 
 * - `@ApiModelProperty`: 描述为“测评任务Id”，并且是必需的（required = true）。 
 * 3. **taskName** 
 * - **类型**: `String` 
 * - **注解**: 
 * - `@ApiModelProperty`: 描述为“测评任务名称”，并且不是必需的（required = false）。 
 * ### 总结 
 * - `EvalTaskStopReq` 类主要用于接收停止评估任务的请求数据。 
 * - 该类包含三个属性：`templateId`、`taskId` 和 `taskName`。 
 * - 其中 `templateId` 和 `taskId` 是必需的，而 `taskName` 是可选的。 
 * - 使用了 Lombok 和 Swagger 注解来简化代码并生成文档。 
 * 这个类的设计目的是确保在停止评估任务时，必须提供模板ID和任务ID，并且这些字段不能为空。 
**/
@Getter
@Setter
public class EvalTaskStopReq {

	/**
	 * 模板id
	 */
	@ApiModelProperty(value = "靶场测评模板Id", required = true)
	@NotNull
	private String templateId;

	/**
	 * 任务id
	 */
	@ApiModelProperty(value = "测评任务Id", required = true)
	@NotNull
	private String taskId;

	/**
	 * 任务名称
	 */
	@ApiModelProperty(value = "测评任务名称", required = false)
	private String taskName;

}
