package com.cyberpeace.cr.redvsblue.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.util.*;
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

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.context.MissionStageScheme;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.enums.RbRoleGroup;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.ReleaseMissionReq;
import com.cyberpeace.cr.crbase.controller.req.WriteUpReq;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneOpsReq;
import com.cyberpeace.cr.crbase.controller.rsp.RoleMissionRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.WebSocketPushService;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.mission.MissionConfig;
import com.cyberpeace.cr.crbase.service.context.mission.MissionDeleteReq;
import com.cyberpeace.cr.crbase.service.context.mission.RedBlueTaskReleaseProgress;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineData;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleContext;
import com.cyberpeace.cr.crbase.service.context.role.RoleData;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.redvsblue.controller.req.ExercisePaperReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbRoomMissionReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.ExercisePaperRsp;
import com.cyberpeace.cr.redvsblue.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.entity.RbTimeManagement;
import com.cyberpeace.cr.redvsblue.service.RbDirectorService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbSceneService;
import com.cyberpeace.cr.redvsblue.service.RbTimeManagementService;
import com.cyberpeace.cr.redvsblue.service.context.CrRbRoleContext;
import com.cyberpeace.cr.redvsblue.service.context.GroupData;
import com.cyberpeace.cr.redvsblue.service.context.GroupRole;
import com.cyberpeace.cs.service.context.scene.SceneData;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "红蓝对抗导演")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/director/red_vs_blue/red_vs_blue/")
public class RbRoomDirectorController {

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private RbTimeManagementService rbTimeManagementService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private RbDirectorService rbDirectorService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RemoteLogService remoteLogService;

	@Autowired
	private WebSocketPushService webSocketPushService;


