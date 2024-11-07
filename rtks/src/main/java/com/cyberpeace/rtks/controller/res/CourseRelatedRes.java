package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程相关结果
 *
 * @author SN
 * @date 2024/11/07
 */

@Data
public class CourseRelatedRes implements Serializable {

	/**
	 * 相关类型知识
	 */
	public final static Integer RELATED_TYPE_KNOWLEDGE = 1;

	/**
	 * 相关类型技能
	 */
	public final static Integer RELATED_TYPE_SKILL = 2;

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "课程id")
	private String resourceId;

	/**
	 * 相关id
	 */
	@ApiModelProperty(value = "课程关联的知识点或技能id")
	private String relatedId;

	/**
	 * 内容
	 */
	@ApiModelProperty(value = "课程关联的知识点或技能名称")
	private String content;

	/**
	 * 重量
	 */
	@ApiModelProperty(value = "关联课程的权重")
	private Integer weight;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "知识点或技能类型")
	private Integer type;
}
