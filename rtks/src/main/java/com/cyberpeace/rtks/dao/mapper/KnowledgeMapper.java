package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.rtks.controller.req.KnowledgeListReq;
import com.cyberpeace.rtks.controller.res.CourseRelatedRes;
import com.cyberpeace.rtks.res.KnowledgeRes;
import com.cyberpeace.rtks.dao.entity.Knowledge;

/**
 * 知识点Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface KnowledgeMapper extends BaseMapper<Knowledge> {

	/**
	 * 获取知识点列表由id列表
	 *
	 * @param page 分页
	 * @param ids  id列表
	 * @return {@code IPage<KnowledgeRes> }
	 * @throws
	 * @see Page
	 * @see List<String>
	 * @see IPage<KnowledgeRes>
	 */
	IPage<KnowledgeRes> getKnowledgeListByIds(Page page, List<String> ids);

	/**
	 * 列表知识点
	 *
	 * @param page             分页
	 * @param knowledgeListReq 知识点列表请求体
	 * @return {@code IPage<KnowledgeRes> }
	 * @throws
	 * @see Page
	 * @see KnowledgeListReq
	 * @see IPage<KnowledgeRes>
	 */
	IPage<KnowledgeRes> listKnowledge(Page page, KnowledgeListReq knowledgeListReq);

	/**
	 * 获取知识点由资源id
	 *
	 * @param id id
	 * @return {@code KnowledgeRes }
	 * @throws
	 * @see String
	 * @see KnowledgeRes
	 */
	KnowledgeRes getKnowledgeByResourceId(String id);

	/**
	 * 获取知识点由课程id
	 *
	 * @param ids id列表
	 * @return {@code List<CourseRelatedRes> }
	 * @throws
	 * @see List<String>
	 * @see List<CourseRelatedRes>
	 */
	List<CourseRelatedRes> getKnowledgeByCourseId(List<String> ids);
}
