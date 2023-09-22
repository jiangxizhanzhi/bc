package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

@Data
public class FlowInfo implements Serializable {

	private String flow_id;

	private String source_ip;

	public void setFlowId(String flowId) {
		this.flow_id = flowId;
	}

	public void setId(String flowId) {
		setFlow_id(flowId);
	}

	public void setSourceIp(String sourceIp) {
		this.source_ip = sourceIp;
	}
}
