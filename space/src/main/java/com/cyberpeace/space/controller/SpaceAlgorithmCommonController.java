package com.cyberpeace.space.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.space.service.SpaceAlgorithmExpertiseService;
import com.cyberpeace.space.service.SpaceAlgorithmIndividualService;
import com.cyberpeace.space.service.SpaceAlgorithmRoleService;
import com.cyberpeace.space.service.SpaceAlgorithmTeamService;
import com.cyberpeace.space.service.context.algorithm.SpaceAlgorithmStatistics;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 空间算法公共Controller
 *
 * @author SN
 * @date 2024/11/07
 */
@Api(tags = "知识库-算法库-通用")
@RestController
@RequestMapping("/space/algorithm/common")
@RequiresAuthentication
@Slf4j
public class SpaceAlgorithmCommonController {

	/**
	 * 专门知识Service
	 */
	@Autowired
	private SpaceAlgorithmExpertiseService expertiseService;

	/**
	 * 角色Service
	 */
	@Autowired
	private SpaceAlgorithmRoleService roleService;

	/**
	 * 个人Service
	 */
	@Autowired
	private SpaceAlgorithmIndividualService individualService;

	/**
	 * 团队Service
	 */
	@Autowired
	private SpaceAlgorithmTeamService teamService;

	/**
	 * 获取统计学
	 *
	 * @return {@code BaseRsp }
	 */
	@ApiOperation("人员能力评估算法统计")
	@GetMapping("/statistics")
	public BaseRsp<SpaceAlgorithmStatistics> getStatistics() {
		SpaceAlgorithmStatistics res = new SpaceAlgorithmStatistics();
		res.setExpertise(expertiseService.count());
		res.setRole(roleService.count());
		res.setIndividual(individualService.count());
		res.setTeam(teamService.count());
		return new BaseRsp(res);
	}
}
