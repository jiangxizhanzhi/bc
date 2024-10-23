
package com.cyberpeace.space.controller;

import com.cyberpeace.space.service.context.rule.SpaceLoadedFileThreatRuleBo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;
import com.cyberpeace.space.service.SpaceLoadedFileThreatRuleService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "知识库-规则库-负载文件威胁规则")
@RestController
@RequestMapping("/space/rule/space_loaded_file_threat_rule")
@RequiresAuthentication
@Slf4j
public class SpaceLoadedFileThreatRuleController extends SpaceRuleRepositoryBaseController<SpaceLoadedFileThreatRuleService, SpaceLoadedFileThreatRule, SpaceLoadedFileThreatRuleBo>
    
{}