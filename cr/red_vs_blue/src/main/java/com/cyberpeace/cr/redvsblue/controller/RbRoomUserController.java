package com.cyberpeace.cr.redvsblue.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
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
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.enums.RbRoleGroup;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.SubmitMissionsReq;
import com.cyberpeace.cr.crbase.dao.entity.CrBadge;
import com.cyberpeace.cr.crbase.dao.entity.CrScore;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.TaskMatrix;
import com.cyberpeace.cr.crbase.service.context.mark.MarkAddReq;
import com.cyberpeace.cr.crbase.service.context.mission.MissionsStatistic;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineData;
import com.cyberpeace.cr.crbase.service.context.screen.ScreenRecordUserRole;
import com.cyberpeace.cr.crbase.service.context.task.CrTaskRejectRsp;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.redvsblue.controller.req.RbDeviceReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.CrTaskReject;
import com.cyberpeace.cr.redvsblue.controller.rsp.MissionInfoRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbDevice;
import com.cyberpeace.cr.redvsblue.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.entity.RbTimeManagement;
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

	@Autowired
	private MissionService missionService;

	@Autowired
	private CrScoreService crScoreService;

	private static final String RESOURCE_ID_MISSING_LOG = "resource id missing";

	@ApiOperation("获取红蓝实例运行状态")
	@GetMapping("/get_running_status")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<Map<String, Integer>> getRunningStatus(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId) {
		Integer status = rbRoomService.getTrainingStatus(resourceId);

		Map<String, Integer> map = Maps.newHashMap();
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
			@ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId) {

		String userId = UserInfoContext.getUserContext().getUserId();

		// 获取下发的任务内容
		List<TaskContext> tasks = crTaskService.getByRoomIdAndUser(resourceId, userId);
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
			if (taskSubmits == null || taskSubmits.isEmpty()) {
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

			if (Boolean.FALSE.equals(taskContext.getMission().getShowWriteup())) {
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

		return new BaseRsp<>(missionInfoRsps);
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
						submitMissionsReq.getMissionHash(), submitMissionsReq.getAnswer(),
						submitMissionsReq.getRemark());
				rbRefereeService.notifySubmission(submitMissionsReq.getMissionHash());
			}
		});
		// 态势导调动效推送
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

	@ApiOperation("红蓝对抗-任务矩阵")
	@GetMapping("/task/matrix")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<TaskMatrix> getTaskMatrix(
			@ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId,
			@ApiParam(name = "user_id", value = "用户id", required = true) @RequestParam(
					value = "user_id", required = true) String userId) {

		if (StringUtils.isBlank(resourceId)) {
			log.error(RESOURCE_ID_MISSING_LOG);
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		if (StringUtils.isBlank(userId)) {
			userId = UserInfoContext.getUserContext().getUserId();
			// 查看自己的任务矩阵之后去掉任务矩阵红点标记
			crBadgeService.removeBadge(resourceId, CrBadge.BadgeType.MISSION_RELEASE.getCode(),
					userId);
		}
		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(resourceId, userId);
		TaskMatrix rbTaskMatrix = crTaskService.getTaskMatrixExcludeTimeCost(resourceId, userId);
		CrScore crScore = crScoreService.getByRoomIdAndUser(resourceId, userId);
		rbTaskMatrix.setFinalScore(crScore == null ? 0 : crScore.getFinalScore());
		rbTaskMatrix.setTime(rbTimeManagementService.getTimeCostLong(resourceId));
		rbTaskMatrix.initTaskListStage(crRole, TaskMatrix.SORT.MATRIX.getDesc());
		return new BaseRsp<>(rbTaskMatrix);
	}

	@ApiOperation("红蓝对抗-用户维度复盘")
	@GetMapping("/task/replayUserTaskInfo")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<TaskMatrix> replayUserTaskInfo(
			@ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId,
			@ApiParam(name = "user_id", value = "用户id", required = true) @RequestParam(
					value = "user_id", required = true) String userId) {

		if (StringUtils.isBlank(resourceId)) {
			log.error(RESOURCE_ID_MISSING_LOG);
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		if (StringUtils.isBlank(userId)) {
			userId = UserInfoContext.getUserContext().getUserId();
		}
		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(resourceId, userId);
		TaskMatrix rbTaskMatrix = crTaskService.getTaskMatrixExcludeTimeCost(resourceId, userId);
		CrScore crScore = crScoreService.getByRoomIdAndUser(resourceId, userId);
		rbTaskMatrix.setFinalScore(crScore == null ? 0 : crScore.getFinalScore());
		rbTaskMatrix.initTaskListStage(crRole, TaskMatrix.SORT.REPLAY.getDesc());
		return new BaseRsp<>(rbTaskMatrix);
	}

	@ApiOperation("红蓝对抗-学员任务矩阵-是否显示红点标记")
	@GetMapping("/task/matrix/badge")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<Boolean> ifShowMissionMatrixBadge(
			@ApiParam(name = "resource_id", value = "红蓝对抗训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId) {
		CrCommonValidator.validateId(resourceId);
		Boolean res = crBadgeService.ifExist(resourceId,
				CrBadge.BadgeType.MISSION_RELEASE.getCode(),
				UserInfoContext.getUserContext().getUserId());
		return new BaseRsp<>(res);
	}

	@ApiOperation("任务矩阵红点点击取消")
	@GetMapping("/matrix/badge/click")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<Boolean> clickEvaluationBadge(
			@ApiParam(name = "resource_id", value = "红蓝对抗训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId) {
		CrCommonValidator.validateId(resourceId);
		crBadgeService.removeBadge(resourceId, CrBadge.BadgeType.MISSION_RELEASE.getCode(),
				UserInfoContext.getUserContext().getUserId());
		return new BaseRsp<>();
	}

	@ApiOperation("红蓝对抗-复盘设备")
	@GetMapping("/task/replayUserTimeInfo")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<ScreenRecordUserRole>> replyUserTimeInfo(
			@ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId,
			@ApiParam(name = "node_id", value = "设备id", required = true) @RequestParam(
					value = "node_id", required = true) String nodeId) {

		if (StringUtils.isBlank(resourceId)) {
			log.error(RESOURCE_ID_MISSING_LOG);
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		RbRoom room = rbRoomService.getById(resourceId);
		if (!room.isEnded()) {
			log.error("rb room [{}] not ended", room.getResourceId());
			throw new BusinessException(CrBusinessErrorCode.PLZ_REPLAY_AFTER_TRAINING_ENDS);
		}
		List<ScreenRecordUserRole> resList = screenService.getReplayUserTimeInfo(resourceId,
				room.getReplaySceneId(), nodeId,
				Lists.newArrayList(RbRoleGroup.RED.getCode(), RbRoleGroup.BLUE.getCode()));

		// 得到场景真实结束时间
		Date actualEndTime = rbTimeManagementService.getActualEndTime(resourceId);
		// 过滤出结束时间之前的数据
		resList = screenService.filterBeforeActualEndTime(resList, actualEndTime);

		return new BaseRsp<>(resList);
	}

	@ApiOperation("新增标记")
	@PostMapping("/mark")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<Boolean> createMark(@RequestBody @Valid MarkAddReq req) {
		crCommonService.addMark(req, BusinessType.RED_VS_BLUE.getCode());
		return new BaseRsp<>();
	}

	/**
	 * 查询红蓝训练学员时间轴
	 * @return
	 */
	@ApiOperation("红蓝对抗学员时间轴")
	@GetMapping("/get_time_line")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<TimeLineData> getTimeLine(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId) {
		TimeLineData data = rbRoomService.getStudentsTimeLine(resourceId);
		return new BaseRsp<>(data);
	}

	@ApiOperation("获取任务详情")
	@GetMapping("/get_mission_detail")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<MissionInfoRsp> getMissionDetail(
			@ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId,
			@ApiParam(name = "mission_id", value = "任务id", required = true) @RequestParam(
					value = "mission_id", required = true) String missionId) {
		String userId = UserInfoContext.getUserContext().getUserId();

		TaskContext taskContext = crTaskService.getByMissionAndUser(missionId, userId);

		MissionInfoRsp missionRsp = new MissionInfoRsp();
		missionRsp.setResourceId(taskContext.getMission().getRoomId());
		missionRsp.setTitle(taskContext.getMission().getTitle());
		missionRsp.setOption(taskContext.getMission().getOption());
		missionRsp.setType(taskContext.getMission().getType());
		missionRsp.setCategory(
				missionService.getCategoryById(taskContext.getMission().getCategoryId()).getName());
		missionRsp.setWriteUpShow(taskContext.getMission().getShowWriteup());
		missionRsp.setWriteUp(taskContext.getMission().getWriteUp());
		missionRsp.setDescription(taskContext.getMission().getDescription());
		missionRsp.setUserAnswer(taskContext.getUserAnswer());
		missionRsp.setRemark(taskContext.getRemark());
		if (Boolean.FALSE.equals(taskContext.getMission().getShowWriteup())) {
			missionRsp.setWriteUp("");
		}
		List<CrTaskRejectRsp> rejectLogs = crTaskService.getTaskRejctsByTaskId(resourceId);
		missionRsp.setRejectLog(rejectLogs);
		missionRsp.setEvaluateStatus(
				CollectionUtils.isEmpty(rejectLogs) ? CrTask.TaskStatus.RELEASED.getCode()
						: taskContext.getStatus());
		missionRsp.initUserMissionStatus(taskContext);
		return new BaseRsp<>(missionRsp);
	}
}
