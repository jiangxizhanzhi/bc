package com.cyberpeace.oj.course.dao.context;

import lombok.Data;

import java.io.Serializable;

/**
 * 特殊课程背景
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SpecialCourseContext implements Serializable {

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
