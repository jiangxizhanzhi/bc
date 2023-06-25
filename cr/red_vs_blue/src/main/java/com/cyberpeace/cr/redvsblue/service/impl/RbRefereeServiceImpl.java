package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.dao.entity.CrBadge;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListReq;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListRsp;
import com.cyberpeace.cr.crbase.service.context.scene.MonitorSession;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.RbRefereeService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.context.evaluation.RbRoleIdNameType;
import com.google.common.collect.Lists;

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

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private CrBadgeService crBadgeService;

	@Autowired
	private CrCommonService crCommonService;

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

	@Override
	public void notifySubmission(String missionId) {
		// get mission
		Mission mission = missionService.getById(missionId);
		// get roomId
		String roomId = mission.getRoomId();
		// get teacher
		List<String> refereeIds = this.getRefereeIdsByRoomId(roomId);
		crBadgeService.createBatch(roomId, CrBadge.BadgeType.NEW_SUBMIT.getCode(), refereeIds);
		crTaskService.pushMissionWsToUsers(roomId, refereeIds, MissionWsCode.MISSION_SUBMISSION);
	}

	@Override
	public List<MonitorSession> getMonitorSessions(String roomId) {
		RbRoom room = rbRoomService.getById(roomId);
		rbRoomService.checkTeacherPermission(roomId, UserInfoContext.getUserContext().getUserId());
		if (room == null || StringUtils.isBlank(room.getLocalSceneId())) {
			return Lists.newArrayList();
		}
		List<MonitorSession> sessionList = crCommonService
				.getMonitorSessions(room.getLocalSceneId(), null);
		// set role group, red or blue
		Map<String, CrRole> userRoleMap = crRoleService.getUserRoleMapByRoomId(roomId);
		for (MonitorSession session : sessionList) {
			CrRole role = userRoleMap.get(session.getUser());
			if (null != role) {
				session.setRoleGroup(role.getRoleGroup());
			}
		}
		return sessionList;
	}

	private List<String> getRefereeIdsByRoomId(String roomId) {
		List<CrRole> roles = crRoleService.getRolesByRoomAndGroups(roomId,
				Lists.newArrayList(CrRole.RbRoleGroup.REFEREE.getCode()));
		if (roles.isEmpty()) {
			return Lists.newArrayList();
		}
		return roles.get(0).getUserIdList();
	}
}
