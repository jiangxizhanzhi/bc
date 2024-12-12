package com.cyberpeace.integratation.model.scene;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 流批量老年 退休金 计划上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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
