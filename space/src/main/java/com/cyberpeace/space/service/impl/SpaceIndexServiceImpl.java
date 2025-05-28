package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.constant.CallType;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmService;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.dao.mapper.ToolMapper;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.space.constant.SpaceKTypeEnum;
import com.cyberpeace.space.dao.entity.*;
import com.cyberpeace.space.dao.mapper.SpaceActionFrameUseMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticAttackMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefenseMapper;
import com.cyberpeace.space.dao.mapper.SpaceWarningDictionaryMapper;
import com.cyberpeace.space.service.*;
import com.cyberpeace.space.service.context.index.*;
import com.cyberpeace.space.service.context.rule.BaseDataRuleStatistics;
import com.cyberpeace.space.service.context.rule.RaceStatistics;
import com.cyberpeace.space.service.context.rule.ThreatDetectStatistics;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 空间编号Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceIndexService
 * @since 1.0.0
 */
@Service
@Slf4j
public class SpaceIndexServiceImpl implements SpaceIndexService {

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private PracticeTheoryMapper practiceTheoryMapper;

	@Autowired
	private PracticeOperationMapper practiceOperationMapper;

	@Autowired
	private SpaceStrategyService strategyService;

	@Autowired
	private SpaceTacticAttackService tacticAttackService;

	@Autowired
	private SpaceTacticDefenseService tacticDefenseService;

	@Autowired
	private SpaceTacticAttackMapper spaceTacticAttackMapper;

	@Autowired
	private SpaceTacticDefenseMapper spaceTacticDefenseMapper;

	@Autowired
	private SpaceRuleCommonService spaceRuleCommonService;

	@Autowired
	private SpaceWarningDictionaryMapper spaceWarningDictionaryMapper;

	@Autowired
	private ToolMapper toolMapper;

	@Autowired
	private SpaceActionFrameService spaceActionFrameService;

	@Autowired
	private EvalAlgorithmService evalAlgorithmService;

	@Autowired
	private SpaceCallCountService callCountService;

	@Autowired
	private SpaceActionFrameUseMapper spaceActionFrameUseMapper;

	@Autowired
	private SpaceAptFeatureService spaceAptFeatureService;

	@Autowired
	private SpaceLoadedFileThreatRuleService spaceLoadedFileThreatRuleService;

	@Autowired
	private SpaceEventIntegrationRuleService spaceEventIntegrationRuleService;

	@Autowired
	private SpaceTrafficDetectRuleService spaceTrafficDetectRuleService;

	@Autowired
	private SpaceAptThreatDetectRuleService spaceAptThreatDetectRuleService;

	@Autowired
	private SpaceScoreRankingRuleService spaceScoreRankingRuleService;

	@Autowired
	private SpaceRoundMechanismRuleService spaceRoundMechanismRuleService;

	@Autowired
	private SpaceViolationJudgeRuleService spaceViolationJudgeRuleService;

	/**
	 * 获取知识点存储 库统计学
	 *
	 * @return 编号知识点
	 * @throws
	 * @see IndexKnowledge
	 */
	@Override
	public IndexKnowledge getKnowledgeRepositoryStatistics() {
		IndexKnowledge res = new IndexKnowledge();
		// 理论知识库
		IndexTheory theory = this.getIndexTheory();
		res.setTheory(theory);
		// 策略库
		IndexStrategy strategy = strategyService.getStatistics();
		strategy.setUse(callCountService.getCountByName(CallType.STRATEGY));
//		this.getAddAndUpdateToday(strategy);
		res.setStrategy(strategy);
		// 战法
		IndexTactic tactic = new IndexTactic();
		tactic.setAttack(spaceTacticAttackMapper.selectCount(null));
		tactic.setDefense(spaceTacticDefenseMapper.selectCount(null));
		tactic.setUse(callCountService.getCountByName(CallType.TACTIC));
		tactic.calSum();
//		this.getAddedToday(tactic);
//		this.getUpdatedToday(tactic);
		res.setTactic(tactic);
		// 规则
		IndexRule rule = this.getIndexRule();
		res.setRule(rule);
		// 流量

		// 算法
		IndexAlgorithm algorithm = new IndexAlgorithm();
		algorithm.setBase(evalAlgorithmService.count());
		algorithm.calSum();
		algorithm.setUse(callCountService.getCountByName(CallType.ALGORITHM));
		res.setAlgorithm(algorithm);
		// 场景
		return res;
	}

