package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理课程结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel
public class AdminCourseRes implements Serializable {

	/**
	 * 课程资源编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课程资源编号")
	private String resourceId;

	/**
	 * 课程名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 课程简介
	 *
	 * @see String
	 */
	@ApiModelProperty("课程简介")
	private String description;

	/**
	 * 课程封面
	 *
	 * @see String
	 */
	@ApiModelProperty("课程封面")
	private String logo;

	/**
	 * 学分
	 *
	 * @see Integer
	 */
	@ApiModelProperty("学分")
	private Integer score;

	/**
	 * 课程分类
	 *
	 * @see String
	 */
	@ApiModelProperty("课程分类")
	private String categoryId;

	/**
	 * 课程大纲
	 *
	 * @see String
	 */
	@ApiModelProperty("课程大纲")
	private String outline;

	/**
	 * 课程难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程难度")
	private Integer difficulty;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程类型")
	private Integer courseType;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 课程讲师
	 *
	 * @see String
	 */
	@ApiModelProperty("课程讲师")
	private String lecturer;

	/**
	 * 课程编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String courseNum;
}
