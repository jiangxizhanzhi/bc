package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrScoreService;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTeacherService;
import com.cyberpeace.cs.service.context.IdNameType;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeamTeacherServiceImpl implements TeamTeacherService {

	@Autowired
	private TeamRoomService teamRoomService;

	@Autowired
	private CrScoreService crScoreService;

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
}
