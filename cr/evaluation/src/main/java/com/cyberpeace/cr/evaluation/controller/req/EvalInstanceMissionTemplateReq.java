package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalInstanceMissionTemplateReq extends EvalMissionTemplateReq {

	@ApiModelProperty("实例id")
	private String taskId;

	@ApiModelProperty("阶段名称")
	private String stageName;

}
