package com.cyberpeace.cr.teamtrain.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.cyberpeace.common.enums.TeamRoleGroup;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.service.*;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.HtmlTemplateEnum;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.HtmlToPdfUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
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
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.TrainingReport;
import com.cyberpeace.cr.crbase.service.context.UserReport;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReport;
import com.cyberpeace.cr.crbase.service.context.mission.StageExport;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cs.constant.AttachmentPath;
import com.cyberpeace.integratation.service.impl.MpEnvService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 团队训练得分Controller
 *
 * @author SN
 * @date 2024/11/14
 */
@Api(tags = "团队训练评分")
@RestController
@RequestMapping("/cr/training_team/training_team")
@Slf4j
public class TeamTrainScoreController {

	/**
	 * 团队房间Service
	 */
	@Autowired
	TeamRoomService teamRoomService;

	/**
	 * 靶场岗位Service
	 */
	@Autowired
	private CrRoleService crRoleService;

	/**
	 * 靶场得分Controller
	 */
	@Autowired
	private CrScoreController crScoreController;

	/**
	 * 靶场得分Service
	 */
	@Autowired
	private CrScoreService crScoreService;

	/**
	 * 靶场任务Service
	 */
	@Autowired
	private CrTaskService crTaskService;

	/**
	 * 任务Service
	 */
	@Autowired
	private MissionService missionService;

	/**
	 * 中台环境Service
	 */
	@Autowired
	private MpEnvService mpEnvService;

	/**
	 * 训练记录Service
	 */
	@Autowired
	private TrainRecordService trainRecordService;

	/**
	 * 导出训练记录2PDF格式
	 *
	 * @param httpServletResponse HTTP Servlet 响应
	 * @param roomId              房间id
	 */
	@ApiOperation("训练记录导出为PDF")
	@GetMapping("/train2pdf")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public void dumpTrainRecord2PDF(HttpServletResponse httpServletResponse,
			@RequestParam("room_id") String roomId) {
		JSONObject data = getTrainRecordJsonObject(roomId);
		data.put("username", UserInfoContext.getUserContext().getUserName());
		data.put("title", crScoreController.getTitle(teamRoomService.getById(roomId).getName()));

		// 获取用户LOGO
		try {
			data.put("user_logo",
					mpEnvService.getMpHost() + UserInfoContext.getUserContext().getAvatarUrl());
		} catch (Exception e) {
			throw new RemoteServiceException();
		}

		String destHtmlPath = String.format("%s%s-%s.html",
				AttachmentPath.TEAM_HTML_TEMPLATE.getDesc(), roomId,
				UserInfoContext.getUserContext().getUserId());
		try {
			HtmlToPdfUtil.getHtmlTemplateInstance(data, HtmlTemplateEnum.TEAM, destHtmlPath);
			String destPdfPath = destHtmlPath.substring(0, destHtmlPath.lastIndexOf("html"))
					+ "pdf";
			HtmlToPdfUtil.convert(destHtmlPath, destPdfPath);
			FileUtil.setResponse(new File(destPdfPath), true, httpServletResponse);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			Path filePath = Paths.get(destHtmlPath);
			try {
				Files.delete(filePath);
			} catch (IOException e) {
				log.error("failed to delete file:{}", destHtmlPath);
			}
		}
	}

	/**
	 * 导出训练记录2PDF格式1
	 *
	 * @param httpServletResponse HTTP Servlet 响应
	 * @param roomId              房间id
	 */
	@ApiOperation("训练记录导出为html")
	@GetMapping("/train2html")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public void dumpTrainRecord2PDF1(HttpServletResponse httpServletResponse,
			@RequestParam("room_id") String roomId) {
		JSONObject data = getTrainRecordJsonObject(roomId);
		data.put("username", UserInfoContext.getUserContext().getUserName());
		data.put("title",
				crScoreController.getTitle(UserInfoContext.getUserContext().getUserName()));

		// 获取用户LOGO
		try {
			data.put("user_logo",
					mpEnvService.getMpHost() + UserInfoContext.getUserContext().getAvatarUrl());
		} catch (Exception e) {
			throw new RemoteServiceException();
		}

		String destHtmlPath = String.format("%s%s-%s.html",
				AttachmentPath.TEAM_HTML_TEMPLATE.getDesc(), roomId,
				UserInfoContext.getUserContext().getUserId());
		HtmlToPdfUtil.getHtmlTemplateInstance(data, HtmlTemplateEnum.TEAM, destHtmlPath);

		FileUtil.setResponse(new File(destHtmlPath), true, httpServletResponse);

	}

