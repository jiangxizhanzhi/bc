package com.cyberpeace.rtks.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.rtks.dao.entity.KnowledgeCategory;

import java.util.List;

/**
 * 知识类别Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
@Repository
public interface KnowledgeCategoryMapper extends BaseMapper<KnowledgeCategory> {

    /**
     * 列出知识类别
     *
     * @param page    分页
     * @param pattern 模式
     * @return {@code IPage<KnowledgeCategory> }
     */
    IPage<KnowledgeCategory> listKnowledgeCategory(Page page, @Param("pattern") String pattern);

    /**
     * 获取由内容
     *
     * @param content 内容
     * @return {@code List<KnowledgeCategory> }
     */
    List<KnowledgeCategory> getByContent(String content);
}