	/**
	 * 获取编号统治
	 *
	 * @return 编号统治
	 * @throws
	 * @see IndexRule
	 */
	private IndexRule getIndexRule() {
		IndexRule rule = new IndexRule();
		BaseDataRuleStatistics baseDataRuleStatistics = spaceRuleCommonService
				.getBaseDataStatistics();
		rule.setBase(baseDataRuleStatistics.getApt() + baseDataRuleStatistics.getIoc()
				+ baseDataRuleStatistics.getFingerPrint() + baseDataRuleStatistics.getVuln());
//		rule.setWarning(spaceWarningDictionaryMapper.selectCount(null));

		ThreatDetectStatistics threatDetectStatistics = spaceRuleCommonService
				.getThreatDetectStatistics();
		rule.setThreat(
				threatDetectStatistics.getAptThreat() + threatDetectStatistics.getThreatImage()
						+ threatDetectStatistics.getTrafficDetect()
						+ threatDetectStatistics.getLoadedFileThreat()
						+ threatDetectStatistics.getTrafficExtraction()
						+ threatDetectStatistics.getEventIntegration()
						+ threatDetectStatistics.getSourceAnalyze());
		RaceStatistics raceStatistics = spaceRuleCommonService.getRaceStatistics();
		rule.setRace(raceStatistics.getScoreRanking() + raceStatistics.getRoundMechanism()
				+ raceStatistics.getViolationJudge());
		rule.calSum();
		rule.setUse(callCountService.getCountByName(CallType.RULE));
		
		return rule;
	}

	/**
	 * 获取编号理论
	 *
	 * @return 编号理论
	 * @throws
	 * @see IndexTheory
	 */
	private IndexTheory getIndexTheory() {
		IndexTheory theory = new IndexTheory();
		LambdaQueryWrapper<CourseLesson> wrapperTheory = new LambdaQueryWrapper<>();
		wrapperTheory.eq(CourseLesson::getType, CourseLesson.LessonType.THEORY.getCode());
		theory.setTheoryCourse(courseLessonMapper.selectCount(wrapperTheory));
		LambdaQueryWrapper<CourseLesson> wrapperExp = new LambdaQueryWrapper<>();
		wrapperExp.eq(CourseLesson::getType, CourseLesson.LessonType.EXPERIMENT.getCode());
		theory.setOnlineExp(courseLessonMapper.selectCount(wrapperExp));
		theory.setTheoryExam(practiceTheoryMapper.selectCount(null));
		theory.setExpExam(practiceOperationMapper.selectCount(null));
		theory.setUse(callCountService.getCountByName(CallType.THEORY));

		LambdaQueryWrapper<CourseLesson> videoWrapper = new LambdaQueryWrapper<>();
		videoWrapper.isNotNull(CourseLesson::getVideoUrl).ne(CourseLesson::getVideoUrl, "");
		List<CourseLesson> videoList = courseLessonMapper.selectList(videoWrapper);
		theory.setVideoCount(videoList.size());
		int videoDuration = 0;
		for (CourseLesson lesson : videoList) {
			if (null != lesson.getDuration())
				videoDuration += lesson.getDuration();
		}
		theory.setVideoDuration(MathUtils.roundDivide(videoDuration, 60));

		theory.calSum();
		return theory;
	}

	/**
	 * 获取工具统计学
	 *
	 * @return 编号工具
	 * @throws
	 * @see IndexTool
	 */
	@Override
	public IndexTool getToolStatistics() {
		IndexTool indexTool = new IndexTool();
		List<Tool> toolList = toolMapper.listTypeAndPlatform();
		indexTool.setAttack(toolList.stream()
				.filter(t -> Tool.Type.ATTACK.getCode().equals(t.getType())).count());
		indexTool.setProbe(toolList.stream()
				.filter(t -> Tool.Type.PROBE.getCode().equals(t.getType())).count());
		indexTool.setDefense(toolList.stream()
				.filter(t -> Tool.Type.DEFENSE.getCode().equals(t.getType())).count());
		indexTool.setControl(toolList.stream()
				.filter(t -> Tool.Type.CONTROL.getCode().equals(t.getType())).count());
		indexTool.setOther(toolList.stream()
				.filter(t -> Tool.Type.OTHER.getCode().equals(t.getType())).count());
		Map<String, Integer> map = Maps.newHashMap();
		for (Tool tool : toolList) {
			String[] platforms = StringUtils.split(tool.getPlatforms(), ",");
			for (String platform : platforms) {
				map.putIfAbsent(platform, 0);
				map.put(platform, map.get(platform) + 1);
			}
		}
		map.put("Linux", map.getOrDefault("linux", 0) + map.getOrDefault("Linux", 0));
		indexTool.setPlatform(map);
		return indexTool;
	}

