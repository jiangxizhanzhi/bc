
package com.cyberpeace.space.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceIocThreat;
import com.cyberpeace.space.service.SpaceIocThreatService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "知识库-规则库-IOC威胁情报库")
@RestController
@RequestMapping("/space/rule/space_ioc_threat")
@RequiresAuthentication
@Slf4j
public class SpaceIocThreatController extends SpaceRuleRepositoryBaseController<SpaceIocThreatService, SpaceIocThreat>
    
{}