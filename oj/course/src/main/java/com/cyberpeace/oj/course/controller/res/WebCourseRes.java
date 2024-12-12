package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Web课程结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class WebCourseRes implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("名称")
	private String name;

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
	 * is 集合
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	/**
	 * 热
	 *
	 * @see Integer
	 */
	@ApiModelProperty("访问量")
	private Integer heat;

}
