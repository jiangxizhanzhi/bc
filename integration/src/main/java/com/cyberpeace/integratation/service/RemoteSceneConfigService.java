package com.cyberpeace.integratation.service;

import com.cyberpeace.integratation.model.SceneConfig;

public interface RemoteSceneConfigService {

	String create(SceneConfig sceneConfig);

	SceneConfig getById(String sceneConfigId);
}
