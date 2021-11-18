package com.cyberpeace.oj.course.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;
import com.cyberpeace.oj.course.service.bo.CategoryAddBo;
import com.cyberpeace.oj.course.service.bo.CourseCategoryBo;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory> implements CourseCategoryService {

	@Autowired
	private CourseCategoryMapper courseCategoryMapper;

	@Override
	public List<String> getChildrenIds(String directionId, Boolean self) {
		CourseCategory courseCategory = courseCategoryMapper.selectById(directionId);
		List<CourseCategory> directions = courseCategoryMapper
				.selectChildrenByCode(courseCategory.getCode());

		List<String> directionIds = StreamApiUtils.simpleMap(directions,
				CourseCategory::getResourceId);
		if (self) {
			directionIds.add(directionId);
		}
		return directionIds;
	}

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

	@Override
	public List<CourseCategoryBo> pageBySearch(Page page, String pattern, Boolean children) {
		List<CourseCategory> categories = courseCategoryMapper.pageBySearch(page, pattern);
		if (children) {
			Map<String, List<CourseCategory>> parentMap = getParentMap(null);
			return packageCategoryList(categories, parentMap);
		}
		return BeanUtils.deepCopyList(categories, CourseCategoryBo.class);
	}

	@Override
	public CourseCategory saveOrUpdate(CategoryAddBo categoryAddBo) {

		CourseCategory courseCategory = BeanUtils.copy(categoryAddBo, CourseCategory.class);
		if (StringUtils.isNotBlank(categoryAddBo.getResourceId())) {
			courseCategoryMapper.updateById(courseCategory);

		} else {
			String resourceId = IDGeneratorUtil.getID();
			if (StringUtils.isNotBlank(categoryAddBo.getParentId())) {
				CourseCategory category = courseCategoryMapper
						.selectById(categoryAddBo.getParentId());

				if (null == category) {
					log.error("Parent course category not found! category_id=[{}]",
							categoryAddBo.getParentId());
					throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
				}
				courseCategory.setCode(category.getCode() + CommonConstant.CODE_COMMA + resourceId);
			} else {
				courseCategory.setCode(resourceId);
			}

			courseCategory.setResourceId(resourceId);
			courseCategoryMapper.insert(courseCategory);
		}

		return courseCategory;
	}

	@Override
	public Integer batchDeleteByIds(List<String> resourceIds) {
		List<CourseCategory> categories = courseCategoryMapper.selectBatchIds(resourceIds);
		List<String> codeList = StreamApiUtils.columnList(categories, CourseCategory::getCode);

		for (String code : codeList) {
			List<CourseCategory> categoryList = courseCategoryMapper.selectChildrenByCode(code);
			List<String> childrenIds = StreamApiUtils.simpleMap(categoryList,
					CourseCategory::getResourceId);
			resourceIds.addAll(childrenIds);
		}

		return courseCategoryMapper.deleteBatchIds(resourceIds);
	}

	@Override
	public CourseCategoryBo selectById(String resourceId, Boolean children) {

		CourseCategory courseCategory = courseCategoryMapper.selectById(resourceId);
		if (null == courseCategory) {
			log.error("CourseCategory not found! category_id=[{}]", resourceId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}

		if (children) {
			Map<String, List<CourseCategory>> parentMap = getParentMap(courseCategory.getCode());
			return packageCategoryBo(courseCategory, parentMap);
		}
		return BeanUtils.copy(courseCategory, CourseCategoryBo.class);
	}

	/**
	 * 获取以父节点编号为key的map数据 
	 * @param code 为空则为全部数据，非空则为当前节点下的数据（除一级分类，父节点为空）
	 * @return
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
	 * @param categories
	 * @param categoryMap
	 * @return
	 */
	private List<CourseCategoryBo> packageCategoryList(List<CourseCategory> categories,
			Map<String, List<CourseCategory>> categoryMap) {

		if (CollectionUtils.isEmpty(categories)) {
			return Lists.newArrayList();
		}

		List<CourseCategoryBo> courseCategoryBos = StreamApiUtils.simpleMap(categories,
				courseCategory -> packageCategoryBo(courseCategory, categoryMap));

		return courseCategoryBos;
	}

	/**
	 * 课程课时分类组装
	 * @param courseCategory
	 * @param categoryMap
	 * @return
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
