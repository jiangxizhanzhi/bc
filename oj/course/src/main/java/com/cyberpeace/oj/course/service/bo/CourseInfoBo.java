package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程信息Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
@ApiModel("课程基本信息")
public class CourseInfoBo implements Serializable {

	/**
	 * 默认重量
	 *
	 * @see Integer
	 */
	public final static Integer DEFAULT_WEIGHT = 1;

	/**
	 * 课程编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String resourceId;

	/**
	 * 课程名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 是否公开
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 课程简介
	 *
	 * @see String
	 */
	@ApiModelProperty("课程简介")
	private String description;

	/**
	 * 课程方向
	 *
	 * @see String
	 */
	@ApiModelProperty("课程方向")
	private String categoryId;

	/**
	 * 课程海报
	 *
	 * @see String
	 */
	@ApiModelProperty("课程海报")
	private String logo;

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
	 * 课程创建人
	 *
	 * @see String
	 */
	@ApiModelProperty("课程创建人")
	private String createUserId;

	/**
	 * 课程最后编辑人
	 *
	 * @see String
	 */
	@ApiModelProperty("课程最后编辑人")
	private String modifyUserId;

	/**
	 * 课程类型 0=
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程类型 0=")
	private Integer courseType;

	/**
	 * 课程热度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程热度")
	private Integer heat;

	/**
	 * 课程讲师
	 *
	 * @see String
	 */
	@ApiModelProperty("课程讲师")
	private String lecturer;

	/**
	 * 课程学分
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程学分")
	private Integer score;

	/**
	 * 课程编号
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String courseNum;

	/**
	 * 课程方向名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程方向名称")
	private String directionName;

	/**
	 * 课程课时数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程课时数量")
	private Integer lessonCount;

	/**
	 * 课程课时时长
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程课时时长")
	private Integer courseDuration;

	/**
	 * 理论课时数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("理论课时数量")
	private Integer theoryCount;

	/**
	 * 课程实验课时数量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程实验课时数量")
	private Integer exerciseCount;

	/**
	 * 建议学习时长
	 *
	 * @see Integer
	 */
	@ApiModelProperty("建议学习时长")
	private Integer suggestDuration;

	/**
	 * 是否收藏
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	/**
	 * 课程权重
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程权重")
	private Integer weight = DEFAULT_WEIGHT;

	/**
	 * 是否具有访问权限
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否具有访问权限")
	private Boolean interview;
}
