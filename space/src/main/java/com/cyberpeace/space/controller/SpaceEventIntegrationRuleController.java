
package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceEventIntegrationRule;
import com.cyberpeace.space.service.SpaceEventIntegrationRuleService;
import com.cyberpeace.space.service.context.rule.SpaceEventIntegrationRuleBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "知识库-规则库-事件综合规则")
@RestController
@RequestMapping("/space/rule/space_event_integration_rule")
@RequiresAuthentication
@Slf4j
public class SpaceEventIntegrationRuleController extends SpaceControllerBase<SpaceEventIntegrationRuleService, SpaceEventIntegrationRule, SpaceEventIntegrationRuleBo, SpacePageReqBase>
    
{}