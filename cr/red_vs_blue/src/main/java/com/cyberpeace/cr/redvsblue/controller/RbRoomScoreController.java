package com.cyberpeace.cr.redvsblue.controller;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.HtmlTemplateEnum;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.HtmlToPdfUtil;
import com.cyberpeace.cr.crbase.controller.CrScoreController;
import com.cyberpeace.cr.crbase.controller.req.MissionEvaluationListReq;
import com.cyberpeace.cr.crbase.controller.req.StudentScoreReq;
import com.cyberpeace.cr.crbase.controller.req.SubmitEvaluationReq;
import com.cyberpeace.cr.crbase.controller.rsp.MissionDynamicRsp;
import com.cyberpeace.cr.crbase.controller.rsp.MissionEvaluationRsp;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreRsp;
import com.cyberpeace.cr.crbase.controller.rsp.TrainingRecordMissionRsp;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.RadarLabel;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrScoreService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.TaskCounter;
import com.cyberpeace.cr.crbase.service.context.TrainingReport;
import com.cyberpeace.cr.crbase.service.context.UserReport;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReport;
import com.cyberpeace.cr.crbase.service.context.mission.MissionsStatistic;
import com.cyberpeace.cr.crbase.service.context.mission.StageExport;
import com.cyberpeace.cr.redvsblue.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.RbRoomScoreService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbTimeManagementService;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomData;
import com.cyberpeace.cr.redvsblue.service.context.mission.RoleScore;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.integratation.service.impl.MpEnvService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "红蓝对抗评分")
@RestController
@RequiresAuthentication
@Slf4j
@RequestMapping("/cr/red_vs_blue/red_vs_blue/")
public class RbRoomScoreController {

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private RbRoomScoreService rbRoomScoreService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private CrScoreController crScoreController;

	@Autowired
	private MpEnvService mpEnvService;

	@Autowired
	private CrScoreService crScoreService;

	@Autowired
	private RbTimeManagementService rbTimeManagementService;

	@ApiOperation("训练记录导出为PDF")
	@GetMapping("/train2pdf")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public void dumpTrainRecord2PDF(HttpServletResponse httpServletResponse,
			@RequestParam("room_id") String roomId,
			@ApiParam(name = "role_group", value = "角色id", required = true) Integer role_group) {
		JSONObject data = getTrainRecordJsonObject(roomId, role_group);
		data.put("username", UserInfoContext.getUserContext().getUserName());
		data.put("title", crScoreController.getTitle(rbRoomService.getById(roomId).getName()));

		// 获取用户LOGO
		try {
			data.put("user_logo",
					mpEnvService.getMpHost() + UserInfoContext.getUserContext().getAvatarUrl());
		} catch (Exception e) {
			log.error("failed to get user logo", e);
			throw new RemoteServiceException();
		}

		String destHtmlPath = String.format("%s%s-%s.html",
				AttachmentPath.RED_BLUE_HTML_TEMPLATE.getDesc(), roomId,
				UserInfoContext.getUserContext().getUserId());
		try {
			HtmlToPdfUtil.getHtmlTemplateInstance(data, HtmlTemplateEnum.REDBLUE, destHtmlPath);
			String destPdfPath = destHtmlPath.substring(0, destHtmlPath.lastIndexOf("html"))
					+ "pdf";
			HtmlToPdfUtil.convert(destHtmlPath, destPdfPath);
			FileUtil.setResponse(new File(destPdfPath), true, httpServletResponse);
		} catch (Exception e) {
			log.error("failed to export pdf: {}", destHtmlPath);
			throw e;
		} finally {
			if (!new File(destHtmlPath).delete()) {
				log.error("failed to delete file:{}", destHtmlPath);
			}
		}
	}

