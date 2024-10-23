
package com.cyberpeace.space.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.space.dao.entity.SpaceScoreRankingRule;
import com.cyberpeace.space.dao.mapper.SpaceScoreRankingRuleMapper;
import com.cyberpeace.space.service.SpaceScoreRankingRuleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SpaceScoreRankingRuleServiceImpl extends ServiceImpl<SpaceScoreRankingRuleMapper, SpaceScoreRankingRule> implements SpaceScoreRankingRuleService

{}