package com.cyberpeace.cr.crbase.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.controller.req.MissionEvaluationListReq;
import com.cyberpeace.cr.crbase.controller.req.StudentScoreReq;
import com.cyberpeace.cr.crbase.controller.req.SubmitEvaluationReq;
import com.cyberpeace.cr.crbase.controller.rsp.MissionDynamicRsp;
import com.cyberpeace.cr.crbase.controller.rsp.MissionEvaluationRsp;
import com.cyberpeace.cr.crbase.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.crbase.controller.rsp.TrainingRecordMissionRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrScore;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrScoreService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.MissionStageScheme;
import com.cyberpeace.cr.crbase.service.context.TrainingReport;
import com.cyberpeace.cr.crbase.service.context.UserReport;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReport;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.integratation.model.UserPageContext;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "团队训练评分")
@RestController
@RequestMapping("/cr/training_team/training_team")
@Slf4j
public class TeamTrainScoreController {

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	TeamRoomService teamRoomService;

	@Autowired
	private CrScoreController crScoreController;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private CrScoreService crScoreService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private MissionService missionService;

	@ApiOperation("查询学员评分")
	@GetMapping("/student_score")
	public BaseRsp<StudentScoreRsp> getStudentScore(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id,
			@ApiParam(name = "user_hash", value = "用户id", required = true) String user_hash) {

		StudentScoreRsp studentScoreRsp = crScoreController.getStudentScore(resource_id, user_hash);

		return new BaseRsp<>(studentScoreRsp);
	}

	@ApiOperation("提交学员评分")
	@PostMapping("/student_score")
	public BaseRsp<StudentScoreRsp> updateStudentScore(@ApiParam(name = "studentScoreReq",
			value = "学员评分") @RequestBody StudentScoreReq studentScoreReq) {

		teamRoomService.checkRoomOpening(studentScoreReq.getTrainingTeam());

		crScoreController.updateStudentScore(studentScoreReq);

		return new BaseRsp<>();
	}

	@ApiOperation("提交任务的人工评估")
	@PostMapping("/submit_evaluation")
	public BaseRsp<String> updateSubmitEvaluation(@ApiParam(name = "submitEvaluationReq",
			value = "学员评分") @RequestBody SubmitEvaluationReq submitEvaluationReq) {
		teamRoomService.checkRoomOpening(submitEvaluationReq.getRoomId());

		crScoreController.updateSubmitEvaluation(submitEvaluationReq);

		return new BaseRsp<>();
	}

	@ApiOperation("获取学员的评估提交记录")
	@GetMapping("/scene_mission_evaluations")
	public BaseRsp<List<MissionEvaluationRsp>>
			getMissionEvaluations(@Validated MissionEvaluationListReq missionEvaluationListReq) {

		teamRoomService.checkTeacherPermission(missionEvaluationListReq.getRoomId(),
				UserInfoContext.getUserContext().getUserId());

		return crScoreController.getMissionEvaluations(missionEvaluationListReq);
	}

	/**
	 * 获取动态事件
	 *
	 * @return
	 */
	@ApiOperation("获取学员动态事件")
	@GetMapping("/get_user_mission_dynamic")
	public BaseRsp<MissionDynamicRsp> getUserMissionDynamic(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id,
			@ApiParam(name = "student_id", value = "用户id", required = true) String student_id) {

		MissionDynamicRsp missionDynamicRsp = crScoreController.getUserMissionDynamic(resource_id,
				student_id);

		return new BaseRsp<>(missionDynamicRsp);
	}

	/**
	 * 获取训练报告 - 任务信息统计
	 *
	 * @return
	 */
	@ApiOperation("获取任务信息统计")
	@GetMapping("/get_training_mission_report")
	public BaseRsp<MissionReport> getTrainingMissionReport(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {

		MissionReport missionReport = crScoreController.getTrainingMissionReport(resource_id);

		return new BaseRsp<>(missionReport);
	}

	/**
	 * 获取训练报告 - 任务信息统计
	 *
	 * @return
	 */
	@ApiOperation("获取用户训练记录")
	@GetMapping("/get_training_record")
	public BaseRsp<List<MissionSchemeObject<TrainingRecordMissionRsp>>> getTrainingRecord(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id,
			@ApiParam(name = "user_hash", value = "用户id", required = true) String user_hash) {

		List<MissionSchemeObject<TrainingRecordMissionRsp>> missionSchemeList = crScoreController
				.getTrainingRecord(resource_id, user_hash);

		return new BaseRsp(missionSchemeList);
	}

	/**
	 * 获取训练报告 - 任务信息统计
	 *
	 * @return
	 */
	@ApiOperation("获取用户训练报告")
	@GetMapping("/get_training_report")
	public BaseRsp<TrainingReport> getTrainingReport(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		TeamRoomData teamRoomdata = teamRoomService.getTeamRoomDataByRoomId(resource_id);

		if (TeamRoom.TrainingStatusCode.UNOPENED.getCode()
				.equals(teamRoomdata.getTrainingStatus())) {
			return new BaseRsp<>(new TrainingReport());
		}


		List<UserReport> userReportList =  crScoreController.getTrainingReport(resource_id);


		Date endTime = teamRoomdata.getSceneEndTime() == null ? new Date()
				: teamRoomdata.getSceneEndTime();
		Date startTime = teamRoomdata.getSceneStartTime() == null ? teamRoomdata.getStartTime()
				: teamRoomdata.getSceneStartTime();
		Integer timeSeconds = Math.toIntExact((endTime.getTime() - startTime.getTime()) / 1000);

		TrainingReport trainingReport = new TrainingReport();

		trainingReport.setSceneStartTime(startTime);
		trainingReport.setTeacherName(UserInfoContext.getUserContext().getUserName());
		trainingReport.setTrainingTime(timeSeconds);
		trainingReport.setUsers(userReportList);

		return new BaseRsp<>(trainingReport);
	}

}
