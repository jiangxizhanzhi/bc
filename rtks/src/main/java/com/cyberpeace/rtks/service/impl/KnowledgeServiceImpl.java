package com.cyberpeace.rtks.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
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

import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

/**
 *
 */
@Service
@Slf4j
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService {

	@Autowired
	private KnowledgeMapper knowledgeMapper;

	@Autowired
	private RelatedCourseService relatedCourseService;

	@Autowired
	private KnowledgeCategoryService knowledgeCategoryService;

	@Autowired
	private CourseRelatedService courseRelatedService;

	@Autowired
	private CourseMapper courseMapper;

	@PostConstruct
	private void init(){
		// 第一次部署会因表不存在而无法执行，升级可以执行成功
		try{
			clearOutdatedFakeData();
		} catch (Exception e){
			log.info("failed to clear outdated fake course-knowledge relation", e);
		}
	}

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
		UserInfo userInfo = UserInfoContext.getUserContext();
		KnowledgeRes knowledgeRes = knowledgeMapper.getKnowledgeByResourceId(id);
		List<RelatedCourse> relatedCourseList = Lists.newArrayList();
		List<CourseStudyProcessBo> processBos = Lists.newArrayList();

		//
		if (relatedCourseService.setCourseResult(userInfo, knowledgeRes.getCourses(),
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
			log.error("knowledge not exist！knowledgeId=[{}]", knowledgeId);
			throw new BusinessException(RtksBusinessErrorCode.KNOWLEDGE_NOT_EXIST);
		}
		return knowledge;
	}

	@Override
	public void validate(Knowledge knowledge) {
		if (knowledgeCategoryService.getById(knowledge.getCategoryId()) == null) {
			log.error("knowledge Category not exist！CategoryId=[{}]", knowledge.getCategoryId());
			throw new BusinessException(RtksBusinessErrorCode.CATEGORY_NOT_EXIST);
		}
	}

	@Override
	public Map<String, List<CourseRelatedRes>> getKnowledgeByCourseId(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return Maps.newHashMap();
		}
		List<CourseRelatedRes> knowledgeList = knowledgeMapper.getKnowledgeByCourseId(ids);

		return knowledgeList.stream().collect(Collectors.groupingBy(CourseRelatedRes::getResourceId));
	}

	@Override
	public void checkKnowledgeRes(List<KnowledgeRes> knowledgeResList, String relatedCourseType) {
		List<Knowledge> knowledgeList = Collections.synchronizedList(new ArrayList<>());
		UserInfo userInfo = UserInfoContext.getUserContext();

		// 创建一个线程池
		ExecutorService exec = new ThreadPoolExecutor(10, 100, 60L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
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

					boolean isDeleted = relatedCourseService.setCourseResult(userInfo, courses,
							relatedCourseList, processBos, relatedCourseType);

					knowledgeRes.setCourses("");
					knowledgeRes.setCourseList(relatedCourseList);
					if (RoleDetailRes.RELATED_COURSE_STUDY.equals(relatedCourseType)) {// 查询学习进度
						knowledgeRes.setProcess(calculateProcess(processBos, relatedCourseList));
						knowledgeRes.setCourses("");
						knowledgeRes.setCourseList(null);
						knowledgeRes.setCourseStudyProcessBoList(processBos);
						double suggestDuration = relatedCourseList.stream()
								.mapToInt(RelatedCourse::getCourseDuration).sum() / 60.0;
						knowledgeRes.setSuggestDuration(new BigDecimal(suggestDuration)
								.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
					} else if (RoleDetailRes.RELATED_COURSE_ALL.equals(relatedCourseType)) {// 查询基本课程和学习进度
						knowledgeRes.setProcess(calculateProcess(processBos, relatedCourseList));
						knowledgeRes.setCourseStudyProcessBoList(processBos);
						double suggestDuration = relatedCourseList.stream()
								.mapToInt(RelatedCourse::getCourseDuration).sum() / 60.0;
						knowledgeRes.setSuggestDuration(new BigDecimal(suggestDuration)
								.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());

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

					return 1;
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
			return MathUtils.ceilDivide(sumProcess, sumWeight);
		}
	}

	@Override
	public List<CourseConfig> getCourseConfigs(String knowledgeId) {
		Knowledge knowledge = getOr404(knowledgeId);
		return JsonUtil.toJsonObjectArray(knowledge.getCourses(), CourseConfig.class);
	}

	@Override
	public List<String> getCourseByIds(List<String> knowledgeIds) {
		if (CollectionUtils.isEmpty(knowledgeIds)) {
			return Lists.newArrayList();
		}
		List<Knowledge> knowledges = knowledgeMapper.selectBatchIds(knowledgeIds);
		List<String> courseIds = knowledges.stream()
				.map(knowledge -> JsonUtil.toJsonObjectArray(knowledge.getCourses(),
						KnowledgeUnderCourseBo.class))
				.flatMap(Collection::stream).map(KnowledgeUnderCourseBo::getResourceId).distinct()
				.collect(Collectors.toList());
		return courseIds;
	}

	@Override
	public List<KnowledgeRes> getAllKnowledge() {
		KnowledgeListReq knowledgeListReq = new KnowledgeListReq();
		knowledgeListReq.setPer_page(10000);
		Page page = knowledgeListReq.toPageParam();

		List<KnowledgeRes> knowledgeResList = knowledgeMapper.listKnowledge(page, knowledgeListReq)
				.getRecords();
		checkKnowledgeRes(knowledgeResList, RoleDetailRes.RELATED_COURSE_ALL);

		return knowledgeResList;
	}

	@Override
	public void clearCourseRel() {
		knowledgeMapper.clearCourseRel();
	}

	/**
	 * 保持向前兼容，去掉k数据表中的关联课程假数据
	 */
	private void clearOutdatedFakeData() {
		List<Knowledge> knowledgeList = knowledgeMapper.selectAll();
		List<String> courseIds = knowledgeList.stream()
				.map(knowledge -> JsonUtil.toJsonObjectArray(knowledge.getCourses(),
						KnowledgeUnderCourseBo.class))
				.flatMap(Collection::stream).map(KnowledgeUnderCourseBo::getResourceId).distinct()
				.collect(Collectors.toList());
		// 获取实际存在的课程
		List<String> existingIds = courseMapper.notDeleteIdsByIds(courseIds);
		for (Knowledge knowledge : knowledgeList) {
			List<KnowledgeUnderCourseBo> courseBos = JsonUtil
					.toJsonObjectArray(knowledge.getCourses(), KnowledgeUnderCourseBo.class);
			if (courseBos.isEmpty()) {
				continue;
			}
			// remove fake
			courseBos.removeIf(courseBo -> !existingIds.contains(courseBo.getResourceId()));
			String newCourses = JsonUtil.toJsonString(courseBos);
			// update
			if (!newCourses.equals(knowledge.getCourses())) {
				knowledge.setCourses(newCourses);
				updateById(knowledge);
			}
		}
	}
}
