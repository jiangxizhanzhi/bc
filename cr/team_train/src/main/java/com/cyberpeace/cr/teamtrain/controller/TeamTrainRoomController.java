package com.cyberpeace.cr.teamtrain.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.annoations.BehaviorRecord;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.HtmlTemplateEnum;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.context.MissionStageScheme;
import com.cyberpeace.common.context.role.CrRoleMissionSchemeStage;
import com.cyberpeace.common.context.role.CrRoleSimple;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.enums.BehaviorModuleEnum;
import com.cyberpeace.common.enums.BehaviorTypeEnum;
import com.cyberpeace.common.enums.TeamRoleGroup;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.room.DashboardInfo;
import com.cyberpeace.common.model.room.DashboardUser;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.HtmlToPdfUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.FlowOpsReq;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.controller.req.ReleaseMissionReq;
import com.cyberpeace.cr.crbase.controller.req.WriteUpReq;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneNodeOpsReq;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneOpsReq;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.mission.*;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.crbase.service.context.scene.Flow;
import com.cyberpeace.cr.crbase.service.context.scene.FlowRsp;
import com.cyberpeace.cr.crbase.service.context.scene.TeamSceneViewContext;
import com.cyberpeace.cr.crbase.service.context.score.ScoreTrendReq;
import com.cyberpeace.cr.crbase.service.context.score.StartSceneRsp;
import com.cyberpeace.cr.crbase.service.context.screen.ScreenRecordUserRole;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.teamtrain.controller.req.ResourceIdReq;
import com.cyberpeace.cr.teamtrain.controller.req.TeamRoomListReq;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainRoomModifyReq;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainRoomReq;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamRoomRoleRes;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamRoomRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTemplateMapper;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamSceneService;
import com.cyberpeace.cr.teamtrain.service.context.RoomRole;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainRoomContext;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.impl.MpEnvService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

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
	private TeamTemplateMapper teamTemplateMapper;

	@Autowired
	private CrScoreService crScoreService;

	@Autowired
	private ScreenService screenService;

	@Autowired
	private CrConstantsService crConstantsService;
	
	@Autowired
	private MpEnvService mpEnvService;

	private static final String MISSING_LOG = "resource_id_missing";

	@ApiOperation("启动场景")
	@PostMapping("/scene")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	@BehaviorRecord(module = BehaviorModuleEnum.TEAM_TRAIN, action = BehaviorTypeEnum.START_SCENE,
			target = "room_id")
	public BaseRsp<StartSceneRsp> startScene(@RequestBody SceneOpsReq sceneStartReq) {
		String roomId = sceneStartReq.getRoomId();
		String userId = UserInfoContext.getUserContext().getUserId();

		teamRoomService.checkTeacherPermission(roomId, userId);

		String sceneId = teamSceneService.startScene(roomId, userId);
		return new BaseRsp<>(new StartSceneRsp(sceneId));
	}

	@ApiOperation("停止场景")
	@DeleteMapping("/scene")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Boolean> stopScene(@RequestBody SceneOpsReq sceneStopReq) {
		String roomId = sceneStopReq.getRoomId();
		String userId = UserInfoContext.getUserContext().getUserId();

		teamRoomService.checkTeacherPermission(roomId, userId);

		Boolean result = teamSceneService.stopScene(roomId);
		return new BaseRsp<>(result);
	}

	@ApiOperation("查看场景")
	@GetMapping("/scene")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TeamSceneViewContext> getScene(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id,
			@ApiParam(name = "role_id", value = "角色id", required = false) String role_id) {
		TeamSceneViewContext context = teamSceneService.viewScene(resource_id, role_id);

		return new BaseRsp<>(context);
	}

	@ApiOperation("暂停场景")
	@PostMapping("/scene/pause")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Void> pauseScene(@RequestBody SceneOpsReq sceneOpsReq) {
		String roomId = sceneOpsReq.getRoomId();

		teamSceneService.pauseScene(roomId);
		return new BaseRsp<>();
	}

	@ApiOperation("从暂停中恢复场景")
	@PostMapping("/scene/unpause")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Void> unpauseScene(@RequestBody SceneOpsReq sceneOpsReq) {
		String roomId = sceneOpsReq.getRoomId();

		teamSceneService.unpauseScene(roomId);
		return new BaseRsp<>();
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
	public BaseRsp<Void> startFlow(@RequestBody FlowOpsReq req) {
		String roomId = req.getRoomId();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、鉴权
		teamRoomService.checkTeacherPermission(roomId, userId);
		// 2、确认场景已开启
		LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
		if (!localSceneService.ensureSceneStarted(localScene)) {
			log.error("scene not started,req =[{}]", JsonUtil.toJsonString(req));
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		// 3、开启流量
		Boolean result = localSceneService.startFlow(localScene, req.getNodeId(), req.getFlowId());
		if (!result) {
			log.error("start flow failure,req =[{}]", JsonUtil.toJsonString(req));
			throw new BusinessException(CrBusinessErrorCode.START_FLOW_FAILURE);
		}
		return new BaseRsp<>();
	}

	@ApiOperation("停止流量")
	@PostMapping("/flow/stop")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Void> stopFlow(@RequestBody FlowOpsReq req) {
		String roomId = req.getRoomId();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、鉴权
		teamRoomService.checkTeacherPermission(roomId, userId);
		// 2、确认场景已开启
		LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
		if (!localSceneService.ensureSceneStarted(localScene)) {
			log.error("scene not started,req =[{}]", JsonUtil.toJsonString(req));
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		// 3、停止流量
		Boolean result = localSceneService.stopFlow(localScene, req.getNodeId(), req.getFlowId());
		if (!result) {
			log.error("stop flow failure,req =[{}]", JsonUtil.toJsonString(req));
			throw new BusinessException(CrBusinessErrorCode.STOP_FLOW_FAILURE);
		}
		return new BaseRsp<>();
	}

	@ApiOperation("重启节点")
	@PostMapping("/scene/node/restart")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Void> restartNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {
		TeamRoom room = teamRoomService.getById(nodeOpsReq.getResourceId());
		if (room == null || StringUtils.isBlank(room.getLocalSceneId())) {
			log.error("scene not started,nodeOpsReq =[{}]", JsonUtil.toJsonString(nodeOpsReq));
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.restartNode(room.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			log.error("failed to restart node,nodeOpsReq =[{}]", JsonUtil.toJsonString(nodeOpsReq));
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RESTART_NODE);
		}
		return new BaseRsp<>();
	}

	@ApiOperation("重建节点")
	@PostMapping("/scene/node/recreate")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Void> recreateNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {
		TeamRoom room = teamRoomService.getById(nodeOpsReq.getResourceId());
		if (room == null || StringUtils.isBlank(room.getLocalSceneId())) {
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.recreateNode(room.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			log.error("failed to recreate node,nodeOpsReq =[{}]",
					JsonUtil.toJsonString(nodeOpsReq));
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RECREATE_NODE);
		}
		return new BaseRsp<>();
	}

	@ApiOperation("新增")
	@PostMapping()
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	@BehaviorRecord(module = BehaviorModuleEnum.TEAM_TRAIN, action = BehaviorTypeEnum.CREATE,
			target = "template", name = "templateName")
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


		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.TEAM_TRAIN,
				OperationType.CREATE, teamTrainRoomReq.getName(),
				LogContent.CR_FORMAT.format(OperationType.CREATE,
						LogContent.TEAM_TRAIN_ROOM.getMsg(), teamTrainRoomReq.getName()));

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
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.TEAM_TRAIN,
				OperationType.UPDATE, teamRoom.getName(),
				LogContent.CR_FORMAT.format(OperationType.UPDATE,
						LogContent.TEAM_TRAIN_ROOM.getMsg(), teamRoom.getName()));

		return getTeamRoom(teamTrainRoomModifyReq.getResourceId());
	}

	@ApiOperation("查询")
	@GetMapping()
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	@BehaviorRecord(module = BehaviorModuleEnum.TEAM_TRAIN, action = BehaviorTypeEnum.ACCESS)
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


		TeamRoom teamRoom = teamRoomService.getById(roomId);
		remoteLogService.addOjLog(UserInfoContext.getUserContext(), OperationModule.TEAM_TRAIN,
				OperationType.DELETE, teamRoom.getName(),
				LogContent.CR_FORMAT.format(OperationType.DELETE,
						LogContent.TEAM_TRAIN_ROOM.getMsg(), teamRoom.getName()));

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
		ListRsp<TeamRoomRsp> rsp = teamRoomService.listRoom(listQuery);
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("修改团队训练状态")
	@PostMapping("/start_end_team")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Map<String,Integer>> changeTrainingStatus(@RequestBody @Validated ResourceIdReq resourceIdReq) {

		int status = teamRoomService.changeTrainingStatus(resourceIdReq.getResourceId());

		Map<String,Integer> map = Maps.newHashMap();
		map.put("training_status", status);

		// 记录远程操作日志
		OperationType operationType = Objects.equals(status,
				TeamRoom.TrainingStatusCode.ENDED.getCode()) ? OperationType.STOP
						: OperationType.START;
		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.TEAM_TRAIN,
				operationType, teamRoomService.getById(resourceIdReq.getResourceId()).getName());
		return new BaseRsp<>(map);
	}

	@ApiOperation("查询团队训练状态")
	@GetMapping("/start_end_team")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Map<String,Integer>> getTrainingStatus(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		Integer status = teamRoomService.getTrainingStatus(resource_id);

		Map<String,Integer> map = Maps.newHashMap();
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
	 * 查询团队训练时间轴
	 * 教员
	 * @return
	 */
	@ApiOperation("查询团队训练时间轴")
	@GetMapping("/get_time_line")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TimeLineData> getTimeLine(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
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
		// 发送ws给接入靶机的学员，任务矩阵显示红点
		crTaskService.pushMissionWsToUsers(crRole.getRoomId(), crRole.getUserIdList(),
				MissionWsCode.MISSION_RELEASE);

		return new BaseRsp<>();
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
			crTaskService.pushMissionWsToUsers(crRole.getRoomId(), crRole.getUserIdList(),
					MissionWsCode.MISSION_RELEASE);
		}
		return new BaseRsp<>(res);
	}

	@ApiOperation("团队对抗-任务导调-任务进度")
	@GetMapping("/mission/progress")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TeamTaskReleaseProgress> getMissionStatistics(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {

		if (StringUtils.isBlank(resource_id)) {
			log.error(MISSING_LOG);
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		// 获取该房间的角色信息
		List<CrRole> studentRoles = teamRoomService.getStudentsRoles(resource_id);
		// parse mission and users
		List<CrRoleSimple> roles = studentRoles.stream().map(CrRoleSimple::new)
				.collect(Collectors.toList());
		// 获取该房间已下发的任务
		List<CrTask> crTaskList = crTaskService.getByRoomId(resource_id);

		TeamTaskReleaseProgress progress = new TeamTaskReleaseProgress();
		// count
		Integer released = crTaskList.size();
		Integer total = roles.stream().mapToInt(CrRoleSimple::getTaskCount).sum();
		Integer submitted = crTaskList.stream().mapToInt(task -> task.isSubmitted() ? 1 : 0).sum();
		long waiting = crTaskList.stream()
				.filter(task -> task.getStatus().equals(CrTask.TaskStatus.WAIT.getCode())).count();

		progress.setTotal(total);
		progress.setReleased(released);
		progress.setUnreleased(total - released);
		progress.setSubmitted(submitted);
		progress.setUnsubmitted(released - submitted);
		progress.setWaiting((int) waiting);

		// 循环各个角色和阶段
		for (CrRoleSimple role : roles) {
			RoleTaskReleaseProgress roleProgress = new RoleTaskReleaseProgress();
			roleProgress.setRoleId(role.getResourceId());
			roleProgress.setRoleName(role.getName());
			for (CrRoleMissionSchemeStage stage : role.getMissionScheme()) {
				StageTaskReleaseProgress stageProgress = new StageTaskReleaseProgress();
				stageProgress.setStageName(stage.getStage());
				// 计算该阶段内任务下发数和任务总数*人数
				Integer releasedTaskCount = crTaskList.stream()
						.mapToInt(task -> stage.getMissions().contains(task.getMissionId()) ? 1 : 0)
						.sum();
				Integer taskCount = stage.getMissions().size() * role.getUserIds().size();
				stageProgress.setTotal(taskCount);
				stageProgress.setReleased(releasedTaskCount);
				// 阶段加入角色之中
				roleProgress.getStage().add(stageProgress);
				roleProgress.addTotal(taskCount);
				roleProgress.addReleased(releasedTaskCount);
			}
			progress.getRoles().getRole().add(roleProgress);
			progress.getRoles().addTotal(roleProgress.getTotal());
			progress.getRoles().addReleased(roleProgress.getReleased());
		}

		return new BaseRsp<>(progress);
	}

	@ApiOperation("团队对抗-任务导调-任务列表")
	@GetMapping("/mission/list")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<TrainTaskReleaseInfoRsp>> getMissionList(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {

		if (StringUtils.isBlank(resource_id)) {
			log.error(MISSING_LOG);
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}

		List<TrainTaskReleaseInfoRsp> res = crRoleService.getTrainTaskReleaseInfoList(resource_id,
				TeamRoleGroup.STUDENT.getCode());
		return new BaseRsp<>(res);
	}

	@ApiOperation("团队对抗-训练手册")
	@GetMapping("/manual")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<TrainTaskReleaseInfoRsp>> getManual(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
		if (StringUtils.isBlank(resource_id)) {
			log.error(MISSING_LOG);
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		String userId = UserInfoContext.getUserContext().getUserId();
		CrRole crRole = teamRoomService.checkRoomPermission(resource_id, userId);
		if (crRole.isTeamStudent()) {
			log.error("role team_student is forbidden");
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}

		List<TrainTaskReleaseInfoRsp> res = crRoleService.getTrainManual(resource_id,
				TeamRoleGroup.STUDENT.getCode());
		return new BaseRsp<>(mergeStageByCreateTime(res));
	}

	@ApiOperation("获取聊天框用户列表")
	@GetMapping("/dashboard_info")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<DashboardInfo> getDashboardInfo(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
		String userId = UserInfoContext.getUserContext().getUserId();
		CrRole crRole = teamRoomService.checkRoomPermission(resource_id, userId);

		DashboardInfo data = teamRoomService.getDashboardInfo(crRole, resource_id, userId);

		return new BaseRsp<>(data);
	}

	@ApiOperation("团队对抗-任务导调-训练报告")
	@GetMapping("/mission/getTrainReports")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TrainReportContext> getTrainReports(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "scoreCount", value = "节点数", required = true) Integer scoreCount) {
		TrainRecord trainRecord = trainRecordService.getByRoomId(resource_id);

		TeamRoom teamRoom = teamRoomService.getById(resource_id);
		if (scoreCount == null || scoreCount == 0) {
			scoreCount = 10;
		}
		ScoreTrendReq scoreTrendReq = new ScoreTrendReq(trainRecord, teamRoom.getStartTime(),
				teamRoom.getEndTime(), scoreCount, teamRoom.getReplaySceneId());
		// 得分趋势
		TrainReportContext trainReport = crScoreService.getTrainReport(resource_id,
				Lists.newArrayList(TeamRoleGroup.STUDENT.getCode()), scoreTrendReq);
		// 训练未开始，训练时间显示的是创建训练填写的训练开始时间，训练时长是训练模板设置的推荐时长
		// 训练已开始，训练时间显示的启动训练时间，训练时长是当前时间-启动训练时间
		// 训练已结束，训练时间显示的启动训练时间，训练时长是结束训练-启动训练时间
		teamRoom.setStartTime(scoreTrendReq.getStartTime());
		teamRoom.setEndTime(scoreTrendReq.getEndTime());
		initTeamReport(teamRoom, trainReport);
		if (trainRecord == null) {
			String timeCost = trainReport.getRoomBaseInfo().getDurationTime() == null
					? CommonConstant.SHORT_TRANSVERSE_LINE
					: trainReport.getRoomBaseInfo().getDurationTime()
							+ TrainingReport.TIME.HOUR.getMsg();
			trainReport.getRoomBaseInfo().setTimeCost(timeCost);
		}
		return new BaseRsp<TrainReportContext>(trainReport);
	}

	@ApiOperation("团队对抗-任务导调-训练报告导出")
	@GetMapping("/mission/TrainReportsPdf")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public void TrainReportsPdf(HttpServletResponse httpServletResponse,
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "scoreCount", value = "节点数", required = true) Integer scoreCount) {
		TrainReportContext originData = this.getTrainReports(resource_id, scoreCount).getData();
		// 导出时，用户头像加上 mp 地址
		for (UserRole userRole : originData.getUserRoles()) {
			userRole.setLogo(mpEnvService.getMpHost() + userRole.getLogo());
		}
		TrainReportExportContext data = BeanUtils.deepCopy(originData,
				TrainReportExportContext.class);
		data.initExport();
		Path destHtmlPathObj = Paths.get(AttachmentPath.TEAM_HTML_TEMPLATE.getDesc(),
				String.format("%s-%s", resource_id, UserInfoContext.getUserContext().getUserId()),
				String.format("%s.html", resource_id));
		FileUtil.copyDirectory(HtmlTemplateEnum.TEAM.getTemplatePath() + "/img",
				AttachmentPath.TEAM_HTML_TEMPLATE.getDesc(), true);
		FileUtil.copyDirectory(HtmlTemplateEnum.TEAM.getTemplatePath() + "/js",
				AttachmentPath.TEAM_HTML_TEMPLATE.getDesc(), true);
		// 临时存放目录
		File directory = destHtmlPathObj.getParent().toFile();
		if (!directory.exists()) {
			boolean isCreated = directory.mkdirs();
			if (!isCreated) {
				log.error("failed_to_make_dir: Path = [{}]", directory.getPath());
				throw new BusinessException(ErrorCode.FAILED_TO_MAKE_DIR, directory.getPath());
			}
		}
		String destHtmlPath = destHtmlPathObj.toString();
		try {
			// 生成html
			HtmlToPdfUtil.getHtmlTemplateInstance(data, HtmlTemplateEnum.TEAM, destHtmlPath);

			// 在同目录下生成pdf
			String destPdfPath = destHtmlPath.substring(0, destHtmlPath.lastIndexOf("html"))
					+ "pdf";
			HtmlToPdfUtil.convert(destHtmlPath, destPdfPath);

			FileUtil.setResponse(new File(destPdfPath), false, httpServletResponse);

		} catch (Exception e) {
			log.error("failed to export pdf: {}", destHtmlPath);
			throw e;
		} finally {
			if (!FileUtil.deleteDir(directory)) {
				log.error("failed to delete dir: {}", directory);
			}
		}

	}

	public void initTeamReport(TeamRoom room, TrainReportContext trainReport) {
		// 基础信息
		RoomBaseInfo roomBaseInfo = BeanUtils.copy(room, RoomBaseInfo.class);
		TeamTemplate template = teamTemplateMapper.selectById(room.getTeamTemplateId());
		if (template != null) {
			roomBaseInfo.setDurationTime(template.getDurationTime());
		}
		roomBaseInfo.initExportString();
		roomBaseInfo.setDifficultyString(crConstantsService.geCnNameByTypeAndCode(
				CrConstantsConfig.ConstantsType.DIFFICULTY.getCode(),
				roomBaseInfo.getDifficulty()));
		trainReport.setRoomBaseInfo(roomBaseInfo);
		List<UserRole> teachers = crRoleService.getUserRolesByRoomIdAndRoleGroups(
				room.getResourceId(), Lists.newArrayList(TeamRoleGroup.TEACHER.getCode()));
		trainReport.setRoomBaseInfo(roomBaseInfo);
		trainReport.setTeacherName(UserRole.getName(UserRole::getName, teachers));
		trainReport.initRbGetScoreRsps(false);
	}

	@ApiOperation("获取聊天框群组成员列表")
	@GetMapping("/members_info")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<JSONObject> getMembersInfo(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "id", value = "房间id", required = true) String id) {

		List<DashboardUser> data = teamRoomService.getMembersInfo(id, resource_id);
		JSONObject json = new JSONObject();
		json.put("list", data);

		return new BaseRsp<>(json);
	}

	@ApiOperation("任务删除")
	@PostMapping("/mission_delete")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<String>
			addMissionInfo(@RequestBody @Validated MissionDeleteReq rbMissionDeleteReq) {
		teamRoomService.checkRoomOpening(rbMissionDeleteReq.getRoomId());
		if (CollectionUtils.isEmpty(rbMissionDeleteReq.getMissionConfig())) {
			return new BaseRsp<>();
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				List<MissionConfig> missionConfigList = rbMissionDeleteReq.getMissionConfig();
				for (MissionConfig missionConfig : missionConfigList) {
					List<String> deleteMissions = missionConfig.getMissions();
					if (CollectionUtils.isEmpty(deleteMissions)) {
						return;
					}
					CrRole crRole = crRoleService.getById(missionConfig.getRoleResourceId());
					if (!crRole.isTeamStudent()) {
						log.error("student role  does not exist！roleId=[{}] ",
								crRole.getResourceId());
						throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);

					}

					if (crTaskService.getByMissions(rbMissionDeleteReq.getRoomId(), deleteMissions)
							.size() > 0) {
						log.error("mission is issued！missionIds=[{}] ",
								JSON.toJSONString(deleteMissions));
						throw new BusinessException(CrBusinessErrorCode.MISSION_ISSUED);
					}

					List<MissionStageScheme> missionStageSchemeList = crRole
							.getMissionStageSchemes();

					for (MissionStageScheme missionStageScheme : missionStageSchemeList) {

						List<String> missions = missionStageScheme.getMissions();
						for (int i = missions.size() - 1; i >= 0; i--) {
							String mission = missions.get(i);

							if (deleteMissions.contains(mission)) {
								missions.remove(mission);
							}
						}
						missionStageScheme.setMissions(missions);

					}

					crRole.setMissionStageSchemes(missionStageSchemeList);
					crRoleService.updateById(crRole);
					missionService.removeByIds(deleteMissions);

				}

			}
		});

		return new BaseRsp<>();
	}

	@ApiOperation("团队对抗-复盘设备")
	@GetMapping("/task/replayUserTimeInfo")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<ScreenRecordUserRole>> replyUserTimeInfo(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "node_id", value = "设备id", required = true) String node_id) {

		if (StringUtils.isBlank(resource_id)) {
			log.error(MISSING_LOG);
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		TeamRoom teamRoom = teamRoomService.getById(resource_id);
		if (!teamRoom.isEnded()) {
			log.error("team training [{}] not ended", resource_id);
			throw new BusinessException(CrBusinessErrorCode.PLZ_REPLAY_AFTER_TRAINING_ENDS);
		}
		List<ScreenRecordUserRole> resList = screenService.getReplayUserTimeInfo(resource_id,
				teamRoom.getReplaySceneId(), node_id,
				Lists.newArrayList(TeamRoleGroup.STUDENT.getCode()));

		// 得到场景真实结束时间
		Date actualEndTime = trainRecordService.getActualEndTime(resource_id);
		// 过滤出结束时间之前的数据
		resList = screenService.filterBeforeActualEndTime(resList, actualEndTime);
		return new BaseRsp<>(resList);
	}

	private List<TrainTaskReleaseInfoRsp> mergeStageByCreateTime(List<TrainTaskReleaseInfoRsp> manuals) {
		List<TrainTaskReleaseInfoRsp> res = new ArrayList<>(1);

		List<MissionReleaseStageInfo> allStages = new ArrayList<>();
		manuals.stream().filter(item -> CollectionUtils.isNotEmpty(item.getMissionStages()))
				.forEach(item -> allStages.addAll(item.getMissionStages()));
		if (CollectionUtils.isEmpty(allStages)) {
			return res;
		}
		Comparator<MissionReleaseStageInfo> comparator = (o1, o2) -> {
			if(o1 == null || CollectionUtils.isEmpty(o1.getMissionInfos())) {
				return 1;
			}
			if(o2 == null || CollectionUtils.isEmpty(o2.getMissionInfos())) {
				return -1;
			}
			Date o1Time = o1.getMissionInfos().get(0).getCreateTime();
			Date o2Time = o2.getMissionInfos().get(0).getCreateTime();
			if (o1Time.before(o2Time)) {
				return 1;
			} else if (o1Time.equals(o2Time)) {
				return 0;
			} else {
				return -1;
			}
		};

		List<MissionReleaseStageInfo> sortedStages = allStages.stream()
				.sorted(comparator)
				.collect(Collectors.toList());

		TrainTaskReleaseInfoRsp taskReleaseInfoRsp = new TrainTaskReleaseInfoRsp();
		taskReleaseInfoRsp.setMissionStages(sortedStages);
		res.add(taskReleaseInfoRsp);
		return res;
	}
}
