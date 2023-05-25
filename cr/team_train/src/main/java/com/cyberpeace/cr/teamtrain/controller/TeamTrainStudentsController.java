package com.cyberpeace.cr.teamtrain.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.TaskMatrix;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.ListQuery;
import com.cyberpeace.cr.crbase.controller.req.SubmitMissionsReq;
import com.cyberpeace.cr.crbase.controller.rsp.MissionRsp;
import com.cyberpeace.cr.crbase.controller.rsp.SchemeListMissionRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.MissionContext;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.role.BaseRole;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.role.RoleUsers;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.teamtrain.controller.req.TeamRoomListReq;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamRoomRoleRes;
import com.cyberpeace.cr.teamtrain.controller.rsp.TeamRoomRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.context.StudentsTimeLineData;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteTopoService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 */
@Slf4j
@Api(tags = "学员团队训练")
@RestController
@RequestMapping("/cr/training_team/training_team_students")
public class TeamTrainStudentsController {

	@Autowired
	private TeamRoomService teamRoomService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private TrainRecordService trainRecordService;
	@ApiOperation("查询")
	@GetMapping()
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TeamRoomRsp> getTeamRoom(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		String userId = UserInfoContext.getUserContext().getUserId();
		crRoleService.checkPermission(resource_id, UserInfoContext.getUserContext().getUserId());

		TeamRoomData teamRoomData = teamRoomService.getTeamRoomDataByRoomId(resource_id);

		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(teamRoomData.getResourceId());

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

		TeamRoomRsp teamRoomRsp = BeanUtils.copy(teamRoomData, TeamRoomRsp.class);
		teamRoomRsp.setTrainingRoleData(getSelfRole(crRoles, userId));
		teamRoomRsp
				.setPreSkill(JsonUtil.toJsonObjectArray(teamRoomData.getPreSkill(), String.class));
		teamRoomRsp.setTrainSkill(
				JsonUtil.toJsonObjectArray(teamRoomData.getTrainSkill(), String.class));
		return new BaseRsp<TeamRoomRsp>(teamRoomRsp);
	}

	@ApiOperation("列表查询")
	@GetMapping("/list")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<ListRsp<TeamRoomRsp>> list(@Validated TeamRoomListReq listReq) {
		ListQuery listQuery = BeanUtils.copy(listReq, ListQuery.class);
		listQuery.setPer_page(listReq.getPerPage());
		String userId = UserInfoContext.getUserContext().getUserId();
		// 默认时间排序
		listQuery.setSort("-cr_team_room.create_time");

		IPage<TeamRoomData> data = teamRoomService.list(listQuery);

		// 训练场景拓扑节点数量获取
		Map<String, TopoDigest> topologyNodesStats = remoteTopoService.batchGetTopoDigest(
				data.getRecords().stream().map(rbRoomData -> rbRoomData.getCopyedTopoId())
						.collect(Collectors.toList()));
		ListRsp<TeamRoomRsp> teamRoomRspListRsp = new ListRsp();

		List<TeamRoomRsp> teamRoomRspList = Lists.newArrayList();

		// attack engage relation
		List<String> businessTemplateIds = data.getRecords().stream()
				.map(TeamRoomData::getBusinessTemplateId).collect(Collectors.toList());
		List<String> attackRelatedIds = businessTemplateService
				.getAttackRelatedIds(businessTemplateIds);
		List<String> engageRelatedIds = businessTemplateService
				.getEngageRelatedIds(businessTemplateIds);

		for (TeamRoomData teamRoomData : data.getRecords()) {
			RoleQuery roleQuery = new RoleQuery();
			roleQuery.setRoomId(teamRoomData.getResourceId());

			List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);

			List<TeamRoomRoleRes> rolesData = Lists.newArrayList();
			crRoles.forEach(crRole -> {
				TeamRoomRoleRes teamRoomRoleRes = BeanUtils.copy(crRole, TeamRoomRoleRes.class);
				teamRoomRoleRes
						.setUserIds(crRoleService.getUserNamesByRoleUsers(crRole.getUserIdList()));
			});

			TeamRoomRsp teamRoomRsp = BeanUtils.copy(teamRoomData, TeamRoomRsp.class);
			teamRoomRsp.setTrainingRoleData(getSelfRoleUsers(crRoles, userId));
			teamRoomRsp.setRolesData(rolesData);
			teamRoomRsp.setNodesCount(
					topologyNodesStats.get(teamRoomData.getCopyedTopoId()).getNodeCounts());
			teamRoomRsp.setResourceOccupiedNums(
					topologyNodesStats.get(teamRoomData.getCopyedTopoId()).getResourceOccupiedNums());
			teamRoomRsp.setPreSkill(
					JsonUtil.toJsonObjectArray(teamRoomData.getPreSkill(), String.class));
			teamRoomRsp.setTrainSkill(
					JsonUtil.toJsonObjectArray(teamRoomData.getTrainSkill(), String.class));
			// set attack engage relation
			teamRoomRsp.setIsAttackRelated(
					attackRelatedIds.contains(teamRoomRsp.getBusinessTemplateId()));
			teamRoomRsp.setIsEngageRelated(
					engageRelatedIds.contains(teamRoomRsp.getBusinessTemplateId()));

			teamRoomRspList.add(teamRoomRsp);
		}

