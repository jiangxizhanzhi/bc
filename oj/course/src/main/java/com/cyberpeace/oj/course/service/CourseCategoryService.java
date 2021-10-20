package com.cyberpeace.oj.course.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.course.dao.entity.CourseCategory;
import com.cyberpeace.oj.course.service.bo.CategoryAddBo;
import com.cyberpeace.oj.course.service.bo.CourseCategoryBo;

public interface CourseCategoryService extends IService<CourseCategory> {

	/**
	 * 根据当前课程方向 获取所有子节点的方向编号
	 * @param directionId
	 * @return
	 */
	List<String> getAllChildrenIds(String directionId);

	/**
	 * 查询所有课程分类
	 * @return
	 */
	List<CourseCategoryBo> selectAll();

	/**
	 * 根据名称模糊搜索课程分类
	 * @param page
	 * @param pattern
	 * @return
	 */
	List<CourseCategory> pageBySearch(Page page, String pattern);

	/**
	 * 新增或者修改数据
	 * @param categoryAddBo
	 * @return
	 */
	CourseCategory saveOrUpdate(CategoryAddBo categoryAddBo);

	/**
	 * 根据课程分类编号批量删除
	 * @param resourceIds
	 * @return
	 */
	Integer batchDeleteByIds(List<String> resourceIds);
}
