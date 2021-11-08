package com.cyberpeace.cr.crbase.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.google.common.collect.Maps;

@Service
public class BusinessTemplateHandler {

	private Map<BusinessTemplate.BusinessType, BusinessTemplateHandler> register = Maps
			.newHashMap();

	public BusinessTemplate.BusinessType setBusinessType() {
		return null;
	}

	@PostConstruct
	public void init() {
		register.put(setBusinessType(), this);
	}

	public void saveBusiness(Object obj) {
		BusinessTemplate.BusinessType businessType = getBusinessType(obj);

		BusinessTemplateHandler realHandler = register.get(businessType);

		realHandler.saveRealBusiness(obj);
	}

	/**
	 * 保存实际的业务模板。如红蓝对抗、团队训练
	 */
	public void saveRealBusiness(Object obj) {
		// 业务定义自己的保存操作
	}

	private BusinessTemplate.BusinessType getBusinessType(Object obj) {
		return null;
	}
}
