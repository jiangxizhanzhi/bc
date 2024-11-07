package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 相关课程
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RelatedCourse implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("课程编号")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("课程名称")
	private String name;

	/**
	 * 重量
	 */
	@ApiModelProperty("课程权重")
	private Integer weight;

	/**
	 * 描述
	 */
	@ApiModelProperty("课程简介")
	private String description;

	/**
	 * 方向
	 */
	@ApiModelProperty("之前使用的课程方向")
	private String direction;

	/**
	 * 类别id
	 */
	@ApiModelProperty("课程方向")
	private String categoryId;

	/**
	 * 图标
	 */
	@ApiModelProperty("课程海报")
	private String logo;

	// @ApiModelProperty("课程大纲")
	// private String courseWriteup;

	/**
	 * 困难
	 */
	@ApiModelProperty("课程难度")
	private Integer difficulty;

	/**
	 * 创建用户
	 */
	@ApiModelProperty("课程创建人")
	private String createUser;

	/**
	 * 上次编辑用户
	 */
	@ApiModelProperty("课程最后编辑人")
	private String lastEditUser;

	/**
	 * 电子书
	 */
	@ApiModelProperty("课程类型")
	private Integer eBook;

	/**
	 * 热
	 */
	@ApiModelProperty("课程热度")
	private Integer heat;

	/**
	 * 讲师
	 */
	@ApiModelProperty("课程讲师")
	private String lecturer;

	/**
	 * 得分
	 */
	@ApiModelProperty("课程成绩")
	private Integer score;

	/**
	 * 课程数量
	 */
	@ApiModelProperty("课程编号")
	private String courseNum;

	/**
	 * 方向名称
	 */
	@ApiModelProperty("课程方向名称")
	private String directionName;

	/**
	 * 课计数
	 */
	@ApiModelProperty("课程课时数量")
	private Integer lessonCount;

	/**
	 * 课程时间
	 */
	@ApiModelProperty("课程课时时长")
	private Integer courseDuration = 0;

	/**
	 * 理论计数
	 */
	@ApiModelProperty("理论课时数量")
	private Integer theoryCount;

	/**
	 * 锻炼计数
	 */
	@ApiModelProperty("课程实验课时数量")
	private Integer exerciseCount;

	/**
	 * 建议持续时间
	 */
	@ApiModelProperty("建议学习时长")
	private Integer suggestDuration = 0;

	/**
	 * 面试
	 */
	@ApiModelProperty("是否具有访问权限")
	private Boolean interview;
}