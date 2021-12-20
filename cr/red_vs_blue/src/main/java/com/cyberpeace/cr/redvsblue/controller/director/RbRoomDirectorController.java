package com.cyberpeace.cr.redvsblue.controller.director;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.ReleaseMissionReq;
import com.cyberpeace.cr.crbase.controller.req.WriteUpReq;
import com.cyberpeace.cr.crbase.controller.rsp.RoleMissionRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleContext;
import com.cyberpeace.cr.crbase.service.context.role.RoleData;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.redvsblue.controller.req.ExercisePaperReq;
import com.cyberpeace.cr.redvsblue.controller.req.MissionConfig;
import com.cyberpeace.cr.redvsblue.controller.req.RbMissionDeleteReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbRoomMissionReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.ExercisePaperRsp;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.context.CrRbRoleContext;
import com.cyberpeace.cr.redvsblue.service.context.GroupData;
import com.cyberpeace.cr.redvsblue.service.context.GroupRole;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "红蓝对抗导演")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/director/red_vs_blue/red_vs_blue_templates/")
public class RbRoomDirectorController {

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@ApiOperation("提交演习文书")
	@PostMapping("/add_exercise_paper")
	public BaseRsp<String>
			addExercisePaper(@RequestBody @Validated ExercisePaperReq exercisePaperReq) {
		RoleQuery roleQuery = new RoleQuery(exercisePaperReq.getResourceId(),
				exercisePaperReq.getGroupHash());

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);
		for (CrRole crRole : crRoles) {
			crRole.setExercisePaper(exercisePaperReq.getExercisePaper());
		}
		crRoleService.updateBatchById(crRoles);

