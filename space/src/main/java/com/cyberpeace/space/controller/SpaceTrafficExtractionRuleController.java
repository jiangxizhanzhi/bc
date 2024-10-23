
package com.cyberpeace.space.controller;

import com.cyberpeace.space.service.context.rule.SpaceTrafficExtractionRuleBo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceTrafficExtractionRule;
import com.cyberpeace.space.service.SpaceTrafficExtractionRuleService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "知识库-规则库-网络流量提取规则")
@RestController
@RequestMapping("/space/rule/space_traffic_extraction_rule")
@RequiresAuthentication
@Slf4j
public class SpaceTrafficExtractionRuleController extends SpaceRuleRepositoryBaseController<SpaceTrafficExtractionRuleService, SpaceTrafficExtractionRule, SpaceTrafficExtractionRuleBo>
    
{}