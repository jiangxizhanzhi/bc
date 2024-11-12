
package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.context.rule.SpaceViolationJudgeRuleBo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceViolationJudgeRule;
import com.cyberpeace.space.service.SpaceViolationJudgeRuleService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "知识库-规则库-违规判定规则")
@RestController
@RequestMapping("/space/rule/space_violation_judge_rule")
@RequiresAuthentication
@Slf4j
public class SpaceViolationJudgeRuleController extends SpaceControllerBase<SpaceViolationJudgeRuleService, SpaceViolationJudgeRule, SpaceViolationJudgeRuleBo>
    
{}