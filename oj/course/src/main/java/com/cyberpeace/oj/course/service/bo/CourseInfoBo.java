package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程信息Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@ApiModel("课程基本信息")
public class CourseInfoBo implements Serializable {

	/**
	 * 默认重量
	 */
	public final static Integer DEFAULT_WEIGHT = 1;

	/**
	 * 课程编号
	 */
	@ApiModelProperty("课程编号")
	private String resourceId;

	/**
	 * 课程名称
	 */
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 是否公开
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 课程简介
	 */
	@ApiModelProperty("课程简介")
	private String description;

	/**
	 * 课程方向
	 */
	@ApiModelProperty("课程方向")
	private String categoryId;

	/**
	 * 课程海报
	 */
	@ApiModelProperty("课程海报")
	private String logo;

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
	 * 课程创建人
	 */
	@ApiModelProperty("课程创建人")
	private String createUserId;

	/**
	 * 课程最后编辑人
	 */
	@ApiModelProperty("课程最后编辑人")
	private String modifyUserId;

	/**
	 * 课程类型 0=
	 */
	@ApiModelProperty("课程类型 0=")
	private Integer courseType;

	/**
	 * 课程热度
	 */
	@ApiModelProperty("课程热度")
	private Integer heat;

	/**
	 * 课程讲师
	 */
	@ApiModelProperty("课程讲师")
	private String lecturer;

	/**
	 * 课程学分
	 */
	@ApiModelProperty("课程学分")
	private Integer score;

	/**
	 * 课程编号
	 */
	@ApiModelProperty("课程编号")
	private String courseNum;

	/**
	 * 课程方向名称
	 */
	@ApiModelProperty("课程方向名称")
	private String directionName;

	/**
	 * 课程课时数量
	 */
	@ApiModelProperty("课程课时数量")
	private Integer lessonCount;

	/**
	 * 课程课时时长
	 */
	@ApiModelProperty("课程课时时长")
	private Integer courseDuration;

	/**
	 * 理论课时数量
	 */
	@ApiModelProperty("理论课时数量")
	private Integer theoryCount;

	/**
	 * 课程实验课时数量
	 */
	@ApiModelProperty("课程实验课时数量")
	private Integer exerciseCount;

	/**
	 * 建议学习时长
	 */
	@ApiModelProperty("建议学习时长")
	private Integer suggestDuration;

	/**
	 * 是否收藏
	 */
	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	/**
	 * 课程权重
	 */
	@ApiModelProperty("课程权重")
	private Integer weight = DEFAULT_WEIGHT;

	/**
	 * 是否具有访问权限
	 */
	@ApiModelProperty("是否具有访问权限")
	private Boolean interview;
}
