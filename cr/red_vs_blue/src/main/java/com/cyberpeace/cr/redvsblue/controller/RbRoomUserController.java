package com.cyberpeace.cr.redvsblue.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.SubmitMissionsReq;
import com.cyberpeace.cr.crbase.controller.rsp.SchemeListMissionRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.MissionContext;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.context.mission.MissionsStatistic;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "红蓝对抗学员")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/red_vs_blue/user/")
public class RbRoomUserController {

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private RemoteUserService remoteUserService;

	@ApiOperation("获取红蓝实例运行状态")
	@GetMapping("/start_end_team")
	public BaseRsp<Map> getTrainingStatus(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		Integer status = rbRoomService.getTrainingStatus(resource_id);
		Map map = Maps.newHashMap();
		map.put("time_management_status", status);

		return new BaseRsp<>(map);
	}

	/**
	 * 获取任务方案信息
	 * @return
	 */
	@ApiOperation("获取任务方案信息")
	@GetMapping("/get_mission_info")
	public BaseRsp<List<MissionsStatistic>> getMissionInfo(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {

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

	@ApiOperation("开始答题")
	@PostMapping("/begin_mission")
	public BaseRsp<String>
			beginMission(@RequestBody @Validated SubmitMissionsReq submitMissionsReq) {
		String userId = UserInfoContext.getUserContext().getUserId();

		rbRoomService.checkStudentPermission(submitMissionsReq.getResourceId(), userId);
		rbRoomService.checkRoomOpening(submitMissionsReq.getResourceId());

		CrTask task = crTaskService.getByUserIdAndMissionId(userId,
				submitMissionsReq.getMissionHash());

		crTaskService.startTask(task);

		return new BaseRsp<>();
	}

	@ApiOperation("提交任务答案")
	@PostMapping("/submit_missions")
	public BaseRsp<String>
			changeTrainingStatus(@RequestBody @Validated SubmitMissionsReq submitMissionsReq) {
		String userId = UserInfoContext.getUserContext().getUserId();

		rbRoomService.checkStudentPermission(submitMissionsReq.getResourceId(), userId);
		rbRoomService.checkRoomOpening(submitMissionsReq.getResourceId());

		crTaskService.submitMissions(submitMissionsReq.getResourceId(),
				submitMissionsReq.getMissionHash(), submitMissionsReq.getAnswer());

		crTaskService.pushMissionWS(submitMissionsReq.getResourceId(),
				MissionWsCode.RVB_MISSION_CODE);

		return new BaseRsp<>();
	}

}
