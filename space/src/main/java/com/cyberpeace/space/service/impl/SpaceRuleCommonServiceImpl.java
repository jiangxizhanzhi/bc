package com.cyberpeace.space.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.space.dao.mapper.*;
import com.cyberpeace.space.service.SpaceRuleCommonService;
import com.cyberpeace.space.service.context.rule.BaseDataRuleStatistics;
import com.cyberpeace.space.service.context.rule.RaceStatistics;
import com.cyberpeace.space.service.context.rule.ThreatDetectStatistics;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceRuleCommonServiceImpl implements SpaceRuleCommonService {

	@Autowired
	private SpaceScoreRankingRuleMapper spaceScoreRankingRuleMapper;

	@Autowired
	private SpaceRoundMechanismRuleMapper spaceRoundMechanismRuleMapper;

	@Autowired
	private SpaceViolationJudgeRuleMapper spaceViolationJudgeRuleMapper;

	@Autowired
	private SpaceTrafficExtractionRuleMapper spaceTrafficExtractionRuleMapper;

	@Autowired
	private SpaceTrafficDetectRuleMapper spaceTrafficDetectRuleMapper;

	@Autowired
	private SpaceLoadedFileThreatRuleMapper spaceLoadedFileThreatRuleMapper;

	@Autowired
	private SpaceAptThreatDetectRuleMapper spaceAptThreatDetectRuleMapper;

	@Autowired
	private SpaceThreatImageRuleMapper spaceThreatImageRuleMapper;

	@Autowired
	private SpaceEventIntegrationRuleMapper spaceEventIntegrationRuleMapper;

	@Autowired
	private SpaceSourceAnalyzeRuleMapper spaceSourceAnalyzeRuleMapper;

	@Autowired
	private SpaceFingerPrintMapper spaceFingerPrintMapper;

	@Autowired
	private SpaceAptFeatureMapper spaceAptFeatureMapper;

	@Autowired
	private SpaceIocThreatMapper spaceIocThreatMapper;

	@Override
	public RaceStatistics getRaceStatistics() {
		Integer scoreRanking = spaceScoreRankingRuleMapper.selectCount(null);
		Integer roundMechanism = spaceRoundMechanismRuleMapper.selectCount(null);
		Integer violation = spaceViolationJudgeRuleMapper.selectCount(null);
		RaceStatistics res = new RaceStatistics();
		res.setScoreRanking(scoreRanking);
		res.setRoundMechanism(roundMechanism);
		res.setViolationJudge(violation);
		return res;
	}

	@Override
	public ThreatDetectStatistics getThreatDetectStatistics() {
		ThreatDetectStatistics statistics = new ThreatDetectStatistics();
		statistics.setTrafficDetect(spaceTrafficDetectRuleMapper.selectCount(null));
//		statistics.setTrafficExtraction(spaceTrafficExtractionRuleMapper.selectCount(null));
		statistics.setLoadedFileThreat(spaceLoadedFileThreatRuleMapper.selectCount(null));
		statistics.setAptThreat(spaceAptThreatDetectRuleMapper.selectCount(null));
		statistics.setEventIntegration(spaceEventIntegrationRuleMapper.selectCount(null));
//		statistics.setThreatImage(spaceThreatImageRuleMapper.selectCount(null));
//		statistics.setSourceAnalyze(spaceSourceAnalyzeRuleMapper.selectCount(null));
		return statistics;
	}

	@Override
	public BaseDataRuleStatistics getBaseDataStatistics() {
		BaseDataRuleStatistics statistics = new BaseDataRuleStatistics();
//		statistics.setFingerPrint(spaceFingerPrintMapper.selectCount(null));
		statistics.setApt(spaceAptFeatureMapper.selectCount(null));
//		statistics.setIoc(spaceIocThreatMapper.selectCount(null));
		return statistics;
	}
}
