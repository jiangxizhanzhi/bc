package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.course.dao.context.CourseContext;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程查询 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see CourseContext
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseQueryBo` 的类，该类是 `com.cyberpeace.oj.course.service.bo` 包的一部分。这个类的主要目的是作为课程查询的数据传输对象（DTO），用于封装与课程查询相关的数据。 
 * ### 类概述 
 * - **继承关系**：`CourseQueryBo` 继承自 `CourseContext` 类。 
 * - **注解**：使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法，从而简化代码并提高可读性。 
 * ### 属性 
 * - `myCourse`：布尔类型，表示是否查询属于当前用户的课程。 
 * - `systemCourse`：布尔类型，表示是否查询系统提供的课程。 
 * - `shareCourse`：布尔类型，表示是否查询可以共享的课程。 
 * - `userId`：字符串类型，表示用户ID，用于查询特定用户的课程。 
 * - `role`：整数类型，表示用户的岗位或角色。 
 * ### 用途 
 * `CourseQueryBo` 类主要用于封装课程查询的参数，这些参数可以通过不同的组合来过滤和获取特定类型的课程。例如，可以根据用户ID、课程类型（如我的课程、系统课程、分享课程）以及用户的岗位来查询课程。 
 * ### 总结 
 * 这个类是一个典型的 DTO，用于在服务层和控制器层之间传递数据。通过继承 `CourseContext` 类，它可能包含了一些上下文信息或默认行为。属性的设计使得它可以灵活地满足不同场景下的课程查询需求。 
**/
@Getter
@Setter
public class CourseQueryBo extends CourseContext {

	/**
	 * 我课程
	 *
	 * @see Boolean
	 */
	private Boolean myCourse;

	/**
	 * 系统课程
	 *
	 * @see Boolean
	 */
	private Boolean systemCourse;

	/**
	 * 分享课程
	 *
	 * @see Boolean
	 */
	private Boolean shareCourse;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	private String userId;

	/**
	 * 岗位
	 *
	 * @see Integer
	 */
	private Integer role;

}

