package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubmitEvaluationReq {

	@ApiModelProperty(value = "房间ID")
	private String roomId;

	@ApiModelProperty(value = "任务提交id")
	private String submitId;

	@ApiModelProperty(value = "评估结果")
	private Integer evaluateStatus;

	@ApiModelProperty(value = "预设原因")
	private Integer reason;

	@ApiModelProperty(value = "裁决详情")
	private String detail;

	@ApiModelProperty(value = "分数改判")
	private Integer score;
}
