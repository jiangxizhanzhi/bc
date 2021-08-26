package com.cyberpeace.oj.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.rtks.controller.req.KnowledgeListReq;
import com.cyberpeace.oj.rtks.controller.res.CourseRelatedRes;
import com.cyberpeace.oj.rtks.controller.res.KnowledgeRes;
import com.cyberpeace.oj.rtks.dao.entity.Knowledge;

@Mapper
@Repository
public interface KnowledgeMapper extends BaseMapper<Knowledge> {

	IPage<KnowledgeRes> listKnowledge(Page page, KnowledgeListReq knowledgeListReq);

	KnowledgeRes getKnowledgeByResourceId(String id);

	List<String> filterIds(List<String> ids);

	List<CourseRelatedRes> getKnowledgeByCourseId(List<String> ids);
}
