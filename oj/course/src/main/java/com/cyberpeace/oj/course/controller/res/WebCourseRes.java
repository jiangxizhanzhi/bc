package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Web课程结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class WebCourseRes implements Serializable {

	/**
	 * 资源编号
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
	 * 是否收藏
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	/**
	 * 访问量
	 *
	 * @see Integer
	 */
	@ApiModelProperty("访问量")
	private Integer heat;

}
