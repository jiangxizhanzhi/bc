package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExercisePaperReq implements Serializable {

	@ApiModelProperty(value = "演习文书")
	private String exercisePaper;

	@ApiModelProperty(value = "房间id", required = true)
	private String resourceId;

	@ApiModelProperty(value = "分组", required = true)
	private Integer groupHash;
}
