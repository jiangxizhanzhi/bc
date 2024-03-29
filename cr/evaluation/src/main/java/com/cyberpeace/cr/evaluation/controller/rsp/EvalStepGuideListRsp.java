package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalStepGuideListRsp implements Serializable {

	@ApiModelProperty("步骤id")
	private String stepId;

	@ApiModelProperty("步骤名称")
	private String stepName;

	@ApiModelProperty("是否配有导调")
	private Boolean isExistGuide;
}
