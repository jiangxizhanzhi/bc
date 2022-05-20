package com.cyberpeace.integratation.model.scene;

import java.util.List;

import lombok.Data;

@Data
public class FlowBatchOpsContext extends NodeOpsContext {

	private Integer run_type;

	private List<FlowInfo> flows;

	public void setRunType(Integer runType) {
		this.run_type = runType;
	}

	public FlowBatchOpsContext(String mpSceneId, String nodeId, Integer runType) {

		this.setRunType(runType);

		this.setNodeId(nodeId);

		this.setId(mpSceneId);

	}

	public FlowBatchOpsContext() {}
}
