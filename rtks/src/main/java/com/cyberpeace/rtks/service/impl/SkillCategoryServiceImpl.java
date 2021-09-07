package com.cyberpeace.rtks.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.rtks.dao.entity.SkillCategory;
import com.cyberpeace.rtks.dao.mapper.SkillCategoryMapper;
import com.cyberpeace.rtks.service.SkillCategoryService;

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
