package com.cyberpeace.assess.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.assess.controller.req.AssessListReq;
import com.cyberpeace.assess.dao.entity.AssessModel;

/**
 *
 */
public interface AssessModelService extends IService<AssessModel> {

	IPage<AssessModel> page(AssessListReq pageReqBase);

	String getDataBaseTable(AssessModel resourceId);
}
