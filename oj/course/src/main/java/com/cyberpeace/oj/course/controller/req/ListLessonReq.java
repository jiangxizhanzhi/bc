package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 列表课请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ListLessonReq` 的 Java 类，该类用于处理课时列表查询的请求参数。以下是对代码的简要分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.oj.course.controller.req` 
 * - **继承关系**: `ListLessonReq` 继承自 `PageReqBase` 
 * ### 注解 
 * - **Lombok 注解**: 
 * - `@Getter`: 自动生成所有字段的 getter 方法。 
 * - `@Setter`: 自动生成所有字段的 setter 方法。 
 * - **Swagger 注解**: 
 * - `@ApiModel("课时列表查询")`: 定义了这个类的 Swagger 模型名称。 
 * - `@ApiModelProperty(...)`: 为类中的字段提供 Swagger 文档描述。 
 * ### 字段 
 * - `type`: 整数类型，表示课时类型（0=理论课，1=实验课），用于课程下课时列表筛选。 
 * - `courseId`: 字符串类型，表示课程编号。 
 * - `exceptCourse`: 字符串类型，表示要排除的课程编号，用于课时导入筛选。 
 * - `categoryId`: 字符串类型，表示课程方向筛选条件，用于课时导入筛选。 
 * ### 方法 
 * - `setCourse_id(String courseId)`: 设置课程编号的方法。 
 * - `setExcept_course(String exceptCourse)`: 设置要排除的课程编号的方法。 
 * - `setCategory_id(String categoryId)`: 设置课程方向筛选条件的方法。 
 * ### 总结 
 * - 这个类主要用于接收和处理课时列表查询的请求参数。 
 * - 使用了 Lombok 注解来简化 getter 和 setter 方法的生成。 
 * - 通过 Swagger 注解提供了文档化的说明，方便 API 使用者理解各个字段的用途。 
 * - 继承自 `PageReqBase` 类，可能包含了分页相关的逻辑。 
**/
@Getter
@Setter
@ApiModel("课时列表查询")
public class ListLessonReq extends PageReqBase {

	/**
	 * 类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时类型 0=理论课 1=实验课 用于课程下课时列表筛选")
	private Integer type;

	/**
	 * 课程id
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "course_id", value = "课程编号")
	private String courseId;

	/**
	 * 除了课程
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "except_course", value = "要排除的课程编号 用于课时导入筛选")
	private String exceptCourse;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "category_id", value = "课程方向筛选条件 用于课时导入筛选")
	private String categoryId;

	public void setCourse_id(String courseId) {
		this.courseId = courseId;
	}

	public void setExcept_course(String exceptCourse) {
		this.exceptCourse = exceptCourse;
	}

	public void setCategory_id(String categoryId) {
		this.categoryId = categoryId;
	}
}

