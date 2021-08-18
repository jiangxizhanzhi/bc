package com.cyberpeace.oj.rtks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.model.PageReqBase;
import com.cyberpeace.oj.rtks.dao.entity.SkillCategary;

/**
 *
 */
public interface SkillCategaryService extends IService<SkillCategary> {
    ListRsp<SkillCategary> page(PageReqBase pageReqBase);
}
