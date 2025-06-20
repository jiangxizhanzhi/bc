package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 课堂场景请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassroomSceneReq` 的 Java 类，用于表示课堂场景的请求体。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **类名**: `ClassroomSceneReq` 
 * - **包**: `com.cyberpeace.oj.course.controller.req` 
 * - **注解**: 
 * - `@Getter` 和 `@Setter`: Lombok 注解，自动生成 getter 和 setter 方法。 
 * - `@ApiModel`: Swagger 注解，用于描述模型。 
 * - `@Getter` 和 `@Setter`: Lombok 注解，自动生成 getter 和 setter 方法。 
 * ### 实现接口 
 * - `Serializable`: 标记该类可以被序列化。 
 * ### 成员变量 
 * - **classroomId**: 
 * - 类型: `String` 
 * - 注释: "课堂编号" 
 * - 注解: 
 * - `@ApiModelProperty`: 描述成员变量。 
 * - `@NotBlank`: 验证注解，确保该字段不为空或空白字符串。 
 * ### 总结 
 * - **用途**: 该类用于接收课堂场景请求，并包含一个必填的 `classroomId` 字段。 
 * - **框架**: 使用了 Lombok 和 Swagger 框架简化代码和文档生成。 
 * - **验证**: 使用了 `@NotBlank` 注解来确保 `classroomId` 字段不为空。 
 * 这个类主要用于处理与课堂相关的请求，并确保请求中包含有效的课堂编号。 
**/
@Getter
@Setter
@ApiModel
public class ClassroomSceneReq implements Serializable {
		
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

}

