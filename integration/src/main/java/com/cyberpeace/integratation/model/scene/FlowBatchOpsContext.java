package com.cyberpeace.integratation.model.scene;

import lombok.Data;

@Data
public class FlowBatchOpsContext extends NodeOpsContext {

	private String flow_id;

	public void setFlowId(String flowId) {
		this.flow_id = flowId;
	}
}
