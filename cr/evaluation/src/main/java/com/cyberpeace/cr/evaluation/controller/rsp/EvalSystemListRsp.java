package com.cyberpeace.cr.evaluation.controller.rsp;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalSystemListRsp {

	@ApiModelProperty(value = "评估体系Id", required = true)
	@NotNull
	private String evalSystemId;

	@ApiModelProperty(value = "名称", required = true)
	@NotNull
	private String name;

	@ApiModelProperty(value = "描述", required = false)
	private String description;

	@ApiModelProperty(value = "一级指标数量", required = true)
	@NotNull
	private Integer firstIndexNum;

	@ApiModelProperty(value = "二级指标数量", required = true)
	@NotNull
	private Integer secondIndexNum;

	@ApiModelProperty(value = "三级指标数量", required = true)
	@NotNull
	private Integer thirdIndexNum;

}