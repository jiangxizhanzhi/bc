package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrScoreService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineData;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;
import com.cyberpeace.cr.crbase.service.context.statistics.TaskStatisticsInfo;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTeacherService;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.IdNameType;

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
	private LocalSceneService localSceneService;

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
	public Boolean ifReplayEnabled(String roomId) {
		TeamRoom room = teamRoomService.getById(roomId);
		if (!room.isEnded() || StringUtils.isNotBlank(room.getLocalSceneId())) {
			log.info("conditions not satisfied, cant replay now");
			throw new BusinessException(CrBusinessErrorCode.REPLAY_NOT_ENABLED);
		}
		return true;
	}
}
