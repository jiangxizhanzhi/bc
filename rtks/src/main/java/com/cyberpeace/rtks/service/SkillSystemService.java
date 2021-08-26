package com.cyberpeace.rtks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.rtks.dao.entity.SkillSystem;

/**
 *
 */
public interface SkillSystemService extends IService<SkillSystem> {

	ListRsp<SkillSystem> page(PageReqBase pageReqBase);

}
