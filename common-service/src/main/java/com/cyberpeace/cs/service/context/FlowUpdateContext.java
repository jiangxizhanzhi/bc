package com.cyberpeace.cs.service.context;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 流更新背景
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class FlowUpdateContext implements Serializable {

	private String mpSceneId;

	private String nodeId;

	private List<String> flowIds;

	/**
	 * 流更新背景
	 *
	 * @param mpSceneId 场景id
	 * @param nodeId    节点id
	 * @param flowIds   流id
	 */
	public FlowUpdateContext(String mpSceneId, String nodeId, List<String> flowIds) {
		this.mpSceneId = mpSceneId;
		this.nodeId = nodeId;
		this.flowIds = flowIds;
	}

	/**
	 * 流更新背景
	 */
	public FlowUpdateContext() {}

}
