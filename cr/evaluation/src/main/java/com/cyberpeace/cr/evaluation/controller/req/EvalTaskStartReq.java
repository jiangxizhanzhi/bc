package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 评估任务开始请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTaskStartReq` 的 Java 类，用于表示评估任务开始请求的数据模型。以下是该类的详细分析： 
 * ### 类名和包 
 * - **包名**: `com.cyberpeace.cr.evaluation.controller.req` 
 * - **类名**: `EvalTaskStartReq` 
 * ### 类的功能 
 * - 这个类主要用于封装评估任务开始请求所需的数据。 
 * ### 类的属性 
 * - **templateId** (String 类型): 
 * - 注释说明: 靶场测评模板Id。 
 * - 注解: 
 * - `@ApiModelProperty`: 描述该字段在 Swagger 文档中的显示信息。 
 * - `@NotNull`: 表示该字段不能为空。 
 * ### 类的特性 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解来自动生成 getter 和 setter 方法，简化了代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述字段在 API 文档中的信息。 
 * ### 总结 
 * - 这个类主要包含一个必需的 `templateId` 字段，用于表示评估任务所使用的模板ID。 
 * - 类的设计符合 RESTful API 设计规范，并且使用了一些现代的开发工具（如 Lombok 和 Swagger）来简化代码并提高可读性。 
 * 这个类非常适合用作 API 请求体的一部分，特别是在需要传递模板ID以启动评估任务的场景中。 
**/
@Getter
@Setter
public class EvalTaskStartReq {

	/**
	 * 模板id
	 */
	@ApiModelProperty(value = "靶场测评模板Id", required = true)
	@NotNull
	private String templateId;

}
