package com.cyberpeace.cr.crbase.service.context.evaluation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvaluationListRsp extends EvaluationListBo implements Serializable {

	@ApiModelProperty(value = "提交人名称")
	private String submitUserName;

	@ApiModelProperty(value = "提交人名称")
	private String submitUserAvatar;

	// @ApiModelProperty(value = "评估人名称")
	// private String evaluatorName;

	// @ApiModelProperty(value = "评估人id")
	// private String evaluator;

	// @ApiModelProperty(value = "任务id")
	// private String missionId;

}
