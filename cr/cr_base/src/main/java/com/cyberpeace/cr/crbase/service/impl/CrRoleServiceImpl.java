package com.cyberpeace.cr.crbase.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.room.DashboardInfo;
import com.cyberpeace.common.model.room.DashboardRole;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReleaseStageInfo;
import com.cyberpeace.cr.crbase.service.context.role.*;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@Service
public class CrRoleServiceImpl extends ServiceImpl<CrRoleMapper, CrRole> implements CrRoleService {

	@Autowired
	private CrRoleMapper crRoleMapper;

	@Autowired
	private MissionService missionService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private CrTaskService crTaskService;

	@Override
	public CrRole getById(String roleId) {
		CrRole role = crRoleMapper.selectById(roleId);
		if (role == null) {
			log.error("role does not exist！roleId=[{}]", roleId);
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}
		return role;
	}

	@Override
	public CrRoleContext<MissionContext> getRoles(String roomId, CrRole.RoleGroup roleGroup) {
		CrRoleContext<MissionContext> crRoleContext = new CrRoleContext();
		RoleQuery roleQuery = new RoleQuery(roomId, roleGroup);

		List<CrRole> crRoles = crRoleMapper.selectCrRoleByRoomId(roleQuery);
		if (crRoles == null) {
			log.error("can't find crRole! roomId = [{}]", roomId);
			throw new ResourceNotFoundException(roomId);
		}

		List<RoleData<MissionContext>> roleResDataList = Lists.newArrayList();
		List<String> dynamics = Lists.newArrayList();

		for (CrRole crRole : crRoles) {

			List<MissionSchemeObject<MissionContext>> missionSchemeObjectList = getBaseMissionByRole(
					crRole);

			for (MissionSchemeObject<MissionContext> missionSchemeObject : missionSchemeObjectList) {
				if (missionSchemeObject.getDynamic() != null) {
					dynamics.addAll(missionSchemeObject.getDynamic());
				}
			}

			RoleData<MissionContext> roleResData = new RoleData();
			roleResData.setName(crRole.getName());
			roleResData.setResourceId(crRole.getResourceId());
			roleResData.setMissionsList(missionSchemeObjectList);
			roleResData.setUsers(getUserByRoleUsers(crRole.getUserIdList()));
			roleResData.setUserIds(crRole.getUserIds());

			roleResDataList.add(roleResData);
		}
		crRoleContext.setRoles(roleResDataList);

		Map<String, MissionCompletion> missionCompletionMapping = missionService
				.getMissionStatusData(roomId);

		List<Mission> missionList = missionService.batchGetByRoom(roomId);

		ShowWriteup showWriteup = new ShowWriteup();
		showWriteup.setShowWriteup(true);
		Map<String, ShowWriteup> showWriteupMap = Maps.newHashMap();

		missionList.forEach(mission -> {
			if (mission.getShowWriteup()) {
				showWriteupMap.put(mission.getResourceId(), showWriteup);
			}
		});

		crRoleContext.setShowWriteupMapping(showWriteupMap);
		crRoleContext.setMissionMapping(missionCompletionMapping);
		crRoleContext.setDynamic(dynamics);

		return crRoleContext;
	}

	@Override
	public List<CrRole> getRolesByRoom(String roomId, Integer businessType) {
		return crRoleMapper.getRolesByRoom(roomId, businessType);
	}

	@Override
	public List<MissionSchemeObject<MissionContext>> getBaseMissionByRole(CrRole crRole) {
		List<MissionSchemeObject<MissionContext>> missionSchemeObjectList = Lists.newArrayList();
		for (MissionStageScheme missionStageScheme : crRole.getMissionStageSchemes()) {
			List<Mission> missionList = missionService.batchGet(missionStageScheme.getMissions());

			missionList.forEach(mission -> {
				mission.setAnswer("");
			});

			List<MissionContext> missionContextList = BeanUtils.deepCopyList(missionList,
					MissionContext.class);
			MissionSchemeObject missionSchemeObject = BeanUtils.copy(missionStageScheme,
					MissionSchemeObject.class);
			missionSchemeObject.setMissions(missionContextList);
			missionSchemeObjectList.add(missionSchemeObject);
		}
		return missionSchemeObjectList;
	}

