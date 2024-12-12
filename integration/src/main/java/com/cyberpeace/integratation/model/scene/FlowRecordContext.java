package com.cyberpeace.integratation.model.scene;

import lombok.Getter;
import lombok.Setter;

/**
 * 流记录上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class FlowRecordContext extends NodeOpsContext {

	/**
	 * 流id
	 */
	private String flow_id;

	/**
	 * 是批量
	 */
	private Boolean is_batch = true;

	public void setFlowId(String flowId) {
		this.flow_id = flowId;
	}

	public void setIsBatch(Boolean isBatch) {
		this.is_batch = isBatch;
	}

	public FlowRecordContext(String mpSceneId, String nodeId, String flowId) {
		this.setId(mpSceneId);
		this.setNodeId(nodeId);
		this.setFlowId(flowId);
	}

	public FlowRecordContext() {}

}
