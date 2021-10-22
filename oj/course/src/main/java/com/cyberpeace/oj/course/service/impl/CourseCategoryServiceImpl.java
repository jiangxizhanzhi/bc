package com.cyberpeace.oj.course.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.course.constant.CourseType;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.service.bo.CategoryAddBo;
import com.cyberpeace.oj.course.service.bo.CourseCategoryBo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.oj.course.dao.mapper.CourseCategoryMapper;
import com.cyberpeace.oj.course.service.CourseCategoryService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory> implements CourseCategoryService {

	@Autowired
	private CourseCategoryMapper courseCategoryMapper;

	@Override
	public List<String> getChildrenIds(String directionId) {
		CourseCategory courseCategory = courseCategoryMapper.selectById(directionId);
		List<CourseCategory> directions = courseCategoryMapper
				.selectChildrenByCode(courseCategory.getCode());
		return directions.stream().map(CourseCategory::getResourceId).collect(Collectors.toList());
	}

	@Override
	public List<CourseCategoryBo> selectAll() {
		// 查询所有
		List<CourseCategory> allCategory = courseCategoryMapper.selectAll();

		// 区分第一级和子级
		List<CourseCategory> firstLevels = Lists.newArrayList();
		List<CourseCategory> childrenNodes = Lists.newArrayList();
		allCategory.forEach(courseCategory -> {
			if (courseCategory.getResourceId().equals(courseCategory.getCode())) {
				firstLevels.add(courseCategory);
			} else {
				childrenNodes.add(courseCategory);
			}
		});

		// 根据code进行分组
		Map<String, List<CourseCategory>> columnMap = StreamApiUtils.groupByColumn(childrenNodes,
				courseCategory -> courseCategory.getCode().substring(0,
						courseCategory.getCode().lastIndexOf(',')));

		// 递归组装数据
		List<CourseCategoryBo> categoryBos = packageData(firstLevels, columnMap);

		return categoryBos;
	}

	@Override
	public List<CourseCategory> pageBySearch(Page page, String pattern) {
		List<CourseCategory> categories = courseCategoryMapper.pageBySearch(page, pattern);
		return categories;
	}

	@Override
	public CourseCategory saveOrUpdate(CategoryAddBo categoryAddBo) {

		CourseCategory courseCategory = BeanUtils.copy(categoryAddBo, CourseCategory.class);
		if (StringUtils.isNotBlank(categoryAddBo.getResourceId())) {
			courseCategoryMapper.updateById(courseCategory);

		} else {
			String resourceId = IDGeneratorUtil.getID();
			if (StringUtils.isNotBlank(categoryAddBo.getParentId())) {
				CourseCategory category = courseCategoryMapper.selectById(categoryAddBo.getParentId());
				courseCategory.setCode(category.getCode() + CommonConstant.CODE_COMMA + resourceId);
			} else {
				courseCategory.setCode(resourceId);
			}

			courseCategory.setResourceId(resourceId);
			courseCategory.setCourseType(CourseType.BASE.getCode());
			courseCategoryMapper.insert(courseCategory);

		}

		return courseCategory;
	}

	@Override
	public Integer batchDeleteByIds(List<String> resourceIds) {
		int row = courseCategoryMapper.deleteBatchIds(resourceIds);
		return row;
	}

	/**
	 * 数据封装转换
	 * @param categories
	 * @param map
	 * @return
	 */
	private List<CourseCategoryBo> packageData(List<CourseCategory> categories,
			Map<String, List<CourseCategory>> map) {

		if (CollectionUtils.isEmpty(categories)) {
			return Lists.newArrayList();
		}

		List<CourseCategoryBo> courseCategoryBos = StreamApiUtils.simpleMap(categories,
				courseCategory -> {
					CourseCategoryBo categoryBo = BeanUtils.copy(courseCategory,
							CourseCategoryBo.class);
					List<CourseCategoryBo> categoryBos = packageData(
							map.get(courseCategory.getCode()), map);
					categoryBo.setChildren(categoryBos);

					return categoryBo;
				});

		return courseCategoryBos;
	}
}
