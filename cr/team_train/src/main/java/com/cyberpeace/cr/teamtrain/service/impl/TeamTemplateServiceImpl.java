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
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainTemplateRole;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamTemplateMapper;
import com.cyberpeace.cr.teamtrain.model.role.BaseRole;
import com.cyberpeace.cr.teamtrain.model.template.*;
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
	public ListRsp<TeamTrainTemplateRsp> page(TeamTemplateReq listReq) {
		Page page = listReq.toPageParam();

		IPage<BaseInfoData> data = teamTemplateMapper.listTeamTemplate(page, listReq);

		ListRsp ret = new ListRsp();

		List<TeamTrainTemplateRsp> teamTrainTemplateRspList = Lists.newArrayList();

		for (BaseInfoData baseInfoData : data.getRecords()) {
			teamTrainTemplateRspList.add(getTeamTrainTemplateOutputByBaseInfoData(baseInfoData));
		}

		ret.setList(teamTrainTemplateRspList);
		ret.setTotal(data.getTotal());
		ret.setLastPage(data.getPages());
		ret.setCurrentPage(data.getCurrent());

		return ret;
	}

	@Transactional(rollbackFor = { Exception.class }, transactionManager = "crTransactionManager")
	@Override
	public TeamTemplate create(TeamTrainTemplateAddReq input) {
		TeamTemplate teamTemplate = new TeamTemplate();

		BaseInfoReq crEventAddInput = input.getBaseInfo();

		BusinessTemplate businessTemplate = getBusinessTemplateFromBaseInfoInput(crEventAddInput);

		// 拓扑场景id保存获取
		SceneConfig sceneConfig = input.getSceneConfigData();
		String sceneConfigId = "todo场景";

		// 保存基础模板
		businessTemplate.setTopoId(sceneConfigId);
		businessTemplate.setCreateUserId(UserInfoContext.getUserContext().getUserId());

		businessTemplateService.save(businessTemplate);

		// 保存团队训练模板
		teamTemplate.setBusinessTemplateId(businessTemplate.getResourceId());
		teamTemplate.setPreSkills(JsonUtil.toJsonString(crEventAddInput.getPre_skill()));
		teamTemplate.setTrainSkills(JsonUtil.toJsonString(crEventAddInput.getTrain_skill()));
		teamTemplate.setSubType(crEventAddInput.getCategory());
		teamTemplate.setTimeLineConfig(JsonUtil.toJsonString(input.getTimeLineConfig()));

		List<TeamTrainTemplateRole> teamTrainTemplateRoleList = Lists.newArrayList();

		for (RoleListReq roleListReq : input.getRoleList()) {
			TeamTrainTemplateRole teamTrainTemplateRole = BeanUtils.deepCopy(roleListReq,
					TeamTrainTemplateRole.class);
			teamTrainTemplateRole.setCrEventId(businessTemplate.getResourceId());
			teamTrainTemplateRole
					.setMissionsScheme(JsonUtil.toJsonString(roleListReq.getMissionsScheme()));

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

	private BusinessTemplate getBusinessTemplateFromBaseInfoInput(BaseInfoReq baseInfoReq) {
		BusinessTemplate businessTemplate = new BusinessTemplate();
		businessTemplate.setName(baseInfoReq.getName());
		businessTemplate.setDescription(baseInfoReq.getDescription());
		businessTemplate.setDifficulty(baseInfoReq.getDifficult());
		businessTemplate.setBusinessType(BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
		return businessTemplate;
	}

	@Transactional(rollbackFor = { Exception.class }, transactionManager = "crTransactionManager")
	@Override
	public TeamTemplate updateTeamTemplate(TeamTrainTemplateModifyReq input,
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
			String sceneConfigId = "场景todo";
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

		updateById(teamTemplate);
		// MissionScheme missionScheme = input.getMissionScheme();
		// if (missionScheme != null) {
		// String missionSchemeId = missionSchemeService.create(missionScheme);
		// template.setMissionSchemeId(missionSchemeId);
		// }

		return teamTemplate;
	}

	@Override
	public TeamTrainTemplateRsp getTeamTemplate(String resourceId) {
		TeamTrainTemplateRsp teamTrainTemplateRsp = new TeamTrainTemplateRsp();
		TeamTemplate template = getById(resourceId);
		if (template == null) {
			throw new ResourceNotFoundException("团队训练模板" + resourceId + "不存在");
		}
		BusinessTemplate businessTemplate = businessTemplateService
				.getById(template.getBusinessTemplateId());

		BaseInfoRsp baseInfoRsp = new BaseInfoRsp(template, businessTemplate);
		String roleString = template.getRoleConfig();

		List<TeamTrainTemplateRole> teamTrainTemplateRoleList = JsonUtil
				.toJsonObjectArray(roleString, TeamTrainTemplateRole.class);
		teamTrainTemplateRsp.setBaseInfo(baseInfoRsp);
		teamTrainTemplateRsp.setRoleList(teamTrainTemplateRoleList);

		teamTrainTemplateRsp.setTimeLineConfig(template.getTimeLineConfig());

		return teamTrainTemplateRsp;
	}

	public TeamTrainTemplateRsp
			getTeamTrainTemplateOutputByBaseInfoData(BaseInfoData baseInfoData) {
		TeamTrainTemplateRsp teamTrainTemplateRsp = new TeamTrainTemplateRsp();

		BaseInfoRsp baseInfoRsp = new BaseInfoRsp(baseInfoData);

		teamTrainTemplateRsp.setBaseInfo(baseInfoRsp);
		String roleString = baseInfoData.getRoleConfig();

		List<BaseRole> teamTrainTemplateRoleList = JsonUtil.toJsonObjectArray(roleString,
				BaseRole.class);
		teamTrainTemplateRsp.setRoleStructure(teamTrainTemplateRoleList);
		return teamTrainTemplateRsp;
	}

}
