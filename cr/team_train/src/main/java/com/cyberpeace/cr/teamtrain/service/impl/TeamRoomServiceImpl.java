package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskLog;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskLogMapper;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.AddTemplateMission;
import com.cyberpeace.cr.crbase.service.context.AddTemplateMissionScheme;
import com.cyberpeace.cr.crbase.service.context.MissionStageScheme;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cr.teamtrain.service.context.*;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.UserPageContext;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Service
@Slf4j
public class TeamRoomServiceImpl extends ServiceImpl<TeamRoomMapper, TeamRoom> implements TeamRoomService {

	@Autowired
	private TeamRoomMapper teamRoomMapper;

	@Autowired
	TeamTemplateService teamTemplateService;

	@Autowired
	BusinessTemplateService businessTemplateService;

	@Autowired
	MissionService missionService;

	@Autowired
	CrRoleService crRoleService;

	@Autowired
	CrTaskService crTaskService;

	@Autowired
	LocalSceneService localSceneService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private CrTaskLogMapper crTaskLogMapper;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Override
	public TeamRoomData getTeamRoomDataByRoomId(String resourceId) {

		TeamRoomData teamRoomData = teamRoomMapper.getTeamRoomRspByRoomId(resourceId);
		if (teamRoomData == null) {
			throw new ResourceNotFoundException(resourceId);
		}
		teamRoomData.setTrainingStatus(getTrainingStatus(teamRoomData));

		return teamRoomData;
	}

