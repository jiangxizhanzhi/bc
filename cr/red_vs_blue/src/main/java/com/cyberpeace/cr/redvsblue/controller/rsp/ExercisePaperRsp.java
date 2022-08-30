package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExercisePaperRsp {

	@ApiModelProperty(value = "演习文书")
	private String exercisePaper;

}
