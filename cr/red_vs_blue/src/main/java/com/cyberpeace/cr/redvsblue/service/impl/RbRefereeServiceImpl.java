package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrScoreService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListReq;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListRsp;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;
import com.cyberpeace.cr.redvsblue.service.RbRefereeService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.context.evaluation.RbRoleIdNameType;

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

	@Override
	public List<RbRoleIdNameType> getRoleList(String roomId) {
		// 获取学员角色
		List<CrRole> roles = rbRoomService.getStudentsRoles(roomId);
		List<RbRoleIdNameType> res = Lists.newArrayList();
		for (CrRole role : roles) {
			RbRoleIdNameType rbRole = new RbRoleIdNameType();
			rbRole.setId(role.getResourceId());
			rbRole.setName(role.getName());
			rbRole.setType(role.getRoleGroup());
			rbRole.setPrefix();

			res.add(rbRole);
		}
		// 红方角色放在前面
		res.sort(Comparator.comparing(RbRoleIdNameType::getType));
		return res;
	}
}
