package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.service.context.RbTemplateContext;
import com.cyberpeace.cr.redvsblue.service.context.RbTemplateData;

public interface RbTemplateService extends IService<RbTemplate> {

	/**
	 * 从businessTemplateId获取红蓝模板
	 * @param businessTemplateId
	 * @return
	 */
	RbTemplate getRbTemplateByBusinessId(String businessTemplateId);

	RbTemplate update(RbTemplateContext rbTemplateContext, BusinessTemplate businessTemplate);


	List<RbTemplate> getRbTemplateByBusinessIds(List<String> ids);

	void batchDelete(List<String> ids, List<String> roomIds);

	IPage<RbTemplateData> page(ListQuery listQuery);
}
