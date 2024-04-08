package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EvalTestStopReq implements Serializable {

	@ApiModelProperty(value = "试验id", required = true)
	@NotNull
	private String testId;

	@ApiModelProperty(value = "试验实例id", required = true)
	private String instanceId;

	@ApiModelProperty(value = "试验实例名称", required = false)
	private String instanceName;
}
