package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cyberpeace.common.context.scene.ElementFlow;
import com.cyberpeace.cr.redvsblue.service.context.RbSceneData;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.enums.SceneStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrSceneService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.SceneFilterService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfig;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.context.scene.Flow;
import com.cyberpeace.cr.redvsblue.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.entity.RbTimeManagement;
import com.cyberpeace.cr.redvsblue.service.*;
import com.cyberpeace.cr.redvsblue.service.context.RbSceneViewContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.scene.*;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RbSceneServiceImpl implements RbSceneService {

	private static final Integer PAUSE_FLAG = 1;

	private static final Integer UNPAUSE_FLAG = 2;

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private SceneFilterService sceneFilterService;

	@Autowired
	private RbTimeManagementService rbTimeManagementService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private CrSceneService crSceneService;

	@Autowired
	private RbDeviceService rbDeviceService;

	@Autowired
	private RbCommonDeviceService rbCommonDeviceService;

	@Override
	public String startScene(String roomId, String userId) {
		// 1、权限校验，学员是不可以启动场景的
		rbRoomService.checkTeacherPermission(roomId, userId);

		RbRoom room = rbRoomService.getById(roomId);
		// 训练暂停状态不能启动场景
		RbTimeManagement management = rbTimeManagementService.getByRoomId(roomId);
		if (management != null && management.isPaused()) {
			log.error("The training is paused, can not start scene, roomId:{}", roomId);
			throw new BusinessException(CrBusinessErrorCode.ALREADY_PAUSED);
		}
		// 2、如果已经有启动的场景，不能重复启动; 如果有脏数据，清除脏数据
		LocalScene localSceneExisted = localSceneService.getByBusinessInstanceId(roomId);
		if (localSceneExisted != null) {
			if (StringUtils.isBlank(localSceneExisted.getMpSceneId())) {
				clearLocal(room);
			} else {
				log.error("The scene has already started, can not start scene again, roomId:{}",
						roomId);
				throw new BusinessException(CrBusinessErrorCode.SCENE_START_REPEAT);
			}
		}
		// 3、构建本地scene对象
		LocalScene localScene = createLocalScene(room, room.getCopyedTopoId());

		// 4、启动mp场景
		String sceneId = localSceneService.startScene(localScene);

		try {
			// 5、更新本地数据库数据
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					// 更新表数据
					localScene.setMpSceneId(sceneId);
					localScene.setSceneStatus(SceneStatusEnum.START.getCode());
					localScene.setStartTime(new Date());
					room.setLocalSceneId(localScene.getResourceId());

					// 执行表更新
					localSceneService.save(localScene);
					rbRoomService.updateById(room);
				}
			});
			// 5.1 本地保存数据错误，删除已启动的远程场景
		} catch (Exception e) {
			localSceneService.stopScene(localScene);
			log.error("Failed to save local data:", e);
			throw new BusinessException(CrBusinessErrorCode.SAVE_LOCAL_DATA_FAILURE);
		}

		return sceneId;
	}

	private LocalScene createLocalScene(RbRoom room, String topoId) {
		LocalScene localScene = new LocalScene();

		localScene.setBusinessCode(BusinessCode.RED_VS_BLUE.getCode());
		localScene.setUserId(UserInfoContext.getUserContext().getUserId());
		localScene.setBusinessInstanceId(room.getResourceId());
		localScene.setTopoId(topoId);
		localScene.setResourceId(IDGeneratorUtil.getID());
		localScene.setSceneStatus(SceneStatusEnum.INIT.getCode());
		localScene.setDestroyMode(LocalScene.DestroyMode.MANUAL.getCode());
		localScene.setStartTime(new Date());
		localScene.setSceneAttr(SceneStartContext.SceneAttrEnum.TEAM.getCode());
		localScene.setSceneName(room.getName());
		localScene.setMeta(JSON.toJSONString(
				LocalSceneMeta.builder().appResourceId(room.getResourceId()).build()));
		localScene.setRecord(room.getEnableRecording());

		return localScene;
	}

	@Override
	public RbSceneViewContext viewScene(String roomId, String roleId) {
		RbSceneViewContext ret = new RbSceneViewContext();
		ret.setRoomId(roomId);
		RbRoom room = rbRoomService.getById(roomId);
		if (room == null) {
			log.error("Room not exist, roomId:{}", roomId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}
		// 1. 获取用户可见节点
		String userId = UserInfoContext.getUserContext().getUserId();
		List<CrRole> roleList = crRoleService.getRolesByRoomId(roomId);
		CrRole selfRole = CrRole.findRoleByUserId(roleList, userId);
		if (null == selfRole) {
			log.error("user [{}] has no permission to access rvb training [{}]", userId, roomId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
		CrRoleNodesData nodes = getNodes(selfRole, roleList, roleId);

		// 2、尝试获取启动的远程场景
		JSONObject sceneData = getMpSceneData(room);
		if (sceneData != null) {
			sceneFilterService.filterVisibleTopo(sceneData, nodes);
			ret.setSceneData(sceneData);
			ret.setLocalSceneId(room.getLocalSceneId());
			ret.setMpSceneId(sceneData.getString(CrCommonConstants.MP_SCENE_ID_KEY));

			// 4. 场景角色资产配置，用于动态编辑,只有教员有权限
			if (selfRole.isRvbStaff()) {
				List<CrRoleConfig> redConfigList = Lists.newArrayList();
				List<CrRoleConfig> blueConfigList = Lists.newArrayList();
				for (CrRole role : roleList) {
					if (role.isRvbStudent()) {
						CrRoleConfig config = new CrRoleConfig();
						CrRoleNodesData nodesData = role.getNodeData();
						config.setId(role.getResourceId());
						config.setName(role.getName());
						config.setAssetNodes(nodesData.getAssetNodes());
						config.setVisibleNodes(nodesData.getVisibleNodes());
						if (role.isBlueStudent()) {
							blueConfigList.add(config);
						} else {
							redConfigList.add(config);
						}
					}
				}
				ret.setRedRoles(redConfigList);
				ret.setBlueRoles(blueConfigList);
			}
			return ret;
		}
		// 3、 远程场景不存在，返回topo数据
		JSONObject topoData = localSceneService.getTopoData(room.getCopyedTopoId())
				.getJSONObject(CrCommonConstants.TOPO_DATA_KEY);
		sceneFilterService.filterVisibleTopo(topoData, nodes);
		ret.setTopoId(room.getCopyedTopoId());
		ret.setTopoData(topoData);
		return ret;
	}

	@Override
	public Boolean stopScene(String roomId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、只有教员角色可以操作场景
		rbRoomService.checkTeacherPermission(roomId, userId);

		RbRoom room = rbRoomService.getById(roomId);

		// 2、如果场景已经停止，返回成功
		if (StringUtils.isBlank(room.getLocalSceneId())) {
			return true;
		}
		LocalScene localScene = localSceneService.getById(room.getLocalSceneId());
		Boolean stopResult = localSceneService.stopScene(localScene);
		if (stopResult) {
			// 记录复盘用的scene_id
			// 是否需要更新replay_scene_id。训练未结束，更新；训练已结束，场景开始时间在训练结束之前才更新。
			// 只有训练已结束且场景开启时间在训练结束之后，才不更新
			if (!(room.isEnded() && localScene.getCreateTime()
					.after(rbTimeManagementService.getActualEndTime(roomId)))) {
				room.setReplaySceneId(localScene.getMpSceneId());
			}
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					// 清除localScene
					clearLocal(room);
					// 恢复创建房间时的角色资产配置
					crRoleService.recoverNodesConfig(roomId);
					// 清理 device
					rbDeviceService.removeByRoomId(roomId);
					// 删除常用资产
					rbCommonDeviceService.delByRoomId(roomId);
				}
			});
			crSceneService.sendSceneStopMessage(roomId);
		}

		return stopResult;
	}

	@Override
	public void clearLocal(RbRoom room) {
		String localSceneId = room.getLocalSceneId();
		room.setLocalSceneId("");
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				rbRoomService.updateById(room);
				localSceneService.removeById(localSceneId);
			}
		});
	}

	@Override
	public Boolean pauseScene(String roomId) {
		// 1. 校验场景存在和用户权限
		RbRoom room = rbRoomService.getById(roomId);
		checkBeforePauseOps(room, PAUSE_FLAG);
		// 如果场景在切换中不允许操作
		RbSceneData sceneData = getSceneData(room);
		if (sceneData != null && !sceneData.isFinish()) {
			log.error("Scene is switching, roomId:{}", roomId);
			throw new BusinessException(CrBusinessErrorCode.SCENE_SWITCHING);
		}
		// 2、 场景开启的状态下发送mp暂停请求
		if (localSceneService.ensureSceneStarted(room.getLocalSceneId())
				&& !localSceneService.pauseScene(room.getLocalSceneId())) {
			log.error("Failed to pause scene, roomId:{}", roomId);
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_PAUSE_SCENE);
		}
		// 3. 更新时间管理
		RbTimeManagement timeManagement = rbTimeManagementService.getByRoomId(roomId);
		rbTimeManagementService.updateTimeManagement(timeManagement, room,
				RbTimeManagement.TimeManagementStatus.PAUSE.getCode(),
				UserInfoContext.getUserContext().getUserId());

		// 4.发送ws提醒前端刷新训练状态
		crTaskService.pushStatusWs(roomId, RbTimeManagement.TimeManagementStatus.PAUSE.getCode());

		return true;
	}

	@Override
	public Boolean unpauseScene(String roomId) {
		// 1. 校验场景存在和用户权限
		RbRoom room = rbRoomService.getById(roomId);
		checkBeforePauseOps(room, UNPAUSE_FLAG);
		// 如果场景在切换中不允许操作
		RbSceneData sceneData = getSceneData(room);
		if (sceneData != null && !sceneData.isFinish()) {
			log.error("Scene is switching, roomId:{}", roomId);
			throw new BusinessException(CrBusinessErrorCode.SCENE_SWITCHING);
		}
		// 2、 如果mp场景存在，发送mp解除暂停请求
		if (localSceneService.ensureSceneStarted(room.getLocalSceneId())
				&& !localSceneService.unpauseScene(room.getLocalSceneId())) {
			log.error("Failed to unpause scene, roomId:{}", roomId);
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_UNPAUSE_SCENE);
		}
		// 3. 更新时间管理
		RbTimeManagement timeManagement = rbTimeManagementService.getByRoomId(roomId);
		rbTimeManagementService.updateTimeManagement(timeManagement, room,
				RbTimeManagement.TimeManagementStatus.RECOVERY.getCode(),
				UserInfoContext.getUserContext().getUserId());

		// 4.发送ws提醒前端刷新训练状态
		crTaskService.pushStatusWs(roomId,
				RbTimeManagement.TimeManagementStatus.RECOVERY.getCode());

		return true;
	}

	private void checkBeforePauseOps(RbRoom room, Integer flag) {
		if (room == null) {
			log.error("Room not exist, roomId:{}", room);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}

		// 只有导演可以暂停、恢复
		String userId = UserInfoContext.getUserContext().getUserId();
		rbRoomService.checkDirectorPermission(room.getResourceId(), userId);

		// 校验当前状态
		RbTimeManagement management = rbTimeManagementService.getByRoomId(room.getResourceId());
		if (management == null) {
			log.error("Time management not exist, roomId:{}", room.getResourceId());
			throw new BusinessException(CrBusinessErrorCode.TRAIN_NOT_BEGIN);
		}
		if (management.isStopped()) {
			log.error("Train is over, roomId:{}", room.getResourceId());
			throw new BusinessException(CrBusinessErrorCode.TRAIN_IS_OVER);
		}
		if (flag.equals(PAUSE_FLAG) && management.isPaused()) {
			log.error("Train is already paused, roomId:{}", room.getResourceId());
			throw new BusinessException(CrBusinessErrorCode.ALREADY_PAUSED);
		}
		if (flag.equals(UNPAUSE_FLAG) && !management.isPaused()) {
			log.error("Train is not paused, roomId:{}", room.getResourceId());
			throw new BusinessException(ErrorCode.NOT_PAUSED);
		}
	}

	@Override
	public List<Flow> getFlowList(String roomId) {
		// 1.创建变量
		RbRoom room = rbRoomService.getById(roomId);
		String userId = UserInfoContext.getUserContext().getUserId();
		List<Flow> flows = Lists.newArrayList();
		CpFlow cpflow;
		Map<String, String> elementsMap = Maps.newHashMap();

		// 2. 获取topo
		LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
		// 2.1 场景已启动
		if (localScene != null) {
			// 2.1.1 请求sceneData
			JSONObject sceneData = localSceneService.getSceneData(localScene, userId);
			// 2.1.2 转换JsonObject为SceneData对象
			SceneData data = sceneData.toJavaObject(SceneData.class);
			// 2.1.3 获取流量数据
			cpflow = data.getPlugins().getCpFlow();
			// 2.1.4 生成节点数据map
			List<SceneElement> elements = data.getTopology().getElements();
			for (SceneElement element : elements) {
				elementsMap.put(element.getId(), element.getName());
			}
		} else {
			// 2.2 场景未启动
			// 2.2.1 请求topoData
			JSONObject topoData = localSceneService.getTopoData(room.getCopyedTopoId());
			TopoData data = topoData.getJSONObject(CrCommonConstants.TOPO_DATA_KEY)
					.toJavaObject(TopoData.class);
			cpflow = data.getPlugins().getCpFlow();

			List<TopoElement> elements = data.getTopology().getElements();
			for (TopoElement element : elements) {
				elementsMap.put(element.getId(), element.getName());
			}
		}
		// 3. 没有流量，返回空列表
		if (cpflow == null) {
			return flows;
		}
		// 4. 获取每个节点的流量，整合成列表
		for (Map.Entry<String, ElementFlowList> entry : cpflow.getElements().entrySet()) {
			String nodeId = entry.getKey();
			String nodeName = elementsMap.get(nodeId);

			for (ElementFlow flow : entry.getValue().getFlows()) {
				Flow flowData = BeanUtils.copy(flow, Flow.class);
				// 4.1 添加节点数据
				flowData.setNodeId(nodeId);
				flowData.setNodeName(nodeName);
				flows.add(flowData);
			}
		}

		// 5. 返回流量数据列表
		return flows;
	}

	@Override
	public JSONArray getAssetList(String roomId, Integer category, String name) {
		RbRoom room = rbRoomService.getOrExceptionById(roomId);
		String localSceneId = room.getLocalSceneId();
		if (StringUtils.isNotBlank(localSceneId)) {
			return localSceneService.getAssetListByInstanceId(roomId, category, name, true);
		} else {
			String topoId = room.getCopyedTopoId();
			return localSceneService.getAssetListByTopoId(topoId, category, name);
		}
	}

	@Override
	public JSONObject getNodeStatistics(String roomId) {
		RbRoom room = rbRoomService.getOrExceptionById(roomId);
		String localSceneId = room.getLocalSceneId();
		if (StringUtils.isNotBlank(localSceneId)) {
			return localSceneService.getNodeStatistics(localSceneId);
		} else {
			String topoId = room.getCopyedTopoId();
			return localSceneService.getNodeStatisticsByTopoId(topoId);
		}
	}

	private JSONObject getMpSceneData(RbRoom room) {
		String localSceneId = room.getLocalSceneId();
		// localSceneId字段为空
		if (StringUtils.isBlank(localSceneId)) {
			return null;
		}
		// localScene不存在，localSceneId字段是脏数据
		LocalScene localScene = localSceneService.getById(localSceneId);
		if (null == localScene) {
			room.setLocalSceneId("");
			rbRoomService.updateById(room);
			return null;
		}
		// localScene 的 mpSceneId为空，localScene是脏数据
		String mpSceneId = localScene.getMpSceneId();
		if (StringUtils.isBlank(mpSceneId)) {
			clearLocal(room);
			return null;
		}
		// 请求远程场景数据
		JSONObject sceneData = localSceneService.getSceneData(mpSceneId,
				UserInfoContext.getUserContext().getUserId());
		// 场景数据为空，清除本地脏数据
		if (null == sceneData) {
			clearLocal(room);
			return null;
		}
		// 场景实际已被删除，清除本地脏数据
		RbSceneData data = sceneData.toJavaObject(RbSceneData.class);
		if (data.isStopped()) {
			clearLocal(room);
			return null;
		}
		sceneData.put(CrCommonConstants.MP_SCENE_ID_KEY, mpSceneId);
		return sceneData;
	}

	public RbSceneData getSceneData(RbRoom room) {
		JSONObject sceneData = getMpSceneData(room);
		if (sceneData == null) {
			return null;
		}
		return sceneData.toJavaObject(RbSceneData.class);
	}

	private CrRoleNodesData getNodes(CrRole selfRole, List<CrRole> roleList, String roleId) {

		// 1、如果是学员角色，直接返回
		if (selfRole.isRvbStudent()) {
			return selfRole.getNodeData();
		}

		// 2、教员角色查看自己的节点，不需要传roleId
		if (StringUtils.isBlank(roleId)) {
			return null;
		}

		// 3、教员查看学员角色的战争迷雾
		// 3.1、查找学员的role
		CrRole fakeRole = CrRole.findRoleByRoleId(roleList, roleId);
		// 3.2、返回学员role的可见节点
		if (null != fakeRole) {
			return fakeRole.getNodeData();
		}

		return null;
	}
}
