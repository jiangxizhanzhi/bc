package com.cyberpeace.oj.rtks.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.rtks.dao.entity.KnowledgeCategory;

@Mapper
@Repository
public interface KnowledgeCategoryMapper extends BaseMapper<KnowledgeCategory> {

	IPage<KnowledgeCategory> listKnowledgeCategory(Page page, @Param("pattern") String pattern);

}
