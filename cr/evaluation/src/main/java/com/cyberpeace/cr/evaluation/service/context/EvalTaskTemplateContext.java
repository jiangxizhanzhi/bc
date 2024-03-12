package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalTaskTemplateContext implements Serializable {

	@ApiModelProperty("任务id")
	private String taskId;

	@ApiModelProperty("任务名称")
	private String taskName;
}