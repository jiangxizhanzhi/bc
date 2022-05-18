package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlowChangeReq implements Serializable {

	@ApiModelProperty("测评靶场任务id")
	@NotNull
	private String taskId;

	@ApiModelProperty("节点id")
	@NotNull
	private String nodeId;

	@ApiModelProperty(value = "流量id")
	@NotNull
	private String flowId;

	@ApiModelProperty(value = "源ip")
	@NotNull
	private String sourceIp;
}