	/**
	 * 获取框架编号
	 *
	 * @return 编号框架
	 * @throws
	 * @see IndexFrame
	 */
	@Override
	public IndexFrame getFrameIndex() {
		IndexFrame frame = new IndexFrame();

		IndexFrameCount count = new IndexFrameCount();
		count.setProcess(spaceActionFrameService.countByType(SpaceActionFrame.Type.PROCESS.getCode()));
		count.setTarget(spaceActionFrameService.countByType(SpaceActionFrame.Type.TARGET.getCode()));
		count.calSum();
		frame.setFrameCount(count);
//		frame.setTrending(this.defaultChart());
		frame.setTrending(this.getActionFrameUseChart());
		return frame;

	}

	/**
	 * 获取人工智能网址
	 *
	 * @return 字符串
	 * @throws
	 * @see String
	 */
	@Override
	public String getAiUrl() {
		String ip = SystemPropUtil.getProp("AI_HOST");
		if (StringUtils.isBlank(ip)) {
			ip = "10.4.2.3";
		}
		return ip;
	}

	/**
	 * 获取kchart
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	@Override
	public KnowledgeChart getKChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.KNOWLEDGE, null, 0);
		chart.getData().add(baseItem);
		KnowledgeChartCategory baseCategory = new KnowledgeChartCategory(SpaceKTypeEnum.KNOWLEDGE);
		chart.getCategories().add(baseCategory);
		// 理论知识库
		if (UserInfoContext.getUserContext().getPermissions()
				.contains(SpaceKTypeEnum.THEORY.getMenuId())) {
			KnowledgeChart theoryChart = this.getTheoryChart();
			chart.addChart(theoryChart);
		}
		// 策略库
		if (UserInfoContext.getUserContext().getPermissions()
				.contains(SpaceKTypeEnum.STRATEGY.getMenuId())) {
			KnowledgeChart strategyChart = strategyService.getStrategyChart();
			chart.addChart(strategyChart);
		}

		// 战法
		if (UserInfoContext.getUserContext().getPermissions()
				.contains(SpaceKTypeEnum.TACTIC.getMenuId())) {
			KnowledgeChart tacticChart = this.getTacticChart();
			chart.addChart(tacticChart);
		}

		// 规则
		if (UserInfoContext.getUserContext().getPermissions()
				.contains(SpaceKTypeEnum.RULE.getMenuId())) {
			KnowledgeChart ruleChart = this.getRuleChart();
			chart.addChart(ruleChart);
		}
		// 流量
		KnowledgeChart trafficChart = this.getTrafficChart();

		// 算法
		if (UserInfoContext.getUserContext().getPermissions()
				.contains(SpaceKTypeEnum.ALGORITHM.getMenuId())) {
			KnowledgeChart algorithmChart = this.getAlgorithmChart();
			chart.addChart(algorithmChart);
		}

		// 场景
		KnowledgeChart sceneChart = this.getSceneChart();

		return chart;
	}

	/**
	 * 获取场景图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	private KnowledgeChart getSceneChart() {
		KnowledgeChart chart = new KnowledgeChart();

		return chart;
	}

	/**
	 * 获取算法图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	private KnowledgeChart getAlgorithmChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.ALGORITHM,
				SpaceKTypeEnum.KNOWLEDGE.getId(), 1);
		chart.getData().add(baseItem);
		chart.getCategories().add(new KnowledgeChartCategory(SpaceKTypeEnum.ALGORITHM));

		if (UserInfoContext.getUserContext().getPermissions()
				.contains(SpaceKTypeEnum.ALGORITHM_EVAL.getMenuId())) {
			KnowledgeChart algorithmEvalChart = this.getAlgorithmEvalChart();
			chart.addChart(algorithmEvalChart);
		}
		return chart;
	}

	/**
	 * 获取算法测评图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	private KnowledgeChart getAlgorithmEvalChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.ALGORITHM_EVAL,
				SpaceKTypeEnum.ALGORITHM.getId(), 2);
		chart.getCategories().add(new KnowledgeChartCategory(SpaceKTypeEnum.ALGORITHM_EVAL));
		List<EvalAlgorithm> algorithmList = evalAlgorithmService.list();
		baseItem.setValue(algorithmList.size());
		chart.getData().add(baseItem);
		for (EvalAlgorithm algorithm : algorithmList) {
			KnowledgeChartItem item = new KnowledgeChartItem(3);
			item.setId(algorithm.getResourceId());
			item.setName(algorithm.getName());
			item.setType(SpaceKTypeEnum.ALGORITHM_EVAL.getType());
			item.setCategory(SpaceKTypeEnum.ALGORITHM_EVAL.getId());
			chart.getData().add(item);
			KnowledgeChartLink link = new KnowledgeChartLink(SpaceKTypeEnum.ALGORITHM_EVAL.getId(),
					algorithm.getResourceId());
			chart.getLinks().add(link);
		}
		return chart;
	}

	/**
	 * 获取流量图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	private KnowledgeChart getTrafficChart() {
		KnowledgeChart chart = new KnowledgeChart();


		return chart;
	}

	/**
	 * 获取统治图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	private KnowledgeChart getRuleChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.RULE,
				SpaceKTypeEnum.KNOWLEDGE.getId(), 1);
		chart.getData().add(baseItem);
		chart.getCategories().add(new KnowledgeChartCategory(SpaceKTypeEnum.RULE));

		if (UserInfoContext.getUserContext().getPermissions()
				.contains(SpaceKTypeEnum.RULE_BASE.getMenuId())) {
			KnowledgeChart baseRuleChart = this.getRuleBaseChart();
			chart.addChart(baseRuleChart);
		}
		if (UserInfoContext.getUserContext().getPermissions()
				.contains(SpaceKTypeEnum.RULE_THREAT_DEFENSE.getMenuId())) {
			KnowledgeChart ruleThreatChart = this.getRuleThreatChart();
			chart.addChart(ruleThreatChart);
		}
		if (UserInfoContext.getUserContext().getPermissions()
				.contains(SpaceKTypeEnum.RULE_RACE.getMenuId())) {
			KnowledgeChart ruleRaceChart = this.getRuleRaceChart();
			chart.addChart(ruleRaceChart);
		}

		return chart;
	}

	/**
	 * 获取统治比赛图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	private KnowledgeChart getRuleRaceChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.RULE_RACE,
				SpaceKTypeEnum.RULE.getId(), 2);
		chart.getData().add(baseItem);
		KnowledgeChartCategory baseCategory = new KnowledgeChartCategory(SpaceKTypeEnum.RULE_RACE);
		chart.getCategories().add(baseCategory);

		KnowledgeChart ruleScoreRankingChart = spaceScoreRankingRuleService
				.getRuleScoreRankingChart();
		chart.addChart(ruleScoreRankingChart);
		KnowledgeChart ruleRoundMechanismChart = spaceRoundMechanismRuleService
				.getRuleRoundMechanismChart();
		chart.addChart(ruleRoundMechanismChart);
		KnowledgeChart ruleViolationJudgeChart = spaceViolationJudgeRuleService
				.getRuleViolationJudgeChart();
		chart.addChart(ruleViolationJudgeChart);
		return chart;
	}

	/**
	 * 获取统治威胁图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	private KnowledgeChart getRuleThreatChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.RULE_THREAT_DEFENSE,
				SpaceKTypeEnum.RULE.getId(), 2);
		chart.getData().add(baseItem);
		KnowledgeChartCategory baseCategory = new KnowledgeChartCategory(
				SpaceKTypeEnum.RULE_THREAT_DEFENSE);
		chart.getCategories().add(baseCategory);

		KnowledgeChart ruleVirusChart = spaceLoadedFileThreatRuleService.geRuleVirusChart();
		chart.addChart(ruleVirusChart);
		KnowledgeChart ruleHostChart = spaceEventIntegrationRuleService.getRuleHostChart();
		chart.addChart(ruleHostChart);
		KnowledgeChart ruleNetworkChart = spaceTrafficDetectRuleService.getRuleNetworkChart();
		chart.addChart(ruleNetworkChart);
		KnowledgeChart ruleAptThreatChart = spaceAptThreatDetectRuleService.getRuleAptThreatChart();
		chart.addChart(ruleAptThreatChart);
		return chart;
	}

	/**
	 * 获取统治基础图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	private KnowledgeChart getRuleBaseChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.RULE_BASE,
				SpaceKTypeEnum.RULE.getId(), 2);
		chart.getData().add(baseItem);
		KnowledgeChartCategory baseCategory = new KnowledgeChartCategory(SpaceKTypeEnum.RULE_BASE);
		chart.getCategories().add(baseCategory);

		KnowledgeChart ruleVulnChart = this.getRuleVulnChart();
		chart.addChart(ruleVulnChart);
		KnowledgeChart ruleAptOrgChart = spaceAptFeatureService.getRuleAptOrgChart();
		chart.addChart(ruleAptOrgChart);

		return chart;
	}

	/**
	 * 获取统治漏洞图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	private KnowledgeChart getRuleVulnChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.RULE_VULN,
				SpaceKTypeEnum.RULE_BASE.getId(),3);
		chart.getData().add(baseItem);
		// chart.getCategories().add(new
		// KnowledgeChartCategory(SpaceKTypeEnum.RULE_VULN));
		return chart;
	}

	/**
	 * 获取战法图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	private KnowledgeChart getTacticChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.TACTIC,
				SpaceKTypeEnum.KNOWLEDGE.getId(), 1);
		chart.getData().add(baseItem);
		KnowledgeChartCategory baseCategory = new KnowledgeChartCategory(SpaceKTypeEnum.TACTIC);
		chart.getCategories().add(baseCategory);
		if (UserInfoContext.getUserContext().getPermissions()
				.contains(SpaceKTypeEnum.TACTIC_ATTACK.getMenuId())) {
			KnowledgeChart tacticAttackChart = tacticAttackService.getTacticAttackChart();
			chart.addChart(tacticAttackChart);
		}
		if (UserInfoContext.getUserContext().getPermissions()
				.contains(SpaceKTypeEnum.TACTIC_DEFENSE.getMenuId())) {
			KnowledgeChart tacticDefenseChart = tacticDefenseService.getTacticDefenseChart();
			chart.addChart(tacticDefenseChart);
		}
		return chart;
	}

	/**
	 * 获取理论图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	private KnowledgeChart getTheoryChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.THEORY,
				SpaceKTypeEnum.KNOWLEDGE.getId(), 1);
		chart.getData().add(baseItem);
		KnowledgeChartCategory baseCategory = new KnowledgeChartCategory(SpaceKTypeEnum.THEORY);
		chart.getCategories().add(baseCategory);

		if (UserInfoContext.getUserContext().getPermissions()
				.contains(SpaceKTypeEnum.COURSE.getMenuId())) {
			KnowledgeChart courseChart = this.getCourseChart();
			chart.addChart(courseChart);
		}
		if (UserInfoContext.getUserContext().getPermissions()
				.contains(SpaceKTypeEnum.PRACTICE_THEORY.getMenuId())) {
			KnowledgeChart practiceTheoryChart = this.getPracticeTheoryChart();
			chart.addChart(practiceTheoryChart);
		}
		if (UserInfoContext.getUserContext().getPermissions()
				.contains(SpaceKTypeEnum.PRACTICE_OPERATION.getMenuId())) {
			KnowledgeChart practiceOperationChart = this.getPracticeOperationChart();
			chart.addChart(practiceOperationChart);
		}

		return chart;
	}

	/**
	 * 获取练习实操图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	private KnowledgeChart getPracticeOperationChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.PRACTICE_OPERATION,
				SpaceKTypeEnum.THEORY.getId(), 2);
		int operationCount = practiceOperationMapper.selectCount(null);
		baseItem.setValue(operationCount);
		chart.getData().add(baseItem);
		return chart;
	}

	/**
	 * 获取练习理论图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	private KnowledgeChart getPracticeTheoryChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.PRACTICE_THEORY,
				SpaceKTypeEnum.THEORY.getId(), 2);
		int theoryExamCount = practiceTheoryMapper.selectCount(null);
		baseItem.setValue(theoryExamCount);
		chart.getData().add(baseItem);
		// KnowledgeChartCategory baseCategory = new
		// KnowledgeChartCategory(SpaceKTypeEnum.PRACTICE_THEORY);
		// chart.getCategories().add(baseCategory);
		return chart;
	}

	/**
	 * 获取课程图表
	 *
	 * @return 知识点图表
	 * @throws
	 * @see KnowledgeChart
	 */
	private KnowledgeChart getCourseChart() {
		KnowledgeChart chart = new KnowledgeChart();
		KnowledgeChartItem baseItem = new KnowledgeChartItem(SpaceKTypeEnum.COURSE,
				SpaceKTypeEnum.THEORY.getId(), 2);
		chart.getData().add(baseItem);
		KnowledgeChartCategory baseCategory = new KnowledgeChartCategory(SpaceKTypeEnum.COURSE);
		chart.getCategories().add(baseCategory);
		for (Course course : courseMapper.selectList(null)) {
			KnowledgeChartItem item = new KnowledgeChartItem(3);
			item.setId(course.getResourceId());
			item.setName(course.getName());
			item.setType(SpaceKTypeEnum.COURSE.getType());
			item.setCategory(baseItem.getId());
			item.setValue(course.getLessonCount());
			chart.getData().add(item);
			// KnowledgeChartCategory category = new
			// KnowledgeChartCategory(course.getName(), course.getResourceId());
			// chart.getCategories().add(category);
			KnowledgeChartLink link = new KnowledgeChartLink(baseItem.getId(),
					course.getResourceId());
			chart.getLinks().add(link);
		}
		return chart;
	}

