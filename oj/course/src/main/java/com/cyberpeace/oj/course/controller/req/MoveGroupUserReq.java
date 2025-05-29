package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 移动群用户请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `MoveGroupUserReq` 的 Java 类，用于表示移动群用户请求的数据结构。以下是对该代码的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.oj.course.controller.req` 
 * - **类名**: `MoveGroupUserReq` 
 * - **功能**: 用于表示移动群用户请求的数据结构。 
 * ### 注解 
 * - **`@Getter` 和 `@Setter`**: Lombok 注解，自动生成属性的 getter 和 setter 方法。 
 * - **`@ApiModel`**: Swagger 注解，用于描述这个类作为 API 模型对象。 
 * - **`@ApiModelProperty`**: Swagger 注解，用于描述类中的各个属性。 
 * - **`@NotBlank`**: JSR 380 (Bean Validation) 注解，确保字段不为空白（即不为 null、空字符串或仅含空白字符）。 
 * ### 属性 
 * - **`fromGroupId`**: 原分组 ID。类型为 `String`，不能为空。 
 * - **`toGroupId`**: 目的分组 ID。类型为 `String`，不能为空。 
 * - **`userId`**: 用户 ID。类型为 `String`，不能为空。 
 * - **`classroomId`**: 课堂 ID。类型为 `String`，不能为空。 
 * ### 实现接口 
 * - **`Serializable`**: 表示该类可以序列化，可以在网络上传输或持久化存储。 
 * ### 总结 
 * `MoveGroupUserReq` 是一个用于处理移动群用户请求的数据传输对象（DTO）。它包含了四个必填字段：原分组 ID、目的分组 ID、用户 ID 和课堂 ID。这些字段通过注解来验证其有效性，并且可以通过 Lombok 自动生成 getter 和 setter 方法。该类还实现了 `Serializable` 接口，使其能够进行序列化操作。 
**/
@Getter
@Setter
@ApiModel
public class MoveGroupUserReq implements Serializable {

	/**
	 * 从群id
	 *
	 * @see String
	 */
	@ApiModelProperty("原分组")
	@NotBlank
	private String fromGroupId;

	/**
	 * 自群id
	 *
	 * @see String
	 */
	@ApiModelProperty("目的分组")
	@NotBlank
	private String toGroupId;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户编号")
	@NotBlank
	private String userId;

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	@ApiModelProperty("课堂编号")
	@NotBlank
	private String classroomId;

}

