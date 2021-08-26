package com.cyberpeace.oj.rtks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.oj.rtks.dao.entity.SkillCategory;

/**
 *
 */
public interface SkillCategoryService extends IService<SkillCategory> {

	ListRsp<SkillCategory> page(PageReqBase pageReqBase);
}
