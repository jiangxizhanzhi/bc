package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EvalStudentScoreReq {

	@ApiModelProperty(value = "能力占比")
	private String abilityPercentage;

	@ApiModelProperty(value = "能力分")
	private String abilityScore;

	@ApiModelProperty(value = "评语")
	private String comment;

	@ApiModelProperty(value = "最终得分")
	private Integer finalScore;

	@ApiModelProperty(value = "训练占比")
	private String trainingPercentage;

	@ApiModelProperty(value = "房间id")
	private String roomId;

	@NotNull
	@ApiModelProperty(value = "测评模版ID")
	private String evalTemplateId;

	@ApiModelProperty(value = "用户id")
	private String user;
}
