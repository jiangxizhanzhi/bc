package com.cyberpeace.oj.rtks.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.rtks.controller.req.KnowledgeListReq;
import com.cyberpeace.oj.rtks.controller.res.KnowledgeRes;
import com.cyberpeace.oj.rtks.dao.entity.Knowledge;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface KnowledgeMapper extends BaseMapper<Knowledge> {

	IPage<KnowledgeRes> listKnowledge(Page page, KnowledgeListReq knowledgeListReq);

	KnowledgeRes getKnowledgeByResourceId(String id);
}
