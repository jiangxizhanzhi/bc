package com.cyberpeace.cr.crbase.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.context.resource.ResourceDumpContext;
import com.cyberpeace.cr.crbase.service.context.resource.TrainingTemplateContext;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import com.google.common.collect.Maps;

/**
 * 业务模板处理程序
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Service
public class BusinessTemplateHandler {

	private static Map<BusinessTemplate.BusinessType, BusinessTemplateHandler> register = Maps
			.newHashMap();

	public BusinessTemplate.BusinessType setBusinessType() {
		return null;
	}

	public BusinessTemplateHandler getHandler(Integer type) {
		BusinessTemplate.BusinessType businessType = getBusinessType(type);
		return register.get(businessType);
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
	 *
	 * 保存实际的业务模板。如红蓝对抗、团队训练
	 *
	 * @param basicTemplate 基本模板
	 * @param businessId    业务标识
	 * @return {@link String}
	 */
	public String saveRealBusiness(BasicTemplate basicTemplate, String businessId) {
		// 业务定义自己的保存操作
		return "";

	}

	/**
	 * 获取业务类型
	 *
	 * @param businessType 业务类型
	 * @return {@link BusinessTemplate.BusinessType}
	 */
	private BusinessTemplate.BusinessType getBusinessType(Integer businessType) {
		// 校验外部传入的类型在BusinessTemplate.BusinessType声明的范围内
		BusinessTemplate.BusinessType enumByCode = EnumUtils.getEnumByCode(businessType,
				BusinessTemplate.BusinessType.class);
		if (enumByCode == null) {
			throw new BusinessException(CrBusinessErrorCode.TEMPLATE_TYPE_NOT_SUPPORTED);
		}
		return enumByCode;

	}

	/**
	 * 导出业务
	 *
	 * @param template 模板
	 * @return {@link ResourceDumpContext}
	 */
	public ResourceDumpContext dumpBusiness(BusinessTemplate template) {
		BusinessTemplateHandler handler = getHandler(template.getBusinessType());
		return handler.realDumpBusiness(template);

	}

	public ResourceDumpContext realDumpBusiness(BusinessTemplate template) {
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	/**
	 * 导入业务资源
	 *
	 * @param resource 资源
	 */
	public void loadBusinessResource(ResourceDumpContext resource) {
		BusinessTemplateHandler handler = getHandler(resource.getType());
		handler.realLoadBusinessResource(resource);
	}

	public void realLoadBusinessResource(ResourceDumpContext resource) {
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	/**
	 * 删除后台模板时删除相关训练模板
	 * @param businessTemplate
	 */
	public void deleteTemplateRelated(BusinessTemplate businessTemplate) {
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	/**
	 * 用于资管导入不同类型的训练模板
	 * @param templateContext
	 */
	public void loadTrainTemplate(TrainingTemplateContext templateContext) {
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	public void checkBeforeDel(BusinessTemplate businessTemplate) {}
}
