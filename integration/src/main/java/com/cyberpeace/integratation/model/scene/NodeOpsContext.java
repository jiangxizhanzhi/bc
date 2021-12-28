package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

@Data
public class NodeOpsContext extends SceneOpsContext implements Serializable {

	private String node_id;

	public void setNodeId(String nodeId) {
		this.node_id = nodeId;
	}
}