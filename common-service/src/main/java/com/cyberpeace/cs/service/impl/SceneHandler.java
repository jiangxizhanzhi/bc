package com.cyberpeace.cs.service.impl;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.google.common.collect.Maps;

@Service(value = "BasicSceneHandler")
public class SceneHandler {

	@Autowired
	private RemoteSceneService remoteSceneService;

	private static Map<Integer, SceneHandler> register = Maps.newHashMap();

	public Integer getBusinessType() {
		return null;
	}

	@PostConstruct
	public void init() {
		if (null != getBusinessType()) {
			register.put(this.getBusinessType(), this);
		}
	}

	public SceneHandler getHandler(Integer businessType) {
		return register.get(businessType);
	}

	public String startScene(LocalScene localScene) {
		SceneStartContext sceneStartContext = getSceneStartContext(localScene);
		return remoteSceneService.startScene(sceneStartContext);
	}

	public SceneStartContext getSceneStartContext(LocalScene localScene) {
		SceneStartContext context = new SceneStartContext();
		setPassThrough(context, localScene);
		context.setId(localScene.getTopoId());
		return context;
	}

	public void setPassThrough(SceneOpsContext context, LocalScene localScene) {}

	public String getReferenceMetaData(LocalScene localScene) {
		return null;
	}

	public void checkUnpauseScenePermission(LocalScene localScene) {}

	public void afterUnpauseScene(LocalScene localScene) {}

	public void afterPauseScene(LocalScene localScene) {}

	public void checkPauseScenePermission(LocalScene localScene) {}
}
