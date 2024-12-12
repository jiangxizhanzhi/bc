package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 课程信息 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel("课程基本信息")
public class CourseInfoBo implements Serializable {

	/**
	 * 默认权重
	 *
	 * @see Integer
	 */
	public final static Integer DEFAULT_WEIGHT = 1;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String resourceId;

	/**
	 * 名称
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
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty("课程简介")
	private String description;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程方向")
	private String categoryId;

	/**
	 * 图标
	 *
	 * @see String
	 */
	@ApiModelProperty("课程海报")
	private String logo;

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
	 * 创造用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程创建人")
	private String createUserId;

	/**
	 * 修改用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程最后编辑人")
	private String modifyUserId;

	/**
	 * 课程类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程类型 0=")
	private Integer courseType;

	/**
	 * 热
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程热度")
	private Integer heat;

	/**
	 * 讲师
	 *
	 * @see String
	 */
	@ApiModelProperty("课程讲师")
	private String lecturer;

	/**
	 * 得分
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程学分")
	private Integer score;

	/**
	 * 课程数量
	 *
	 * @see String
	 */
	@ApiModelProperty("课程编号")
	private String courseNum;

	/**
	 * 方向名称
	 *
	 * @see String
	 */
	@ApiModelProperty("课程方向名称")
	private String directionName;

	/**
	 * 课计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程课时数量")
	private Integer lessonCount;

	/**
	 * 课程时间
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程课时时长")
	private Integer courseDuration;

	/**
	 * 理论计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("理论课时数量")
	private Integer theoryCount;

	/**
	 * 锻炼计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程实验课时数量")
	private Integer exerciseCount;

	/**
	 * 建议持续时间
	 *
	 * @see Integer
	 */
	@ApiModelProperty("建议学习时长")
	private Integer suggestDuration;

	/**
	 * is 集合
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	/**
	 * 重量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课程权重")
	private Integer weight = DEFAULT_WEIGHT;

	/**
	 * 面试
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否具有访问权限")
	private Boolean interview;

	/**
	 * 岗位
	 *
	 * @see Integer
	 */
	private Integer role;
}
