package com.cyberpeace.space.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.cr.tool.dao.entity.Tool;
import com.cyberpeace.cr.tool.dao.mapper.ToolMapper;
import com.cyberpeace.cr.tool.service.ToolService;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeOperationMapper;
import com.cyberpeace.oj.practice.dao.mapper.PracticeTheoryMapper;
import com.cyberpeace.space.dao.entity.SpaceActionFrame;
import com.cyberpeace.space.dao.mapper.SpaceBaseAlgorithmMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticAttackMapper;
import com.cyberpeace.space.dao.mapper.SpaceTacticDefenseMapper;
import com.cyberpeace.space.dao.mapper.SpaceWarningDictionaryMapper;
import com.cyberpeace.space.service.SpaceActionFrameService;
import com.cyberpeace.space.service.SpaceIndexService;
import com.cyberpeace.space.service.SpaceRuleCommonService;
import com.cyberpeace.space.service.SpaceStrategyService;
import com.cyberpeace.space.service.context.index.*;
import com.cyberpeace.space.service.context.rule.BaseDataRuleStatistics;
import com.cyberpeace.space.service.context.rule.RaceStatistics;
import com.cyberpeace.space.service.context.rule.ThreatDetectStatistics;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
		theory.calSum();
		res.setTheory(theory);
		// 策略库
		IndexStrategy strategy = strategyService.getStatistics();
		res.setStrategy(strategy);
		// 战法
		IndexTactic tactic = new IndexTactic();
		tactic.setAttack(spaceTacticAttackMapper.selectCount(null));
		tactic.setDefense(spaceTacticDefenseMapper.selectCount(null));
		tactic.calSum();
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
		algorithm.setBase(spaceBaseAlgorithmMapper.selectCount(null));
		algorithm.calSum();
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

		return frame;

	}
}
