package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cyberpeace.common.model.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.entity.MissionCategory;
import com.cyberpeace.cr.crbase.dao.mapper.MissionCategoryMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.MissionStageScheme;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamScore;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamScoreMapper;
import com.cyberpeace.cr.teamtrain.service.TeamScoreService;
import com.cyberpeace.cr.teamtrain.service.context.MissionsStatistics;
import com.cyberpeace.cr.teamtrain.service.context.TaskCounter;
import com.google.common.collect.Maps;

/**
 *
 */
@Service
public class TeamScoreServiceImpl extends ServiceImpl<TeamScoreMapper, TeamScore> implements TeamScoreService {

	@Autowired
	TeamScoreMapper teamScoreMapper;

	@Autowired
	MissionCategoryMapper missionCategoryMapper;

	@Autowired
	MissionService missionService;

	@Autowired
	CrRoleService crRoleService;

	@Autowired
	CrTaskService crTaskService;

	@Override
	public TeamScore getByRoomIdAndUser(String roomId, String userId) {
		return teamScoreMapper.getByRoomIdAndUser(roomId, userId);
	}

	@Override
	public TaskCounter getTaskCounter(String roomId, String studentId) {
		TaskCounter taskCounter = new TaskCounter();

		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(roomId, studentId);
		List<MissionStageScheme> missionStageSchemes = JsonUtil
				.toJsonObjectArray(crRole.getMissionScheme(), MissionStageScheme.class);
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
		for (MissionStageScheme missionStageScheme : missionStageSchemes) {
			countMissions += missionStageScheme.getMissions().size();
		}
		taskCounter.setNotReleased(countMissions - finished - released);

		return taskCounter;
	}

	@Override
	public MissionsStatistics getMissionsStatistics(String roomId) {
		MissionsStatistics missionsStatistics = new MissionsStatistics();

		RoleQuery roleQuery = new RoleQuery(roomId, CrRole.TeamRoleGroupCode.STUDENT.getCode());

		List<CrRole> crRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);

		Integer missionCount = 0;
		for (CrRole crRole : crRoleList) {
			Integer roleMissionCount = crRoleService.getMissionCount(crRole);
			List<String> userList = JsonUtil.toJsonObjectArray(crRole.getUserIds(), String.class);
			missionCount += roleMissionCount * userList.size();
		}

		missionsStatistics
				.setUnpublishedProportion(missionCount - crTaskService.getByRoomId(roomId).size());
		missionsStatistics.setNotSubmitProportion(crTaskService.countUnsubmitted(roomId));
		missionsStatistics.setNotFinishProportion(crTaskService.countUnfinished(roomId));
		missionsStatistics.setFinishProportion(crTaskService.countFinished(roomId));
		return missionsStatistics;
	}

	@Override
	public List<TeamScore> selectByRoomIdAndUserIds(String roomId, List<String> userIds) {
		return teamScoreMapper.selectByRoomIdAndUserIds(roomId, userIds);
	}

	@Override
	public Map<String, Integer> getMissionsCategory(String roomId) {
		Map<String, Integer> missionsCategory = Maps.newHashMap();

		List<Mission> missionList = missionService.batchGetByRoom(roomId);

		List<MissionCategory> missionCategories = missionService.listCategories();

 		Map<String, String> missionCategoryMap = missionCategories.stream()
				.collect(Collectors.toMap(MissionCategory::getResourceId, bo -> bo.getName()));

		for (Mission mission : missionList) {
			Integer count = missionsCategory.getOrDefault(mission.getCategoryId(), 0) + 1;
			missionsCategory.put(missionCategoryMap.get(mission.getCategoryId()), count);
		}

		return missionsCategory;
	}
}
