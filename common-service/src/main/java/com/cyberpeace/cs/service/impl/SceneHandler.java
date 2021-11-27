package com.cyberpeace.cs.service.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
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

	public String getRoomInfoByLocalSceneId(String localSceneId) {
		return null;
	}

	public void checkSceneOperationPermission(String roomId, String userId) {}

	public void checkBeforeStartingScene(String roomId) {}

	public void filterVisibleTopo(JSONObject topoData, LocalScene localScene, String userId,
			String roleId) {}

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

	public void doAfterStopScene(LocalScene localScene) {}

	public void checkPauseScenePermission(LocalScene localScene) {}

	public void afterPauseScene(LocalScene localScene) {}

	public void checkUnpauseScenePermission(LocalScene localScene) {}

	public void afterUnpauseScene(LocalScene localScene) {}
}