	@Override
	public List<String> getRoomIdsByUserId(String userId) {
		return crRoleMapper.getRoomIdsByUserId(userId);

	}

	@Override
	public List<CrRole> selectCrRoleByRoomId(RoleQuery roleQuery) {
		return crRoleMapper.selectCrRoleByRoomId(roleQuery);
	}

	@Override
	public List<Integer> getRadar(String roomId, String userId) {

		CrRole crRole = getCrRoleByRoomIdAndUserId(roomId, userId);

		List<MissionSchemeObject<MissionContext>> missionSchemeObjectList = getBaseMissionByRole(
				crRole);
		// 获取当前学员的每项总分
		List<Integer> allPoints = CrCommonUtil.getInitPoints();

		for (MissionSchemeObject<MissionContext> missionSchemeObject : missionSchemeObjectList) {
			for (MissionContext mission : missionSchemeObject.getMissions()) {
				String skillPoints = mission.getSkillPoints();
				List<Integer> points = JSON.parseArray(skillPoints, Integer.class);

				for (int i = 0; i < points.size(); i++) {
					allPoints.set(i, allPoints.get(i) + points.get(i));
				}
			}
		}

		// 获取当前学员成功的每项总分
		List<Integer> successPoints = CrCommonUtil.getInitPoints();

		List<CrTask> finishedTasks = crTaskService.getFinishedByRoomIdAndUserId(roomId, userId);
		for (CrTask finishedTask : finishedTasks) {
			if (CrTask.TaskStatus.SUCCESS.getCode().equals(finishedTask.getStatus())) {
				String skillPoints = missionService.getById(finishedTask.getMissionId())
						.getSkillPoints();
				List<Integer> points = JSON.parseArray(skillPoints, Integer.class);

				for (int i = 0; i < points.size(); i++) {
					successPoints.set(i, successPoints.get(i) + points.get(i));
				}
			}
		}

		List<Integer> radarPoints = CrCommonUtil.getInitPoints();

		for (int i = 0; i < successPoints.size(); i++) {
			int radarPoint = 0;
			if (!allPoints.get(i).equals(0)) {
				radarPoint = successPoints.get(i) * 100 / allPoints.get(i);
			}
			radarPoints.set(i, radarPoint);
		}

		return radarPoints;
	}

	@Override
	public CrRole getCrRoleByRoomIdAndUserId(String roomId, String userId) {

		CrRole crRole = crRoleMapper.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (crRole == null) {
			log.error("User does not have Role in TeamRoom！roomId=[{}] userId=[{}]", roomId,
					userId);
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}
		return crRole;
	}

	@Override
	public List<CrRole> getRolesByRoomId(String roomId) {
		return crRoleMapper.getRolesByRoomId(roomId);
	}

	@Override
	public List<MissionSchemeObject<MissionContext>>
			getCrRoleMissionByRoomIdAndUserId(String roomId, String userId) {

		CrRole crRole = getCrRoleByRoomIdAndUserId(roomId, userId);

		List<MissionSchemeObject<MissionContext>> missionSchemeObjectList = getBaseMissionByRole(
				crRole);

		List<TaskContext> tasks = crTaskService.getByRoomIdAndUser(roomId, userId);
		Map<String, Integer> taskStatusMap = tasks.stream().collect(Collectors
				.toMap(TaskContext::getMissionId, taskContext -> taskContext.getStatus()));

		missionSchemeObjectList.forEach(missionSchemeObject -> {

			List<MissionContext> missionContextList = Lists.newArrayList();
			missionSchemeObject.getMissions().forEach(mission -> {
				if (taskStatusMap.containsKey(mission.getResourceId())) {
					MissionContext newMission = new MissionContext();
					newMission.setResourceId(mission.getResourceId());
					newMission.setTitle(mission.getTitle());
					newMission.setCategoryId(mission.getCategoryId());
					newMission.setDescription(mission.getDescription());
					newMission.setState(taskStatusMap.get(mission.getResourceId()));
					missionContextList.add(newMission);
				}

			});
			missionSchemeObject.setMissions(missionContextList);

		});
		return missionSchemeObjectList;
	}

