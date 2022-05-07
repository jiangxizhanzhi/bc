package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cs.dao.entity.LocalScene;

/**
 *
 */
public interface CrSceneService {

	String saveTopo(SceneConfigData sceneConfigData, LocalScene.BusinessCode businessCode, String topoId);

}
