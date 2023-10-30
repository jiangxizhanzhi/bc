package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @TableName rtks_course_related
 */

@Data
public class CourseRelatedRes implements Serializable {

	public static final Integer RELATED_TYPE_KNOWLEDGE = 1;

	public static final Integer RELATED_TYPE_SKILL = 2;

	@ApiModelProperty(value = "课程id")
	private String resourceId;

	@ApiModelProperty(value = "课程关联的知识点或技能id")
	private String relatedId;

	@ApiModelProperty(value = "课程关联的知识点或技能名称")
	private String content;

	@ApiModelProperty(value = "关联课程的权重")
	private Integer weight;

	@ApiModelProperty(value = "知识点或技能类型")
	private Integer type;
}
