package com.cyberpeace.cr.redvsblue.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseTable;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.rsp.CrLabelGroup;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.redvsblue.controller.rsp.*;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbSituationService;
import com.cyberpeace.cr.redvsblue.service.context.RbSituation;
import com.cyberpeace.cr.redvsblue.service.context.RbSituationServer;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.situation.constant.ws.ActionTypeZHL;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RbSituationServiceImpl implements RbSituationService {

	@Autowired
	private RbRoomMapper rbRoomMapper;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private MissionEventDeviceService missionEventDeviceService;

	@Autowired
	private CrTrainActionService crTrainActionService;

	@Autowired
	private CrRoleAssetNodeService crRoleAssetNodeService;

	@Autowired
	private CrConstantsService crConstantsService;

	@Override
	public List<RbSituation> getAllRbSituation() {
		return rbRoomMapper.selectAllRunning();
	}

	public Map<String, String> nodeMap = new HashMap<>();

	public String sceneId;

	public String mpSceneId;

	@Override
	public List<RbSituationServer> getRbServerByResourceId(String resourceId) {
		nodeMap.clear();
		List<RbSituationServer> rbSituationServerList = new ArrayList<>();
		resourceId = resourceId.substring(0,resourceId.length()-1);
		RbRoom room = rbRoomService.getById(resourceId);
		if(room == null){
			throw new BusinessException(CrBusinessErrorCode.LOCAL_SCENE_NOT_EXIST);
		}
		LocalScene localScene = localSceneService.getById(room.getLocalSceneId());

		JSONObject scene = remoteSceneService.getById(localScene.getMpSceneId(), null);
		List<JSONObject> elements = scene.getJSONObject("scene").getJSONObject("topology").getObject("elements",List.class);
		for(JSONObject jsonObject : elements){
			RbSituationServer rbSituationServer = new RbSituationServer();
			rbSituationServer.setServerId(jsonObject.getString("id"));
			rbSituationServer.setServerName(jsonObject.getString("name"));
			rbSituationServer.setNetworkId("network-1");
			rbSituationServer.setNetworkName("默认网络");
			rbSituationServerList.add(rbSituationServer);
		}
		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(room.getResourceId());
		roleQuery.setRoleGroup(CrRole.RbRoleGroup.RED.getCode());
		List<CrRole> redRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);
		for(CrRole redRole: redRoleList){
			List<JSONObject> visibleNodes = JSONObject.parseObject(redRole.getNodes()).getObject("visibleNodes",List.class);
			for(JSONObject visibleNode: visibleNodes){
				String nodeId = visibleNode.getString("id");
				for (RbSituationServer rbSituationServer: rbSituationServerList){
					if(rbSituationServer.getServerId().equals(nodeId)){
						rbSituationServer.setRole("red");
						nodeMap.put(nodeId, "red");
					}
				}
			}
		}
		roleQuery.setRoleGroup(CrRole.RbRoleGroup.BLUE.getCode());
		List<CrRole> blueRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);
		for(CrRole blueRole: blueRoleList){
			List<JSONObject> visibleNodes = JSONObject.parseObject(blueRole.getNodes()).getObject("visibleNodes",List.class);
			for(JSONObject visibleNode: visibleNodes){
				String nodeId = visibleNode.getString("id");
				for (RbSituationServer rbSituationServer: rbSituationServerList){
					if(rbSituationServer.getServerId().equals(nodeId)){
						rbSituationServer.setRole("blue");
						nodeMap.put(nodeId, "blue");
					}
				}
			}
		}
		mpSceneId = localScene.getMpSceneId();
		sceneId = resourceId;
		return rbSituationServerList;
	}

	@Override
	public String getSceneId(){
		return sceneId;
	}

	@Override
	public Map<String, String> getNodeMap(){
		return nodeMap;
	}

	@Override
	public String getMpSceneId(){
		return mpSceneId;
	}

	private void checkRole(Integer roleGroup) {
		if (!CrRole.RbRoleGroup.isRvbStudentRole(roleGroup)) {
			throw new BusinessException(CrBusinessErrorCode.INVALID_ROLE_INFO);
		}
	}

	@Override
	public RbSituationScoreRsp getTeamScore(String roomId, Integer roleType) {
		checkRole(roleType);

		Map<String, Mission> missionMap = missionService.batchGetByRoom(roomId).stream().collect(Collectors.toMap(MissionBase::getResourceId, mission -> mission));

		List<CrRole> crRoleList = crRoleService.selectRbStudentsRoleByRoomIdAndRoleGroup(roomId, roleType);
		List<CrTask> crTaskList = crTaskService.getByRoles(crRoleList.stream().map(CrRole::getResourceId).collect(Collectors.toList()));
		crTaskList.sort(Comparator.comparing(
				(CrTask crTask) -> crTask.getScore() == null ? 0 : crTask.getScore()
		).reversed());

		Set<String> tmpFilter = Sets.newHashSet();
		crTaskList = crTaskList.stream().filter(crTask -> tmpFilter.add(crTask.getMissionId())).collect(Collectors.toList());

		Map<String, RbSituationScoreRsp.RoleScoreInfo> roleScoreInfoMap = crRoleList.stream().collect(Collectors.toMap(CrRole::getResourceId, crRole -> {
			RbSituationScoreRsp.RoleScoreInfo roleScoreInfo = new RbSituationScoreRsp.RoleScoreInfo();
			roleScoreInfo.setRoleId(crRole.getResourceId());
			roleScoreInfo.setRoleName(crRole.getName());
			roleScoreInfo.setScore(0);

			return roleScoreInfo;
		}));

		Integer score = 0;
		Integer residualScore = 0;
		for (CrTask crTask: crTaskList) {
			if (crTask.getStatus().intValue() == CrTask.TaskStatus.SUCCESS.getCode()) {
				score += crTask.getScore();
				RbSituationScoreRsp.RoleScoreInfo roleScoreInfo = roleScoreInfoMap.get(crTask.getRoleId());
				roleScoreInfo.setScore(roleScoreInfo.getScore() + crTask.getScore());

			} else {
				residualScore += missionMap.get(crTask.getMissionId()).getScore();
			}
		}

		RbSituationScoreRsp rbSituationScoreRsp = new RbSituationScoreRsp();
		rbSituationScoreRsp.setScore(score);
		rbSituationScoreRsp.setResidualScore(residualScore);

		rbSituationScoreRsp.setRoleScoreInfoList(
				roleScoreInfoMap.values().stream().peek(
						roleScoreInfo -> roleScoreInfo.setPercentage(
								roleScoreInfo.getScore() == 0
										? (double) 0
										: (double) roleScoreInfo.getScore() / (double) rbSituationScoreRsp.getScore() * 100
						)
				).collect(Collectors.toList())
		);

		return rbSituationScoreRsp;
	}

	@Override
	public List<RbSituationMissionStatusRsp> getMissionStatus(String roomId, Integer roleType) {
		checkRole(roleType);
		List<CrRole> crRoleList = crRoleService.selectRbStudentsRoleByRoomIdAndRoleGroup(roomId, roleType);
		Map<String, CrRole> crRoleMap = crRoleList.stream().collect(Collectors.toMap(CrRole::getResourceId, crRole -> crRole));
		Map<String, Mission> missionMap = missionService.batchGetByRoom(roomId).stream().collect(
				Collectors.toMap(MissionBase::getResourceId, mission -> mission)
		);

		List<CrTask> crTaskList = crTaskService.getByRoles(
				crRoleList.stream().map(CrRole::getResourceId).collect(Collectors.toList())
		);

		// 过滤重复的crTask，优先保留已完成状态
		Map<String, CrTask> crTaskMap = Maps.newHashMap();
		crTaskList.forEach(crTask -> {
			if (CrTask.TaskStatus.FINISHED_STATUS_LIST.contains(crTask.getStatus())) {
				crTaskMap.put(crTask.getMissionId(), crTask);
			} else {
				if (!crTaskMap.containsKey(crTask.getMissionId())) {
					crTaskMap.put(crTask.getMissionId(), crTask);
				}
			}
		});

		List<RbSituationMissionStatusRsp> result = Lists.newArrayList();

		crTaskMap.values().forEach(crTask -> {
			RbSituationMissionStatusRsp rbSituationMissionStatusRsp = new RbSituationMissionStatusRsp();

			Mission mission = missionMap.get(crTask.getMissionId());

			rbSituationMissionStatusRsp.setMissionTitle(mission.getTitle());
			rbSituationMissionStatusRsp.setRoleId(crTask.getRoleId());
			rbSituationMissionStatusRsp.setRoleName(crRoleMap.get(crTask.getRoleId()).getName());
			rbSituationMissionStatusRsp.setStatus(crTask.getStatus());
			rbSituationMissionStatusRsp.setUpdateTime(crTask.getUpdateTime());

			result.add(rbSituationMissionStatusRsp);
		});

		return result.stream().sorted(
				Comparator.comparing(RbSituationMissionStatusRsp::getUpdateTime).reversed()
		).collect(Collectors.toList());
	}

	@Override
	public List<RbSituationAdEventRsp> getAdEvent(String roomId, Integer roleType) {
		List<CrRole> crRoleList = crRoleService.selectRbStudentsRoleByRoomIdAndRoleGroup(roomId, roleType);
		Map<String, Mission> missionMap = missionService.batchGetByRoom(roomId).stream().collect(
				Collectors.toMap(MissionBase::getResourceId, mission -> mission)
		);

		List<CrTask> crTaskList = crTaskService.getByRoles(crRoleList.stream().map(CrRole::getResourceId).collect(Collectors.toList()))
				.stream()
				.filter(crTask -> crTask.getStatus().intValue() == CrTask.TaskStatus.SUCCESS.getCode())
				.collect(Collectors.toList());

		Map<String, String> deviceMap = rbRoomService.missionEventDevice(roomId).stream().collect(
				Collectors.toMap(CrLabelGroup::getValue, CrLabelGroup::getLabel)
		);

		List<RbSituationAdEventRsp> result = Lists.newArrayList();
		if (crTaskList.isEmpty()) {
			return result;
		}

		// 所有任务事件
		List<CrConstantsConfig> crConstantsConfigList = Lists.newArrayList();
		crConstantsConfigList.addAll(crConstantsService.getByType(CrConstantsConfig.ConstantsType.RED_ACTION_TYPE.getCode()));
		crConstantsConfigList.addAll(crConstantsService.getByType(CrConstantsConfig.ConstantsType.BLUE_ACTION_TYPE.getCode()));

		Map<Integer, String> actionMap = crConstantsConfigList.stream().collect(Collectors.toMap(
				CrConstantsConfig::getCode,
				CommonContext.isChinese() ? CrConstantsConfig::getCnName : CrConstantsConfig::getEnName
		));

		List<MissionEventDevice> missionEventDeviceList = missionEventDeviceService.lambdaQuery()
				.in(MissionEventDevice::getMissionId, crTaskList.stream().map(CrTask::getMissionId).collect(Collectors.toList()))
				.list();

		crTaskList.forEach(crTask -> {
			Mission mission = missionMap.get(crTask.getMissionId());

			RbSituationAdEventRsp rbSituationAdEventRsp = new RbSituationAdEventRsp();

			List<String> taskDeviceIds = missionEventDeviceList.stream()
					.filter(missionEventDevice -> missionEventDevice.getMissionId().equals(crTask.getMissionId()))
					.map(MissionEventDevice::getDeviceId)
					.collect(Collectors.toList());

			rbSituationAdEventRsp.setDeviceName(taskDeviceIds.stream().map(deviceMap::get).collect(Collectors.joining(", ")));
			rbSituationAdEventRsp.setEventName(actionMap.get(mission.getEventType()));
			rbSituationAdEventRsp.setEventTime(crTask.getLastSubmitTime());

			result.add(rbSituationAdEventRsp);

		});

		result.sort(Comparator.comparing(RbSituationAdEventRsp::getEventTime).reversed());

		return result;
	}

	@Override
	public RbSituationAssetStatusRsp getAssetStatus(String roomId, Integer roleType) {
		RbSituationAssetStatusRsp rbSituationAssetStatusRsp = new RbSituationAssetStatusRsp();
		List<CrRoleAssetNode> crRoleAssetNodeList =  crRoleAssetNodeService.lambdaQuery()
				.eq(CrRoleAssetNode::getRoomId, roomId)
				.list();

		int networkOriginalCount = 0;
		int networkChangedCount = 0;
		int operationOriginalCount = 0;
		int operationChangedCount = 0;
		int applicationOriginalCount = 0;
		int applicationChangedCount = 0;
		int securityOriginalCount = 0;
		int securityChangedCount = 0;
		for (CrRoleAssetNode crRoleAssetNode: crRoleAssetNodeList) {
			if (crRoleAssetNode.getOriginalRoleType().equals(roleType) && !crRoleAssetNode.getRoleType().equals(roleType)) {
				if (crRoleAssetNode.getNodeCategory() == 3) {
					networkChangedCount -= 1;
				} else if (crRoleAssetNode.getNodeCategory() == 1) {
					operationChangedCount -= 1;
				} else if (crRoleAssetNode.getNodeCategory() == 2) {
					applicationChangedCount -= 1;
				} else if (crRoleAssetNode.getNodeCategory() == 4) {
					securityChangedCount -= 1;
				}
			}

			if (!crRoleAssetNode.getOriginalRoleType().equals(roleType) && crRoleAssetNode.getRoleType().equals(roleType)) {
				if (crRoleAssetNode.getNodeCategory() == 3) {
					networkChangedCount += 1;
				} else if (crRoleAssetNode.getNodeCategory() == 1) {
					operationChangedCount += 1;
				} else if (crRoleAssetNode.getNodeCategory() == 2) {
					applicationChangedCount += 1;
				} else if (crRoleAssetNode.getNodeCategory() == 4) {
					securityChangedCount += 1;
				}
			}

			if (crRoleAssetNode.getOriginalRoleType().equals(roleType)) {
				if (crRoleAssetNode.getNodeCategory() == 3) {
					networkOriginalCount += 1;
				} else if (crRoleAssetNode.getNodeCategory() == 1) {
					operationOriginalCount += 1;
				} else if (crRoleAssetNode.getNodeCategory() == 2) {
					applicationOriginalCount += 1;
				} else if (crRoleAssetNode.getNodeCategory() == 4) {
					securityOriginalCount += 1;
				}
			}

		}

		rbSituationAssetStatusRsp.setNetwork(RbSituationAssetStatusRsp.AssetCount.builder()
				.originalCount(networkOriginalCount).changedCount(networkChangedCount).build());
		rbSituationAssetStatusRsp.setOperation(RbSituationAssetStatusRsp.AssetCount.builder()
				.originalCount(operationOriginalCount).changedCount(operationChangedCount).build());
		rbSituationAssetStatusRsp.setApplication(RbSituationAssetStatusRsp.AssetCount.builder()
				.originalCount(applicationOriginalCount).changedCount(applicationChangedCount).build());
		rbSituationAssetStatusRsp.setSecurity(RbSituationAssetStatusRsp.AssetCount.builder()
				.originalCount(securityOriginalCount).changedCount(securityChangedCount).build());

		return rbSituationAssetStatusRsp;
	}

	@Override
	public List<RbSituationActionRsp> getTeamAction(String roomId, Integer roleType) {
		List<CrTrainAction> crTrainActionList = crTrainActionService.lambdaQuery()
				.eq(CrTrainAction::getRoomId, roomId)
				.eq(CrTrainAction::getRoleType, roleType)
				.eq(CrTrainAction::getModule, CrTrainAction.Module.TEAM.getCode())
				.orderByDesc(CrTrainAction::getActionTime)
				.list();

		return toRbSituationActionRsp(crTrainActionList);
	}

	@Override
	public List<RbSituationActionRsp> getPlatformAction(String roomId, Integer roleType) {
		List<CrTrainAction> crTrainActionList = crTrainActionService.lambdaQuery()
				.eq(CrTrainAction::getRoomId, roomId)
				.eq(CrTrainAction::getModule, CrTrainAction.Module.PLATFORM.getCode())
				.in(CrTrainAction::getModuleEvent, roleType.equals(
						CrRole.RbRoleGroup.RED.getCode())
						? CrTrainAction.PlatformEvent.RED_PLATFORM_ACTION
						: CrTrainAction.PlatformEvent.BLUE_PLATFORM_ACTION)
				.orderByDesc(CrTrainAction::getActionTime)
				.list();

		return toRbSituationActionRsp(crTrainActionList);
	}

	private List<RbSituationActionRsp> toRbSituationActionRsp(List<CrTrainAction> crTrainActionList) {
		List<RbSituationActionRsp> result = Lists.newArrayList();
		crTrainActionList.forEach(crTrainAction -> {
			RbSituationActionRsp rbSituationActionRsp = new RbSituationActionRsp();
			BeanUtils.copyProperties(crTrainAction, rbSituationActionRsp);

			result.add(rbSituationActionRsp);
		});

		return result;
	}
}
