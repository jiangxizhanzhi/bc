package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrScoreService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListReq;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListRsp;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RbRefereeServiceImpl implements RbRefereeService {

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private CrScoreService crScoreService;

	@Override
	public List<EvaluationListRsp> getEvaluationList(EvaluationListReq req) {
		return crTaskService.getEvaluationList(req);
	}

	@Override
	public List<StudentScoreListRsp> getScoreList(String roomId) {
		// get roles
		List<CrRole> roleList = rbRoomService.getStudentsRoles(roomId);

		return crScoreService.getScoreListRsp(roomId, roleList);
	}
}
