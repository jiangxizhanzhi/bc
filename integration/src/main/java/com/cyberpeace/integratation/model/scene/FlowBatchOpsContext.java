package com.cyberpeace.integratation.model.scene;

import java.util.List;

import lombok.Data;

/**
 * 流量批量操作上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class FlowBatchOpsContext extends NodeOpsContext {

	/**
	 * 运行类型
	 */
	private Integer run_type;

	/**
	 * 流
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
