package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Node Ops 上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class NodeOpsContext extends SceneOpsContext implements Serializable {

	/**
	 * 节点id
	 */
	private String node_id;

	public void setNodeId(String nodeId) {
		this.node_id = nodeId;
	}
}