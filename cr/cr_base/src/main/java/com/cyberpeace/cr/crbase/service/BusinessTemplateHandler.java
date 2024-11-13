package com.cyberpeace.cr.crbase.service;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.context.resource.ResourceDumpContext;
import com.cyberpeace.cr.crbase.service.context.resource.TrainingTemplateContext;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 业务模板处理器
 *
 * @author SN
 * @date 2024/11/13
 */
@Service
@Slf4j
public class BusinessTemplateHandler {

	/**
	 * 未实施
	 */
	private static final String NOT_IMPLEMENTED = "method not implemented, plz do not use it";

	/**
	 * 注册
	 */
	private static Map<BusinessType, BusinessTemplateHandler> register = Maps
			.newHashMap();

	/**
	 * 设置业务类型
	 *
	 * @return {@code BusinessType }
	 */
	public BusinessType setBusinessType() {
		return null;
	}

	/**
	 * 获取处理器
	 *
	 * @param type 类型
	 * @return {@code BusinessTemplateHandler }
	 */
	public BusinessTemplateHandler getHandler(Integer type) {
		BusinessType businessType = getBusinessType(type);
		return register.get(businessType);
	}

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		register.put(setBusinessType(), this);
	}

	/**
	 * 保存业务
	 *
	 * @param basicTemplate 基本模板
	 * @param businessId    业务id
	 * @return {@code String }
	 */
	public String saveBusiness(BasicTemplate basicTemplate, String businessId) {
		BusinessType businessType = getBusinessType(
				basicTemplate.getBaseInfo().getType());

		BusinessTemplateHandler realHandler = register.get(businessType);

		return realHandler.saveRealBusiness(basicTemplate, businessId);
	}

	/**
	 * 保存实际的业务模板。如红蓝对抗、团队训练
	 *
	 * @param basicTemplate 基本模板
	 * @param businessId    业务id
	 * @return {@code String }
	 */
	public String saveRealBusiness(BasicTemplate basicTemplate, String businessId) {
		// 业务定义自己的保存操作
		return "";

	}

	/**
	 * 获取业务类型
	 *
	 * @param businessType 业务类型
	 * @return {@code BusinessType }
	 */
	private BusinessType getBusinessType(Integer businessType) {
		// 校验外部传入的类型在BusinessTemplate.BusinessType声明的范围内
		BusinessType enumByCode = EnumUtils.getEnumByCode(businessType,
				BusinessType.class);
		if (enumByCode == null) {
			throw new BusinessException(CrBusinessErrorCode.TEMPLATE_TYPE_NOT_SUPPORTED);
		}
		return enumByCode;

	}

	/**
	 * 导出业务
	 *
	 * @param template 模板
	 * @return {@code ResourceDumpContext }
	 */
	public ResourceDumpContext dumpBusiness(BusinessTemplate template) {
		BusinessTemplateHandler handler = getHandler(template.getBusinessType());
		return handler.realDumpBusiness(template);

	}

	/**
	 * 真实导出业务
	 *
	 * @param template 模板
	 * @return {@code ResourceDumpContext }
	 */
	public ResourceDumpContext realDumpBusiness(BusinessTemplate template) {
		log.error(NOT_IMPLEMENTED);
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	/**
	 * 加载业务资源
	 *
	 * @param resource 资源
	 */
	public void loadBusinessResource(ResourceDumpContext resource) {
		BusinessTemplateHandler handler = getHandler(resource.getType());
		handler.realLoadBusinessResource(resource);
	}

	/**
	 * 真实加载业务资源
	 *
	 * @param resource 资源
	 */
	public void realLoadBusinessResource(ResourceDumpContext resource) {
		log.error(NOT_IMPLEMENTED);
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	/**
	 * 删除后台模板时删除相关训练模板
	 *
	 * @param businessTemplate 业务模板
	 */
	public void deleteTemplateRelated(BusinessTemplate businessTemplate) {
		log.error(NOT_IMPLEMENTED);
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	/**
	 * 用于资管导入不同类型的训练模板
	 *
	 * @param templateContext 模板上下文
	 */
	public void loadTrainTemplate(TrainingTemplateContext templateContext) {
		log.error(NOT_IMPLEMENTED);
		throw new BusinessException(CrBusinessErrorCode.NOT_IMPLEMENTED_EXCEPTION);
	}

	/**
	 * 在 del 之前检查
	 *
	 * @param businessTemplate 业务模板
	 */
	public void checkBeforeDel(BusinessTemplate businessTemplate) {
		// do nothing
	}
}
