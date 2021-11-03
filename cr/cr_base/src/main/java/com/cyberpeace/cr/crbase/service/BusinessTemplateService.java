package com.cyberpeace.cr.crbase.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.model.BusinessTemplateRsp;
import com.cyberpeace.cr.crbase.model.Share;
import com.cyberpeace.cr.crbase.model.ShareReq;
import com.cyberpeace.cr.crbase.model.TemplateReq;

/**
 *
 */
public interface BusinessTemplateService extends IService<BusinessTemplate> {

	ListRsp<BusinessTemplateRsp> page(TemplateReq listReq);

	Share getShares(String resourceId);

	void batchSetShares(ShareReq shareReq);
}
