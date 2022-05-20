package com.cyberpeace.integratation.model.scene;

import lombok.Data;

@Data
public class FlowRecordContext extends NodeOpsContext {

	private String flow_id;

	private Boolean is_batch = true;

	public void setFlowId(String flowId) {
		this.flow_id = flowId;
	}

	public void setIsBatch(Boolean isBatch) {
		this.is_batch = isBatch;
	}

	public FlowRecordContext(String mpSceneId, String nodeId) {
		this.setId(mpSceneId);
		this.setNodeId(nodeId);
	}

	public FlowRecordContext() {}

}
