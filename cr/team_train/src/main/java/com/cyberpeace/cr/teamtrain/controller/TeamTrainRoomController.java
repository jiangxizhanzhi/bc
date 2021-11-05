package com.cyberpeace.cr.teamtrain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.exception.ResourceNotFoundException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.MissionSchemeService;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.role.RoleUsers;
import com.cyberpeace.cr.teamtrain.controller.req.ResourceIdReq;
import com.cyberpeace.cr.teamtrain.controller.req.TeamRoomReq;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainRoomReq;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamRoomRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTemplate;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTemplateService;
import com.cyberpeace.cr.teamtrain.service.context.ListQuery;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainRoomContext;
import com.cyberpeace.cr.teamtrain.service.context.TeamTrainTemplateContext;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "团队训练实例")
@RestController
@RequestMapping("/cr/team_train/room")
public class TeamTrainRoomController {

	@Autowired
	TeamRoomService teamRoomService;

	@Autowired
	CrRoleService crRoleService;

	@Autowired
	TeamTemplateService teamTemplateService;


	@Autowired
	BusinessTemplateService businessTemplateService;

	@Autowired
	MissionSchemeService missionSchemeService;

	@ApiOperation("新增")
	@PostMapping()
	public BaseRsp<TeamRoomRsp> create(@RequestBody @Validated TeamTrainRoomReq teamTrainRoomReq) {
		TeamTrainRoomContext teamTrainRoomContext = BeanUtils.copy(teamTrainRoomReq,
				TeamTrainRoomContext.class);
		TeamTemplate teamTemplate = teamRoomService.create(teamTrainRoomContext);

		return getTeamRoom(teamTemplate.getResourceId());
	}

	@ApiOperation("修改")
	@PutMapping()
	public BaseRsp<TeamRoomRsp> update(@RequestBody @Validated TeamTrainRoomReq teamTrainRoomReq) {

		TeamTrainTemplateContext teamTrainTemplateContext = new TeamTrainTemplateContext();
		TeamTrainRoomContext teamTrainRoomContext = BeanUtils.copy(teamTrainRoomReq,
				TeamTrainRoomContext.class);

		TeamRoom teamRoom = teamRoomService.getById(teamTrainRoomContext.getResourceId());

		if (teamRoom == null) {
			throw new ResourceNotFoundException(teamTrainRoomContext.getResourceId());
		}
		teamRoomService.updateTeamRoom(teamTrainRoomContext, teamRoom);

		return getTeamRoom(teamTrainRoomContext.getResourceId());
	}

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<TeamRoomRsp> getTeamRoom(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		String userId = UserInfoContext.getUserContext().getUserId();

		TeamRoomData teamRoomData = teamRoomService.getTeamRoomRspByRoomId(resource_id);

		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(teamRoomData.getResourceId());

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

		TeamRoomRsp teamRoomRsp = BeanUtils.copy(teamRoomData, TeamRoomRsp.class);
		teamRoomRsp.setTrainingRoleData(getSelfRole(crRoles, userId));
		teamRoomRsp.setRolesData(crRoles);

		return new BaseRsp<TeamRoomRsp>(teamRoomRsp);
	}

	@ApiOperation("删除")
	@DeleteMapping()
	public BaseRsp<String> batchDelete(@RequestBody @Validated DeleteReq deleteReq) {
		teamRoomService.removeByIds(deleteReq.getIds());

		return new BaseRsp<>();
	}

	@ApiOperation("列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<TeamRoomRsp>> list(TeamRoomReq listReq) {
		ListQuery listQuery = BeanUtils.copy(listReq, ListQuery.class);

		String userId = UserInfoContext.getUserContext().getUserId();
		listQuery.setSort("-create_time");

		IPage<TeamRoomData> data = teamRoomService.list(listQuery);

		ListRsp<TeamRoomRsp> teamRoomRspListRsp = new ListRsp();

		List<TeamRoomRsp> teamRoomRspList = Lists.newArrayList();

		for (TeamRoomData teamRoomData : data.getRecords()) {
			RoleQuery roleQuery = new RoleQuery();
			roleQuery.setRoomId(teamRoomData.getResourceId());

			List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

			TeamRoomRsp teamRoomRsp = BeanUtils.copy(teamRoomData, TeamRoomRsp.class);
			teamRoomRsp.setTrainingRoleData(getSelfRoleUsers(crRoles, userId));
			teamRoomRsp.setRolesData(crRoles);

			teamRoomRspList.add(teamRoomRsp);
		}

		teamRoomRspListRsp.setList(teamRoomRspList);
		teamRoomRspListRsp.setTotal(data.getTotal());
		teamRoomRspListRsp.setLastPage(data.getPages());
		teamRoomRspListRsp.setCurrentPage(data.getCurrent());

		return new BaseRsp<ListRsp<TeamRoomRsp>>(teamRoomRspListRsp);
	}

	@ApiOperation("修改团队训练状态")
	@PostMapping("/startEndTeam")
	public BaseRsp<Integer>
			changeTrainingStatus(@RequestBody @Validated ResourceIdReq resourceIdReq) {
		int status = teamRoomService.changeTrainingStatus(resourceIdReq.getResourceId());

		return new BaseRsp<>(status);
	}

	@ApiOperation("查询团队训练状态")
	@GetMapping("/startEndTeam")
	public BaseRsp<Integer> getTrainingStatus(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		Integer status = teamRoomService.getTrainingStatus(resource_id);

		return new BaseRsp<>(status);
	}

	// 获取单个实例中自己的角色，只包括自己角色的基本信息
	private BaseRole getSelfRole(List<CrRole> crRoles, String userId) {
		BaseRole baseRole = new BaseRole();
		crRoles.forEach(crRole -> {
			if (crRole.getUserIds().contains(userId)) {
				baseRole.setResourceId(crRole.getResourceId());
				baseRole.setName(crRole.getName());

				baseRole.setDescription(crRole.getDescription());
				baseRole.setRoleGroup(crRole.getRoleGroup());
			}
		});

		return baseRole;
	}

	// 先获取实例列表中自己的角色，再获取所有的学员角色
	private BaseRole getSelfRoleUsers(List<CrRole> crRoles, String userId) {
		BaseRole baseRole = new BaseRole();
		List<RoleUsers> roleUsersList = Lists.newArrayList();
		crRoles.forEach(crRole -> {
			if (crRole.getUserIds().contains(userId)) {
				baseRole.setResourceId(crRole.getResourceId());
				baseRole.setName(crRole.getName());

				baseRole.setDescription(crRole.getDescription());
				baseRole.setRoleGroup(crRole.getRoleGroup());
			}
			if (crRole.getRoleGroup().intValue() != CrRole.RoleGroupCodeInteger.TEACHER
					.getValue()) {
				RoleUsers roleUsers = new RoleUsers();
				roleUsers.setRoleName(crRole.getName());
				roleUsers.setRoleGroup(crRole.getRoleGroup());
				roleUsers.setUsers(JsonUtil.toJsonObjectArray(crRole.getUserIds(), String.class));
				roleUsersList.add(roleUsers);
			}
		});

		baseRole.setRoleDataList(roleUsersList);

		return baseRole;
	}

}
