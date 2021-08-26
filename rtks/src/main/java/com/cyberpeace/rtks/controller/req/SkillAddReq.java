package com.cyberpeace.rtks.controller.req;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.rtks.dao.entity.RelatedCourse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class SkillAddReq implements Serializable {

	@ApiModelProperty(value = "技能id")
	private String resourceId;

	@ApiModelProperty(value = "技能内容")
	@NotBlank
	private String content;

	@ApiModelProperty(value = "体系id")
	@NotBlank
	private String systemId;

	@ApiModelProperty(value = "类别id")
	@NotBlank
	private String categoryId;

	@ApiModelProperty(value = "权重")
	private Integer weight = 1;

	@ApiModelProperty(value = "课程列表")
	private List<RelatedCourse> courseList;
}
