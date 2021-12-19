package com.cyberpeace.cr.crbase.service;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.context.LoadTemplateData;
import com.cyberpeace.cr.crbase.service.context.resource.ResourceDumpContext;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import com.google.common.collect.Maps;

@Service
public class BusinessTemplateHandler {

	private static Map<BusinessTemplate.BusinessType, BusinessTemplateHandler> register = Maps
			.newHashMap();

	public BusinessTemplate.BusinessType setBusinessType() {
		return null;
	}

	@PostConstruct
	public void init() {
		register.put(setBusinessType(), this);
	}

	public String saveBusiness(BasicTemplate basicTemplate, String businessId) {
		BusinessTemplate.BusinessType businessType = getBusinessType(
				basicTemplate.getBaseInfo().getType());

		BusinessTemplateHandler realHandler = register.get(businessType);

		return realHandler.saveRealBusiness(basicTemplate, businessId);
	}

	/**
	 * 保存实际的业务模板。如红蓝对抗、团队训练
	 */
	public String saveRealBusiness(BasicTemplate basicTemplate, String businessId) {
		// 业务定义自己的保存操作
		return "";

	}

	private BusinessTemplate.BusinessType getBusinessType(Integer businessType) {
		// 校验外部传入的类型在BusinessTemplate.BusinessType声明的范围内
		BusinessTemplate.BusinessType enumByCode = EnumUtils.getEnumByCode(businessType,
				BusinessTemplate.BusinessType.class);
		if (enumByCode == null) {
			throw new BusinessException(CrBusinessErrorCode.TEMPLATE_TYPE_NOT_SUPPORTED);
		}
		return enumByCode;

	}

	public void loads(LoadTemplateData templateData, List<MissionTemplate> missionTemplateList) {
		formatData(templateData);

		BusinessTemplate.BusinessType businessType = getBusinessType(templateData.getType());
		BusinessTemplateHandler realHandler = register.get(businessType);
		realHandler.realLoads(templateData, missionTemplateList);

	}

	public void realLoads(LoadTemplateData templateData,
			List<MissionTemplate> missionTemplateList) {
		// 业务定义自己的导入操作
	}

	private void formatData(LoadTemplateData templateData) {
		// 1、团队将people_number的值设置到base_info中，兼容从页面生成到新建请求
		Integer peopleNumber = templateData.getTemplateData().getInteger("people_number");
		templateData.getTemplateData().getJSONObject("base_info").put("people_number",
				peopleNumber);
	}

	public ResourceDumpContext dumpBusiness(BusinessTemplate template) {
		BusinessTemplate.BusinessType type = getBusinessType(template.getBusinessType());
		BusinessTemplateHandler handler = register.get(type);
		return handler.dumpBusiness(template);
	}

}
