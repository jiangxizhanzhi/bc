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
import com.cyberpeace.cr.tool.service.context.ToolTypeCategory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 工具类别Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
@Service
@CacheConfig(cacheNames = { CacheConstant.CR_TOOL_CATEGORY })
public class ToolCategoryServiceImpl extends ServiceImpl<ToolCategoryMapper, ToolCategory> implements ToolCategoryService {

	/**
	 * 工具类别Mapper
	 */
	@Autowired
	private ToolCategoryMapper toolCategoryMapper;

	/**
	 * 工具Mapper
	 */
	@Autowired
	private ToolMapper toolMapper;

	/**
	 * 查询分页
	 *
	 * @param listReq 列表请求体
	 * @param type    类型
	 * @return {@code ListRsp<ToolCategory> }
	 */
	@Override
	public ListRsp<ToolCategory> selectPage(PageReqBase listReq, String type) {
		Page page = listReq.toPageParam();
		IPage<ToolCategory> result = toolCategoryMapper.list(page, listReq.getPattern(), type);
		return PageUtil.getListResult(result);
	}

	/**
	 * 查询全部
	 *
	 * @return {@code List<ToolCategory> }
	 */
	@Override
	@Cacheable
	public List<ToolCategory> selectAll() {
		return toolCategoryMapper.selectList(null);
	}

	/**
	 * 前台查询全部
	 *
	 * @param type 类型
	 * @return {@code List<WebToolCategoryRsp> }
	 */
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

	/**
	 * 获取由名称
	 *
	 * @param name 名称
	 * @return {@code ToolCategory }
	 */
	@Override
	@Cacheable(key = "#p0")
	public ToolCategory getByName(String name, String type) {
		return toolCategoryMapper.getByCnNameType(name, null, type);
	}

	/**
	 * 获取由类型
	 *
	 * @param type 类型
	 * @return {@code ToolCategory }
	 */
	@Override
	@Cacheable
	public List<ToolCategory> getByType(String type) {
		return toolCategoryMapper.getByType(type);
	}

	/**
	 * 保存类别
	 *
	 * @param name 名称
	 * @return {@code ToolCategory }
	 */
	@Override
	@CacheEvict(allEntries = true)
	public ToolCategory saveCategory(String name, String type) {
		ToolCategory newCategory = new ToolCategory();

		newCategory.setCnName(name);
		newCategory.setEnName(name);
		newCategory.setType(type);
		toolCategoryMapper.insert(newCategory);

		return newCategory;
	}

	/**
	 * 批量删除
	 *
	 * @param resourceIds 资源id列表
	 */
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

	/**
	 * 验证工具类别名称存在
	 *
	 * @param toolCategory 工具类别
	 */
	@Override
	public void validateToolCategoryNameExist(ToolCategory toolCategory) {
		// 名称重复性校验
		ToolCategory toolCategoryObject = toolCategoryMapper.getByCnName(toolCategory.getCnName(), toolCategory.getResourceId());
		if (toolCategoryObject != null && toolCategory.getType().equals(toolCategoryObject.getType())) {
			log.error("toolCategoryObject is not null,toolCategoryObject =[{}]", JsonUtil.toJsonString(toolCategoryObject));
			throw new BusinessException(CrBusinessErrorCode.DUPLICATED_TOOL_CATEGORY_NAME);
		}
		// 英文名称重复性校验
	}

	/**
	 * 保存工具类别
	 *
	 * @param toolCategory 工具类别
	 */
	@Override
	@CacheEvict(allEntries = true)
	public void saveToolCategory(ToolCategory toolCategory){
		toolCategoryMapper.insert(toolCategory);
	}

	/**
	 * 更新工具类别由id
	 *
	 * @param toolCategory 工具类别
	 */
	@Override
	@CacheEvict(allEntries = true)
	public void updateToolCategoryById(ToolCategory toolCategory){
		validateToolCategoryNameExist(toolCategory);
		toolCategoryMapper.updateById(toolCategory);
	}

	/**
	 * 获取全部类型类别
	 *
	 * @return {@code List<ToolTypeCategory> }
	 */
	@Override
	public List<ToolTypeCategory> getAllTypeCategory() {
		List<ToolCategory> toolCategories = selectAll();

		List<ToolTypeCategory> res = Lists.newArrayList();
		for (Tool.Type type : Tool.Type.values()) {
			List<ToolCategory> list = StreamApiUtils.filter(toolCategories,
					c -> type.getCode().equals(c.getType()));
			ToolTypeCategory toolTypeCategory = new ToolTypeCategory();
			toolTypeCategory.setType(type.getCode());
			toolTypeCategory.setTypeName(type.getDesc());
			toolTypeCategory.setCategories(StreamApiUtils.map(list,
					c -> WebToolCategoryRsp.builder().resourceId(c.getResourceId())
							.categoryI18nName(c.getCnName()).build()));

			res.add(toolTypeCategory);
		}

		return res;
	}
}
