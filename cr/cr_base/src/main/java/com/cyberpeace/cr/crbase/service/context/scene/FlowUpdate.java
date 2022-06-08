package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class FlowUpdate implements Serializable {

	private String mpSceneId;

	private String nodeId;

	private List<ElementFlow> updateFlows;

	public FlowUpdate(String mpSceneId, String nodeId, List<ElementFlow> updateFlows) {
		this.mpSceneId = mpSceneId;
		this.nodeId = nodeId;
		this.updateFlows = updateFlows;
	}

	public FlowUpdate() {}

}
