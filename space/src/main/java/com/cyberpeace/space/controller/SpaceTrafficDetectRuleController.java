
package com.cyberpeace.space.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.controller.req.SpaceTrafficDetectRulePageReq;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.service.SpaceTrafficDetectRuleService;
import com.cyberpeace.space.service.context.rule.SpaceTrafficDetectRuleBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "知识库-规则库-流量攻击检测规则")
@RestController
@RequestMapping("/space/rule/space_traffic_detect_rule")
@RequiresAuthentication
@Slf4j
public class SpaceTrafficDetectRuleController extends SpaceControllerBase<SpaceTrafficDetectRuleService, SpaceTrafficDetectRule, SpaceTrafficDetectRuleBo, SpaceTrafficDetectRulePageReq> {

    @Override
	public void checkNameDuplication(SpaceTrafficDetectRule entity) {

    }
}