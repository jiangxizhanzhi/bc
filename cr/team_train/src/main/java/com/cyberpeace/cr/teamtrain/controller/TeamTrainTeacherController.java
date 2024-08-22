package com.cyberpeace.cr.teamtrain.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.rsp.PlaybackOptionRsp;
import com.cyberpeace.cr.crbase.controller.rsp.constants.MissionCategoryRsp;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListReq;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListRsp;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationMissionRsp;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineData;
import com.cyberpeace.cr.crbase.service.context.scene.MonitorSession;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;
import com.cyberpeace.cr.crbase.service.context.statistics.TaskStatisticsInfo;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.teamtrain.controller.req.TeamRoomMissionReq;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamSceneService;
import com.cyberpeace.cr.teamtrain.service.TeamTeacherService;
import com.cyberpeace.cs.service.context.BadgeBo;
import com.cyberpeace.cs.service.context.IdNameType;
import com.cyberpeace.iov.service.VehicleFrameService;
import com.cyberpeace.iov.service.bo.dut.AreaAndCount;
import com.cyberpeace.iov.service.bo.frame.FrameTopoItem;
import com.cyberpeace.situation.model.SituationTopo;
import com.cyberpeace.situation.service.SituationHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "团队训练实例-教员")
@Slf4j
@RestController
@RequestMapping("/cr/training_team/training_team_teacher")
public class TeamTrainTeacherController {

	@Autowired
	CrCommonService crCommonService;

	@Autowired
	private TeamRoomService teamRoomService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private TeamTeacherService teamTeacherService;

	@Autowired
	private TeamSceneService teamSceneService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private CrBadgeService crBadgeService;

	@Autowired
	private GuideHistoryService guideHistoryService;

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private VehicleFrameService vehicleFrameService;

	@ApiOperation("获取桌面监控")
	@GetMapping("/sessions")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<MonitorSession>> getSessions(
			@ApiParam(name = "room_id", value = "训练实例id", required = true) String room_id,
			@ApiParam(name = "student_id", value = "学员id", required = false) String student_id) {
		TeamRoom room = teamRoomService.getById(room_id);
		// 1. 确认场景已开启
		if (room == null || StringUtils.isBlank(room.getLocalSceneId())) {
			return new BaseRsp<>(Lists.newArrayList());
		}
		return new BaseRsp<>(
				crCommonService.getMonitorSessions(room.getLocalSceneId(), student_id));
	}

	@ApiOperation("获取角色列表")
	@GetMapping("/roles")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<IdNameType>> getRoleList(
			@ApiParam(name = "resource_id", value = "红蓝对抗id", required = true) String resource_id) {
		List<IdNameType> res = teamTeacherService.getRoleList(resource_id);
		return new BaseRsp<>(res);
	}

	@ApiOperation("获取学员的评估提交记录")
	@GetMapping("/evaluation/list")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<EvaluationListRsp>> getEvaluationList(@Validated EvaluationListReq req) {
		// 鉴权
		teamRoomService.checkTeacherPermission(req.getRoom_id(),
				UserInfoContext.getUserContext().getUserId());
		// get evaluation list
		List<EvaluationListRsp> res = teamRoomService.getEvaluationList(req);
		return new BaseRsp<>(res);
	}

	@ApiOperation("获取学员单个评估和任务详情，用于裁决页面")
	@GetMapping("/evaluation/detail")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<EvaluationMissionRsp> getEvaluationDetail(
			@ApiParam(name = "resource_id", value = "评估编号", required = true) String resource_id) {
		EvaluationMissionRsp res = crTaskService.getEvaluationById(resource_id);
		return new BaseRsp<>(res);
	}

	@ApiOperation("获取评分列表")
	@GetMapping("/score/list")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<StudentScoreListRsp>> getScoreList(
			@ApiParam(name = "resource_id", value = "训练房间id", required = true) String resource_id) {
		teamRoomService.checkTeacherPermission(resource_id,
				UserInfoContext.getUserContext().getUserId());
		List<StudentScoreListRsp> res = teamTeacherService.getScoreList(resource_id);
		return new BaseRsp<>(res);
	}

	@ApiOperation("动态任务新增")
	@PostMapping("/mission_info_create")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Boolean>
	addMissionInfo(@RequestBody @Validated TeamRoomMissionReq rbRoomMissionReq) {
		// validate
		teamRoomService.checkTeacherPermission(rbRoomMissionReq.getRoomId(),
				UserInfoContext.getUserContext().getUserId());
		teamRoomService.checkRoomOpening(rbRoomMissionReq.getRoomId());
		// new mission
		Mission mission = BeanUtils.copy(rbRoomMissionReq, Mission.class);
		mission.setCategoryId(rbRoomMissionReq.getCategory());
		mission.setSkillPoints(JsonUtil.toJsonString(rbRoomMissionReq.getSkillPoints()));
		mission.setCategoryId(rbRoomMissionReq.getCategory());
		mission.setShowWriteup(false);

		CrRole crRole = crRoleService.getById(rbRoomMissionReq.getRoleResourceId());
		return new BaseRsp<>(
				missionService.addDynamic(mission, crRole, rbRoomMissionReq.getMissionStage(),rbRoomMissionReq.getAfterMissionId()));
	}

