package com.cyberpeace.cs.service.context;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class FlowUpdateContext implements Serializable {

	private String mpSceneId;

	private String nodeId;

	private List<String> flowIds;

	public FlowUpdateContext(String mpSceneId, String nodeId, List<String> flowIds) {
		this.mpSceneId = mpSceneId;
		this.nodeId = nodeId;
		this.flowIds = flowIds;
	}

	public FlowUpdateContext() {}

}
