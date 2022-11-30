package com.cyberpeace.assess.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.assess.controller.req.AssessListReq;
import com.cyberpeace.assess.dao.entity.AssessData;

/**
 *
 */
public interface AssessDataService extends IService<AssessData> {

    IPage<AssessData> page(AssessListReq pageReqBase);

    void checkNameRepeat(String name, String resourceId);
}
