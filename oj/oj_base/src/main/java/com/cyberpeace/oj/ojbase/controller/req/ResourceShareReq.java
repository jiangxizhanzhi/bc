package com.cyberpeace.oj.ojbase.controller.req;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 资源共享请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ResourceShareReq` 的 Java 类，用于处理资源共享请求。以下是对其简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.ojbase.controller.req` 
 * - **类名**：`ResourceShareReq` 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 提供，自动生成 getter 和 setter 方法。 
 * - `@ApiModel`：用于 Swagger 文档，描述该类为“资源授权操作”。 
 * ### 属性 
 * 1. **resourceIds** 
 * - **类型**：`List<String>` 
 * - **注解**： 
 * - `@NotEmpty`：确保该列表不为空。 
 * - `@ApiModelProperty("资源ID")`：描述该字段在 Swagger 中的显示名称。 
 * - **作用**：存储需要被授权的资源 ID 列表。 
 * 2. **operationAuthIds** 
 * - **类型**：`List<String>` 
 * - **注解**： 
 * - `@ApiModelProperty("授权人编号集合")`：描述该字段在 Swagger 中的显示名称。 
 * - **作用**：存储授权人的编号列表。 
 * 3. **operationAuthType** 
 * - **类型**：`Integer` 
 * - **注解**： 
 * - `@NotNull`：确保该值不为空。 
 * - `@ApiModelProperty("授权模式, 1-所有, 2-自定义")`：描述该字段在 Swagger 中的显示名称及其可能的值。 
 * - **作用**：指定授权模式（例如，1 表示所有，2 表示自定义）。 
 * ### 总结 
 * - `ResourceShareReq` 类主要用于接收和验证资源共享请求的数据。 
 * - 它包含三个属性：资源 ID 列表、授权人编号列表以及授权模式。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来简化代码。 
 * - 使用了 Swagger 注解来生成文档，并提供清晰的字段说明。 
 * 这个类的设计简洁明了，能够满足资源共享请求的基本需求。 
**/
@Getter
@Setter
@ApiModel("资源授权操作")
public class ResourceShareReq {

	/**
	 * 资源id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("资源ID")
	@NotEmpty
	private List<String> resourceIds;

	/**
	 * 实操认证id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("授权人编号集合")
	private List<String> operationAuthIds;

	/**
	 * 实操认证类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("授权模式, 1-所有, 2-自定义")
	@NotNull
	private Integer operationAuthType;

}

