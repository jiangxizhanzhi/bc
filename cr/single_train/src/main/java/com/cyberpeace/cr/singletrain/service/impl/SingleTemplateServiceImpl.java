package com.cyberpeace.cr.singletrain.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMission;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigMissionsScheme;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleConfigSchemeStage;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleTemplateMapper;
import com.cyberpeace.cr.singletrain.service.SingleTemplateService;
import com.cyberpeace.cr.singletrain.service.bo.EventDataBo;
import com.cyberpeace.cr.singletrain.service.bo.SingleTemplateBo;
import com.cyberpeace.cs.service.LocalSceneService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SingleTemplateServiceImpl extends ServiceImpl<SingleTemplateMapper, SingleTemplate> implements SingleTemplateService {

	@Autowired
	private SingleTemplateMapper singleTemplateMapper;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private MissionTemplateService missionTemplateService;

	/**
	 * 单兵训练模板获取
	 */
	@Override
	public SingleTemplateBo get(String businessTemplateId) {
		SingleTemplate trainObject = singleTemplateMapper.getByBusinessId(businessTemplateId);
		BusinessTemplate businessTemplate = businessTemplateService.getById(businessTemplateId);

		EventDataBo eventData = new EventDataBo();
		BeanUtils.copyProperties(trainObject, eventData);
		BeanUtils.copyProperties(businessTemplate, eventData);

		// 属性 todo create_user, scene_config_data
		eventData.setDifficult(businessTemplate.getDifficulty());
		eventData.setCategory(trainObject.getSubType());
		eventData.setPreSkill(JsonUtil.toJsonObjectArray(trainObject.getPreSkills(), String.class));
		eventData.setTrainSkill(
				JsonUtil.toJsonObjectArray(trainObject.getTrainSkills(), String.class));
		eventData.setSceneConfigData(localSceneService.getTopoData(businessTemplate.getTopoId()));
		eventData.setLogo(businessTemplate.getLogo());

		SingleTemplateBo singleTemplateBo = new SingleTemplateBo();

		singleTemplateBo.setResourceId(businessTemplate.getResourceId());
		singleTemplateBo.setEventData(eventData);
		singleTemplateBo.setMissionsScheme(getMissionTemplates(trainObject.getMissionsScheme()));

		return singleTemplateBo;
	}

	/**
	 * 单兵训练模板更新
	 * @param businessTemplateId cr_business_template.resource_id
	 * @param baseInfo 训练基础数据
	 * @param missionsScheme 训练任务方案
	 * @return 更新后的单兵训练数据对象
	 */
	@Override
	public SingleTemplateBo update(String businessTemplateId, BaseInfo baseInfo,
			JSONObject missionsScheme) {
		// business template
		BusinessTemplate businessTemplate = businessTemplateService.getById(businessTemplateId);
		BeanUtils.copyProperties(baseInfo, businessTemplate);

		// single
		SingleTemplate singleTemplate = singleTemplateMapper.getByBusinessId(businessTemplateId);
		BeanUtils.copyProperties(baseInfo, singleTemplate);
		singleTemplate.setPreSkills(JsonUtil.toJsonString(baseInfo.getPreSkill()));
		singleTemplate.setTrainSkills(JsonUtil.toJsonString(baseInfo.getTrainSkill()));
		singleTemplate.setMissionsScheme(JsonUtil.toJsonString(missionsScheme));
		singleTemplate.setSubType(baseInfo.getCategory());

		// topology
		if (baseInfo.getSceneConfigData() != null) {
			JSONObject sceneConfigData = new JSONObject();
			sceneConfigData.put("scene_config", baseInfo.getSceneConfigData().getSceneConfig());
			localSceneService.saveTopo(sceneConfigData, businessTemplate.getTopoId());
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				businessTemplateService.updateById(businessTemplate);
				singleTemplateMapper.updateById(singleTemplate);
			}
		});

		return get(businessTemplateId);
	}

	@Override
	public SingleTemplate getByBusinessTemplateId(String resourceId) {
		return singleTemplateMapper.getByBusinessId(resourceId);
	}

	@Override
	public List<String> getMissionTemplateIdsByMissionsScheme(String missionsScheme) {
		CrRoleConfigMissionsScheme rspMissionsScheme = JsonUtil.toJsonObject(missionsScheme,
				CrRoleConfigMissionsScheme.class);
		List<String> missionTemplateIds = Lists.newArrayList();
		rspMissionsScheme.getSchemeData().forEach(stage -> stage.getMissions()
				.forEach(mission -> missionTemplateIds.add(mission.getResourceId())));
		return missionTemplateIds;
	}

	private JSONObject getMissionTemplates(String missionsScheme) {
		// get missionTemplates mapping
		Map<String, MissionTemplate> mapping = missionTemplateService
				.getMappingBySchemeJson(missionsScheme);
		// 循环获取 title 和 desc
		CrRoleConfigMissionsScheme rspMissionsScheme = JsonUtil.toJsonObject(missionsScheme,
				CrRoleConfigMissionsScheme.class);
		for (CrRoleConfigSchemeStage stage : rspMissionsScheme.getSchemeData()) {
			for (CrRoleConfigMission mission : stage.getMissions()) {
				MissionTemplate missionTemplate = mapping.get(mission.getResourceId());
				mission.setTitle(missionTemplate.getTitle());
				mission.setDescription(missionTemplate.getDescription());
			}
		}
		return JsonUtil.java2JsonObject(rspMissionsScheme, PropertyNamingStrategy.SnakeCase);
	}
}
