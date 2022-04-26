package com.cyberpeace.rtks.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

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
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.controller.req.SkillListReq;
import com.cyberpeace.rtks.controller.res.RelatedCourse;
import com.cyberpeace.rtks.controller.res.RoleDetailRes;
import com.cyberpeace.rtks.controller.res.SkillRes;
import com.cyberpeace.rtks.dao.entity.CourseRelated;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.dao.entity.SkillMissionRelation;
import com.cyberpeace.rtks.dao.mapper.SkillMapper;
import com.cyberpeace.rtks.dao.mapper.SkillMissionRelationMapper;
import com.cyberpeace.rtks.service.CourseRelatedService;
import com.cyberpeace.rtks.service.RelatedCourseService;
import com.cyberpeace.rtks.service.SkillService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 *
 */
@Slf4j
@Service
public class SkillServiceImpl extends ServiceImpl<SkillMapper, Skill> implements SkillService {

	@Autowired
	private SkillMapper skillMapper;

	@Autowired
	private RelatedCourseService relatedCourseDao;

	@Autowired
	private CourseRelatedService courseRelatedService;

	@Autowired
	private SkillMissionRelationMapper skillMissionRelationMapper;

	@Override
	public ListRsp<SkillRes> page(SkillListReq skillListReq) {
		Page page = skillListReq.toPageParam();

		IPage<SkillRes> result = skillMapper.listSkill(page, skillListReq);

		List<SkillRes> skillResList = result.getRecords();
		checkSkillRes(skillResList, RoleDetailRes.RELATED_COURSE_ALL);

		return PageUtil.getListResult(result);
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
			// skill.setCourses(courses);

			updateById(skill);
		} else {
			// skillRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
			skillRes.setCourseList(relatedCourseList);
		}

		return skillRes;
	}

	@Override
	public Skill getOr404(String skillId) {
		Skill skill = getById(skillId);
		if (skill == null) {
			log.error("skill not exist！skillId=[{}]", skillId);
			throw new BusinessException(RtksBusinessErrorCode.SKILL_NOT_EXIST);
		}
		return skill;
	}

	@Override
	public void checkSkillRes(List<SkillRes> skillResList, String related_course_type) {
		List<Skill> skillList = Collections.synchronizedList(new ArrayList<>());
		UserInfo userInfo = UserInfoContext.getUserContext(); // 创建一个线程池
		ExecutorService exec = new ThreadPoolExecutor(10, 100, 60L, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
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
						// skill.setCourses(courses);

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
	public void addMissionRelation(String skillId, List<String> missionTemplateIds) {
		if (missionTemplateIds.isEmpty()) {
			return;
		}
		List<SkillMissionRelation> relations = Lists.newArrayList();
		for (String missionTemplateId : missionTemplateIds) {
			SkillMissionRelation relation = new SkillMissionRelation();
			relation.setSkillId(skillId);
			relation.setMissionTemplateId(missionTemplateId);
			// todo
			relation.setWeight(1);
		}
		// 插入前检查是否存在任务。 todo
		skillMissionRelationMapper.insertBatch(relations);
	}

	@Override
	public void deleteMissionRelation(String skillId, List<String> missionTemplateIds) {
		skillMissionRelationMapper.batchDeleteBySkillAndMissions(skillId, missionTemplateIds);
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

}
