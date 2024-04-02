package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EvalGuideAddReq implements Serializable {

	@ApiModelProperty(value = "试验id", required = true)
	@NotBlank
	private String testId;

	@ApiModelProperty(value = "事件名称", required = true)
	@NotBlank
	private String name;

	@ApiModelProperty(value = "类型：1=任务导调，2=手动导调", required = true)
	@NotNull
	private Integer type;

	@ApiModelProperty(value = "攻击类型", required = true)
	@NotNull
	private Integer attackType;

	@ApiModelProperty(value = "攻击源", required = true)
	@NotBlank
	private String source;

	@ApiModelProperty(value = "攻击目标", required = true)
	@NotBlank
	private String target;

	@ApiModelProperty(value = "攻击结果", required = true)
	@NotNull
	private Integer result;

	@ApiModelProperty(value = "步骤id")
	private String stepId;

}