	/**
	 * 获取训练记录JSON 格式对象
	 *
	 * @param roomId 房间id
	 * @return {@code JSONObject }
	 */
	private JSONObject getTrainRecordJsonObject(String roomId) {
		JSONObject jsonObject = new JSONObject();

		TrainingReport trainingReport = getTrainingReport(roomId).getData();

		List<JSONObject> userList = Lists.newArrayList();

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
		jsonObject.put("mission_report", getTrainingMissionReport(roomId).getData());
		jsonObject.put("report", trainingReport);
		jsonObject.put("reportTime", getDuration(trainingReport.getTrainingTime()));

		return jsonObject;
	}


	/**
	 * 获取学员得分
	 *
	 * @param resource_id 资源id
	 * @param user_hash   用户id
	 * @return {@code BaseRsp<StudentScoreRsp> }
	 */
	@ApiOperation("查询学员评分")
	@GetMapping("/student_score")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<StudentScoreRsp> getStudentScore(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id,
			@ApiParam(name = "user_hash", value = "用户id", required = true) String user_hash) {
		String userId = UserInfoContext.getUserContext().getUserId();
		teamRoomService.checkTeacherPermission(resource_id, userId);

		StudentScoreRsp rsp = crScoreService.getStudentScore(resource_id, user_hash);
		// 训练时间
		Integer duration = teamRoomService.getDuration(resource_id);
		String timeCost = trainRecordService.getTimeCost(resource_id);
		rsp.getTrainingSummary().setDuration(duration);
		rsp.getTrainingSummary().setTimeCost(timeCost);
		return new BaseRsp<>(rsp);
	}

	/**
	 * 更新学员得分
	 *
	 * @param studentScoreReq 学员得分请求体
	 * @return {@code BaseRsp<Void> }
	 */
	@ApiOperation("提交学员评分")
	@PostMapping("/student_score")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<Void> updateStudentScore(@ApiParam(name = "studentScoreReq",
			value = "学员评分") @RequestBody StudentScoreReq studentScoreReq) {
		String userId = UserInfoContext.getUserContext().getUserId();
		teamRoomService.checkTeacherPermission(studentScoreReq.getRoomId(), userId);

		teamRoomService.checkRoomOpening(studentScoreReq.getRoomId());

		crScoreController.updateStudentScore(studentScoreReq);

		return new BaseRsp<>();
	}

	/**
	 * 更新提交评估
	 *
	 * @param submitEvaluationReq 提交评估请求体
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("提交任务的人工评估")
	@PostMapping("/submit_evaluation")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<String> updateSubmitEvaluation(@ApiParam(name = "submitEvaluationReq",
			value = "学员评分") @RequestBody SubmitEvaluationReq submitEvaluationReq) {
		teamRoomService.checkRoomOpening(submitEvaluationReq.getRoomId());

		crTaskService.submitEvaluation(submitEvaluationReq.getSubmitId(),
				submitEvaluationReq.getScore(), submitEvaluationReq.getDetail(),
				submitEvaluationReq.getReason(), submitEvaluationReq.getEvaluateStatus(),
				UserInfoContext.getUserContext().getUserId());

		crTaskService.pushMissionWS(submitEvaluationReq.getRoomId(),
				MissionWsCode.TEAM_MISSION_CODE);
		return new BaseRsp<>();
	}

	/**
	 * 弃用
	 * 更换为不分页的接口
	 *
	 * @param missionEvaluationListReq 任务评估列表请求体
	 * @return {@code BaseRsp<List<MissionEvaluationRsp>>}
	 */
	@ApiOperation("获取学员的评估提交记录")
	@GetMapping("/scene_mission_evaluations")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<MissionEvaluationRsp>>
			getMissionEvaluations(@Validated MissionEvaluationListReq missionEvaluationListReq) {

		teamRoomService.checkTeacherPermission(missionEvaluationListReq.getRoomId(),
				UserInfoContext.getUserContext().getUserId());

		return crScoreController.getMissionEvaluations(missionEvaluationListReq);
	}

	/**
	 * 获取动态事件
	 *
	 * @param resource_id 资源id
	 * @param student_id  学员id
	 * @return
	 */
	@ApiOperation("获取学员动态事件")
	@GetMapping("/get_user_mission_dynamic")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
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
	 * @param resource_id 资源id
	 * @return
	 */
	@ApiOperation("获取任务信息统计")
	@GetMapping("/get_training_mission_report")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<MissionReport> getTrainingMissionReport(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		RoleQuery roleQuery = new RoleQuery(resource_id, TeamRoleGroup.STUDENT);
		List<CrRole> crRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);

		MissionReport missionReport = crScoreController.getTrainingMissionReport(resource_id,
				crRoleList);

