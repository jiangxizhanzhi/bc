package com.cyberpeace.assess.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.assess.dao.entity.AssessModel;
import com.cyberpeace.common.model.PageReqBase;

/**
 *
 */
public interface AssessModelService extends IService<AssessModel> {

	IPage<AssessModel> page(PageReqBase pageReqBase);

	String getDataBaseTable(AssessModel resourceId);
}
