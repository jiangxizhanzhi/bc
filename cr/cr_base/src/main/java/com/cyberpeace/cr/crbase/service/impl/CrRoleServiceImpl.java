package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.role.*;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * cr角色服务impl
 *
 * @author cyberpeace
 * @date 2023/05/06
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

	/**
	 * 通过id获取
	 *
	 * @param roleId 角色id
	 * @return {@link CrRole}
	 */
	@Override
	public CrRole getById(String roleId) {
		CrRole role = crRoleMapper.selectById(roleId);
		if (role == null) {
			log.error("role does not exist！roleId=[{}]", roleId);
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}
		return role;
	}

	/**
	 * 获取角色
	 *
	 * @param roomId    房间id
	 * @param roleGroup 角色集团
	 * @return {@link CrRoleContext}<{@link MissionContext}>
	 */
	@Override
	public CrRoleContext<MissionContext> getRoles(String roomId, CrRole.RoleGroup roleGroup) {
		CrRoleContext<MissionContext> crRoleContext = new CrRoleContext();
		RoleQuery roleQuery = new RoleQuery(roomId, roleGroup);

		List<CrRole> crRoles = crRoleMapper.selectCrRoleByRoomId(roleQuery);
		if (crRoles == null) {
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

	/**
	 * 通过房间获取角色
	 *
	 * @param roomId       房间id
	 * @param businessType 业务类型
	 * @return {@link List}<{@link CrRole}>
	 */
	@Override
	public List<CrRole> getRolesByRoom(String roomId, Integer businessType) {
		return crRoleMapper.getRolesByRoom(roomId, businessType);
	}

	/**
	 * 获取基地任务通过角色
	 *
	 * @param crRole cr角色
	 * @return {@link List}<{@link MissionSchemeObject}<{@link MissionContext}>>
	 */
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

	/**
	 * 通过用户id获取房间id
	 *
	 * @param userId 用户id
	 * @return {@link List}<{@link String}>
	 */
	@Override
	public List<String> getRoomIdsByUserId(String userId) {
		return crRoleMapper.getRoomIdsByUserId(userId);

	}

	/**
	 * 通过房间id获取cr角色
	 *
	 * @param roleQuery 角色查询
	 * @return {@link List}<{@link CrRole}>
	 */
	@Override
	public List<CrRole> selectCrRoleByRoomId(RoleQuery roleQuery) {
		return crRoleMapper.selectCrRoleByRoomId(roleQuery);
	}

	/**
	 * 获取雷达
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@link List}<{@link Integer}>
	 */
	@Override
	public List<Integer> getRadar(String roomId, String userId) {

		CrRole crRole = getCrRoleByRoomIdAndUserId(roomId, userId);

		List<MissionSchemeObject<MissionContext>> missionSchemeObjectList = getBaseMissionByRole(
				crRole);
		// 获取当前学员的每项总分
		List<Integer> allPoints = getPoints();

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
		List<Integer> successPoints = getPoints();

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

		List<Integer> radarPoints = getPoints();

		for (int i = 0; i < successPoints.size(); i++) {
			int radarPoint = 0;
			if (!allPoints.get(i).equals(0)) {
				radarPoint = successPoints.get(i) * 100 / allPoints.get(i);
			}
			radarPoints.set(i, radarPoint);
		}

		return radarPoints;
	}

	/**
	 * 获取点
	 *
	 * @return {@link List}<{@link Integer}>
	 */
	private List<Integer> getPoints() {
		List<Integer> points = Lists.newArrayList();
		int skillPointsSize = CrCommonConstants.SKILL_POINTS_SIZE;
		for (int i = 0; i < skillPointsSize; i++) {
			points.add(0);
		}
		return points;
	}

	/**
	 * 获取cr角色通过房间id和用户id
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@link CrRole}
	 */
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

	/**
	 * 获取cr角色任务通过房间id和用户id
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@link List}<{@link MissionSchemeObject}<{@link MissionContext}>>
	 */
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

	/**
	 * 获取任务数
	 *
	 * @param crRole cr角色
	 * @return {@link Integer}
	 */
	@Override
	public Integer getMissionCount(CrRole crRole) {
		int count = 0;
		for (MissionStageScheme missionStageScheme : crRole.getMissionStageSchemes()) {
			count += missionStageScheme.getMissions().size();
		}
		return count;
	}

	/**
	 * 通过角色用户获取用户
	 *
	 * @param userList 用户列表
	 * @return {@link List}<{@link NameElement}>
	 */
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

	/**
	 * 通过角色用户获取用户名字
	 *
	 * @param userList 用户列表
	 * @return {@link String}
	 */
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

	/**
	 * 获取任务计数器
	 *
	 * @param roomId    房间id
	 * @param studentId 学生证
	 * @return {@link TaskCounter}
	 */
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

	/**
	 * 获取用户数据
	 *
	 * @param userIdList 用户id列表
	 * @return {@link List}<{@link UserData}>
	 */
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

	/**
	 * 通过房间id获取rb学员角色
	 *
	 * @param roomId 房间id
	 * @return {@link List}<{@link CrRole}>
	 */
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

	/**
	 * 清除房间角色
	 *
	 * @param roomId 房间id
	 */
	@Override
	public void clearRoomRoles(String roomId) {
		crRoleMapper.batchDeleteByRoomId(roomId);
	}

	/**
	 * 通过房间id和角色id获取cr角色
	 *
	 * @param roomId 房间id
	 * @param roleId 角色id
	 * @return {@link List}<{@link CrRole}>
	 */
	@Override
	public List<CrRole> selectCrRoleByRoomIdAndRoleId(String roomId, String roleId) {
		return crRoleMapper.selectCrRoleByRoomIdAndRoleId(roomId, roleId);
	}

	/**
	 * 获取新房间计划
	 *
	 * @param missionsScheme 任务计划
	 * @param roomId         房间id
	 * @return {@link String}
	 */
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

	/**
	 * 通过房间id和角色集团获取rb学生角色
	 *
	 * @param roomId    房间id
	 * @param roleGroup 角色集团
	 * @return {@link List}<{@link CrRole}>
	 */
	@Override
	public List<CrRole> selectRbStudentsRoleByRoomIdAndRoleGroup(String roomId, int roleGroup) {
		if (!CrRole.RbRoleGroup.isRvbStudentRole(roleGroup)) {
			log.error("role does not exist！roleId=[{}]", roleGroup);
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}

		RoleQuery roleQuery = new RoleQuery(roomId, roleGroup);
		List<CrRole> crRoles = selectCrRoleByRoomId(roleQuery);

		return crRoles;
	}

	/**
	 * 检查权限
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 */
	@Override
	public void checkPermission(String roomId, String userId) {
		CrRole crRole = crRoleMapper.getCrRoleByRoomIdAndUserId(roomId, userId);

		if (crRole == null) {
			log.error("user does not have RbRoom Permission！roomId=[{}], userId=[{}]", roomId,
					userId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
	}

}
