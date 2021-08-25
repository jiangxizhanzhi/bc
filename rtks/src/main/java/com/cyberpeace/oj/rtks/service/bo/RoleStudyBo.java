package com.cyberpeace.oj.rtks.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户学习记录
 */
@Data
public class RoleStudyBo implements Serializable {

	/**
	 * 总课程数
	 */
	private String totalCourses;

	/**
	 * 已完成理论课时数
	 */
	private String finishedTheoryLessons;

	/**
	 * 未完成理论课时数
	 */
	private String unFinishedTheoryLessons;

	/**
	 * 已完成实验课时数
	 */
	private String finishedPracticeLessons;

	/**
	 * 未完成实验课时数
	 */
	private String unFinishedPracticeLessons;

}
