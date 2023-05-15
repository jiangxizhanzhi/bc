package com.cyberpeace.cr.redvsblue.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.ReleaseMissionReq;
import com.cyberpeace.cr.crbase.controller.req.WriteUpReq;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneOpsReq;
import com.cyberpeace.cr.crbase.controller.rsp.RoleMissionRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.mission.RoleTaskReleaseProgress;
import com.cyberpeace.cr.crbase.service.context.mission.StageTaskReleaseProgress;
import com.cyberpeace.cr.crbase.service.context.mission.TaskReleaseProgress;
import com.cyberpeace.cr.crbase.service.context.role.*;
import com.cyberpeace.cr.redvsblue.controller.req.ExercisePaperReq;
import com.cyberpeace.cr.redvsblue.controller.req.MissionConfig;
import com.cyberpeace.cr.redvsblue.controller.req.RbMissionDeleteReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbRoomMissionReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.ExercisePaperRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTimeManagement;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbTimeManagementService;
import com.cyberpeace.cr.redvsblue.service.context.CrRbRoleContext;
import com.cyberpeace.cr.redvsblue.service.context.GroupData;
import com.cyberpeace.cr.redvsblue.service.context.GroupRole;
import com.cyberpeace.cr.redvsblue.service.context.TimeLineData;
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
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RemoteLogService remoteLogService;

	@ApiOperation("提交演习文书")
	@PostMapping("/add_exercise_paper")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<String>
			addExercisePaper(@RequestBody @Validated ExercisePaperReq exercisePaperReq) {
		RoleQuery roleQuery = new RoleQuery(exercisePaperReq.getResourceId(),
				exercisePaperReq.getGroupHash());

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);
		for (CrRole crRole : crRoles) {
			crRole.setExercisePaper(exercisePaperReq.getExercisePaper());
		}
		crRoleService.updateBatchById(crRoles);

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
				missionService.addDynamic(mission, crRole, rbRoomMissionReq.getMissionStage()));
	}

	@ApiOperation("任务删除")
	@PostMapping("/mission_delete")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<String>
			addMissionInfo(@RequestBody @Validated RbMissionDeleteReq rbMissionDeleteReq) {

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				List<MissionConfig> missionConfigList = rbMissionDeleteReq.getMissionConfig();
				for (MissionConfig missionConfig : missionConfigList) {
					CrRole crRole = crRoleService.getById(missionConfig.getRoleResourceId());
					if (!crRole.isRvbStudent()) {
						log.error("student role  does not exist！roleId=[{}] ",
								crRole.getResourceId());
						throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);

					}
					List<String> deleteMissions = missionConfig.getMissions();

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
		for (CrRole.RbRoleGroup roleGroup : CrRole.RbRoleGroup.studentRoleGroups()) {
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
		RbRoom rbRoom = rbRoomService.getById(resource_id);
		Date startTime = rbRoom.getStartTime();
		Date endTime = rbRoom.getEndTime();

		RbTimeManagement rbTimeManagement = rbTimeManagementService.getByRoomId(resource_id);
		if (rbTimeManagement != null) {
			startTime = rbTimeManagement.getCreateTime();

			if (RbTimeManagement.TimeManagementStatus.STOP.getCode()
					.equals(rbTimeManagement.getStatus())) {
				endTime = rbTimeManagement.getUpdateTime();
			} else {
				endTime = new Date();
			}
		}

		TimeLineData data = rbRoomService.getMissionTimeline(resource_id);

		data.setStartTime(startTime);
		data.setEndTime(endTime);

		return new BaseRsp(data);
	}

	@ApiOperation("停止演习")
	@PostMapping("/stop")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp stop(@RequestBody SceneOpsReq opsReq) {
		String roomId = opsReq.getRoomId();
		if (roomId == null) {
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}
		RbRoom room = rbRoomService.getById(roomId);
		if (room == null) {
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}
		// 需要导演权限
		String userId = UserInfoContext.getUserContext().getUserId();
		rbRoomService.checkDirectorPermission(roomId, userId);

		RbTimeManagement management = rbTimeManagementService.getByRoomId(roomId);
		if (management == null) {
			throw new BusinessException(CrBusinessErrorCode.TRAIN_NOT_BEGIN);
		}
		if (management.isStopped()) {
			throw new BusinessException(CrBusinessErrorCode.TRAIN_IS_OVER);
		}
		// 更改时间管理状态
		rbTimeManagementService.updateTimeManagement(management, room,
				RbTimeManagement.TimeManagementStatus.STOP.getCode(), userId);

		// 推送ws
		crTaskService.pushStatusWs(roomId, RbTimeManagement.TimeManagementStatus.STOP.getCode());

		// 记录远程操作日志

		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
				OperationType.STOP, room.getName());

		return new BaseRsp();
	}

	@ApiOperation("红蓝对抗-任务导调-任务进度")
	@GetMapping("/mission/progress")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<TaskReleaseProgress> getMissionStatistics(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {

		if (StringUtils.isBlank(resource_id)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		// get roles
		List<CrRole> studentRoles = rbRoomService.getStudentsRoles(resource_id);
		// parse mission and users
		List<CrRoleSimple> roles = studentRoles.stream().map(CrRoleSimple::new)
				.collect(Collectors.toList());
		// get tasks
		List<CrTask> crTaskList = crTaskService.getByRoomId(resource_id);

		TaskReleaseProgress progress = new TaskReleaseProgress();
		// count
		Integer released = crTaskList.size();
		Integer total = roles.stream().mapToInt(CrRoleSimple::getTaskCount).sum();
		Integer submitted = crTaskList.stream().mapToInt(task -> task.isSubmitted() ? 1 : 0).sum();

		progress.setTotal(total);
		progress.setReleased(released);
		progress.setUnreleased(total - released);
		progress.setSubmitted(submitted);
		progress.setUnsubmitted(released - submitted);

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
			// red role
			if (role.getRoleGroup().equals(CrRole.RbRoleGroup.RED.getCode())) {
				progress.getRedRoles().getRole().add(roleProgress);
				progress.getRedRoles().addTotal(roleProgress.getTotal());
				progress.getRedRoles().addReleased(roleProgress.getReleased());
			} else {
				// blue role
				progress.getBlueRoles().getRole().add(roleProgress);
				progress.getBlueRoles().addTotal(roleProgress.getTotal());
				progress.getBlueRoles().addReleased(roleProgress.getReleased());
			}
		}

		return new BaseRsp<>(progress);
	}
}
