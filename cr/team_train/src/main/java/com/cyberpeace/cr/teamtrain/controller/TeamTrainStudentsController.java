package com.cyberpeace.cr.teamtrain.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.controller.req.SubmitMissionsReq;
import com.cyberpeace.cr.crbase.controller.rsp.MissionRsp;
import com.cyberpeace.cr.crbase.controller.rsp.SchemeListMissionRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrBadge;
import com.cyberpeace.cr.crbase.dao.entity.CrScore;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.MissionContext;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.TaskMatrix;
import com.cyberpeace.cr.crbase.service.context.device.AssetPageReq;
import com.cyberpeace.cr.crbase.service.context.mark.MarkAddReq;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineData;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.role.RoleUsers;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.teamtrain.controller.req.TeamCommonDeviceReq;
import com.cyberpeace.cr.teamtrain.controller.req.TeamRoomListReq;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainDeviceReq;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamRoomRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamCommonDevice;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainDevice;
import com.cyberpeace.cr.teamtrain.service.TeamCommonDeviceService;
import com.cyberpeace.cr.teamtrain.service.TeamDeviceService;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTeacherService;
import com.cyberpeace.cr.teamtrain.service.context.TeamCommonDeviceContext;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainDeviceContext;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@Api(tags = "团队训练学员")
@RestController
@RequestMapping("/cr/training_team/training_team_students")
public class TeamTrainStudentsController {

	@Autowired
	private TeamRoomService teamRoomService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private TrainRecordService trainRecordService;

	@Autowired
	private CrCommonService crCommonService;

	@Autowired
	private TeamDeviceService teamDeviceService;

	@Autowired
	private TeamCommonDeviceService teamCommonDeviceService;

	@Autowired
	private CrSceneService crSceneService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private CrBadgeService crBadgeService;

	@Autowired
	private TeamTeacherService teamTeacherService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private CrScoreService crScoreService;

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
		teamRoomRsp
				.setPreSkill(JsonUtil.toJsonObjectArray(teamRoomData.getPreSkill(), String.class));
		teamRoomRsp.setTrainSkill(
				JsonUtil.toJsonObjectArray(teamRoomData.getTrainSkill(), String.class));

