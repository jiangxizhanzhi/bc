package com.cyberpeace.cr.tool.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CacheConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;
import com.cyberpeace.cr.tool.dao.mapper.ToolCategoryMapper;
import com.cyberpeace.cr.tool.dao.mapper.ToolMapper;
import com.cyberpeace.cr.tool.service.ToolCategoryService;

@Service
@CacheConfig(cacheNames = { CacheConstant.CR_TOOL_CATEGORY })
public class ToolCategoryServiceImpl extends ServiceImpl<ToolCategoryMapper, ToolCategory> implements ToolCategoryService {

	@Autowired
	private ToolCategoryMapper toolCategoryMapper;

	@Autowired
	private ToolMapper toolMapper;

	@Override
	public ListRsp<ToolCategory> selectPage(PageReqBase listReq) {
		Page page = listReq.toPageParam();
		IPage<ToolCategory> result = toolCategoryMapper.list(page, listReq.getPattern());
		return PageUtil.getListResult(result);
	}

	@Override
	@Cacheable
	public List<ToolCategory> selectAll() {
		List<ToolCategory> result = toolCategoryMapper.selectList(null);
		return result;
	}

	@Override
	@Cacheable(key = "#p0")
	public ToolCategory getByName(String name) {
		ToolCategory toolCategory = toolCategoryMapper.getByCnName(name);

		return toolCategory;
	}

	@Override
	@CacheEvict
	public ToolCategory saveCategory(String name) {
		ToolCategory newCategory = new ToolCategory();

		newCategory.setCnName(name);
		newCategory.setEnName(name);
		toolCategoryMapper.insert(newCategory);

		return newCategory;
	}

	@Override
	public void batchDelete(List<String> resourceIds) {
		// 查询该分类是否有被工具库使用
		for (String resourceId : resourceIds) {
			List<Tool> tools = toolMapper.selectByCategory(resourceId);
			if (CollectionUtils.isNotEmpty(tools)) {
				throw new BusinessException(CrBusinessErrorCode.TOOL_CATEGORY_IN_USE);
			}
		}
		removeByIds(resourceIds);
	}
}
