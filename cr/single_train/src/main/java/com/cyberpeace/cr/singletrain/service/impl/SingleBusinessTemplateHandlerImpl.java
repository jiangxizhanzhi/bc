package com.cyberpeace.cr.singletrain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.resource.ResourceDumpContext;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainTemplateReq;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.service.SingleTemplateService;

@Service
public class SingleBusinessTemplateHandlerImpl extends BusinessTemplateHandler {

	@Autowired
	private SingleTemplateService singleTemplateService;

	@Autowired
	private MissionService missionService;

	@Override
	public BusinessTemplate.BusinessType setBusinessType() {
		return BusinessTemplate.BusinessType.TRAINING_SINGLE;
	}

	@Override
	public String saveRealBusiness(BasicTemplate basicTemplate, String businessId) {
		SingleTemplate singleTemplate = new SingleTemplate();
		BaseInfo baseInfo = basicTemplate.getBaseInfo();

		singleTemplate.setBusinessTemplateId(businessId);
		singleTemplate.setPreSkills(JsonUtil.toJsonString(baseInfo.getPreSkill()));
		singleTemplate.setTrainSkills(JsonUtil.toJsonString(baseInfo.getTrainSkill()));
		singleTemplate.setSubType(baseInfo.getCategory());
		singleTemplate.setDurationTime(baseInfo.getDurationTime());

		SingleTrainTemplateReq requestData = (SingleTrainTemplateReq) basicTemplate;
		singleTemplate.setMissionsScheme(JsonUtil.toJsonString(requestData.getMissionsScheme()));
		// todo save missions -> return mission_id ? ->
		// singleTemplate.setMissionSchemeId(mission_id)

		singleTemplateService.save(singleTemplate);

		return singleTemplate.getBusinessTemplateId();
	}

	@Override
	public ResourceDumpContext realDumpBusiness(BusinessTemplate template) {
		ResourceDumpContext<SingleTemplate> context = new ResourceDumpContext<>();
		// 获取 teamTemplate
		SingleTemplate singleTemplate = singleTemplateService
				.getByBusinessTemplateId(template.getResourceId());
		singleTemplate.setId(null);
		context.setBusiness(singleTemplate);
		// 获取任务
		String missionScheme = singleTemplate.getMissionsScheme();
		context.setMissions(missionService.batchGetByMissionSchemeJson(missionScheme));

		return context;
	}

	@Override
	public void realLoadBusinessResource(ResourceDumpContext resource) {
		SingleTemplate singleTemplate = ((JSONObject) resource.getBusiness())
				.toJavaObject(SingleTemplate.class);
		singleTemplate.setId(null);
		singleTemplate.setCreateTime(null);
		singleTemplate.setUpdateTime(null);
		if (null != singleTemplateService.getById(singleTemplate.getResourceId())) {
			singleTemplateService.updateById(singleTemplate);
		} else {
			singleTemplateService.save(singleTemplate);
		}
	}
}
