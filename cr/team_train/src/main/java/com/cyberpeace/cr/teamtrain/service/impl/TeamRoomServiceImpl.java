package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.teamtrain.dao.entity.CrRole;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainTemplateRole;
import com.cyberpeace.cr.teamtrain.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.model.role.BaseRole;
import com.cyberpeace.cr.teamtrain.model.role.RoleReq;
import com.cyberpeace.cr.teamtrain.model.role.RoleUsers;
import com.cyberpeace.cr.teamtrain.model.room.*;
import com.cyberpeace.cr.teamtrain.model.template.BaseInfoReq;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateModifyReq;
import com.cyberpeace.cr.teamtrain.service.CrRoleService;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.integratation.model.SceneConfig;
import com.google.common.collect.Lists;

/**
 *
 */
@Service
public class TeamRoomServiceImpl extends ServiceImpl<TeamRoomMapper, TeamRoom> implements TeamRoomService {

	@Autowired
	private TeamRoomMapper teamRoomMapper;

	@Autowired
	private CrRoleMapper crRoleMapper;

	@Autowired
	TeamTemplateService teamTemplateService;

	@Autowired
	BusinessTemplateService businessTemplateService;

	@Autowired
	CrRoleService crRoleService;

	@Override
	public TeamRoomRsp getTeamRoomOutputById(String resourceId) {
		String userid = UserInfoContext.getUserContext().getUserId();
		TeamRoomData teamRoomdata = teamRoomMapper.getTeamRoomOutputById(resourceId);

		RoleReq roleReq = new RoleReq();
		roleReq.setRoomId(teamRoomdata.getResourceId());

		List<CrRole> crRoles = crRoleMapper.selectCrRoleByRoomId(roleReq);

		TeamRoomRsp teamRoomRsp = getTeamRoomOutputFromTeamRoomData(teamRoomdata);
		teamRoomRsp.setTrainingRoleData(getSelfRole(crRoles, userid));
		teamRoomRsp.setRolesData(crRoles);

		return teamRoomRsp;
	}

	// 获取单个实例中自己的角色，只包括自己角色的基本信息
	private BaseRole getSelfRole(List<CrRole> crRoles, String userid) {
		BaseRole baseRole = new BaseRole();
		crRoles.forEach(crRole -> {
			if (crRole.getUserIds().contains(userid)) {
				baseRole.setResourceId(crRole.getResourceId());
				baseRole.setName(crRole.getName());

				baseRole.setDescription(crRole.getDescription());
				baseRole.setRoleGroup(crRole.getRoleGroup());
			}
		});

		return baseRole;
	}
	// 先获取实例列表中自己的角色，再获取所有的学员角色

	private BaseRole getSelfRoleUsers(List<CrRole> crRoles, String userid) {
		BaseRole baseRole = new BaseRole();
		List<RoleUsers> roleUsersList = Lists.newArrayList();
		crRoles.forEach(crRole -> {
			if (crRole.getUserIds().contains(userid)) {
				baseRole.setResourceId(crRole.getResourceId());
				baseRole.setName(crRole.getName());

				baseRole.setDescription(crRole.getDescription());
				baseRole.setRoleGroup(crRole.getRoleGroup());
			}
			if (crRole.getRoleGroup().intValue() != CrRole.RoleGroupCodeInteger.TEACHER
					.getValue()) {
				RoleUsers roleUsers = new RoleUsers();
				roleUsers.setRoleName(crRole.getName());
				roleUsers.setRoleGroup(crRole.getRoleGroup());
				roleUsers.setUsers(JsonUtil.toJsonObjectArray(crRole.getUserIds(), String.class));
				roleUsersList.add(roleUsers);
			}
		});

		baseRole.setRoleDataList(roleUsersList);
		return baseRole;
	}

