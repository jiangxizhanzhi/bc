package com.cyberpeace.oj.course.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.bo.CategoryAddBo;
import com.cyberpeace.oj.course.service.bo.CourseCategoryBo;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 课程类别Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see ServiceImpl
 * @see CourseCategoryService
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseCategoryServiceImpl` 的服务类，实现了 `CourseCategoryService` 接口。该服务主要用于管理课程类别相关的业务逻辑。以下是代码的主要功能和结构分析： 
 * ### 主要功能 
 * 1. **获取子级ID列表 (`getChildrenIds`)**: 
 * - 根据给定的方向ID和是否包含自身来获取子级类别ID列表。 
 * 2. **查询所有类别 (`selectAll`)**: 
 * - 查询所有类别，并根据参数决定是否递归获取子级类别。 
 * 3. **分页查询并搜索 (`pageBySearch`)**: 
 * - 根据分页参数、搜索模式和是否包含子级来查询课程类别，并进行数据转换。 
 * 4. **保存或更新类别 (`saveOrUpdate`)**: 
 * - 保存或更新一个课程类别。如果类别已存在且是内置的，则抛出异常。 
 * 5. **批量删除类别 (`batchDeleteByIds`)**: 
 * - 根据提供的资源ID列表批量删除课程类别，同时检查是否有内置类别。 
 * 6. **查询单个类别 (`selectById`)**: 
 * - 根据资源ID查询单个课程类别，并根据参数决定是否包含子级信息。 
 * 7. **获取或创建类别 (`getOrCreate`)**: 
 * - 根据类别名称获取或创建一个新的课程类别。 
 * 8. **获取以父节点编号为键的映射 (`getParentMap`)**: 
 * - 根据给定的代码获取课程类别的层级关系。 
 * 9. **数据封装转换 (`packageCategoryList` 和 `packageCategoryBo`)**: 
 * - 将查询结果进行数据转换，封装成便于前端展示的格式。 
 * ### 技术点 
 * - **MyBatis Plus**: 使用了 MyBatis Plus 进行数据库操作。 
 * - **Lombok**: 通过 Lombok 注解简化了代码，如 `@Slf4j` 提供日志记录功能。 
 * - **BeanUtils**: 提供了对象之间的复制和转换功能。 
 * - **Stream API**: 利用 Java 8 的 Stream API 进行集合处理，使代码更加简洁。 
 * - **Guava**: 使用 Guava 库中的 `Lists.newArrayList()` 创建不可变列表。 
 * ### 总结 
 * 该服务类提供了全面的课程类别管理功能，从数据的查询、插入、更新到删除等操作，都有对应的实现方法。通过合理利用框架和技术手段，使得代码结构清晰，易于维护。 
**/
@Service
@Slf4j
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory> implements CourseCategoryService {

	/**
	 * 课程类别Mapper
	 *
	 * @see CourseCategoryMapper
	 */
	@Autowired
	private CourseCategoryMapper courseCategoryMapper;

	/**
	 * 课程验证处理器
	 *
	 * @see CourseValidateHandler
	 */
	@Autowired
	private CourseValidateHandler courseValidateHandler;

	/**
	 * 获取子级id列表
	 *
	 * @param directionId 方向id
	 * @param self        自我
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see List<String>
	 */
	@Override
	public List<String> getChildrenIds(String directionId, Boolean self) {
		CourseCategory courseCategory = courseCategoryMapper.selectById(directionId);
		List<CourseCategory> directions = courseCategoryMapper
				.selectChildrenByCode(courseCategory.getCode());

		List<String> directionIds = StreamApiUtils.map(directions, CourseCategory::getResourceId);
		if (self) {
			directionIds.add(directionId);
		}
		return directionIds;
	}

	/**
	 * 查询都
	 *
	 * @param children 子级
	 * @return {@code List<CourseCategoryBo> }
	 * @throws
	 * @see Boolean
	 * @see List<CourseCategoryBo>
	 */
	@Override
	public List<CourseCategoryBo> selectAll(Boolean children) {
		// 查询所有
		List<CourseCategory> allCategory = courseCategoryMapper.selectFirstLevel();

		if (children) {
			Map<String, List<CourseCategory>> parentMap = getParentMap(null);
			// 递归组装数据
			return packageCategoryList(allCategory, parentMap);
		}

		return BeanUtils.deepCopyList(allCategory, CourseCategoryBo.class);
	}

	/**
	 * 分页由搜索
	 *
	 * @param page     分页
	 * @param pattern  模式
	 * @param children 子级
	 * @return {@code IPage<CourseCategoryBo> }
	 * @throws
	 * @see Page
	 * @see String
	 * @see Boolean
	 * @see IPage<CourseCategoryBo>
	 */
	@Override
	public IPage<CourseCategoryBo> pageBySearch(Page page, String pattern, Boolean children) {
		IPage<CourseCategory> iPage = courseCategoryMapper.pageBySearch(page, pattern);

		if (children) {
			Map<String, List<CourseCategory>> parentMap = getParentMap(null);
			return iPage.convert(courseCategory -> packageCategoryBo(courseCategory, parentMap));
		}

		return iPage
				.convert(courseCategory -> BeanUtils.copy(courseCategory, CourseCategoryBo.class));
	}

	/**
	 * 保存或更新
	 *
	 * @param categoryAddBo 类别新增博
	 * @return {@code CourseCategory }
	 * @throws
	 * @see CategoryAddBo
	 * @see CourseCategory
	 */
	@Override
	public CourseCategory saveOrUpdate(CategoryAddBo categoryAddBo) {

		CourseCategory courseCategory = BeanUtils.copy(categoryAddBo, CourseCategory.class);
		if (StringUtils.isNotBlank(categoryAddBo.getResourceId())) {
			CourseCategory old = this.getById(categoryAddBo.getResourceId());
			if (old.getBuiltin()){
				throw new BusinessException(ErrorCode.BUILT_IN);
			}
			courseCategoryMapper.updateById(courseCategory);

		} else {
			String resourceId = IDGeneratorUtil.getID();
			if (StringUtils.isNotBlank(categoryAddBo.getParentId())) {
				CourseCategory category = courseCategoryMapper
						.selectById(categoryAddBo.getParentId());

				if (null == category) {
					log.error("Parent course category not found! category_id=[{}]",
							categoryAddBo.getParentId());
					throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
				}
				courseCategory.setCode(category.getCode() + CommonConstant.CODE_COMMA + resourceId);
			} else {
				courseCategory.setCode(resourceId);
			}

			courseCategory.setResourceId(resourceId);
			courseCategory.setBuiltin(CommonConstant.FALSE);
			courseCategoryMapper.insert(courseCategory);
		}

		return courseCategory;
	}

	/**
	 * 批量删除由id列表
	 *
	 * @param resourceIds 资源id列表
	 * @return {@code Integer }
	 * @throws
	 * @see List<String>
	 * @see Integer
	 */
	@Override
	public Integer batchDeleteByIds(List<String> resourceIds) {
		// 数据校验
		resourceIds.forEach(
				categoryId -> courseValidateHandler.validateEnableDeleteCategory(categoryId));

		List<CourseCategory> categories = courseCategoryMapper.selectBatchIds(resourceIds);
		List<String> toDelete = Lists.newArrayList();
		for (CourseCategory category : categories) {
			if (!category.getBuiltin()) {
				toDelete.add(category.getResourceId());
			}
		}
		if (CollectionUtils.isNotEmpty(toDelete)) {
			return courseCategoryMapper.deleteBatchIds(toDelete);
		} else {
			throw new BusinessException(ErrorCode.BUILT_IN);
		}
	}

	/**
	 * 查询由id
	 *
	 * @param resourceId 资源id
	 * @param children   子级
	 * @return {@code CourseCategoryBo }
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see CourseCategoryBo
	 */
	@Override
	public CourseCategoryBo selectById(String resourceId, Boolean children) {

		CourseCategory courseCategory = courseCategoryMapper.selectById(resourceId);
		if (null == courseCategory) {
			log.error("CourseCategory not found! category_id=[{}]", resourceId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		if (children) {
			Map<String, List<CourseCategory>> parentMap = getParentMap(courseCategory.getCode());
			return packageCategoryBo(courseCategory, parentMap);
		}
		return BeanUtils.copy(courseCategory, CourseCategoryBo.class);
	}

	/**
	 * 获取或创造
	 *
	 * @param categoryName 类别名称
	 * @return {@code CourseCategory }
	 * @throws
	 * @see String
	 * @see CourseCategory
	 */
	@Override
	public CourseCategory getOrCreate(String categoryName) {
		String resourceId = IDGeneratorUtil.getID();
		CourseCategory category = CourseCategory.builder()
				.courseType(CourseCategory.CourseType.BASE.getCode()).code(resourceId)
				.name(categoryName).resourceId(resourceId).build();
		try {
			courseCategoryMapper.insert(category);
		} catch (DuplicateKeyException e) {
			return courseCategoryMapper.selectByName(categoryName);
		}

		return category;
	}

	/**
	 * 获取以父节点编号为key的map数据
	 *
	 * @param code 为空则为全部数据，非空则为当前节点下的数据（除一级分类，父节点为空）
	 * @return
	 * @throws
	 * @see String
	 * @see Map<String, List<CourseCategory>>
	 */
	private Map<String, List<CourseCategory>> getParentMap(String code) {

		List<CourseCategory> categories;
		if (StringUtils.isNotBlank(code)) {
			categories = courseCategoryMapper.selectChildrenByCode(code);
		} else {
			categories = courseCategoryMapper.selectAll();
		}

		return categories.stream()
				.filter(courseCategory -> StringUtils.isNotBlank(courseCategory.getParentId()))
				.collect(Collectors.groupingBy(CourseCategory::getParentId));
	}

	/**
	 * 数据封装转换
	 *
	 * @param categories
	 * @param categoryMap
	 * @return
	 * @throws
	 * @see List<CourseCategory>
	 * @see Map<String, List<CourseCategory>>
	 * @see List<CourseCategoryBo>
	 */
	private List<CourseCategoryBo> packageCategoryList(List<CourseCategory> categories,
			Map<String, List<CourseCategory>> categoryMap) {

		if (CollectionUtils.isEmpty(categories)) {
			return Lists.newArrayList();
		}

		return StreamApiUtils.map(categories, courseCategory -> packageCategoryBo(courseCategory, categoryMap));
	}

	/**
	 * 课程课时分类组装
	 *
	 * @param courseCategory
	 * @param categoryMap
	 * @return
	 * @throws
	 * @see CourseCategory
	 * @see Map<String, List<CourseCategory>>
	 * @see CourseCategoryBo
	 */
	private CourseCategoryBo packageCategoryBo(CourseCategory courseCategory,
			Map<String, List<CourseCategory>> categoryMap) {

		CourseCategoryBo categoryBo = BeanUtils.copy(courseCategory, CourseCategoryBo.class);
		List<CourseCategoryBo> categoryBos = packageCategoryList(
				categoryMap.get(courseCategory.getResourceId()), categoryMap);
		categoryBo.setChildren(categoryBos);

		return categoryBo;
	}
}

