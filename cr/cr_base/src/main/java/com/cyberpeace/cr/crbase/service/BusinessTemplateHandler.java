package com.cyberpeace.cr.crbase.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
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
		BusinessTemplate.BusinessType businessType = getBusinessType(basicTemplate);

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

	private BusinessTemplate.BusinessType getBusinessType(BasicTemplate basicTemplate) {
		// 校验外部传入的类型在BusinessTemplate.BusinessType声明的范围内
		BusinessTemplate.BusinessType enumByCode = EnumUtils.getEnumByCode(
				basicTemplate.getBaseInfo().getCategory(),
				BusinessTemplate.BusinessType.class);
		if (enumByCode == null) {
			throw new BusinessException(CrBusinessErrorCode.TEMPLETE_TYPE_NOT_SUPPORTED);
		}
		return enumByCode;

	}
}
