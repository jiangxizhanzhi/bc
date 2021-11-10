package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.practice.controller.req.PracticeCategoryListReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.service.PracticeCategoryService;
import com.cyberpeace.oj.practice.service.bo.CategoryListBo;
import com.cyberpeace.oj.practice.service.bo.CategoryListQuery;
import com.cyberpeace.oj.practice.service.bo.PracticeCategoryParent;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class PracticeCategoryServiceImpl extends ServiceImpl<PracticeCategoryMapper, PracticeCategory> implements PracticeCategoryService {

	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	@Override
	public Integer batchDelete(List<String> resourceIds) {
		return practiceCategoryMapper.batchDelete(resourceIds);
	}

	@Override
	public IPage<CategoryListBo> pageByListQuery(Page page, CategoryListQuery query,
			Boolean children) {
		IPage<PracticeCategory> categoryIPage = practiceCategoryMapper.getList(page,
				query.getPattern(), query.getPracticeType());

		IPage<CategoryListBo> boIPage = categoryIPage
				.convert(practiceCategory -> entityToListBo(practiceCategory, children));

		return boIPage;
	}

	@Override
	public CategoryListBo selectById(String resourceId, Boolean children) {
		PracticeCategory practiceCategory = practiceCategoryMapper.selectById(resourceId);
		return entityToListBo(practiceCategory, children);
	}

	@Override
	public Integer updateCategory(PracticeCategory category) {
		return practiceCategoryMapper.updateById(category);
	}

	@Override
	public Integer addCategory(PracticeCategory practiceCategory, String userId) {
		practiceCategory.setCreateUserId(userId);
		return practiceCategoryMapper.updateById(practiceCategory);
	}

	@Override
	public String getCodeByParent(String parentId) {

		if (StringUtils.isNotBlank(parentId)) {
			PracticeCategory parentCategory = practiceCategoryMapper.selectById(parentId);
			return null != parentCategory
					? parentCategory.getCode() + CommonConstant.CODE_COMMA
							+ parentCategory.getResourceId()
					: null;
		}

		return IDGeneratorUtil.getID();
	}

	/**
	 * 实体类转listBo
	 * @param practiceCategory 习题分类实体类
	 * @return
	 */
	private CategoryListBo entityToListBo(PracticeCategory practiceCategory, Boolean children) {

		return children ? setChildren(practiceCategory)
				: BeanUtils.copy(practiceCategory, CategoryListBo.class);
	}

	/**
	 * 组织树结构
	 * @param practiceCategory
	 * @return
	 */
	private CategoryListBo setChildren(PracticeCategory practiceCategory) {

		CategoryListBo listBo = BeanUtils.copy(practiceCategory, CategoryListBo.class);
		List<PracticeCategory> categoryList = practiceCategoryMapper
				.selectByCode(practiceCategory.getCode());

		List<CategoryListBo> listBos = StreamApiUtils.simpleMap(categoryList,
				category -> entityToListBo(category, CommonConstant.TRUE));
		listBo.setCategoryListBos(listBos);

		return listBo;
	}

}
