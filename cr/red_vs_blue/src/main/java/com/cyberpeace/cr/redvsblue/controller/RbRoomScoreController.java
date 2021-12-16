package com.cyberpeace.cr.redvsblue.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.redvsblue.service.RbRoomScoreService;
import com.cyberpeace.cr.redvsblue.service.context.mission.RoleScore;
import com.cyberpeace.cs.service.LocalSceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "红蓝对抗实例")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/red_vs_blue/red_vs_blue/")
public class RbRoomScoreController {

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private RbRoomScoreService rbRoomService;

	@Autowired
	private RbRoomScoreService rbRoomScoreService;

	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * 查询训练角色得分信息
	 * @return
	 */
	@ApiOperation("查询训练角色得分信息")
	@GetMapping("/get_role_score_info")
	public BaseRsp<List<RoleScore>> getRoleScoreInfo(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		List<RoleScore> data = rbRoomScoreService.getRoleScoreInfo(resource_id);

		return new BaseRsp(data);
	}
}
