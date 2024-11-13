package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.common.context.scene.ElementFlow;

import lombok.Data;

/**
 * 流量更新模型
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class FlowUpdate implements Serializable {

	/**
	 * 中台场景id
	 */
	private String mpSceneId;

	/**
	 * 节点id
	 */
	private String nodeId;

	/**
	 * 更新流
	 */
	private List<ElementFlow> updateFlows;

	public FlowUpdate(String mpSceneId, String nodeId, List<ElementFlow> updateFlows) {
		this.mpSceneId = mpSceneId;
		this.nodeId = nodeId;
		this.updateFlows = updateFlows;
	}

	public FlowUpdate() {}

}
