package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 课程统计结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class CourseStatisticsRes implements Serializable {

	/**
	 * 已完成课程数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("已完成课程数")
	private Integer completedCourseCount;

	/**
	 * 课程数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程数")
	private Integer courseCount;

	/**
	 * 学习记录
	 */
	@ApiModelProperty("学习记录")
	private List<LessonRecordRes> lessonRecords;
}
