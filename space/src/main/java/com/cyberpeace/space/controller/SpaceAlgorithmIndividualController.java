package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.controller.SpaceControllerBase;
import com.cyberpeace.space.model.req.SpacePageReqBase;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceAlgorithmIndividual;
import com.cyberpeace.space.service.SpaceAlgorithmIndividualService;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmIndividualBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "知识库-算法库-个人能力评估算法")
@RestController
@RequestMapping("/space/algorithm/algorithm_individual")
@RequiresAuthentication
@Slf4j
public class SpaceAlgorithmIndividualController extends SpaceControllerBase<SpaceAlgorithmIndividualService, SpaceAlgorithmIndividual, SpaceAlgorithmIndividualBo, SpacePageReqBase> {
}
    