	@Override
	public Integer getMissionCount(CrRole crRole) {
		int count = 0;
		for (MissionStageScheme missionStageScheme : crRole.getMissionStageSchemes()) {
			count += missionStageScheme.getMissions().size();
		}
		return count;
	}

	private List<NameElement> getUserByRoleUsers(List<String> userList) {
		List<NameElement> userElements = Lists.newArrayList();

		List<UserInfo> userInfoList = remoteUserService.getByIdList(userList);
		userInfoList.forEach(userInfo -> {
			if (userList.contains(userInfo.getUserId())) {
				NameElement nameElement = new NameElement();
				nameElement.setName(userInfo.getUserName());
				userElements.add(nameElement);

			}
		});

		return userElements;
	}

	@Override
	public String getUserNamesByRoleUsers(List<String> userList) {
		List<String> userNames = Lists.newArrayList();

		List<UserInfo> userInfoList = remoteUserService.getByIdList(userList);
		userInfoList.forEach(userInfo -> {
			if (userList.contains(userInfo.getUserId())) {
				userNames.add(userInfo.getUserName());
			}
		});

		return JsonUtil.toJsonString(userNames);
	}

	@Override
	public TaskCounter getTaskCounter(String roomId, String studentId) {
		TaskCounter taskCounter = new TaskCounter();

		CrRole crRole = getCrRoleByRoomIdAndUserId(roomId, studentId);
		Integer finished = 0;
		Integer released = 0;
		List<TaskContext> taskContextList = crTaskService.getByRoomIdAndUser(roomId, studentId);

		for (TaskContext taskContext : taskContextList) {
			if (taskContext.isFinished()) {
				finished++;
			} else {
				released++;
			}
		}
		taskCounter.setFinished(finished);
		taskCounter.setReleased(released);
		int countMissions = 0;
		for (MissionStageScheme missionStageScheme : crRole.getMissionStageSchemes()) {
			countMissions += missionStageScheme.getMissions().size();
		}
		taskCounter.setNotReleased(countMissions - finished - released);

		return taskCounter;
	}

	@Override
	public List<UserData> getUserDatas(List<String> userIdList) {
		List<UserData> userDataList = Lists.newArrayList();
		List<UserInfo> userInfoList = remoteUserService.getByIdList(userIdList);
		for (UserInfo userInfo : userInfoList) {
			UserData userData = new UserData();
			userData.setResourceId(userInfo.getUserId());
			userData.setRepName(userInfo.getName());
			userData.setLogo(userInfo.getAvatarUrl());
			userDataList.add(userData);
		}
		return userDataList;
	}

	@Override
	public List<CrRole> selectRbStudentsRoleByRoomId(String roomId) {
		List<CrRole> crRoleList = Lists.newArrayList();

		for (CrRole.RbRoleGroup roleCode : CrRole.RbRoleGroup.studentRoleGroups()) {
			RoleQuery roleQuery = new RoleQuery(roomId, roleCode);
			List<CrRole> crRoles = selectCrRoleByRoomId(roleQuery);
			crRoleList.addAll(crRoles);
		}

		return crRoleList;
	}

	@Override
	public void clearRoomRoles(String roomId) {
		crRoleMapper.batchDeleteByRoomId(roomId);
	}

	@Override
	public List<CrRole> selectCrRoleByRoomIdAndRoleId(String roomId, String roleId) {
		return crRoleMapper.selectCrRoleByRoomIdAndRoleId(roomId, roleId);
	}

