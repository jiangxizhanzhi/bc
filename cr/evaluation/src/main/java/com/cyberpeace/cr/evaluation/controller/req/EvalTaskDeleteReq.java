package com.cyberpeace.cr.evaluation.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTaskDeleteReq {

	@ApiModelProperty(value = "测评任务Ids", required = true)
	@NotNull
	private List<String> taskIds;

}