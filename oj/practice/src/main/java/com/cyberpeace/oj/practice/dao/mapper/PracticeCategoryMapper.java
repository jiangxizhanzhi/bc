package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import com.cyberpeace.oj.practice.service.bo.PracticeCategoryParent;
import com.cyberpeace.oj.practice.service.externalBo.PracticeCategorySimple;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PracticeCategoryMapper extends BaseMapper<PracticeCategory> {

    /**
     * 分页获取一级节点
     * @param page
     * @param pattern
     * @param practiceType
     * @return
     */
    IPage<PracticeCategory> getList(Page page, @Param("pattern") String pattern, @Param("practice_type") Integer practiceType);

    Integer batchDelete(@Param("resource_ids") List<String> resourceIds);

    List<PracticeCategory> selectByCode(@Param("code") String parentCode);

    List<PracticeCategoryParent> batchGetByCode(@Param("codes") List<String> codes);

    /**
     * 仅仅查询一级 #外部接口使用
     * @return 所有理论基础类型一级分类
     */
    List<PracticeCategorySimple> getAll();
}
