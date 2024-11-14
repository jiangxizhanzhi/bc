package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理课程统计学结果
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class AdminCourseStatisticsRes implements Serializable {

	/**
	 * 课程资源编号
	 */
	@ApiModelProperty("课程资源编号")
	private String resourceId;

	/**
	 * 课程名称
	 */
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 课程分类
	 */
	@ApiModelProperty("课程分类")
	private String categoryName;

	/**
	 * 课程难度
	 */
	@ApiModelProperty("课程难度")
	private Integer difficulty;

	/**
	 * 课时数
	 */
	@ApiModelProperty("课时数")
	private Integer lessonCount;

	/**
	 * 学习人数
	 */
	@ApiModelProperty("学习人数")
	private Integer heat;

	/**
	 * 提交报告数
	 */
	@ApiModelProperty("提交报告数")
	private String finishReportCount;

	/**
	 * 创建者
	 */
	@ApiModelProperty("创建者")
	private String createUser;
}
