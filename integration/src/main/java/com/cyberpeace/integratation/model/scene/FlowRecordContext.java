package com.cyberpeace.integratation.model.scene;

import lombok.Data;

/**
 * 流量记录上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see NodeOpsContext
 * @since 1.0.0
 */
@Data
public class FlowRecordContext extends NodeOpsContext {

	/**
	 * 流量id
	 *
	 * @see String
	 */
	private String flow_id;

	/**
	 * 是否批量
	 *
	 * @see Boolean
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
