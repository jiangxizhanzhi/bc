package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
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

/**
 * RB业务模板处理器实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
public class RbBusinessTemplateHandlerImpl extends BusinessTemplateHandler {

	/**
	 * RB模板Service
	 */
	@Autowired
	private RbTemplateService rbTemplateService;

	/**
	 * RB模板Mapper
	 */
	@Autowired
	private RbTemplateMapper rbTemplateMapper;

	/**
	 * 任务Service
	 */
	@Autowired
	private MissionService missionService;

	/**
	 * 任务模板Service
	 */
	@Autowired
	private MissionTemplateService missionTemplateService;

	/**
	 * 设置业务类型
	 *
	 * @return {@code BusinessTemplate.BusinessType }
	 */
	@Override
	public BusinessTemplate.BusinessType setBusinessType() {
		return BusinessTemplate.BusinessType.RED_VS_BLUE;
	}

	/**
	 * 保存真正业务
	 *
	 * @param basicTemplate 基本模板
	 * @param businessId    业务id
	 * @return {@code String }
	 */
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

		redRoleRsps.forEach(jsonObject -> {
			missionService.missionsSchemeValidator(jsonObject.getJSONObject("missions_scheme"));
		});
		blueRoleRsps.forEach(jsonObject -> {
			missionService.missionsSchemeValidator(jsonObject.getJSONObject("missions_scheme"));
		});

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

	/**
	 * 真正转 储业务
	 *
	 * @param template 模板
	 * @return {@code ResourceDumpContext }
	 */
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

	/**
	 * 真正加载业务资源
	 *
	 * @param resource 资源
	 */
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

	/**
	 * 删除模板相关
	 *
	 * @param businessTemplate 业务模板
	 */
	@Override
	public void deleteTemplateRelated(BusinessTemplate businessTemplate) {
		RbTemplate rbTemplate = rbTemplateMapper
				.selectByBusinessTemplateId(businessTemplate.getResourceId());
		// delete mission templates
		missionTemplateService.batchDelete(
				rbTemplateService.getMissionTemplateIdsByRoleConfig(rbTemplate.getRoleConfig()));
		rbTemplateMapper.deleteById(rbTemplate.getResourceId());
	}

	/**
	 * 加载训练模板
	 *
	 * @param templateContext 模板上下文
	 */
	@Override
	public void loadTrainTemplate(TrainingTemplateContext templateContext) {
		RbTemplate template = BeanUtils.copy(templateContext, RbTemplate.class);
		// skill字段名不统一， todo
		template.setPreSkill(templateContext.getPreSkills());
		template.setTrainSkill(templateContext.getTrainSkills());
		rbTemplateMapper.insert(template);
	}
}
