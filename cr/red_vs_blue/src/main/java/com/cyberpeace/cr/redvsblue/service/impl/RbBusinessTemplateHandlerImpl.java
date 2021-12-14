package com.cyberpeace.cr.redvsblue.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateHandler;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.service.RbTemplateService;
import com.cyberpeace.cr.redvsblue.service.context.RbTemplateContext;

@Service
public class RbBusinessTemplateHandlerImpl extends BusinessTemplateHandler {

	@Autowired
	private RbTemplateService rbTemplateService;

	@Override
	public BusinessTemplate.BusinessType setBusinessType() {
		return BusinessTemplate.BusinessType.RED_VS_BLUE;
	}

	@Override
	public String saveRealBusiness(BasicTemplate basicTemplate, String businessId) {
		RbTemplateContext rbTemplateContext = BeanUtils.copy(basicTemplate,
				RbTemplateContext.class);

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
}
