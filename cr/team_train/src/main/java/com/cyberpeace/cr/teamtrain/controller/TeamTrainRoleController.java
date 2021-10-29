package com.cyberpeace.cr.teamtrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.teamtrain.model.role.CrRoleRes;
import com.cyberpeace.cr.teamtrain.service.CrRoleService;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "团队训练角色")
@RestController
@RequestMapping("/cr/team_train/role")
public class TeamTrainRoleController {

	@Autowired
	TeamRoomService teamRoomService;

	@Autowired
	CrRoleService crRoleService;

	@ApiOperation("团队实例查询角色")
	@GetMapping()
	public BaseRsp<CrRoleRes> getRoles(String roomId) {
		CrRoleRes t = crRoleService.getOr404(roomId);
		return new BaseRsp<CrRoleRes>(t);
	}

}
