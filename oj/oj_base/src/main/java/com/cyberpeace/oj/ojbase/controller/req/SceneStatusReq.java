package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 场景状态请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SceneStatusReq` 的 Java 类，用于表示场景状态请求体。以下是对其主要特性和功能的简要分析： 
 * ### 包声明 
 * - `com.cyberpeace.oj.ojbase.controller.req`: 这个类位于 `com.cyberpeace.oj.ojbase.controller.req` 包下，表明它可能是一个请求对象，用于处理某个控制器中的场景状态相关的请求。 
 * ### 导入的库 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty`: 这些注解用于 Swagger 文档生成，帮助文档工具自动生成 API 文档。 
 * - `lombok.Getter` 和 `lombok.Setter`: Lombok 注解，用来自动生成 getter 和 setter 方法。 
 * - `javax.validation.constraints.NotBlank` 和 `javax.validation.constraints.NotNull`: 这些注解用于数据验证，确保字段不为空或不为 null。 
 * ### 类定义 
 * - `SceneStatusReq` 类实现了 `Serializable` 接口，意味着它可以被序列化，这通常是为了在网络上传输对象或将其保存到磁盘上。 
 * ### 字段 
 * - `resourceId`: 一个 `String` 类型的字段，表示资源编号，使用了 `@NotBlank` 注解，确保这个字段不能为空。 
 * - `status`: 一个 `Integer` 类型的字段，表示状态（如启动、暂停），使用了 `@NotNull` 注解，确保这个字段不为空。 
 * - `roomId`: 一个 `String` 类型的字段，表示房间编号（实际文档中描述为课程表编号）。 
 * ### 注解 
 * - `@ApiModel`：描述该类是一个模型对象，用于 Swagger 文档生成。 
 * - `@ApiModelProperty`：用于描述模型属性，提供额外的元数据，同样用于 Swagger 文档生成。 
 * ### 总结 
 * `SceneStatusReq` 类主要用于接收与场景状态相关的请求数据，包括资源编号、状态和房间编号。通过使用 Lombok 和 JSR 303 验证注解，简化了代码并增强了数据的有效性检查。同时，通过 Swagger 注解，方便生成 API 文档。 
**/
@Getter
@Setter
@ApiModel
public class SceneStatusReq implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	@NotBlank
	private String resourceId;

	/**
	 * 状态
	 *
	 * @see Integer
	 */
	@ApiModelProperty("1=启动 3=暂停")
	@NotNull
	private Integer status;

	/**
	 * 房间id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程表编号")
	private String roomId;

}

