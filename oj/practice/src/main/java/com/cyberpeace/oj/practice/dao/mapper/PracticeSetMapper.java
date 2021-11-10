package com.cyberpeace.oj.practice.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.practice.dao.entity.PracticeSet;
import com.cyberpeace.oj.practice.service.externalBo.PracticeSetSimple;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface PracticeSetMapper extends BaseMapper<PracticeSet> {

    /**
     * 查询获取习题集
     */
    IPage<PracticeSet> getList(Page page, @Param("practiceType") Integer type, @Param("pattern") String pattern);

    Integer batchDelete(@Param("resource_ids") List<String> resourceIds);

    /**
     * 查询所有习题库 #外部接口使用
     * @return
     */
    List<PracticeSetSimple> getAll();

}
