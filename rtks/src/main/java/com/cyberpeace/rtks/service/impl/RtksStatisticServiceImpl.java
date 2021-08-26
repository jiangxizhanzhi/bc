package com.cyberpeace.rtks.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import com.cyberpeace.rtks.service.bo.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.oj.course.service.CourseStatisticsService;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.dao.mapper.KnowledgeMapper;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.dao.mapper.SkillMapper;
import com.cyberpeace.rtks.service.RtksStatisticService;
import com.cyberpeace.rtks.service.TreeService;
import com.cyberpeace.rtks.service.bo.*;
import com.google.common.collect.Lists;

@Service
public class RtksStatisticServiceImpl implements RtksStatisticService {

	@Autowired
	private TreeService treeService;

	@Autowired
	private CourseStatisticsService courseStatisticsService;

	@Autowired
	private KnowledgeMapper knowledgeMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private SkillMapper skillMapper;

	/**
	 * 角色评分中技能所占百分比
	 */
	private Integer skillWeight;

	private final Integer MAX_PERCENT = 100;

	@PostConstruct
	public void init() {
		String skillWeightString = ConfigUtil.getContextProperty("skillWeight");
		skillWeight = Integer.parseInt(skillWeightString);
	}

	@Override
	public String roleMatchPercent(String roleId, String userId) {
		Integer knowledgeScore = 0;
		Integer skillScore = 0;
		// 1、计算知识点得分
		if (skillWeight.intValue() != MAX_PERCENT.intValue()) {
			knowledgeScore = calculateKnowledgeScore(roleId, userId);
		}

		// 2、计算技能得分
		if (skillWeight != 0) {
			skillScore = calculateSkillScore(roleId, userId);
		}

		// 3、技能得分和知识点得分加权平均
		Integer result = knowledgeScore * (100 - skillWeight) + skillScore * skillWeight;

		Integer ret = floor(result);

		return ret + "";
	}

	@Override
	public List<RtksRadarBo> calculateRadar(String roleId, String userId) {
		List<RtksRadarBo> ret = Lists.newArrayList();

		// 1、获取当前岗位对应的框架数据
		Level nearestLevel = treeService.getNearestLevelByRole(roleId);
		String frameId = nearestLevel.getFrame();

		// 2、获取当前岗位对应的第一树形结构
		List<Level> firstLevel = treeService.getFirstLevel(frameId);

		// 3、计算当前树形节点的评分
		for (Level level : firstLevel) {
			String matchPercent = calculateRadarByLevel(level.getResourceId(), userId);

			RtksRadarBo rtksRadarBo = new RtksRadarBo();

			rtksRadarBo.setFirstTreeId(level.getResourceId());
			rtksRadarBo.setTreeName(level.getName());
			rtksRadarBo.setMatchPercent(matchPercent);

			ret.add(rtksRadarBo);
		}

		return ret;
	}

	@Override
	public List<KnowledgeStudyPercentBo> calculateKnowledgePercent(String role, String userId) {
		// 获取当前角色的所有技能点及权重
		Role roleInfo = roleMapper.selectById(role);
		List<String> knowledgeIds = JsonUtil.toJsonObjectArray(roleInfo.getKnowledgeIds(),
				String.class);
		List<Knowledge> knowledges = knowledgeMapper.selectBatchIds(knowledgeIds);

		List<KnowledgeStudyPercentBo> percentBoList = knowledges.stream().map(knowledge -> {
			ValueDataBo valueDataBo = calculateKnowledgeScore(knowledge, userId, true);
			KnowledgeStudyPercentBo percentBo = new KnowledgeStudyPercentBo(
					valueDataBo.getResourceId(), valueDataBo.getContent(),
					valueDataBo.getProcess().toString());
			return percentBo;
		}).collect(Collectors.toList());
		return percentBoList;
	}

	@Override
	public RoleStudyBo roleKnowledgeStudyRecord(String roleId, String userId) {
		// 1、获取用户角色关联的知识点
		List<String> knowledgeIds = getKnowledgeIdsByRole(roleId);

		// 2、获取知识点对应的课程学习进度
		List<CourseStudyProcessBo> processBoList = knowledgeIds.stream().map(knowledgeId -> {
			List<String> courseIds = getCourseIdsByKnowledge(knowledgeId);
			List<CourseStudyProcessBo> processBos = courseStatisticsService.statisticByIds(courseIds, userId);
			return processBos;
		}).flatMap(Collection::stream).collect(Collectors.toList());

		return getStudyBo(processBoList);
	}


	@Override
	public RoleStudyBo roleSkillStudyRecord(String roleId, String userId) {
		// 1、获取用户角色关联的技能点
		List<String> skillIds = getSkillIdsByRole(roleId);
		List<CourseStudyProcessBo> processBoList = skillIds.stream().map(skllId -> {
			List<String> courseIds = getCourseIdsBySkill(skllId);
			List<CourseStudyProcessBo> processBos = courseStatisticsService.statisticByIds(courseIds, userId);
			return processBos;
		}).flatMap(Collection::stream).collect(Collectors.toList());
		// 2、获取技能点对应的课程
		RoleStudyBo studyBo = getStudyBo(processBoList);

		return studyBo;
	}

