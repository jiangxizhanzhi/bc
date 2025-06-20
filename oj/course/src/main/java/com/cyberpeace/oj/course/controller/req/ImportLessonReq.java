package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 导入课请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ImportLessonReq` 的 Java 类，用于表示导入课程请求的请求体。以下是对该代码的简要分析： 
 * ### 包声明和导入 
 * - `package com.cyberpeace.oj.course.controller.req;` 
 * - 指定了类所在的包。 
 * - 导入的其他类： 
 * - `Serializable`: 标记该类可以序列化。 
 * - `List`: 用于存储多个字符串对象的集合。 
 * - `NotBlank` 和 `NotEmpty`: 用于验证字段不能为空或空字符串。 
 * - `OjBusinessErrorCode`: 业务错误码常量类。 
 * - `ApiModel` 和 `ApiModelProperty`: 用于 Swagger 文档生成。 
 * - `Getter` 和 `Setter`: Lombok 注解，自动生成 getter 和 setter 方法。 
 * ### 类声明 
 * - `@Getter` 和 `@Setter`: Lombok 注解，自动生成字段的 getter 和 setter 方法。 
 * - `@ApiModel(value = "导入课请求体")`: 用于 Swagger 文档生成，描述该模型为“导入课请求体”。 
 * - `public class ImportLessonReq implements Serializable`: 定义了 `ImportLessonReq` 类，并实现了 `Serializable` 接口。 
 * ### 字段 
 * 1. **courseId** 
 * - 类型: `String` 
 * - 注释: “课程编号” 
 * - 验证注解: `@NotBlank(message = OjBusinessErrorCode.COURSE_INFORMATION_NOT_NULL)` 
 * - 表示该字段不能为空或空字符串，否则会抛出相应的业务错误码。 
 * - Swagger 注解: `@ApiModelProperty("课程编号")` 
 * 2. **lessonIds** 
 * - 类型: `List<String>` 
 * - 注释: “课时编号列表” 
 * - 验证注解: `@NotEmpty(message = OjBusinessErrorCode.LESSON_INFORMATION_NOT_NULL)` 
 * - 表示该字段不能为空，否则会抛出相应的业务错误码。 
 * - Swagger 注解: `@ApiModelProperty("课时编号列表")` 
 * ### 总结 
 * - `ImportLessonReq` 类主要用于处理导入课程请求的数据。 
 * - 通过 `@NotBlank` 和 `@NotEmpty` 注解确保了必要的字段不为空。 
 * - 使用 Swagger 注解来生成 API 文档。 
 * - 实现了 `Serializable` 接口，使得对象可以被序列化。 
 * 这个类在处理导入课程请求时，能够有效地验证输入数据的合法性，并提供清晰的文档说明。 
**/
@Getter
@Setter
@ApiModel
public class ImportLessonReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 课程id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	@NotBlank(message = OjBusinessErrorCode.COURSE_INFORMATION_NOT_NULL)
	private String courseId;

	/**
	 * 课id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("课时编号列表")
	@NotEmpty(message = OjBusinessErrorCode.LESSON_INFORMATION_NOT_NULL)
	private List<String> lessonIds;

}

