package com.cyberpeace.oj.ojbase.service.impl;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import org.springframework.stereotype.Service;

import com.cyberpeace.cs.service.impl.SceneHandler;

@Service("OjSceneHandler")
public class OjSceneHandler extends SceneHandler {

	public String startScene(Integer businessCode, OjLocalScene ojLocalScene, String topoId) {

		LocalScene localScene = initLocalScene(businessCode, ojLocalScene, topoId);

		String referenceMetaData = getReferenceMetaData(localScene);
		SceneOpsContext sceneOpsContext = makeSceneOpsContext(localScene);

		return localScene.getMpSceneId();
	}

	/**
	 * 获取初始化场景数据
	 * @param businessCode
	 * @param ojLocalScene
	 * @param topoId
	 * @return
	 */
	private LocalScene initLocalScene(Integer businessCode, OjLocalScene ojLocalScene,
			String topoId) {
		LocalScene localScene = new LocalScene();
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.INIT.getCode());
		localScene.setBusinessCode(businessCode);
		localScene.setBusinessInstanceId(ojLocalScene.getResourceId());
		localScene.setDestoryMode(LocalScene.DestroyMode.AUTO.getCode());
		localScene.setResourceId(IDGeneratorUtil.getID());
		localScene.setTopoId(topoId);
		localScene.setUserId(ojLocalScene.getUserId());
		return localScene;
	}




}
