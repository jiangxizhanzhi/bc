package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 课堂群请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassroomGroupReq` 的 Java 类，用于表示课堂群请求体。以下是对该代码的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.oj.course.controller.req` 
 * - **类名**: `ClassroomGroupReq` 
 * - **功能**: 用于封装课堂群相关的请求数据。 
 * ### 主要特性 
 * - **注解**: 
 * - `@Getter` 和 `@Setter`: Lombok 注解，自动生成属性的 getter 和 setter 方法。 
 * - `@ApiModel`: Swagger 注解，用于描述 API 模型。 
 * - `@ApiModelProperty`: Swagger 注解，用于描述模型属性。 
 * - `@NotBlank`: JSR 380 (Bean Validation) 注解，确保字符串不为空或空白。 
 * - `@NotNull`: JSR 380 注解，确保对象不为 null。 
 * - **实现接口**: 实现了 `Serializable` 接口，表明该类的对象可以序列化。 
 * ### 属性 
 * - **classroomId** 
 * - **类型**: `String` 
 * - **注释**: 课堂 ID。 
 * - **约束**: 必须提供且不能为空（通过 `@NotBlank` 约束）。 
 * - **groupCount** 
 * - **类型**: `Integer` 
 * - **注释**: 每组人数。 
 * - **约束**: 必须提供且不能为 null（通过 `@NotNull` 约束）。 
 * ### 总结 
 * - **用途**: 用于接收前端传递的课堂群相关信息。 
 * - **验证**: 提供了字段级的验证规则，确保传入的数据符合预期格式。 
 * - **可读性**: 通过 Swagger 注解，便于文档生成和 API 使用者理解。 
 * 该类的设计简洁明了，并且通过注解实现了必要的数据校验，提高了代码的健壮性和可维护性。 
**/
@Getter
@Setter
@ApiModel
public class ClassroomGroupReq implements Serializable {

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
	 * 群计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("每组人数")
	@NotNull
	private Integer groupCount;

}

