package com.cyberpeace.cr.evaluation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlowDownloadReq implements Serializable {

	@ApiModelProperty("测评靶场任务id")
	private String taskId;

	@ApiModelProperty("节点id")
	private String nodeId;

	@ApiModelProperty("流量id")
	private String flowId;

}
