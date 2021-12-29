package com.cyberpeace.cr.teamtrain.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.context.scene.MonitorSession;
import com.cyberpeace.cr.crbase.service.context.scene.Port;
import com.cyberpeace.cr.crbase.service.context.scene.SceneData;
import com.cyberpeace.cr.crbase.service.context.scene.SceneElement;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "团队训练实例")
@RestController
@RequestMapping("/cr/training_team/training_team_teacher")
public class TeamTrainTeacherController {

	@Autowired
	private TeamRoomService teamRoomService;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	RemoteUserService remoteUserService;

	@ApiOperation("获取桌面监控")
	@GetMapping("/sessions")
	BaseRsp<List<MonitorSession>> getSessions(
			@ApiParam(name = "room_id", value = "训练实例id", required = true) String room_id,
			@ApiParam(name = "student_id", value = "学员id", required = false) String student_id) {
		TeamRoom room = teamRoomService.getById(room_id);
		// 1. 确认场景已开启
		if (room == null || StringUtils.isBlank(room.getLocalSceneId())) {
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		LocalScene localScene = localSceneService.getById(room.getLocalSceneId());
		// 2. 获取中台会话数据
		JSONArray sessions = localSceneService.getMonitorSessions(localScene, student_id);
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

		return new BaseRsp<>(sessionList);
	}
}
