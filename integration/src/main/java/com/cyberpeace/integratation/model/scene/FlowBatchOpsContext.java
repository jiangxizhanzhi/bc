package com.cyberpeace.integratation.model.scene;

import java.util.List;

import lombok.Data;

/**
 * 流量批量操作上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see NodeOpsContext
 * @since 1.0.0
 */
@Data
public class FlowBatchOpsContext extends NodeOpsContext {

	/**
	 * 运行类型
	 *
	 * @see Integer
	 */
	private Integer run_type;

	/**
	 * 流
	 *
	 * @see List<FlowInfo>
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