	private JSONObject getTrainRecordJsonObject(String roomId, Integer roleGroup) {
		JSONObject jsonObject = new JSONObject();

		TrainingReport trainingReport = getTrainingReport(roomId, roleGroup).getData();
		List<JSONObject> userList = org.apache.commons.compress.utils.Lists.newArrayList();

		for (UserReport user : trainingReport.getUsers()) {
			String userId = user.getUserId();

			JSONObject userObject = new JSONObject();
			userObject.put("userId", userId);
			userObject.put("userName", user.getUserName());
			userObject.put("userRole", user.getRoleName());

			userObject.put("score", getStudentScore(roomId, userId).getData());
			userObject.put("mission", getUserMissionDynamic(roomId, userId).getData());
			userObject.put("radar", crRoleService.getRadar(roomId, userId));
			List<StageExport> stageExports = getStageRsps(
					getTrainingRecord(roomId, userId).getData());

			userObject.put("stages", stageExports);

			userList.add(userObject);

		}

		List<RadarLabel> radarLabels = missionService.getRadarLabel();

		jsonObject.put("radarLabels", radarLabels);
		jsonObject.put("locale", crScoreController.getTemplateI18n());
		jsonObject.put("user_list", userList);
		jsonObject.put("mission_report", getTrainingMissionReport(roomId, roleGroup).getData());
		jsonObject.put("report", trainingReport);

		return jsonObject;
	}

	private List<StageExport>
			getStageRsps(List<MissionSchemeObject<TrainingRecordMissionRsp>> missionSchemeList) {
		List<StageExport> stageExports = org.apache.commons.compress.utils.Lists.newArrayList();

		for (MissionSchemeObject<TrainingRecordMissionRsp> missionSchemeObject : missionSchemeList) {
			StageExport stageExport = new StageExport(missionSchemeObject);

			stageExports.add(stageExport);
		}

		return stageExports;
	}

	@ApiOperation("查询学员评分")
	@GetMapping("/student_score")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<StudentScoreRsp> getStudentScore(
			@ApiParam(name = "resource_id", value = "红蓝对抗id", required = true) String resource_id,
			@ApiParam(name = "user_hash", value = "用户id", required = true) String user_hash) {
		rbRoomService.checkRefereePermission(resource_id,
				UserInfoContext.getUserContext().getUserId());

		StudentScoreRsp rsp = crScoreService.getStudentScore(resource_id, user_hash);
		Integer duration = rbRoomService.getDuration(resource_id);
		String timeCost = rbTimeManagementService.getTimeCost(resource_id);
		rsp.getTrainingSummary().setTimeCost(timeCost);
		rsp.getTrainingSummary().setDuration(duration);

		return new BaseRsp<>(rsp);
	}

	@ApiOperation("提交学员评分")
	@PostMapping("/student_score")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp updateStudentScore(@ApiParam(name = "studentScoreReq",
			value = "学员评分") @RequestBody StudentScoreReq studentScoreReq) {
		rbRoomService.checkRefereePermission(studentScoreReq.getRoomId(),
				UserInfoContext.getUserContext().getUserId());
		rbRoomService.checkRoomOpening(studentScoreReq.getRoomId());

		crScoreController.updateStudentScore(studentScoreReq);

		return new BaseRsp<>();
	}

	@ApiOperation("提交任务的人工评估")
	@PostMapping("/submit_evaluation")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<String> updateSubmitEvaluation(@ApiParam(name = "submitEvaluationReq",
			value = "学员评分") @RequestBody SubmitEvaluationReq submitEvaluationReq) {
		rbRoomService.checkRefereePermission(submitEvaluationReq.getRoomId(),
				UserInfoContext.getUserContext().getUserId());

		rbRoomService.checkRoomOpening(submitEvaluationReq.getRoomId());

		crScoreController.updateSubmitEvaluation(submitEvaluationReq);

		return new BaseRsp<>();
	}

