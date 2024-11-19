package com.cyberpeace.rtks.service.impl;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.controller.course.CourseConfig;
import com.cyberpeace.rtks.controller.req.KnowledgeListReq;
import com.cyberpeace.rtks.controller.res.CourseRelatedRes;
import com.cyberpeace.rtks.res.KnowledgeRes;
import com.cyberpeace.rtks.controller.res.RelatedCourse;
import com.cyberpeace.rtks.res.RoleDetailRes;
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
 * 知识点Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see ServiceImpl
 * @see KnowledgeService
 * @since 1.0.0
 */
@Service
@Slf4j
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService {

	/**
	 * 知识点Mapper
	 *
	 * @see KnowledgeMapper
	 */
	@Autowired
	private KnowledgeMapper knowledgeMapper;

	/**
	 * 相关课程Service
	 *
	 * @see RelatedCourseService
	 */
	@Autowired
	private RelatedCourseService relatedCourseService;

	/**
	 * 知识点类别Service
	 *
	 * @see KnowledgeCategoryService
	 */
	@Autowired
	private KnowledgeCategoryService knowledgeCategoryService;

	/**
	 * 课程相关Service
	 *
	 * @see CourseRelatedService
	 */
	@Autowired
	private CourseRelatedService courseRelatedService;

	/**
	 * 分页
	 *
	 * @param knowledgeListReq 知识点列表请求体
	 * @return {@code ListRsp<KnowledgeRes> }
	 * @throws
	 * @see KnowledgeListReq
	 * @see ListRsp<KnowledgeRes>
	 */
	@Override
	public ListRsp<KnowledgeRes> page(KnowledgeListReq knowledgeListReq) {
		Page page = knowledgeListReq.toPageParam();

		IPage<KnowledgeRes> result = knowledgeMapper.listKnowledge(page, knowledgeListReq);

		List<KnowledgeRes> knowledgeResList = result.getRecords();

		checkKnowledgeRes(knowledgeResList, RoleDetailRes.RELATED_COURSE_ALL);

		return PageUtil.getListResult(result);
	}

	/**
	 * 获取知识点由资源id
	 *
	 * @param id id
	 * @return {@code KnowledgeRes }
	 * @throws
	 * @see String
	 * @see KnowledgeRes
	 */
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

	/**
	 * 获取OR404
	 *
	 * @param knowledgeId 知识点id
	 * @return {@code Knowledge }
	 * @throws
	 * @see String
	 * @see Knowledge
	 */
	@Override
	public Knowledge getOr404(String knowledgeId) {
		Knowledge knowledge = getById(knowledgeId);
		if (knowledge == null) {
			log.error("knowledge not exist！knowledgeId=[{}]", knowledgeId);
			throw new BusinessException(RtksBusinessErrorCode.KNOWLEDGE_NOT_EXIST);
		}
		return knowledge;
	}

	/**
	 * 验证
	 *
	 * @param knowledge 知识点
	 * @throws
	 * @see Knowledge
	 */
	@Override
	public void validate(Knowledge knowledge) {
		if (knowledgeCategoryService.getById(knowledge.getCategoryId()) == null) {
			log.error("knowledge Category not exist！CategoryId=[{}]", knowledge.getCategoryId());
			throw new BusinessException(RtksBusinessErrorCode.CATEGORY_NOT_EXIST);
		}
	}

	/**
	 * 获取知识点由课程id
	 *
	 * @param ids id列表
	 * @return {@code Map<String, List<CourseRelatedRes>> }
	 * @throws
	 * @see List<String>
	 * @see Map<String, List<CourseRelatedRes>>
	 */
	@Override
	public Map<String, List<CourseRelatedRes>> getKnowledgeByCourseId(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return Maps.newHashMap();
		}
		List<CourseRelatedRes> knowledgeList = knowledgeMapper.getKnowledgeByCourseId(ids);

		return knowledgeList.stream().collect(Collectors.groupingBy(CourseRelatedRes::getResourceId));
	}

	/**
	 * 检查知识点结果
	 *
	 * @param knowledgeResList  知识点结果列表
	 * @param relatedCourseType 相关课程类型
	 * @throws
	 * @see List<KnowledgeRes>
	 * @see String
	 */
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
						knowledgeRes.setSuggestDuration(BigDecimal.valueOf(suggestDuration)
								.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());
					} else if (RoleDetailRes.RELATED_COURSE_ALL.equals(relatedCourseType)) {// 查询基本课程和学习进度
						knowledgeRes.setProcess(calculateProcess(processBos, relatedCourseList));
						knowledgeRes.setCourseStudyProcessBoList(processBos);
						double suggestDuration = relatedCourseList.stream()
								.mapToInt(RelatedCourse::getCourseDuration).sum() / 60.0;
						knowledgeRes.setSuggestDuration(BigDecimal.valueOf(suggestDuration)
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
			exec.invokeAll(tasks);
		} catch (InterruptedException e) {
			log.error("checkKnowledgeRes invokeAll error");
			Thread.currentThread().interrupt();
		}

		// 关闭线程池
		exec.shutdown();
		updateBatchById(knowledgeList);
	}

	/**
	 * 计算过程
	 *
	 * @param processBos        处理 BOS
	 * @param relatedCourseList 相关课程列表
	 * @return {@code Integer }
	 * @throws
	 * @see List<CourseStudyProcessBo>
	 * @see List<RelatedCourse>
	 * @see Integer
	 */
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

	/**
	 * 获取课程配置
	 *
	 * @param knowledgeId 知识点id
	 * @return {@code List<CourseConfig> }
	 * @throws
	 * @see String
	 * @see List<CourseConfig>
	 */
	@Override
	public List<CourseConfig> getCourseConfigs(String knowledgeId) {
		Knowledge knowledge = getOr404(knowledgeId);
		return JsonUtil.toJsonObjectArray(knowledge.getCourses(), CourseConfig.class);
	}

	/**
	 * 获取课程由id列表
	 *
	 * @param knowledgeIds 知识点id列表
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
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

	/**
	 * 获取全部知识点
	 *
	 * @return {@code List<KnowledgeRes> }
	 * @throws
	 * @see List<KnowledgeRes>
	 */
	@Override
	public List<KnowledgeRes> getAllKnowledge() {
		KnowledgeListReq knowledgeListReq = new KnowledgeListReq();
		knowledgeListReq.setPer_page(10000);
		Page page = knowledgeListReq.toPageParam();

		IPage<KnowledgeRes> knowledgeResIPage = knowledgeMapper.listKnowledge(page, knowledgeListReq);
		checkKnowledgeRes(knowledgeResIPage.getRecords(), RoleDetailRes.RELATED_COURSE_ALL);
		return knowledgeResIPage.getRecords();
	}
}
