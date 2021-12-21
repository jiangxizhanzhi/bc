package com.cyberpeace.cr.redvsblue.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExercisePaperReq {

	private String exercisePaper;

	@ApiModelProperty(value = "房间id", required = true)
	private String resourceId;

	@ApiModelProperty(value = "分组", required = true)
	private Integer groupHash;
}
