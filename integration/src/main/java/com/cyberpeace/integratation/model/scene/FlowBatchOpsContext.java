package com.cyberpeace.integratation.model.scene;

import java.util.List;

import lombok.Data;

/**
 * 流量批量操作 计划上下文
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class FlowBatchOpsContext extends NodeOpsContext {

	/**
	 * 运行类型
	 */
	private Integer run_type;

	/**
	 * 流量
	 */
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