	@Override
	public TeamRoom create(TeamTrainRoomContext teamTrainRoomContext) {
		String userId = UserInfoContext.getUserContext().getUserId();

		TeamTemplate template = teamTemplateService.getById(teamTrainRoomContext.getTemplate());
		if (template == null) {
			throw new ResourceNotFoundException(teamTrainRoomContext.getTemplate());
		}

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		TeamRoom teamRoom = new TeamRoom(teamTrainRoomContext, template);
		teamRoom.setCreateUserId(userId);
		teamRoom.setCopyedTopoId(remoteTopoService.copyTopo(businessTemplate.getTopoId()));
		teamRoom.setTeamTemplateName(businessTemplate.getName());
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				save(teamRoom);

				List<TeamTrainTemplateRole> teamTrainTemplateRoleList = JsonUtil
						.toJsonObjectArray(template.getRoleConfig(), TeamTrainTemplateRole.class);
				List<RoomRole> users = teamTrainRoomContext.getRoleConfig();
				int index = 0;
				TeamTrainTemplateRole teacherRole = new TeamTrainTemplateRole();
				teacherRole.setName("教员");
				teacherRole.setRoleGroup(CrRole.TeamRoleGroupCode.TEACHER.getValue());

				teamTrainTemplateRoleList.add(0, teacherRole);

				for (TeamTrainTemplateRole teamTrainTemplateRole : teamTrainTemplateRoleList) {

					CrRole crRole = BeanUtils.deepCopy(teamTrainTemplateRole, CrRole.class);
					// crRole.setBusinessInstanceId();
					crRole.setUserIds(JsonUtil.toJsonString(users.get(index++).getUsers()));
					teamTrainTemplateRole.getResourceId();
					JsonUtil.toJsonString(teamTrainTemplateRoleList);
					crRole.setRoomId(teamRoom.getResourceId());
					crRole.setBusinessType(BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
					crRole.setMissionScheme(getNewRoomScheme(
							teamTrainTemplateRole.getMissionsScheme(), teamRoom.getResourceId()));
					crRoleService.save(crRole);

				}

				LocalScene localScene = getLocalScene(teamRoom.getResourceId());

				localSceneService.save(localScene);
				teamRoom.setLocalSceneId(localScene.getResourceId());
				teamRoomMapper.updateById(teamRoom);

			}

		});

		return teamRoom;
	}

	/** 根据模板的任务id，复制任务并获取新的id，返回新的json字符串
	 * @param missionsScheme 
	 * @return
	 */
	private String getNewRoomScheme(String missionsScheme, String roomId) {

		ColorMission colorMission = JsonUtil.toJsonObject(missionsScheme, ColorMission.class);
		if (null == colorMission) {
			return "[]";
		}
		List<MissionStageScheme> missionStageSchemes = Lists.newArrayList();
		List<String> originMissionIds = Lists.newArrayList();
		// 遍历每个阶段
		for (AddTemplateMissionScheme schemeDatum : colorMission.getSchemeData()) {

			List<AddTemplateMission> missions = schemeDatum.getMissions();
			// 遍历每个任务
			for (AddTemplateMission mission : missions) {
				originMissionIds.add(mission.getResourceId());
			}
		}

		Map<String, String> originMissionMaps = missionService.copyScheme(originMissionIds,
				UserInfoContext.getUserContext().getUserId(), roomId);

		// 遍历每个阶段设置新的任务id
		for (AddTemplateMissionScheme schemeDatum : colorMission.getSchemeData()) {
			MissionStageScheme missionStageScheme = new MissionStageScheme();

			List<AddTemplateMission> missions = schemeDatum.getMissions();
			List<String> targetMissionIds = Lists.newArrayList();
			missionStageScheme.setStage(schemeDatum.getStage());
			missionStageScheme.setDynamic(schemeDatum.getDynamic());

			// 遍历每个任务设置新的任务id
			for (AddTemplateMission mission : missions) {
				targetMissionIds.add(originMissionMaps.getOrDefault(mission.getResourceId(),
						mission.getResourceId()));
			}
			missionStageScheme.setMissions(targetMissionIds);
			missionStageSchemes.add(missionStageScheme);
		}

		return JsonUtil.toJsonString(missionStageSchemes);

	}

	@Override
	public void updateTeamRoom(TeamTrainRoomContext teamTrainRoomContext, TeamRoom teamRoom) {

		String userId = UserInfoContext.getUserContext().getUserId();

		if (!StringUtils.isBlank(teamTrainRoomContext.getName())) {
			teamRoom.setName(teamTrainRoomContext.getName());
		}
		if (teamTrainRoomContext.getCategory() != null) {
			teamRoom.setSubType(teamTrainRoomContext.getCategory());
		}
		if (!StringUtils.isBlank(teamTrainRoomContext.getDescription())) {
			teamRoom.setDescription(teamTrainRoomContext.getDescription());
		}

		if (teamTrainRoomContext.getPreSkill() != null
				&& teamTrainRoomContext.getPreSkill().size() > 0) {
			teamRoom.setPreSkills(JsonUtil.toJsonString(teamTrainRoomContext.getPreSkill()));
		}
		if (teamTrainRoomContext.getTrainSkill() != null
				&& teamTrainRoomContext.getTrainSkill().size() > 0) {
			teamRoom.setTrainSkills(JsonUtil.toJsonString(teamTrainRoomContext.getTrainSkill()));
		}
		if (teamTrainRoomContext.getStartTime() != null) {
			teamRoom.setStartTime(teamTrainRoomContext.getStartTime());
		}
		if (teamTrainRoomContext.getEndTime() != null) {
			teamRoom.setEndTime(teamTrainRoomContext.getEndTime());
		}
		teamRoom.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				updateById(teamRoom);
				if (teamTrainRoomContext.getRoleConfig() != null
						&& teamTrainRoomContext.getRoleConfig().size() > 0) {

					Map<String, RoomRole> roomRoleMap = teamTrainRoomContext.getRoleConfig()
							.stream().collect(Collectors.toMap(RoomRole::getResourceId, bo -> bo));

					RoleQuery roleQuery = new RoleQuery();
					roleQuery.setRoomId(teamRoom.getResourceId());
					List<CrRole> crRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);

					crRoleList.forEach(crRole -> {
						List<String> users = roomRoleMap.get(crRole.getResourceId()).getUsers();
						crRole.setUserIds(JsonUtil.toJsonString(users));
						crRoleService.updateById(crRole);

					});
					// crRoleService.updateBatchById(crRoleList);
				}
			}
		});

	}

	@Override
	public IPage<TeamRoomData> list(ListQuery listQuery) {
		String userId = UserInfoContext.getUserContext().getUserId();

		Page page = listQuery.toPageParam();
		listQuery.setIds(crRoleService.getRoomIdsByUserId(userId));

		IPage<TeamRoomData> data = teamRoomMapper.listTeamRoom(page, listQuery);
		for (TeamRoomData teamRoomData : data.getRecords()) {
			teamRoomData.setTrainingStatus(getTrainingStatus(teamRoomData));
		}

		return data;
	}

	@Override
	public Integer changeTrainingStatus(String resourceId) {
		TeamRoomData teamRoomdata = getTeamRoomDataByRoomId(resourceId);

		LocalScene localScene = localSceneService
				.getByBusinessInstanceId(teamRoomdata.getResourceId());
		if (teamRoomdata.isUnopened()) {
			teamRoomdata.setTrainingStatus(TeamRoom.TrainingStatusCode.OPENING.getCode());
			teamRoomdata.setSceneStartTime(new Date());
			localScene.setStartTime(new Date());
		}
		// 已开始，修改local_scene数据
		else if (teamRoomdata.isOpening()) {
			teamRoomdata.setSceneEndTime(new Date());
			teamRoomdata.setTrainingStatus(TeamRoom.TrainingStatusCode.ENDED.getCode());
			localScene.setEndTime(new Date());
		} else if (teamRoomdata.isEnded()) {
			throw new BusinessException(CrBusinessErrorCode.TRAIN_IS_OVER);
		}
		localSceneService.updateById(localScene);

		if (teamRoomdata.isOpening()) {
			// todo 审计日志
		}

		else if (teamRoomdata.isEnded()) {
			// todo 审计日志
		}
		return getTrainingStatus(teamRoomdata);

	}

	@Override
	public Integer getTrainingStatus(String resourceId) {
		TeamRoomData teamRoomdata = getTeamRoomDataByRoomId(resourceId);

		return teamRoomdata.getTrainingStatus();
	}

	@Override
	public List<UserRole> getUserList(String resourceId) {
		List<UserRole> userRoleList = Lists.newArrayList();

		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(resourceId);
		List<String> userList = Lists.newArrayList();

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);
		for (CrRole crRole : crRoles) {
			// 获取每个学员用户，以及学员id
			if (crRole.getRoleGroup().intValue() != CrRole.TeamRoleGroupCode.TEACHER.getValue()) {

				List<String> users = JsonUtil.toJsonObjectArray(crRole.getUserIds(), String.class);
				users.forEach(user -> {
					UserRole userRole = new UserRole();
					userRole.setResourceId(user);
					userRole.setRoleName(crRole.getName());
					userRole.setRoleDesc(crRole.getDescription());
					userRoleList.add(userRole);
					userList.add(user);
				});

			}
		}

		UserPageContext userPageContext = new UserPageContext();
		userPageContext.setIds(String.join(",", userList));
		List<UserInfo> userInfoList = remoteUserService.getLazyUserList(userPageContext);

		Map<String, UserInfo> userInfoMap = userInfoList.stream()
				.collect(Collectors.toMap(UserInfo::getUserId, bo -> bo));

		for (UserRole userRole : userRoleList) {
			userRole.setName(userInfoMap.get(userRole.getResourceId()).getName());
			userRole.setLogo(userInfoMap.get(userRole.getResourceId()).getAvatarUrl());
		}

		return userRoleList;
	}

	@Override
	public StudentsTimeLineData getStudentsTimeLine(String roomId) {
		TimeLineData timeLineData = new TimeLineData();
		setTime(timeLineData, roomId);
		StudentsTimeLineData studentsTimeLineData = BeanUtils.copy(timeLineData,
				StudentsTimeLineData.class);

		List<TimeLineEvent> timeLineEventList = new ArrayList<>();
		List<TaskContext> taskList = crTaskService.getByRoomIdAndUser(roomId,
				UserInfoContext.getUserContext().getUserId());

		for (TaskContext task : taskList) {
			TimeLineEvent timeLineEvent = new TimeLineEvent();

			timeLineEvent.setTime(task.getReportTime());
			timeLineEvent.setTitle(missionService.getById(task.getMissionId()).getTitle());
			timeLineEvent.setUserName(UserInfoContext.getUserContext().getName());

			timeLineEventList.add(timeLineEvent);
		}

		studentsTimeLineData.setData(timeLineEventList);

		return studentsTimeLineData;
	}

	@Override
	public TeamRoom getByLocalSceneId(String localSceneId) {
		TeamRoom room = teamRoomMapper.selectbySceneManagerId(localSceneId);
		if (room == null) {
			log.error("failed to get TeamRoom with localSceneId [{}]", localSceneId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}
		return room;
	}

	@Override
	public TeamRoom getById(String roomId) {
		TeamRoom room = teamRoomMapper.selectById(roomId);
		if (room == null) {
			log.error("failed to get TeamRoom with Id [{}]", roomId);
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}
		return room;
	}

	@Override
	public TimeLineData getTimeLine(String roomId) {
		TimeLineData timeLineData = new TimeLineData();
		List<TimeLineEvent> timeLineEventList = new ArrayList<>();

		List<TaskContext> taskList = crTaskService.getByRoomIdAndUser(roomId,
				UserInfoContext.getUserContext().getUserId());

		List<Integer> actionCodes = Lists.newArrayList();
		actionCodes.add(CrTaskLog.ActionCode.RELEASE.getCode());
		List<CrTaskLog> crTaskLogList = crTaskLogMapper.selectReleaseByRoomIdAndActionCodes(roomId,
				actionCodes);

		for (CrTaskLog crTaskLog : crTaskLogList) {
			TimeLineEvent timeLineEvent = new TimeLineEvent();

			timeLineEvent.setTime(crTaskLog.getCreateTime());
			timeLineEvent.setTitle(missionService.getById(crTaskLog.getMissionId()).getTitle());
			timeLineEvent.setUserName("");
			timeLineEvent.setType(TimeLineEvent.SendDataType.SEND_DATA_IS_MISSION.getCode());

			timeLineEventList.add(timeLineEvent);
		}

		timeLineData.setUserTimeLineConfig(timeLineEventList);

		return timeLineData;
	}

	@Override
	public void checkStudentPermission(String roomId, String userId) {
		permissionCheck(roomId, userId, CrRole.TeamRoleGroupCode.STUDENT.getValue());
	}

	@Override
	public void checkTeacherPermission(String roomId, String userId) {
		permissionCheck(roomId, userId, CrRole.TeamRoleGroupCode.TEACHER.getValue());
	}

	@Override
	public void checkRoomOpening(String roomId) {
		TeamRoomData teamRoomdata = getTeamRoomDataByRoomId(roomId);
		// 未开始，新增local_scene数据
		if (teamRoomdata.isUnopened()) {
			throw new BusinessException(CrBusinessErrorCode.TRAIN_NOT_BEGIN);

		}
		if (teamRoomdata.isEnded()) {
			throw new BusinessException(CrBusinessErrorCode.TRAIN_IS_OVER);

		}
	}

	private void permissionCheck(String roomId, String userId, Integer teacher) {
		TeamRoom teamRoom = getById(roomId);
		if (teamRoom == null) {
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}
		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (!crRole.getRoleGroup().equals(teacher)) {
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
	}

	private int getTrainingStatus(TeamRoomData teamRoomdata) {
		if (null == teamRoomdata.getSceneStartTime()) {
			return TeamRoom.TrainingStatusCode.UNOPENED.getCode();
		} else if (null == teamRoomdata.getSceneEndTime()) {
			return TeamRoom.TrainingStatusCode.OPENING.getCode();
		} else {
			return TeamRoom.TrainingStatusCode.ENDED.getCode();
		}
	}

	private LocalScene getLocalScene(String teamRoomId) {
		TeamRoom teamRoom = teamRoomMapper.selectById(teamRoomId);

		LocalScene localScene = new LocalScene();
		localScene.setUserId(UserInfoContext.getUserContext().getUserId());
		localScene.setSceneStatus(LocalScene.SceneStatusEnum.INIT.getCode());
		localScene.setBusinessCode(LocalScene.BusinessCode.TEAM_TRAINING.getCode());

		localScene.setDestoryMode(LocalScene.DestroyMode.MANUAL.getCode());
		localScene.setBusinessInstanceId(teamRoomId);
		localScene.setTopoId(teamRoom.getCopyedTopoId());

		return localScene;
	}

	private void setTime(TimeLineData timeLineData, String roomId) {
		TeamRoomData teamRoom = getTeamRoomDataByRoomId(roomId);

		timeLineData.setCurrentTime(new Date());
		timeLineData.setStartTime(teamRoom.getSceneStartTime() == null ? teamRoom.getStartTime()
				: teamRoom.getSceneStartTime());

		Date endTime = teamRoom.getEndTime();

		if (teamRoom.getSceneStartTime() != null) {
			if (teamRoom.getSceneEndTime() == null) {
				long startToEnd = teamRoom.getEndTime().getTime()
						- teamRoom.getStartTime().getTime();
				long now = new Date().getTime();
				long sceneStartToNow = new Date().getTime()
						- teamRoom.getSceneStartTime().getTime();
				// 训练时长大于实际开始时间到当前时间的时长，时间间隔取训练时长,否则取当前时间
				if (startToEnd >= sceneStartToNow) {
					endTime = new Date(startToEnd + teamRoom.getSceneStartTime().getTime());
				} else {
					endTime = new Date(now);
				}
			}
			if (teamRoom.getSceneEndTime() != null
					&& teamRoom.getSceneEndTime().compareTo(teamRoom.getSceneStartTime()) > 0) {
				endTime = teamRoom.getSceneStartTime();

			}
		}

		timeLineData.setEndTime(endTime);
	}

	private void preStartScene(String localSceneId) {
		// TODO
		// 根据角色校验是否启动
	}
	//
	// private SceneStartContext getSceneStartContext() {
	// // TODO
	// // 单兵、团队、红蓝是否可以聚合
	// SceneStartContext sceneStartContext = new SceneStartContext();
	//
	// sceneStartContext.setContext(new HashMap<>());
	// sceneStartContext.setSceneAttr(SceneStartContext.SceneAttrEnum.SINGLE.getCode());
	//
	// return sceneStartContext;
	// }

}
