package com.cyberpeace.cr.teamtrain.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.FlowOpsReq;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.controller.req.ReleaseMissionReq;
import com.cyberpeace.cr.crbase.controller.req.WriteUpReq;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneNodeOpsReq;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneOpsReq;
import com.cyberpeace.cr.crbase.controller.rsp.StartSceneRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.role.RoleUsers;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.crbase.service.context.scene.Flow;
import com.cyberpeace.cr.crbase.service.context.scene.FlowRsp;
import com.cyberpeace.cr.teamtrain.controller.req.*;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamRoomRoleRes;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamRoomRsp;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamTimeManagementRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTimeManagement;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTemplateMapper;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamSceneService;
import com.cyberpeace.cr.teamtrain.service.TeamTimeManagementService;
import com.cyberpeace.cr.teamtrain.service.context.RoomRole;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainRoomContext;
import com.cyberpeace.cr.teamtrain.service.context.TimeLineData;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Api(tags = "团队训练实例")
@RestController
@Slf4j
@RequestMapping("/cr/team_train/room")
@RequiresAuthentication
public class TeamTrainRoomController {

	@Autowired
	private TeamRoomService teamRoomService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private TeamSceneService teamSceneService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteLogService remoteLogService;

	@Autowired
	private TrainRecordService trainRecordService;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private TeamTemplateMapper teamTemplateMapper;

	@Autowired
	private BusinessTemplateMapper businessTemplateMapper;

	@Autowired
	private TeamRoomMapper teamRoomMapper;

	@Autowired
	private TeamTimeManagementService teamTimeManagementService;

	@Autowired
	private CrRoomDeviceApplyService crRoomDeviceApplyService;

	@ApiOperation("启动场景")
	@PostMapping("/scene")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp startScene(@RequestBody SceneOpsReq sceneStartReq) {
		String roomId = sceneStartReq.getRoomId();
		String userId = UserInfoContext.getUserContext().getUserId();

		teamRoomService.checkTeacherPermission(roomId, userId);

		String sceneId = teamSceneService.startScene(roomId, userId);
		return new BaseRsp(new StartSceneRsp(sceneId));
	}

	@ApiOperation("停止场景")
	@DeleteMapping("/scene")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Boolean> stopScene(@RequestBody SceneOpsReq sceneStopReq) {
		String roomId = sceneStopReq.getRoomId();
		String userId = UserInfoContext.getUserContext().getUserId();

		teamRoomService.checkTeacherPermission(roomId, userId);

		TeamRoom teamRoom = teamRoomService.getById(roomId);
		// 2、如果场景已经停止，返回成功
		if (StringUtils.isBlank(teamRoom.getLocalSceneId())) {
			return new BaseRsp(true);
		}

		TeamTimeManagement timeManagement = teamTimeManagementService.getByRoomId(roomId);
		if (timeManagement == null) {
			throw new BusinessException(CrBusinessErrorCode.TRAIN_NOT_BEGIN);
		}
		if (timeManagement.isStopped()) {
			throw new BusinessException(CrBusinessErrorCode.TRAIN_IS_OVER);
		}

		Boolean stopResult = localSceneService.stopScene(teamRoom.getLocalSceneId());
		if (stopResult) {
			teamTimeManagementService.updateTimeManagement(timeManagement, teamRoom,
					TeamTimeManagement.TimeManagementStatus.STOP.getCode(),userId);
			crRoomDeviceApplyService.releaseApply(teamRoom.getResourceId());
		}

		// 推送ws
		crTaskService.pushStatusWs(roomId, TeamTimeManagement.TimeManagementStatus.STOP.getCode());

		// 记录远程操作日志
		remoteLogService.addCrLog(UserInfoContext.getUserContext(),  teamRoom.getModule(),
				OperationType.STOP, teamRoom.getName());

		return new BaseRsp(stopResult);
	}

	@ApiOperation("查看场景")
	@GetMapping("/scene")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<SceneViewContext> getScene(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id,
			@ApiParam(name = "role_id", value = "角色id", required = false) String role_id) {
		SceneViewContext context = teamSceneService.viewScene(resource_id, role_id);
		return new BaseRsp<>(context);
	}

