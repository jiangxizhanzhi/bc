package com.cyberpeace.cr.teamtrain.controller;

import java.util.Comparator;
import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cs.service.context.IdNameType;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.service.CrCommonService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListReq;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListRsp;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationMissionRsp;
import com.cyberpeace.cr.crbase.service.context.scene.MonitorSession;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreListRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamTeacherService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "团队训练实例-教员")
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
		return new BaseRsp(res);
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
}
