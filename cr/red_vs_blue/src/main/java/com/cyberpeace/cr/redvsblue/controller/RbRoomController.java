package com.cyberpeace.cr.redvsblue.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import com.cyberpeace.cr.crbase.service.context.role.ChartInfo;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.role.RoleUsers;
import com.cyberpeace.cr.redvsblue.controller.req.RbRoomReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbRoomRoleRes;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbRoomRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.RbRoomData;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomContext;
import com.cyberpeace.cr.redvsblue.service.context.RoomRole;
import com.cyberpeace.cs.service.LocalSceneService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "红蓝对抗实例")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/red_vs_blue/red_vs_blue/")
public class RbRoomController {

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private LocalSceneService localSceneService;

	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp create(@RequestBody @Validated RbRoomReq rbRoomReq) {
		RbRoomContext rbRoomContext = BeanUtils.copy(rbRoomReq, RbRoomContext.class);
		rbRoomContext
				.setPreSkill(JsonUtil.toJsonObjectArray(rbRoomReq.getPreSkill(), String.class));
		rbRoomContext
				.setTrainSkill(JsonUtil.toJsonObjectArray(rbRoomReq.getTrainSkill(), String.class));
		rbRoomContext.setRoleConfig(
				JsonUtil.toJsonObjectArray(rbRoomReq.getRoleConfig(), RoomRole.class));
		RbRoom teamRoom = rbRoomService.create(rbRoomContext);

		return getRbRoom(teamRoom.getResourceId());
	}

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<RbRoomRsp> getRbRoom(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
		String userId = UserInfoContext.getUserContext().getUserId();

		RbRoomData teamRoomData = rbRoomService.getRbRoomDataByRoomId(resource_id);

		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(teamRoomData.getResourceId());

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

		RbRoomRsp teamRoomRsp = BeanUtils.copy(teamRoomData, RbRoomRsp.class);
		teamRoomRsp.setRolePermission(getSelfRole(crRoles, userId));

		teamRoomRsp.setRolesData(getRbRoomRoleRes(crRoles));

		teamRoomRsp
				.setPreSkill(JsonUtil.toJsonObjectArray(teamRoomData.getPreSkill(), String.class));
		teamRoomRsp.setTrainSkill(
				JsonUtil.toJsonObjectArray(teamRoomData.getTrainSkill(), String.class));

		return new BaseRsp<RbRoomRsp>(teamRoomRsp);
	}

	private List<RbRoomRoleRes> getRbRoomRoleRes(List<CrRole> crRoles) {
		List<RbRoomRoleRes> teamRoomRoleResList = Lists.newArrayList();
		List<RbRoomRoleRes> redRoles = Lists.newArrayList();
		List<RbRoomRoleRes> blueRoles = Lists.newArrayList();

		for (CrRole crRole : crRoles) {
			RbRoomRoleRes teamRoomRoleRes = BeanUtils.copy(crRole, RbRoomRoleRes.class);
			teamRoomRoleRes.setUsersData(crRoleService.getUserDatas(crRole.getUserIds()));
			teamRoomRoleRes.setUsers(JsonUtil.toJsonObjectArray(crRole.getUserIds(), String.class));

			if (crRole.getRoleGroup().equals(CrRole.RbRoleGroupCode.RED.getCode())) {
				redRoles.add(teamRoomRoleRes);
			} else if (crRole.getRoleGroup().equals(CrRole.RbRoleGroupCode.BLUE.getCode())) {
				blueRoles.add(teamRoomRoleRes);
			} else {
				teamRoomRoleResList.add(teamRoomRoleRes);
			}
		}
		RbRoomRoleRes redRoleRes = new RbRoomRoleRes();
		redRoleRes.setName(CrRole.RbRoleGroupCode.RED.getRole());
		redRoleRes.setRoleGroup(CrRole.RbRoleGroupCode.RED.getCode());
		redRoleRes.setChildren(redRoles);
		teamRoomRoleResList.add(redRoleRes);

		RbRoomRoleRes blueRoleRes = new RbRoomRoleRes();
		blueRoleRes.setName(CrRole.RbRoleGroupCode.BLUE.getRole());
		blueRoleRes.setRoleGroup(CrRole.RbRoleGroupCode.BLUE.getCode());
		blueRoleRes.setChildren(blueRoles);
		teamRoomRoleResList.add(blueRoleRes);

		return teamRoomRoleResList;
	}

	private BaseRole getSelfRole(List<CrRole> crRoles, String userId) {
		BaseRole baseRole = new BaseRole();
		List<RoleUsers> roleUsersList = Lists.newArrayList();
		crRoles.forEach(crRole -> {
			if (crRole.getUserIds().contains(userId)) {
				baseRole.setResourceId(crRole.getResourceId());
				baseRole.setName(crRole.getName());
				baseRole.setParentHash(crRole.getRoleGroup());
				baseRole.setDescription(crRole.getDescription());
				baseRole.setRoleGroup(crRole.getRoleGroup());
			}

			RoleUsers roleUsers = new RoleUsers();
			roleUsers.setRoleName(crRole.getName());
			roleUsers.setRoleGroup(crRole.getRoleGroup());
			String userNamesByRoleUsers = crRoleService
					.getUserNamesByRoleUsers(crRole.getUserIds());
			roleUsers.setUsers(JsonUtil.toJsonObjectArray(userNamesByRoleUsers, String.class));
			roleUsersList.add(roleUsers);

		});

		ChartInfo chartInfo = new ChartInfo();
		chartInfo.setInfoType(ChartInfo.ChartInfoType.NOT_RED_OR_BLUE_ONGOING.getCode());
		chartInfo.setRoleDataList(roleUsersList);

		baseRole.setChartInfo(chartInfo);

		return baseRole;
	}
	// @ApiOperation("修改")
	// @PutMapping()
	// public BaseRsp<RbRoomRsp>
	// update(@RequestBody @Validated TeamTrainRoomModifyReq teamTrainRoomModifyReq)
	// {
	//
	// RbRoom teamRoom =
	// teamRoomService.getById(teamTrainRoomModifyReq.getResourceId());
	//
	// if (teamRoom == null) {
	// throw new ResourceNotFoundException(teamTrainRoomModifyReq.getResourceId());
	// }
	// rbRoomContext rbRoomContext = BeanUtils.copy(teamTrainRoomModifyReq,
	// rbRoomContext.class);
	// List<RoomRole> roomRoleList = JsonUtil
	// .toJsonObjectArray(teamTrainRoomModifyReq.getRoleConfig(), RoomRole.class);
	// rbRoomContext.setRoleConfig(roomRoleList);
	// teamRoomService.updateRbRoom(rbRoomContext, teamRoom);
	//
	// return getRbRoom(teamTrainRoomModifyReq.getResourceId());
	// }
}