	/**
	 * 获取行动框架使用图表
	 *
	 * @return 编号行动框架图表
	 * @throws
	 * @see IndexActionFrameChart
	 */
	private IndexActionFrameChart getActionFrameUseChart() {
		IndexActionFrameChart chart = new IndexActionFrameChart();
		Date date = TimeUtil.addDates(new Date(), -6);
		for (int i = 0; i < 7; i++) {
			chart.getXDates().add(TimeUtil.format6(date));
			date = TimeUtil.addDates(date, 1);
		}
		LambdaQueryWrapper<SpaceActionFrameUse> wrapper = new LambdaQueryWrapper<>();
		wrapper.in(SpaceActionFrameUse::getDate, chart.getXDates());
		List<SpaceActionFrameUse> useList = spaceActionFrameUseMapper.selectList(wrapper);
		Map<String, Integer> processMap = StreamApiUtils.toMap(
				StreamApiUtils.filter(useList,
						u -> SpaceActionFrame.Type.PROCESS.getName().equals(u.getType())),
				SpaceActionFrameUse::getDate, SpaceActionFrameUse::getCount);
		Map<String, Integer> targetMap = StreamApiUtils.toMap(
				StreamApiUtils.filter(useList,
						u -> SpaceActionFrame.Type.TARGET.getName().equals(u.getType())),
				SpaceActionFrameUse::getDate, SpaceActionFrameUse::getCount);
		for (String xDate : chart.getXDates()) {
			chart.getProcess().add(processMap.getOrDefault(xDate, 0));
			chart.getTarget().add(targetMap.getOrDefault(xDate, 0));
		}
		return chart;
	}

