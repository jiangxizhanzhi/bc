package com.cyberpeace.rtks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.rtks.dao.entity.SkillCategory;

/**
 *
 */
public interface SkillCategoryService extends IService<SkillCategory> {

	ListRsp<SkillCategory> page(PageReqBase pageReqBase);

}
