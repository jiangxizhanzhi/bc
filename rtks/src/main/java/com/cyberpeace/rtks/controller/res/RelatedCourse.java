package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RelatedCourse implements Serializable {

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
	 * 课程权重
	 */
	@ApiModelProperty("课程权重")
	private Integer weight;

	/**
	 * 课程简介
	 */
	@ApiModelProperty("课程简介")
	private String description;

	/**
	 * 之前使用的课程方向
	 */
	@ApiModelProperty("之前使用的课程方向")
	private String direction;

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
	 * 课程难度
	 */
	@ApiModelProperty("课程难度")
	private Integer difficulty;

	/**
	 * 课程创建人
	 */
	@ApiModelProperty("课程创建人")
	private String createUser;

	/**
	 * 课程最后编辑人
	 */
	@ApiModelProperty("课程最后编辑人")
	private String lastEditUser;

	/**
	 * 课程类型
	 */
	@ApiModelProperty("课程类型")
	private Integer eBook;

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
	 * 课程成绩
	 */
	@ApiModelProperty("课程成绩")
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
	private Integer courseDuration = 0;

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
	private Integer suggestDuration = 0;

	/**
	 * 是否具有访问权限
	 */
	@ApiModelProperty("是否具有访问权限")
	private Boolean interview;
}