package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskSubmitMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.redvsblue.controller.XYData;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTimeManagement;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.service.RbRoomScoreService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbTimeManagementService;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomData;
import com.cyberpeace.cr.redvsblue.service.context.RoleUserIds;
import com.cyberpeace.cr.redvsblue.service.context.TimeData;
import com.cyberpeace.cr.redvsblue.service.context.mission.ObjWithTime;
import com.cyberpeace.cr.redvsblue.service.context.mission.RankData;
import com.cyberpeace.cr.redvsblue.service.context.mission.RoleScore;
import com.cyberpeace.cr.redvsblue.service.context.mission.UserCounter;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RbRoomScoreServiceImpl extends ServiceImpl<RbRoomMapper, RbRoom> implements RbRoomScoreService {

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private RbTimeManagementService rbTimeManagementService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private CrTaskSubmitMapper crTaskSubmitMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public List<RoleScore> getRoleScoreInfo(String roomId) {
		RoleQuery roleQuery = new RoleQuery(roomId, CrRole.RbRoleGroupCode.RED.getCode());
		List<CrRole> crRedRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

		List<String> redRoles = Lists.newArrayList();
		for (CrRole crRole : crRedRoles) {
			redRoles.add(crRole.getResourceId());
		}

		roleQuery = new RoleQuery(roomId, CrRole.RbRoleGroupCode.BLUE.getCode());
		List<CrRole> crBlueRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

		List<String> blueRoles = Lists.newArrayList();
		for (CrRole crRole : crBlueRoles) {
			blueRoles.add(crRole.getResourceId());
		}

		List<CrTask> taskList = crTaskService.getByRoomId(roomId);

		Integer redScore = 0;
		Integer redFinishedTaskCount = 0;
		Integer redTaskCount = 0;

		Integer blueScore = 0;
		Integer blueFinishedTaskCount = 0;
		Integer blueTaskCount = 0;

		for (CrTask task : taskList) {
			if (redRoles.contains(task.getRoleId())) {
				redTaskCount++;
				if (task.isFinished()) {
					redFinishedTaskCount++;
					redScore += task.getScore();
				}

			} else {
				blueTaskCount++;
				if (task.isFinished()) {
					blueFinishedTaskCount++;
					blueScore += task.getScore();
				}
			}
		}

		RoleScore redRoleScore = new RoleScore(CrRole.RbRoleGroupCode.RED.getDesc(),
				CrRole.RbRoleGroupCode.RED.getCode(), redScore, redFinishedTaskCount, redTaskCount);
		RoleScore blueRoleScore = new RoleScore(CrRole.RbRoleGroupCode.BLUE.getDesc(),
				CrRole.RbRoleGroupCode.BLUE.getCode(), blueScore, blueFinishedTaskCount,
				blueTaskCount);

		List<RoleScore> roleScores = Lists.newArrayList();
		roleScores.add(redRoleScore);
		roleScores.add(blueRoleScore);

		return roleScores;
	}

	@Override
	public TimeData getRbTimeData(String roomId) {
		RbRoomData rbRoomData = rbRoomService.getRbRoomDataByRoomId(roomId);

		RbTimeManagement rbTimeManagement = rbTimeManagementService.getByRoomId(roomId);

		Date endtime = rbRoomData.getEndTime();

		// 进行中
		TimeData timeDate = new TimeData(1, rbTimeManagement.getCreateTime(), new Date(), endtime);
		// 未开始
		if (rbTimeManagement == null) {
			timeDate = new TimeData(0, rbRoomData.getStartTime(), endtime, endtime);
		}
		// 已结束
		if (rbTimeManagement.isStopped()) {
			timeDate = new TimeData(2, rbTimeManagement.getCreateTime(),
					rbTimeManagement.getUpdateTime(), endtime);
		}

		return timeDate;
	}

	@Override
	public XYData getRbMissionCategory(String roomId, Integer roleType) {
		// 获取场景实例下的任务hash
		RoleUserIds roleUserIds = getRoleInfo(roomId, roleType);
		List<TaskContext> taskContexts = crTaskService.getTaskContextByRoomIdAndUsers(roomId,
				roleUserIds.getUserIds());

		Map<String, Integer> missionsCategory = Maps.newHashMap();

		List<MissionCategory> missionCategories = missionService.listCategories();

		Map<String, String> missionCategoryMap = missionCategories.stream()
				.collect(Collectors.toMap(MissionCategory::getResourceId, bo -> bo.getName()));
		for (TaskContext taskContext : taskContexts) {
			Mission mission = taskContext.getMission();
			Integer count = missionsCategory.getOrDefault(mission.getCategoryId(), 0) + 1;
			missionsCategory.put(missionCategoryMap.get(mission.getCategoryId()), count);
		}

		List<String> xData = Lists.newArrayList();
		List<Integer> yData = Lists.newArrayList();

		for (MissionCategory missionCategory : missionCategories) {
			xData.add(missionCategory.getName());
			yData.add(missionsCategory.getOrDefault(missionCategory.getName(), 0));
		}

		XYData xyData = new XYData(xData, yData);
		return xyData;
	}

	@Override
	public JSONObject getRbMembersRanking(String roomId, Integer roleType) {

		List<RankData> rankDataList = getUserScoreMissionData(roomId, roleType);

		Collections.sort(rankDataList);

		int ranking = 1;
		Integer total = 0;

		for (RankData rankData : rankDataList) {
			rankData.setRanking(ranking++);
			total += rankData.getScore();
		}

		JSONObject json = new JSONObject();
		json.put("ranking_data", rankDataList);
		json.put("time_stamp", System.currentTimeMillis() / 1000);
		json.put("total_score", total);

		return json;
	}

	@Override
	public JSONObject getRbMissionEvaluation(String roomId, Integer roleType) {

		RoleUserIds roleUserIds = getRoleInfo(roomId, roleType);

		Integer unreportedCount = crTaskService.countUnreportedByUsers(roomId,
				roleUserIds.getUserIds());

		// 获取学员提交数据
		List<CrTaskSubmit> crTaskSubmits = crTaskSubmitMapper.selectByRoomIdAndUsers(roomId,
				roleUserIds.getUserIds());
		Integer waitCount = 0;
		Integer successCount = 0;
		Integer failCount = 0;
		Integer rejectCount = 0;

		for (CrTaskSubmit taskSubmit : crTaskSubmits) {

			if (CrTaskSubmit.EvaluateStatus.SUCCESS.getCode()
					.equals(taskSubmit.getEvaluateStatus())) {
				successCount++;
			} else if (CrTaskSubmit.EvaluateStatus.FAIL.getCode()
					.equals(taskSubmit.getEvaluateStatus())) {
				failCount++;
			} else if (CrTaskSubmit.EvaluateStatus.REJECT.getCode()
					.equals(taskSubmit.getEvaluateStatus())) {
				rejectCount++;
			} else {
				waitCount++;
			}
		}
		List<String> crTasks = crTaskService.getIdsByUserIds(roomId, roleUserIds.getUserIds());

		JSONObject json = new JSONObject();

		json.put("total", crTasks.size());
		json.put("wait", waitCount);
		json.put("success", successCount);
		json.put("fail", failCount);
		json.put("reject", rejectCount);
		json.put("unreported", unreportedCount);

		return json;
	}

	@Override
	public JSONObject getRbLeaderBoard(String roomId, Integer roleType) {

		List<RankData> rankDataList = getUserScoreMissionData(roomId, roleType);

		Collections.sort(rankDataList);

		int ranking = 1;
		List<RankData> missionDataList = Lists.newArrayList();

		for (RankData rankData : rankDataList) {
			rankData.setRanking(ranking++);

			RankData missionData = BeanUtils.deepCopy(rankData, RankData.class);
			missionData.setSortType("missionRatio");

			missionDataList.add(missionData);
		}

		Collections.sort(missionDataList);

		ranking = 1;
		for (RankData missionData : missionDataList) {
			missionData.setRanking(ranking++);
		}
		JSONObject json = new JSONObject();

		json.put("ranking_data", rankDataList);
		json.put("time_stamp", System.currentTimeMillis() / 1000);
		json.put("mission_data", missionDataList);

		return json;
	}

	@Override
	public JSONObject getRbScoreProgress(String roomId, Integer roleType) {
		// 获取实例实际开始时间和理论结束时间
		TimeData timeData = getRbTimeData(roomId);

		List<Integer> yData = getScoreProgress(timeData, roomId, roleType);

		JSONObject json = new JSONObject();

		json.put("status", timeData.getStatus());
		json.put("time_stamp", System.currentTimeMillis() / 1000);
		json.put("start_time", timeData.getStartTime());
		json.put("running", timeData.getRunning());
		json.put("total_score", timeData.getAccumulatedValue());
		json.put("x_data", timeData.getXData());
		json.put("y_data", yData);

		return json;

	}

	private List<Integer> getScoreProgress(TimeData timeData, String roomId, Integer roleType) {
		// 获取角色组数据
		RoleUserIds roleUserIds = getRoleInfo(roomId, roleType);

		// 一次取出所有用户的得分纪录
		List<CrTask> finishedTasks = crTaskService.getFinishedByRoomIdAndUserIds(roomId,
				roleUserIds.getUserIds());

		List<ObjWithTime> taskTimes = finishedTasks.stream().map(taskContext -> {
			ObjWithTime objWithTime = new ObjWithTime();
			objWithTime.setTime(taskContext.getLastSubmitTime());
			objWithTime.setValue(taskContext.getScore());

			return objWithTime;
		}).collect(Collectors.toList());

		List<Integer> yData = timeData.calculateCountByTime(taskTimes);

		return yData;
	}

	@Override
	public JSONObject getAllRbScoreProgress(String roomId, Integer roleType) {

		// 获取实例实际开始时间和理论结束时间
		TimeData timeData = getRbTimeData(roomId);

		JSONObject json = new JSONObject();

		json.put("status", timeData.getStatus());
		json.put("time_stamp", System.currentTimeMillis() / 1000);
		json.put("start_time", timeData.getStartTime());
		json.put("running", timeData.getRunning());
		json.put("total_score", 0);
		json.put("x_data", timeData.getXData());

		List<Integer> yRedData = getScoreProgress(timeData, roomId,
				CrRole.RbRoleGroupCode.RED.getCode());

		json.put("red_total_score", timeData.getAccumulatedValue());
		json.put("y_red_score", yRedData);

		List<Integer> yBlueData = getScoreProgress(timeData, roomId,
				CrRole.RbRoleGroupCode.BLUE.getCode());

		json.put("blue_total_score", timeData.getAccumulatedValue());
		json.put("y_blue_score", yBlueData);

		return json;
	}

	@Override
	public JSONObject getRbMissionProgress(String roomId, Integer roleType) {

		// 获取角色组数据
		RoleUserIds roleUserIds = getRoleInfo(roomId, roleType);

		// 获取实例实际开始时间和理论结束时间
		TimeData timeData = getRbTimeData(roomId);

		// 获取组内已下发的任务
		List<TaskContext> crTasks = crTaskService.getTaskContextByRoomIdAndUsers(roomId,
				roleUserIds.getUserIds());

		List<ObjWithTime> taskTimes = crTasks.stream().map(taskContext -> {
			ObjWithTime objWithTime = new ObjWithTime();
			objWithTime.setTime(taskContext.getReleaseTime());

			return objWithTime;
		}).collect(Collectors.toList());

		List<Integer> totalProgressYData = timeData.calculateCountByTime(taskTimes);

		// 获取已完成的任务
		List<CrTask> finishedTasks = crTaskService.getFinishedByRoomIdAndUserIds(roomId,
				roleUserIds.getUserIds());

		List<ObjWithTime> finishedTimes = finishedTasks.stream().map(crTask -> {
			ObjWithTime objWithTime = new ObjWithTime();
			objWithTime.setTime(crTask.getLastSubmitTime());

			return objWithTime;
		}).collect(Collectors.toList());

		List<Integer> completedProgressYData = timeData.calculateCountByTime(finishedTimes);

		JSONObject json = new JSONObject();

		json.put("total_mission", crTasks.size());
		json.put("completed_mission", finishedTasks.size());
		json.put("total_members", roleUserIds.getUserIds().size());
		json.put("x_data", timeData.getXData());
		json.put("y_total", totalProgressYData);
		json.put("y_complete", completedProgressYData);

		return json;
	}

	private List<RankData> getUserScoreMissionData(String roomId, Integer roleType) {

		// 获取角色组数据
		RoleQuery roleQuery = new RoleQuery(roomId, roleType);
		List<CrRole> studentRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

		Map<String, CrRole> rolesMap = StreamApiUtils.toMap(studentRoles, CrRole::getResourceId,
				pc -> pc);

		// 获取角色组用户
		List<String> userIds = Lists.newArrayList();
		for (CrRole crRole : studentRoles) {
			userIds.addAll(JsonUtil.toJsonObjectArray(crRole.getUserIds(), String.class));

		}
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);

		// 获取所有用户的评估数据
		List<CrTask> finishedTasks = crTaskService.getFinishedByRoomIdAndUserIds(roomId, userIds);
		Map<String, UserCounter> userCounterHashMap = Maps.newHashMap();

		for (CrTask crTask : finishedTasks) {

			String submitUser = crTask.getUserId();
			// 获取用户每一个已完成的任务
			UserCounter userCounter = userCounterHashMap.getOrDefault(submitUser,
					new UserCounter());

			// 更新用户的分数和完成任务数量
			userCounter.getRemoteMissionIds().add(crTask.getMissionId());
			userCounter.setScore(userCounter.getScore() + crTask.getScore());
			userCounter.setRoleId(crTask.getRoleId());

			userCounterHashMap.put(submitUser, userCounter);

		}
		List<RankData> rankDataList = Lists.newArrayList();

		for (String key : userCounterHashMap.keySet()) {
			UserCounter userCounter = userCounterHashMap.get(key);
			Integer roleMissionCount = crRoleService
					.getMissionCount(rolesMap.get(userCounter.getRoleId()));
			Integer missionRatio = userCounter.getRemoteMissionIds().size() * 100
					/ roleMissionCount;

			RankData rankData = new RankData();

			rankData.setName(userInfoMap.get(key).getName());
			rankData.setLogo(userInfoMap.get(key).getAvatarUrl());
			rankData.setScore(userCounter.getScore());
			rankData.setCompletedMission(userCounter.getRemoteMissionIds().size());
			rankData.setMissionRatio(missionRatio);

			rankDataList.add(rankData);
		}

		return rankDataList;
	}

	private RoleUserIds getRoleInfo(String roomId, Integer roleType) {
		RoleQuery roleQuery = new RoleQuery(roomId, roleType);
		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

		if (crRoles == null) {
			log.error("role does not exist！roleType=[{}]", roleType);
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}
		List<String> roleIds = Lists.newArrayList();
		List<String> userIds = Lists.newArrayList();

		for (CrRole crRole : crRoles) {
			roleIds.add(crRole.getResourceId());
			userIds.addAll(JsonUtil.toJsonObjectArray(crRole.getUserIds(), String.class));
		}
		RoleUserIds roleUserIds = new RoleUserIds();
		roleUserIds.setRoleIds(roleIds);
		roleUserIds.setUserIds(userIds);

		return roleUserIds;
	}

}
