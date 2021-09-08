package com.cyberpeace.rtks.service.bo;

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
	private Integer totalCourses;

	/**
	 * 完成的总课程数
	 */
	private Integer finishedCourses;

	/**
	 * 已完成理论课时数
	 */
	private Integer finishedTheoryLessons;

	/**
	 * 未完成理论课时数
	 */
	private Integer unFinishedTheoryLessons;

	/**
	 * 已完成实验课时数
	 */
	private Integer finishedPracticeLessons;

	/**
	 * 未完成实验课时数
	 */
	private Integer unFinishedPracticeLessons;

}
