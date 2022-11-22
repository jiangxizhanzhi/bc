package com.cyberpeace.assess.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.assess.dao.entity.AssessTag;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;

/**
 *
 */
public interface AssessTagService extends IService<AssessTag> {

	ListRsp<AssessTag> page(PageReqBase pageReqBase);
}
