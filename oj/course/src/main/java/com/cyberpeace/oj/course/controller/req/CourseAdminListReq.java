package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程管理列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see CourseListReq
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseAdminListReq` 的类，它是 `CourseListReq` 类的一个子类。这个类用于处理课程管理列表的请求，并且使用了 Lombok 注解来简化 getter 和 setter 方法的编写。下面是详细的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.controller.req` 
 * - **继承关系**：`CourseAdminListReq` 继承自 `CourseListReq` 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 提供，自动生成 getter 和 setter 方法。 
 * - `@ApiModel`：由 Swagger 提供，用于描述 API 模型。 
 * ### 属性 
 * - **myCourse**：布尔类型，表示是否查询“我的课程”。 
 * - **systemCourse**：布尔类型，表示是否查询“系统课程”。 
 * - **shareCourse**：布尔类型，表示是否查询“共享课程”。 
 * ### 方法 
 * - **setMy_course**：设置 `myCourse` 属性的方法。 
 * - **setSystem_course**：设置 `systemCourse` 属性的方法。 
 * - **setShare_course**：设置 `shareCourse` 属性的方法。 
 * ### 总结 
 * - 这个类主要用于处理后台请求，特别是那些需要区分不同类型的课程（如“我的课程”、“系统课程”和“共享课程”）的请求。 
 * - 使用 Lombok 注解减少了样板代码，使代码更简洁。 
 * - Swagger 注解帮助文档生成工具更好地理解这个类的用途。 
 * ### 改进建议 
 * - 可以考虑将属性名改为驼峰命名法（例如 `myCourse` 而不是 `my_course`），以保持一致性和更好的可读性。 
 * - 如果不需要手动实现 setter 方法，可以直接删除这些方法，因为 Lombok 已经提供了自动化的 getter 和 setter。 
**/
@Getter
@Setter
@ApiModel("后台参数接收")
public class CourseAdminListReq extends CourseListReq {

	/**
	 * 我课程
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(name = "my_course", value = "我的课程")
	private Boolean myCourse;

	/**
	 * 系统课程
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(name = "system_course", value = "系统课程")
	private Boolean systemCourse;

	/**
	 * 分享课程
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(name = "share_course", value = "共享课程")
	private Boolean shareCourse;

	public void setMy_course(Boolean myCourse) {
		this.myCourse = myCourse;
	}

	public void setSystem_course(Boolean systemCourse) {
		this.systemCourse = systemCourse;
	}

	public void setShare_course(Boolean shareCourse) {
		this.shareCourse = shareCourse;
	}
}

