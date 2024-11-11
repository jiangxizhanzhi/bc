package com.cyberpeace.oj.course.service.bo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程信息 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel("课程基本信息")
public class CourseInfoBo implements Serializable {

	/**
	 * 默认权重
	 */
	public final static Integer DEFAULT_WEIGHT = 1;

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
	 * 是公共
	 */
	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	/**
	 * 描述
	 */
	@ApiModelProperty("课程简介")
	private String description;

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

	/**
	 * 大纲
	 */
	@ApiModelProperty("课程大纲")
	private String outline;

	/**
	 * 困难
	 */
	@ApiModelProperty("课程难度")
	private Integer difficulty;

	/**
	 * 创造用户id
	 */
	@ApiModelProperty("课程创建人")
	private String createUserId;

	/**
	 * 修改用户id
	 */
	@ApiModelProperty("课程最后编辑人")
	private String modifyUserId;

	/**
	 * 课程类型
	 */
	@ApiModelProperty("课程类型 0=")
	private Integer courseType;

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
	@ApiModelProperty("课程学分")
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
	private Integer courseDuration;

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
	private Integer suggestDuration;

	/**
	 * is 集合
	 */
	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	/**
	 * 重量
	 */
	@ApiModelProperty("课程权重")
	private Integer weight = DEFAULT_WEIGHT;

	/**
	 * 面试
	 */
	@ApiModelProperty("是否具有访问权限")
	private Boolean interview;
}
