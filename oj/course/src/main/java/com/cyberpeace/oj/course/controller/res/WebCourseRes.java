package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Web课程结果
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class WebCourseRes implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("资源编号")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty("名称")
	private String name;

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
	 * 难度
	 */
	@ApiModelProperty("课程难度")
	private Integer difficulty;

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
	 * is 集合
	 */
	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	/**
	 * 热
	 */
	@ApiModelProperty("访问量")
	private Integer heat;

}
