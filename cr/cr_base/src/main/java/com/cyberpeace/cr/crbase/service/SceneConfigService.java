package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.model.SceneConfig;

public interface SceneConfigService {

	String create(SceneConfig sceneConfig);

	SceneConfig getById(String sceneConfigId);
}
