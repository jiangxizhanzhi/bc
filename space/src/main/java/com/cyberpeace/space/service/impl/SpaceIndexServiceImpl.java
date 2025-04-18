package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.constant.CallType;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmService;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.dao.mapper.ToolMapper;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.practice.dao.entity.PracticeOperation;
import com.cyberpeace.oj.practice.dao.entity.PracticeTheory;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.space.dao.entity.*;
import com.cyberpeace.space.dao.mapper.*;
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

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
	private SpaceTacticAttackMapper spaceTacticAttackMapper;

	@Autowired
	private SpaceTacticDefenseMapper spaceTacticDefenseMapper;

	@Autowired
	private SpaceRuleCommonService spaceRuleCommonService;

	@Autowired
	private SpaceWarningDictionaryMapper spaceWarningDictionaryMapper;

	@Autowired
	private SpaceBaseAlgorithmMapper spaceBaseAlgorithmMapper;

	@Autowired
	private ToolService toolService;

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

	@Override
	public IndexKnowledge getKnowledgeRepositoryStatistics() {
		IndexKnowledge res = new IndexKnowledge();
		// 理论知识库
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
		theory.calSum();
		this.getAddedToday(theory);
		this.getUpdatedToday(theory);
		res.setTheory(theory);
		// 策略库
		IndexStrategy strategy = strategyService.getStatistics();
		strategy.setUse(callCountService.getCountByName(CallType.STRATEGY));
		this.getAddAndUpdateToday(strategy);
		res.setStrategy(strategy);
		// 战法
		IndexTactic tactic = new IndexTactic();
		tactic.setAttack(spaceTacticAttackMapper.selectCount(null));
		tactic.setDefense(spaceTacticDefenseMapper.selectCount(null));
		tactic.setUse(callCountService.getCountByName(CallType.TACTIC));
		tactic.calSum();
		this.getAddedToday(tactic);
		this.getUpdatedToday(tactic);
		res.setTactic(tactic);
		// 规则
		IndexRule rule = new IndexRule();
		BaseDataRuleStatistics baseDataRuleStatistics = spaceRuleCommonService
				.getBaseDataStatistics();
		rule.setBase(baseDataRuleStatistics.getApt() + baseDataRuleStatistics.getIoc()
				+ baseDataRuleStatistics.getFingerPrint() + baseDataRuleStatistics.getVuln());
		rule.setWarning(spaceWarningDictionaryMapper.selectCount(null));

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
		indexTool.setSum(toolList.size());

		Map<String, Integer> map = Maps.newHashMap();
		for (Tool tool : toolList) {
			List<String> platforms = Arrays.asList(StringUtils.split(tool.getPlatforms(), ","));
			for (String platform : platforms) {
				map.putIfAbsent(platform, 0);
				map.put(platform, map.get(platform) + 1);
			}
		}
		indexTool.setPlatform(map);
		return indexTool;
	}

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

	@Override
	public String getAiUrl() {
		String ip = SystemPropUtil.getProp("AI_HOST");
		if (StringUtils.isBlank(ip)) {
			ip = "10.4.2.3";
		}
		return ip;
	}

	@Override
	public KnowledgeChart getKChart() {
		KnowledgeChart chart = new KnowledgeChart();
		// 理论知识库
		KnowledgeChart theoryChart = this.getTheoryChart();

		// 策略库
		KnowledgeChart strategyChart = this.getStrategyChart();

		// 战法
		KnowledgeChart tacticChart = this.getTacticChart();

		// 规则
		KnowledgeChart ruleChart = this.getRuleChart();
		// 流量
		KnowledgeChart trafficChart = this.getTrafficChart();

		// 算法
		KnowledgeChart algorithmChart = this.getAlgorithmChart();

		// 场景
		KnowledgeChart sceneChart = this.getSceneChart();

		return null;
	}

	private KnowledgeChart getSceneChart() {
		KnowledgeChart chart = new KnowledgeChart();

		return chart;
	}

	private KnowledgeChart getAlgorithmChart() {
		KnowledgeChart chart = new KnowledgeChart();
		IndexAlgorithm algorithm = new IndexAlgorithm();
		algorithm.setBase(evalAlgorithmService.count());
		algorithm.calSum();
		algorithm.setUse(callCountService.getCountByName(CallType.ALGORITHM));

		return chart;
	}

	private KnowledgeChart getTrafficChart() {
		KnowledgeChart chart = new KnowledgeChart();


		return chart;
	}

	private KnowledgeChart getRuleChart() {
		KnowledgeChart chart = new KnowledgeChart();
		IndexRule rule = new IndexRule();
		BaseDataRuleStatistics baseDataRuleStatistics = spaceRuleCommonService
				.getBaseDataStatistics();
		rule.setBase(baseDataRuleStatistics.getApt() + baseDataRuleStatistics.getIoc()
				+ baseDataRuleStatistics.getFingerPrint() + baseDataRuleStatistics.getVuln());
		rule.setWarning(spaceWarningDictionaryMapper.selectCount(null));

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

		return chart;
	}

	private KnowledgeChart getTacticChart() {
		KnowledgeChart chart = new KnowledgeChart();
		int attack = spaceTacticAttackMapper.selectCount(null);
		int defense = spaceTacticDefenseMapper.selectCount(null);
		return chart;
	}

	private KnowledgeChart getTheoryChart() {
		KnowledgeChart chart = new KnowledgeChart();
		LambdaQueryWrapper<CourseLesson> wrapperTheory = new LambdaQueryWrapper<>();
		wrapperTheory.eq(CourseLesson::getType, CourseLesson.LessonType.THEORY.getCode());
		int theoryCourse = courseLessonMapper.selectCount(wrapperTheory);
		LambdaQueryWrapper<CourseLesson> wrapperExp = new LambdaQueryWrapper<>();
		wrapperExp.eq(CourseLesson::getType, CourseLesson.LessonType.EXPERIMENT.getCode());
		int onlineExp = courseLessonMapper.selectCount(wrapperExp);
		int theoryExam = practiceTheoryMapper.selectCount(null);
		int expExam = practiceOperationMapper.selectCount(null);
		return chart;
	}

	private KnowledgeChart getStrategyChart() {
		KnowledgeChart chart = new KnowledgeChart();
		IndexStrategy strategy = strategyService.getStatistics();
		return chart;
	}

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
		chart.setProcess(Arrays.asList(0, 0, 0, 0, 0, 0, 0));
		Date date = TimeUtil.addDates(new Date(), -7);
		int j = (int) (date.getTime() / 86_400_000) % 40;
		for (int i = 0; i < 7; i++) {
			chart.getXDates().add(TimeUtil.format6(date));
			date = TimeUtil.addDates(date, 1);
			chart.getTarget().add(list.get((j + i) % 40));
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
