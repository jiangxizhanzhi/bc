package com.cyberpeace.cr.redvsblue.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;

public interface RbTemplateService extends IService<RbTemplate> {

	/**
	 * 从businessTemplateId获取红蓝模板
	 * @param businessTemplateId
	 * @return
	 */
	RbTemplate getByBusinessTemplateId(String businessTemplateId);
}
