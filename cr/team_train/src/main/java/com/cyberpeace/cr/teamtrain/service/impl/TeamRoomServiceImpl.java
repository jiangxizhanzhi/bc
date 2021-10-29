package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.teamtrain.dao.entity.CrRole;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.teamtrain.dao.mapper.RoleReq;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.model.room.TeamRoomData;
import com.cyberpeace.cr.teamtrain.model.room.TeamRoomOutput;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;

/**
 *
 */
@Service
public class TeamRoomServiceImpl extends ServiceImpl<TeamRoomMapper, TeamRoom> implements TeamRoomService {

	@Autowired
	private TeamRoomMapper teamRoomMapper;

	@Autowired
	private CrRoleMapper crRoleMapper;

	@Override
	public TeamRoomOutput getTeamRoomOutputById(String resourceId) {
		TeamRoomData teamRoomdata = teamRoomMapper.getTeamRoomOutputById(resourceId);

		RoleReq roleReq = new RoleReq();
		roleReq.setRoomId(teamRoomdata.getResourceId());
		List<CrRole> crRoles = crRoleMapper.selectCrRoleByRoomId(roleReq);

		TeamRoomOutput teamRoomOutput = getTeamRoomOutputFromTeamRoomData(teamRoomdata);

		teamRoomOutput.setTrainingRoleData(crRoles);
		return teamRoomOutput;
	}

	private TeamRoomOutput getTeamRoomOutputFromTeamRoomData(TeamRoomData teamRoomdata) {
		TeamRoomOutput teamRoomOutput = BeanUtils.copy(teamRoomdata, TeamRoomOutput.class);

		teamRoomOutput
				.setPreSkill(JsonUtil.toJsonObjectArray(teamRoomdata.getPreSkill(), String.class));
		teamRoomOutput.setTrainSkill(
				JsonUtil.toJsonObjectArray(teamRoomdata.getTrainSkill(), String.class));

		return teamRoomOutput;
	}
}
