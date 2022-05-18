package com.cyberpeace.integratation.model.scene;

import lombok.Data;

@Data
public class FlowOpsContext extends NodeOpsContext {

	private String flow_id;

	private String source_ip;

	public void setFlowId(String flowId) {
		this.flow_id = flowId;
	}

	public void setSourceIp(String sourceIp) {
		this.source_ip = sourceIp;
	}
}
