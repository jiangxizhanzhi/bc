package com.cyberpeace.oj.practice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.practice.controller.req.PracticeCategoryListReq;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.service.bo.CategoryListBo;
import com.cyberpeace.oj.practice.service.bo.CategoryListQuery;
import com.cyberpeace.oj.practice.service.bo.PracticeCategoryParent;

import java.util.List;

public interface PracticeCategoryService extends IService<PracticeCategory> {

    Integer batchDelete(List<String> resourceIds);

    /**
     * 根据类型和名称查询分类信息
     * @param page
     * @param query
     * @param children 是否查询子类信息
     * @return
     */
    IPage<CategoryListBo> pageByListQuery(Page page, CategoryListQuery query, Boolean children);

    /**
     * 根据
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
}
