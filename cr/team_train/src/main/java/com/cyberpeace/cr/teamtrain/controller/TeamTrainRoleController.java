package com.cyberpeace.cr.teamtrain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.model.role.CrRoleRsp;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

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
	public BaseRsp<CrRoleRsp> getRoles(
			@ApiParam(name = "room_id", value = "团队训练id", required = true) String room_id) {
		CrRoleRsp crRoleRsp = crRoleService.getRoles(room_id);

		return new BaseRsp<CrRoleRsp>(crRoleRsp);
	}

}