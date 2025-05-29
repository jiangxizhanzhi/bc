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
/**
 * 这段代码是一个Java类，名为`ToolCategoryServiceImpl`，位于`com.cyberpeace.cr.tool.service.impl`包中。这个类实现了`ToolCategoryService`接口，并且继承了`ServiceImpl<ToolCategoryMapper, ToolCategory>`。以下是对其主要功能和结构的简要分析： 
 * ### 主要功能 
 * 1. **数据访问层（DAO）**：通过`ToolCategoryMapper`和`ToolMapper`与数据库进行交互。 
 * 2. **缓存管理**：使用Spring的缓存注解（`@Cacheable`, `@CacheEvict`）来优化性能。 
 * 3. **业务逻辑处理**： 
 * - 分页查询工具类别信息。 
 * - 查询所有工具类别信息。 
 * - 根据类型获取工具类别信息。 
 * - 保存、更新、删除工具类别。 
 * - 验证工具类别名称是否重复。 
 * ### 主要方法 
 * 1. **selectPage**：分页查询工具类别信息。 
 * 2. **selectAll**：查询所有工具类别信息。 
 * 3. **webSelectAll**：前台查询所有工具类别信息。 
 * 4. **getByName**：根据名称查询工具类别信息。 
 * 5. **getByType**：根据类型查询工具类别信息。 
 * 6. **saveCategory**：保存新的工具类别信息。 
 * 7. **batchDelete**：批量删除工具类别信息。 
 * 8. **validateToolCategoryNameExist**：验证工具类别名称是否存在。 
 * 9. **saveToolCategory**：保存工具类别信息。 
 * 10. **updateToolCategoryById**：更新工具类别信息。 
 * 11. **getAllTypeCategory**：获取所有工具类型的类别信息。 
 * ### 技术点 
 * - **MyBatis Plus**：用于简化数据库操作，如分页查询等。 
 * - **Lombok**：通过`@Slf4j`注解自动生成日志记录器。 
 * - **Apache Commons Collections**：用于处理集合相关的操作。 
 * - **Spring Cache**：通过注解管理缓存，提高应用性能。 
 * ### 总结 
 * 这个类的主要作用是提供一套完整的工具类别管理服务，包括CRUD操作以及缓存管理。通过MyBatis Plus和Spring Cache，提高了系统的性能和可维护性。 
**/
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

