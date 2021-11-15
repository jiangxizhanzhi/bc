package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.MissionStageScheme;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamScore;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamScoreMapper;
import com.cyberpeace.cr.teamtrain.service.TeamScoreService;
import com.cyberpeace.cr.teamtrain.service.context.MissionsStatistics;
import com.cyberpeace.cr.teamtrain.service.context.TaskCounter;

/**
 *
 */
@Service
public class TeamScoreServiceImpl extends ServiceImpl<TeamScoreMapper, TeamScore> implements TeamScoreService {

	@Autowired
	TeamScoreMapper teamScoreMapper;

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
		taskCounter.setNotReleased(missionStageSchemes.size() - finished - released);

		return taskCounter;
	}

	@Override
	public MissionsStatistics getMissionsStatistics(String roomId) {
		MissionsStatistics missionsStatistics = new MissionsStatistics();

		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(roomId);
		roleQuery.setRoleGroup(CrRole.RoleGroupCodeInteger.STUDENT.getValue());
		List<CrRole> crRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);

		Integer missionCount = 0;
		for (CrRole crRole : crRoleList) {
			Integer roleMissionCount = crRoleService.getMissionCount(crRole);
			List<String> userList = JsonUtil.toJsonObjectArray(crRole.getUserIds(), String.class);
			missionCount += roleMissionCount * userList.size();
		}

		missionsStatistics
				.setNotFinishProportion(missionCount - crTaskService.getByRoomId(roomId).size());
		// missionsStatistics.setNotSubmitProportion(crTaskService.countNotSubmitted(roomId));

		return null;
	}

	@Override
	public List<TeamScore> selectByRoomIdAndUserIds(String roomId, List<String> userIds) {
		return teamScoreMapper.selectByRoomIdAndUserIds(roomId, userIds);
	}
}
