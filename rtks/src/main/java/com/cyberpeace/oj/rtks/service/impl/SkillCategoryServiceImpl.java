package com.cyberpeace.oj.rtks.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.model.PageReqBase;
import com.cyberpeace.oj.common.util.PageUtil;
import com.cyberpeace.oj.rtks.dao.entity.SkillCategory;
import com.cyberpeace.oj.rtks.dao.mapper.SkillCategoryMapper;
import com.cyberpeace.oj.rtks.service.SkillCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class SkillCategoryServiceImpl extends ServiceImpl<SkillCategoryMapper, SkillCategory> implements SkillCategoryService {

	@Autowired
	private SkillCategoryMapper skillCategoryMapper;

	@Override
	public ListRsp<SkillCategory> page(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<SkillCategory> result = skillCategoryMapper.listSkillCategory(page,
				pageReqBase.getPattern());

		return PageUtil.getListResult(result);
	}
}
