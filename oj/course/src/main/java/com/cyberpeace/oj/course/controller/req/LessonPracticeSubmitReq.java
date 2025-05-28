/**
 * 这段代码定义了一个名为 `LessonPracticeSubmitReq` 的 Java 类，用于表示提交课程实践的请求体。以下是对该类的简要分析： 
 * ### 类名及包信息 
 * - **包名**: `com.cyberpeace.oj.course.controller.req` 
 * - **类名**: `LessonPracticeSubmitReq` 
 * ### 注解 
 * - 使用了 `@Getter` 和 `@Setter` 注解来自 Lombok 库，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 `@ApiModel` 注解，用于在 Swagger 文档中描述这个模型。 
 * - 每个字段使用了 `@ApiModelProperty` 注解来提供字段的描述。 
 * ### 字段 
 * 1. **courseId** 
 * - 类型: `String` 
 * - 注解: `@ApiModelProperty("课程编号")` 和 `@NotBlank` 
 * - 描述: 课程编号，不能为空。 
 * 2. **lessonId** 
 * - 类型: `String` 
 * - 注解: `@ApiModelProperty("课时编号")` 和 `@NotBlank` 
 * - 描述: 课时编号，不能为空。 
 * 3. **answers** 
 * - 类型: `List<PracticeSubmitReq>` 
 * - 注解: `@ApiModelProperty("答案提交")` 
 * - 描述: 提交的答案列表，类型为 `PracticeSubmitReq`。 
 * ### 实现接口 
 * - 实现了 `Serializable` 接口，说明对象可以序列化。 
 * ### 总结 
 * - `LessonPracticeSubmitReq` 类用于封装提交课程实践所需的信息，包括课程编号、课时编号以及答案列表。 
 * - 使用了 Lombok 的注解以减少样板代码，并且通过 Swagger 注解提供了文档化的支持。 
 * - 类中的每个字段都有相应的注解，确保字段的必填性和文档化描述。 
 * 该类适用于处理与课程实践相关的请求，确保数据的完整性和可读性。 
**/
package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 课实践提交请求体
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
public class LessonPracticeSubmitReq implements Serializable {

	/**
	 * 课程id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	@NotBlank
	private String courseId;

	/**
	 * 课id
	 *
	 * @see String
	 */
	@ApiModelProperty("课时编号")
	@NotBlank
	private String lessonId;

	/**
	 * 答案
	 *
	 * @see List<PracticeSubmitReq>
	 */
	@ApiModelProperty("答案提交")
	private List<PracticeSubmitReq> answers;

}
