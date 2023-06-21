package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName rtks_course_related
 */

@Data
public class CourseKnowledgeBo implements Serializable {


	@ApiModelProperty(value = "课程id")
	private String resourceId;

	@ApiModelProperty(value = "课程关联的知识点或技能id")
	private String relatedId;

	@ApiModelProperty(value = "课程关联的知识点或技能名称")
	private String content;

}
