package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

@Data
public class FlowParamUpdateContext implements Serializable {

	private SceneMpContext context = new SceneMpContext();

	private String flow_id;

	private String id;

	private String node_id;

	private String source_ip;

	public void setFlowId(String flowId) {
		this.flow_id = flowId;
	}

	public void setSourceIp(String sourceIp) {
		this.source_ip = sourceIp;
	}

	public void setNodeId(String nodeId) {
		this.node_id = nodeId;
	}

}