		teamRoomRspListRsp.setList(teamRoomRspList);
		teamRoomRspListRsp.setTotal(data.getTotal());
		teamRoomRspListRsp.setLastPage(data.getPages());
		teamRoomRspListRsp.setCurrentPage(data.getCurrent());

		return new BaseRsp(teamRoomRspListRsp);
	}

	/**
	 * 获取任务列表
	 * @return
	 */
	@ApiOperation("获取任务列表")
	@GetMapping("/get_mission_scheme")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
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
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<String>
			changeTrainingStatus(@RequestBody @Validated SubmitMissionsReq submitMissionsReq) {
		CrCommonValidator.checkStringExist(submitMissionsReq.getAnswer(),
				CrBusinessErrorCode.ANSWER_NOT_NULLABLE);

		teamRoomService.checkRoomOpening(submitMissionsReq.getResourceId());
		String userId = UserInfoContext.getUserContext().getUserId();
		teamRoomService.checkStudentPermission(submitMissionsReq.getResourceId(), userId);

		crTaskService.submitMissions(submitMissionsReq.getResourceId(),
				submitMissionsReq.getMissionHash(), submitMissionsReq.getAnswer());

		crTaskService.pushMissionWS(submitMissionsReq.getResourceId(),
				MissionWsCode.TEAM_MISSION_CODE);

		return new BaseRsp<>();
	}

	@ApiOperation("获取任务详情")
	@GetMapping("/get_mission_detail")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
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
		missionRsp.setCategory(
				missionService.getCategoryById(taskContext.getMission().getCategoryId()).getName());
		missionRsp.setShowWriteup(taskContext.getMission().getShowWriteup());
		missionRsp.setWriteUp(taskContext.getMission().getWriteUp());
		missionRsp.setDescription(taskContext.getMission().getDescription());

		missionRsp.setUserAnswer(taskContext.getUserAnswer());
		if (!taskContext.getMission().getShowWriteup()) {
			missionRsp.setWriteUp("");
		}

		return new BaseRsp(missionRsp);
	}

	/**
	 * 查询团队训练时间线
	 * @return
	 */
	@ApiOperation("查询团队训练时间线")
	@GetMapping("/get_time_line")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
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
			if (crRole.containsUser(userId)) {
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
			if (crRole.containsUser(userId)) {
				baseRole.setResourceId(crRole.getResourceId());
				baseRole.setName(crRole.getName());

				baseRole.setDescription(crRole.getDescription());
				baseRole.setRoleGroup(crRole.getRoleGroup());
			}
			if (crRole.isTeamStudent()) {
				RoleUsers roleUsers = new RoleUsers();
				roleUsers.setRoleName(crRole.getName());
				roleUsers.setRoleGroup(crRole.getRoleGroup());
				roleUsers.setUsers(crRole.getUserIdList());
				roleUsersList.add(roleUsers);
			}
		});

		baseRole.setRoleDataList(roleUsersList);

		return baseRole;
	}


	@ApiOperation("团队对抗-学员任务矩阵")
	@GetMapping("/task/matrix")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TaskMatrix> getTaskMatrix(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {

		if (StringUtils.isBlank(resource_id)) {
			log.error("resource_id_missing");
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}

		TaskMatrix rbTaskMatrix = crTaskService.getTaskMatrixExcludeTimeCost(resource_id);
		rbTaskMatrix.setTimeCost(trainRecordService.getTimeCost(resource_id));
		return new BaseRsp<>(rbTaskMatrix);
	}
}