		return new BaseRsp<>();
	}

	@ApiOperation("获取演习文书")
	@GetMapping("/get_mission_detail")
	public BaseRsp<ExercisePaperRsp>
			get_exercise_paper(@RequestBody @Validated ExercisePaperReq exercisePaperReq) {
		RoleQuery roleQuery = new RoleQuery(exercisePaperReq.getResourceId(),
				exercisePaperReq.getGroupHash());

		List<CrRole> crRoles = crRoleService.selectCrRoleByRoomId(roleQuery);
		ExercisePaperRsp exercisePaperRsp = new ExercisePaperRsp();
		if (crRoles.size() > 0) {
			exercisePaperRsp.setExercisePaper("");
		}
		return new BaseRsp(exercisePaperRsp);
	}

	/**
	 * 修改开放题解
	 * @return
	 */
	@ApiOperation("修改开放题解")
	@PostMapping("/update_show_write_up")
	public BaseRsp<String> updateShowWriteUp(@RequestBody @Validated WriteUpReq writeUpReq) {

		rbRoomService.checkDirectorPermission(writeUpReq.getRoomId(),
				UserInfoContext.getUserContext().getUserId());
		rbRoomService.checkRoomOpening(writeUpReq.getRoomId());

		missionService.updateShowWriteup(writeUpReq.getMissionResourceId(),
				writeUpReq.getWriteUpShow());

		return new BaseRsp<>();
	}

	/**
	 * 任务下发
	 * @return
	 */
	@ApiOperation("任务下发")
	@PostMapping("/missions")
	public BaseRsp<String>
			releaseMissions(@RequestBody @Validated ReleaseMissionReq releaseMissionReq) {

		CrRole crRole = crRoleService.getById(releaseMissionReq.getRoleResourceId());

		rbRoomService.checkDirectorPermission(crRole.getRoomId(),
				UserInfoContext.getUserContext().getUserId());
		rbRoomService.checkRoomOpening(crRole.getRoomId());

		if (crRole == null) {
			log.error("role does not exist！roleId=[{}]", releaseMissionReq.getRoleResourceId());
			throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);
		}

		missionService.release(releaseMissionReq.getMissionResourceId(),
				releaseMissionReq.getRoleResourceId(),
				UserInfoContext.getUserContext().getUserId());

		crTaskService.pushMissionWS(crRole.getRoomId(), MissionWsCode.RVB_MISSION_CODE);

		return new BaseRsp<>();
	}

	@ApiOperation("动态任务新增")
	@PostMapping("/mission_info_create")
	public BaseRsp<String>
			addMissionInfo(@RequestBody @Validated RbRoomMissionReq rbRoomMissionReq) {

		rbRoomService.checkRoomOpening(rbRoomMissionReq.getRoomId());

		Mission mission = BeanUtils.copy(rbRoomMissionReq, Mission.class);
		mission.setCategoryId(rbRoomMissionReq.getCategory());
		mission.setSkillPoints(JsonUtil.toJsonString(rbRoomMissionReq.getSkillPoints()));
		mission.setCategoryId(rbRoomMissionReq.getCategory());

		CrRole crRole = crRoleService.getById(rbRoomMissionReq.getRoleResourceId());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				missionService.save(mission);

				List<MissionStageScheme> missionStageSchemeList = JsonUtil
						.toJsonObjectArray(crRole.getMissionScheme(), MissionStageScheme.class);
				int index = 0;

				for (MissionStageScheme missionStageScheme : missionStageSchemeList) {
					List<String> missions = missionStageScheme.getMissions();

					if (index == rbRoomMissionReq.getMissionStage().intValue()) {
						missions.add(mission.getResourceId());
						missionStageScheme.setMissions(missions);
					}

				}
				crRole.setMissionScheme(JSONObject.toJSONString(missionStageSchemeList));
				crRoleService.save(crRole);
			}
		});

		return new BaseRsp<>();
	}

	@ApiOperation("任务删除")
	@PostMapping("/mission_delete")
	public BaseRsp<String>
			addMissionInfo(@RequestBody @Validated RbMissionDeleteReq rbMissionDeleteReq) {

		rbRoomService.checkRoomOpening(rbMissionDeleteReq.getRoomId());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				List<MissionConfig> missionConfigList = rbMissionDeleteReq.getMissionConfig();
				for (MissionConfig missionConfig : missionConfigList) {
					CrRole crRole = crRoleService.getById(missionConfig.getRoleResourceId());
					if (!crRole.isStudent()) {
						log.error("student role  does not exist！roleId=[{}] ",
								crRole.getResourceId());
						throw new BusinessException(CrBusinessErrorCode.ROLE_NOT_EXIST_ERROR);

					}
					List<String> deleteMissions = missionConfig.getMissions();

					if (crTaskService.getByMissions(rbMissionDeleteReq.getRoomId(), deleteMissions)
							.size() > 0) {
						log.error("mission is issued！missionIds=[{}] ",
								JSONObject.toJSONString(deleteMissions));
						throw new BusinessException(CrBusinessErrorCode.MISSION_ISSUED);
					}

					List<MissionStageScheme> missionStageSchemeList = JsonUtil
							.toJsonObjectArray(crRole.getMissionScheme(), MissionStageScheme.class);

					for (MissionStageScheme missionStageScheme : missionStageSchemeList) {

						List<String> missions = missionStageScheme.getMissions();
						for (int i = missions.size() - 1; i >= 0; i--) {
							String mission = missions.get(i);

							if (deleteMissions.contains(mission)) {
								missions.remove(mission);
							}
						}
						missionStageScheme.setMissions(missions);

					}

					crRole.setMissionScheme(JSONObject.toJSONString(missionStageSchemeList));
					crRoleService.save(crRole);
					missionService.removeByIds(deleteMissions);

				}

			}
		});

		return new BaseRsp<>();
	}

	@ApiOperation("查询角色")
	@GetMapping("/roles")
	public BaseRsp<CrRbRoleContext> getRoles(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		CrRbRoleContext crRbRoleContext = new CrRbRoleContext();
		List<GroupData> groups = Lists.newArrayList();
		// 遍历角色组
		for (Integer roleCode : CrRole.RB_STUDENT_ROLE_GROUPS) {

			Integer missionCount = 0;
			Integer peopleCount = 0;
			List<GroupRole> childRoleInfoList = Lists.newArrayList();
			String roleGroupName = EnumUtils.getEnumDescByCode(roleCode,
					CrRole.RbRoleGroupCode.class);

			CrRoleContext<MissionContext> crRoleContext = crRoleService.getRoles(resource_id,
					roleCode);

			crRbRoleContext.setMissionMapping(crRoleContext.getMissionMapping());
			crRbRoleContext.setShowWriteupMapping(crRoleContext.getShowWriteupMapping());

			CrRoleContext<RoleMissionRsp> crRoleContextRsp = BeanUtils.copy(crRoleContext,
					CrRoleContext.class);

			// 遍历每一个角色
			for (int i = 0; i < crRoleContextRsp.getRoles().size(); i++) {
				RoleData<RoleMissionRsp> roleData = crRoleContextRsp.getRoles().get(i);

				List<String> userIds = JsonUtil.toJsonObjectArray(roleData.getUserIds(),
						String.class);

				List<RoleMissionRsp> roleMissionRspList = Lists.newArrayList();

				// 遍历每一个阶段
				for (int j = 0; j < roleData.getMissionsList().size(); j++) {
					MissionSchemeObject<RoleMissionRsp> roleMissionObject = roleData
							.getMissionsList().get(j);
					// 遍历获取mission列表
					List<MissionContext> missions = crRoleContext.getRoles().get(i)
							.getMissionsList().get(j).getMissions();

					List<RoleMissionRsp> roleStageMissionRspList = missions.stream()
							.map(roleMission -> {

								// 获取mission的用户列表
								List<UserData> userDatas = crRoleService
										.getUserDatas(roleData.getUserIds());
								List<RbUserData> rbUserDatas = userDatas.stream().map(userData -> {
									RbUserData rbUserData = BeanUtils.copy(userData,
											RbUserData.class);

									rbUserData.setGroupName(roleGroupName);
									rbUserData.setRoleName(roleData.getName());

									return rbUserData;
								}).collect(Collectors.toList());

								RoleMissionRsp roleMissionRsp = BeanUtils.copy(roleMission,
										RoleMissionRsp.class);
								roleMissionRsp.setCategory(roleMission.getCategoryId());
								roleMissionRsp.setMissionStatus(roleMission.getState());
								roleMissionRsp.setDeliveryDetails(rbUserDatas);

								return roleMissionRsp;
							}).collect(Collectors.toList());

					roleMissionRspList.addAll(roleStageMissionRspList);
					roleMissionObject.setMissions(roleMissionRspList);

				}

				GroupRole groupRole = new GroupRole();
				groupRole.setName(roleData.getName());
				groupRole.setMissions(roleMissionRspList);
				groupRole.setPeopleNumber(userIds.size());
				groupRole.setMissionNumber(roleMissionRspList.size());
				groupRole.setRoleResourceId(roleData.getResourceId());

				childRoleInfoList.add(groupRole);

				peopleCount += userIds.size();
				missionCount += roleMissionRspList.size();
			}

			GroupData groupData = new GroupData();

			groupData.setGroup(roleGroupName);
			groupData.setGroupHash(roleCode);
			groupData.setRoles(childRoleInfoList);
			groupData.setGroupNumber(crRoleContextRsp.getRoles().size());
			groupData.setPeopleNumber(peopleCount);
			groupData.setMissionNumber(missionCount);

			groups.add(groupData);

		}

		crRbRoleContext.setGroups(groups);

		return new BaseRsp(crRbRoleContext);
	}
}
