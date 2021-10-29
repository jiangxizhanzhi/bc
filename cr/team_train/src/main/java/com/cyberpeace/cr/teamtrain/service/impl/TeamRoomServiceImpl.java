package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.cebase.model.crevent.CrEvent;
import com.cyberpeace.cr.teamtrain.dao.entity.*;
import com.cyberpeace.cr.teamtrain.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.teamtrain.dao.mapper.RoleReq;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.model.room.RoomRole;
import com.cyberpeace.cr.teamtrain.model.room.TeamRoomData;
import com.cyberpeace.cr.teamtrain.model.room.TeamRoomRes;
import com.cyberpeace.cr.teamtrain.model.room.TeamTrainRoomReq;
import com.cyberpeace.cr.teamtrain.model.template.BaseInfoInput;
import com.cyberpeace.cr.teamtrain.model.template.TeamTrainTemplateModifyInput;
import com.cyberpeace.cr.teamtrain.service.BusinessTemplateService;
import com.cyberpeace.cr.teamtrain.service.CrRoleService;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.integratation.model.SceneConfig;

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
	public TeamRoomRes getTeamRoomOutputById(String resourceId) {
		TeamRoomData teamRoomdata = teamRoomMapper.getTeamRoomOutputById(resourceId);

		RoleReq roleReq = new RoleReq();
		roleReq.setRoomId(teamRoomdata.getResourceId());
		List<CrRole> crRoles = crRoleMapper.selectCrRoleByRoomId(roleReq);

		TeamRoomRes teamRoomRes = getTeamRoomOutputFromTeamRoomData(teamRoomdata);

		teamRoomRes.setTrainingRoleData(crRoles);
		return teamRoomRes;
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
			crRole.setBusinessType(CrEvent.CeType.TRAINING_TEAM);
			crRoleService.save(crRole);

		}

		// teamTemplate.setRoleConfig(JsonUtil.toJsonString(teamTrainTemplateRoleList));

		// 保存任务信息
		// todo

		return template;
	}

	@Transactional(rollbackFor = { Exception.class }, transactionManager = "crTransactionManager")
	@Override
	public TeamTemplate updateTeamRoom(TeamTrainTemplateModifyInput input,
			BusinessTemplate businessTemplate) {
		TeamTemplate teamTemplate = new TeamTemplate();

		BaseInfoInput baseInfoInput = input.getBaseInfo();

		SceneConfig sceneConfig = input.getSceneConfigData();

		businessTemplate.setTopoId("");
		businessTemplate.setCreateUser(UserInfoContext.getUserContext().getUserId());
		businessTemplate.setResourceId(businessTemplate.getResourceId());
		businessTemplate.setName(baseInfoInput.getName());
		businessTemplate.setDescription(baseInfoInput.getDescription());
		businessTemplate.setDifficulty(baseInfoInput.getDifficult());

		if (sceneConfig != null) {
			String sceneConfigId = "todo";
			businessTemplate.setTopoId(sceneConfigId);
		}

		businessTemplateService.updateById(businessTemplate);

		// 保存团队训练模板
		teamTemplate.setResourceId(input.getResourceId());
		teamTemplate.setBusinessTemplateId(businessTemplate.getResourceId());
		teamTemplate.setPreSkills(JsonUtil.toJsonString(baseInfoInput.getPre_skill()));
		teamTemplate.setTrainSkills(JsonUtil.toJsonString(baseInfoInput.getTrain_skill()));
		teamTemplate.setSubType(baseInfoInput.getCategory());
		teamTemplate.setTimeLineConfig(JsonUtil.toJsonString(input.getTimeLineConfig()));
		teamTemplate.setDurationTime(baseInfoInput.getDurationTime());
		teamTemplate.setPeopleNumber(baseInfoInput.getPeopleNumber());

		teamTemplateService.updateById(teamTemplate);
		// MissionScheme missionScheme = input.getMissionScheme();
		// if (missionScheme != null) {
		// String missionSchemeId = missionSchemeService.create(missionScheme);
		// template.setMissionSchemeId(missionSchemeId);
		// }

		return teamTemplate;
	}

	private TeamRoomRes getTeamRoomOutputFromTeamRoomData(TeamRoomData teamRoomdata) {
		TeamRoomRes teamRoomRes = BeanUtils.copy(teamRoomdata, TeamRoomRes.class);

		teamRoomRes
				.setPreSkill(JsonUtil.toJsonObjectArray(teamRoomdata.getPreSkill(), String.class));
		teamRoomRes.setTrainSkill(
				JsonUtil.toJsonObjectArray(teamRoomdata.getTrainSkill(), String.class));

		return teamRoomRes;
	}
}
