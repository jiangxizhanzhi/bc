package com.cyberpeace.cr.crbase.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import com.cyberpeace.common.enums.BusinessType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.context.resource.ResourceDumpContext;
import com.cyberpeace.cr.crbase.service.context.resource.TrainingTemplateContext;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import com.google.common.collect.Maps;

@Service
@Slf4j
public class BusinessTemplateHandler {

	private static Map<BusinessType, BusinessTemplateHandler> register = Maps
			.newHashMap();

	public BusinessType setBusinessType() {
		return null;
	}

	public BusinessTemplateHandler getHandler(Integer type) {
		BusinessType businessType = getBusinessType(type);
		return register.get(businessType);
	}

	@PostConstruct
	public void init() {
		register.put(setBusinessType(), this);
	}

	public String saveBusiness(BasicTemplate basicTemplate, String businessId) {
		BusinessType businessType = getBusinessType(
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

	private BusinessType getBusinessType(Integer businessType) {
		// 校验外部传入的类型在BusinessTemplate.BusinessType声明的范围内
		BusinessType enumByCode = EnumUtils.getEnumByCode(businessType,
				BusinessType.class);
		if (enumByCode == null) {
			throw new BusinessException(CrBusinessErrorCode.TEMPLATE_TYPE_NOT_SUPPORTED);
		}
		return enumByCode;

	}

	public ResourceDumpContext dumpBusiness(BusinessTemplate template) {
		BusinessTemplateHandler handler = getHandler(template.getBusinessType());
		return handler.realDumpBusiness(template);

	}

	public ResourceDumpContext realDumpBusiness(BusinessTemplate template) {
		log.error("method not implemented, plz do not use it");
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	public void loadBusinessResource(ResourceDumpContext resource) {
		BusinessTemplateHandler handler = getHandler(resource.getType());
		handler.realLoadBusinessResource(resource);
	}

	public void realLoadBusinessResource(ResourceDumpContext resource) {
		log.error("method not implemented, plz do not use it");
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	/**
	 * 删除后台模板时删除相关训练模板
	 * @param businessTemplate
	 */
	public void deleteTemplateRelated(BusinessTemplate businessTemplate) {
		log.error("method not implemented, plz do not use it");
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	/**
	 * 用于资管导入不同类型的训练模板
	 * @param templateContext
	 */
	public void loadTrainTemplate(TrainingTemplateContext templateContext) {
		log.error("method not implemented, plz do not use it");
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	public void checkBeforeDel(BusinessTemplate businessTemplate) {}
}
