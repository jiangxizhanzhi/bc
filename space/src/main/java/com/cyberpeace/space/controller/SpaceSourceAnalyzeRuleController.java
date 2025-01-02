
package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.service.context.rule.SpaceSourceAnalyzeRuleBo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceSourceAnalyzeRule;
import com.cyberpeace.space.service.SpaceSourceAnalyzeRuleService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "知识库-规则库-攻击源分析规则")
@RestController
@RequestMapping("/space/rule/space_source_analyze_rule")
@RequiresAuthentication
@Slf4j
public class SpaceSourceAnalyzeRuleController extends SpaceControllerBase<SpaceSourceAnalyzeRuleService, SpaceSourceAnalyzeRule, SpaceSourceAnalyzeRuleBo, SpacePageReqBase>
    
{}