	/**
	 * 默认图表,假数据
	 *
	 * @return {@code IndexActionFrameChart }
	 * @throws
	 * @see IndexActionFrameChart
	 */
	private IndexActionFrameChart defaultChart() {
		List<Integer> list = Lists.newArrayList(61, 25, 43, 32, 59, 62, 68, 54, 78, 55, 71, 77, 41,
				50, 16, 65, 58, 47, 48, 58, 49, 60, 53, 43, 12, 58, 18, 25, 56, 14, 67, 14, 38, 62,
				53, 12, 77, 10, 59, 22);
		IndexActionFrameChart chart = new IndexActionFrameChart();
		Date date = TimeUtil.addDates(new Date(), -7);
		int j = (int) (date.getTime() / 86_400_000) % 40;
		for (int i = 0; i < 7; i++) {
			chart.getXDates().add(TimeUtil.format6(date));
			date = TimeUtil.addDates(date, 1);
			chart.getTarget().add(list.get((j + i) % 40));
			chart.getProcess().add(list.get((j + i + 20) % 40));
		}
		return chart;
	}

	/**
	 * 获取今日新增
	 *
	 * @param theory 理论
	 * @throws
	 * @see IndexTheory
	 */
	private void getAddedToday(IndexTheory theory) {
		Date start = TimeUtil.getStartOfDay(new Date());
		LambdaQueryWrapper<CourseLesson> wrapperLesson = new LambdaQueryWrapper<>();
		wrapperLesson.ge(CourseLesson::getCreateTime, start);
		int lesson = courseLessonMapper.selectCount(wrapperLesson);
		LambdaQueryWrapper<PracticeTheory> wrapperTheory = new LambdaQueryWrapper<>();
		wrapperTheory.ge(PracticeTheory::getCreateTime, start);
		int practiceTheory = practiceTheoryMapper.selectCount(wrapperTheory);
		LambdaQueryWrapper<PracticeOperation> wrapperOperation = new LambdaQueryWrapper<>();
		wrapperOperation.ge(PracticeOperation::getCreateTime, start);
		int practiceOperation = practiceOperationMapper.selectCount(wrapperOperation);
		theory.setAddedToday(lesson + practiceTheory + practiceOperation);
	}

