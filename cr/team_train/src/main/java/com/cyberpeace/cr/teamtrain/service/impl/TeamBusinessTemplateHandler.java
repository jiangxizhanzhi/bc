package com.cyberpeace.cr.teamtrain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.context.resource.ResourceDumpContext;
import com.cyberpeace.cr.crbase.service.context.resource.TrainingTemplateContext;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeamBusinessTemplateHandler extends BusinessTemplateHandler {

	@Autowired
	TeamTemplateService teamTemplateService;

	@Autowired
	BusinessTemplateService businessTemplateService;

	@Autowired
	MissionService missionService;

	@Autowired
	MissionTemplateService missionTemplateService;

	@Override
	public BusinessType setBusinessType() {
		return BusinessType.TRAINING_TEAM;
	}

	@Override
	public ResourceDumpContext<TeamTemplate> realDumpBusiness(BusinessTemplate template) {
		ResourceDumpContext<TeamTemplate> context = new ResourceDumpContext<>();
		// 获取 teamTemplate
		TeamTemplate teamTemplate = teamTemplateService
				.getByTemplateBusinessId(template.getResourceId());
		teamTemplate.setId(null);
		context.setBusiness(teamTemplate);
		// 获取任务
		String missionScheme = teamTemplate.getRoleConfig();
		context.setMissions(missionTemplateService.batchGetByMissionSchemeJson(missionScheme));

		return context;
	}

	@Override
	public void realLoadBusinessResource(ResourceDumpContext resource) {
		TeamTemplate teamTemplate = ((JSONObject) resource.getBusiness())
				.toJavaObject(TeamTemplate.class);
		teamTemplate.setId(null);
		teamTemplate.setCreateTime(null);
		teamTemplate.setUpdateTime(null);
		if (null != teamTemplateService.getById(teamTemplate.getResourceId())) {
			teamTemplateService.updateById(teamTemplate);
		} else {
			teamTemplateService.save(teamTemplate);
		}
	}

	@Override
	public void deleteTemplateRelated(BusinessTemplate businessTemplate) {
		TeamTemplate teamTemplate = teamTemplateService
				.getByTemplateBusinessId(businessTemplate.getResourceId());
		// 删除任务模板
		missionTemplateService.batchDelete(teamTemplateService
				.getMissionTemplateIdsByRoleConfig(teamTemplate.getRoleConfig()));
		// 删除训练模板
		teamTemplateService.removeById(teamTemplate.getResourceId());
	}

	@Override
	public void loadTrainTemplate(TrainingTemplateContext templateContext) {
		TeamTemplate teamTemplate = BeanUtils.copy(templateContext, TeamTemplate.class);
		teamTemplateService.save(teamTemplate);
	}

}