	/**
	 * 根据课程进度列表计算岗位学习进度
	 * @param processBoList
	 * @return
	 */
	private RoleStudyBo getStudyBo(List<CourseStudyProcessBo> processBoList) {
		// 计算总体进度
		RoleStudyBo studyBo = new RoleStudyBo();
		studyBo.setTotalCourses(processBoList.size());
		int completeTheorySum = processBoList.stream().mapToInt(CourseStudyProcessBo::getTheoryCount).sum();
		studyBo.setFinishedTheoryLessons(completeTheorySum);
		int completeExerciseSum = processBoList.stream().mapToInt(CourseStudyProcessBo::getCompleteExercise).sum();
		studyBo.setFinishedPracticeLessons(completeExerciseSum);
		int unTheorySum = processBoList.stream().mapToInt(bo -> bo.getTheoryCount() - bo.getCompleteTheory()).sum();
		studyBo.setUnFinishedTheoryLessons(unTheorySum);
		int unExerciseSum = processBoList.stream().mapToInt(bo -> bo.getExerciseCount() - bo.getCompleteExercise()).sum();
		studyBo.setUnFinishedPracticeLessons(unExerciseSum);
		return studyBo;
	}

	/**
	 * 计算角色对应的技能得分
	 * @return
	 */
	private Integer calculateSkillScore(String roleId, String userId) {
		// 1、获取当前角色的所有技能点及权重
		List<String> skillIds = getSkillIdsByRole(roleId);

		// 2、计算每个技能点的得分
		// 连接靶场数据库，没有数据点技能点算0分
		List<Integer> knowledgeScores = Lists.newArrayList();

		// 3、2中的数据根据每个技能点的权重加权平均
		return null;
	}

	/**
	 * 计算角色对应的知识点得分
	 * @param roleId
	 * @param userId
	 * @return
	 */
	private Integer calculateKnowledgeScore(String roleId, String userId) {
		// 1、获取当前角色的所有知识点及权重
		Role role = roleMapper.selectById(roleId);
		List<String> knowledgeIds = JsonUtil.toJsonObjectArray(role.getKnowledgeIds(),
				String.class);

		List<Knowledge> knowledges = knowledgeMapper.selectBatchIds(knowledgeIds);
		// 根据权重计算数据根据每个知识点的权重加权平均
		Integer percent = calculateKnowledgeFinishPercent(knowledges, userId, true);

		return percent;
	}

	/**
	 * 计算当前层级下所有知识点的完成度
	 * @param levelId
	 * @return
	 */
	private String calculateRadarByLevel(String levelId, String userId) {
		// 1、获取当前层级下所有role数据
		List<Role> roles = treeService.roleUnderLevel(levelId);

		// 2、获取所有知识点
		List<Knowledge> knowledges = mergeKnowledgeByRoles(roles);

		// 3、统计所有知识点的课时完成度
		Integer ret = calculateKnowledgeFinishPercent(knowledges, userId, true);

		return ret.toString();
	}

	/**
	 * 计算知识点涉及到的所有课时的完成度
	 * @param knowledges 知识点编号
	 * @param userId 用户编号
	 * @return
	 */
	private Integer calculateKnowledgeFinishPercent(List<Knowledge> knowledges, String userId,
			boolean useWeight) {

		// 1、统计所有知识点的进度和权重
		List<ValueDataBo> knowledgeScores = knowledges.stream()
				.map(knowledge -> calculateKnowledgeScore(knowledge, userId, useWeight))
				.collect(Collectors.toList());

		if (useWeight) {
			double sumProcess = knowledgeScores.stream()
					.mapToDouble(value -> MathUtils.mul(value.getProcess(), value.getWeight()))
					.sum();

			double sumWeight = knowledgeScores.stream().mapToDouble(ValueDataBo::getWeight).sum();
			return MathUtils.divide(sumProcess, sumWeight, 0).intValue();
		} else {
			// 2、统计所有课程的总课时A；
			double lessonCount = knowledgeScores.stream().mapToDouble(ValueDataBo::getLessonCount)
					.sum();
			// 3、统计所有课程的已学课时B;
			double completeCount = knowledgeScores.stream()
					.mapToDouble(ValueDataBo::getCompleteCount).sum();
			// 4、B/A，向上取整
			Double divide = MathUtils.divide(completeCount, lessonCount, 0);
			return divide.intValue();
		}

	}

	/**
	 * 获取角色列表的所有知识点集合。
	 *
	 * 注意去重！
	 * @param roles
	 * @return
	 */
	private List<Knowledge> mergeKnowledgeByRoles(List<Role> roles) {
		List<String> knowledgeIds = roles.stream()
				.map(role -> JsonUtil.toJsonObjectArray(role.getKnowledgeIds(), String.class))
				.flatMap(Collection::stream).collect(Collectors.toList());

		if (CollectionUtils.isEmpty(knowledgeIds)) {
			return Lists.newArrayList();
		}

		List<Knowledge> knowledges = knowledgeMapper.selectBatchIds(knowledgeIds);
		return knowledges;
	}

