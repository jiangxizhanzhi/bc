/**
 * 这段代码定义了一个名为 `CourseCategoryService` 的接口，该接口扩展了 `IService<CourseCategory>` 接口。这个接口主要用于处理与课程分类相关的业务逻辑。以下是对该接口的简要分析： 
 * ### 接口概述 
 * - **包名**: `com.cyberpeace.oj.course.service` 
 * - **接口名称**: `CourseCategoryService` 
 * - **继承自**: `IService<CourseCategory>` 
 * - **作者**: SN 
 * - **版本**: 1.0.0 
 * - **日期**: 2024年11月8日 
 * ### 主要方法 
 * 1. **getChildrenIds** 
 * - **功能**: 根据给定的课程方向ID获取其所有子节点的课程方向编号。 
 * - **参数**: 
 * - `directionId`: 课程方向ID。 
 * - `self`: 是否包含自身。 
 * - **返回值**: 包含所有子节点课程方向编号的列表。 
 * 2. **selectAll** 
 * - **功能**: 查询所有的课程分类。 
 * - **参数**: 
 * - `children`: 是否包含子级数据。 
 * - **返回值**: 所有课程分类的列表。 
 * 3. **pageBySearch** 
 * - **功能**: 模糊搜索课程分类，并分页显示结果。 
 * - **参数**: 
 * - `page`: 分页对象。 
 * - `pattern`: 搜索模式（如名称）。 
 * - `children`: 是否包含子级数据。 
 * - **返回值**: 分页后的课程分类列表。 
 * 4. **saveOrUpdate** 
 * - **功能**: 新增或更新课程分类数据。 
 * - **参数**: 
 * - `categoryAddBo`: 包含新增或更新数据的对象。 
 * - **返回值**: 更新后的课程分类对象。 
 * 5. **batchDeleteByIds** 
 * - **功能**: 根据课程分类编号批量删除数据。 
 * - **参数**: 
 * - `resourceIds`: 需要删除的课程分类编号列表。 
 * - **返回值**: 删除记录的数量。 
 * 6. **selectById** 
 * - **功能**: 根据课程分类编号查询详细信息。 
 * - **参数**: 
 * - `resourceId`: 课程分类编号。 
 * - `children`: 是否查询下级分类。 
 * - **返回值**: 查询到的课程分类详细信息。 
 * 7. **getOrCreate** 
 * - **功能**: 如果指定的课程分类名称不存在，则创建一个新分类；如果存在，则返回该分类。 
 * - **参数**: 
 * - `categoryName`: 课程分类名称。 
 * - **返回值**: 创建或已存在的课程分类对象。 
 * ### 总结 
 * 该接口提供了全面的课程分类管理功能，包括查询、新增、更新、删除和批量操作等。通过这些方法，可以方便地管理和维护课程分类的数据。 
**/
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
