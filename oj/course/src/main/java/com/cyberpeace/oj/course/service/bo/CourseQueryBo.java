package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.course.dao.context.CourseContext;
import lombok.Data;

/**
 * 课程查询Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see CourseContext
 * @since 1.0.0
 */
@Data
public class CourseQueryBo extends CourseContext {

	/**
	 * 我的课程
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
	 * 共享课程
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

}
