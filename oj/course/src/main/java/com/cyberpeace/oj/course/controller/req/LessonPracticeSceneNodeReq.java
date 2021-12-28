package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel
public class LessonPracticeSceneNodeReq extends LessonPracticeSceneReq {

	@ApiModelProperty("节点编号")
	@NotBlank
	private String nodeId;

}
