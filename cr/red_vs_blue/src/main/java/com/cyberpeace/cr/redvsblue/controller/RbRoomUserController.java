package com.cyberpeace.cr.redvsblue.controller;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.MissionFlagAttachmentQuery;
import com.cyberpeace.cr.crbase.controller.req.MissionFlagQuery;
import com.cyberpeace.cr.crbase.controller.req.RoomDeviceApplyReq;
import com.cyberpeace.cr.crbase.controller.req.SubmitMissionsReq;
import com.cyberpeace.cr.crbase.controller.rsp.CrLabelGroup;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.CrFlagTaskMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskLogMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.service.CrRoomDeviceApplyService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.CrTrainActionService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.mission.MissionsStatistic;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.redvsblue.controller.req.RbDeviceReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.CrTaskReject;
import com.cyberpeace.cr.redvsblue.controller.rsp.MissionInfoRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbDevice;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTimeManagement;
import com.cyberpeace.cr.redvsblue.service.RbDeviceService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbTimeManagementService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	private CrRoomDeviceApplyService crRoomDeviceApplyService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private CrTrainActionService crTrainActionService;

	@Autowired
	private CrTaskLogMapper crTaskLogMapper;

	@Autowired
	private CrTaskMapper crTaskMapper;

	@Autowired
	private CrFlagTaskMapper crFlagTaskMapper;

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
				if (evaluateStatus != CrTask.TaskStatus.CLOSE.getCode()) {
					evaluateStatus = CrTask.TaskStatus.RELEASED.getCode();
				}
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
			if(score == null){
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

			if (MissionBase.MissionType.FLAG.getCode().equals(missionInfoRsp.getType())){
				MissionFlagQuery missionFlagQuery = JSON.parseObject(missionInfoRsp.getAnswer(), MissionFlagQuery.class);
				if (MissionFlagQuery.FlagType.STATIC_FLAG.getCode().equals(missionFlagQuery.getFlagType())) {
					missionInfoRsp.setAnswer(missionFlagQuery.getFlagValue());
				}else {
					MissionFlagAttachmentQuery missionFlag = crFlagTaskMapper.selectFileByTask(taskContext.getResourceId());
					missionInfoRsp.setMissionFlagAttachmentQuery(missionFlag);
					missionInfoRsp.setAnswer(missionFlag.getFlagValue());
				}
			}
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
		RbRoom rbRoom = rbRoomService.getById(task.getRoomId());
		// 新增红蓝队员进
		// 添加用户是否为红蓝队员的校验
		if(null != rbRoom ){
			CrTaskLog crTaskLog = new CrTaskLog();
			crTaskLog.setActionCode(CrTaskLog.ActionCode.START_ANSWER.getCode());
			crTaskLog.setMissionId(task.getMissionId());
			crTaskLog.setRoomId(rbRoom.getResourceId());
			crTaskLog.setUserId(UserInfoContext.getUserContext().getUserId());
			crTaskLog.setTaskId(task.getResourceId());
			crTaskLogMapper.insert(crTaskLog);
		}
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

		crTaskService.submitMissions(submitMissionsReq.getResourceId(),
				submitMissionsReq.getMissionHash(), submitMissionsReq.getAnswer());

		TaskContext taskContext = crTaskService.getByMissionAndUser(
				submitMissionsReq.getMissionHash(),
				UserInfoContext.getUserContext().getUserId()
		);
		crTrainActionService.saveTeamAction(
				taskContext.getRoomId(),
				String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.SUBMIT_TASK), taskContext.getMission().getTitle()),
				CrTrainAction.TeamEvent.SUBMIT_TASK.getCode()
		);
		if (taskContext.getStatus().intValue() == CrTask.TaskStatus.SUCCESS.getCode()) {
			crTrainActionService.saveTeamAction(
					taskContext.getRoomId(),
					String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.FINISH_TASK), taskContext.getMission().getTitle()),
					CrTrainAction.TeamEvent.FINISH_TASK.getCode(),
					CrTrainAction.Type.FINISH.getCode()
			);
		}

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

	@ApiOperation("实装分配-设备审批申请")
	@PostMapping("/device/apply")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<Boolean> deviceApply(@RequestBody @Validated RoomDeviceApplyReq roomDeviceApplyReq) {
		RbRoom rbRoom = rbRoomService.getById(roomDeviceApplyReq.getRoomId());
		if (rbRoom == null) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}

		return new BaseRsp<>(crRoomDeviceApplyService.apply(
				rbRoom.getResourceId(),
				rbRoom.getName(),
				roomDeviceApplyReq.getDeviceId(),
				rbRoom.getLocalSceneId(),
				BusinessTemplate.BusinessType.RED_VS_BLUE.getCode(),
				null));
	}

	@ApiOperation("队伍动态-任务预览")
	@GetMapping("/action/receive_mission")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<Boolean> receiveMission(
			@ApiParam(value = "下发任务ID", required = true) @RequestParam("mission_id") String missionId) {
		TaskContext crTask = crTaskService.getByMissionAndUser(missionId, UserInfoContext.getUserContext().getUserId());
		Mission mission = missionService.getById(crTask.getMissionId());
		return new BaseRsp<>(
				crTrainActionService.saveTeamAction(
						crTask.getRoomId(),
						String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.RECEIVE_TASK), mission.getTitle()),
						CrTrainAction.TeamEvent.RECEIVE_TASK.getCode()
				));
	}

	@ApiOperation("队伍动态-接入靶机")
	@GetMapping("/action/access_terminal")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<Boolean> accessTerminal(
			@ApiParam(value = "训练实例id", required = true) @RequestParam("room_id") String roomId,
			@ApiParam(value = "场景节点id", required = true) @RequestParam("device_id") String deviceId) {

		Map<String, String> nodeMap = rbRoomService.missionEventDevice(roomId).stream().collect(Collectors.toMap(CrLabelGroup::getValue, CrLabelGroup::getLabel));

		return new BaseRsp<>(
				crTrainActionService.saveTeamAction(
						roomId,
						String.format(BusinessException.getMessageByLanguage(CrBusinessErrorCode.ACCESS_TERMINAL), nodeMap.get(deviceId)),
						CrTrainAction.TeamEvent.RECEIVE_TASK.getCode()
				));
	}

}
