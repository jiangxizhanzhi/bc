package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.cyberpeace.integratation.service.RemoteSceneService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RemoteSceneServiceImpl implements RemoteSceneService {

	@Autowired
	private MpEnvService mpEnvService;

	private String SCENE_START_URL;

	private String SCENE_DELETE_URL;

	private String SCENE_RETRIEVE;

	@PostConstruct
	public void init() {
		SCENE_START_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.start");

		SCENE_DELETE_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.delete");

		SCENE_RETRIEVE = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.retrieve");
	}

	@Override
	public String startScene(String topoId, SceneStartContext sceneStartContext) {
		String targetUrl = SCENE_START_URL + "?id=" + topoId;

		JSONObject data = HttpClientUtil.post(targetUrl, sceneStartContext, JSONObject.class);

		if (null == data) {
			log.error("topoId=[{}] startScene error, response data is null", topoId);
			throw new RemoteServiceException();
		}

		String sceneId = data.getString("id");

		return sceneId;
	}

	@Override
	public Boolean stopScene(String sceneId, SceneOpsContext sceneOpsContext) {
		String targetUrl = SCENE_DELETE_URL + "?id=" + sceneId;

		Boolean result = HttpClientUtil.delete(targetUrl, sceneOpsContext, Boolean.class);

		return result;
	}

	@Override
	public JSONObject getById(String sceneId, String userId) {
		String targetUrl = SCENE_RETRIEVE + "?id=" + sceneId + "&accessUserId=" + userId;
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);

	}
}
