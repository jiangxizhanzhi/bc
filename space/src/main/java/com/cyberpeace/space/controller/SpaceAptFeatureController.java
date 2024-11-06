
package com.cyberpeace.space.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceAptFeature;
import com.cyberpeace.space.service.SpaceAptFeatureService;
import com.cyberpeace.space.service.context.rule.SpaceAptFeatureBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "知识库-规则库-APT特征库")
@RestController
@RequestMapping("/space/rule/space_apt_feature")
@RequiresAuthentication
@Slf4j
public class SpaceAptFeatureController extends SpaceControllerBase<SpaceAptFeatureService, SpaceAptFeature, SpaceAptFeatureBo>
    
{}