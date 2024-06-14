package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class EvalSceneNodeReq implements Serializable {

	@ApiModelProperty(value = "实例id", required = true)
	@NotBlank
	private String instanceId;

	@ApiModelProperty(value = "节点id", required = true)
	@NotBlank
	private String nodeId;
}
