package com.cyberpeace.rtks.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.service.context.mission.CompletionStatus;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.service.CourseStatisticsService;
import com.cyberpeace.oj.course.service.LessonRecordService;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.constant.RtksBusinessErrorCode;
import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.entity.Level;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.entity.SkillAndKnowledgeBase;
import com.cyberpeace.rtks.dao.mapper.*;
import com.cyberpeace.rtks.service.RtksStatisticService;
import com.cyberpeace.rtks.service.SkillService;
import com.cyberpeace.rtks.service.TreeService;
import com.cyberpeace.rtks.service.bo.*;
import com.cyberpeace.rtks.service.bo.skill.*;
import com.cyberpeace.rtks.util.WeightedCalculation;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RtksStatisticServiceImpl implements RtksStatisticService {

	private static final Integer MAX_PERCENT = 100;

	@Autowired
	private TreeService treeService;

	@Autowired
	private CourseStatisticsService courseStatisticsService;

	@Autowired
	private KnowledgeMapper knowledgeMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private CrTaskMapper crTaskMapper;

	@Autowired
	private TrainingEvaluationMapper trainingEvaluationMapper;

	@Autowired
	private SkillService skillService;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private LessonRecordService lessonRecordService;

	@Autowired
	private RoleSkillRelationMapper roleSkillRelationMapper;

	@Autowired
	private SkillTrainingMapper skillTrainingMapper;

	@Override
	public Integer roleMatchPercent(String roleId, String userId) {
		Integer knowledgeScore = 0;
		Integer skillScore = 0;
		Role role = roleMapper.selectById(roleId);
		Integer knowledgeWeight = role.getKnowledgeWeight();
		// 1、计算知识点得分
		if (knowledgeWeight != 0) {
			knowledgeScore = calculateKnowledgeScore(roleId, userId);
		}

		// 2、计算技能得分
		if (knowledgeWeight != MAX_PERCENT.intValue()) {
			skillScore = calculateSkillScore(roleId, userId);
		}

		// 3、技能得分和知识点得分加权平均
		Integer result = knowledgeScore * knowledgeWeight + skillScore * (100 - knowledgeWeight);

		return floor(result);
	}

	@Override
	@Cacheable(cacheNames = "role", key = "#userId")
	public List<RtksRadarBo> calculateRadar(String roleId, String userId) {
		List<RtksRadarBo> ret = Lists.newArrayList();

		// 1、获取当前岗位对应的框架数据
		Level nearestLevel = treeService.getNearestLevelByRole(roleId);
		String frameId = nearestLevel.getFrame();

		// 2、获取当前岗位对应的第一树形结构
		List<Level> firstLevel = treeService.getFirstLevel(frameId);

		// 3、计算当前树形节点的评分
		for (Level level : firstLevel) {
			Integer matchPercent = calculateRadarByLevel(level.getResourceId(), userId);

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
		List<Knowledge> knowledges = getKnowledges(role);
		if (CollectionUtils.isEmpty(knowledges)) {
			return Lists.newArrayList();
		}
		// 计算结果
		return calculatePercent(knowledges, userId);
	}

	/**
	 * 根据技能点或者知识点编号计算对应的进度
	 * @param userId
	 * @param <T>
	 * @return
	 */
	private <T extends SkillAndKnowledgeBase> List<KnowledgeStudyPercentBo>
			calculatePercent(List<T> knowledges, String userId) {
		return knowledges.stream().map(knowledge -> {
			ValueDataBo valueDataBo = calculateKnowledgeScore(knowledge, userId,
					null != knowledge.getWeight());
			KnowledgeStudyPercentBo percentBo = new KnowledgeStudyPercentBo(
					valueDataBo.getResourceId(), valueDataBo.getContent(),
					MathUtils.ceil(valueDataBo.getProcess()));
			return percentBo;
		}).collect(Collectors.toList());
	}

	@Override
	public RoleStudyBo roleKnowledgeStudyRecord(String roleId, String userId) {
		// 1、获取用户角色关联的知识点
		List<Knowledge> knowledgeIds = getKnowledges(roleId);

		// 2、返回计算结果
		return calculateStudy(knowledgeIds, userId);
	}

	/**
	 * 根据技能点或者知识点计算学习的课程数量
	 * @param skillOrKnowledgeIds
	 * @param userId
	 * @param <T>
	 * @return
	 */
	private <T extends SkillAndKnowledgeBase> RoleStudyBo
			calculateStudy(List<T> skillOrKnowledgeIds, String userId) {

		// 1、获取课程学习进度
		List<String> courseIds = skillOrKnowledgeIds.stream()
				.map(this::getCourseIds).flatMap(Collection::stream)
				.distinct().collect(Collectors.toList());
		List<CourseStudyProcessBo> processBoList = courseStatisticsService.statisticByIds(courseIds,
				userId);

		// 2、获取技能点对应的课程
		return getStudyBo(processBoList);
	}

	@Override
	public StudyRecordBo calculateStudyRecord(String userId, String roleId, Date startTime,
			Date endTime) {
		List<LessonRecord> records = lessonRecordService.selectByTime(userId, startTime, endTime);

		int daysBetween = TimeUtil.getDaysBetween(TimeUtil.getStartOfDay(startTime),
				TimeUtil.getStartOfDay(endTime));
		Map<String, List<LessonRecord>> recordMap = records.stream().collect(Collectors
				.groupingBy(lessonRecord -> TimeUtil.format8(lessonRecord.getUpdateTime())));

		List<Knowledge> knowledgeIds = getKnowledges(roleId);
		if (CollectionUtils.isEmpty(knowledgeIds)) {
			return null;
		}

		List<String> courseIds = getCourseIdsByKnowledge(knowledgeIds);
		if (CollectionUtils.isEmpty(courseIds)) {
			return null;
		}
		List<CourseLesson> lessons = courseLessonMapper.listByCourses(courseIds, true);

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
						.map(LessonRecord::getLessonId).distinct().collect(Collectors.toList());
				List<String> recordCourseIds = lessonRecordService
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

	@Override
	public List<MissionPathBo> getTrainingPath(String userId, String roleId) {
		Role role = roleMapper.selectById(roleId);
		if (role == null) {
			return Lists.newArrayList();
		}
		// get missions and related business template ids
		List<MissionContext> missionContextList = roleSkillRelationMapper
				.getMissionContextsByRoleId(roleId);
		if (missionContextList.isEmpty()) {
			return Lists.newArrayList();
		}
		// deduplicate, keep order
		LinkedHashMap<String, MissionContext> missionMap = Maps.newLinkedHashMap();
		Set<String> businessTemplateIds = Sets.newHashSet();
		missionContextList.forEach((missionContext -> {
			missionMap.put(missionContext.getMissionTemplateId(), missionContext);
			// collect businessTemplate ids
			businessTemplateIds.add(missionContext.getBusinessTemplateId());
		}));
		// collect mission-skill relations, one mission may be related to several skills
		Map<String, List<MissionContext>> missionSkillRel = StreamApiUtils
				.groupBy(missionContextList, MissionContext::getMissionTemplateId);
		// collect mission completion status; true or false
		Set<String> missionTemplateIds = missionMap.keySet();
		List<CompletionStatus> completionStatusList = crTaskMapper
				.getCompletionStatusByUserIdAndMissionTemplateIds(userId, missionTemplateIds);
		Map<String, Boolean> completionStatusMap = Maps.newHashMap();
		for (CompletionStatus mission : completionStatusList) {
			if (mission.getStatus() > 0) {
				completionStatusMap.put(mission.getResourceId(), true);
			}
		}
		// get single training
		List<TrainingRaw> singles = skillTrainingMapper
				.getSingleByBusinessTemplateIdsAndUserId(userId, businessTemplateIds);
		// get available team training
		List<TrainingRaw> teams = skillTrainingMapper.getTeamByBusinessTemplateIdsAndUserId(userId,
				businessTemplateIds);
		// get available rb training
		List<TrainingRaw> rbs = skillTrainingMapper.getRbByBusinessTemplateIdsAndUserId(userId,
				businessTemplateIds);
		// unify status
		handleRbStatus(rbs);
		// collect available trainings
		Map<String, List<TrainingRaw>> trainingMap = Maps.newHashMap();
		mapTrainings(trainingMap, singles, BusinessType.TRAINING_SINGLE.getCode());
		mapTrainings(trainingMap, teams, BusinessType.TRAINING_TEAM.getCode());
		mapTrainings(trainingMap, rbs, BusinessType.RED_VS_BLUE.getCode());
		// get saved mission order
		List<String> order = JsonUtil.toJsonObjectArray(role.getSkillMissions(), String.class);
		// generate new order by merge with saved order
		List<MissionPathBo> newOrder = Lists.newArrayList();
		for (String missionTemplateId : order) {
			// mission does not exist or skill-mission relation deleted
			if (!missionMap.containsKey(missionTemplateId)) {
				continue;
			}
			MissionContext missionContext = missionMap.get(missionTemplateId);
			addMissionBoByOrder(trainingMap, missionSkillRel, missionContext, newOrder,
					completionStatusMap);
			missionMap.remove(missionTemplateId);
		}
		// missions not in saved mission order
		for (Map.Entry<String, MissionContext> entry : missionMap.entrySet()) {
			MissionContext missionContext = entry.getValue();
			// training template has no active training
			addMissionBoByOrder(trainingMap, missionSkillRel, missionContext, newOrder,
					completionStatusMap);
		}
		return newOrder;
	}

	@Override
	public SkillTrainingRecordBo calculateTrainingRecord(String userId, String roleId,
			Date startTime, Date endTime) {
		SkillTrainingRecordBo result = new SkillTrainingRecordBo();
		// get all related missions
		List<MissionContext> missionContextList = roleSkillRelationMapper
				.getMissionContextsByRoleId(roleId);
		if (missionContextList.isEmpty()) {
			return result;
		}
		// get mission-skill rel
		Map<String, List<MissionContext>> missionSkillRel = StreamApiUtils
				.groupBy(missionContextList, MissionContext::getMissionTemplateId);
		// get records
		Set<String> missionTemplateIds = missionContextList.stream()
				.map(MissionContext::getMissionTemplateId).collect(Collectors.toSet());
		List<MissionRecordBo> missionRecordBoList = trainingEvaluationMapper
				.getTrainingRecordByMissionTemplateIdsAndUserId(missionTemplateIds, userId);
		// fill skill_id
		for (MissionRecordBo missionRecordBo : missionRecordBoList) {
			missionSkillRel.get(missionRecordBo.getResourceId())
					.forEach(missionContext -> missionRecordBo.getSkillIds()
							.add(missionContext.getSkillId()));
		}
		result.setRecords(missionRecordBoList);
		// group by date
		int daysBetween = TimeUtil.getDaysBetween(TimeUtil.getStartOfDay(startTime),
				TimeUtil.getStartOfDay(endTime));
		Map<String, List<MissionRecordBo>> recordMap = missionRecordBoList.stream().collect(
				Collectors.groupingBy(lessonRecord -> TimeUtil.format8(lessonRecord.getTime())));

		for (int i = 0; i < daysBetween; i++) {
			Date date = TimeUtil.addDates(startTime, i);
			String format8 = TimeUtil.format8(date);
			result.getXDates().add(format8);
			if (recordMap.containsKey(format8)) {
				result.getYMissions().add(recordMap.get(format8).size());
			} else {
				result.getYMissions().add(0);
			}
		}
		return result;
	}

	/**
	 * 将 trainingRaw 训练数据 和  missionContext 任务数据组装成前端展示的内容 trainingBo
	 * @param trainingMap 
	 * @param missionSkillRel
	 * @param missionContext
	 * @param missionPathBoList
	 */
	private void addMissionBoByOrder(Map<String, List<TrainingRaw>> trainingMap,
			Map<String, List<MissionContext>> missionSkillRel, MissionContext missionContext,
			List<MissionPathBo> missionPathBoList, Map<String, Boolean> completionStatusMap) {
		MissionPathBo missionPathBo = new MissionPathBo();
		List<TrainingBo> trainings = Lists.newArrayList();
		// training template has no active training
		if (trainingMap.containsKey(missionContext.getBusinessTemplateId())) {
			// convert trainingRaw to trainingBo
			for (TrainingRaw trainingRaw : trainingMap
					.get(missionContext.getBusinessTemplateId())) {
				TrainingBo trainingBo = BeanUtils.copy(trainingRaw, TrainingBo.class);
				trainingBo.setMissionTitle(missionContext.getTitle());
				trainingBo.setMissionTemplateId(missionContext.getMissionTemplateId());
				trainingBo.setPreSkill(
						JsonUtil.toJsonObjectArray(trainingRaw.getPreSkill(), String.class));
				trainingBo.setTrainSkill(
						JsonUtil.toJsonObjectArray(trainingRaw.getTrainSkill(), String.class));
				trainingBo.setSkills(
						getSkillsByMission(missionSkillRel.get(trainingBo.getMissionTemplateId())));

				trainings.add(trainingBo);
			}
			missionPathBo.setAvailable(true);
		} else {
			missionPathBo.setAvailable(false);
		}

		missionPathBo.setMissionTemplateId(missionContext.getMissionTemplateId());
		missionPathBo.setCompletionStatus(
				completionStatusMap.getOrDefault(missionContext.getMissionTemplateId(), false));
		missionPathBo.setRelatedTrainings(trainings);
		missionPathBo.setTitle(missionContext.getTitle());

		missionPathBoList.add(missionPathBo);
	}

	/** 获取任务对应的skill
	 * @return
	 */
	private List<SkillResBase> getSkillsByMission(List<MissionContext> missionContexts) {
		List<SkillResBase> skillResBaseList = Lists.newArrayList();
		missionContexts.forEach(mission -> {
			SkillResBase skill = new SkillResBase();
			skill.setResourceId(mission.getSkillId());
			skill.setContent(mission.getSkillContent());
			skillResBaseList.add(skill);
		});
		return skillResBaseList;
	}

	/**
	 * 将查到的训练以businessTemplateId为键分类收集，一个businessTemplateId可能对应多个训练场景
	 * @param map 
	 * @param trainingRaws
	 */
	private void mapTrainings(Map<String, List<TrainingRaw>> map, List<TrainingRaw> trainingRaws,
			Integer type) {
		trainingRaws.forEach(training -> {
			String businessTemplateId = training.getBusinessTemplateId();
			if (!map.containsKey(businessTemplateId)) {
				map.put(businessTemplateId, Lists.newArrayList());
			}
			training.setTrainingType(type);
			map.get(businessTemplateId).add(training);
		});
	}

	/**
	 * 将红蓝训练状态统一成单兵、团队的样子
	 * UNOPENED(0, "未开始"), OPENING(1, "已开始"), ENDED(2, "已结束") PAUSE(3, "已暂停"), RECOVERY(4, "恢复");
	 * @param rbs
	 */
	private void handleRbStatus(List<TrainingRaw> rbs) {
		rbs.forEach(rb -> {
			Integer trainingStatus = rb.getTrainingStatus();
			rb.setTrainingStatus(convert(trainingStatus));
		});
	}

	private Integer convert(Integer rbStatus) {
		switch (rbStatus) {
			case 4:
				return 0;
			case 2:
				return 1;
			case 0:
				return 2;
			case 1:
				return 3;
			case 3:
				return 4;
			default:
				return rbStatus;
		}
	}

	/**
	 * 根据知识点
	 * @param knowledges
	 * @return
	 */
	private List<String> getCourseIdsByKnowledge(List<Knowledge> knowledges) {
		return knowledges.stream()
				.map(knowledge -> JsonUtil.toJsonObjectArray(knowledge.getCourses(),
						KnowledgeUnderCourseBo.class))
				.flatMap(Collection::stream).map(KnowledgeUnderCourseBo::getResourceId).distinct()
				.collect(Collectors.toList());
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

		List<CourseStudyProcessBo> bos = processBoList.stream().filter(
				bo -> bo.getLessonCount().intValue() != 0 && bo.getCompleteCount().intValue() > 0)
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
		List<IdWeight> skillWeights = roleSkillRelationMapper.listByRoleId(roleId);
		WeightedCalculation calculation = new WeightedCalculation();
		for (IdWeight skillWight : skillWeights) {
			// 2、获取单个技能得分
			Double skillScore = skillService.calculateScore(skillWight.getResourceId(), userId);
			// 3、统计
			calculation.feed(skillWight.getWeight(), skillScore);
		}
		// 4、返回向上取整的分数
		return MathUtils.ceil(calculation.solve());
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
		List<WeightBo> weights = JsonUtil.toJsonObjectArray(role.getKnowledgeIdWeights(),
				WeightBo.class);
		Map<String, Integer> weightMap = weights.stream()
				.collect(Collectors.toMap(WeightBo::getId, WeightBo::getWeight));
		knowledges.forEach(knowledge -> {
			if (null != weightMap) {
				Integer weight = weightMap.get(knowledge.getResourceId());
				knowledge.setWeight(null == weight ? 1 : weight);
			}
		});

		return calculateFinishPercent(knowledges, userId);
	}

	/**
	 * 计算当前层级下所有知识点的完成度
	 * @param levelId
	 * @return
	 */
	private Integer calculateRadarByLevel(String levelId, String userId) {
		// 1、获取当前层级下所有role数据
		List<Role> roles = treeService.roleUnderLevel(levelId);

		// 2, calculate match by role and user
		Integer count = 0;
		Integer sum = 0;
		for (Role role : roles) {
			sum += roleMatchPercent(role.getResourceId(), userId);
			count++;
		}
		// 3、取岗位匹配度平均值
		if (sum == 0) {
			return 0;
		}
		return MathUtils.roundDivide(sum, count);
	}

	/**
	 * 计算知识点涉及到的所有课时的完成度
	 * @param knowledges 知识点编号
	 * @param userId 用户编号
	 * @return
	 */
	private <T extends SkillAndKnowledgeBase> Integer calculateFinishPercent(List<T> knowledges,
			String userId) {

		// 1、统计所有知识点的进度和权重
		List<ValueDataBo> knowledgeScores = knowledges.stream()
				.map(base -> calculateKnowledgeScore(base, userId, null != base.getWeight()))
				.collect(Collectors.toList());

		double sumProcess = knowledgeScores.stream()
				.mapToDouble(value -> MathUtils.mul(value.getProcess(), value.getWeight())).sum();

		double sumWeight = knowledgeScores.stream().mapToDouble(ValueDataBo::getWeight).sum();
		return MathUtils.ceilDivide(sumProcess, sumWeight);

	}

	/**
	 * 计算知识点的完成度。
	 * 计算方法：
	 * 该知识点下所有课程的学习进度和课程权重的加权平均。
	 * @param knowledge 知识点
	 * @param userId 用户编号
	 * @param userWeight 是否使用权重 不是用权重则赋值为1
	 * @return
	 */
	private ValueDataBo calculateKnowledgeScore(SkillAndKnowledgeBase knowledge, String userId,
			boolean userWeight) {

		// 获取知识点课程列表
		ValueDataBo knowledgeDataBo = new ValueDataBo();
		knowledgeDataBo.setContent(knowledge.getContent());
		knowledgeDataBo.setResourceId(knowledge.getResourceId());
		knowledgeDataBo.setWeight(userWeight ? Double.valueOf(knowledge.getWeight()) : 1);

		List<KnowledgeUnderCourseBo> courses = JsonUtil.toJsonObjectArray(knowledge.getCourses(),
				KnowledgeUnderCourseBo.class);

		if (CollectionUtils.isEmpty(courses)) {
			knowledgeDataBo.setProcess((double) 0);
			return knowledgeDataBo;
		}

		List<String> courseIds = courses.stream().map(KnowledgeUnderCourseBo::getResourceId)
				.collect(Collectors.toList());
		Map<String, KnowledgeUnderCourseBo> underCourseBoMap = courses.stream()
				.collect(Collectors.toMap(KnowledgeUnderCourseBo::getResourceId, bo -> bo));

		// 计算课程学习进度
		List<CourseStudyProcessBo> studyProcessBos = courseStatisticsService
				.statisticByIds(courseIds, userId);

		// 使用权重 sum(进度 * 权重)/sum(权重) 不使用权重 则权重为1进行计算
		List<ValueDataBo> courseProcess = studyProcessBos.stream().map(underCourseBo -> {
			ValueDataBo dataBo = new ValueDataBo();
			dataBo.setProcess(underCourseBo.getProcess());
			Integer existWeight = underCourseBoMap.get(underCourseBo.getCourseId()).getWeight();
			// 可能影响排错，可防止空指针报错
			int weight = null == existWeight ? 1 : existWeight;
			dataBo.setWeight(userWeight ? (double) weight : 1);
			return dataBo;
		}).collect(Collectors.toList());

		if (CollectionUtils.isEmpty(courseProcess)) {
			knowledgeDataBo.setProcess((double) 0);
		} else {
			double courseProcessSum = courseProcess.stream()
					.mapToDouble(value -> MathUtils.mul(value.getProcess(), value.getWeight()))
					.sum();

			double weightSum = courseProcess.stream().mapToDouble(ValueDataBo::getWeight).sum();
			knowledgeDataBo.setProcess(MathUtils.divide(courseProcessSum, weightSum));
		}

		return knowledgeDataBo;

	}

	/**
	 * 获取所有知识点 并设置权重
	 * @param roleId
	 * @return
	 */
	private List<Knowledge> getKnowledges(String roleId) {
		Role role = roleMapper.selectById(roleId);
		if (null == role) {
			throw new BusinessException(RtksBusinessErrorCode.ROLE_NOT_EXIST);
		}

		List<String> resourceIds = JsonUtil.toJsonObjectArray(role.getKnowledgeIds(), String.class);

		if (CollectionUtils.isNotEmpty(resourceIds)) {
			List<WeightBo> weights = JsonUtil.toJsonObjectArray(role.getKnowledgeIdWeights(),
					WeightBo.class);

			Map<String, Integer> weightMap = weights.stream()
					.collect(Collectors.toMap(WeightBo::getId, WeightBo::getWeight));
			List<Knowledge> knowledges = knowledgeMapper.selectBatchIds(resourceIds);

			knowledges.forEach(knowledge -> {
				Integer weight = weightMap.get(knowledge.getResourceId());
				knowledge.setWeight(null != weight ? weight : 1);
			});
			return knowledges;
		}
		return Lists.newArrayList();

	}

	/**
	 * 根据知识点或者技能编号查询全部的课程编号
	 * @param skillOrKnowledge
	 * @return
	 */
	private List<String> getCourseIds(SkillAndKnowledgeBase skillOrKnowledge) {

		if (null != skillOrKnowledge) {
			List<KnowledgeUnderCourseBo> courseIds = JsonUtil
					.toJsonObjectArray(skillOrKnowledge.getCourses(), KnowledgeUnderCourseBo.class);
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