		return new BaseRsp<>(missionReport);
	}

	/**
	 * 获取训练报告 - 任务信息统计
	 *
	 * @param resource_id 资源id
	 * @param user_hash   用户id
	 * @return
	 */
	@ApiOperation("获取用户训练记录")
	@GetMapping("/get_training_record")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<List<MissionSchemeObject<TrainingRecordMissionRsp>>> getTrainingRecord(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id,
			@ApiParam(name = "user_hash", value = "用户id", required = true) String user_hash) {

		if (StringUtils.isBlank(user_hash)) {
			throw new BusinessException(CrBusinessErrorCode.PLZ_SPECIFY_ONE_STUDENT);
		}

		List<MissionSchemeObject<TrainingRecordMissionRsp>> missionSchemeList = crScoreController
				.getTrainingRecord(resource_id, user_hash);

		return new BaseRsp<>(missionSchemeList);
	}

	/**
	 * 获取训练报告 - 任务信息统计
	 *
	 * @param resource_id 资源id
	 * @return
	 */
	@ApiOperation("获取用户训练报告")
	@GetMapping("/get_training_report")
	@RequiresPermissions(MenuConstant.WEB_CR_TEAM_TRAIN)
	public BaseRsp<TrainingReport> getTrainingReport(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		TeamRoomData teamRoomdata = teamRoomService.getTeamRoomDataByRoomId(resource_id);



		RoleQuery roleQuery = new RoleQuery(resource_id, TeamRoleGroup.STUDENT);
		List<CrRole> crRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);

		List<UserReport> userReportList = crScoreController.getTrainingReport(resource_id,
				crRoleList);

		Date endTime = teamRoomdata.getSceneEndTime() == null ? new Date()
				: teamRoomdata.getSceneEndTime();
		Date startTime = teamRoomdata.getSceneStartTime() == null ? teamRoomdata.getStartTime()
				: teamRoomdata.getSceneStartTime();
		Integer timeSeconds = Math.toIntExact((endTime.getTime() - startTime.getTime()) / 1000);

		TrainingReport trainingReport = new TrainingReport();

		if (teamRoomdata.getSceneStartTime() == null) {
			// 暂无
			trainingReport.setTrainingTime(0);
		} else {
			trainingReport.setTrainingTime(timeSeconds);
		}
		trainingReport.setSceneStartTime(startTime);
		trainingReport.setTeacherName(UserInfoContext.getUserContext().getUserName());
		trainingReport.setUsers(userReportList);

		return new BaseRsp<>(trainingReport);
	}

	/**
	 * 获取期间
	 *
	 * @param trainingTime 训练时间
	 * @return {@code String }
	 */
	private String getDuration(Integer trainingTime) {
		String duration = "";
		// 几天几小时
		if (trainingTime > 86400) {

			duration = trainingTime / 86400 + EnumUtils.getEnumDescByCode(
					TrainingReport.TIME.DAY.getCode(), TrainingReport.TIME.class);
			int i = trainingTime % 86400 / 3600;
			if (i > 0) {
				duration += " " + trainingTime % 86400 / 3600 + EnumUtils.getEnumDescByCode(
						TrainingReport.TIME.HOUR.getCode(), TrainingReport.TIME.class);
			}
		}
		// 几小时几分钟
		else if (trainingTime > 3600) {
			duration = trainingTime / 3600 + EnumUtils.getEnumDescByCode(
					TrainingReport.TIME.HOUR.getCode(), TrainingReport.TIME.class);
			int i = trainingTime % 3600 / 60;
			if (i > 0) {
				duration += " " + trainingTime % 86400 / 3600 + EnumUtils.getEnumDescByCode(
						TrainingReport.TIME.MINUTE.getCode(), TrainingReport.TIME.class);
			}
		} // 几分钟几秒
		else if (trainingTime > 60) {
			duration = trainingTime / 60 + EnumUtils.getEnumDescByCode(
					TrainingReport.TIME.MINUTE.getCode(), TrainingReport.TIME.class);
			int i = trainingTime % 60;
			if (i > 0) {
				duration += " " + trainingTime % 60 + EnumUtils.getEnumDescByCode(
						TrainingReport.TIME.SECOND.getCode(), TrainingReport.TIME.class);
			}
		} // 几秒
		else {
			duration = trainingTime + EnumUtils.getEnumDescByCode(
					TrainingReport.TIME.SECOND.getCode(), TrainingReport.TIME.class);
		}

		return duration;

	}

	/**
	 * 获取阶段RSP
	 *
	 * @param missionSchemeList 任务计划列表
	 * @return {@code List<StageExport> }
	 */
	private List<StageExport>
			getStageRsps(List<MissionSchemeObject<TrainingRecordMissionRsp>> missionSchemeList) {
		List<StageExport> stageExports = Lists.newArrayList();

		for (MissionSchemeObject<TrainingRecordMissionRsp> missionSchemeObject : missionSchemeList) {
			StageExport stageExport = new StageExport(missionSchemeObject);

			stageExports.add(stageExport);
		}

		return stageExports;
	}

}
