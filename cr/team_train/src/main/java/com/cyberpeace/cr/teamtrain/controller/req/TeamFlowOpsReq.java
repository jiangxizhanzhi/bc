package com.cyberpeace.cr.teamtrain.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamFlowOpsReq implements Serializable {

	@ApiModelProperty("团队训练实例id")
	private String roomId;

	@ApiModelProperty("节点id")
	private String nodeId;

	@ApiModelProperty("流量id")
	private String flowId;
}
