package com.cyberpeace.cr.redvsblue.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.SubmitMissionsReq;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.TaskMatrix;
import com.cyberpeace.cr.crbase.service.context.mark.MarkAddReq;
import com.cyberpeace.cr.crbase.service.context.mission.MissionsStatistic;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineData;
import com.cyberpeace.cr.crbase.service.context.screen.ScreenRecordUserRole;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.redvsblue.controller.req.RbDeviceReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.CrTaskReject;
import com.cyberpeace.cr.redvsblue.controller.rsp.MissionInfoRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbDevice;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTimeManagement;
import com.cyberpeace.cr.redvsblue.service.RbDeviceService;
import com.cyberpeace.cr.redvsblue.service.RbRefereeService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbTimeManagementService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "红蓝对抗学员")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/cr/red_vs_blue/user/")
public class RbRoomUserController {

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private RbDeviceService rbDeviceService;

	@Autowired
	private RbTimeManagementService rbTimeManagementService;

	@Autowired
	private CrCommonService crCommonService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private ScreenService screenService;

	@Autowired
	private CrBadgeService crBadgeService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private RbRefereeService rbRefereeService;

	@ApiOperation("获取红蓝实例运行状态")
	@GetMapping("/get_running_status")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
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
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
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
			if (taskSubmits == null || taskSubmits.size() == 0) {
				evaluateStatus = CrTask.TaskStatus.RELEASED.getCode();
			}
			// 已提交过，获取任务驳回记录
			else {

				for (CrTaskSubmit taskSubmit : taskSubmits) {
					if (taskSubmit.getEvaluateStatus()
							.equals(CrTaskSubmit.EvaluateStatus.REJECT.getCode())) {
						CrTaskReject crTaskReject = new CrTaskReject();

						crTaskReject.setRejectTime(taskSubmit.getUpdateTime());
						crTaskReject.setRejectReason(taskSubmit.getReason());
						crTaskReject.setReferee(userInfoMap.get(taskSubmit.getEvaluatorId()));

						rejectLogs.add(crTaskReject);
					}
				}
			}

			MissionInfoRsp missionInfoRsp = BeanUtils.copy(taskContext.getMission(),
					MissionInfoRsp.class);

			if (!taskContext.getMission().getShowWriteup()) {
				missionInfoRsp.setWriteUp("");
			}

			missionInfoRsp.setMissionStatus(0);
			missionInfoRsp.setCategory(taskContext.getMission().getCategoryId());
			missionInfoRsp.setWriteUpShow(taskContext.getMission().getShowWriteup());
			missionInfoRsp.setUserAnswer(taskContext.getUserAnswer());
			// 未答题，显示得分为0分
			Integer score = taskContext.getScore();
			if (score == null) {
				score = 0;
			}
			missionInfoRsp.setScore(score);

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
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<String>
			beginMission(@RequestBody @Validated SubmitMissionsReq submitMissionsReq) {
		String userId = UserInfoContext.getUserContext().getUserId();

		rbRoomService.checkStudentPermission(submitMissionsReq.getResourceId(), userId);
		rbRoomService.checkRoomOpening(submitMissionsReq.getResourceId());

		CrTask task = crTaskService.getByUserIdAndMissionId(userId,
				submitMissionsReq.getMissionHash());

		if (null == task) {
			log.error("user‘s mission does not release！userId=[{}] missionId=[{}]", userId,
					submitMissionsReq.getMissionHash());
			throw new BusinessException(CrBusinessErrorCode.TASK_NOT_EXIST_ERROR);
		}

		crTaskService.startTask(task);

		return new BaseRsp<>();
	}

