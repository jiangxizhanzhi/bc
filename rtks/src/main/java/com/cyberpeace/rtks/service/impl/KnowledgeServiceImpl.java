package com.cyberpeace.rtks.service.impl;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.controller.course.CourseConfig;
import com.cyberpeace.rtks.controller.req.KnowledgeListReq;
import com.cyberpeace.rtks.controller.res.CourseRelatedRes;
import com.cyberpeace.rtks.controller.res.KnowledgeRes;
import com.cyberpeace.rtks.controller.res.RelatedCourse;
import com.cyberpeace.rtks.controller.res.RoleDetailRes;
import com.cyberpeace.rtks.dao.entity.CourseRelated;
import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.mapper.KnowledgeMapper;
import com.cyberpeace.rtks.service.CourseRelatedService;
import com.cyberpeace.rtks.service.KnowledgeCategoryService;
import com.cyberpeace.rtks.service.KnowledgeService;
import com.cyberpeace.rtks.service.RelatedCourseService;
import com.cyberpeace.rtks.service.bo.KnowledgeUnderCourseBo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 *
 */
@Service
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService {

	@Autowired
	private KnowledgeMapper knowledgeMapper;

	@Autowired
	private RelatedCourseService relatedCourseService;

	@Autowired
	private KnowledgeCategoryService knowledgeCategoryService;

	@Autowired
	private CourseRelatedService courseRelatedService;

	@Override
	public ListRsp<KnowledgeRes> page(KnowledgeListReq knowledgeListReq) {
		Page page = knowledgeListReq.toPageParam();

		IPage<KnowledgeRes> result = knowledgeMapper.listKnowledge(page, knowledgeListReq);

		List<KnowledgeRes> knowledgeResList = result.getRecords();

		checkKnowledgeRes(knowledgeResList, RoleDetailRes.RELATED_COURSE_ALL);

		return PageUtil.getListResult(result);
	}

	@Override
	public KnowledgeRes getKnowledgeByResourceId(String id) {
		String userId = UserInfoContext.getUserContext().getUserId();
		KnowledgeRes knowledgeRes = knowledgeMapper.getKnowledgeByResourceId(id);
		List<RelatedCourse> relatedCourseList = Lists.newArrayList();
		List<CourseStudyProcessBo> processBos = Lists.newArrayList();

		//
		if (relatedCourseService.setCourseResult(userId, knowledgeRes.getCourses(),
				relatedCourseList, processBos, RoleDetailRes.RELATED_COURSE_COURSE)) {
			knowledgeRes.setCourseList(relatedCourseList);

			Knowledge knowledge = new Knowledge();
			BeanUtils.copyProperties(knowledgeRes, knowledge);

			// 修改关联表
			String courses = courseRelatedService.saveCourseRelatedList(relatedCourseList,
					knowledge.getResourceId(), CourseRelated.RELATED_TYPE_KNOWLEDGE);
			knowledge.setCourses(courses);
			updateById(knowledge);

		}

		knowledgeRes.setCourseList(relatedCourseList);

		return knowledgeRes;
	}

	@Override
	public Knowledge getOr404(String knowledgeId) {
		Knowledge knowledge = getById(knowledgeId);
		if (knowledge == null) {
			throw new ResourceNotFoundException("知识点" + knowledgeId + "未找到");
		}
		return knowledge;
	}

	@Override
	public void validate(Knowledge knowledge) {
		if (knowledgeCategoryService.getById(knowledge.getCategoryId()) == null) {
			throw new InvalidParamException("类别不存在");
		}
	}

	@Override
	public Map<String, List<CourseRelatedRes>> getKnowledgeByCourseId(List<String> ids) {
		List<CourseRelatedRes> knowledgeList = knowledgeMapper.getKnowledgeByCourseId(ids);

		Map<String, List<CourseRelatedRes>> courseKnowledgeResMap = knowledgeList.stream()
				.collect(Collectors.groupingBy(CourseRelatedRes::getResourceId));
		return courseKnowledgeResMap;
	}

	@Override
	public void checkKnowledgeRes(List<KnowledgeRes> knowledgeResList, String relatedCourseType) {
		List<Knowledge> knowledgeList = Collections.synchronizedList(new ArrayList<>());
		String userId = UserInfoContext.getUserContext().getUserId();
		// 创建一个线程池
		ExecutorService exec = new ThreadPoolExecutor(10, 100, 60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());
		// 定义一个任务集合
		List<Callable<Integer>> tasks = new ArrayList<>();
		Callable<Integer> task = null;

		// 遍历知识点，获取知识点的相关内容，修改已删除的关联课程
		for (KnowledgeRes knowledgeRes : knowledgeResList) {

			task = new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					String courses = knowledgeRes.getCourses();

					List<RelatedCourse> relatedCourseList = Lists.newArrayList();
					List<CourseStudyProcessBo> processBos = Lists.newArrayList();

					boolean isDeleted = relatedCourseService.setCourseResult(userId, courses,
							relatedCourseList, processBos, relatedCourseType);

					knowledgeRes.setCourses("");
					knowledgeRes.setCourseList(relatedCourseList);
					if (RoleDetailRes.RELATED_COURSE_STUDY.equals(relatedCourseType)) {
						knowledgeRes.setProcess(calculateProcess(processBos, relatedCourseList));
						knowledgeRes.setCourses("");
						knowledgeRes.setCourseList(null);
						knowledgeRes.setCourseStudyProcessBoList(processBos);
					} else if (RoleDetailRes.RELATED_COURSE_ALL.equals(relatedCourseType)) {
						knowledgeRes.setProcess(calculateProcess(processBos, relatedCourseList));
						knowledgeRes.setCourseStudyProcessBoList(processBos);
					}

					if (isDeleted) {
						// 修改关联表
						Knowledge knowledge = new Knowledge();
						BeanUtils.copyProperties(knowledgeRes, knowledge);
						courses = courseRelatedService.saveCourseRelatedList(relatedCourseList,
								knowledge.getResourceId(), CourseRelated.RELATED_TYPE_KNOWLEDGE);
						knowledge.setCourses(courses);
						knowledgeList.add(knowledge);

					}

					return 123;
				}

			};
			tasks.add(task);

		}

		try {
			List<Future<Integer>> results = exec.invokeAll(tasks);
			results.isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 关闭线程池
		exec.shutdown();
		updateBatchById(knowledgeList);
	}

	public Integer calculateProcess(List<CourseStudyProcessBo> processBos,
			List<RelatedCourse> relatedCourseList) {
		Map<String, Integer> map = Maps.newHashMap();

		for (RelatedCourse relatedCourse : relatedCourseList) {
			map.put(relatedCourse.getResourceId(), relatedCourse.getWeight());
		}

		Double sumWeight = 0.0;

		Double sumProcess = 0.0;
		for (CourseStudyProcessBo courseStudyProcessBo : processBos) {
			sumProcess += MathUtils.mul(courseStudyProcessBo.getProcess(),
					Double.valueOf(map.get(courseStudyProcessBo.getCourseId())));
			sumWeight += map.get(courseStudyProcessBo.getCourseId());
		}
		if (sumWeight <= 0.0) {
			return 0;
		} else {
			return MathUtils.divide(sumProcess, sumWeight, 0).intValue();
		}
	}

	@Override
	public List<CourseConfig> getCourseConfigs(String knowledgeId) {
		Knowledge knowledge = getOr404(knowledgeId);
		return JsonUtil.toJsonObjectArray(knowledge.getCourses(), CourseConfig.class);
	}

	@Override
	public List<String> getCourseByIds(List<String> knowledgeIds) {
		List<Knowledge> knowledges = knowledgeMapper.selectBatchIds(knowledgeIds);
		List<String> courseIds = knowledges.stream()
				.map(knowledge -> JsonUtil.toJsonObjectArray(knowledge.getCourses(),
						KnowledgeUnderCourseBo.class))
				.flatMap(Collection::stream).map(KnowledgeUnderCourseBo::getResourceId).distinct()
				.collect(Collectors.toList());
		return courseIds;
	}
}
