
package com.cyberpeace.space.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceAptThreatDetectRule;
import com.cyberpeace.space.service.SpaceAptThreatDetectRuleService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "知识库-规则库-特定APT威胁检测规则")
@RestController
@RequestMapping("/space/rule/space_apt_threat_detect_rule")
@RequiresAuthentication
@Slf4j
public class SpaceAptThreatDetectRuleController extends SpaceRuleRepositoryBaseController<SpaceAptThreatDetectRuleService, SpaceAptThreatDetectRule>
    
{}