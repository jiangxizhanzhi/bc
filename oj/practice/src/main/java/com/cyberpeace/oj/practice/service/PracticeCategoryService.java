package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.service.bo.CategoryListBo;
import com.cyberpeace.oj.practice.service.bo.CategoryListQuery;

import java.util.List;

/**
 * 实践类别Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see IService
 * @since 1.0.0
 */
public interface PracticeCategoryService extends IService<PracticeCategory> {

	/**
	 * 根据类型和名称查询分类信息
	 *
	 * @param page
	 * @param query
	 * @param children 是否查询子类信息
	 * @return
	 * @throws
	 * @see Page
	 * @see CategoryListQuery
	 * @see Boolean
	 * @see IPage<CategoryListBo>
	 */
	IPage<CategoryListBo> pageByListQuery(Page page, CategoryListQuery query, Boolean children);

	/**
	 * 根据分类编号查询分类信息
	 *
	 * @param resourceId
	 * @param children   是否查询子类信息
	 * @return
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see CategoryListBo
	 */
	CategoryListBo selectById(String resourceId, Boolean children);

	/**
	 * 更新习题分类
	 *
	 * @param category
	 * @return
	 * @throws
	 * @see PracticeCategory
	 * @see Integer
	 */
	Integer updateCategory(PracticeCategory category);

	/**
	 * 习题分类新增
	 *
	 * @param practiceCategory
	 * @param userId
	 * @return
	 * @throws
	 * @see PracticeCategory
	 * @see String
	 * @see Integer
	 */
	Integer addCategory(PracticeCategory practiceCategory, String userId);

	/**
	 * 获取分类的code值 无则生成一个
	 *
	 * @param parentId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 */
	String getCodeByParent(String parentId);

	/**
	 * 查询列表
	 *
	 * @param listQuery
	 * @param children
	 * @return
	 * @throws
	 * @see CategoryListQuery
	 * @see Boolean
	 * @see List<CategoryListBo>
	 */
	List<CategoryListBo> listByTypeAndName(CategoryListQuery listQuery, Boolean children);

	/**
	 * 查询子类分类编号
	 *
	 * @param parentId
	 * @param self     是否包含本身 true包含 false不包含
	 * @return
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see List<String>
	 */
	List<String> selectChildrenIds(String parentId, Boolean self);

	/**
	 * 批量删除分类
	 *
	 * @param resourceIds
	 * @return
	 * @throws
	 * @see List<String>
	 * @see int
	 */
	int batchDelete(List<String> resourceIds);

	/**
	 * 查询或者创建分类
	 *
	 * @param categoryName
	 * @param code
	 * @param userId
	 * @return
	 * @throws
	 * @see String
	 * @see Integer
	 * @see String
	 * @see String
	 */
	String selectCategoryOrCreate(String categoryName, Integer code, String userId);
}