	@Override
	public String getNewRoomScheme(String missionsScheme, String roomId) {
		ColorMission<AddTemplateMission> colorMission = JSON.parseObject(missionsScheme,
				new TypeReference<ColorMission<AddTemplateMission>>() {});
		if (null == colorMission || null == colorMission.getSchemeData()) {
			return "[]";
		}

		List<MissionStageScheme> missionStageSchemes = Lists.newArrayList();
		List<String> originMissionIds = Lists.newArrayList();
		// 遍历每个阶段
		for (MissionSchemeObject<AddTemplateMission> schemeDatum : colorMission.getSchemeData()) {

			List<AddTemplateMission> missions = schemeDatum.getMissions();
			// 遍历每个任务
			for (AddTemplateMission mission : missions) {
				originMissionIds.add(mission.getResourceId());
			}
		}

		Map<String, String> originMissionMaps = missionService.copyScheme(originMissionIds,
				UserInfoContext.getUserContext().getUserId(), roomId);

		// 遍历每个阶段设置新的任务id
		for (MissionSchemeObject<AddTemplateMission> schemeDatum : colorMission.getSchemeData()) {
			MissionStageScheme missionStageScheme = new MissionStageScheme();

			List<String> targetMissionIds = Lists.newArrayList();

			// 遍历每个任务设置新的任务id
			for (AddTemplateMission mission : schemeDatum.getMissions()) {
				targetMissionIds.add(originMissionMaps.getOrDefault(mission.getResourceId(),
						mission.getResourceId()));
			}
			missionStageScheme.setStage(schemeDatum.getStage());
			missionStageScheme.setDynamic(schemeDatum.getDynamic());
			missionStageScheme.setMissions(targetMissionIds);

			missionStageSchemes.add(missionStageScheme);
		}

		return JsonUtil.toJsonString(missionStageSchemes);

	}

	@Override
	public List<CrRole> selectRbStudentsRoleByRoomIdAndRoleGroup(String roomId, int roleGroup) {
		if (!CrRole.RbRoleGroup.isRvbStudentRole(roleGroup)) {
			log.error("role does not exist！roleId=[{}]", roleGroup);
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}

		RoleQuery roleQuery = new RoleQuery(roomId, roleGroup);

		return selectCrRoleByRoomId(roleQuery);
	}

