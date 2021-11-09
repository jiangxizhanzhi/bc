package com.cyberpeace.cr.crbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplateData;
import com.cyberpeace.cr.crbase.service.context.BusinessTemplateQuery;
import com.cyberpeace.cr.crbase.service.context.Share;
import com.cyberpeace.cr.crbase.service.context.ShareContext;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;

/**
 *
 */
public interface BusinessTemplateService extends IService<BusinessTemplate> {

	/** 分页获取团队训练模板
	 * @param businessTemplateQuery
	 * @return
	 */
	ListRsp<BusinessTemplateData> page(BusinessTemplateQuery businessTemplateQuery);

	/** 获取权限团队训练的操作权限人员
	 * @param resourceId 
	 * @return
	 */
	Share getShares(String resourceId);

	/** 批量修改多个团队训练的权限
	 * @param shareContext
	 */
	void batchSetShares(ShareContext shareContext);

	String addTemplate(BasicTemplate basicTemplate);

}