	@ApiOperation("提交任务答案")
	@PostMapping("/submit_missions")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<String>
			submitMissions(@RequestBody @Validated SubmitMissionsReq submitMissionsReq) {
		CrCommonValidator.checkStringExist(submitMissionsReq.getAnswer(),
				CrBusinessErrorCode.ANSWER_NOT_NULLABLE);

		String userId = UserInfoContext.getUserContext().getUserId();

		rbRoomService.checkStudentPermission(submitMissionsReq.getResourceId(), userId);
		rbRoomService.checkRoomOpening(submitMissionsReq.getResourceId());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				crTaskService.submitMissions(submitMissionsReq.getResourceId(),
						submitMissionsReq.getMissionHash(), submitMissionsReq.getAnswer());
				rbRefereeService.notifySubmission(submitMissionsReq.getMissionHash());
			}
		});

		crTaskService.pushMissionWS(submitMissionsReq.getResourceId(),
				MissionWsCode.RVB_MISSION_CODE);

		return new BaseRsp<>();
	}

	@ApiOperation("新增占用主机")
	@PostMapping("/device")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<Boolean> createDevice(@RequestBody @Valid RbDeviceReq req) {
		String roomId = req.getRoomId();
		String deviceId = req.getDeviceId();
		String userId = UserInfoContext.getUserContext().getUserId();

		// check permission
		rbRoomService.checkStudentPermission(roomId, userId);
		// check training status
		RbTimeManagement management = rbTimeManagementService.getByRoomId(roomId);
		if (management == null || !management.isRunning()) {
			log.error("training is not running, room_id is {}", roomId);
			throw new BusinessException(CrBusinessErrorCode.TRAINING_NOT_RUNNING);
		}
		// check if machine occupied
		RbDevice deviceExist = rbDeviceService.getByRoomIdAndDevice(roomId, deviceId);
		if (deviceExist != null) {
			throw new BusinessException(CrBusinessErrorCode.MACHINE_OCCUPIED);
		}
		// create rb_device
		Boolean ret = rbDeviceService.create(roomId, deviceId, userId);

		return new BaseRsp<>(ret);
	}

	@ApiOperation("解除主机占用")
	@DeleteMapping("/device")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<Boolean> deleteDevice(@RequestBody @Valid RbDeviceReq req) {
		String roomId = req.getRoomId();
		String deviceId = req.getDeviceId();
		String userId = UserInfoContext.getUserContext().getUserId();
		Boolean ret = rbDeviceService.removeByRoomIdAndUserIdAndDeviceId(roomId, userId, deviceId);
		return new BaseRsp<>(ret);
	}

	@ApiOperation("红蓝对抗-任务矩阵&&用户维度复盘")
	@GetMapping("/task/matrix")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TaskMatrix> getTaskMatrix(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "user_id", value = "用户id", required = true) String user_id) {

		if (StringUtils.isBlank(resource_id)) {
			log.error("resource_id_missing");
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		if (StringUtils.isBlank(user_id)) {
			user_id = UserInfoContext.getUserContext().getUserId();
			// 查看自己的任务矩阵之后去掉任务矩阵红点标记
			crBadgeService.removeBadge(resource_id, CrBadge.BadgeType.MISSION_RELEASE.getCode(), user_id);
		}
		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(resource_id, user_id);
		TaskMatrix rbTaskMatrix = crTaskService.getTaskMatrixExcludeTimeCost(resource_id,user_id);
		rbTaskMatrix.setTimeCost(rbTimeManagementService.getTimeCost(resource_id));
		rbTaskMatrix.initTaskListStage(crRole);
		return new BaseRsp<>(rbTaskMatrix);
	}


	@ApiOperation("红蓝对抗-学员任务矩阵-是否显示红点标记")
	@GetMapping("/task/matrix/badge")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Boolean> ifShowMissionMatrixBadge(
			@ApiParam(name = "resource_id", value = "红蓝对抗训练id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
		Boolean res = crBadgeService.ifExist(resource_id,
				CrBadge.BadgeType.MISSION_RELEASE.getCode(),
				UserInfoContext.getUserContext().getUserId());
		return new BaseRsp<>(res);
	}

	@ApiOperation("红蓝对抗-复盘设备")
	@GetMapping("/task/replayUserTimeInfo")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<ScreenRecordUserRole>> replyUserTimeInfo(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "node_id", value = "设备id", required = true) String node_id) {

		if (StringUtils.isBlank(resource_id)) {
			log.error("resource_id_missing");
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		List<ScreenRecordUserRole> resList= screenService.getReplayUserTimeInfo(resource_id,node_id);
		return new BaseRsp<>(resList);
	}

	@ApiOperation("新增标记")
	@PostMapping("/mark")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp createMark(@RequestBody @Valid MarkAddReq req){
		crCommonService.addMark(req, BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
		return new BaseRsp();
	}

	/**
	 * 查询红蓝训练学员时间轴
	 * @return
	 */
	@ApiOperation("红蓝对抗学员时间轴")
	@GetMapping("/get_time_line")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TimeLineData> getTimeLine(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		TimeLineData data = rbRoomService.getStudentsTimeLine(resource_id);
		return new BaseRsp(data);
	}
}
