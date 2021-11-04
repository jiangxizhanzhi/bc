package com.cyberpeace.cr.crbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.model.BusinessTemplateRsp;
import com.cyberpeace.cr.crbase.model.Share;
import com.cyberpeace.cr.crbase.model.ShareReq;
import com.cyberpeace.cr.crbase.model.TemplateReq;
import com.cyberpeace.cr.crbase.service.context.BusinessTemplateContext;

/**
 *
 */
public interface BusinessTemplateService extends IService<BusinessTemplate> {

	/** 分页获取团队训练模板
	 * @param listReq 
	 * @return
	 */
	ListRsp<BusinessTemplateContext> page(TemplateReq listReq);

	/** 获取权限团队训练的操作权限人员
	 * @param resourceId 
	 * @return
	 */
	Share getShares(String resourceId);

	/** 批量修改多个团队训练的权限
	 * @param shareReq 
	 */
	void batchSetShares(ShareReq shareReq);
}
