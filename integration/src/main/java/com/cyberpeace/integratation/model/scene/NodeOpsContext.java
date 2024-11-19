package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 节点操作上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see SceneOpsContext
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class NodeOpsContext extends SceneOpsContext implements Serializable {

	/**
	 * 节点id
	 *
	 * @see String
	 */
	private String node_id;

	public void setNodeId(String nodeId) {
		this.node_id = nodeId;
	}
}