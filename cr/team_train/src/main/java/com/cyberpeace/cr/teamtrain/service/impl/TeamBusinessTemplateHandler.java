package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.parser.ParserConfig;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.context.AddTemplateMission;
import com.cyberpeace.cr.crbase.service.context.LoadTemplateData;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import com.cyberpeace.cr.teamtrain.service.context.RoleListContext;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainTemplateReq;
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

	@Autowired
	BusinessTemplateService businessTemplateService;

	@Override
	public void realLoads(LoadTemplateData templateData,
			List<MissionTemplate> missionTemplateList) {
		String templateString = JsonUtil.toJsonString(templateData.getTemplateData());

		ParserConfig parserConfig = new ParserConfig();
		parserConfig.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
		TeamTrainTemplateReq teamTrainTemplateAddReq = JSON.parseObject(templateString,
				TeamTrainTemplateReq.class, parserConfig);

		List<RoleListContext<Integer>> roleListReq = teamTrainTemplateAddReq.getRoleList();

		List<RoleListContext<AddTemplateMission>> newRoleList = Lists.newArrayList();

		for (RoleListContext<Integer> listReq : roleListReq) {

			RoleListContext<AddTemplateMission> newRole = BeanUtils.copy(listReq, RoleListContext.class);

			List<MissionSchemeObject<Integer>> stageList = listReq.getMissionsScheme()
					.getSchemeData();
			List<MissionSchemeObject<AddTemplateMission>> newStageList = Lists.newArrayList();

			for (MissionSchemeObject<Integer> missionSchemeObject : stageList) {
				MissionSchemeObject<AddTemplateMission> newStage = BeanUtils
						.copy(missionSchemeObject, MissionSchemeObject.class);

				List<AddTemplateMission> newMissions = Lists.newArrayList();
				for (Integer index : missionSchemeObject.getMissions()) {
					AddTemplateMission addTemplateMission = BeanUtils
							.copy(missionTemplateList.get(index), AddTemplateMission.class);
					newMissions.add(addTemplateMission);
				}
				newStage.setMissions(newMissions);
				newStageList.add(newStage);
			}

			newRole.getMissionsScheme().setSchemeData(newStageList);
			newRoleList.add(newRole);
		}

		teamTrainTemplateAddReq.setRoleList(newRoleList);
		TeamTrainTemplateContext teamTrainTemplateContext = BeanUtils.copy(teamTrainTemplateAddReq,
				TeamTrainTemplateContext.class);
		teamTrainTemplateContext
				.setRoleList(JsonUtil.toJsonString(teamTrainTemplateAddReq.getRoleList()));
		teamTrainTemplateContext.getBaseInfo()
				.setType(BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
		teamTrainTemplateContext.getBaseInfo()
				.setPeopleNumber(teamTrainTemplateContext.getPeopleNumber());
		businessTemplateService.addTemplate(teamTrainTemplateContext);
	}

}