		String copyedTopoId = teamRoomData.getCopyedTopoId();
		List<String> copyedTopoIds = new ArrayList<>();
		copyedTopoIds.add(copyedTopoId);
		// 训练场景拓扑节点数量获取
		Map<String, TopoDigest> topologyNodesStats = remoteTopoService
				.batchGetTopoDigest(copyedTopoIds);
		teamRoomRsp.setNodesCount(
				topologyNodesStats.get(teamRoomData.getCopyedTopoId()).getNodeCounts());
		return new BaseRsp<TeamRoomRsp>(teamRoomRsp);
	}

	/**
	 * 5.4弃用
	 *
	 * @param listReq 列表请求体
	 * @return {@code BaseRsp<ListRsp<TeamRoomRsp>>}
	 */
	@ApiOperation("列表查询")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<ListRsp<TeamRoomRsp>> list(@Validated TeamRoomListReq listReq) {
		ListQuery listQuery = BeanUtils.copy(listReq, ListQuery.class);
		listQuery.setPer_page(listReq.getPerPage());
		ListRsp<TeamRoomRsp> rsp = teamRoomService.listRoom(listQuery);
		return new BaseRsp<>(rsp);
	}

	/**
	 * 获取任务列表
	 * @return
	 */
	@ApiOperation("获取任务列表")
	@GetMapping("/get_mission_scheme")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<MissionSchemeObject<SchemeListMissionRsp>>> getMissionScheme(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		String userId = UserInfoContext.getUserContext().getUserId();

		// 获取角色中的任务内容
		List<MissionSchemeObject<MissionContext>> missionSchemeObjectList = crRoleService
				.getCrRoleMissionByRoomIdAndUserId(resource_id, userId);

		List<MissionSchemeObject<SchemeListMissionRsp>> missionSchemeList = missionSchemeObjectList
				.stream().map(missionSchemeObject -> {
					MissionSchemeObject<SchemeListMissionRsp> schemeListMissionRsp = new MissionSchemeObject<>();
					schemeListMissionRsp.setStage(missionSchemeObject.getStage());
					schemeListMissionRsp.setDynamic(missionSchemeObject.getDynamic());
					List<SchemeListMissionRsp> schemeListMissionRsps = BeanUtils.deepCopyList(
							missionSchemeObject.getMissions(), SchemeListMissionRsp.class);
					schemeListMissionRsp.setMissions(schemeListMissionRsps);

					return schemeListMissionRsp;
				}).collect(Collectors.toList());

		return new BaseRsp(missionSchemeList);
	}

	@ApiOperation("提交任务答案")
	@PostMapping("/submit_missions")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<String>
			changeTrainingStatus(@RequestBody @Validated SubmitMissionsReq submitMissionsReq) {
		CrCommonValidator.checkStringExist(submitMissionsReq.getAnswer(),
				CrBusinessErrorCode.ANSWER_NOT_NULLABLE);

		teamRoomService.checkRoomOpening(submitMissionsReq.getResourceId());
		String userId = UserInfoContext.getUserContext().getUserId();
		teamRoomService.checkStudentPermission(submitMissionsReq.getResourceId(), userId);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				crTaskService.submitMissions(submitMissionsReq.getResourceId(),
						submitMissionsReq.getMissionHash(), submitMissionsReq.getAnswer(),
						submitMissionsReq.getRemark());
				teamTeacherService.notifySubmission(submitMissionsReq.getMissionHash());
			}
		});
		crTaskService.pushMissionWS(submitMissionsReq.getResourceId(),
				MissionWsCode.TEAM_MISSION_CODE);

		return new BaseRsp<>();
	}

	@ApiOperation("获取任务详情")
	@GetMapping("/get_mission_detail")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<MissionRsp> getMissionDetail(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id,
			@ApiParam(name = "mission_id", value = "任务id", required = true) String mission_id) {
		String userId = UserInfoContext.getUserContext().getUserId();

		TaskContext taskContext = crTaskService.getByMissionAndUser(mission_id, userId);

		MissionRsp missionRsp = new MissionRsp();
		missionRsp.setResourceId(taskContext.getMission().getRoomId());
		missionRsp.setTitle(taskContext.getMission().getTitle());
		missionRsp.setOption(taskContext.getMission().getOption());
		missionRsp.setType(taskContext.getMission().getType());
		missionRsp.setCategory(
				missionService.getCategoryById(taskContext.getMission().getCategoryId()).getName());
		missionRsp.setShowWriteup(taskContext.getMission().getShowWriteup());
		missionRsp.setWriteUp(taskContext.getMission().getWriteUp());
		missionRsp.setDescription(taskContext.getMission().getDescription());
		missionRsp.setRemark(taskContext.getRemark());
		missionRsp.setUserAnswer(taskContext.getUserAnswer());
		if (!taskContext.getMission().getShowWriteup()) {
			missionRsp.setWriteUp("");
		}

		return new BaseRsp(missionRsp);
	}

	/**
	 * 查询团队训练学员时间轴
	 * @return
	 */
	@ApiOperation("查询团队训练时间线")
	@GetMapping("/get_time_line")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TimeLineData> getTimeLine(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
		TimeLineData data = teamRoomService.getStudentsTimeLine(resource_id);

		return new BaseRsp(data);
	}

	@ApiOperation("新增标记")
	@PostMapping("/mark")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp createMark(@RequestBody @Valid MarkAddReq req) {
		crCommonService.addMark(req, BusinessType.TRAINING_TEAM.getCode());
		return new BaseRsp();
	}

	@ApiOperation("团队对抗-学员任务矩阵")
	@GetMapping("/task/matrix")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TaskMatrix> getTaskMatrix(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "user_id", value = "用户id", required = true) String user_id) {

		if (StringUtils.isBlank(resource_id)) {
			log.error("resource_id_missing");
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		if (StringUtils.isBlank(user_id)) {
			user_id = UserInfoContext.getUserContext().getUserId();
			// 查看自己的任务矩阵之后去掉任务矩阵红点标记
			crBadgeService.removeBadge(resource_id, CrBadge.BadgeType.MISSION_RELEASE.getCode(),
					user_id);
		}
		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(resource_id, user_id);
		TaskMatrix teamTaskMatrix = crTaskService.getTaskMatrixExcludeTimeCost(resource_id,
				user_id);
		CrScore crScore = crScoreService.getByRoomIdAndUser(resource_id, user_id);
		teamTaskMatrix.setFinalScore(crScore==null?0:crScore.getFinalScore());
		teamTaskMatrix.setTime(trainRecordService.getTimeCostLong(resource_id));
		teamTaskMatrix.initTaskListStage(crRole, TaskMatrix.SORT.MATRIX.getDesc());
		return new BaseRsp<>(teamTaskMatrix);
	}

	@ApiOperation("团队对抗-复盘用户任务列表")
	@GetMapping("/task/replayUserTaskInfo")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TaskMatrix> replayUserTaskInfo(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "user_id", value = "用户id", required = true) String user_id) {

		if (StringUtils.isBlank(resource_id)) {
			log.error("resource_id_missing");
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		if (StringUtils.isBlank(user_id)) {
			user_id = UserInfoContext.getUserContext().getUserId();
		}
		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(resource_id, user_id);
		TaskMatrix teamTaskMatrix = crTaskService.getTaskMatrixExcludeTimeCost(resource_id, user_id);
		CrScore crScore = crScoreService.getByRoomIdAndUser(resource_id, user_id);
		teamTaskMatrix.setFinalScore(crScore==null?0:crScore.getFinalScore());
		teamTaskMatrix.initTaskListStage(crRole, TaskMatrix.SORT.REPLAY.getDesc());
		return new BaseRsp<>(teamTaskMatrix);
	}

	@ApiOperation("团队对抗-学员任务矩阵-是否显示红点")
	@GetMapping("/task/matrix/badge")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Boolean> ifShowMissionMatrixBadge(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
		Boolean res = crBadgeService.ifExist(resource_id,
				CrBadge.BadgeType.MISSION_RELEASE.getCode(),
				UserInfoContext.getUserContext().getUserId());
		return new BaseRsp<>(res);
	}

	@ApiOperation("任务矩阵红点点击取消")
	@GetMapping("/matrix/badge/click")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp clickEvaluationBadge(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
		crBadgeService.removeBadge(resource_id, CrBadge.BadgeType.MISSION_RELEASE.getCode(),
				UserInfoContext.getUserContext().getUserId());
		return new BaseRsp<>();
	}

	@ApiOperation("新增占用主机")
	@PostMapping("/device")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Boolean> createDevice(@RequestBody @Valid TeamTrainDeviceReq req) {
		String roomId = req.getRoomId();
		String deviceId = req.getDeviceId();
		String userId = UserInfoContext.getUserContext().getUserId();

		teamRoomService.checkStudentPermission(roomId, userId);

		// 校验主机是否被占用
		TeamTrainDevice occupied = teamDeviceService.getOccupied(roomId, deviceId);
		if (occupied != null) {
			throw new BusinessException(CrBusinessErrorCode.MACHINE_OCCUPIED);
		}

		TeamTrainDevice device = new TeamTrainDevice();
		device.setCreateUserId(userId);
		device.setRoomId(roomId);
		device.setDeviceId(deviceId);
		boolean ret = teamDeviceService.occupy(device);
		return new BaseRsp<>(ret);
	}

	@ApiOperation("解除主机占用")
	@DeleteMapping("/device")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Boolean> deleteDevice(@RequestBody @Valid TeamTrainDeviceReq req) {
		String roomId = req.getRoomId();
		String deviceId = req.getDeviceId();
		String userId = UserInfoContext.getUserContext().getUserId();
		Boolean ret = teamDeviceService.remove(roomId, userId, deviceId);
		return new BaseRsp<>(ret);
	}

	@ApiOperation("查询主机占用情况")
	@GetMapping("/device")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<TeamTrainDeviceContext>> getOccupiedDevices(
			@ApiParam(value = "room_id", name = "团队房间id", required = true) String room_id,
			@ApiParam(value = "standard_device", name = "主机",
					required = false) String standard_device) {
		List<TeamTrainDeviceContext> ret = teamDeviceService.getOccupies(room_id, standard_device);

		return new BaseRsp<>(ret);
	}

	@ApiOperation("常用资产可添加列表")
	@GetMapping("/device/common_device/not_yet")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<TeamCommonDeviceContext>> getNotYetCommonDevices(
			@ApiParam(name = "resource_id", value = "红蓝房间id", required = true) String resource_id,
			@ApiParam(name = "category", value = "设备类型", required = false) Integer category,
			@ApiParam(name = "name", value = "搜索关键字", required = false) String name) {

		// 1.拉取场景中可用 设备
		AssetPageReq assetPageReq = new AssetPageReq();
		assetPageReq.setResource_id(resource_id);
		List<JSONObject> availableDevices = null;
		try {
			availableDevices = crSceneService.getAssetList(assetPageReq, true);
		} catch (BusinessException e) {
			if (StringUtils.isNotEmpty(e.getMessage())
					&& e.getMessage().equals(ErrorCode.UNKNOWN_SCENE)) {
				log.error("localscene: [{}], is not started", resource_id);
				throw new BusinessException(ErrorCode.NOT_STARTED_SCENE);
			}
		}
		// 2.获取占用的设备
		List<TeamTrainDeviceContext> occupyDevices = teamDeviceService.getOccupies(resource_id,
				null);
		// 3.查询已添加设备
		// 所有已添加为常用资产的deviceIds
		List<TeamCommonDevice> allCollectedCommonDevices = teamCommonDeviceService
				.getAllCollectedCommonDeviceDevices(resource_id);
		// 4.装配设备信息和设置占用标志 并过滤 已添加设备
		List<TeamCommonDeviceContext> commonDeviceContexts = combineAvailableDevices(
				availableDevices, occupyDevices, resource_id, allCollectedCommonDevices);
		// 5.过滤可视设备
		commonDeviceContexts = teamCommonDeviceService.filterVisibleCommonDevices(resource_id,
				commonDeviceContexts);

		return new BaseRsp<>(commonDeviceContexts);
	}

	@ApiOperation("添加常用资产")
	@PostMapping("/device/common_device")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp addDevice(@RequestBody TeamCommonDeviceReq teamCommonDeviceReq) {
		if (StringUtils.isEmpty(teamCommonDeviceReq.getRoomId())) {
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}
		List<TeamCommonDeviceContext> commonDeviceContextList = teamCommonDeviceReq
				.getTeamCommonDeviceContextList();
		if (CollectionUtils.isEmpty(commonDeviceContextList)) {
			throw new BusinessException(CrBusinessErrorCode.INVALID_COMMON_DEVICE);
		}
		commonDeviceContextList.forEach(item -> {
			item.setRoomId(teamCommonDeviceReq.getRoomId());
		});
		teamCommonDeviceService.addListToCollect(commonDeviceContextList);
		return new BaseRsp();
	}

	@ApiOperation("删除常用资产")
	@DeleteMapping("/device/common_device")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp delDevice(@RequestBody TeamCommonDeviceContext teamCommonDeviceContext) {
		String resourceId = teamCommonDeviceContext.getResourceId();
		teamCommonDeviceService.delCollect(resourceId);
		return new BaseRsp();
	}

	@ApiOperation("常用资产已添加列表")
	@GetMapping("/device/common_device/already")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<TeamCommonDeviceContext>> getAlreadyCommonDevices(
			@ApiParam(name = "resource_id", value = "团队房间id", required = true) String resource_id,
			@ApiParam(name = "category", value = "设备类型", required = false) Integer category,
			@ApiParam(name = "name", value = "搜索关键字", required = false) String name) {
		// 2.拉取场景中可用 设备
		AssetPageReq assetPageReq = new AssetPageReq();
		assetPageReq.setResource_id(resource_id);
		List<JSONObject> availableDevices = null;
		try {
			availableDevices = crSceneService.getAssetList(assetPageReq, true);
		} catch (BusinessException e) {
			if (StringUtils.isNotEmpty(e.getMessage())
					&& e.getMessage().equals(ErrorCode.UNKNOWN_SCENE)) { // 如果是场景未启动导致的就返回空数组
				return new BaseRsp<>(new ArrayList<>());
			}
		}

		// 3.查询已添加的设备
		String userId = UserInfoContext.getUserContext().getUserId();
		List<TeamCommonDevice> collectedCommonDevices = teamCommonDeviceService
				.getCollectedCommonDevices(resource_id, userId);

		// 4.装配设备信息
		List<TeamCommonDeviceContext> commonDeviceContexts = combineAvailableDevices(
				availableDevices, collectedCommonDevices);

		return new BaseRsp<>(commonDeviceContexts);
	}

	/** 获取单个实例中自己的角色信息，只包括自己角色的基本信息
	 * @param crRoles
	 * @param userId
	 * @return
	 */
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

	/** 先获取实例列表中自己的角色，再获取所有的学员角色
	 * @param crRoles
	 * @param userId
	 * @return
	 */
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
				roleUsers.setUsers(crRole.getUserIdList());
				roleUsersList.add(roleUsers);
			}
		});

		baseRole.setRoleDataList(roleUsersList);

		return baseRole;
	}

	/**
	 * 装配 中台过来的设备信息 和 设置占用标志 并过滤 已添加设备
	 * @param availableDevices 场景中的可用设备，中台的设备状态信息列表
	 * @param occupyDevices 当前场景已占用的设备列表
	 * @param roomId 房间id
	 * @param allCollectedCommonDevices 已添加的常用资产列表
	 * @return 可添加的常用资产列表
	 */
	private List<TeamCommonDeviceContext> combineAvailableDevices(List<JSONObject> availableDevices,
			List<TeamTrainDeviceContext> occupyDevices, String roomId,
			List<TeamCommonDevice> allCollectedCommonDevices) {
		// 如果场景中可用设备为空，直接返回空数组
		if (CollectionUtils.isEmpty(availableDevices)) {
			return new ArrayList<>();
		}

		List<String> userIds = allCollectedCommonDevices.stream()
				.map(TeamCommonDevice::getCollectUserId).collect(Collectors.toList());
		List<UserInfo> userInfos = remoteUserService.getByIdList(userIds);
		List<TeamCommonDeviceContext> commonDeviceContexts = new ArrayList<>();
		for (int i = 0; i < availableDevices.size(); i++) {
			JSONObject availableDevice = availableDevices.get(i);

			// 根据占用设备情况 设置标志位
			TeamCommonDeviceContext context = new TeamCommonDeviceContext();
			for (TeamCommonDevice collectedCommonDeviceDevice : allCollectedCommonDevices) {
				if (!collectedCommonDeviceDevice.getDeviceId()
						.equals(availableDevice.getString("id"))) {
					continue;
				}
				context.setOccupyFlag(1);
				UserInfo userInfo = remoteUserService.searchUserByUserIdInUserList(
						collectedCommonDeviceDevice.getCollectUserId(), userInfos);
				if (userInfo == null) {
					continue;
				}
				context.setCollectUserId(userInfo.getUserId());
				context.setCollectUser(userInfo.getUserName());
			}

			// 如果 已添加常用设备 包含 场景中可用设备
			if (allCollectedCommonDevices.contains(availableDevice.getString("id"))) {
				context.setOccupyFlag(1);;
			}

			for (TeamTrainDeviceContext occupyDevice : occupyDevices) {
				if (!occupyDevice.getDeviceId().equals(availableDevice.getString("id"))) {
					context.setOccupyFlag(0);
				} else {
					context.setOccupyFlag(1);
					context.setCollectUserId(occupyDevice.getCreateUserId());
					context.setCollectUser(occupyDevice.getCreateUser());
				}
			}
			context.setDeviceId(availableDevice.getString("id"));
			context.setDeviceInfo(availableDevice);
			context.setRoomId(roomId);
			commonDeviceContexts.add(context);
		}
		return commonDeviceContexts;
	}

	/**
	 *
	 * @param availableDevices 常用中可用设备，中台的设备状态信息
	 * @param collectedCommonDevices 已添加的常用资产列表
	 * @return 已添加常用资产列表
	 */
	private List<TeamCommonDeviceContext> combineAvailableDevices(List<JSONObject> availableDevices,
			List<TeamCommonDevice> collectedCommonDevices) {
		if (CollectionUtils.isEmpty(availableDevices)
				|| CollectionUtils.isEmpty(collectedCommonDevices)) {
			return new ArrayList<>();
		}
		List<TeamCommonDeviceContext> commonDeviceContexts = new ArrayList<>();
		outer: for (int i = 0; i < availableDevices.size(); i++) {
			JSONObject availableDevice = availableDevices.get(i);
			TeamCommonDeviceContext context = new TeamCommonDeviceContext();

			for (TeamCommonDevice commonDevice : collectedCommonDevices) {
				if (!commonDevice.getDeviceId().equals(availableDevice.getString("id"))) {
					continue;
				}
				org.springframework.beans.BeanUtils.copyProperties(commonDevice, context);
				context.setDeviceInfo(availableDevice);
				context.setOccupyFlag(1);
				commonDeviceContexts.add(context);
				continue outer;
			}
		}
		return commonDeviceContexts;
	}
}
