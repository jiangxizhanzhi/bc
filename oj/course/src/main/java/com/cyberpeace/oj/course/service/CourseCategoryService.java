package com.cyberpeace.oj.course.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.service.bo.CategoryAddBo;
import com.cyberpeace.oj.course.service.bo.CourseCategoryBo;

/**
 * 课程类别Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see IService
 * @since 1.0.0
 */
public interface CourseCategoryService extends IService<CourseCategory> {

	/**
	 * 根据当前课程方向 获取所有子节点的方向编号
	 *
	 * @param directionId
	 * @param self        是否包含自身
	 * @return
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see List<String>
	 */
	List<String> getChildrenIds(String directionId, Boolean self);

	/**
	 * 查询所有课程分类
	 *
	 * @param children 子级
	 * @return
	 * @throws
	 * @see Boolean
	 * @see List<CourseCategoryBo>
	 */
	List<CourseCategoryBo> selectAll(Boolean children);

	/**
	 * 根据名称模糊搜索课程分类
	 *
	 * @param page
	 * @param pattern
	 * @param children true包含子类数据 false不包含
	 * @return
	 * @throws
	 * @see Page
	 * @see String
	 * @see Boolean
	 * @see IPage<CourseCategoryBo>
	 */
	IPage<CourseCategoryBo> pageBySearch(Page page, String pattern, Boolean children);

	/**
	 * 新增或者修改数据
	 *
	 * @param categoryAddBo
	 * @return
	 * @throws
	 * @see CategoryAddBo
	 * @see CourseCategory
	 */
	CourseCategory saveOrUpdate(CategoryAddBo categoryAddBo);

	/**
	 * 根据课程分类编号批量删除
	 *
	 * @param resourceIds
	 * @return
	 * @throws
	 * @see List<String>
	 * @see Integer
	 */
	Integer batchDeleteByIds(List<String> resourceIds);

	/**
	 * 根绝课程分类编号查询详细信息
	 *
	 * @param resourceId
	 * @param children   是否查询下级分类
	 * @return
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see CourseCategoryBo
	 */
	CourseCategoryBo selectById(String resourceId, Boolean children);

	/**
	 * 无则创建 有则返回
	 *
	 * @param categoryName
	 * @return
	 * @throws
	 * @see String
	 * @see CourseCategory
	 */
	CourseCategory getOrCreate(String categoryName);
}