	@ApiOperation("获取学员的评估提交记录")
	@GetMapping("/scene_mission_evaluations")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<MissionEvaluationRsp>>
			getMissionEvaluations(@Validated MissionEvaluationListReq missionEvaluationListReq) {

		rbRoomService.checkRefereePermission(missionEvaluationListReq.getRoomId(),
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
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<MissionDynamicRsp> getUserMissionDynamic(
			@ApiParam(name = "resource_id", value = "红蓝对抗id", required = true) String resource_id,
			@ApiParam(name = "student_id", value = "用户id", required = true) String student_id) {
		rbRoomService.checkRefereePermission(resource_id,
				UserInfoContext.getUserContext().getUserId());

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
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<MissionReport> getTrainingMissionReport(
			@ApiParam(name = "resource_id", value = "红蓝对抗id", required = true) String resource_id,
			@ApiParam(name = "role_group", value = "角色id", required = true) Integer role_group) {
		rbRoomService.checkRefereePermission(resource_id,
				UserInfoContext.getUserContext().getUserId());

		List<CrRole> crRoleList = crRoleService
				.selectRbStudentsRoleByRoomIdAndRoleGroup(resource_id, role_group);

		MissionReport missionReport = crScoreController.getTrainingMissionReport(resource_id,
				crRoleList);

		return new BaseRsp<>(missionReport);
	}

	/**
	 * 获取训练报告 - 任务信息统计
	 *
	 * @return
	 */
	@ApiOperation("获取用户训练记录")
	@GetMapping("/get_training_record")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<MissionSchemeObject<TrainingRecordMissionRsp>>> getTrainingRecord(
			@ApiParam(name = "resource_id", value = "红蓝对抗id", required = true) String resource_id,
			@ApiParam(name = "user_hash", value = "用户id", required = true) String user_hash) {
		rbRoomService.checkRefereePermission(resource_id,
				UserInfoContext.getUserContext().getUserId());

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
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<TrainingReport> getTrainingReport(
			@ApiParam(name = "resource_id", value = "红蓝对抗id", required = true) String resource_id,
			@ApiParam(name = "role_group", value = "角色id", required = true) Integer role_group) {

		rbRoomService.checkRefereePermission(resource_id,
				UserInfoContext.getUserContext().getUserId());

		RbRoomData rbRoomdata = rbRoomService.getRbRoomDataByRoomId(resource_id);

		if (RbRoom.TrainingStatusCode.UNOPENED.getCode().equals(rbRoomdata.getTrainingStatus())) {
			return new BaseRsp<>(new TrainingReport());
		}

		List<CrRole> crRoleList = crRoleService
				.selectRbStudentsRoleByRoomIdAndRoleGroup(resource_id, role_group);

		List<UserReport> userReportList = crScoreController.getTrainingReport(resource_id,
				crRoleList);

		Date endTime = rbRoomdata.getSceneEndTime() == null ? new Date()
				: rbRoomdata.getSceneEndTime();
		Date startTime = rbRoomdata.getSceneStartTime() == null ? rbRoomdata.getStartTime()
				: rbRoomdata.getSceneStartTime();
		Integer timeSeconds = Math.toIntExact((endTime.getTime() - startTime.getTime()) / 1000);

		TrainingReport trainingReport = new TrainingReport();

		trainingReport.setSceneStartTime(startTime);
		trainingReport.setTeacherName(UserInfoContext.getUserContext().getUserName());
		trainingReport.setTrainingTime(timeSeconds);
		trainingReport.setUsers(userReportList);

		return new BaseRsp<>(trainingReport);
	}

	/**
	 * 查询训练角色得分信息
	 * @return
	 */
	@ApiOperation("查询训练角色得分信息")
	@GetMapping("/get_role_score_info")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<RoleScore>> getRoleScoreInfo(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
		List<RoleScore> data = rbRoomScoreService.getRoleScoreInfo(resource_id);

		return new BaseRsp(data);
	}

	/**
	 * 查询任务状态
	 * 导演-任务编排 左侧柱状图
	 * @return
	 */
	@ApiOperation("查询任务状态")
	@GetMapping("/missions_statistics")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<MissionsStatistic>> getMissionsStatistics(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
		rbRoomService.checkDirectorPermission(resource_id,
				UserInfoContext.getUserContext().getUserId());

		List<MissionsStatistic> missionsStatistics = Lists.newArrayList();
		List<String> allUserIds = Lists.newArrayList();

		List<CrRole> crRoles = crRoleService.selectRbStudentsRoleByRoomId(resource_id);
		for (CrRole crRole : crRoles) {
			List<String> userIds = crRole.getUserIdList();

			allUserIds.addAll(userIds);

			for (String userId : userIds) {
				TaskCounter taskCounter = crRoleService.getTaskCounter(resource_id, userId);
				MissionsStatistic missionsStatistic = new MissionsStatistic(crRole.getRoleGroup(),
						userId, taskCounter);
				missionsStatistics.add(missionsStatistic);
			}
		}

		Map<String, String> idNameMap = remoteUserService.getIdNameMap(allUserIds);
		missionsStatistics.forEach(missionsStatistic -> {
			missionsStatistic.setUserName(idNameMap.get(missionsStatistic.getUserResourceId()));
		});

		return new BaseRsp(missionsStatistics);
	}

}