	@ApiOperation("暂停场景")
	@PostMapping("/scene/pause")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Boolean> pauseScene(@RequestBody SceneOpsReq sceneOpsReq) {
		String roomId = sceneOpsReq.getRoomId();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、只有教员可以操作场景
		teamRoomService.checkTeacherPermission(roomId, userId);
		TeamRoom teamRoom = teamRoomService.getById(roomId);

		// 2、 场景开启的状态下发送mp暂停请求
		if (localSceneService.ensureSceneStarted(teamRoom.getLocalSceneId())
				&& !localSceneService.pauseScene(teamRoom.getLocalSceneId())) {
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_PAUSE_SCENE);
		}
		// 3. 更新时间管理
		TeamTimeManagement timeManagement = teamTimeManagementService.getByRoomId(roomId);
		teamTimeManagementService.
				updateTimeManagement(timeManagement, teamRoom, TeamTimeManagement.TimeManagementStatus.PAUSE.getCode(),
						UserInfoContext.getUserContext().getUserId());
		// 4.发送ws提醒前端刷新训练状态
		crTaskService.pushStatusWs(roomId, TeamTimeManagement.TimeManagementStatus.PAUSE.getCode());
		return new BaseRsp(true);

	}

	@ApiOperation("从暂停中恢复场景")
	@PostMapping("/scene/unpause")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp unpauseScene(@RequestBody SceneOpsReq sceneOpsReq) {
		String roomId = sceneOpsReq.getRoomId();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 1. 校验场景存在和用户权限
		teamRoomService.checkTeacherPermission(roomId, userId);
		TeamRoom teamRoom = teamRoomService.getById(roomId);

		// 2、 如果mp场景存在，发送mp解除暂停请求
		if (localSceneService.ensureSceneStarted(teamRoom.getLocalSceneId())
				&& !localSceneService.unpauseScene(teamRoom.getLocalSceneId())) {
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_UNPAUSE_SCENE);
		}
		// 3. 更新时间管理
		TeamTimeManagement timeManagement = teamTimeManagementService.getByRoomId(roomId);
		teamTimeManagementService.updateTimeManagement(timeManagement, teamRoom,
				TeamTimeManagement.TimeManagementStatus.RECOVERY.getCode(),
				UserInfoContext.getUserContext().getUserId());

		// 4.发送ws提醒前端刷新训练状态
		crTaskService.pushStatusWs(roomId, TeamTimeManagement.TimeManagementStatus.RECOVERY.getCode());
		return new BaseRsp();
	}

	@ApiOperation("查询流量")
	@GetMapping("flow")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<FlowRsp> getFlowList(
			@ApiParam(name = "room_id", value = "团队训练id", required = true) String room_id) {

		List<Flow> flows = teamSceneService.getFlowList(room_id);
		FlowRsp ret = new FlowRsp();
		ret.setData(flows);

		return new BaseRsp<>(ret);
	}

	@ApiOperation("启动流量")
	@PostMapping("/flow")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp startFlow(@RequestBody FlowOpsReq req) {
		String roomId = req.getRoomId();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、鉴权
		teamRoomService.checkTeacherPermission(roomId, userId);
		// 2、确认场景已开启
		LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
		if (!localSceneService.ensureSceneStarted(localScene)) {
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		// 3、开启流量
		Boolean result = localSceneService.startFlow(localScene, req.getNodeId(), req.getFlowId());
		if (!result) {
			throw new BusinessException(CrBusinessErrorCode.START_FLOW_FAILURE);
		}
		return new BaseRsp();
	}

	@ApiOperation("停止流量")
	@PostMapping("/flow/stop")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp stopFlow(@RequestBody FlowOpsReq req) {
		String roomId = req.getRoomId();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、鉴权
		teamRoomService.checkTeacherPermission(roomId, userId);
		// 2、确认场景已开启
		LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
		if (!localSceneService.ensureSceneStarted(localScene)) {
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		// 3、停止流量
		Boolean result = localSceneService.stopFlow(localScene, req.getNodeId(), req.getFlowId());
		if (!result) {
			throw new BusinessException(CrBusinessErrorCode.STOP_FLOW_FAILURE);
		}
		return new BaseRsp();
	}

	@ApiOperation("重启节点")
	@PostMapping("/scene/node/restart")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp restartNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {
		TeamRoom room = teamRoomService.getById(nodeOpsReq.getResourceId());
		if (room == null || StringUtils.isBlank(room.getLocalSceneId())) {
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.restartNode(room.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RESTART_NODE);
		}
		return new BaseRsp();
	}

	@ApiOperation("重建节点")
	@PostMapping("/scene/node/recreate")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp recreateNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {
		TeamRoom room = teamRoomService.getById(nodeOpsReq.getResourceId());
		if (room == null || StringUtils.isBlank(room.getLocalSceneId())) {
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.recreateNode(room.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RECREATE_NODE);
		}
		return new BaseRsp();
	}

	@ApiOperation("新增")
	@PostMapping()
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TeamRoomRsp> create(@RequestBody @Validated TeamTrainRoomReq teamTrainRoomReq) {
		// 校验
		teamTrainRoomReq.validate();
		TeamTrainRoomContext teamTrainRoomContext = BeanUtils.copy(teamTrainRoomReq,
				TeamTrainRoomContext.class);
		List<RoomRole> roles = BeanUtils.deepCopyList(teamTrainRoomReq.getRoleConfig(),
				RoomRole.class);
		teamTrainRoomContext.setRoleConfig(roles);
		teamTrainRoomContext.setPreSkill(
				JsonUtil.toJsonObjectArray(teamTrainRoomReq.getPreSkill(), String.class));
		teamTrainRoomContext.setTrainSkill(
				JsonUtil.toJsonObjectArray(teamTrainRoomReq.getTrainSkill(), String.class));
		TeamRoom teamRoom = teamRoomService.create(teamTrainRoomContext);

		TeamTemplate teamTemplate = teamTemplateMapper.selectById(teamTrainRoomReq.getTemplate());
		String teamTemplateName = businessTemplateMapper
				.selectById(teamTemplate.getBusinessTemplateId()).getName();
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), teamRoom.getModule(),
				OperationType.CREATE, teamTrainRoomReq.getName(),
				LogContent.CR_FORMAT.format(OperationType.CREATE, teamRoom.getLogMsg(), teamTrainRoomReq.getName()));

		return getTeamRoom(teamRoom.getResourceId());
	}

	@ApiOperation("修改")
	@PutMapping()
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TeamRoomRsp>
			update(@RequestBody @Validated TeamTrainRoomModifyReq teamTrainRoomModifyReq) {
		// 校验
		teamTrainRoomModifyReq.validate();
		TeamRoom teamRoom = teamRoomService.getById(teamTrainRoomModifyReq.getResourceId());

		if (teamRoom == null) {
			throw new ResourceNotFoundException(teamTrainRoomModifyReq.getResourceId());
		}
		TeamTrainRoomContext teamTrainRoomContext = BeanUtils.copy(teamTrainRoomModifyReq,
				TeamTrainRoomContext.class);
		List<RoomRole> roomRoleList = JsonUtil
				.toJsonObjectArray(teamTrainRoomModifyReq.getRoleConfig(), RoomRole.class);
		teamTrainRoomContext.setRoleConfig(roomRoleList);
		teamRoomService.updateTeamRoom(teamTrainRoomContext, teamRoom);

		teamRoom = teamRoomService.getById(teamTrainRoomModifyReq.getResourceId());
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), teamRoom.getModule(),
				OperationType.UPDATE, teamRoom.getName(),
				LogContent.CR_FORMAT.format(OperationType.UPDATE, teamRoom.getLogMsg(), teamRoom.getName()));

		return getTeamRoom(teamTrainRoomModifyReq.getResourceId());
	}

	@ApiOperation("查询")
	@GetMapping()
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TeamRoomRsp> getTeamRoom(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		String userId = UserInfoContext.getUserContext().getUserId();
		crRoleService.checkPermission(resource_id, UserInfoContext.getUserContext().getUserId());

		TeamRoomData teamRoomData = teamRoomService.getTeamRoomDataByRoomId(resource_id);

		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(teamRoomData.getResourceId());

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

		TeamRoomRsp teamRoomRsp = BeanUtils.copy(teamRoomData, TeamRoomRsp.class);
		teamRoomRsp.setTrainingRoleData(getSelfRole(crRoles, userId));

		teamRoomRsp.setRolesData(getTeamRoomRoleRes(crRoles));

		teamRoomRsp
				.setPreSkill(JsonUtil.toJsonObjectArray(teamRoomData.getPreSkill(), String.class));
		teamRoomRsp.setTrainSkill(
				JsonUtil.toJsonObjectArray(teamRoomData.getTrainSkill(), String.class));

		return new BaseRsp<TeamRoomRsp>(teamRoomRsp);
	}

	private List<TeamRoomRoleRes> getTeamRoomRoleRes(List<CrRole> crRoles) {
		List<TeamRoomRoleRes> teamRoomRoleResList = Lists.newArrayList();
		for (CrRole crRole : crRoles) {
			TeamRoomRoleRes teamRoomRoleRes = BeanUtils.copy(crRole, TeamRoomRoleRes.class);
			teamRoomRoleRes.setUsersData(crRoleService.getUserDatas(crRole.getUserIdList()));
			teamRoomRoleRes.setUsers(crRole.getUserIdList());

			teamRoomRoleRes.setTrainingTeam(crRole.getRoomId());
			teamRoomRoleResList.add(teamRoomRoleRes);
		}
		return teamRoomRoleResList;
	}

	@ApiOperation("删除")
	@DeleteMapping()
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<String> batchDelete(@RequestBody @Validated SceneOpsReq opsReq) {

		String roomId = opsReq.getRoomId();

		String teamTemplateName = teamRoomMapper.selectById(opsReq.getRoomId())
				.getTeamTemplateName();
		TeamRoom teamRoom = teamRoomService.getById(roomId);
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), teamRoom.getModule(),
				OperationType.DELETE, teamRoom.getName(),
				LogContent.CR_FORMAT.format(OperationType.DELETE, teamRoom.getLogMsg(), teamRoom.getName()));

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				teamSceneService.stopScene(roomId);
				crRoleService.clearRoomRoles(roomId);
				teamRoomService.removeById(roomId);
				// 记录房间删除时间
				trainRecordService.updateEndTimeByRoomId(roomId, new Date());
			}
		});

		// 不放入事务中，删除失败或成功均可
		if (StringUtils.isNotBlank(teamRoom.getCopyedTopoId())) {
			try {
				remoteTopoService.deleteById(teamRoom.getCopyedTopoId());
			} catch (Exception e) {
				log.error("Team train delete topo failed! team_id=[{}], topo_id=[{}], catch error:",
						teamRoom.getResourceId(), teamRoom.getCopyedTopoId(), e);
			}
		}

		return new BaseRsp<>();
	}

	@ApiOperation("列表查询")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<ListRsp<TeamRoomRsp>> list(@Validated TeamRoomListReq listReq) {
		ListQuery listQuery = BeanUtils.copy(listReq, ListQuery.class);
		listQuery.setPer_page(listReq.getPerPage());
		String userId = UserInfoContext.getUserContext().getUserId();
		// 默认时间排序
		listQuery.setSort("-cr_team_room.create_time");

		IPage<TeamRoomData> data = teamRoomService.list(listQuery);

		// 训练场景拓扑节点数量获取
		Map<String, TopoDigest> topologyNodesStats = remoteTopoService.batchGetTopoDigest(
				data.getRecords().stream().map(rbRoomData -> rbRoomData.getCopyedTopoId())
						.collect(Collectors.toList()));
		ListRsp<TeamRoomRsp> teamRoomRspListRsp = new ListRsp();

		List<TeamRoomRsp> teamRoomRspList = Lists.newArrayList();

		// attack engage relation
		List<String> businessTemplateIds = data.getRecords().stream()
				.map(TeamRoomData::getBusinessTemplateId).collect(Collectors.toList());
		List<String> attackRelatedIds = businessTemplateService
				.getAttackRelatedIds(businessTemplateIds);
		List<String> engageRelatedIds = businessTemplateService
				.getEngageRelatedIds(businessTemplateIds);

		for (TeamRoomData teamRoomData : data.getRecords()) {
			RoleQuery roleQuery = new RoleQuery();
			roleQuery.setRoomId(teamRoomData.getResourceId());

			List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

			List<TeamRoomRoleRes> rolesData = Lists.newArrayList();
			crRoles.forEach(crRole -> {
				TeamRoomRoleRes teamRoomRoleRes = BeanUtils.copy(crRole, TeamRoomRoleRes.class);
				teamRoomRoleRes
						.setUserIds(crRoleService.getUserNamesByRoleUsers(crRole.getUserIdList()));
			});

			TeamRoomRsp teamRoomRsp = BeanUtils.copy(teamRoomData, TeamRoomRsp.class);
			teamRoomRsp.setTrainingRoleData(getSelfRoleUsers(crRoles, userId));
			teamRoomRsp.setRolesData(rolesData);
			teamRoomRsp.setNodesCount(
					topologyNodesStats.get(teamRoomData.getCopyedTopoId()).getNodeCounts());
			teamRoomRsp.setResourceOccupiedNums(
					topologyNodesStats.get(teamRoomData.getCopyedTopoId()).getResourceOccupiedNums());
			teamRoomRsp.setPreSkill(
					JsonUtil.toJsonObjectArray(teamRoomData.getPreSkill(), String.class));
			teamRoomRsp.setTrainSkill(
					JsonUtil.toJsonObjectArray(teamRoomData.getTrainSkill(), String.class));
			// set attack engage relation
			teamRoomRsp.setIsAttackRelated(
					attackRelatedIds.contains(teamRoomRsp.getBusinessTemplateId()));
			teamRoomRsp.setIsEngageRelated(
					engageRelatedIds.contains(teamRoomRsp.getBusinessTemplateId()));

			teamRoomRsp.setUserAvatar(UserInfoContext.getUserContext().getAvatarUrl());
			teamRoomRspList.add(teamRoomRsp);
		}

		teamRoomRspListRsp.setList(teamRoomRspList);
		teamRoomRspListRsp.setTotal(data.getTotal());
		teamRoomRspListRsp.setLastPage(data.getPages());
		teamRoomRspListRsp.setCurrentPage(data.getCurrent());

		return new BaseRsp(teamRoomRspListRsp);
	}

	@ApiOperation("修改团队训练状态")
	@PostMapping("/start_end_team")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Map> changeTrainingStatus(@RequestBody @Validated ResourceIdReq resourceIdReq) {

		int status = teamRoomService.changeTrainingStatus(resourceIdReq.getResourceId());
		TeamRoom teamRoom = teamRoomService.getById(resourceIdReq.getResourceId());

		Map map = Maps.newHashMap();
		map.put("training_status", status);

		// 记录远程操作日志
		OperationType operationType = Objects.equals(status,
				TeamRoom.TrainingStatusCode.ENDED.getCode()) ? OperationType.STOP
						: OperationType.START;
		remoteLogService.addCrLog(UserInfoContext.getUserContext(), teamRoom.getModule(),
				operationType, teamRoomService.getById(resourceIdReq.getResourceId()).getName());
		return new BaseRsp<>(map);
	}

	@ApiOperation("查询团队训练状态")
	@GetMapping("/start_end_team")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Map> getTrainingStatus(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		Integer status = teamRoomService.getTrainingStatus(resource_id);

		Map map = Maps.newHashMap();
		map.put("training_status", status);

		return new BaseRsp<>(map);
	}

	/**
	 * 查询团队训练学员用户列表
	 * @return
	 */
	@ApiOperation("查询团队训练学员用户列表")
	@GetMapping("/get_user_list")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<UserRole>> getUserList(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		List<UserRole> data = teamRoomService.getUserList(resource_id);

		return new BaseRsp<>(data);
	}

	/**
	 * 查询团队训练时间线
	 * @return
	 */
	@ApiOperation("查询团队训练时间线")
	@GetMapping("/get_time_line")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TimeLineData> getTimeLine(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {

		TimeLineData data = teamRoomService.getTimeLine(resource_id);

		return new BaseRsp<>(data);
	}

	/**
	 * 查询团队训练训练手册
	 * @return
	 */
	@ApiOperation("查询团队训练训练手册")
	@GetMapping("/get_training_manual")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<String> getTrainingManual(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {

		String data = missionService.getTeamTrainingManual(resource_id);

		return new BaseRsp<>(data);
	}

	/**
	 * 修改开放题解
	 * @return
	 */
	@ApiOperation("修改开放题解")
	@PostMapping("/update_show_write_up")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<String> updateShowWriteUp(@RequestBody @Validated WriteUpReq writeUpReq) {

		teamRoomService.checkTeacherPermission(writeUpReq.getRoomId(),
				UserInfoContext.getUserContext().getUserId());
		teamRoomService.checkRoomOpening(writeUpReq.getRoomId());

		missionService.openOrCloseWriteup(writeUpReq.getMissionResourceId(),
				writeUpReq.getWriteUpShow());

		return new BaseRsp<>();
	}

	/**
	 * 任务下发
	 * @return
	 */
	@ApiOperation("任务下发")
	@PostMapping("/missions")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<String>
			releaseMissions(@RequestBody @Validated ReleaseMissionReq releaseMissionReq) {

		CrRole crRole = crRoleService.getById(releaseMissionReq.getRoleResourceId());

		teamRoomService.checkTeacherPermission(crRole.getRoomId(),
				UserInfoContext.getUserContext().getUserId());
		teamRoomService.checkRoomOpening(crRole.getRoomId());

		if (crRole == null) {
			log.error("role does not exist！roleId=[{}]", releaseMissionReq.getRoleResourceId());
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}

		missionService.release(releaseMissionReq.getMissionResourceId(),
				releaseMissionReq.getRoleResourceId(),
				UserInfoContext.getUserContext().getUserId());

		crTaskService.pushMissionWS(crRole.getRoomId(), MissionWsCode.TEAM_MISSION_CODE);

		return new BaseRsp<>();
	}

	@ApiOperation("批量任务下发")
	@PostMapping("/batchMissions")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Boolean>
	releaseBatchMissions(@RequestBody @Validated ReleaseMissionReq releaseMissionReq) {
		if (CollectionUtils.isEmpty(releaseMissionReq.getMissionResourceIds())) {
			return new BaseRsp<>(true);
		}
		CrRole crRole = crRoleService.getById(releaseMissionReq.getRoleResourceId());
		teamRoomService.checkTeacherPermission(crRole.getRoomId(),
				UserInfoContext.getUserContext().getUserId());
		teamRoomService.checkRoomOpening(crRole.getRoomId());
		Boolean res = missionService.batchRelease(releaseMissionReq.getMissionResourceIds(),
				releaseMissionReq.getRoleResourceId(),
				UserInfoContext.getUserContext().getUserId());
		if (Boolean.TRUE.equals(res)) {
			crTaskService.pushMissionWS(crRole.getRoomId(), MissionWsCode.TEAM_MISSION_CODE);
		}
		return new BaseRsp<>(res);
	}

	@ApiOperation("任务关闭")
	@PostMapping("/missions/close")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Boolean> closeMissions(@RequestBody @Validated ReleaseMissionReq releaseMissionReq) {
		CrRole crRole = crRoleService.getById(releaseMissionReq.getRoleResourceId());
		if (crRole == null) {
			log.error("role does not exist！roleId=[{}]", releaseMissionReq.getRoleResourceId());
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}

		teamRoomService.checkTeacherPermission(crRole.getRoomId(),
				UserInfoContext.getUserContext().getUserId());
		teamRoomService.checkRoomOpening(crRole.getRoomId());
		Boolean result = crTaskService.missionClose(releaseMissionReq.getMissionResourceId(), crRole);

		if (result) {
			crTaskService.pushMissionWS(crRole.getRoomId(), MissionWsCode.TEAM_MISSION_CODE);
		}

		return new BaseRsp<>(result);
	}

	// 获取单个实例中自己的角色信息，只包括自己角色的基本信息
	private BaseRole getSelfRole(List<CrRole> crRoles, String userId) {
		BaseRole baseRole = new BaseRole();
		crRoles.forEach(crRole -> {
			if (crRole.containsUser(userId)) {
				baseRole.setResourceId(crRole.getResourceId());
				baseRole.setName(crRole.getName());

				baseRole.setDescription(crRole.getDescription());
				baseRole.setRoleGroup(crRole.getRoleGroup());
			}
		});

		return baseRole;
	}

	// 先获取实例列表中自己的角色，再获取所有的学员角色
	private BaseRole getSelfRoleUsers(List<CrRole> crRoles, String userId) {
		BaseRole baseRole = new BaseRole();
		List<RoleUsers> roleUsersList = Lists.newArrayList();
		crRoles.forEach(crRole -> {
			if (crRole.containsUser(userId)) {
				baseRole.setResourceId(crRole.getResourceId());
				baseRole.setName(crRole.getName());

				baseRole.setDescription(crRole.getDescription());
				baseRole.setRoleGroup(crRole.getRoleGroup());
			}
			if (crRole.isTeamStudent()) {
				RoleUsers roleUsers = new RoleUsers();
				roleUsers.setRoleName(crRole.getName());
				roleUsers.setRoleGroup(crRole.getRoleGroup());
				String userNamesByRoleUsers = crRoleService
						.getUserNamesByRoleUsers(crRole.getUserIdList());
				roleUsers.setUsers(JsonUtil.toJsonObjectArray(userNamesByRoleUsers, String.class));
				roleUsersList.add(roleUsers);
			}
		});

		baseRole.setRoleDataList(roleUsersList);

		return baseRole;
	}

	/**
	 * 获取时间管理数据
	 * @return
	 */
	@ApiOperation("获取时间管理数据")
	@GetMapping("/get_time_management")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TeamTimeManagementRsp> getTimeManagement(@ApiParam(name = "resource_id", value = "训练id", required = true) String resourceId) {

		TeamTimeManagement teamTimeManagement = teamTimeManagementService.getByRoomId(resourceId);
		if (teamTimeManagement == null) {
			return new BaseRsp(null);
		}

		TeamTimeManagementRsp rbTimeManagementRsp = BeanUtils.copy(teamTimeManagement, TeamTimeManagementRsp.class);

		// 计算当前天文时间
		long time = teamTimeManagement.getAstronomicalTime().getTime() + System.currentTimeMillis()
				- teamTimeManagement.getCreateTime().getTime();
		Date currentAstronomicalTime = new Date(time);

		rbTimeManagementRsp.setCurrentAstronomicalTime(currentAstronomicalTime);
		rbTimeManagementRsp.setCreateUser(teamTimeManagement.getCreateUserId());
		rbTimeManagementRsp.setModifyTime(teamTimeManagement.getUpdateTime());
		rbTimeManagementRsp.setModifyUser(teamTimeManagement.getUpdateUserId());
		rbTimeManagementRsp.setRedVsBlue(teamTimeManagement.getRoomId());

		// 计算当前作战时间
		if (teamTimeManagement.isStopped() || teamTimeManagement.isPaused()) {
			rbTimeManagementRsp.setCurrentCombatTime(teamTimeManagement.getCombatTime());
		} else {
			time = teamTimeManagement.getCombatTime().getTime()
					+ (System.currentTimeMillis() - teamTimeManagement.getUpdateTime().getTime())
					* teamTimeManagement.getRate();
			Date currentCombatTime = new Date(time);
			rbTimeManagementRsp.setCurrentCombatTime(currentCombatTime);
		}

		return new BaseRsp(rbTimeManagementRsp);
	}

	/**
	 * 创建时间管理
	 * @return
	 */
	@ApiOperation("创建时间管理")
	@PostMapping("/create_time_management")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TeamTimeManagementRsp>
	addTimeManagement(@RequestBody @Validated TeamTimeManagementReq teamTimeManagementReq) {
		String userId = UserInfoContext.getUserContext().getUserId();
		//todo 角色验证
//		rbRoomService.checkDirectorPermission(teamTimeManagementReq.getRoomId(), userId);

		if (teamTimeManagementService.getByRoomId(teamTimeManagementReq.getRoomId()) != null) {
			log.error("teamTrain has begun！roleId=[{}]", teamTimeManagementReq.getRoomId());
			throw new BusinessException(CrBusinessErrorCode.TRAIN_HAS_BEGUN);
		}

		Integer rate = teamTimeManagementReq.getRate();
		if (rate >TeamTimeManagement.RateStatus.FIVE.getCode()
				|| rate < TeamTimeManagement.RateStatus.ONCE.getCode()) {
			log.error("Rate Value Error！rate=[{}]", rate);
			throw new BusinessException(CrBusinessErrorCode.RATE_VALUE_RANGE_ERROR);
		}
		// 创建时间管理
		TeamTimeManagement timeManagement = BeanUtils.copy(teamTimeManagementReq, TeamTimeManagement.class);
		TeamRoom teamRoom = teamRoomService.getById(teamTimeManagementReq.getRoomId());

		teamTimeManagementService.addTimeManagement(timeManagement, teamRoom,
				TeamTimeManagement.TimeManagementStatus.START.getCode(), userId);

		TeamTimeManagementRsp teamTimeManagementRsp = BeanUtils.copy(timeManagement,
				TeamTimeManagementRsp.class);
		teamTimeManagementRsp.setRedVsBlue(timeManagement.getRoomId());

		// 记录远程操作日志
		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
				OperationType.START, teamRoom.getName());

		return new BaseRsp(teamTimeManagementRsp);
	}
}
