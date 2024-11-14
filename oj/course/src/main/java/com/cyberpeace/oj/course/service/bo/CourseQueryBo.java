package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.course.dao.context.CourseContext;
import lombok.Data;

/**
 * 课程查询Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class CourseQueryBo extends CourseContext {

	/**
	 * 我的课程
	 */
	private Boolean myCourse;

	/**
	 * 系统课程
	 */
	private Boolean systemCourse;

	/**
	 * 共享课程
	 */
	private Boolean shareCourse;

	/**
	 * 用户id
	 */
	private String userId;

}
