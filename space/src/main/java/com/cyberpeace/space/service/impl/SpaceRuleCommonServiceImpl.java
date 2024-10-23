package com.cyberpeace.space.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.space.dao.mapper.SpaceRoundMechanismRuleMapper;
import com.cyberpeace.space.dao.mapper.SpaceScoreRankingRuleMapper;
import com.cyberpeace.space.dao.mapper.SpaceViolationJudgeRuleMapper;
import com.cyberpeace.space.service.SpaceRuleCommonService;
import com.cyberpeace.space.service.context.rule.RaceStatistics;

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
}
