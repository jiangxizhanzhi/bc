package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cr.redvsblue.service.RbRoomScoreService;
import com.cyberpeace.cr.redvsblue.service.context.mission.RoleScore;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RbRoomScoreServiceImpl extends ServiceImpl<RbRoomMapper, RbRoom> implements RbRoomScoreService {

	@Autowired
	private RbRoomMapper rbRoomMapper;

	@Autowired
	CrTaskService crTaskService;

	@Autowired
	private CrRoleService crRoleService;

	@Override
	public List<RoleScore> getRoleScoreInfo(String roomId) {
		RoleQuery roleQuery = new RoleQuery(roomId, CrRole.RbRoleGroupCode.RED.getCode());
		List<CrRole> crRedRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

		List<String> redRoles = Lists.newArrayList();
		for (CrRole crRole : crRedRoles) {
			redRoles.add(crRole.getResourceId());
		}

		roleQuery = new RoleQuery(roomId, CrRole.RbRoleGroupCode.BLUE.getCode());
		List<CrRole> crBlueRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

		List<String> blueRoles = Lists.newArrayList();
		for (CrRole crRole : crBlueRoles) {
			blueRoles.add(crRole.getResourceId());
		}

		List<CrTask> taskList = crTaskService.getByRoomId(roomId);

		Integer redScore = 0;
		Integer redFinishedTaskCount = 0;
		Integer redTaskCount = 0;

		Integer blueScore = 0;
		Integer blueFinishedTaskCount = 0;
		Integer blueTaskCount = 0;

		for (CrTask task : taskList) {
			if (redRoles.contains(task.getRoleId())) {
				redTaskCount++;
				if (task.isFinished()) {
					redFinishedTaskCount++;
					redScore += task.getScore();
				}

			} else {
				blueTaskCount++;
				if (task.isFinished()) {
					blueFinishedTaskCount++;
					blueScore += task.getScore();
				}
			}
		}

		RoleScore redRoleScore = new RoleScore(CrRole.RbRoleGroupCode.RED.getDesc(),
				CrRole.RbRoleGroupCode.RED.getCode(), redScore, redFinishedTaskCount, redTaskCount);
		RoleScore blueRoleScore = new RoleScore(CrRole.RbRoleGroupCode.BLUE.getDesc(),
				CrRole.RbRoleGroupCode.BLUE.getCode(), blueScore, blueFinishedTaskCount,
				blueTaskCount);

		List<RoleScore> roleScores = Lists.newArrayList();
		roleScores.add(redRoleScore);
		roleScores.add(blueRoleScore);

		return roleScores;
	}

}
