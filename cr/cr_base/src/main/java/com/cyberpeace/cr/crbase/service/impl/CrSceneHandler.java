package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleNode;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleNodesData;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.impl.SceneHandler;
import com.cyberpeace.integratation.model.scene.NodeOpsContext;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.cyberpeace.integratation.model.scene.SceneStartContext;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CrSceneHandler extends SceneHandler {

	@Override
	public Integer getBusinessType() {
		return 1000;
	}

	@Override
	public void setPassThrough(SceneOpsContext context, LocalScene localScene) {
		context.getContext().getPassThrough().put("scene_manager_id", localScene.getResourceId());
	}

	public void filterVisibleTopo(JSONObject topoData, CrRoleNodesData nodes) {
		if (nodes == null) {
			return;
		}

		List<String> visibleNodeIds = nodes.getVisibleNodes().stream().map(CrRoleNode::getId)
				.collect(Collectors.toList());
		JSONArray elements = topoData.getJSONObject("topology").getJSONArray("elements");
		int i = 0;
		while (i < elements.size()) {
			if (!visibleNodeIds.contains(elements.getJSONObject(i).getString("id"))) {
				elements.remove(i);
			} else {
				i++;
			}
		}
	}

	public void putContext(SceneStartContext sceneStartContext, LocalScene localScene) {
		sceneStartContext.getContext().getPassThrough().put("scene_manager_id",
				localScene.getResourceId());
	}

	public SceneStartContext getSceneStartContext(LocalScene localScene) {
		SceneHandler handler = getHandler(localScene.getBusinessCode());
		SceneStartContext sceneStartContext = new SceneStartContext();
		sceneStartContext.getContext().getPassThrough().put("scene_manager_id",
				localScene.getResourceId());
		sceneStartContext.setReferenceMetaData(handler.getReferenceMetaData(localScene));
		sceneStartContext.setId(localScene.getTopoId());

		return sceneStartContext;
	}

	public NodeOpsContext getNodeOpsContext(LocalScene localScene, String nodeId) {
		NodeOpsContext nodeOpsContext = new NodeOpsContext();
		nodeOpsContext.getContext().getPassThrough().put("scene_manager_id",
				localScene.getResourceId());
		nodeOpsContext.setId(localScene.getMpSceneId());
		nodeOpsContext.setNodeId(nodeId);

		return nodeOpsContext;
	}
}
