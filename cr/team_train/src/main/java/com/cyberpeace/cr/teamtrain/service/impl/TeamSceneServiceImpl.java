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
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.SceneFilterService;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
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

		// 2、如果已经有启动的场景，不能重复启动; 如果有脏数据，清除脏数据
		LocalScene localSceneExisted = localSceneService.getByBusinessInstanceId(roomId);
		if (localSceneExisted != null) {
			if (StringUtils.isBlank(localSceneExisted.getMpSceneId())) {
				clearLocal(room);
			} else {
				throw new BusinessException(CrBusinessErrorCode.SCENE_START_REPEAT);
			}
		}

		// 3、构建本地scene对象
		LocalScene localScene = createLocalScene(LocalScene.BusinessCode.TEAM_TRAINING.getCode(),
				room, room.getCopyedTopoId());

		// 4、启动mp场景
		String sceneId = localSceneService.startScene(localScene);

		// 5、更新本地数据库数据
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
	public SceneViewContext viewScene(String roomId, String roleId) {
		SceneViewContext ret = new SceneViewContext();
		ret.setRoomId(roomId);

		TeamRoom room = teamRoomService.getById(roomId);

		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、获取可见节点&鉴权， null代表所有节点可见
		CrRoleNodesData nodes = getNodes(userId, roomId, roleId);

		// 2、如果没有启动的场景，直接返回拓扑数据
		LocalScene localScene = localSceneService.getById(room.getLocalSceneId());

		// 后两个判断，主要是为了防止脏数据
		if (StringUtils.isBlank(room.getLocalSceneId()) || null == localScene
				|| StringUtils.isBlank(localScene.getMpSceneId())) {
			JSONObject topoData = getTopoData(room.getCopyedTopoId(), nodes);
			ret.setTopoData(topoData);
			return ret;
		}

		// 3、有启动的场景，尝试返回启动的场景数据
		String sceneId = localScene.getMpSceneId();
		JSONObject sceneData = localSceneService.getSceneData(sceneId, userId);
		if (sceneData != null) {
			sceneFilterService.filterVisibleTopo(sceneData, nodes);
			ret.setSceneData(sceneData);
			ret.setLocalSceneId(room.getLocalSceneId());
		} else {
			log.info("roomId=[{}] local has sceneId=[{}], remote is null", room, sceneId);
			// 清除本地脏数据
			clearLocal(room);

			// 这时实际场景是没启动的，返回拓扑数据
			JSONObject topoData = getTopoData(room.getCopyedTopoId(), nodes);
			ret.setTopoData(topoData);
		}

		return ret;
	}

	@Override
	public Boolean stopScene(String roomId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、只有教员可以操作场景
		teamRoomService.checkTeacherPermission(roomId, userId);

		TeamRoom teamRoom = teamRoomService.getById(roomId);

		// 2、如果场景已经停止，返回成功
		if (StringUtils.isBlank(teamRoom.getLocalSceneId())) {
			return true;
		}

		Boolean stopResult = localSceneService.stopScene(teamRoom.getLocalSceneId());
		if (stopResult) {
			clearLocal(teamRoom);
		}

		return stopResult;
	}

	@Override
	public void clearLocal(TeamRoom room) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				String localSceneId = room.getLocalSceneId();
				room.setLocalSceneId("");

				// 清除业务数据、localscene表中数据
				localSceneService.removeById(localSceneId);
				teamRoomService.updateById(room);
			}
		});
	}

	@Override
	public Boolean pauseScene(String roomId) {

		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、只有教员可以操作场景
		teamRoomService.checkTeacherPermission(roomId, userId);

		TeamRoom teamRoom = teamRoomService.getById(roomId);

		// 2、如果场景不存在
		if (StringUtils.isBlank(teamRoom.getLocalSceneId())) {
			throw new BusinessException(CrBusinessErrorCode.LOCAL_SCENE_NOT_EXIST);
		}
		// 3、 发送mp暂停请求
		return localSceneService.pauseScene(teamRoom.getLocalSceneId());
	}

	@Override
	public Boolean unpauseScene(String roomId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、只有教员可以操作场景
		teamRoomService.checkTeacherPermission(roomId, userId);

		TeamRoom teamRoom = teamRoomService.getById(roomId);

		// 2、如果场景不存在
		if (StringUtils.isBlank(teamRoom.getLocalSceneId())) {
			throw new BusinessException(CrBusinessErrorCode.LOCAL_SCENE_NOT_EXIST);
		}
		// 3、 发送mp恢复暂停请求
		return localSceneService.unpauseScene(teamRoom.getLocalSceneId());
	}

	private JSONObject getTopoData(String topoId, CrRoleNodesData nodes) {
		JSONObject topoData = localSceneService.getTopoData(topoId).getJSONObject("scene_config");

		// 拓扑数据根据用户的角色，进行适当过滤
		sceneFilterService.filterVisibleTopo(topoData, nodes);

		return topoData;
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
