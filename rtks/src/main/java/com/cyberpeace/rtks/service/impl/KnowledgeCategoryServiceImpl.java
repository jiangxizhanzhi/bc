package com.cyberpeace.rtks.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.rtks.dao.entity.KnowledgeCategory;
import com.cyberpeace.rtks.dao.mapper.KnowledgeCategoryMapper;
import com.cyberpeace.rtks.service.KnowledgeCategoryService;

import java.util.List;

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
		IPage<KnowledgeCategory> result = knowledgeCategoryMapper.listKnowledgeCategory(page,pageReqBase.getPattern());
		return PageUtil.getListResult(result);
	}

	@Override
	public List<KnowledgeCategory> getByContent(String content) {
		return knowledgeCategoryMapper.getByContent(content);
	}

}
