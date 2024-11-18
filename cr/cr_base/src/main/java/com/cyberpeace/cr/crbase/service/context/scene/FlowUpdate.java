package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cs.service.context.scene.ElementFlow;

import lombok.Data;

/**
 * 流量更新模型
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class FlowUpdate implements Serializable {

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
	 * 更新流量
	 *
	 * @see List<ElementFlow>
	 */
	private List<ElementFlow> updateFlows;

	public FlowUpdate(String mpSceneId, String nodeId, List<ElementFlow> updateFlows) {
		this.mpSceneId = mpSceneId;
		this.nodeId = nodeId;
		this.updateFlows = updateFlows;
	}

	public FlowUpdate() {}

}
