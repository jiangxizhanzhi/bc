package com.cyberpeace.cr.scene_manage.service.impl;

import java.util.List;

import com.cyberpeace.cr.scene_manage.dao.entity.SceneTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.TrainRecordService;
import com.cyberpeace.cr.crbase.service.context.resource.ResourceDumpContext;
import com.cyberpeace.cr.crbase.service.context.resource.TrainingTemplateContext;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import com.cyberpeace.cr.scene_manage.controller.req.SceneTrainTemplateReq;
import com.cyberpeace.cr.scene_manage.service.SceneTemplateService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SceneBusinessTemplateHandlerImpl extends BusinessTemplateHandler {

	@Autowired
	TransactionTemplate transactionTemplate;

	@Autowired
	TrainRecordService trainRecordService;

	@Autowired
	private SceneTemplateService sceneTemplateService;



	@Autowired
	private MissionTemplateService missionTemplateService;

	@Override
	public BusinessTemplate.BusinessType setBusinessType() {
		return BusinessTemplate.BusinessType.SCENE_MANAGE;
	}

	@Override
	public String saveRealBusiness(BasicTemplate basicTemplate, String businessId) {
		SceneTemplate sceneTemplate = new SceneTemplate();
		BaseInfo baseInfo = basicTemplate.getBaseInfo();

		sceneTemplate.setBusinessTemplateId(businessId);
		sceneTemplate.setPreSkills(JsonUtil.toJsonString(baseInfo.getPreSkill()));
		sceneTemplate.setTrainSkills(JsonUtil.toJsonString(baseInfo.getTrainSkill()));
		sceneTemplate.setSubType(baseInfo.getCategory());
		sceneTemplate.setDurationTime(baseInfo.getDurationTime());

		SceneTrainTemplateReq requestData = (SceneTrainTemplateReq) basicTemplate;
		sceneTemplate.setMissionsScheme(JsonUtil.toJsonString(requestData.getMissionsScheme()));
		// todo save missions -> return mission_id ? ->
		// sceneTemplate.setMissionSchemeId(mission_id)

		sceneTemplateService.save(sceneTemplate);

		return sceneTemplate.getBusinessTemplateId();
	}

	@Override
	public ResourceDumpContext realDumpBusiness(BusinessTemplate template) {
		ResourceDumpContext<SceneTemplate> context = new ResourceDumpContext<>();
		// 获取 teamTemplate
		SceneTemplate sceneTemplate = sceneTemplateService
				.getByBusinessTemplateId(template.getResourceId());
		sceneTemplate.setId(null);
		context.setBusiness(sceneTemplate);
		// 获取任务
		String missionScheme = sceneTemplate.getMissionsScheme();
		context.setMissions(missionTemplateService.batchGetByMissionSchemeJson(missionScheme));

		return context;
	}

	@Override
	public void realLoadBusinessResource(ResourceDumpContext resource) {
		SceneTemplate sceneTemplate = ((JSONObject) resource.getBusiness())
				.toJavaObject(SceneTemplate.class);
		sceneTemplate.setId(null);
		sceneTemplate.setCreateTime(null);
		sceneTemplate.setUpdateTime(null);
		if (null != sceneTemplateService.getById(sceneTemplate.getResourceId())) {
			sceneTemplateService.updateById(sceneTemplate);
		} else {
			sceneTemplateService.save(sceneTemplate);
		}
	}

	@Override
	public void deleteTemplateRelated(BusinessTemplate businessTemplate) {
		SceneTemplate sceneTemplate = sceneTemplateService
				.getByBusinessTemplateId(businessTemplate.getResourceId());
		// 删除任务, 但不删除图片
		List<String> missionTemplateIds = sceneTemplateService
				.getMissionTemplateIdsByMissionsScheme(sceneTemplate.getMissionsScheme());
		missionTemplateService.batchDelete(missionTemplateIds);
		// 删除sceneTemplate
		sceneTemplateService.removeById(sceneTemplate.getResourceId());
	}

	@Override
	public void loadTrainTemplate(TrainingTemplateContext templateContext) {
		SceneTemplate template = BeanUtils.copy(templateContext, SceneTemplate.class);
		sceneTemplateService.save(template);
	}


}
