package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlowsOpsReq implements Serializable {

	@ApiModelProperty("测评靶场任务id")
	private String taskId;

	@ApiModelProperty("节点id")
	private String nodeId;

	@ApiModelProperty(value = "执行方式：0 串行，1 并行")
	private int runType;
}
