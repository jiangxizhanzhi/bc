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
 * 知识点类别Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see ServiceImpl
 * @see KnowledgeCategoryService
 * @since 1.0.0
 */
@Service
public class KnowledgeCategoryServiceImpl extends ServiceImpl<KnowledgeCategoryMapper, KnowledgeCategory> implements KnowledgeCategoryService {

	/**
	 * 知识点类别Mapper
	 *
	 * @see KnowledgeCategoryMapper
	 */
	@Autowired
	private KnowledgeCategoryMapper knowledgeCategoryMapper;

	/**
	 * 分页
	 *
	 * @param pageReqBase 分页请求体基础
	 * @return {@code ListRsp<KnowledgeCategory> }
	 * @throws
	 * @see PageReqBase
	 * @see ListRsp<KnowledgeCategory>
	 */
	@Override
	public ListRsp<KnowledgeCategory> page(PageReqBase pageReqBase) {
		Page page = pageReqBase.toPageParam();
		IPage<KnowledgeCategory> result = knowledgeCategoryMapper.listKnowledgeCategory(page,pageReqBase.getPattern());
		return PageUtil.getListResult(result);
	}

	/**
	 * 获取由内容
	 *
	 * @param content 内容
	 * @return {@code List<KnowledgeCategory> }
	 * @throws
	 * @see String
	 * @see List<KnowledgeCategory>
	 */
	@Override
	public List<KnowledgeCategory> getByContent(String content) {
		return knowledgeCategoryMapper.getByContent(content);
	}

}
