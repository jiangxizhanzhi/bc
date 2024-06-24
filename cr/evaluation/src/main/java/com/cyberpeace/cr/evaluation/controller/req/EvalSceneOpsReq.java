package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalSceneOpsReq implements Serializable {

	@ApiModelProperty(value = "试验实例id", required = true)
	private String instanceId;

}