	@Autowired
	RbSceneService rbSceneService;
	@ApiOperation("提交演习文书")
	@PostMapping("/add_exercise_paper")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<String>
			addExercisePaper(@RequestBody @Validated ExercisePaperReq exercisePaperReq) {

		// 校验方向长度
		int categoryMax = Integer.parseInt(ConfigUtil.getContextProperty("rtks.exercise.paper.size"));
		CrCommonValidator.validateRange(exercisePaperReq.getExercisePaper().length(), CrCommonConstants.ZERO_STR_LEN,
				categoryMax, CrBusinessErrorCode.INVALID_TRAINING_MANUAL_CONTENT);
		RoleQuery roleQuery = new RoleQuery(exercisePaperReq.getResourceId(),
				exercisePaperReq.getGroupHash());

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);
		for (CrRole crRole : crRoles) {
			crRole.setExercisePaper(exercisePaperReq.getExercisePaper());
		}
		crRoleService.updateBatchById(crRoles);
		List<String> userList = crRoles.stream().map(CrRole::getUserIdList).flatMap(Collection::stream).collect(Collectors.toList());
		webSocketPushService.notice(userList,MissionWsCode.EXERCISE_PAPER_CODE,exercisePaperReq.getResourceId(), WsPath.EXERCISE_PAPER);
		return new BaseRsp<>();
	}

	@ApiOperation("获取演习文书")
	@GetMapping("/get_exercise_paper")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<ExercisePaperRsp> getExercisePapern(
			@RequestParam("resource_id") String businessTemplateId,
			@RequestParam("group_hash") Integer group_hash) {

		RoleQuery roleQuery = new RoleQuery(businessTemplateId, group_hash);

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);
		ExercisePaperRsp exercisePaperRsp = new ExercisePaperRsp();
		exercisePaperRsp.setExercisePaper("");
		if (crRoles.size() > 0) {
			exercisePaperRsp.setExercisePaper(crRoles.get(0).getExercisePaper());
		}
		return new BaseRsp(exercisePaperRsp);
	}

	/**
	 * 修改开放题解
	 * @return
	 */
	@ApiOperation("修改开放题解")
	@PostMapping("/update_show_write_up")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<String> updateShowWriteUp(@RequestBody @Validated WriteUpReq writeUpReq) {

		rbRoomService.checkDirectorPermission(writeUpReq.getRoomId(),
				UserInfoContext.getUserContext().getUserId());
		rbRoomService.checkRoomOpening(writeUpReq.getRoomId());

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
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<String>
			releaseMissions(@RequestBody @Validated ReleaseMissionReq releaseMissionReq) {

		CrRole crRole = crRoleService.getById(releaseMissionReq.getRoleResourceId());

		rbRoomService.checkDirectorPermission(crRole.getRoomId(),
				UserInfoContext.getUserContext().getUserId());
		rbRoomService.checkRoomOpening(crRole.getRoomId());

		if (crRole == null) {
			log.error("role does not exist！roleId=[{}]", releaseMissionReq.getRoleResourceId());
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}

		missionService.release(releaseMissionReq.getMissionResourceId(),
				releaseMissionReq.getRoleResourceId(),
				UserInfoContext.getUserContext().getUserId());

		crTaskService.pushMissionWS(crRole.getRoomId(), MissionWsCode.RVB_MISSION_CODE);
		crTaskService.pushMissionWsToUsers(crRole.getRoomId(), crRole.getUserIdList(),
				MissionWsCode.MISSION_RELEASE);

		return new BaseRsp<>();
	}

	@ApiOperation("动态任务新增")
	@PostMapping("/mission_info_create")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<Boolean>
			addMissionInfo(@RequestBody @Validated RbRoomMissionReq rbRoomMissionReq) {
		// validate
		rbRoomService.checkRoomUnclosed(rbRoomMissionReq.getRoomId());
		rbRoomMissionReq.validate();

		// new mission
		Mission mission = BeanUtils.copy(rbRoomMissionReq, Mission.class);
		mission.setCategoryId(rbRoomMissionReq.getCategory());
		mission.setSkillPoints(JsonUtil.toJsonString(rbRoomMissionReq.getSkillPoints()));
		mission.setCategoryId(rbRoomMissionReq.getCategory());
		mission.setShowWriteup(false);

		CrRole crRole = crRoleService.getById(rbRoomMissionReq.getRoleResourceId());
		return new BaseRsp<>(
				missionService.addDynamic(mission, crRole, rbRoomMissionReq.getMissionStage(),rbRoomMissionReq.getAfterMissionId()));
	}

	@ApiOperation("任务删除")
	@PostMapping("/mission_delete")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<String>
			addMissionInfo(@RequestBody @Validated MissionDeleteReq rbMissionDeleteReq) {
		rbRoomService.checkRoomOpening(rbMissionDeleteReq.getRoomId());
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
					if (!crRole.isRvbStudent()) {
						log.error("student role  does not exist！roleId=[{}] ",
								crRole.getResourceId());
						throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);

					}

					if (crTaskService.getByMissions(rbMissionDeleteReq.getRoomId(), deleteMissions)
							.size() > 0) {
						log.error("mission is issued！missionIds=[{}] ",
								JSONObject.toJSONString(deleteMissions));
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

	@ApiOperation("查询角色")
	@GetMapping("/roles")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<CrRbRoleContext> getRoles(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
		CrRbRoleContext crRbRoleContext = new CrRbRoleContext();
		List<GroupData> groups = Lists.newArrayList();
		List<String> dynamics = Lists.newArrayList();

		// 遍历学员角色组
		for (RbRoleGroup roleGroup : RbRoleGroup.studentRoleGroups()) {
			Integer missionCount = 0;
			Integer peopleCount = 0;

			List<GroupRole> childRoleInfoList = Lists.newArrayList();
			String roleGroupName = roleGroup.getDesc();

			CrRoleContext<MissionContext> crRoleContext = crRoleService.getRoles(resource_id,
					roleGroup);

			crRbRoleContext.setMissionMapping(crRoleContext.getMissionMapping());
			crRbRoleContext.setShowWriteupMapping(crRoleContext.getShowWriteupMapping());
			dynamics.addAll(crRoleContext.getDynamic());

			CrRoleContext<RoleMissionRsp> crRoleContextRsp = BeanUtils.copy(crRoleContext,
					CrRoleContext.class);

			// 获取下发mission的id列表
			List<CrTask> crTasks = crTaskService.getByRoomId(resource_id);
			List<String> missionIds = StreamApiUtils.getColumn(crTasks, CrTask::getMissionId);

			// 遍历每一个角色
			for (int i = 0; i < crRoleContextRsp.getRoles().size(); i++) {
				RoleData<RoleMissionRsp> roleData = crRoleContextRsp.getRoles().get(i);

				List<String> userIds = JsonUtil.toJsonObjectArray(roleData.getUserIds(),
						String.class);

				List<RoleMissionRsp> roleMissionRspList = Lists.newArrayList();

				// 遍历每一个阶段
				for (int j = 0; j < roleData.getMissionsList().size(); j++) {
					MissionSchemeObject<RoleMissionRsp> roleMissionObject = roleData
							.getMissionsList().get(j);
					// 遍历获取mission列表
					List<MissionContext> missions = crRoleContext.getRoles().get(i)
							.getMissionsList().get(j).getMissions();

					List<RoleMissionRsp> roleStageMissionRspList = missions.stream()
							.map(roleMission -> {

								RoleMissionRsp roleMissionRsp = BeanUtils.copy(roleMission,
										RoleMissionRsp.class);
								roleMissionRsp.setCategory(roleMission.getCategoryId());
								roleMissionRsp.setMissionStatus(roleMission.getState());

								// 如果下发，获取mission的用户列表
								if (missionIds.contains(roleMission.getResourceId())) {
									List<String> userIdList = JsonUtil
											.toJsonObjectArray(roleData.getUserIds(), String.class);
									List<UserData> userDatas = crRoleService
											.getUserDatas(userIdList);

									List<RbUserData> rbUserDatas = userDatas.stream()
											.map(userData -> {
												RbUserData rbUserData = BeanUtils.copy(userData,
														RbUserData.class);

												rbUserData.setGroupName(roleGroupName);
												rbUserData.setRoleName(roleData.getName());

												return rbUserData;
											}).collect(Collectors.toList());

									roleMissionRsp.setDeliveryDetails(rbUserDatas);
								}

								return roleMissionRsp;
							}).collect(Collectors.toList());

					roleMissionRspList.addAll(roleStageMissionRspList);
					roleMissionObject.setMissions(roleMissionRspList);

				}

				GroupRole groupRole = new GroupRole();
				groupRole.setName(roleData.getName());
				groupRole.setMissions(roleMissionRspList);
				groupRole.setPeopleNumber(userIds.size());
				groupRole.setMissionNumber(roleMissionRspList.size());
				groupRole.setRoleResourceId(roleData.getResourceId());

				childRoleInfoList.add(groupRole);

				peopleCount += userIds.size();
				missionCount += roleMissionRspList.size();
			}

			GroupData groupData = new GroupData();

			groupData.setGroup(roleGroupName);
			groupData.setGroupHash(roleGroup.getCode());
			groupData.setRoles(childRoleInfoList);
			groupData.setGroupNumber(crRoleContextRsp.getRoles().size());
			groupData.setPeopleNumber(peopleCount);
			groupData.setMissionNumber(missionCount);

			groups.add(groupData);

		}

		crRbRoleContext.setDynamic(dynamics);
		crRbRoleContext.setGroups(groups);

		return new BaseRsp(crRbRoleContext);
	}

	/**
	 * 查询红蓝对抗时间线
	 * @return
	 */
	@ApiOperation("查询红蓝对抗导演时间线")
	@GetMapping("/mission_timeline")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<TimeLineData> getMissionTimeline(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
		TimeLineData data = rbRoomService.getMissionTimeline(resource_id);
		return new BaseRsp<>(data);
	}

	@ApiOperation("停止演习")
	@PostMapping("/stop")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp stop(@RequestBody SceneOpsReq opsReq) {
		String roomId = opsReq.getRoomId();
		if (roomId == null) {
			log.error("room_id is null");
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}
		RbRoom room = rbRoomService.getById(roomId);
		if (room == null) {
			log.error("room is null, room_id:{}", roomId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}
		// 需要导演权限
		String userId = UserInfoContext.getUserContext().getUserId();
		rbRoomService.checkDirectorPermission(roomId, userId);

		RbTimeManagement management = rbTimeManagementService.getByRoomId(roomId);
		if (management == null) {
			log.error("time management is null, room_id:{}", roomId);
			throw new BusinessException(CrBusinessErrorCode.TRAIN_NOT_BEGIN);
		}
		if (management.isStopped()) {
			log.error("time management is stopped, room_id:{}", roomId);
			throw new BusinessException(CrBusinessErrorCode.TRAIN_IS_OVER);
		}
		// 更改时间管理状态
		management.setLength(management.getLength()
				+ TimeUtil.getIntervalSeconds(management.getRecoverTime(), new Date()));
		management.setEndTime(new Date());
		rbTimeManagementService.updateTimeManagement(management, room,
				RbTimeManagement.TimeManagementStatus.STOP.getCode(), userId);

		// 推送ws
		crTaskService.pushStatusWs(roomId, RbTimeManagement.TimeManagementStatus.STOP.getCode());

		//5.4 如果场景是暂停状态直接关闭
		SceneData sceneData = rbSceneService.getSceneData(room);
		if (sceneData!=null&&sceneData.isPause()) {
			rbSceneService.stopScene(roomId);
		}

		// 记录远程操作日志

		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
				OperationType.STOP, room.getName());

		return new BaseRsp();
	}

	@ApiOperation("红蓝对抗-任务导调-任务进度")
	@GetMapping("/mission/progress")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<RedBlueTaskReleaseProgress> getMissionStatistics(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
		RedBlueTaskReleaseProgress res = rbDirectorService.getTaskReleaseProgress(resource_id);
		return new BaseRsp<>(res);
	}

	@ApiOperation("批量任务下发")
	@PostMapping("/batchMissions")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<Boolean> releaseBatchMissions(@RequestBody @Validated ReleaseMissionReq releaseMissionReq) {

		if (CollectionUtils.isEmpty(releaseMissionReq.getMissionResourceIds())) {
			return new BaseRsp<>(true);
		}
		CrRole crRole = crRoleService.getById(releaseMissionReq.getRoleResourceId());

		rbRoomService.checkDirectorPermission(crRole.getRoomId(),
				UserInfoContext.getUserContext().getUserId());
		rbRoomService.checkRoomOpening(crRole.getRoomId());

		Boolean res = missionService.batchRelease(releaseMissionReq.getMissionResourceIds(),
				releaseMissionReq.getRoleResourceId(),
				UserInfoContext.getUserContext().getUserId());
		if (Boolean.TRUE.equals(res)) {
			crTaskService.pushMissionWS(crRole.getRoomId(), MissionWsCode.RVB_MISSION_CODE);
			crTaskService.pushMissionWsToUsers(crRole.getRoomId(), crRole.getUserIdList(),
					MissionWsCode.MISSION_RELEASE);
		}

		return new BaseRsp<>(res);
	}

	@ApiOperation("红蓝对抗-任务导调-任务列表")
	@GetMapping("/mission/list")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<TrainTaskReleaseInfoContext>> getMissionList(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {

		if (StringUtils.isBlank(resource_id)) {
			log.error("resource_id_missing");
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		List<TrainTaskReleaseInfoContext> list = new ArrayList<>();
		// 获取该房间的角色信息
		List<TrainTaskReleaseInfoRsp> red = crRoleService.getTrainTaskReleaseInfoList(resource_id,
				RbRoleGroup.RED.getCode());
		list.add(new TrainTaskReleaseInfoContext("red", red));
		List<TrainTaskReleaseInfoRsp> blue = crRoleService.getTrainTaskReleaseInfoList(resource_id,
				RbRoleGroup.BLUE.getCode());
		list.add(new TrainTaskReleaseInfoContext("blue", blue));
		return new BaseRsp<>(list);
	}

	/**
	 * 获取复盘时间轴
	 * 传入用户id，则展示事件，否则只提供开始时间、结束时间
	 *
	 * @param resource_id 资源id
	 * @param user_id     用户id
	 * @return {@code BaseRsp<TimeLineData>}
	 */
	@ApiOperation("导演-复盘时间轴")
	@GetMapping("/replay/timeline")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<TimeLineData> getReviewTimeLine(
			@ApiParam(name = "resource_id", value = "红蓝训练id", required = true) String resource_id,
			@ApiParam(name = "user_id", value = "用户id", required = false) String user_id) {
		CrCommonValidator.validateId(resource_id);
		TimeLineData res = rbRoomService.getReplayTimeline(resource_id, user_id);
		return new BaseRsp<>(res);
	}

	/**
	 * 满足复盘条件，返回scene_id
	 * 不满足复盘条件，返回错误提示
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<String>}
	 */
	@ApiOperation("查询复盘所需scene_id")
	@GetMapping("/replay/enable")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<String> isReplayEnabled(
			@ApiParam(name = "resource_id", value = "红蓝训练id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
		String res = rbDirectorService.getReplaySceneId(resource_id);
		return new BaseRsp<>(res);
	}
}
