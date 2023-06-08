package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.dao.entity.CrBadge;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineData;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;
import com.cyberpeace.cr.crbase.service.context.statistics.TaskStatisticsInfo;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTeacherService;
import com.cyberpeace.cs.service.context.IdNameType;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeamTeacherServiceImpl implements TeamTeacherService {

	@Autowired
	private TeamRoomService teamRoomService;

	@Autowired
	private CrScoreService crScoreService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private CrBadgeService crBadgeService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private CrRoleService crRoleService;

	@Override
	public List<StudentScoreListRsp> getScoreList(String roomId) {
		// get roles
		List<CrRole> roleList = teamRoomService.getStudentsRoles(roomId);

		return crScoreService.getScoreListRsp(roomId, roleList);
	}

	@Override
	public List<IdNameType> getRoleList(String roomId) {
		List<CrRole> roles = teamRoomService.getStudentsRoles(roomId);
		List<IdNameType> res = Lists.newArrayList();
		for (CrRole role : roles) {
			IdNameType teamRole = new IdNameType();
			teamRole.setId(role.getResourceId());
			teamRole.setName(role.getName());
			teamRole.setType(role.getRoleGroup());

			res.add(teamRole);
		}
		return res;
	}

	@Override
	public TimeLineData getReplayTimeline(String roomId, String userId) {
		return teamRoomService.getTimeLine(roomId, userId);
	}

	@Override
	public TaskStatisticsInfo getTaskStatisticsInfo(String roomId) {
		return crTaskService.getTaskStatisticsInfoByRoomId(roomId);
	}

	@Override
	public String getReplaySceneId(String roomId) {
		TeamRoom room = teamRoomService.getById(roomId);
		if (!room.isEnded()) {
			log.info("training not ended: {}", roomId);
			throw new BusinessException(CrBusinessErrorCode.PLZ_REPLAY_AFTER_TRAINING_ENDS);
		}
		if (StringUtils.isNotBlank(room.getLocalSceneId())) {
			log.info("scene not stopped: {}", roomId);
			throw new BusinessException(CrBusinessErrorCode.PLZ_REPLAY_AFTER_SCENE_IS_STOPPED);
		}
		String res = room.getReplaySceneId();
		if (StringUtils.isBlank(res)) {
			log.info("This training [{}] hasn't start a scene, no scene to replay.", roomId);
			throw new BusinessException(CrBusinessErrorCode.REPLAY_SCENE_NOT_EXIST);
		}
		return res;
	}

	@Override
	public void notifySubmission(String missionId) {
		// get mission
		Mission mission = missionService.getById(missionId);
		// get roomId
		String roomId = mission.getRoomId();
		// get teacher
		List<String> teacherIds = this.getTeacherIdsByRoomId(roomId);
		crBadgeService.createBatch(roomId, CrBadge.BadgeType.NEW_SUBMIT.getCode(), teacherIds);
		crTaskService.pushMissionWsToUsers(roomId, teacherIds, MissionWsCode.MISSION_SUBMISSION);
	}

	private List<String> getTeacherIdsByRoomId(String roomId) {
		List<CrRole> roles = crRoleService.getRolesByRoomAndGroups(roomId,
				Lists.newArrayList(CrRole.TeamRoleGroup.TEACHER.getCode()));
		if (roles.isEmpty()) {
			return Lists.newArrayList();
		}
		return roles.get(0).getUserIdList();
	}
}
