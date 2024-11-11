package com.cyberpeace.oj.course.dao.context;

import lombok.Data;

import java.io.Serializable;

/**
 * 特殊课程背景
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class SpecialCourseContext implements Serializable {

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