	@Override
	public void checkPermission(String roomId, String userId) {
		CrRole crRole = crRoleMapper.getCrRoleByRoomIdAndUserId(roomId, userId);

		if (crRole == null) {
			log.error("user does not have RbRoom Permission！roomId=[{}], userId=[{}]", roomId,
					userId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
	}

	@Override
	public List<CrRole> getRolesByRoomAndGroups(String roomId, Collection<Integer> groups) {
		if (CollectionUtils.isEmpty(groups)) {
			return Lists.newArrayList();
		}
		return crRoleMapper.selectByRoomIdAndGroups(roomId, groups);
	}

	@Override
	public List<TrainTaskReleaseInfoRsp> getTrainTaskReleaseInfoList(String roomId, Integer code) {
		List<TrainTaskReleaseInfoRsp> res = new ArrayList<>();
		// 获取该房间的角色信息
		List<CrRoleSimple> roles =CrRole.getSimpleRoles(this.getRolesByRoomAndGroups(roomId, Collections.singletonList(code)));

		// 获取该房间已下发的任务
		List<CrTask> crTaskList = crTaskService.getByRoomId(roomId);
		Map<String, List<CrTask>> taskMap = crTaskList.stream().collect(Collectors.groupingBy(CrTask::getMissionId));

		List<String> missionIds = CrRoleSimple.getFieldList(CrRoleSimple::getMissionIds, roles);

		List<Mission> allMissions =CollectionUtils.isEmpty(missionIds)?new ArrayList<>(): missionService.listByIds(missionIds);

		List<UserData> allUserInfos = this.getUserDatas(CrRoleSimple.getFieldList(CrRoleSimple::getUserIds,roles));

		//按角色分组
		for (CrRoleSimple roleSimple : roles) {
			TrainTaskReleaseInfoRsp taskReleaseInfoRsp = new TrainTaskReleaseInfoRsp();
			taskReleaseInfoRsp.setRoleName(roleSimple.getName());
			//获取用户列表
			List<UserData> userInfoList = allUserInfos.stream().filter(a->roleSimple.getUserIds()!=null
					&&roleSimple.getUserIds().contains(a.getResourceId())).collect(Collectors.toList());
			taskReleaseInfoRsp.setUserInfoList(userInfoList);
			List<MissionReleaseStageInfo>  missionReleaseStageInfos = new ArrayList<>();
			for (CrRoleMissionSchemeStage stage : roleSimple.getMissionScheme()) {
				missionReleaseStageInfos.add(new MissionReleaseStageInfo(stage,allMissions,taskMap,userInfoList));
			}
			taskReleaseInfoRsp.setMissionStages(missionReleaseStageInfos);
			taskReleaseInfoRsp.initCount();
			taskReleaseInfoRsp.setRoleId(roleSimple.getResourceId());
			res.add(taskReleaseInfoRsp);
		}
		return res;
	}

	@Override
	public List<TrainTaskReleaseInfoRsp> getTrainManual(String roomId, Integer code) {
		List<TrainTaskReleaseInfoRsp> res = new ArrayList<>();
		// 获取该房间的角色信息
		List<CrRoleSimple> roles = CrRole.getSimpleRoles(this.getRolesByRoomAndGroups(roomId, Collections.singletonList(code)));
		// 获取该房间已下发的任务
		List<CrTask> crTaskList = crTaskService.getByRoomId(roomId);
		Map<String, List<CrTask>> taskMap = crTaskList.stream().collect(Collectors.groupingBy(CrTask::getMissionId));

		List<String> missionIds = CrRoleSimple.getFieldList(CrRoleSimple::getMissionIds, roles);
		List<Mission> allMissions = CollectionUtils.isEmpty(missionIds) ? new ArrayList<>() : missionService.listByIds(missionIds);

		List<UserData> allUserInfos = this.getUserDatas(CrRoleSimple.getFieldList(CrRoleSimple::getUserIds,roles));

		//按角色分组
		for (CrRoleSimple roleSimple : roles) {
			if(!roleSimple.getRoleGroup().equals(code)) {
				continue;
			}
			TrainTaskReleaseInfoRsp taskReleaseInfoRsp = new TrainTaskReleaseInfoRsp();
			taskReleaseInfoRsp.setRoleName(roleSimple.getName());
			//获取用户列表
			List<UserData> userInfoList = allUserInfos.stream().filter(a->roleSimple.getUserIds()!=null
					&&roleSimple.getUserIds().contains(a.getResourceId())).collect(Collectors.toList());
			taskReleaseInfoRsp.setUserInfoList(userInfoList);
			List<MissionReleaseStageInfo>  missionReleaseStageInfos = new ArrayList<>();
			for (CrRoleMissionSchemeStage stage : roleSimple.getMissionScheme()) {
				missionReleaseStageInfos.add(new MissionReleaseStageInfo(stage,allMissions,taskMap,userInfoList));
			}
			taskReleaseInfoRsp.setMissionStages(missionReleaseStageInfos);
			taskReleaseInfoRsp.initCount();
			res.add(taskReleaseInfoRsp);
		}
		return res;
	}

	@Override
	public void combineRoleConst(DashboardInfo dashboardInfo) {
		List<DashboardRole> roleConst = new ArrayList<DashboardRole>() {{
			DashboardRole red = new DashboardRole();
			red.setRoleType(CrRole.RbRoleGroup.RED.getEnglish());
			red.setRole(CrRole.RbRoleGroup.RED.getMsg());
			red.setRoleCode(CrRole.RbRoleGroup.RED.getCode());
			add(red);

			DashboardRole blue = new DashboardRole();
			blue.setRoleType(CrRole.RbRoleGroup.BLUE.getEnglish());
			blue.setRole(CrRole.RbRoleGroup.BLUE.getMsg());
			blue.setRoleCode(CrRole.RbRoleGroup.BLUE.getCode());
			add(blue);

			DashboardRole white = new DashboardRole();
			white.setRoleType(CrRole.RbRoleGroup.WHITE.getEnglish());
			white.setRole(CrRole.RbRoleGroup.WHITE.getMsg());
			white.setRoleCode(CrRole.RbRoleGroup.WHITE.getCode());
			add(white);

			DashboardRole teamTeacher = new DashboardRole();
			teamTeacher.setRoleType(CrRole.TeamRoleGroup.TEACHER.getEnglish());
			teamTeacher.setRole(CrRole.TeamRoleGroup.TEACHER.getMsg());
			teamTeacher.setRoleCode(CrRole.TeamRoleGroup.TEACHER.getCode());
			add(teamTeacher);

			DashboardRole teamStudent = new DashboardRole();
			teamStudent.setRoleType(CrRole.TeamRoleGroup.STUDENT.getEnglish());
			teamStudent.setRole(CrRole.TeamRoleGroup.STUDENT.getMsg());
			teamStudent.setRoleCode(CrRole.TeamRoleGroup.STUDENT.getCode());
		}};
		dashboardInfo.setRoleConst(roleConst);
	}

}
