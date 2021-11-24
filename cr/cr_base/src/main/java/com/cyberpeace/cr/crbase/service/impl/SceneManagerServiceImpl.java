package com.cyberpeace.cr.crbase.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.crbase.dao.entity.LocalSceneManager;
import com.cyberpeace.cr.crbase.dao.mapper.SceneManagerMapper;
import com.cyberpeace.cr.crbase.service.SceneManagerService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SceneManagerServiceImpl extends ServiceImpl<SceneManagerMapper, LocalSceneManager> implements SceneManagerService {

	@Autowired
	SceneManagerMapper sceneManagerMapper;

	@Autowired
	RemoteTopoService remoteTopoService;

	@Autowired
	RemoteSceneService remoteSceneService;

	@Override
	public LocalSceneManager copySceneManager(Integer appId, String sceneManagerId, Boolean clone) {
		LocalSceneManager sceneManager = sceneManagerMapper.selectById(sceneManagerId);
		String topoId = sceneManager.getTopoId();

		if (StringUtils.isBlank(topoId)) {
			throw new RuntimeException();
		}

		if (clone) {
			topoId = remoteTopoService.copyTopo(topoId);
		}
		return createManager(appId, topoId);
	}

	@Override
	public LocalSceneManager createManager(Integer appId, String topoId) {
		LocalSceneManager sceneManager = new LocalSceneManager();
		sceneManager.setAppId(appId);
		sceneManager.setTopoId(topoId);
		sceneManager.setSceneId("");
		sceneManagerMapper.insert(sceneManager);

		return sceneManager;
	}

	@Override
	public String saveTopo(JSONObject topoData, Integer appId) {
		topoData.put("reference_type", appId);
		return remoteTopoService.save(topoData);
	}

	@Override
	public String saveTopo(JSONObject topoData, String topoId) {
		topoData.put("id", topoId);
		return remoteTopoService.save(topoData);
	}

	@Override
	public Integer getNodeCount(LocalSceneManager sceneManager) {
		if (null != sceneManager.getNodeCount()) {
			return sceneManager.getNodeCount();
		} else {
			return remoteTopoService.getTopoDigest(sceneManager.getTopoId()).getNodeCounts();
		}
	}

	@Override
	public Integer getNodeCount(String managerId) {
		LocalSceneManager sceneManager = sceneManagerMapper.selectById(managerId);
		if (sceneManager == null) {
			throw new RuntimeException();
		}
		return getNodeCount(sceneManager);
	}

	@Override
	public JSONObject getSceneData(LocalSceneManager sceneManager, String userId) {
		if (StringUtils.isNotBlank(sceneManager.getSceneId())) {
			JSONObject scene = remoteSceneService.getById(sceneManager.getSceneId(), userId);
			if (scene != null) {
				return scene.getJSONObject("scene");
			}
		}
		return null;
	}

	@Override
	public JSONObject getTopoData(LocalSceneManager sceneManager) {
		return remoteTopoService.getTopoById(sceneManager.getTopoId()).getJSONObject("scene_config");
	}

	@Override
	public void updateScene(LocalSceneManager sceneManager, String sceneId) {
		sceneManager.setSceneId(sceneId);
		sceneManagerMapper.updateById(sceneManager);
	}

}
