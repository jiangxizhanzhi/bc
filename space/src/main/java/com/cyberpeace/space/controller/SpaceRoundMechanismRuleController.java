
package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import com.cyberpeace.space.service.context.rule.SpaceRoundMechanismRuleBo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceRoundMechanismRule;
import com.cyberpeace.space.service.SpaceRoundMechanismRuleService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "知识库-规则库-轮次机制规则")
@RestController
@RequestMapping("/space/rule/space_round_mechanism_rule")
@RequiresAuthentication
@Slf4j
public class SpaceRoundMechanismRuleController extends SpaceControllerBase<SpaceRoundMechanismRuleService, SpaceRoundMechanismRule, SpaceRoundMechanismRuleBo, SpacePageReqBase>
    
{}