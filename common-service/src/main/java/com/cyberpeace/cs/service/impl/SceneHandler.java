package com.cyberpeace.cs.service.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

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

	public String getReferenceMetaData(LocalScene localScene) {
		return null;
	}

	public SceneOpsContext makeSceneOpsContext(LocalScene localScene) {
		SceneOpsContext startContext = new SceneOpsContext();
		startContext.getContext().getPassThrough().put("scene_manager_id",
				localScene.getResourceId());
		startContext.setId(localScene.getMpSceneId());

		return startContext;
	}

	public void doAfterStopScene(LocalScene localScene) {
		// 公共的删除 localScene todo
	}

	public void checkPauseScenePermission(LocalScene localScene) {}

	public void afterPauseScene(LocalScene localScene) {
		// 公共的修改localScene状态
	}

	public void checkUnpauseScenePermission(LocalScene localScene) {}

	public void afterUnpauseScene(LocalScene localScene) {}
}
