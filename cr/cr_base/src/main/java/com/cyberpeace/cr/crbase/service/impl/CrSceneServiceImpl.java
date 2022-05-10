package com.cyberpeace.cr.crbase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.service.CrSceneService;
import com.cyberpeace.cr.crbase.service.impl.bo.SceneConfigData;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.integratation.service.RemoteTopoService;

/**
 *
 */
@Service
public class CrSceneServiceImpl implements CrSceneService {

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Override
	public String saveTopo(SceneConfigData sceneConfigData, LocalScene.BusinessCode businessCode, String topoId) {
		JSONObject topoJson = new JSONObject();
		topoJson.put("scene_config", sceneConfigData.getSceneConfig());
		topoJson.put("logo", sceneConfigData.getLogo());
		topoJson.put("reference_type", businessCode.getCode());

		if (topoId != null){
			topoJson.put("id", topoId);
		}

		return remoteTopoService.save(topoJson);
	}
}
