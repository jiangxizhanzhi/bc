/**
 * 这段代码定义了一个名为 `EvalTaskDeleteReq` 的 Java 类，用于处理评估任务删除请求。以下是该类的简要分析： 
 * ### 类概述 
 * - **包名**：`com.cyberpeace.cr.evaluation.controller.req` 
 * - **类名**：`EvalTaskDeleteReq` 
 * - **功能**：用于接收评估任务删除请求的数据模型。 
 * ### 类属性 
 * - **taskIds** 
 * - **类型**：`List<String>` 
 * - **注解**： 
 * - `@ApiModelProperty(value = "测评任务Ids", required = true)`：表示该字段是必需的，并且在 Swagger 文档中显示为“测评任务Ids”。 
 * - `@NotNull`：确保该字段不为空。 
 * - `@NotEmpty`：确保该字段的列表不为空（即列表中至少有一个元素）。 
 * ### 特点 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：自动生成 getter 和 setter 方法。 
 * ### 总结 
 * - `EvalTaskDeleteReq` 类主要用于接收包含多个任务 ID 的删除请求。 
 * - 通过使用 Lombok 的 `@Getter` 和 `@Setter` 注解简化了代码。 
 * - 使用了 `javax.validation.constraints` 中的注解来确保数据的有效性，例如 `@NotNull` 和 `@NotEmpty`。 
 * - 通过 `@ApiModelProperty` 注解，使得该类在 Swagger 文档中易于理解和使用。 
 * 这段代码简洁明了地定义了一个用于接收评估任务删除请求的数据模型，同时保证了数据的完整性和有效性。 
**/
package com.cyberpeace.cr.evaluation.controller.req;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估任务删除请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalTaskDeleteReq {

	/**
	 * 任务id列表
	 */
	@ApiModelProperty(value = "测评任务Ids", required = true)
	@NotNull
	@NotEmpty
	private List<String> taskIds;

}