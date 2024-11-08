package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.oj.course.dao.context.CourseContext;
import lombok.Data;

/**
 * 课程查询 bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class CourseQueryBo extends CourseContext {

	/**
	 * 我课程
	 */
	private Boolean myCourse;

	/**
	 * 系统课程
	 */
	private Boolean systemCourse;

	/**
	 * 分享课程
	 */
	private Boolean shareCourse;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 岗位
	 */
	private Integer role;

}
