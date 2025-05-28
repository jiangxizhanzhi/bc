
package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.service.context.rule.SpaceScoreRankingRuleBo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceScoreRankingRule;
import com.cyberpeace.space.service.SpaceScoreRankingRuleService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间得分排名统治Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpaceControllerBase
 * @since 1.0.0
 */
@Api(tags = "知识库-规则库-记分排行规则")
@RestController
@RequestMapping("/space/rule/space_score_ranking_rule")
@RequiresAuthentication
@Slf4j
public class SpaceScoreRankingRuleController extends SpaceControllerBase<SpaceScoreRankingRuleService, SpaceScoreRankingRule, SpaceScoreRankingRuleBo, SpacePageReqBase>
    
{}