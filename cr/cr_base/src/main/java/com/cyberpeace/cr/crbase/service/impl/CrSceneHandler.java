package com.cyberpeace.cr.crbase.service.impl;

import org.springframework.stereotype.Service;

import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.impl.SceneHandler;
import com.cyberpeace.integratation.model.scene.NodeOpsContext;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CrSceneHandler extends SceneHandler {

	public NodeOpsContext getNodeOpsContext(LocalScene localScene, String nodeId) {
		NodeOpsContext nodeOpsContext = new NodeOpsContext();
		nodeOpsContext.getContext().getPassThrough().put("scene_manager_id",
				localScene.getResourceId());
		nodeOpsContext.setId(localScene.getMpSceneId());
		nodeOpsContext.setNodeId(nodeId);

		return nodeOpsContext;
	}
}