	/**
	 * 获取今日新增
	 *
	 * @param theory 理论
	 * @throws
	 * @see IndexTheory
	 */
	private void getUpdatedToday(IndexTheory theory) {
		Date start = TimeUtil.getStartOfDay(new Date());
		LambdaQueryWrapper<CourseLesson> wrapperLesson = new LambdaQueryWrapper<>();
		wrapperLesson.ge(CourseLesson::getUpdateTime, start);
		int lesson = courseLessonMapper.selectCount(wrapperLesson);
		LambdaQueryWrapper<PracticeTheory> wrapperTheory = new LambdaQueryWrapper<>();
		wrapperTheory.ge(PracticeTheory::getUpdateTime, start);
		int practiceTheory = practiceTheoryMapper.selectCount(wrapperTheory);
		LambdaQueryWrapper<PracticeOperation> wrapperOperation = new LambdaQueryWrapper<>();
		wrapperOperation.ge(PracticeOperation::getUpdateTime, start);
		int practiceOperation = practiceOperationMapper.selectCount(wrapperOperation);
		theory.setUpdatedToday(lesson + practiceTheory + practiceOperation);
	}

	/**
	 * 获取今日新增和更新
	 *
	 * @param strategy 策略
	 * @throws
	 * @see IndexStrategy
	 */
	private void getAddAndUpdateToday(IndexStrategy strategy) {
		Date start = TimeUtil.getStartOfDay(new Date());
		LambdaQueryWrapper<SpaceStrategy> wrapperAdd = new LambdaQueryWrapper<>();
		wrapperAdd.ge(SpaceStrategy::getCreateTime, start);
		LambdaQueryWrapper<SpaceStrategy> wrapperUpdate = new LambdaQueryWrapper<>();
		wrapperUpdate.ge(SpaceStrategy::getUpdateTime, start);
		strategy.setAddedToday(strategyService.count(wrapperAdd));
		strategy.setUpdatedToday(strategyService.count(wrapperUpdate));
	}

