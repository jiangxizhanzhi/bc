package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 管理课程结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class AdminCourseRes implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程资源编号")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty("课程简介")
	private String description;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty("课程封面")
	private String logo;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	@ApiModelProperty("学分")
	private Integer score;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程分类")
	private String categoryId;

	/**
	 * 大纲
	 *
	 * @see String
	 */
	@ApiModelProperty("课程大纲")
	private String outline;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程难度")
	private Integer difficulty;

	/**
	 * 等级
	 *
	 * @see Integer
	 */
	@ApiModelProperty("等级,type=51")
	private Integer rank;

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
	 * 讲师
	 *
	 * @see String
	 */
	@ApiModelProperty("课程讲师")
	private String lecturer;

	/**
	 * 课程数量
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String courseNum;

	/**
	 * 岗位
	 *
	 * @see Integer
	 */
	@ApiModelProperty("岗位")
	private Integer role;
}
