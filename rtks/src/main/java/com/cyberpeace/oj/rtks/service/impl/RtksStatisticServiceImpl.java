package com.cyberpeace.oj.rtks.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.oj.course.service.CourseStatisticsService;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.oj.rtks.dao.entity.Knowledge;
import com.cyberpeace.oj.rtks.dao.entity.Level;
import com.cyberpeace.oj.rtks.dao.entity.Role;
import com.cyberpeace.oj.rtks.dao.mapper.KnowledgeMapper;
import com.cyberpeace.oj.rtks.service.KnowledgeService;
import com.cyberpeace.oj.rtks.service.RtksStatisticService;
import com.cyberpeace.oj.rtks.service.TreeService;
import com.cyberpeace.oj.rtks.service.bo.*;
import com.google.common.collect.Lists;

@Service
public class RtksStatisticServiceImpl implements RtksStatisticService {

	@Autowired
	private TreeService treeService;

	@Autowired
	private CourseStatisticsService courseStatisticsService;

	@Autowired
	private KnowledgeService knowledgeService;

	@Autowired
	private KnowledgeMapper knowledgeMapper;

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
		// 1、获取当前角色的所有技能点及权重

		// 2、计算每个技能点的得分，忽略权重
		// calculateKnowledgeScore(String knowledgeId, false)
		return null;
	}

	@Override
	public RoleStudyBo roleKnowledgeStudyRecord(String roleId, String userId) {
		// TODO
		// 1、获取用户角色关联的知识点
		// 2、获取知识点对应的课程
		// 3、针对课程统计
		return null;
	}

	@Override
	public RoleStudyBo roleSkillStudyRecord(String roleId, String userId) {
		// TODO
		// 1、获取用户角色关联的技能点
		// 2、获取技能点对应的课程
		// 3、针对课程统计
		return null;
	}

	/**
	 * 计算角色对应的技能得分
	 * @return
	 */
	private Integer calculateSkillScore(String roleId, String userId) {
		// TODO
		// 1、获取当前角色的所有技能点及权重

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

		// 2、计算每个知识点点的得分
		// calculateKnowledgeScore
		List<Double> knowledgeScores = Lists.newArrayList();

		// 3、2中的数据根据每个知识点的权重加权平均
		// TODO
		return null;
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
		String ret = calculateKnowledgeFinishPercent(knowledges, userId, true);

		return ret;
	}

	/**
	 * 计算知识点涉及到的所有课时的完成度
	 * @param knowledges 知识点编号
	 * @param userId 用户编号
	 * @return
	 */
	private String calculateKnowledgeFinishPercent(List<Knowledge> knowledges, String userId,
			boolean useWeight) {
		// 1、统计出知识点下的所有课程
		List<ValueDataBo> knowledgeScores = calculateKnowledgeScore(knowledges, userId, useWeight);
		if (useWeight) {

			double sumProcess = knowledgeScores.stream()
					.mapToDouble(value -> MathUtils.mul(value.getProcess(), value.getWeight()))
					.sum();

			double sumWeight = knowledgeScores.stream().mapToDouble(ValueDataBo::getWeight).sum();
			return MathUtils.divide(sumProcess, sumWeight, 0).toString();
		} else {
			// 2、统计所有课程的总课时A；
			double lessonCount = knowledgeScores.stream().mapToDouble(ValueDataBo::getLessonCount)
					.sum();
			// 3、统计所有课程的已学课时B;
			double completeCount = knowledgeScores.stream()
					.mapToDouble(ValueDataBo::getCompleteCount).sum();
			// 4、B/A，向上取整
			Double divide = MathUtils.divide(completeCount, lessonCount, 0);
			return divide.toString();
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
	 * @param knowledges 知识点
	 * @param userId 用户编号
	 * @param userWeight 是否使用权重
	 * @return
	 */
	private List<ValueDataBo> calculateKnowledgeScore(List<Knowledge> knowledges, String userId,
			boolean userWeight) {

		// 获取知识点课程列表
		List<ValueDataBo> dataBos = knowledges.stream().map(knowledge -> {

			ValueDataBo knowledgeDataBo = new ValueDataBo();
			knowledgeDataBo.setContent(knowledge.getContent());
			knowledgeDataBo.setResourceId(knowledge.getResourceId());

			List<KnowledgeUnderCourseBo> courses = JsonUtil
					.toJsonObjectArray(knowledge.getCourses(), KnowledgeUnderCourseBo.class);

			if (CollectionUtils.isEmpty(courses)) {
				knowledgeDataBo.setProcess(new Double(0));
				return knowledgeDataBo;
			}

			List<String> courseIds = courses.stream().map(KnowledgeUnderCourseBo::getResourceId)
					.collect(Collectors.toList());

			// 计算课程学习进度
			List<CourseStudyProcessBo> studyProcessBos = courseStatisticsService
					.statisticByIds(courseIds, userId);
			Map<String, CourseStudyProcessBo> boMap = studyProcessBos.stream()
					.collect(Collectors.toMap(CourseStudyProcessBo::getCourseId,
							courseStudyProcessBo -> courseStudyProcessBo));

			if (userWeight) {
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

				double weightSum = courseProcess.stream().mapToDouble(value -> value.getWeight())
						.sum();

				knowledgeDataBo.setResourceId(knowledge.getResourceId());
				knowledgeDataBo.setWeight(new Double(knowledge.getWeight()));
				knowledgeDataBo.setProcess(MathUtils.divide(courseProcessSum, weightSum));

			} else {
				double completeCount = studyProcessBos.stream()
						.mapToDouble(courseStudy -> new Double(courseStudy.getCompleteCount()))
						.sum();

				double lessonCount = studyProcessBos.stream()
						.mapToDouble(courseStudy -> new Double(courseStudy.getLessonCount())).sum();

				knowledgeDataBo.setProcess(MathUtils.divide(completeCount, lessonCount));
				knowledgeDataBo.setCompleteCount(completeCount);
				knowledgeDataBo.setLessonCount(lessonCount);
			}
			return knowledgeDataBo;
		}).collect(Collectors.toList());

		return dataBos;
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
