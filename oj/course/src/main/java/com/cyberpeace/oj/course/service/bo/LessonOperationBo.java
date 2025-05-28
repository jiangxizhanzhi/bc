/**
 * 这段代码定义了一个名为 `LessonOperationBo` 的 Java 类，该类继承自 `OperationLessonBo`。以下是对这段代码的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.course.service.bo` 
 * - **类名**：`LessonOperationBo` 
 * - **继承关系**：`LessonOperationBo` 继承自 `OperationLessonBo` 
 * ### 注解 
 * - **Lombok 注解**： 
 * - `@Getter`：为类的所有字段生成 getter 方法。 
 * - `@Setter`：为类的所有字段生成 setter 方法。 
 * - **Swagger 注解**： 
 * - `@ApiModel`：用于描述这个类作为一个模型对象。 
 * - `@ApiModelProperty`：用于描述类中的某个字段。 
 * ### 字段 
 * - **resolvedDetail**：类型为 `PracticeSolvedBo`，用于存储实操练习的解题详情。 
 * - 注释说明：这个字段表示实操练习的解题详情。 
 * ### 总结 
 * - **功能**：`LessonOperationBo` 类继承了 `OperationLessonBo` 类，并添加了一个新的字段 `resolvedDetail` 来存储实操练习的解题详情。 
 * - **注解使用**：使用 Lombok 和 Swagger 注解来简化代码和文档生成。 
 * - **用途**：这个类可能用于课程管理中记录学生的实操练习解题情况。 
 * 通过这段代码可以看出，`LessonOperationBo` 是一个用于记录和管理学生在课程中的实操练习解题详情的数据传输对象（DTO）。 
**/
package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.practice.service.bo.OperationLessonBo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课实操博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see OperationLessonBo
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class
LessonOperationBo extends OperationLessonBo {

	/**
	 * 解决详情
	 *
	 * @see PracticeSolvedBo
	 */
	@ApiModelProperty("实操练习解题详情")
	private PracticeSolvedBo resolvedDetail;

}
