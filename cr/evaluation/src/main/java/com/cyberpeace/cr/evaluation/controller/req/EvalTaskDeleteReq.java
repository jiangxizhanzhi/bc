package com.cyberpeace.cr.evaluation.controller.req;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTaskDeleteReq {

	@ApiModelProperty(value = "测评任务Ids", required = true)
	@NotNull
	@NotEmpty
	private List<String> taskIds;

}