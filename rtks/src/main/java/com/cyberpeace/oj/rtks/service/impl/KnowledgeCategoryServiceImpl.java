package com.cyberpeace.oj.rtks.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.model.PageReqBase;
import com.cyberpeace.oj.common.util.PageUtil;
import com.cyberpeace.oj.rtks.dao.entity.KnowledgeCategory;
import com.cyberpeace.oj.rtks.dao.mapper.KnowledgeCategoryMapper;
import com.cyberpeace.oj.rtks.service.KnowledgeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class KnowledgeCategoryServiceImpl extends ServiceImpl<KnowledgeCategoryMapper, KnowledgeCategory> implements KnowledgeCategoryService {

	@Autowired
	private KnowledgeCategoryMapper knowledgeCategoryMapper;

	@Override
	public ListRsp<KnowledgeCategory> page(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<KnowledgeCategory> result = knowledgeCategoryMapper.listKnowledgeCategory(page,
				pageReqBase.getPattern());

		return PageUtil.getListResult(result);
	}

}
