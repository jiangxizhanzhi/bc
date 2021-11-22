package com.cyberpeace.integratation.service;

import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.cyberpeace.integratation.model.scene.SceneStartContext;

public interface RemoteSceneService {

    String startScene(String topoId, SceneStartContext sceneStartContext);

    Boolean stopScene(String sceneId, SceneOpsContext sceneOpsContext);
}
