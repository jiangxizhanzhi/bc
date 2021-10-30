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
import com.cyberpeace.cr.teamtrain.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainTemplateRole;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTemplateMapper;
import com.cyberpeace.cr.teamtrain.model.role.BaseRole;
import com.cyberpeace.cr.teamtrain.model.template.*;
import com.cyberpeace.cr.teamtrain.service.BusinessTemplateService;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.integratation.model.SceneConfig;
import com.google.common.collect.Lists;

/**
 *
 */
@Service
public class TeamTemplateServiceImpl extends ServiceImpl<TeamTemplateMapper, TeamTemplate> implements TeamTemplateService {

	@Autowired
	private TeamTemplateMapper teamTemplateMapper;

	@Autowired
	BusinessTemplateService businessTemplateService;

	@Autowired
	private TeamRoomMapper teamTrainTemplateMapper;

	@Override
	public ListRsp<TeamTrainTemplateOutput> page(TeamTemplateReq listReq) {
		Page page = listReq.toPageParam();

		IPage<BaseInfoData> data = teamTemplateMapper.listTeamTemplate(page, listReq);

		ListRsp ret = new ListRsp();

		List<TeamTrainTemplateOutput> teamTrainTemplateOutputList = Lists.newArrayList();

		for (BaseInfoData baseInfoData : data.getRecords()) {
			teamTrainTemplateOutputList.add(getTeamTrainTemplateOutputByBaseInfoData(baseInfoData));
		}

		ret.setList(teamTrainTemplateOutputList);
		ret.setTotal(data.getTotal());
		ret.setLastPage(data.getPages());
		ret.setCurrentPage(data.getCurrent());

		return ret;
	}

	@Transactional(rollbackFor = { Exception.class }, transactionManager = "crTransactionManager")
	@Override
	public TeamTemplate create(TeamTrainTemplateAddInput input) {
		TeamTemplate teamTemplate = new TeamTemplate();

		BaseInfoInput crEventAddInput = input.getBaseInfo();
		BusinessTemplate businessTemplate = new BusinessTemplate(crEventAddInput);

		// 拓扑场景id保存获取
		SceneConfig sceneConfig = input.getSceneConfigData();
		String sceneConfigId = "todo场景";

		// 保存基础模板
		businessTemplate.setTopoId(sceneConfigId);
		businessTemplate.setCreateUser(UserInfoContext.getUserContext().getUserId());

		businessTemplateService.save(businessTemplate);
		// BusinessTemplate businessTemplate = crEventService.create(crEventAddInput,
		// CrEvent.CeType.TRAINING_TEAM);

		// 保存团队训练模板
		teamTemplate.setBusinessTemplateId(businessTemplate.getResourceId());
		teamTemplate.setPreSkills(JsonUtil.toJsonString(crEventAddInput.getPre_skill()));
		teamTemplate.setTrainSkills(JsonUtil.toJsonString(crEventAddInput.getTrain_skill()));
		teamTemplate.setSubType(crEventAddInput.getCategory());
		teamTemplate.setTimeLineConfig(JsonUtil.toJsonString(input.getTimeLineConfig()));

		List<TeamTrainTemplateRole> teamTrainTemplateRoleList = Lists.newArrayList();

		for (RoleListInput roleListInput : input.getRoleList()) {
			TeamTrainTemplateRole teamTrainTemplateRole = BeanUtils.deepCopy(roleListInput,
					TeamTrainTemplateRole.class);
			teamTrainTemplateRole.setCrEventId(businessTemplate.getResourceId());
			teamTrainTemplateRole
					.setMissionsScheme(JsonUtil.toJsonString(roleListInput.getMissionsScheme()));

			teamTrainTemplateRoleList.add(teamTrainTemplateRole);
		}
		teamTemplate.setRoleConfig(JsonUtil.toJsonString(teamTrainTemplateRoleList));

		// 保存任务信息
		// MissionScheme missionScheme = input.getMissionScheme();
		// String missionSchemeId = missionSchemeService.create(missionScheme);
		// template.setMissionSchemeId(missionSchemeId);
		save(teamTemplate);

		return teamTemplate;
	}

	@Transactional(rollbackFor = { Exception.class }, transactionManager = "crTransactionManager")
	@Override
	public TeamTemplate updateTeamTemplate(TeamTrainTemplateModifyInput input,
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
			String sceneConfigId = "场景todo";
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

		updateById(teamTemplate);
		// MissionScheme missionScheme = input.getMissionScheme();
		// if (missionScheme != null) {
		// String missionSchemeId = missionSchemeService.create(missionScheme);
		// template.setMissionSchemeId(missionSchemeId);
		// }

		return teamTemplate;
	}

	@Override
	public TeamTrainTemplateOutput getTeamTemplate(String resourceId) {
		TeamTrainTemplateOutput teamTrainTemplateOutput = new TeamTrainTemplateOutput();
		TeamTemplate template = getById(resourceId);
		if (template == null) {
			throw new ResourceNotFoundException("团队训练模板" + resourceId + "不存在");
		}
		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		BaseInfoOutput baseInfoOutput = new BaseInfoOutput(template, businessTemplate);
		String roleString = template.getRoleConfig();

		List<TeamTrainTemplateRole> teamTrainTemplateRoleList = JsonUtil
				.toJsonObjectArray(roleString, TeamTrainTemplateRole.class);
		teamTrainTemplateOutput.setBaseInfo(baseInfoOutput);
		teamTrainTemplateOutput.setRoleList(teamTrainTemplateRoleList);

		teamTrainTemplateOutput.setTimeLineConfig(template.getTimeLineConfig());

		return teamTrainTemplateOutput;
	}

	public TeamTrainTemplateOutput
			getTeamTrainTemplateOutputByBaseInfoData(BaseInfoData baseInfoData) {
		TeamTrainTemplateOutput teamTrainTemplateOutput = new TeamTrainTemplateOutput();

		BaseInfoOutput baseInfoOutput = new BaseInfoOutput(baseInfoData);

		teamTrainTemplateOutput.setBaseInfo(baseInfoOutput);
		String roleString = baseInfoData.getRoleConfig();

		List<BaseRole> teamTrainTemplateRoleList = JsonUtil.toJsonObjectArray(roleString,
				BaseRole.class);
		teamTrainTemplateOutput.setRoleStructure(teamTrainTemplateRoleList);
		return teamTrainTemplateOutput;
	}

}
