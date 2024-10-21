package com.cyberpeace.cr.tool.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CacheConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.tool.controller.res.WebToolCategoryRsp;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.dao.entity.ToolCategory;
import com.cyberpeace.cr.tool.dao.mapper.ToolCategoryMapper;
import com.cyberpeace.cr.tool.dao.mapper.ToolMapper;
import com.cyberpeace.cr.tool.service.ToolCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@CacheConfig(cacheNames = { CacheConstant.CR_TOOL_CATEGORY })
public class ToolCategoryServiceImpl extends ServiceImpl<ToolCategoryMapper, ToolCategory> implements ToolCategoryService {

	@Autowired
	private ToolCategoryMapper toolCategoryMapper;

	@Autowired
	private ToolMapper toolMapper;

	@Override
	public ListRsp<ToolCategory> selectPage(PageReqBase listReq, String type) {
		Page page = listReq.toPageParam();
		IPage<ToolCategory> result = toolCategoryMapper.list(page, listReq.getPattern(), type);
		return PageUtil.getListResult(result);
	}

	@Override
	@Cacheable
	public List<ToolCategory> selectAll() {
		return toolCategoryMapper.selectList(null);
	}

	public List<WebToolCategoryRsp> webSelectAll(String type) {
		List<ToolCategory> toolCategories = selectAll();
		if (StringUtils.isNotBlank(type)) {
			toolCategories = StreamApiUtils.filter(toolCategories, c -> type.equals(c.getType()));
		}
		return toolCategories.stream()
				.map(toolCategory -> WebToolCategoryRsp.builder()
						.resourceId(toolCategory.getResourceId())
						.categoryI18nName(toolCategory.selectI18nName()).build())
				.collect(Collectors.toList());
	}

	@Override
	@Cacheable(key = "#p0")
	public ToolCategory getByName(String name) {

		return toolCategoryMapper.getByCnName(name, null);
	}

	@Override
	@CacheEvict(allEntries = true)
	public ToolCategory saveCategory(String name) {
		ToolCategory newCategory = new ToolCategory();

		newCategory.setCnName(name);
		newCategory.setEnName(name);
		toolCategoryMapper.insert(newCategory);

		return newCategory;
	}

	@Override
	@CacheEvict(allEntries = true)
	public void batchDelete(List<String> resourceIds) {
		// 查询该分类是否有被工具库使用
		for (String resourceId : resourceIds) {
			List<Tool> tools = toolMapper.selectByCategory(resourceId);
			if (CollectionUtils.isNotEmpty(tools)) {
				log.error("resourceId is null,resourceId =[{}]", resourceId);
				throw new BusinessException(CrBusinessErrorCode.TOOL_CATEGORY_IN_USE);
			}
		}
		removeByIds(resourceIds);
	}

	@Override
	public void validateToolCategoryNameExist(ToolCategory toolCategory) {
		// 名称重复性校验
		ToolCategory toolCategoryObject = toolCategoryMapper.getByCnName(toolCategory.getCnName(), toolCategory.getResourceId());
		if (toolCategoryObject != null) {
			log.error("toolCategoryObject is not null,toolCategoryObject =[{}]", JsonUtil.toJsonString(toolCategoryObject));
			throw new BusinessException(CrBusinessErrorCode.DUPLICATED_TOOL_CATEGORY_NAME);
		}
		// 英文名称重复性校验
	}

	@Override
	@CacheEvict(allEntries = true)
	public void saveToolCategory(ToolCategory toolCategory){
		toolCategoryMapper.insert(toolCategory);
	}

	@Override
	@CacheEvict(allEntries = true)
	public void updateToolCategoryById(ToolCategory toolCategory){
		validateToolCategoryNameExist(toolCategory);
		toolCategoryMapper.updateById(toolCategory);
	}
}
