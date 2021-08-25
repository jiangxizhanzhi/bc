package com.cyberpeace.oj.rtks.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.util.JsonUtil;
import com.cyberpeace.oj.common.util.PageUtil;
import com.cyberpeace.oj.rtks.controller.req.KnowledgeListReq;
import com.cyberpeace.oj.rtks.controller.res.KnowledgeRes;
import com.cyberpeace.oj.rtks.service.RelatedCourseService;
import com.cyberpeace.oj.rtks.dao.entity.Knowledge;
import com.cyberpeace.oj.rtks.dao.entity.RelatedCourse;
import com.cyberpeace.oj.rtks.dao.mapper.KnowledgeMapper;
import com.cyberpeace.oj.rtks.service.KnowledgeService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService {

	@Autowired
	private KnowledgeMapper knowledgeMapper;

	@Autowired
	private RelatedCourseService relatedCourseDao;

	@Override
	public ListRsp<KnowledgeRes> page(KnowledgeListReq knowledgeListReq) {
		Page page = knowledgeListReq.toPageParam();

		IPage<KnowledgeRes> result = knowledgeMapper.listKnowledge(page, knowledgeListReq);

		List<KnowledgeRes> knowledgeResList = result.getRecords();

		List<Knowledge> knowledgeList = Lists.newArrayList();

		for (KnowledgeRes knowledgeRes : knowledgeResList) {
			String courses = knowledgeRes.getCourses();

			List<RelatedCourse> relatedCourseList = Lists.newArrayList();
			if (relatedCourseDao.setCourseResult(courses, relatedCourseList)) {
				Knowledge knowledge = new Knowledge();
				BeanUtils.copyProperties(knowledgeRes, knowledge);
				knowledgeList.add(knowledge);
			} ;

			knowledgeRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
			knowledgeRes.setCourseList(relatedCourseList);
		}
		updateBatchById(knowledgeList);

		return PageUtil.getListResult(result);
	}

	@Override
	public KnowledgeRes getKnowledgeByResourceId(String id) {
		KnowledgeRes knowledgeRes = knowledgeMapper.getKnowledgeByResourceId(id);
		List<RelatedCourse> relatedCourseList = Lists.newArrayList();

		if (relatedCourseDao.setCourseResult(knowledgeRes.getCourses(), relatedCourseList)) {
			Knowledge knowledge = new Knowledge();
			BeanUtils.copyProperties(knowledgeRes, knowledge);
			updateById(knowledge);
		} ;

		knowledgeRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
		knowledgeRes.setCourseList(relatedCourseList);

		return knowledgeRes;
	}

	@Override
	public List<String> filterIds(List<String> ids) {
		return knowledgeMapper.filterIds(ids);
	}
}
