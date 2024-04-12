package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

@Data
public class EvalSceneNodeOpsReq implements Serializable {

	@ApiModelProperty(value = "测评靶场任务id", required = true)
	@NotBlank
	private String taskId;

	@ApiModelProperty(value = "节点id", required = true)
	@NotBlank
	private String nodeId;

	@ApiModelProperty(value = "节点id")
	private String mpSceneId;

	@ApiModelProperty(value = "登录方式")
	private Integer operate;
}