	/**
	 * 获取设备列表
	 * 透传中台
	 *
	 * @param resource_id 资源id
	 * @param category    类别
	 * @param name        名称
	 * @return {@code BaseRsp<JSONArray>}
	 */
	@ApiOperation("设备清单")
	@GetMapping("/device/list")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<FrameTopoItem>> getDeviceList(
			@ApiParam(name = "resource_id", value = "团队房间id", required = true) String resource_id,
			@ApiParam(name = "category", value = "设备类型", required = false) Integer category,
			@ApiParam(name = "name", value = "搜索关键字", required = false) String name) {
		if (StringUtils.isBlank(resource_id)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		TeamRoom room = teamRoomService.getOrExceptionById(resource_id);
		List<FrameTopoItem> res = vehicleFrameService.getTopoByFrameId(room.getVehicleFrameId());
		return new BaseRsp<>(res);
	}

	/**
	 * 获取设备统计数据
	 * 透传中台
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<JSONObject>}
	 */
	@ApiOperation("设备统计")
	@GetMapping("/device/statistics")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<AreaAndCount>> getDeviceStatistics(
			@ApiParam(name = "resource_id", value = "团队房间id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
		List<AreaAndCount> res = teamTeacherService.getDutCount(resource_id);
		return new BaseRsp<>(res);
	}

	/**
	 * 获取复盘时间行
	 * 传入用户id，则展示事件，否则只提供开始时间、结束时间
	 * @param resource_id 资源id
	 * @param user_id     用户id
	 * @return {@code BaseRsp<TimeLineData>}
	 */
	@ApiOperation("复盘时间轴")
	@GetMapping("/replay/timeline")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TimeLineData> getReviewTimeLine(
			@ApiParam(name = "resource_id", value = "团队房间id", required = true) String resource_id,
			@ApiParam(name = "user_id", value = "用户id", required = false) String user_id) {
		CrCommonValidator.validateId(resource_id);
		TimeLineData res = teamTeacherService.getReplayTimeline(resource_id, user_id);
		return new BaseRsp<>(res);
	}

	/**
	 * 获取任务状态统计数据
	 * @return
	 */
	@ApiOperation("获取任务状态统计数据")
	@GetMapping("/get_statistical_info")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TaskStatisticsInfo> getStatisticalInfo(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
		TaskStatisticsInfo res = teamTeacherService.getTaskStatisticsInfo(resource_id);
		return new BaseRsp<>(res);
	}

	@ApiOperation("查询复盘所需scene_id")
	@GetMapping("/replay/enable")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<String> isReplayEnabled(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
		String res = teamTeacherService.getReplaySceneId(resource_id);
		return new BaseRsp<>(res);
	}

	@ApiOperation("任务裁决红点是否显示")
	@GetMapping("/evaluation/badge")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<BadgeBo> ifShowEvaluationBadge(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
		BadgeBo res = crBadgeService.getEvaluationBadge(resource_id);
		return new BaseRsp<>(res);
	}

	@ApiOperation("任务裁决红点点击取消")
	@GetMapping("/evaluation/badge/click")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Void> clickEvaluationBadge(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
		crBadgeService.clickEvaluationBadge(resource_id);
		return new BaseRsp<>();
	}

	@ApiOperation("返回态势回放所有下拉框选项")
	@GetMapping("/playback/option")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<PlaybackOptionRsp> getAllOption(@ApiParam(name = "room_id", value = "房间ID") @RequestParam("room_id") String roomId) {
		TimeLineData res = teamTeacherService.getReplayTimeline(roomId, null);
		PlaybackOptionRsp playbackOptionRsp = guideHistoryService.getAllOption(roomId);
		playbackOptionRsp.setRoleType(null);
		playbackOptionRsp.setBelongingEventNames(null);
		playbackOptionRsp.setStartTime(TimeUtil.format1(res.getStartTime()));
		playbackOptionRsp.setEndTime(TimeUtil.format1(res.getEndTime()));

		//根据拓扑id获取节点信息
		TeamRoom teamRoom = teamRoomService.getById(roomId);
		String sceneId = teamRoom.getReplaySceneId();
		if (StringUtils.isBlank(sceneId)) {
			log.info("This training [{}] hasn't start a scene, no scene to replay.", roomId);
			throw new BusinessException(CrBusinessErrorCode.REPLAY_SCENE_NOT_EXIST);
		}
		SituationTopo situationTopo = situationHandlerService.getSituationTopoBySceneId(sceneId, true);
		List<MissionCategoryRsp> nodeList = situationTopo.getElements().values().stream()
														 .map(elementInfo -> new MissionCategoryRsp(elementInfo.getElementId(), elementInfo.getName()))
														 .collect(Collectors.toList());
		//区域信息
		List<MissionCategoryRsp> areaList = situationTopo.getAreas().values().stream()
														 .map(areaInfo -> new MissionCategoryRsp(areaInfo.getAreaId(), "area-" + areaInfo.getAreaId()))
														 .collect(Collectors.toList());
		nodeList.addAll(areaList);
		playbackOptionRsp.setNodeList(nodeList);
		return new BaseRsp<>(playbackOptionRsp);
	}

}
