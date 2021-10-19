package com.cyberpeace.rtks.service.impl;

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
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.controller.course.CourseConfig;
import com.cyberpeace.rtks.controller.req.SkillListReq;
import com.cyberpeace.rtks.controller.res.CourseRelatedRes;
import com.cyberpeace.rtks.controller.res.RelatedCourse;
import com.cyberpeace.rtks.controller.res.RoleDetailRes;
import com.cyberpeace.rtks.controller.res.SkillRes;
import com.cyberpeace.rtks.dao.entity.CourseRelated;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.dao.mapper.SkillMapper;
import com.cyberpeace.rtks.service.*;
import com.cyberpeace.rtks.service.bo.KnowledgeUnderCourseBo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 *
 */
@Service
public class SkillServiceImpl extends ServiceImpl<SkillMapper, Skill> implements SkillService {

	@Autowired
	private SkillMapper skillMapper;

	@Autowired
	private RelatedCourseService relatedCourseDao;

	@Autowired
	private SkillCategoryService skillCategoryService;

	@Autowired
	private SkillSystemService skillSystemService;

	@Autowired
	private CourseRelatedService courseRelatedService;

	@Override
	public ListRsp<SkillRes> page(SkillListReq skillListReq) {
		Page page = skillListReq.toPageParam();

		IPage<SkillRes> result = skillMapper.listSkill(page, skillListReq);

		List<SkillRes> skillResList = result.getRecords();
		checkSkillRes(skillResList, RoleDetailRes.RELATED_COURSE_ALL);

		return PageUtil.getListResult(result);
	}

	@Override
	public List<String> getCourseByIds(List<String> skillIds) {
		if (CollectionUtils.isEmpty(skillIds)) {
			return Lists.newArrayList();
		}
		List<Skill> skills = skillMapper.selectBatchIds(skillIds);
		List<String> courseIds = skills.stream()
				.map(skill -> JsonUtil.toJsonObjectArray(skill.getCourses(),
						KnowledgeUnderCourseBo.class))
				.flatMap(Collection::stream).map(KnowledgeUnderCourseBo::getResourceId).distinct()
				.collect(Collectors.toList());
		return courseIds;
	}

	@Override
	public SkillRes getSkillByResourceId(String id) {
		UserInfo userInfo = UserInfoContext.getUserContext();
		SkillRes skillRes = skillMapper.getSkillByResourceId(id);
		List<RelatedCourse> relatedCourseList = Lists.newArrayList();
		List<CourseStudyProcessBo> processBos = Lists.newArrayList();

		if (relatedCourseDao.setCourseResult(userInfo, skillRes.getCourses(), relatedCourseList,
				processBos, RoleDetailRes.RELATED_COURSE_COURSE)) {
			// skillRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
			skillRes.setCourseList(relatedCourseList);

			Skill skill = new Skill();
			BeanUtils.copyProperties(skillRes, skill);
			// 修改关联表
			String courses = courseRelatedService.saveCourseRelatedList(relatedCourseList,
					skill.getResourceId(), CourseRelated.RELATED_TYPE_SKILL);
			skill.setCourses(courses);

			updateById(skill);
		} else {
			// skillRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
			skillRes.setCourseList(relatedCourseList);
		}

		return skillRes;
	}

	@Override
	public void validate(Skill skill) {
		if (skillCategoryService.getById(skill.getCategoryId()) == null) {
			throw new InvalidParamException("类别不存在");
		}
		if (skillSystemService.getById(skill.getSystemId()) == null) {
			throw new InvalidParamException("体系不存在");
		}
	}

	@Override
	public Skill getOr404(String skillId) {
		Skill skill = getById(skillId);
		if (skill == null) {
			throw new ResourceNotFoundException("技能" + skillId + "未找到");
		}
		return skill;
	}

	@Override
	public void checkSkillRes(List<SkillRes> skillResList, String related_course_type) {
		List<Skill> skillList = Collections.synchronizedList(new ArrayList<>());
		UserInfo userInfo = UserInfoContext.getUserContext(); // 创建一个线程池
		ExecutorService exec = new ThreadPoolExecutor(10, 100, 60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());
		// 定义一个任务集合
		List<Callable<Integer>> tasks = new ArrayList<>();
		Callable<Integer> task = null;

		for (SkillRes skillRes : skillResList) {
			task = new Callable<Integer>() {

				@Override
				public Integer call() throws Exception {
					String courses = skillRes.getCourses();

					List<RelatedCourse> relatedCourseList = Lists.newArrayList();
					List<CourseStudyProcessBo> processBos = Lists.newArrayList();

					boolean isDeleted = relatedCourseDao.setCourseResult(userInfo, courses,
							relatedCourseList, processBos, related_course_type);
					// skillRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
					skillRes.setCourseList(relatedCourseList);
					if (RoleDetailRes.RELATED_COURSE_STUDY.equals(related_course_type)) {
						skillRes.setProcess(calculateProcess(processBos, relatedCourseList));
						skillRes.setCourses("");
						skillRes.setCourseList(null);
						skillRes.setCourseStudyProcessBoList(processBos);
					} else if (RoleDetailRes.RELATED_COURSE_ALL.equals(related_course_type)) {
						skillRes.setProcess(calculateProcess(processBos, relatedCourseList));
						skillRes.setCourseStudyProcessBoList(processBos);
					}

					if (isDeleted) {

						// 修改关联表
						Skill skill = new Skill();
						BeanUtils.copyProperties(skillRes, skill);
						courses = courseRelatedService.saveCourseRelatedList(relatedCourseList,
								skill.getResourceId(), CourseRelated.RELATED_TYPE_SKILL);
						skill.setCourses(courses);

						skillList.add(skill);
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
		updateBatchById(skillList);
	}

	@Override
	public List<SkillRes> getAllSkills() {
		List<SkillRes> skillResList = skillMapper.getAllSkills("");
		checkSkillRes(skillResList, RoleDetailRes.RELATED_COURSE_ALL);

		return skillResList;
	}

	@Override
	public List<SkillRes> getSkillBySceneCategory(String scene_category_id) {
		List<SkillRes> skillResList = skillMapper.getAllSkills(scene_category_id);

		return skillResList;
	}

	@Override
	public List<SkillRes> getAllSkillsBySkillSystemId(String skillSystemId) {
		List<SkillRes> skillResList = skillMapper.getAllSkillsBySkillSystemId(skillSystemId);
		checkSkillRes(skillResList, RoleDetailRes.RELATED_COURSE_ALL);

		return skillResList;
	}

	@Override
	public Map<String, List<CourseRelatedRes>> getSkillByCourseId(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return Maps.newHashMap();
		}
		List<CourseRelatedRes> knowledgeList = skillMapper.getSkillByCourseId(ids);

		Map<String, List<CourseRelatedRes>> courseKnowledgeResMap = knowledgeList.stream()
				.collect(Collectors.groupingBy(CourseRelatedRes::getResourceId));

		return courseKnowledgeResMap;
	}

	public Integer calculateProcess(List<CourseStudyProcessBo> processBos,
			List<RelatedCourse> relatedCourseList) {
		Map<String, Integer> map = Maps.newHashMap();

		for (RelatedCourse relatedCourse : relatedCourseList) {
			map.put(relatedCourse.getResourceId(), relatedCourse.getWeight());
		}

		Double sumWeight = Double.valueOf(0);
		Double sumProcess = Double.valueOf(0);

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
	public List<CourseConfig> getCourseConfigs(String skillId) {
		Skill skill = getOr404(skillId);
		return JsonUtil.toJsonObjectArray(skill.getCourses(), CourseConfig.class);
	}
}
