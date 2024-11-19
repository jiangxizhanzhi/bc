package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程相关结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */

@Data
public class CourseRelatedRes implements Serializable {

	/**
	 * 相关类型知识点
	 *
	 * @see Integer
	 */
	public final static Integer RELATED_TYPE_KNOWLEDGE = 1;

	/**
	 * 相关类型技能
	 *
	 * @see Integer
	 */
	public final static Integer RELATED_TYPE_SKILL = 2;

	/**
	 * 课程id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "课程id")
	private String resourceId;

	/**
	 * 课程关联的知识点或技能id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "课程关联的知识点或技能id")
	private String relatedId;

	/**
	 * 课程关联的知识点或技能名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "课程关联的知识点或技能名称")
	private String content;

	/**
	 * 关联课程的权重
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "关联课程的权重")
	private Integer weight;

	/**
	 * 知识点或技能类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "知识点或技能类型")
	private Integer type;
}
