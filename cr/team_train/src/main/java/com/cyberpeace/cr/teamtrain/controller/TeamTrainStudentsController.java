package com.cyberpeace.cr.teamtrain.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.controller.req.SubmitMissionsReq;
import com.cyberpeace.cr.crbase.controller.rsp.MissionRsp;
import com.cyberpeace.cr.crbase.controller.rsp.SchemeListMissionRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionSchemeService;
import com.cyberpeace.cr.crbase.service.context.MissionContext;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.role.RoleUsers;
import com.cyberpeace.cr.teamtrain.controller.req.TeamRoomListReq;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamRoomRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.context.ListQuery;
import com.cyberpeace.cr.teamtrain.service.context.StudentsTimeLineData;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 */
@Api(tags = "学员团队训练")
@RestController
@RequestMapping("/cr/training_team/training_team_students")
public class TeamTrainStudentsController {

	@Autowired
	TeamRoomService teamRoomService;

	@Autowired
	CrRoleService crRoleService;

	@Autowired
	CrTaskService crTaskService;

	@Autowired
	BusinessTemplateService businessTemplateService;

	@Autowired
	MissionSchemeService missionSchemeService;

	@ApiOperation("查询")
	@GetMapping()
	public BaseRsp<TeamRoomRsp> getTeamRoom(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		String userId = UserInfoContext.getUserContext().getUserId();

		TeamRoomData teamRoomData = teamRoomService.getTeamRoomDataByRoomId(resource_id);

		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(teamRoomData.getResourceId());

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

		TeamRoomRsp teamRoomRsp = BeanUtils.copy(teamRoomData, TeamRoomRsp.class);
		teamRoomRsp.setTrainingRoleData(getSelfRole(crRoles, userId));
		teamRoomRsp.setRolesData(crRoles);
		teamRoomRsp
				.setPreSkill(JsonUtil.toJsonObjectArray(teamRoomData.getPreSkill(), String.class));
		teamRoomRsp.setTrainSkill(
				JsonUtil.toJsonObjectArray(teamRoomData.getTrainSkill(), String.class));
		return new BaseRsp<TeamRoomRsp>(teamRoomRsp);
	}

	@ApiOperation("列表查询")
	@GetMapping("/list")
	public BaseRsp<ListRsp<TeamRoomRsp>> list(@Validated TeamRoomListReq listReq) {
		ListQuery listQuery = BeanUtils.copy(listReq, ListQuery.class);

		String userId = UserInfoContext.getUserContext().getUserId();
		listQuery.setSort("-cr_team_room.create_time");

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

	/**
	 * 获取任务列表
	 * @return
	 */
	@ApiOperation("获取任务列表")
	@GetMapping("/get_mission_scheme")
	public BaseRsp<List<MissionSchemeObject<SchemeListMissionRsp>>> getMissionScheme(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		String userId = UserInfoContext.getUserContext().getUserId();

		// 获取角色中的任务内容
		List<MissionSchemeObject<MissionContext>> missionSchemeObjectList = crRoleService
				.getCrRoleMissionByRoomIdAndUserId(resource_id, userId);

		List<MissionSchemeObject<SchemeListMissionRsp>> missionSchemeList = missionSchemeObjectList
				.stream().map(missionSchemeObject -> {
					MissionSchemeObject<SchemeListMissionRsp> schemeListMissionRsp = new MissionSchemeObject<>();
					schemeListMissionRsp.setStage(missionSchemeObject.getStage());
					schemeListMissionRsp.setDynamic(missionSchemeObject.getDynamic());
					List<SchemeListMissionRsp> schemeListMissionRsps = BeanUtils.deepCopyList(
							missionSchemeObject.getMissions(), SchemeListMissionRsp.class);
					schemeListMissionRsp.setMissions(schemeListMissionRsps);

					return schemeListMissionRsp;
				}).collect(Collectors.toList());

		return new BaseRsp(missionSchemeList);
	}

	@ApiOperation("提交任务答案")
	@PostMapping("/submit_missions")
	public BaseRsp<String>
			changeTrainingStatus(@RequestBody @Validated SubmitMissionsReq submitMissionsReq) {
		crTaskService.submitMissions(submitMissionsReq.getResourceId(),
				submitMissionsReq.getMissionHash(), submitMissionsReq.getAnswer());

		return new BaseRsp<>();
	}

	@ApiOperation("获取任务详情")
	@GetMapping("/get_mission_detail")
	public BaseRsp<MissionRsp> getMissionDetail(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id,
			@ApiParam(name = "mission_id", value = "任务id", required = true) String mission_id) {
		String userId = UserInfoContext.getUserContext().getUserId();

		TaskContext taskContext = crTaskService.getByMissionAndUser(mission_id, userId);

		MissionRsp missionRsp = new MissionRsp();
		missionRsp.setResourceId(taskContext.getMission().getRoomId());
		missionRsp.setTitle(taskContext.getMission().getTitle());
		missionRsp.setOption(taskContext.getMission().getOption());
		missionRsp.setType(taskContext.getMission().getType());
		missionRsp.setCategory(taskContext.getMission().getCategoryId());
		missionRsp.setShowWriteup(taskContext.getMission().getShowWriteup());
		missionRsp.setWriteUp(taskContext.getMission().getWriteUp());

		missionRsp.setUserAnswer(taskContext.getUserAnswer());
		if (!taskContext.getMission().getShowWriteup()) {
			missionRsp.setWriteUp("");
		}

		return new BaseRsp<>(missionRsp);
	}

	/**
	 * 查询团队训练时间线
	 * @return
	 */
	@ApiOperation("查询团队训练时间线")
	@GetMapping("/get_time_line")
	public BaseRsp<StudentsTimeLineData> getTimeLine(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {

		StudentsTimeLineData data = teamRoomService.getStudentsTimeLine(resource_id);

		return new BaseRsp(data);
	}

	/** 获取单个实例中自己的角色信息，只包括自己角色的基本信息
	 * @param crRoles 
	 * @param userId
	 * @return
	 */
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

	/** 先获取实例列表中自己的角色，再获取所有的学员角色
	 * @param crRoles 
	 * @param userId
	 * @return
	 */
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
			if (crRole.getRoleGroup().intValue() != CrRole.TeamRoleGroupCode.TEACHER
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
