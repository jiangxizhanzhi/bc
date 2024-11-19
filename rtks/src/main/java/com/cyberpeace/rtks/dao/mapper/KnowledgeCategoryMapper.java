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
 * 知识点类别Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface KnowledgeCategoryMapper extends BaseMapper<KnowledgeCategory> {

    /**
     * 列表知识点类别
     *
     * @param page    分页
     * @param pattern 模式
     * @return {@code IPage<KnowledgeCategory> }
     * @throws
     * @see Page
     * @see String
     * @see IPage<KnowledgeCategory>
     */
    IPage<KnowledgeCategory> listKnowledgeCategory(Page page, @Param("pattern") String pattern);

    /**
     * 获取由内容
     *
     * @param content 内容
     * @return {@code List<KnowledgeCategory> }
     * @throws
     * @see String
     * @see List<KnowledgeCategory>
     */
    List<KnowledgeCategory> getByContent(String content);
}
