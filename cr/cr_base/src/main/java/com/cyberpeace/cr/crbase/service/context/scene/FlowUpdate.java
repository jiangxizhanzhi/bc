package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cs.service.context.scene.ElementFlow;

import lombok.Data;

/**
 * 流程更新
 * 流量更新模型
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
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
