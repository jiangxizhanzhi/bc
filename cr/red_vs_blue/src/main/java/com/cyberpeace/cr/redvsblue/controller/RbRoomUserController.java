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
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.redvsblue.controller.rsp.CrTaskReject;
import com.cyberpeace.cr.redvsblue.controller.rsp.MissionInfoRsp;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.context.mission.MissionsStatistic;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
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
	private CrTaskService crTaskService;

	@Autowired
	private RemoteUserService remoteUserService;

	@ApiOperation("获取红蓝实例运行状态")
	@GetMapping("/get_running_status")
	public BaseRsp<Map> getRunningStatus(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		Integer status = rbRoomService.getTrainingStatus(resource_id);

		Map map = Maps.newHashMap();
		map.put("time_management_status", status);

		return new BaseRsp<>(map);
	}

	/**
	 * 获取任务方案信息
	 *
	 * @return
	 */
	@ApiOperation("获取任务方案信息")
	@GetMapping("/get_mission_info")
	public BaseRsp<List<MissionInfoRsp>> getMissionInfo(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {

		String userId = UserInfoContext.getUserContext().getUserId();

		// 获取下发的任务内容
		List<TaskContext> tasks = crTaskService.getByRoomIdAndUser(resource_id, userId);
		List<MissionInfoRsp> missionInfoRsps = tasks.stream().map(taskContext -> {

			// 获取评估状态
			List<CrTaskReject> rejectLogs = Lists.newArrayList();

			List<CrTaskSubmit> taskSubmits = crTaskService
					.getOrderedSubmitsByTask(taskContext.getResourceId());

			// 获取评估人名字
			List<String> userIds = taskSubmits.stream().map(CrTaskSubmit::getEvaluatorId).distinct()
					.collect(Collectors.toList());
			Map<String, String> userInfoMap = remoteUserService.getIdNameMap(userIds);

			Integer evaluateStatus = taskContext.getStatus();

			// 未提交过
			if (taskSubmits == null) {
				evaluateStatus = CrTaskSubmit.EvaluateStatus.TASK_NOT_SUBMITTED.getCode();
			}
			// 已提交过，获取任务驳回记录
			else {

				for (CrTaskSubmit taskSubmit : taskSubmits) {
					if (taskSubmit.getEvaluateStatus()
							.equals(CrTaskSubmit.EvaluateStatus.REJECT.getCode())) {
						CrTaskReject crTaskReject = new CrTaskReject();

						crTaskReject.setRejectTime(taskSubmit.getUpdateTime());
						crTaskReject.setRejectReason(taskSubmit.getReason());
						crTaskReject.setReferee(userInfoMap
								.getOrDefault(taskSubmit.getEvaluatorId(), "new UserInfo()"));

						rejectLogs.add(crTaskReject);
					}
				}
			}

			MissionInfoRsp missionInfoRsp = BeanUtils.copy(taskContext.getMission(),
					MissionInfoRsp.class);

			if (!taskContext.getMission().getShowWriteup()) {
				missionInfoRsp.setWriteUp("");
			}

			missionInfoRsp.setMissionStatus(taskContext.getStatus());
			missionInfoRsp.setCategory(taskContext.getMission().getCategoryId());
			missionInfoRsp.setWriteUpShow(taskContext.getMission().getShowWriteup());
			missionInfoRsp.setUserAnswer(taskContext.getUserAnswer());

			if (CrTask.TaskStatus.RELEASED.getCode().equals(taskContext.getStatus())) {
				missionInfoRsp.setUserMissionStatus(
						MissionsStatistic.MissionStatisticalState.MISSION_NOT_STARTED.getCode());
			} else if (taskContext.isFinished()) {
				missionInfoRsp.setUserMissionStatus(
						MissionsStatistic.MissionStatisticalState.MISSION_COMPLETED.getCode());
			} else {
				missionInfoRsp.setUserMissionStatus(
						MissionsStatistic.MissionStatisticalState.MISSION_ONGOING.getCode());
			}

			missionInfoRsp.setEvaluateStatus(evaluateStatus);
			missionInfoRsp.setRejectLog(rejectLogs);

			return missionInfoRsp;
		}).collect(Collectors.toList());

		return new BaseRsp(missionInfoRsps);
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
