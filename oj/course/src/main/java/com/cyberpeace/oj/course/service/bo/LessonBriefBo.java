/**
 * 这段代码定义了一个名为 `LessonBriefBo` 的 Java 类，该类用于表示课程简介。以下是对其主要特性和功能的简要分析： 
 * ### 类定义 
 * - **包声明**：该类位于 `com.cyberpeace.oj.course.service.bo` 包中。 
 * - **继承**：`LessonBriefBo` 实现了 `Serializable` 接口，这意味着该类的对象可以被序列化，以便在网络上传输或持久化存储。 
 * ### 注解 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：这些注解自动生成所有字段的 getter 和 setter 方法，从而减少样板代码。 
 * - **Swagger 注解**： 
 * - `@ApiModel`：此注解标记该类为 Swagger 模型对象，通常用于生成 API 文档。 
 * - `@ApiModelProperty`：用于描述模型属性，提供属性的描述信息，有助于 API 文档的生成。 
 * ### 字段 
 * - **courseId**：表示课程的唯一标识符，类型为 `String`。 
 * - **courseName**：表示课程的名称，类型为 `String`。 
 * - **lessonId**：表示课时（lesson）的唯一标识符，类型为 `String`。 
 * - **lessonName**：表示课时的名称，类型为 `String`。 
 * ### 总结 
 * `LessonBriefBo` 类主要用于封装课程的基本信息，如课程编号、课程名称、课时编号及课时名称等。通过使用 Lombok 的注解，简化了代码量并提高了可读性。此外，通过 Swagger 注解，便于生成文档，提高 API 的可维护性和易用性。 
**/
package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 课程简介 bo
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
public class LessonBriefBo implements Serializable {

	/**
	 * 课程id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String courseId;

	/**
	 * 课程名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程名称")
	private String courseName;

	/**
	 * 课id
	 *
	 * @see String
	 */
	@ApiModelProperty("课时编号")
	private String lessonId;

	/**
	 * 课名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课时名称")
	private String lessonName;

}
