package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.AddTemplateMission;
import com.cyberpeace.cr.crbase.service.context.ColorMission;
import com.cyberpeace.cr.crbase.service.context.LoadTemplateData;
import com.cyberpeace.cr.crbase.service.context.resource.ResourceDumpContext;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateContext;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateRole;
import com.google.common.collect.Lists;

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
					teamTrainTemplateRole.getMissionsScheme(),
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
		teamTemplate.setRoleConfig(teamTrainTemplateContext.getRoleList());

		teamTemplate.setTimeLineConfig(
				JsonUtil.toJsonString(teamTrainTemplateContext.getTimeLineConfig()));

		teamTemplateService.save(teamTemplate);

		return teamTemplate.getResourceId();
	}

	@Override
	public void realLoads(LoadTemplateData templateData,
			List<MissionTemplate> missionTemplateList) {
		// 1、替换任务方案中的mission字段
		replaceMissions(templateData, missionTemplateList);

		// 2、转换成新增基础数据结构
		TeamTrainTemplateContext teamTrainTemplateContext = JSON.parseObject(
				templateData.getTemplateData().toJSONString(), TeamTrainTemplateContext.class);
		teamTrainTemplateContext.getBaseInfo()
				.setType(BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());

		// 3、新增数据
		businessTemplateService.addTemplate(teamTrainTemplateContext);
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
		context.setMissions(missionService.batchGetByMissionSchemeJson(missionScheme));

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
	public Boolean deleteTemplateRelated(BusinessTemplate businessTemplate) {
		return teamTemplateService.removeByBusinessTemplateId(businessTemplate.getResourceId());
	}

	private void replaceMissions(LoadTemplateData loadTemplateData,
			List<MissionTemplate> missions) {
		// 从python版5.3导出的数据，在导入该版本时，role_list的mission字段要进行替换
		JSONArray roleList = loadTemplateData.getTemplateData().getJSONArray("role_list");
		for (int i = 0; i < roleList.size(); i++) {
			JSONObject missionScheme = roleList.getJSONObject(i).getJSONObject("missions_scheme");
			if (null == missionScheme) {
				continue;
			}

			JSONArray schemaData = missionScheme.getJSONArray("scheme_data");
			if (null == schemaData) {
				continue;
			}

			for (int j = 0; j < schemaData.size(); j++) {
				JSONObject stage = schemaData.getJSONObject(j);
				if (null == stage) {
					continue;
				}

				List<Integer> missionIds = JsonUtil.toJsonObjectArray(stage.getString("missions"),
						Integer.class);

				if (missionIds.isEmpty()) {
					continue;
				}

				List<AddTemplateMission> replacedMissions = Lists.newArrayList();
				for (Integer index : missionIds) {
					replacedMissions.add(getAddTemplateMission(index, missions));
				}

				stage.put("missions", replacedMissions);
			}
		}
	}

	private AddTemplateMission getAddTemplateMission(Integer missionIndex,
			List<MissionTemplate> missions) {
		MissionTemplate origin = missions.get(missionIndex);

		AddTemplateMission result = BeanUtils.copy(origin, AddTemplateMission.class);
		return result;
	}

}
