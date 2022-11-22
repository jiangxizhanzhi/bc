package com.cyberpeace.assess.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.assess.dao.entity.AssessData;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;

/**
 *
 */
public interface AssessDataService extends IService<AssessData> {

	ListRsp<AssessData> page(PageReqBase pageReqBase);

}
