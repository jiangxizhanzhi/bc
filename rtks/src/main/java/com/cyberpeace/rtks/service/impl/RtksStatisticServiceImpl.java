package com.cyberpeace.rtks.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.dao.entity.MissionSkillEvaluation;
import com.cyberpeace.cr.crbase.dao.mapper.MissionSkillEvaluationMapper;
import com.cyberpeace.integratation.model.skill.OutSkillScore;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.CourseStatisticsService;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.dao.entity.*;
import com.cyberpeace.rtks.dao.mapper.KnowledgeMapper;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.dao.mapper.SkillCategoryMapper;
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

	@Autowired
	private SkillCategoryMapper skillCategoryMapper;

	@Autowired
	private CourseLessonService courseLessonService;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private MissionSkillEvaluationMapper missionSkillEvaluationMapper;

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
	public List<RtksRadarBo> calculateLgdRadar(String roleId, String userId) {
		List<RtksRadarBo> ret = Lists.newArrayList();

		// 1、获取当前岗位对应的框架数据
		Level nearestLevel = treeService.getNearestLevelByRole(roleId);
		String frameId = nearestLevel.getFrame();

		// 2、获取当前岗位对应的第一树形结构
		List<Level> firstLevel = treeService.getFirstLevel(frameId);

		// 3、计算当前树形节点的评分
		for (Level level : firstLevel) {
			String matchPercent = calculateLgdRadarByLevel(level.getResourceId(), userId);

			RtksRadarBo rtksRadarBo = new RtksRadarBo();

			rtksRadarBo.setFirstTreeId(level.getResourceId());
			rtksRadarBo.setTreeName(level.getName());
			rtksRadarBo.setMatchPercent(matchPercent);

			ret.add(rtksRadarBo);
		}

		return ret;
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

		// 获取知识点
		List<String> knowledgeIds = getSkillOrKnowledges(role, Knowledge.class);
		if (CollectionUtils.isEmpty(knowledgeIds)) {
			return Lists.newArrayList();
		}
		// 计算结果
		return calculatePercent(knowledgeIds, userId, knowledgeMapper);
	}

	/**
	 * 根据技能点或者知识点编号计算对应的进度
	 * @param skillOrKnowledgeIds
	 * @param baseMapper
	 * @param userId
	 * @param <T>
	 * @return
	 */
	private <T extends SkillAndKnowledgeBase> List<KnowledgeStudyPercentBo> calculatePercent(
			List<String> skillOrKnowledgeIds, String userId, BaseMapper<T> baseMapper) {
		List<T> knowledges = baseMapper.selectBatchIds(skillOrKnowledgeIds);
		List<KnowledgeStudyPercentBo> percentBoList = knowledges.stream().map(knowledge -> {
			ValueDataBo valueDataBo = calculateKnowledgeScore(knowledge, userId, true);
			KnowledgeStudyPercentBo percentBo = new KnowledgeStudyPercentBo(
					valueDataBo.getResourceId(), valueDataBo.getContent(),
					MathUtils.ceil(valueDataBo.getProcess()));
			return percentBo;
		}).collect(Collectors.toList());
		return percentBoList;
	}

	@Override
	public List<KnowledgeStudyPercentBo> calculateSkillPercent(String roleId, String userId) {
		// 获取知识点
		List<String> skillIds = getSkillOrKnowledges(roleId, Skill.class);
		if (CollectionUtils.isEmpty(skillIds)) {
			return Lists.newArrayList();
		}
		// 计算结果
		return calculatePercent(skillIds, userId, skillMapper);
	}

	@Override
	public RoleStudyBo roleKnowledgeStudyRecord(String roleId, String userId) {
		// 1、获取用户角色关联的知识点
		List<String> knowledgeIds = getSkillOrKnowledges(roleId, Knowledge.class);

		// 2、返回计算结果
		return calculateStudy(knowledgeIds, userId, knowledgeMapper);
	}

	@Override
	public RoleStudyBo roleSkillStudyRecord(String roleId, String userId) {
		// 1、获取用户角色关联的技能点
		List<String> skillIds = getSkillOrKnowledges(roleId, Skill.class);

		// 2、返回计算结果
		return calculateStudy(skillIds, userId, skillMapper);
	}

	/**
	 * 根据技能点或者知识点计算学习的课程数量
	 * @param skillOrKnowledgeIds
	 * @param userId
	 * @param baseMapper
	 * @param <T>
	 * @return
	 */
	private <T extends SkillAndKnowledgeBase> RoleStudyBo calculateStudy(
			List<String> skillOrKnowledgeIds, String userId, BaseMapper<T> baseMapper) {

		// 1、获取课程学习进度
		List<CourseStudyProcessBo> processBoList = skillOrKnowledgeIds.stream().map(skllId -> {
			List<String> courseIds = getCourseIds(skllId, baseMapper);
			List<CourseStudyProcessBo> processBos = courseStatisticsService
					.statisticByIds(courseIds, userId);
			return processBos;
		}).flatMap(Collection::stream).collect(Collectors.toList());

		// 2、获取技能点对应的课程
		RoleStudyBo studyBo = getStudyBo(processBoList);

		return studyBo;
	}

	@Override
	public List<CategorySkillBo> getRoleSkillScores(String userId, String roleId) {

		Role role = roleMapper.selectById(roleId);
		List<String> skillIds = JsonUtil.toJsonObjectArray(role.getSkillIds(), String.class);
		if (CollectionUtils.isEmpty(skillIds)) {
			skillIds = Lists.newArrayList();
		}

		List<Skill> skillList = skillMapper.selectBatchIds(skillIds);
		Map<String, List<Skill>> skillMap = skillList.stream()
				.collect(Collectors.groupingBy(Skill::getCategoryId));

		List<String> categoryIds = skillList.stream().map(Skill::getCategoryId).distinct()
				.collect(Collectors.toList());
		List<SkillCategory> categories = skillCategoryMapper.selectBatchIds(categoryIds);

		// 获取技能得分情况 转化为map
		List<OutSkillScore> scores = getSkillScore(userId, skillIds);
		Map<String, String> scoreMap = scores.stream()
				.collect(Collectors.toMap(OutSkillScore::getSkillId, OutSkillScore::getScore));

		// 组装数据
		List<CategorySkillBo> skillBos = categories.stream().map(skillCategory -> {
			CategorySkillBo skillBo = BeanUtils.copy(skillCategory, CategorySkillBo.class);
			List<Skill> skills = skillMap.get(skillCategory.getResourceId());

			// 组装技能得分
			List<SkillScoreBo> scoreBos = skills.stream().map(skill -> {
				SkillScoreBo scoreBo = new SkillScoreBo();
				scoreBo.setSkillId(skill.getResourceId());
				scoreBo.setSkillContent(skill.getContent());
				scoreBo.setScore(scoreMap.get(skill.getResourceId()));
				scoreBo.setWeight(skill.getWeight());
				return scoreBo;
			}).collect(Collectors.toList());

			skillBo.setSkills(scoreBos);
			return skillBo;
		}).collect(Collectors.toList());

		return skillBos;
	}

	@Override
	public List<RtksRadarBo> calculateSkillRadar(String userId, String roleId) {
		List<CategorySkillBo> skillScores = getRoleSkillScores(userId, roleId);

		List<RtksRadarBo> boList = skillScores.stream().map(categorySkillBo -> {
			RtksRadarBo radarBo = new RtksRadarBo();
			radarBo.setFirstTreeId(categorySkillBo.getResourceId());
			radarBo.setTreeName(categorySkillBo.getContent());

			double sum = categorySkillBo.getSkills().stream()
					.mapToDouble(skillBo -> Double.parseDouble(skillBo.getScore())
							* new Double(skillBo.getWeight()))
					.sum();
			double weightSum = categorySkillBo.getSkills().stream()
					.mapToDouble(SkillScoreBo::getWeight).sum();
			radarBo.setMatchPercent(String.valueOf(MathUtils.divide(sum, weightSum, 0).intValue()));

			return radarBo;
		}).collect(Collectors.toList());
		return boList;
	}

	@Override
	public StudyRecordBo calculateStudyRecord(String userId, String roleId, Date startTime,
			Date endTime) {
		List<LessonRecord> records = courseLessonService.selectByTime(userId, startTime, endTime);

		int daysBetween = TimeUtil.getDaysBetween(TimeUtil.getStartOfDay(startTime),
				TimeUtil.getStartOfDay(endTime));
		Map<String, List<LessonRecord>> recordMap = records.stream().collect(Collectors
				.groupingBy(lessonRecord -> TimeUtil.format8(lessonRecord.getStartTime())));

		List<String> knowledgeIds = getSkillOrKnowledges(roleId, Knowledge.class);
		if (CollectionUtils.isEmpty(knowledgeIds)) {
			return null;
		}

		List<Knowledge> knowledges = knowledgeMapper.selectBatchIds(knowledgeIds);
		List<String> courseIds = getCourseIdsByKnowledge(knowledges);
		if (CollectionUtils.isEmpty(courseIds)) {
			return null;
		}
		List<CourseLesson> lessons = courseLessonMapper.listByCourses(courseIds);

		Set<String> lessonSet = lessons.stream().map(CourseLesson::getResourceId)
				.collect(Collectors.toSet());

		List<String> yCourses = Lists.newArrayList();
		List<String> yLessons = Lists.newArrayList();
		List<String> xDates = Lists.newArrayList();

		for (int i = 0; i < daysBetween; i++) {
			Date date = TimeUtil.addDates(startTime, i);
			String format8 = TimeUtil.format8(date);
			xDates.add(format8);
			List<LessonRecord> lessonRecordList = recordMap.get(format8);

			if (CollectionUtils.isNotEmpty(lessonRecordList)) {

				List<String> recordLessonIds = lessonRecordList.stream()
						.map(LessonRecord::getLesson).distinct().collect(Collectors.toList());
				List<String> recordCourseIds = courseLessonService
						.recordBelongCourse(recordLessonIds, lessons);
				yCourses.add(String.valueOf(recordCourseIds.size()));

				recordLessonIds.retainAll(lessonSet);
				yLessons.add(String.valueOf(recordLessonIds.size()));

			} else {
				yCourses.add(String.valueOf(0));
				yLessons.add(String.valueOf(0));
			}
		}
		StudyRecordBo recordBo = new StudyRecordBo();
		recordBo.setXDates(xDates);
		recordBo.setYCourses(yCourses);
		recordBo.setYLessons(yLessons);
		return recordBo;
	}

	/**
	 * 根据知识点
	 * @param knowledges
	 * @return
	 */
	private List<String> getCourseIdsByKnowledge(List<Knowledge> knowledges) {
		List<String> courseIds = knowledges.stream()
				.map(knowledge -> JsonUtil.toJsonObjectArray(knowledge.getCourses(),
						KnowledgeUnderCourseBo.class))
				.flatMap(Collection::stream).map(KnowledgeUnderCourseBo::getResourceId).distinct()
				.collect(Collectors.toList());
		return courseIds;
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

		List<CourseStudyProcessBo> bos = processBoList.stream()
				.filter(bo -> bo.getLessonCount().intValue() == bo.getCompleteCount())
				.collect(Collectors.toList());
		studyBo.setFinishedCourses(bos.size());

		int completeTheorySum = processBoList.stream()
				.mapToInt(CourseStudyProcessBo::getCompleteTheory).sum();
		studyBo.setFinishedTheoryLessons(completeTheorySum);

		int completeExerciseSum = processBoList.stream()
				.mapToInt(CourseStudyProcessBo::getCompleteExercise).sum();
		studyBo.setFinishedPracticeLessons(completeExerciseSum);

		int unTheorySum = processBoList.stream()
				.mapToInt(bo -> bo.getTheoryCount() - bo.getCompleteTheory()).sum();
		studyBo.setUnFinishedTheoryLessons(unTheorySum);

		int unExerciseSum = processBoList.stream()
				.mapToInt(bo -> bo.getExerciseCount() - bo.getCompleteExercise()).sum();
		studyBo.setUnFinishedPracticeLessons(unExerciseSum);
		return studyBo;
	}

	/**
	 * 计算角色对应的技能得分
	 * @return
	 */
	private Integer calculateSkillScore(String roleId, String userId) {
		// 1、获取当前角色的所有技能点及权重
		List<String> skillIds = getSkillOrKnowledges(roleId, Skill.class);
		List<Skill> skills = skillMapper.selectBatchIds(skillIds);
		// 2、计算每个技能点的得分
		// 连接靶场数据库，没有数据点技能点算0分
		List<OutSkillScore> skillScore = getSkillScore(userId, skillIds);
		Map<String, String> scoreMap = skillScore.stream()
				.collect(Collectors.toMap(OutSkillScore::getSkillId, OutSkillScore::getScore));

		double sum = skills.stream().mapToDouble(skill -> new Double(skill.getWeight())
				* Double.parseDouble(scoreMap.get(skill.getResourceId()))).sum();
		int weightSum = skills.stream().mapToInt(Skill::getWeight).sum();
		Double result = MathUtils.divide(sum, Double.parseDouble(String.valueOf(weightSum)), 0);

		// 3、2中的数据根据每个技能点的权重加权平均
		return result.intValue();
	}

	/**
	 * 查询技能技能点得分
	 * @param userId
	 * @param skillIds
	 * @return
	 */
	private List<OutSkillScore> getSkillScore(String userId, List<String> skillIds) {
		// 查询技能点得分 为空则为零分
		List<MissionSkillEvaluation> skillScores = skillIds.stream().map(skillId -> {
			MissionSkillEvaluation skillEvaluation = missionSkillEvaluationMapper
					.selectLastOne(userId, skillId);

			// 设置零分返回
			if (null == skillEvaluation) {
				skillEvaluation = new MissionSkillEvaluation();
				skillEvaluation.setSkill(skillId);
				skillEvaluation.setScore(0);
			}
			return skillEvaluation;
		}).collect(Collectors.toList());

		// 数据类型转换
		List<OutSkillScore> scores = skillScores.stream().map(missionSkillEvaluation -> {
			OutSkillScore skillScore = new OutSkillScore();
			skillScore.setScore(
					DataTransferUtils.nullToZero(missionSkillEvaluation.getScore()).toString());
			skillScore.setSkillId(missionSkillEvaluation.getSkill());
			return skillScore;
		}).collect(Collectors.toList());

		return scores;
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

		if (CollectionUtils.isEmpty(knowledgeIds)) {
			return 0;
		}
		List<Knowledge> knowledges = knowledgeMapper.selectBatchIds(knowledgeIds);

		// 根据权重计算数据根据每个知识点的权重加权平均
		Integer percent = calculateFinishPercent(knowledges, userId, true);

		return percent;
	}

	/**
	 * 计算当前层级下所有技能点的完成度
	 * @param levelId
	 * @return
	 */
	private String calculateLgdRadarByLevel(String levelId, String userId) {
		// 1、获取当前层级下所有role数据
		List<Role> roles = treeService.roleUnderLevel(levelId);

		// 2、获取所有知识点
		List<Skill> skills = mergeSkillByRoles(roles);

		// 3、统计所有知识点的课时完成度
		Integer ret = calculateFinishPercent(skills, userId, true);

		return ret.toString();
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
		Integer ret = calculateFinishPercent(knowledges, userId, true);

		return ret.toString();
	}

	/**
	 * 计算知识点涉及到的所有课时的完成度
	 * @param knowledges 知识点编号
	 * @param userId 用户编号
	 * @return
	 */
	private <T extends SkillAndKnowledgeBase> Integer calculateFinishPercent(List<T> knowledges,
			String userId, boolean useWeight) {

		// 1、统计所有知识点的进度和权重
		List<ValueDataBo> knowledgeScores = knowledges.stream()
				.map(base -> calculateKnowledgeScore(base, userId, useWeight))
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
	 * 获取角色列表的所有技能点集合。
	 *
	 * 注意去重！
	 * @param roles
	 * @return
	 */
	private List<Skill> mergeSkillByRoles(List<Role> roles) {
		List<String> skillIds = roles.stream()
				.map(role -> JsonUtil.toJsonObjectArray(role.getSkillIds(), String.class))
				.flatMap(Collection::stream).collect(Collectors.toList());

		if (CollectionUtils.isEmpty(skillIds)) {
			return Lists.newArrayList();
		}

		List<Skill> skills = skillMapper.selectBatchIds(skillIds);
		return skills;
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
	private ValueDataBo calculateKnowledgeScore(SkillAndKnowledgeBase knowledge, String userId,
			boolean userWeight) {

		// 获取知识点课程列表
		ValueDataBo knowledgeDataBo = new ValueDataBo();
		knowledgeDataBo.setContent(knowledge.getContent());
		knowledgeDataBo.setResourceId(knowledge.getResourceId());
		knowledgeDataBo.setWeight(new Double(knowledge.getWeight()));

		List<KnowledgeUnderCourseBo> courses = JsonUtil.toJsonObjectArray(knowledge.getCourses(),
				KnowledgeUnderCourseBo.class);

		if (CollectionUtils.isEmpty(courses)) {
			knowledgeDataBo.setProcess(new Double(MAX_PERCENT));
			return knowledgeDataBo;
		}

		List<String> courseIds = courses.stream().map(KnowledgeUnderCourseBo::getResourceId)
				.collect(Collectors.toList());
		Map<String, KnowledgeUnderCourseBo> underCourseBoMap = courses.stream()
				.collect(Collectors.toMap(KnowledgeUnderCourseBo::getResourceId, bo -> bo));

		// 计算课程学习进度
		List<CourseStudyProcessBo> studyProcessBos = courseStatisticsService
				.statisticByIds(courseIds, userId);

		if (userWeight) {
			// 使用权重 sum(进度 * 权重)/sum(权重)
			List<ValueDataBo> courseProcess = studyProcessBos.stream().map(underCourseBo -> {
				ValueDataBo dataBo = new ValueDataBo();
				dataBo.setProcess(new Double(underCourseBo.getProcess()));
				dataBo.setWeight(
						new Double(underCourseBoMap.get(underCourseBo.getCourseId()).getWeight()));
				return dataBo;
			}).collect(Collectors.toList());

			if (CollectionUtils.isEmpty(courseProcess)) {
				knowledgeDataBo.setProcess(new Double(MAX_PERCENT));
			} else {
				double courseProcessSum = courseProcess.stream()
						.mapToDouble(value -> MathUtils.mul(value.getProcess(), value.getWeight()))
						.sum();

				double weightSum = courseProcess.stream().mapToDouble(value -> value.getWeight())
						.sum();
				knowledgeDataBo.setProcess(MathUtils.divide(courseProcessSum, weightSum));
			}

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
	 * 根据岗位编号查询技能点或者知识点
	 * @param roleId
	 * @return
	 */
	private List<String> getSkillOrKnowledges(String roleId, Class cls) {
		Role role = roleMapper.selectById(roleId);
		if (null == role) {
			return Lists.newArrayList();
		}
		List<String> resourceIds;
		if (cls.getName().equals(Skill.class.getName())) {
			resourceIds = JsonUtil.toJsonObjectArray(role.getSkillIds(), String.class);
		} else {
			resourceIds = JsonUtil.toJsonObjectArray(role.getKnowledgeIds(), String.class);
		}
		return resourceIds;
	}

	/**
	 * 根据知识点或者技能编号查询全部的课程编号
	 * @param skillOrKnowledge
	 * @return
	 */
	private <T extends SkillAndKnowledgeBase> List<String> getCourseIds(String skillOrKnowledge,
			BaseMapper<T> baseMapper) {
		T skill = baseMapper.selectById(skillOrKnowledge);
		if (null != skill) {
			List<KnowledgeUnderCourseBo> courseIds = JsonUtil.toJsonObjectArray(skill.getCourses(),
					KnowledgeUnderCourseBo.class);
			return courseIds.stream().map(KnowledgeUnderCourseBo::getResourceId)
					.collect(Collectors.toList());
		}

		return Lists.newArrayList();

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
