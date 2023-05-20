package com.cyberpeace.cr.teamtrain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrScoreService;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;

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
}
