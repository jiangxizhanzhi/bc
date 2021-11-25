package com.cyberpeace.cr.crbase.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.dao.entity.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.context.scene.RoomInfo;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Service("sceneHandler")
@Slf4j
public class SceneHandler {

	private static Map<Integer, SceneHandler> register = Maps.newHashMap();

	public Integer getAppId() {
		return null;
	}

	@PostConstruct
	protected void init() {
		register.put(this.getAppId(), this);
	}

	public SceneHandler getHandler(Integer appId) {
		return register.get(appId);
	}

	public String getRoomIdBySceneManagerId(String sceneManagerId) {
		return null;
	}

	public RoomInfo getRoomInfoBySceneManagerId(String sceneManagerId) {
		return null;
	}

	public CrRoleNodesData getNodes(String userId, String roomId, String roleId) {
		// null 代表所有节点可见
		return null;
	}

	public void checkSceneOperationPermission(String roomId, String userId) {}

	public void checkBeforeStartingScene(String roomId) {}
}
