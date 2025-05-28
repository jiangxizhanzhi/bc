/**
 * 这段代码定义了一个名为 `EvaTemplateBaseInfoReq` 的 Java 类，该类用于表示评估靶场模板的基础信息请求体。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.cr.evaluation.controller.req` 
 * - **类名**：`EvaTemplateBaseInfoReq` 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 库提供，自动生成 getter 和 setter 方法。 
 * - `@ApiModelProperty`：由 Swagger 注解库提供，用于生成 API 文档。 
 * ### 类属性 
 * - **resourceId** (String)：资源 ID，可选。 
 * - **name** (String)：模板名称，必填。 
 * - **description** (String)：模板描述，可选。 
 * - **logoId** (String)：图标 ID，必填。 
 * - **categoryCode** (Integer)：类别代号，必填。 
 * - **enableRecording** (Boolean)：是否启用录制功能，必填。 
 * ### 属性注解 
 * - `@NotNull`：确保属性值不为空。 
 * - `@ApiModelProperty`：提供属性的描述和是否必填的信息。 
 * ### 总结 
 * 这个类主要用于接收评估靶场模板的基础信息请求。它包含了一些必要的信息字段，并通过注解来确保数据的有效性和生成 API 文档。使用 Lombok 库简化了 getter 和 setter 方法的编写。 
**/
package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 伊娃模板基础信息请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvaTemplateBaseInfoReq {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "测评靶场模板id", required = false)
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "测评靶场模板名称", required = true)
	@NotNull
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "测评靶场描述", required = false)
	private String description;

	/**
	 * 图标id
	 */
	@ApiModelProperty(value = "测评靶场Logo文件id", required = true)
	@NotNull
	private String logoId;

	/**
	 * 类别代号
	 */
	@ApiModelProperty(value = "测评靶场类型", required = true)
	@NotNull
	private Integer categoryCode;

	/**
	 * 启用录制
	 */
	@ApiModelProperty(value = "是否录屏", required = true)
	@NotNull
	private Boolean enableRecording;

}