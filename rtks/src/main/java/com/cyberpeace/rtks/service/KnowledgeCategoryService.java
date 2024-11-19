package com.cyberpeace.rtks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.rtks.dao.entity.KnowledgeCategory;

import java.util.List;

/**
 * 知识点类别Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see IService
 * @since 1.0.0
 */
public interface KnowledgeCategoryService extends IService<KnowledgeCategory> {

    /**
     * 分页
     *
     * @param pageReqBase 分页请求体基础
     * @return {@code ListRsp<KnowledgeCategory> }
     * @throws
     * @see PageReqBase
     * @see ListRsp<KnowledgeCategory>
     */
    ListRsp<KnowledgeCategory> page(PageReqBase pageReqBase);

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