	/**
	 * 获取今日新增
	 *
	 * @param tactic 战法
	 * @throws
	 * @see IndexTactic
	 */
	private void getAddedToday(IndexTactic tactic) {
		Date start = TimeUtil.getStartOfDay(new Date());
		LambdaQueryWrapper<SpaceTacticAttack> attackWrapper = new LambdaQueryWrapper<>();
		attackWrapper.ge(SpaceTacticAttack::getCreateTime, start);
		LambdaQueryWrapper<SpaceTacticDefense> defenseWrapper = new LambdaQueryWrapper<>();
		defenseWrapper.ge(SpaceTacticDefense::getCreateTime, start);
		tactic.setAddedToday(spaceTacticAttackMapper.selectCount(attackWrapper)
				+ spaceTacticDefenseMapper.selectCount(defenseWrapper));
	}

	/**
	 * 获取今日更新
	 *
	 * @param tactic 战法
	 * @throws
	 * @see IndexTactic
	 */
	private void getUpdatedToday(IndexTactic tactic) {
		Date start = TimeUtil.getStartOfDay(new Date());
		LambdaQueryWrapper<SpaceTacticAttack> attackWrapper = new LambdaQueryWrapper<>();
		attackWrapper.ge(SpaceTacticAttack::getUpdateTime, start);
		LambdaQueryWrapper<SpaceTacticDefense> defenseWrapper = new LambdaQueryWrapper<>();
		defenseWrapper.ge(SpaceTacticDefense::getUpdateTime, start);
		tactic.setUpdatedToday(spaceTacticAttackMapper.selectCount(attackWrapper)
				+ spaceTacticDefenseMapper.selectCount(defenseWrapper));
	}
}
