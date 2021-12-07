package com.cyberpeace.oj.practice.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.practice.controller.req.PracticeCategoryListReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeCategoryMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PracticeCategoryServiceImpl extends ServiceImpl<PracticeCategoryMapper, PracticeCategory> implements PracticeCategoryService {

	@Autowired
	private PracticeCategoryMapper practiceCategoryMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Override
	public IPage<CategoryListBo> pageByListQuery(Page page, CategoryListQuery query,
			Boolean children) {
		IPage<PracticeCategory> categoryIPage = practiceCategoryMapper.pageByTypeAndName(page,
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

		// 分类名称校验
		if (StringUtils.isNotBlank(category.getName())) {
			PracticeCategory data = practiceCategoryMapper.selectById(category);
			String categoryId = practiceCategoryMapper.selectByName(data.getPracticeType(),
					category.getName());
			if (StringUtils.isNotBlank(categoryId)) {
				throw new InvalidParamException("分类名称已存在！");
			}
		}

		// 数据调整
		List<PracticeCategory> categoryList = Lists.newArrayList();
		if (StringUtils.isNotBlank(category.getParentId())) {
			PracticeCategory existCategory = practiceCategoryMapper
					.selectById(category.getResourceId());
			List<PracticeCategory> categories = practiceCategoryMapper
					.selectByCode(existCategory.getCode());

			categories.forEach(practiceCategory -> {
				practiceCategory.setCode(practiceCategory.getCode().replace(existCategory.getCode(),
						category.getCode()));
			});

			categoryList.addAll(categories);
		}

		return transactionTemplate.execute(new TransactionCallback<Integer>() {

			@Override
			public Integer doInTransaction(TransactionStatus status) {
				updateBatchById(categoryList);
				return practiceCategoryMapper.updateById(category);
			}
		});
	}

	@Override
	public Integer addCategory(PracticeCategory practiceCategory, String userId) {
		// 分类名称校验
		String categoryId = practiceCategoryMapper.selectByName(practiceCategory.getPracticeType(),
				practiceCategory.getName());
		if (StringUtils.isNotBlank(categoryId)) {
			throw new InvalidParamException("分类名称已存在！");
		}

		practiceCategory.setCreateUserId(userId);
		return practiceCategoryMapper.insert(practiceCategory);
	}

	@Override
	public String getCodeByParent(String parentId) {

		if (StringUtils.isNotBlank(parentId)) {
			PracticeCategory parentCategory = practiceCategoryMapper.selectById(parentId);
			if (null == parentCategory) {
				throw new InvalidParamException("父级分类不存在！");
			}
			return parentCategory.getCode() + CommonConstant.CODE_COMMA
					+ parentCategory.getResourceId();
		}

		return null;
	}

	@Override
	public List<CategoryListBo> listByTypeAndName(CategoryListQuery listQuery, Boolean children) {
		List<PracticeCategory> categoryList = practiceCategoryMapper
				.listByTypeAndName(listQuery.getPracticeType(), listQuery.getPattern());

		List<CategoryListBo> listBos = StreamApiUtils.map(categoryList,
				practiceCategory -> entityToListBo(practiceCategory, children));

		return listBos;
	}

	@Override
	public List<String> selectChildrenIds(String parentId, Boolean self) {

		if (StringUtils.isBlank(parentId)) {
			return Lists.newArrayList();
		}

		PracticeCategory category = practiceCategoryMapper.selectById(parentId);
		if (null == category) {
			return Arrays.asList(parentId);
		}

		List<PracticeCategory> categories = practiceCategoryMapper.selectByCode(category.getCode());
		List<String> categoryIds = StreamApiUtils.getColumn(categories,
				PracticeCategory::getResourceId);

		if (self) {
			categoryIds.add(parentId);
		}

		return categoryIds;
	}

	@Override
	public int batchDelete(List<String> categoryIds) {

		// 查询所有的子集分类
		List<String> allDeleteIds = categoryIds.stream().map(categoryId -> {
			PracticeCategory category = practiceCategoryMapper.selectById(categoryId);
			return practiceCategoryMapper.selectByCode(category.getCode());
		}).flatMap(Collection::stream).map(PracticeCategory::getResourceId)
				.collect(Collectors.toList());

		// 加入父级分类
		allDeleteIds.addAll(categoryIds);

		// 查询是否存在基础理论题
		List<String> theoryIds = practiceTheoryMapper.selectByCategoryIds(allDeleteIds,
				CommonConstant.FALSE);
		if (CollectionUtils.isNotEmpty(theoryIds)) {
			throw new InvalidParamException("习题集下存在习题！");
		}

		// 查询是否存在实操题
		List<String> operationIds = practiceOperationMapper.selectByCategoryIds(allDeleteIds,
				CommonConstant.FALSE);
		if (CollectionUtils.isNotEmpty(operationIds)) {
			throw new InvalidParamException("习题集下存在习题！");
		}

		// 数据删除
		return practiceCategoryMapper.deleteBatchIds(allDeleteIds);
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

		Map<String, List<PracticeCategory>> parentMap = StreamApiUtils.groupBy(
				StreamApiUtils.filter(categoryList,
						category -> StringUtils.isNotBlank(category.getParentId())),
				PracticeCategory::getParentId);

		List<PracticeCategory> childrenList = parentMap.get(practiceCategory.getResourceId());
		List<CategoryListBo> listBos = StreamApiUtils.map(childrenList,
				category -> entityToListBo(category, CommonConstant.TRUE));
		listBo.setCategoryListBos(listBos);

		return listBo;
	}

}
