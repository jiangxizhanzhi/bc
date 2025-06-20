package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 群场景延迟请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `GroupSceneDelayReq` 的 Java 类，用于表示群场景延迟请求体。以下是对该类的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.controller.req` 
 * - **类名**：`GroupSceneDelayReq` 
 * - **实现接口**：`Serializable`（序列化接口） 
 * ### 注解 
 * - **Lombok**： 
 * - `@Getter` 和 `@Setter`：自动生成属性的 getter 和 setter 方法。 
 * - **Swagger**： 
 * - `@ApiModel`：标记这是一个模型对象，通常用于 Swagger 文档生成。 
 * - `@ApiModelProperty`：为模型中的属性添加描述。 
 * ### 属性 
 * 1. **classroomId** 
 * - **类型**：`String` 
 * - **注解**： 
 * - `@NotBlank`：确保该字段不为空或空白字符串。 
 * - `@ApiModelProperty`：描述为“课堂编号”。 
 * 2. **end** 
 * - **类型**：`Boolean` 
 * - **注解**： 
 * - `@NotNull`：确保该字段不为 null。 
 * - `@ApiModelProperty`：描述为“是否开启场景延时 true=开启 false=不开启”。 
 * ### 总结 
 * - **功能**：该类主要用于接收群场景延迟请求的数据，包含课堂编号和是否开启场景延时两个属性。 
 * - **约束**：`classroomId` 字段不能为空且必须是有效的字符串；`end` 字段不能为空且必须是一个布尔值。 
 * - **文档**：通过 Swagger 注解，可以自动生成 API 文档，方便前端开发人员理解如何使用这个请求体。 
 * 这段代码简洁明了，符合良好的编程实践，并且易于扩展和维护。 
**/
@Getter
@Setter
@ApiModel
public class GroupSceneDelayReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 课堂id
	 *
	 * @see String
	 */
	@ApiModelProperty("课堂编号")
	@NotBlank
	private String classroomId;

	/**
	 * 结束
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否开启场景延时 true=开启 false=不开启")
	@NotNull
	private Boolean end;

}

