package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.practice.dao.entity.PracticeCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PracticeCategoryMapper extends BaseMapper<PracticeCategory> {

    PracticeCategory getSingle(@Param("resource_id") String resourceId);

    IPage<PracticeCategory> getList(Page page, @Param("tableName") String table);

    Integer partialUpdate(@Param("tableName") String tableName, @Param("resource_id") String id, @Param("name") String name);

    Integer batchDelete(@Param("tableName") String tableName, @Param("resource_ids") List<String> resourceIds);
}
