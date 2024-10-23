
package com.cyberpeace.space.controller;

import com.cyberpeace.space.service.context.rule.SpaceBaseAlgorithmBo;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceBaseAlgorithm;
import com.cyberpeace.space.service.SpaceBaseAlgorithmService;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "知识库-算法库-基础算法库")
@RestController
@RequestMapping("/space/algorithm/base_algorithm")
@RequiresAuthentication
@Slf4j
public class SpaceBaseAlgorithmController extends SpaceRuleRepositoryBaseController<SpaceBaseAlgorithmService, SpaceBaseAlgorithm, SpaceBaseAlgorithmBo> {

}