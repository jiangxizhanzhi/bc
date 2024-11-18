package com.cyberpeace.cs.service.context;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 流量更新上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class FlowUpdateContext implements Serializable {

	/**
	 * 中台场景id
	 *
	 * @see String
	 */
	private String mpSceneId;

	/**
	 * 节点id
	 *
	 * @see String
	 */
	private String nodeId;

	/**
	 * 流量id列表
	 *
	 * @see List<String>
	 */
	private List<String> flowIds;

	public FlowUpdateContext(String mpSceneId, String nodeId, List<String> flowIds) {
		this.mpSceneId = mpSceneId;
		this.nodeId = nodeId;
		this.flowIds = flowIds;
	}

	public FlowUpdateContext() {}

}
