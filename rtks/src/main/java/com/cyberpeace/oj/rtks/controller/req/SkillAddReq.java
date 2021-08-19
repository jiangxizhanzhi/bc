package com.cyberpeace.oj.rtks.controller.req;

import com.cyberpeace.oj.rtks.dao.entity.RelatedCourse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel
public class SkillAddReq implements Serializable {

	@ApiModelProperty()
	private String resourceId;

	@NotBlank
	private String content;

	@NotBlank
	private String systemId;

	@NotBlank
	private String categoryId;

	@ApiModelProperty
	private List<RelatedCourse> courseList;
}
