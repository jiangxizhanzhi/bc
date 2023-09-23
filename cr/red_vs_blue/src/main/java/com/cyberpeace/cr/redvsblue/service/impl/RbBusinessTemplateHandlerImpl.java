package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.MissionTemplateService;
import com.cyberpeace.cr.crbase.service.context.resource.ResourceDumpContext;
import com.cyberpeace.cr.crbase.service.context.resource.TrainingTemplateContext;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTemplateMapper;
import com.cyberpeace.cr.redvsblue.service.RbTemplateService;
import com.cyberpeace.cr.redvsblue.service.context.RbTemplateContext;

@Service
public class RbBusinessTemplateHandlerImpl extends BusinessTemplateHandler {

	@Autowired
	private RbTemplateService rbTemplateService;

	@Autowired
	private RbTemplateMapper rbTemplateMapper;

	@Autowired
	private MissionService missionService;

	@Autowired
	private MissionTemplateService missionTemplateService;

	@Override
	public BusinessType setBusinessType() {
		return BusinessType.RED_VS_BLUE;
	}

	@Override
	public String saveRealBusiness(BasicTemplate basicTemplate, String businessId) {
		RbTemplateContext rbTemplateContext = BeanUtils.copy(basicTemplate,
				RbTemplateContext.class);

		JSONObject roleObject = JsonUtil.toJsonObject(rbTemplateContext.getRoleList(),
				JSONObject.class);

		List<JSONObject> redRoleRsps = JsonUtil
				.toJsonObjectArray(roleObject.get("red_roles").toString(), JSONObject.class);

		List<JSONObject> blueRoleRsps = JsonUtil
				.toJsonObjectArray(roleObject.get("blue_roles").toString(), JSONObject.class);

		redRoleRsps.forEach(jsonObject -> missionService
				.missionsSchemeValidator(jsonObject.getJSONObject("missions_scheme")));
		blueRoleRsps.forEach(jsonObject -> missionService
				.missionsSchemeValidator(jsonObject.getJSONObject("missions_scheme")));

		RbTemplate rbTemplate = new RbTemplate();
		BaseInfo baseInfo = basicTemplate.getBaseInfo();

		rbTemplate.setBusinessTemplateId(businessId);
		rbTemplate.setPreSkill(JsonUtil.toJsonString(baseInfo.getPreSkill()));
		rbTemplate.setTrainSkill(JsonUtil.toJsonString(baseInfo.getTrainSkill()));
		rbTemplate.setSubType(baseInfo.getCategory());
		rbTemplate.setDurationTime(baseInfo.getDurationTime());
		rbTemplate.setRoleConfig(rbTemplateContext.getRoleList());

		rbTemplateService.save(rbTemplate);

		return rbTemplate.getBusinessTemplateId();
	}

	@Override
	public ResourceDumpContext realDumpBusiness(BusinessTemplate template) {
		ResourceDumpContext<RbTemplate> context = new ResourceDumpContext<>();
		// 获取 rbTemplate
		RbTemplate rbTemplate = rbTemplateService
				.getRbTemplateByBusinessId(template.getResourceId());
		rbTemplate.setId(null);
		context.setBusiness(rbTemplate);
		// 获取任务
		String missionScheme = rbTemplate.getRoleConfig();
		context.setMissions(missionTemplateService.batchGetByMissionSchemeJson(missionScheme));

		return context;
	}

	@Override
	public void realLoadBusinessResource(ResourceDumpContext resource) {
		RbTemplate rbTemplate = ((JSONObject) resource.getBusiness())
				.toJavaObject(RbTemplate.class);
		rbTemplate.setId(null);
		rbTemplate.setCreateTime(null);
		rbTemplate.setUpdateTime(null);
		if (null != rbTemplateService.getById(rbTemplate.getResourceId())) {
			rbTemplateService.updateById(rbTemplate);
		} else {
			rbTemplateService.save(rbTemplate);
		}
	}

	@Override
	public void deleteTemplateRelated(BusinessTemplate businessTemplate) {
		RbTemplate rbTemplate = rbTemplateMapper
				.selectByBusinessTemplateId(businessTemplate.getResourceId());
		// delete mission templates
		missionTemplateService.batchDelete(
				rbTemplateService.getMissionTemplateIdsByRoleConfig(rbTemplate.getRoleConfig()));
		rbTemplateMapper.deleteById(rbTemplate.getResourceId());
	}

	@Override
	public void loadTrainTemplate(TrainingTemplateContext templateContext) {
		RbTemplate template = BeanUtils.copy(templateContext, RbTemplate.class);
		template.setPreSkill(templateContext.getPreSkills());
		template.setTrainSkill(templateContext.getTrainSkills());
		rbTemplateMapper.insert(template);
	}
}
