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
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleContext;
import com.cyberpeace.cr.crbase.service.context.role.RoleData;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainTemplateRole;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cr.teamtrain.service.context.*;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.model.UserPageContext;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

/**
 *
 */
@Service
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

	@Override
	public TeamRoomData getTeamRoomRspByRoomId(String resourceId) {

		TeamRoomData teamRoomData = teamRoomMapper.getTeamRoomRspByRoomId(resourceId);
		if (teamRoomData == null) {
			throw new ResourceNotFoundException(resourceId);
		}
		teamRoomData.setTrainingStatus(setTrainingStatus(teamRoomData));

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

		TeamRoom teamRoom = new TeamRoom(teamTrainRoomContext, template, businessTemplate);
		teamRoom.setCreateUserId(userId);
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				save(teamRoom);

				List<TeamTrainTemplateRole> teamTrainTemplateRoleList = JsonUtil
						.toJsonObjectArray(template.getRoleConfig(), TeamTrainTemplateRole.class);
				List<RoomRole> users = teamTrainRoomContext.getRoleConfig();
				int index = 0;
				for (TeamTrainTemplateRole teamTrainTemplateRole : teamTrainTemplateRoleList) {

					CrRole crRole = BeanUtils.deepCopy(teamTrainTemplateRole, CrRole.class);
					// crRole.setBusinessInstanceId();
					crRole.setUserIds(JsonUtil.toJsonString(users.get(index++).getUsers()));
					teamTrainTemplateRole.getResourceId();
					JsonUtil.toJsonString(teamTrainTemplateRoleList);
					crRole.setBusinessInstanceId(teamRoom.getResourceId());
					crRole.setBusinessType(BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
					crRoleService.save(crRole);

				}

				// todo 保存任务角色信息
			}
		});

		return teamRoom;
	}

	@Override
	public void updateTeamRoom(TeamTrainRoomContext teamTrainRoomContext, TeamRoom teamRoom) {

		String userId = UserInfoContext.getUserContext().getUserId();

		if (!StringUtils.isBlank(teamTrainRoomContext.getName())) {
			teamRoom.setName(teamTrainRoomContext.getName());
		}
		if (teamTrainRoomContext.getCategory() != 0) {
			teamRoom.setSubType(teamTrainRoomContext.getCategory());
		}
		if (!StringUtils.isBlank(teamTrainRoomContext.getDescription())) {
			teamRoom.setDescription(teamTrainRoomContext.getDescription());
		}

		if (teamTrainRoomContext.getPreSkill().size() > 0) {
			teamRoom.setPreSkills(JsonUtil.toJsonString(teamTrainRoomContext.getPreSkill()));
		}
		if (teamTrainRoomContext.getTrainSkill().size() > 0) {
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

		return data;
	}

	@Override
	public Integer changeTrainingStatus(String resourceId) {
		TeamRoomData teamRoomdata = teamRoomMapper.getTeamRoomRspByRoomId(resourceId);
		teamRoomdata.setTrainingStatus(setTrainingStatus(teamRoomdata));

		// 未开始，新增local_scene数据
		if (teamRoomdata.isUnopened()) {
			LocalScene localScene = new LocalScene();
			localScene.setStartTime(new Date());
			localScene.setBusinessInstanceId(teamRoomdata.getResourceId());
			localSceneService.save(localScene);
		}
		// 已开始，修改local_scene数据
		if (teamRoomdata.isOpening()) {
			LocalScene localScene = localSceneService
					.getByBusinessInstanceId(teamRoomdata.getResourceId());
			localScene.setEndTime(new Date());
			localSceneService.save(localScene);
		}

		if (teamRoomdata.isOpening()) {
			// todo 日志
		}

		else if (teamRoomdata.isEnded()) {
			// todo 日志
		}
		return teamRoomdata.getTrainingStatus();

	}

	@Override
	public Integer getTrainingStatus(String resourceId) {
		TeamRoomData teamRoomdata = teamRoomMapper.getTeamRoomRspByRoomId(resourceId);
		teamRoomdata.setTrainingStatus(setTrainingStatus(teamRoomdata));
		return setTrainingStatus(teamRoomdata);
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
			if (crRole.getRoleGroup().intValue() != CrRole.RoleGroupCodeInteger.TEACHER
					.getValue()) {

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
	public TimeLineData getStudentsTimeLine(String resourceId) {
		TimeLineData timeLineData = new TimeLineData();
		List<TimeLineEvent> timeLineEventList = new ArrayList<>();
		List<TaskContext> taskList = crTaskService.getByRoomIdAndUser(resourceId,
				UserInfoContext.getUserContext().getUserId());

		for (TaskContext task : taskList) {
			TimeLineEvent timeLineEvent = new TimeLineEvent();

			timeLineEvent.setTime(task.getReportTime());
			timeLineEvent.setTitle(missionService.getById(task.getMissionId()).getTitle());
			timeLineEvent.setUserName(UserInfoContext.getUserContext().getName());

			timeLineEventList.add(timeLineEvent);
		}
		TeamRoom teamRoom = getById(resourceId);

		timeLineData.setCurrentTime(new Date());
		timeLineData.setStartTime(teamRoom.getStartTime());
		timeLineData.setEndTime(teamRoom.getEndTime());
		timeLineData.setUserTimeLineConfig(timeLineEventList);

		return timeLineData;
	}

	@Override
	public TimeLineData getTimeLine(String resourceId) {
		TimeLineData timeLineData = new TimeLineData();
		List<TimeLineEvent> timeLineEventList = new ArrayList<>();

		// todo 时间线

		TeamRoom teamRoom = getById(resourceId);

		timeLineData.setCurrentTime(new Date());
		timeLineData.setStartTime(teamRoom.getStartTime());
		timeLineData.setEndTime(teamRoom.getEndTime());
		timeLineData.setUserTimeLineConfig(timeLineEventList);

		return timeLineData;
	}

	@Override
	public String getTrainingManual(String resourceId) {
		TeamRoom teamRoom = getById(resourceId);
		List<MissionSchemeObject> missionSchemeList = Lists.newArrayList();
		CrRoleContext roles = crRoleService.getRoles(resourceId);
		for (RoleData roleData : roles.getRoles()) {
			List<MissionSchemeObject> missionSchemeObjectList = roleData.getMissionsList();
			missionSchemeList.addAll(missionSchemeObjectList);
			// todo 任务

		}
		return JsonUtil.toJsonString(missionSchemeList);
	}

	@Override
	public void checkTeacherPermission(String roomId, String userId) {

		permissionCheck(roomId, userId, CrRole.RoleGroupCodeInteger.TEACHER.getValue());
	}

	@Override
	public void checkRoomOpening(String roomId) {
		TeamRoomData teamRoomdata = teamRoomMapper.getTeamRoomRspByRoomId(roomId);
		teamRoomdata.setTrainingStatus(setTrainingStatus(teamRoomdata));

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
			throw new BusinessException(CrBusinessErrorCode.CASE_DOES_NOT_EXIST);
		}
		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (!crRole.getRoleGroup().equals(teacher)) {
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_NOT_EXIST);
		}
	}

	private int setTrainingStatus(TeamRoomData teamRoomdata) {
		if (null == teamRoomdata.getSceneStartTime()) {
			return TeamRoom.TrainingStatusCode.UNOPENED.getCode();
		} else if (null == teamRoomdata.getSceneEndTime()) {
			return TeamRoom.TrainingStatusCode.OPENING.getCode();
		} else {
			return TeamRoom.TrainingStatusCode.ENDED.getCode();
		}
	}

}
