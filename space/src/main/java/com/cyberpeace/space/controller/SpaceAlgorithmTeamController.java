package com.cyberpeace.space.controller;

import com.cyberpeace.space.model.controller.SpaceControllerBase;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.space.dao.entity.SpaceAlgorithmTeam;
import com.cyberpeace.space.service.SpaceAlgorithmTeamService;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmTeamBo;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "知识库-算法库-团队能力评估算法")
@RestController
@RequestMapping("/space/algorithm/algorithm_team")
@RequiresAuthentication
@Slf4j
public class SpaceAlgorithmTeamController extends SpaceControllerBase<SpaceAlgorithmTeamService, SpaceAlgorithmTeam, SpaceAlgorithmTeamBo> {
}
    