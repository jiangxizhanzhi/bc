package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.cyberpeace.integratation.service.RemoteSceneService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RemoteSceneServiceImpl implements RemoteSceneService {

	@Autowired
	private MpEnvService mpEnvService;

	private String SCENE_START_URL;

	private String SCENE_START_TEST_URL;

	private String SCENE_DELETE_URL;

	private String SCENE_CLEAR_URL;

	private String SCENE_RETRIEVE;

	private String SCENE_PAUSE_URL;

	private String SCENE_UNPAUSE_URL;

	@PostConstruct
	public void init() {
		SCENE_START_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.start.app");

		SCENE_START_TEST_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.start.test");

		SCENE_DELETE_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.delete");

		SCENE_CLEAR_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.clear");

		SCENE_RETRIEVE = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.retrieve");

		SCENE_PAUSE_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.pause");

		SCENE_UNPAUSE_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.unpause");
	}

	@Override
	public String startScene(SceneOpsContext sceneStartContext, Boolean isTest) {

		String targetUrl = null;
		if (isTest) {
			targetUrl = SCENE_START_TEST_URL;
		} else {
			targetUrl = SCENE_START_URL;
		}

		JSONObject data = HttpClientUtil.post(targetUrl, sceneStartContext, JSONObject.class);

		if (null == data) {
			log.error("topoId=[{}] startScene error, response data is null",
					sceneStartContext.getId());
			throw new RemoteServiceException();
		}

		String sceneId = data.getString("id");

		return sceneId;
	}

	@Override
	public Boolean stopScene(String sceneId, SceneOpsContext sceneOpsContext) {
		String targetUrl = SCENE_CLEAR_URL;

		Boolean result = HttpClientUtil.delete(targetUrl, sceneOpsContext, Boolean.class);

		return result;
	}

	@Override
	public Boolean pauseScene(String sceneId, SceneOpsContext sceneOpsContext) {
		return HttpClientUtil.post(SCENE_PAUSE_URL, sceneOpsContext, Boolean.class);
	}

	@Override
	public JSONObject getById(String sceneId, String userId) {
		String targetUrl = SCENE_RETRIEVE + "?id=" + sceneId + "&accessUserId=" + userId;
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);

	}

	@Override
	public Boolean unpauseScene(String mpSceneId, SceneOpsContext sceneOpContext) {
		return HttpClientUtil.post(SCENE_UNPAUSE_URL, sceneOpContext, Boolean.class);

	}
}
