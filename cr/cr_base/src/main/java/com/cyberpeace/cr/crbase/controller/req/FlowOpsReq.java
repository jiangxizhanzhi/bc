package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlowOpsReq implements Serializable {

	@ApiModelProperty("团队训练实例id")
	private String roomId;

	@ApiModelProperty("节点id")
	private String nodeId;

	@ApiModelProperty("流量id")
	private String flowId;

	@ApiModelProperty("源IP")
	private String sourceIp;
}
