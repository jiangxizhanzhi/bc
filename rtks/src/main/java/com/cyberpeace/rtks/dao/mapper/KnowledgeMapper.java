package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.rtks.controller.req.KnowledgeListReq;
import com.cyberpeace.rtks.controller.res.CourseRelatedRes;
import com.cyberpeace.rtks.controller.res.KnowledgeRes;
import com.cyberpeace.rtks.dao.entity.Knowledge;

/**
 * 知识Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
@Repository
public interface KnowledgeMapper extends BaseMapper<Knowledge> {

	/**
	 * 获取知识列表由id列表
	 *
	 * @param page 分页
	 * @param ids  id列表
	 * @return {@code IPage<KnowledgeRes> }
	 */
	IPage<KnowledgeRes> getKnowledgeListByIds(Page page, List<String> ids);

	/**
	 * 列表知识
	 *
	 * @param page             分页
	 * @param knowledgeListReq 知识列表请求体
	 * @return {@code IPage<KnowledgeRes> }
	 */
	IPage<KnowledgeRes> listKnowledge(Page page, KnowledgeListReq knowledgeListReq);

	/**
	 * 获取知识由资源id
	 *
	 * @param id id
	 * @return {@code KnowledgeRes }
	 */
	KnowledgeRes getKnowledgeByResourceId(String id);

	/**
	 * 获取知识由课程id
	 *
	 * @param ids id列表
	 * @return {@code List<CourseRelatedRes> }
	 */
	List<CourseRelatedRes> getKnowledgeByCourseId(List<String> ids);
}
