package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.service.bo.CategoryListBo;
import com.cyberpeace.oj.practice.service.bo.CategoryListQuery;

import java.util.List;

public interface PracticeCategoryService extends IService<PracticeCategory> {

	/**
	 * 根据类型和名称查询分类信息
	 * @param page
	 * @param query
	 * @param children 是否查询子类信息
	 * @return
	 */
	IPage<CategoryListBo> pageByListQuery(Page page, CategoryListQuery query, Boolean children);

	/**
	 * 根据分类编号查询分类信息
	 * @param resourceId
	 * @param children 是否查询子类信息
	 * @return
	 */
	CategoryListBo selectById(String resourceId, Boolean children);

	/**
	 * 更新习题分类
	 * @param category
	 * @return
	 */
	Integer updateCategory(PracticeCategory category);

	/**
	 * 习题分类新增
	 * @param practiceCategory
	 * @param userId
	 * @return
	 */
	Integer addCategory(PracticeCategory practiceCategory, String userId);

	/**
	 * 获取分类的code值 无则生成一个
	 * @param parentId
	 * @return
	 */
	String getCodeByParent(String parentId);

	/**
	 * 查询列表
	 * @param listQuery
	 * @param children
	 * @return
	 */
	List<CategoryListBo> listByTypeAndName(CategoryListQuery listQuery, Boolean children);

	/**
	 * 查询子类分类编号
	 * @param parentId
	 * @param self 是否包含本身 true包含 false不包含
	 * @return
	 */
	List<String> selectChildrenIds(String parentId, Boolean self);

	/**
	 * 批量删除分类
	 * @param resourceIds
	 * @return
	 */
	int batchDelete(List<String> resourceIds);

	/**
	 * 查询或者创建分类
	 * @param categoryName
	 * @param code
	 * @param userId
	 * @return
	 */
	String selectCategoryOrCreate(String categoryName, Integer code, String userId);

	/**
	 * 获取批量byid列表
	 *
	 * @param categoryIds categoryid列表
	 * @return {@code List<PracticeCategory>}
	 */
	List<PracticeCategory> getBatchByIds(List<String> categoryIds);
}
