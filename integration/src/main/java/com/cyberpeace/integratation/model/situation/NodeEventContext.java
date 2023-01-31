package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeEventContext implements Serializable {

	@ApiModelProperty("节点ID")
	private String nodeId;

	@ApiModelProperty("工作状态")
	private String workStatus;

	public NodeEventContext() {}

	public NodeEventContext(String nodeId) {
		this.nodeId = nodeId;
		this.workStatus = "工作中";
	}

}
