package com.cyberpeace.oj.rtks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.model.PageReqBase;
import com.cyberpeace.oj.rtks.dao.entity.SkillSystem;

/**
 *
 */
public interface SkillSystemService extends IService<SkillSystem> {
    ListRsp<SkillSystem> page(PageReqBase pageReqBase);

}
