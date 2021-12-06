package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.SceneFilterService;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamSceneService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.scene.SceneStartContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TeamSceneServiceImpl implements TeamSceneService {

	@Autowired
	private TeamRoomService teamRoomService;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private SceneFilterService sceneFilterService;

	@Override
	public void startScene(String roomId, String userId) {
		// 1、权限校验，学员是不可以启动场景的
		teamRoomService.checkTeacherPermission(roomId, userId);

		TeamRoom room = teamRoomService.getById(roomId);

		// 2、构建本地scene对象
		LocalScene localScene = createLocalScene(LocalScene.BusinessCode.TEAM_TRAINING.getCode(),
				room, room.getCopyedTopoId());

		// 3、启动mp场景
		String sceneId = localSceneService.startScene(localScene);

		// 4、更新本地数据库数据
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// 更新表数据
				localScene.setMpSceneId(sceneId);
				localScene.setSceneStatus(LocalScene.SceneStatusEnum.START.getCode());
				localScene.setStartTime(new Date());
				room.setLocalSceneId(localScene.getResourceId());

				// 执行表更新
				localSceneService.save(localScene);
				teamRoomService.updateById(room);
			}
		});

	}

	@Override
	public JSONObject viewScene(String roomId, String roleId) {
		TeamRoom room = teamRoomService.getById(roomId);

		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、获取可见节点&鉴权， null代表所有节点可见
		CrRoleNodesData nodes = getNodes(userId, roomId, roleId);

		// 2、如果没有启动的场景，直接返回拓扑数据
		if (StringUtils.isBlank(room.getLocalSceneId())) {
			return getTopoData(room.getCopyedTopoId(), nodes);
		}

		// 3、有启动的场景，尝试返回启动的场景数据
		LocalScene localScene = localSceneService.getById(room.getLocalSceneId());
		String sceneId = localScene.getMpSceneId();
		JSONObject sceneData = localSceneService.getSceneData(sceneId, userId);
		if (sceneData != null) {
			sceneFilterService.filterVisibleTopo(sceneData, nodes);
			return sceneData;
		} else {
			log.info("roomId=[{}] local has sceneId=[{}], remote is null", room, sceneId);
			// 清除本地脏数据
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					room.setLocalSceneId(null);

					localSceneService.removeById(localScene.getResourceId());
					teamRoomService.updateById(room);
				}
			});

			// 这时实际场景是没启动的，返回拓扑数据
			return getTopoData(localScene.getTopoId(), nodes);
		}

	}

	private JSONObject getTopoData(String topoId, CrRoleNodesData nodes) {
		JSONObject topoData = localSceneService.getTopoData(topoId).getJSONObject("scene_config");

		// 拓扑数据根据用户的角色，进行适当过滤
		sceneFilterService.filterVisibleTopo(topoData, nodes);

		return topoData;
	}

	@Override
	public void stopScene() {

	}

	private CrRoleNodesData getNodes(String userId, String roomId, String roleId) {
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);

		// 1、如果是学员，直接返回
		if (role.getRoleGroup().equals(CrRole.TeamRoleGroupCode.STUDENT.getCode())) {
			return JsonUtil.toJsonObject(role.getNodes(), CrRoleNodesData.class);
		}

		// 2、教员查看自己的节点，不需要传roleId
		if (StringUtils.isBlank(roleId)) {
			return null;
		}

		// 3、教员查看学员角色的战争迷雾
		// 3.1、查找学员的role
		CrRole fakeRole = crRoleService.getById(roleId);
		// 3.2、返回学员role的可见节点
		if (fakeRole.getRoomId().equals(roomId)) {
			return JsonUtil.toJsonObject(fakeRole.getNodes(), CrRoleNodesData.class);
		}

		return null;
	}

	private LocalScene createLocalScene(Integer businessCode, TeamRoom teamRoom, String topoId) {
		LocalScene localScene = new LocalScene();

		localScene.setBusinessCode(businessCode);
		localScene.setUserId(UserInfoContext.getUserContext().getUserId());
		localScene.setBusinessInstanceId(teamRoom.getResourceId());
		localScene.setTopoId(topoId);
		localScene.setResourceId(IDGeneratorUtil.getID());
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.INIT.getCode());
		localScene.setDestroyMode(LocalScene.DestroyMode.MANUAL.getCode());
		localScene.setStartTime(new Date());
		localScene.setSceneAttr(SceneStartContext.SceneAttrEnum.TEAM.getCode());
		localScene.setSceneName(teamRoom.getName());
		localScene.setMeta(JSON.toJSONString(
				LocalSceneMeta.builder().appResourceId(teamRoom.getResourceId()).build()));

		return localScene;
	}
}
