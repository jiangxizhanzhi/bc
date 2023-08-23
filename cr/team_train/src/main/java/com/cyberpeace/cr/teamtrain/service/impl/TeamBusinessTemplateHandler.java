package com.cyberpeace.cr.teamtrain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.TypeReference;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.context.AddTemplateMission;
import com.cyberpeace.cr.crbase.service.context.ColorMission;
import com.cyberpeace.cr.crbase.service.context.resource.ResourceDumpContext;
import com.cyberpeace.cr.crbase.service.context.resource.TrainingTemplateContext;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateContext;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
	public BusinessTemplate.BusinessType setBusinessType() {
		return BusinessTemplate.BusinessType.TRAINING_TEAM;
	}

	@Override
	public String saveRealBusiness(BasicTemplate basicTemplate, String businessId) {

		TeamTrainTemplateContext teamTrainTemplateContext = BeanUtils.copy(basicTemplate,
				TeamTrainTemplateContext.class);
		TeamTemplate teamTemplate = new TeamTemplate();

		BaseInfo baseInfo = basicTemplate.getBaseInfo();

		List<TeamTrainTemplateRole> teamTrainTemplateRoleList = JsonUtil.toJsonObjectArray(
				teamTrainTemplateContext.getRoleList(), TeamTrainTemplateRole.class);

		// 校验角色的任务
		teamTrainTemplateRoleList.forEach(teamTrainTemplateRole -> {
			ColorMission<AddTemplateMission> colorMission = JSON.parseObject(
					JsonUtil.toJsonString(teamTrainTemplateRole.getMissionsScheme()),
					new TypeReference<ColorMission<AddTemplateMission>>() {});

			missionService.validateMission(colorMission);
		});

		List<String> roleList = teamTrainTemplateRoleList.stream()
				.map(TeamTrainTemplateRole::getName).distinct().collect(Collectors.toList());
		if (roleList.size() < teamTrainTemplateRoleList.size()) {
			log.error("role name duplicated！");

			throw new BusinessException(CrBusinessErrorCode.ROLE_NAME_DUPLICATE);
		}

		// 保存团队训练模板
		teamTemplate.setBusinessTemplateId(businessId);
		teamTemplate.setPreSkills(JsonUtil.toJsonString(baseInfo.getPreSkill()));
		teamTemplate.setTrainSkills(JsonUtil.toJsonString(baseInfo.getTrainSkill()));
		teamTemplate.setSubType(baseInfo.getCategory());
		teamTemplate.setPeopleNumber(basicTemplate.getBaseInfo().getPeopleNumber());
		teamTemplate.setDurationTime(baseInfo.getDurationTime());
		teamTemplate.setRoleConfig(
				JsonUtil.toJsonString(teamTrainTemplateRoleList, PropertyNamingStrategy.SnakeCase));

		teamTemplate.setTimeLineConfig(
				JsonUtil.toJsonString(teamTrainTemplateContext.getTimeLineConfig()));

		teamTemplateService.save(teamTemplate);

		return teamTemplate.getResourceId();
	}

	@Override
	public ResourceDumpContext realDumpBusiness(BusinessTemplate template) {
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
		if (!TeamRoom.TrainCategory.DAILY.getCode().equals(teamTemplate.getTrainCategory())){
			missionTemplateService.batchDelete(teamTemplateService
					.getMissionTemplateIdsByRoleConfig(teamTemplate.getRoleConfig()));
		}
		// 删除训练模板
		teamTemplateService.removeById(teamTemplate.getResourceId());
	}

	@Override
	public void loadTrainTemplate(TrainingTemplateContext templateContext) {
		TeamTemplate teamTemplate = BeanUtils.copy(templateContext, TeamTemplate.class);
		teamTemplateService.save(teamTemplate);
	}

}
