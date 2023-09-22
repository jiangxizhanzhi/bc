package com.cyberpeace.cr.teamtrain.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.enums.TeamRoleGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.controller.rsp.RoleMissionRsp;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.MissionContext;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleContext;
import com.cyberpeace.cr.crbase.service.context.role.RoleData;
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
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<CrRoleContext<RoleMissionRsp>> getRoles(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		CrRoleContext<MissionContext> crRoleContext = crRoleService.getRoles(resource_id, TeamRoleGroup.STUDENT);

		CrRoleContext<RoleMissionRsp> crRoleContextRsp = BeanUtils.copy(crRoleContext,
				CrRoleContext.class);

		for (int i = 0; i < crRoleContextRsp.getRoles().size(); i++) {
			RoleData<RoleMissionRsp> roleData = crRoleContextRsp.getRoles().get(i);
			for (int j = 0; j < roleData.getMissionsList().size(); j++) {
				MissionSchemeObject<RoleMissionRsp> roleMissionObject = roleData.getMissionsList()
						.get(j);
				// 拷贝获取不到mission列表，遍历获取
				List<MissionContext> missions = crRoleContext.getRoles().get(i).getMissionsList()
						.get(j).getMissions();

				List<RoleMissionRsp> roleMissionRspList = missions.stream().map(roleMission -> BeanUtils.copy(roleMission,
						RoleMissionRsp.class)).collect(Collectors.toList());
				roleMissionObject.setMissions(roleMissionRspList);
			}
		}

		return new BaseRsp<>(crRoleContextRsp);
	}

}