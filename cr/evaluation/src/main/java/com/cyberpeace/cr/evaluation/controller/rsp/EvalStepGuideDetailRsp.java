package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalStepGuideDetailRsp implements Serializable {

	@ApiModelProperty("阶段id")
	private String stepId;

	@ApiModelProperty("导调id")
	private String guideId;

	@ApiModelProperty("事件名称")
	private String name;

	@ApiModelProperty("攻击类型")
	private Integer attackType;

	@ApiModelProperty("攻击源")
	private String source;

	@ApiModelProperty("攻击目标")
	private String target;

	@ApiModelProperty("攻击结果")
	private Integer result;
}
