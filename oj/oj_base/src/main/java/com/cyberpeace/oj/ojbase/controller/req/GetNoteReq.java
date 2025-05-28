/**
 * 这段代码定义了一个名为 `GetNoteReq` 的 Java 类，该类用于表示获取笔记请求的请求体。以下是对此代码的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.oj.ojbase.controller.req` 
 * - **类名**: `GetNoteReq` 
 * - **功能**: 表示一个获取笔记请求的请求体 
 * ### 类注解 
 * - **`@Getter` 和 `@Setter`**: Lombok 注解，自动生成属性的 getter 和 setter 方法。 
 * - **`@ApiModel`**: Swagger 注解，用于描述这个类作为一个模型对象。 
 * - **`implements Serializable`**: 表示该类实现了 `Serializable` 接口，可以序列化为字节流。 
 * ### 属性及注解 
 * - **`resourceId`**: 
 * - **类型**: `String` 
 * - **注解**: 
 * - **`@ApiModelProperty`**: 描述属性名称和值 
 * - **`@NotBlank`**: 确保该字段不为空或空白字符串 
 * - **`noteType`**: 
 * - **类型**: `Integer` 
 * - **注解**: 
 * - **`@ApiModelProperty`**: 描述属性名称和值 
 * - **`@NotNull`**: 确保该字段不为空 
 * - **`resourceType`**: 
 * - **类型**: `Integer` 
 * - **注解**: 
 * - **`@ApiModelProperty`**: 描述属性名称和值 
 * - **`@NotNull`**: 确保该字段不为空 
 * - **`userId`**: 
 * - **类型**: `String` 
 * - **注解**: 
 * - **`@ApiModelProperty`**: 描述属性名称和值 
 * ### 自定义 setter 方法 
 * 尽管 Lombok 的 `@Setter` 注解已经提供了所有属性的 setter 方法，但该类中还手动定义了每个属性的 setter 方法。这些手动定义的方法实际上并没有增加任何新的逻辑，因此它们是多余的。 
 * ### 总结 
 * - **优点**: 
 * - 使用了 Lombok 注解简化了代码。 
 * - 使用了 Swagger 注解来描述 API 模型。 
 * - 属性有适当的验证注解（如 `@NotBlank` 和 `@NotNull`）以确保数据的有效性。 
 * - **改进点**: 
 * - 手动定义的 setter 方法是多余的，可以删除这些方法。 
 * - 可以进一步优化属性的命名风格，使其更一致和易读（例如将 `setResource_id` 改为 `setResourceId`）。 
**/
package com.cyberpeace.oj.ojbase.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 获取注意请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class GetNoteReq implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "resource_id", value = "资源编号")
	@NotBlank
	private String resourceId;

	/**
	 * 注释类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(name = "note_type", value = "笔记类型 1=笔记 2=实验报告")
	@NotNull
	private Integer noteType;

	/**
	 * 资源类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(name = "resource_type", value = "资源类型 1=课程 2=课时 3=练习 4=课后练习")
	@NotNull
	private Integer resourceType;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "user_id", value = "创建者")
	private String userId;

	public void setResource_id(String resourceId) {
		this.resourceId = resourceId;
	}

	public void setNote_type(Integer noteType) {
		this.noteType = noteType;
	}

	public void setResource_type(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public void setUser_id(String userId) {
		this.userId = userId;
	}

}
