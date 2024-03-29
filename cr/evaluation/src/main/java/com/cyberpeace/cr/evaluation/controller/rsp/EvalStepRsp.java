package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalStepRsp implements Serializable {

	@ApiModelProperty("资源id")
	private String stepId;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("描述")
	private String process;

}
