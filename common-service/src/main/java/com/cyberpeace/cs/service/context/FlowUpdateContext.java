package com.cyberpeace.cs.service.context;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 流更新上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class FlowUpdateContext implements Serializable {

	/**
	 * 中台场景id
	 */
	private String mpSceneId;

	/**
	 * 节点id
	 */
	private String nodeId;

	/**
	 * 流id列表
	 */
	private List<String> flowIds;

	public FlowUpdateContext(String mpSceneId, String nodeId, List<String> flowIds) {
		this.mpSceneId = mpSceneId;
		this.nodeId = nodeId;
		this.flowIds = flowIds;
	}

	public FlowUpdateContext() {}

}
