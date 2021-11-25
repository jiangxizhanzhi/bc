package com.cyberpeace.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.cyberpeace.integratation.model.scene.SceneStartContext;

public interface LocalSceneService extends IService<LocalScene> {

	LocalScene getByBusinessInstanceId(String businessInstanceId);

	Boolean startScene(String localSceneId, SceneOpsContext sceneOpsContext);

	Boolean closeScene(String localSceneId);

	Boolean pauseScene(String localSceneId);

	Boolean unPause(String localSceneId);
}
