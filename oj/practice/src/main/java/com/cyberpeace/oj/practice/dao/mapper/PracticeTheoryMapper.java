package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PracticeTheoryMapper extends BaseMapper<PracticeTheory> {

    IPage<PracticeTheory> getList(Page page, @Param("pattern") String pattern, @Param("practice_set") String practiceSet,
                                  @Param("category") String category, @Param("question_type") Integer questionType);

    Integer batchDelete(@Param("resource_ids") List<String> resourceIds);

    List<PracticeTheory> batchGet(@Param("resource_ids") List<String> resourceIds);
}
