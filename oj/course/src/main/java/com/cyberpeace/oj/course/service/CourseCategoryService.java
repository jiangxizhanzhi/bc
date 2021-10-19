package com.cyberpeace.oj.course.service;

import com.cyberpeace.oj.course.dao.entity.CourseCategory;

import java.util.List;

public interface CourseCategoryService {

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
    List<CourseCategory> selectAll();
}
