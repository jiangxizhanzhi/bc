package com.cyberpeace.rtks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.rtks.dao.entity.KnowledgeCategory;

import java.util.List;

/**
 *
 */
public interface KnowledgeCategoryService extends IService<KnowledgeCategory> {

	ListRsp<KnowledgeCategory> page(PageReqBase pageReqBase);

    List<KnowledgeCategory> getByContent(String content);
}