	@Transactional(rollbackFor = { Exception.class }, transactionManager = "crTransactionManager")
	@Override
	public TeamTemplate create(TeamTrainRoomReq input) {
		TeamTemplate template = teamTemplateService.getById(input.getTemplate());
		if (template == null) {
			throw new ResourceNotFoundException("团队训练模板" + input.getTemplate() + "不存在");
		}

		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		TeamRoom teamRoom = new TeamRoom(input, template, businessTemplate);

		save(teamRoom);

		List<TeamTrainTemplateRole> teamTrainTemplateRoleList = JsonUtil
				.toJsonObjectArray(template.getRoleConfig(), TeamTrainTemplateRole.class);
		List<RoomRole> users = input.getRoleConfig();
		int index = 0;
		for (TeamTrainTemplateRole teamTrainTemplateRole : teamTrainTemplateRoleList) {

			CrRole crRole = BeanUtils.deepCopy(teamTrainTemplateRole, CrRole.class);
			// crRole.setBusinessInstanceId();
			crRole.setUserIds(JsonUtil.toJsonString(users.get(index++).getUsers()));
			teamTrainTemplateRole.getResourceId();
			JsonUtil.toJsonString(teamTrainTemplateRoleList);
			crRole.setBusinessInstanceId(teamRoom.getResourceId());
			crRole.setBusinessType(BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
			crRoleService.save(crRole);

		}

		// teamTemplate.setRoleConfig(JsonUtil.toJsonString(teamTrainTemplateRoleList));

		// 保存任务信息
		// todo

		return template;
	}

	@Transactional(rollbackFor = { Exception.class }, transactionManager = "crTransactionManager")
	@Override
	public TeamTemplate updateTeamRoom(TeamTrainTemplateModifyReq input,
			BusinessTemplate businessTemplate) {
		TeamTemplate teamTemplate = new TeamTemplate();

		BaseInfoReq baseInfoReq = input.getBaseInfo();

		SceneConfig sceneConfig = input.getSceneConfigData();

		businessTemplate.setTopoId("");
		businessTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		businessTemplate.setResourceId(businessTemplate.getResourceId());
		businessTemplate.setName(baseInfoReq.getName());
		businessTemplate.setDescription(baseInfoReq.getDescription());
		businessTemplate.setDifficulty(baseInfoReq.getDifficult());

		if (sceneConfig != null) {
			String sceneConfigId = "todo";
			businessTemplate.setTopoId(sceneConfigId);
		}

		businessTemplateService.updateById(businessTemplate);

		// 保存团队训练模板
		teamTemplate.setResourceId(input.getResourceId());
		teamTemplate.setBusinessTemplateId(businessTemplate.getResourceId());
		teamTemplate.setPreSkills(JsonUtil.toJsonString(baseInfoReq.getPre_skill()));
		teamTemplate.setTrainSkills(JsonUtil.toJsonString(baseInfoReq.getTrain_skill()));
		teamTemplate.setSubType(baseInfoReq.getCategory());
		teamTemplate.setTimeLineConfig(JsonUtil.toJsonString(input.getTimeLineConfig()));
		teamTemplate.setDurationTime(baseInfoReq.getDurationTime());
		teamTemplate.setPeopleNumber(baseInfoReq.getPeopleNumber());

		teamTemplateService.updateById(teamTemplate);
		// MissionScheme missionScheme = input.getMissionScheme();
		// if (missionScheme != null) {
		// String missionSchemeId = missionSchemeService.create(missionScheme);
		// template.setMissionSchemeId(missionSchemeId);
		// }

		return teamTemplate;
	}

	@Override
	public ListRsp<TeamRoomRsp> list(TeamRoomReq listReq) {
		String userid = UserInfoContext.getUserContext().getUserId();
		Page page = listReq.toPageParam();
		listReq.setIds(crRoleService.getRoomIdsByUserid(userid));
		IPage<TeamRoomData> data = teamRoomMapper.listTeamRoom(page, listReq);

		ListRsp ret = new ListRsp();

		List<TeamRoomRsp> teamRoomRspList = Lists.newArrayList();

		for (TeamRoomData teamRoomData : data.getRecords()) {
			RoleReq roleReq = new RoleReq();
			roleReq.setRoomId(teamRoomData.getResourceId());

			List<CrRole> crRoles = crRoleMapper.selectCrRoleByRoomId(roleReq);

			TeamRoomRsp teamRoomRsp = getTeamRoomOutputFromTeamRoomData(teamRoomData);
			teamRoomRsp.setTrainingRoleData(getSelfRoleUsers(crRoles, userid));
			teamRoomRsp.setRolesData(crRoles);

			teamRoomRspList.add(teamRoomRsp);
		}

		ret.setList(teamRoomRspList);
		ret.setTotal(data.getTotal());
		ret.setLastPage(data.getPages());
		ret.setCurrentPage(data.getCurrent());

		return ret;
	}

	private TeamRoomRsp getTeamRoomOutputFromTeamRoomData(TeamRoomData teamRoomdata) {
		TeamRoomRsp teamRoomRsp = BeanUtils.copy(teamRoomdata, TeamRoomRsp.class);

		teamRoomRsp
				.setPreSkill(JsonUtil.toJsonObjectArray(teamRoomdata.getPreSkill(), String.class));
		teamRoomRsp.setTrainSkill(
				JsonUtil.toJsonObjectArray(teamRoomdata.getTrainSkill(), String.class));

		return teamRoomRsp;
	}
}