	/**
	 * 计算知识点的完成度。
	 * 计算方法：
	 * 该知识点下所有课程的学习进度和课程权重的加权平均。
	 * @param knowledge 知识点
	 * @param userId 用户编号
	 * @param userWeight 是否使用权重
	 * @return
	 */
	private ValueDataBo calculateKnowledgeScore(Knowledge knowledge, String userId,
			boolean userWeight) {

		// 获取知识点课程列表
		ValueDataBo knowledgeDataBo = new ValueDataBo();
		knowledgeDataBo.setContent(knowledge.getContent());
		knowledgeDataBo.setResourceId(knowledge.getResourceId());

		List<KnowledgeUnderCourseBo> courses = JsonUtil.toJsonObjectArray(knowledge.getCourses(),
				KnowledgeUnderCourseBo.class);

		if (CollectionUtils.isEmpty(courses)) {
			knowledgeDataBo.setProcess(new Double(0));
			return knowledgeDataBo;
		}

		List<String> courseIds = courses.stream().map(KnowledgeUnderCourseBo::getResourceId)
				.collect(Collectors.toList());

		// 计算课程学习进度
		List<CourseStudyProcessBo> studyProcessBos = courseStatisticsService
				.statisticByIds(courseIds, userId);
		Map<String, CourseStudyProcessBo> boMap = studyProcessBos.stream().collect(Collectors.toMap(
				CourseStudyProcessBo::getCourseId, courseStudyProcessBo -> courseStudyProcessBo));

		if (userWeight) {
			// 使用权重 sum(进度 * 权重)/sum(权重)
			List<ValueDataBo> courseProcess = courses.stream().map(underCourseBo -> {
				ValueDataBo dataBo = new ValueDataBo();
				dataBo.setProcess(
						new Double(boMap.get(underCourseBo.getResourceId()).getProcess()));
				dataBo.setWeight(new Double(underCourseBo.getWeight()));
				return dataBo;
			}).collect(Collectors.toList());

			double courseProcessSum = courseProcess.stream()
					.mapToDouble(value -> MathUtils.mul(value.getProcess(), value.getWeight()))
					.sum();

			double weightSum = courseProcess.stream().mapToDouble(value -> value.getWeight()).sum();

			knowledgeDataBo.setResourceId(knowledge.getResourceId());
			knowledgeDataBo.setWeight(new Double(knowledge.getWeight()));
			knowledgeDataBo.setProcess(MathUtils.divide(courseProcessSum, weightSum));

		} else {
			// 学习课时总数 /总课时数量
			double completeCount = studyProcessBos.stream()
					.mapToDouble(courseStudy -> new Double(courseStudy.getCompleteCount())).sum();

			double lessonCount = studyProcessBos.stream()
					.mapToDouble(courseStudy -> new Double(courseStudy.getLessonCount())).sum();

			knowledgeDataBo.setProcess(MathUtils.divide(completeCount, lessonCount));
			knowledgeDataBo.setCompleteCount(completeCount);
			knowledgeDataBo.setLessonCount(lessonCount);
		}
		return knowledgeDataBo;

	}

	/**
	 * 根据岗位编号查询知识点编号
	 * @param roleId
	 * @return
	 */
	private List<String> getKnowledgeIdsByRole(String roleId) {
		Role role = roleMapper.selectById(roleId);
		if (null == role) {
			return Lists.newArrayList();
		}
		List<String> knowledgeIds = JsonUtil.toJsonObjectArray(role.getKnowledgeIds(), String.class);
		return knowledgeIds;
	}

	/**
	 * 根据岗位编号查询技能点
	 * @param roleId
	 * @return
	 */
	private List<String> getSkillIdsByRole(String roleId) {
		Role role = roleMapper.selectById(roleId);
		if (null == role) {
			return Lists.newArrayList();
		}
		List<String> skillIds = JsonUtil.toJsonObjectArray(role.getSkillIds(), String.class);
		return skillIds;
	}

	/**
	 * 根据知识点编号查询全部的课程编号
	 * @param knowledgeId
	 * @return
	 */
	private List<String> getCourseIdsByKnowledge(String knowledgeId) {
		Knowledge knowledge = knowledgeMapper.selectById(knowledgeId);
		List<String> courseIds = JsonUtil.toJsonObjectArray(knowledge.getCourses(), String.class);
		return courseIds;
	}

	/**
	 * 根据技能编号查询全部的课程编号
	 * @param skillId
	 * @return
	 */
	private List<String> getCourseIdsBySkill(String skillId) {
		Skill skill = skillMapper.selectById(skillId);
		List<String> courseIds = JsonUtil.toJsonObjectArray(skill.getCourses(), String.class);
		return courseIds;
	}

	/**
	 * 向上取整，防止计算过程中精度丢失导致永远到不了100%
	 * @param num
	 * @return
	 */
	private Integer floor(Integer num) {
		int ret = num / 100;
		int mod = num % 100;

		if (mod != 0) {
			ret++;
		}

		if (ret > MAX_PERCENT) {
			ret = 100;
		}

		if (ret < 0) {
			ret = 0;
		}

		return ret;
	}
}
