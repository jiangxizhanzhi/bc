package com.cyberpeace.oj.course.dao.context;

import lombok.Data;

import java.io.Serializable;

/**
 * 特殊课程上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class SpecialCourseContext implements Serializable {

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

	public Boolean getMyCourse() {
		return null == myCourse ? true : myCourse;
	}

	public Boolean getSystemCourse() {
		return null == systemCourse ? true : systemCourse;
	}

	public Boolean getShareCourse() {
		return null == shareCourse ? true : shareCourse;
	}
}
