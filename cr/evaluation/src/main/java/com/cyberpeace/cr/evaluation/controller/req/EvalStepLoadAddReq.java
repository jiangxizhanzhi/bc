package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalStepLoadAddReq implements Serializable {

	@ApiModelProperty("试验实例id")
	private String testInstanceId;

	@ApiModelProperty("载荷id")
	private String loadId;

	@ApiModelProperty("步骤id")
	private String stepId;

	@ApiModelProperty("执行命令")
	private String command;

	@ApiModelProperty("载荷名称")
	private String loadName;

	@ApiModelProperty("载荷描述")
	private String loadDescription;

	@ApiModelProperty("x坐标")
	private String x;

	@ApiModelProperty("y坐标")
	private String y;
}
