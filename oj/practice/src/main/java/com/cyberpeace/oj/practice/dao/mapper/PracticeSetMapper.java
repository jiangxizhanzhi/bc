package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PracticeSetMapper extends BaseMapper<PracticeSet> {

    /**
     * 查询获取习题集 TODO Page
     */
    IPage<PracticeSet> getList(Page page, @Param("practiceType") Integer type, @Param("pattern") String pattern);

    Integer batchDelete(@Param("resource_ids") List<String> resourceIds);

}
