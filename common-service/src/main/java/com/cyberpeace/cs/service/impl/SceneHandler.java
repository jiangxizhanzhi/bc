package com.cyberpeace.cs.service.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.google.common.collect.Maps;

@Service(value = "BasicSceneHandler")
public class SceneHandler {

	private static Map<Integer, SceneHandler> register = Maps.newHashMap();

	public Integer getBusinessType() {
		return null;
	}

	@PostConstruct
	public void init() {
		register.put(this.getBusinessType(), this);
	}

	public SceneHandler getHandler(Integer businessType) {
		return register.get(businessType);
	}

	public String getRoomIdBySceneManagerId(String sceneManagerId) {
		return null;
	}

	public String getRoomInfoBySceneManagerId(String sceneManagerId) {
		return null;
	}

	public void checkSceneOperationPermission(String roomId, String userId) {}

	public void checkBeforeStartingScene(String roomId) {}

	public void filterVisibleTopo(JSONObject topoData, LocalScene localScene, String userId,
			String roleId) {}

	public SceneStartContext makeSceneStartContext(LocalScene localScene) {
		SceneStartContext startContext = new SceneStartContext();
		startContext.getContext().getPassThrough().put("scene_manager_id",
				localScene.getResourceId());
		startContext.setReferenceMetaData(getReferenceMetaData(localScene));
		startContext.setId(localScene.getTopoId());

		return startContext;
	}

	public String getReferenceMetaData(LocalScene localScene) {
		return null;
	}

	public Boolean isTest() {
		return getBusinessType().equals(LocalScene.BusinessCode.TEST_SCENE.getCode());
	}

	public void afterCreateScene() {}

	public SceneOpsContext makeSceneOpsContext(LocalScene localScene) {
		SceneOpsContext startContext = new SceneOpsContext();
		startContext.getContext().getPassThrough().put("scene_manager_id",
				localScene.getResourceId());
		startContext.setId(localScene.getMpSceneId());

		return startContext;
	}

	public SceneOpsContext makeSceneStopContext(LocalScene localScene, String mpSceneId) {
		SceneOpsContext startContext = new SceneOpsContext();
		startContext.getContext().getPassThrough().put("scene_manager_id",
				localScene.getResourceId());
		startContext.setId(mpSceneId);

		return startContext;
	}

	public void doAfterStopScene() {}
}
