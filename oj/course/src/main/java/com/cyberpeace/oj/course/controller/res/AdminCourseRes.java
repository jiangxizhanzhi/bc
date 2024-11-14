package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理课程结果
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel
public class AdminCourseRes implements Serializable {

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
	 * 课程简介
	 */
	@ApiModelProperty("课程简介")
	private String description;

	/**
	 * 课程封面
	 */
	@ApiModelProperty("课程封面")
	private String logo;

	/**
	 * 学分
	 */
	@ApiModelProperty("学分")
	private Integer score;

	/**
	 * 课程分类
	 */
	@ApiModelProperty("课程分类")
	private String categoryId;

	/**
	 * 课程大纲
	 */
	@ApiModelProperty("课程大纲")
	private String outline;

	/**
	 * 课程难度
	 */
	@ApiModelProperty("课程难度")
	private Integer difficulty;

	/**
	 * 课程类型
	 */
	@ApiModelProperty("课程类型")
	private Integer courseType;

	/**
	 * 是否公开
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 课程讲师
	 */
	@ApiModelProperty("课程讲师")
	private String lecturer;

	/**
	 * 课程编号
	 */
	@ApiModelProperty("课程编号")
	private String courseNum;
}
