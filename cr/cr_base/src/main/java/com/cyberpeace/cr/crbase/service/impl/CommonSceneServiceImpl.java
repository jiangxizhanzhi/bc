package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.CommonSceneService;
import com.cyberpeace.cr.crbase.service.context.scene.MonitorSession;
import com.cyberpeace.cr.crbase.service.context.scene.Port;
import com.cyberpeace.cr.crbase.service.context.scene.SceneData;
import com.cyberpeace.cr.crbase.service.context.scene.SceneElement;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommonSceneServiceImpl implements CommonSceneService {

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 场景删除事件代号
	 */
	private static final Integer MP_SCENE_DELETE_EVENT_CODE = 9;

	/**
	 * 场景事件完成代号
	 */
	private static final Integer MP_SCENE_PROGRESS_FINISH_CODE = 4;

	@Override
	public List<MonitorSession> getMonitorSessions(String localSceneId, String studentId) {
		// 1. 查询localScene
		LocalScene localScene = localSceneService.getById(localSceneId);
		if (localScene == null) {
			throw new BusinessException(CrBusinessErrorCode.LOCAL_SCENE_NOT_EXIST);
		}
		// 2. 获取中台会话数据
		JSONArray sessions = localSceneService.getMonitorSessions(localScene, studentId);
		List<MonitorSession> sessionList = sessions.toJavaList(MonitorSession.class);
		// 3. 获取用户名、用户头像、ip
		JSONObject sceneData = localSceneService.getSceneData(localScene,
				UserInfoContext.getUserContext().getUserId());
		SceneData data = sceneData.toJavaObject(SceneData.class);
		Map<String, String> ipMap = Maps.newHashMap();
		for (SceneElement element : data.getTopology().getElements()) {
			for (Port port : element.getPorts()) {
				ipMap.put(element.getId(), port.getIp());
				break;
			}
		}
		// 3.1 批量获取用户信息
		List<String> userIds = Lists.newArrayList();
		for (MonitorSession session : sessionList) {
			userIds.add(session.getUser());
		}
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);

		for (MonitorSession session : sessionList) {
			UserInfo userInfo = userInfoMap.get(session.getUser());
			if (userInfo == null) {
				continue;
			}
			session.setUsername(userInfo.getUserName());
			session.setAvatar(userInfo.getAvatarUrl());
			session.setIp(ipMap.get(session.getNodeId()));
		}

		return sessionList;
	}

	@Override
	public Boolean isStopped(JSONObject sceneData) {
		SceneData data = sceneData.toJavaObject(SceneData.class);
		return data.getTopology().getScene().getEvent().equals(MP_SCENE_DELETE_EVENT_CODE) && data
				.getTopology().getScene().getEventStatus().equals(MP_SCENE_PROGRESS_FINISH_CODE);
	}
}
