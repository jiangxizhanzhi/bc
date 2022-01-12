package com.cyberpeace.cr.teamtrain.controller;

import java.util.Date;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.MissionEvaluationListReq;
import com.cyberpeace.cr.crbase.controller.req.SubmitEvaluationReq;
import com.cyberpeace.cr.crbase.controller.rsp.MissionEvaluationRsp;
import com.cyberpeace.cr.crbase.controller.rsp.TrainingRecordMissionRsp;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrScoreService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReport;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.teamtrain.controller.req.StudentScoreReq;
import com.cyberpeace.cr.teamtrain.controller.rsp.MissionDynamicRsp;
import com.cyberpeace.cr.teamtrain.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.context.StatisticalInfo;
import com.cyberpeace.cr.teamtrain.service.context.TrainingReport;
import com.cyberpeace.cr.teamtrain.service.context.TrainingScore;
import com.cyberpeace.cr.teamtrain.service.context.UserReport;
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
	private TeamRoomService teamRoomService;

	@Autowired
	private CrRoleService crRoleService;

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

		List<TaskContext> taskList = crTaskService.getByRoomIdAndUser(resource_id, user_hash);

		Integer getScoreObjective = 0;

		Integer getScoreSubjective = 0;

		Integer sumScoreObjective = 0;

		Integer sumScoreSubjective = 0;

		TrainingScore trainingScore = new TrainingScore();

		for (TaskContext task : taskList) {
			Mission mission = task.getMission();
			int score = task.getScore() == null ? 0 : task.getScore();
			if (mission.isObjective()) {
				getScoreObjective += score;
				sumScoreObjective += mission.getScore();
			} else {
				getScoreSubjective += score;
				sumScoreSubjective += mission.getScore();
			}

		}

		trainingScore.setSumScoreSubjective(sumScoreSubjective);
		trainingScore.setSumScoreObjective(sumScoreObjective);
		trainingScore.setGetScoreSubjective(getScoreSubjective);
		trainingScore.setGetScoreObjective(getScoreObjective);

		CrScore crScore = crScoreService.getByRoomIdAndUser(resource_id, user_hash);
		StudentScoreRsp studentScoreRsp = new StudentScoreRsp();
		if (crScore != null) {
			studentScoreRsp = BeanUtils.copy(crScore, StudentScoreRsp.class);
			studentScoreRsp.setAbilityScore(
					JsonUtil.toJsonObjectArray(crScore.getSkillScore(), Integer.class));
			studentScoreRsp.setAbilityPercentage(crScore.getSkillPercentage());
		}

		studentScoreRsp.setTrainingScore(trainingScore);

		return new BaseRsp<>(studentScoreRsp);
	}

	@ApiOperation("提交学员评分")
	@PostMapping("/student_score")
	public BaseRsp<StudentScoreRsp> updateStudentScore(@ApiParam(name = "studentScoreReq",
			value = "学员评分") @RequestBody StudentScoreReq studentScoreReq) {

		teamRoomService.checkRoomOpening(studentScoreReq.getTrainingTeam());

		Integer trainingPercentage = Integer
				.parseInt(studentScoreReq.getTrainingPercentage().replace("%", ""));
		if (trainingPercentage < CrScore.ScoreValue.MIN_PERCENTAGE.getCode()
				|| trainingPercentage > CrScore.ScoreValue.MAX_PERCENTAGE.getCode()) {
			log.error("trainingPercentage out of range！value=[{}]", trainingPercentage);
			throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
		}
		Integer abilityPercentage = Integer
				.parseInt(studentScoreReq.getAbilityPercentage().replace("%", ""));
		if (abilityPercentage < CrScore.ScoreValue.MIN_PERCENTAGE.getCode()
				|| abilityPercentage > CrScore.ScoreValue.MAX_PERCENTAGE.getCode()) {
			log.error("abilityPercentage out of range！value=[{}]", abilityPercentage);
			throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
		}
		if (abilityPercentage + trainingPercentage != CrScore.ScoreValue.SUM_OF_PERCENTAGE_INT
				.getCode()) {
			log.error("sumPercentage is wrong！value=[{}],sum=[{}]",
					abilityPercentage + trainingPercentage,
					CrScore.ScoreValue.SUM_OF_PERCENTAGE_INT.getCode());
			throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);

		}

		List<Integer> abilityScoreList = JsonUtil
				.toJsonObjectArray(studentScoreReq.getAbilityScore(), Integer.class);

		if (abilityScoreList.size() != CrScore.ScoreValue.ABILITY_SCORE_NUM.getCode()) {
			log.error("abilityScoreListSize is wrong！value=[{}],size=[{}]", abilityScoreList.size(),
					CrScore.ScoreValue.ABILITY_SCORE_NUM.getCode());
			throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
		}
		IntSummaryStatistics stats = abilityScoreList.stream().mapToInt((x) -> x)
				.summaryStatistics();
		if (stats.getMax() > CrScore.ScoreValue.MAX_ABILITY_SCORE_VALUE.getCode()
				|| stats.getMin() < CrScore.ScoreValue.MIN_ABILITY_SCORE_VALUE.getCode()) {
			log.error("abilityScore out of range！maxValue=[{}],minValue=[{}]", stats.getMax(),
					stats.getMin());
			throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
		}

		if (studentScoreReq.getComment().length() > CrScore.ScoreValue.MAX_COMMENT_LEN.getCode()) {
			log.error("comment length out of range！comment length=[{}]",
					studentScoreReq.getComment().length());
			throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
		}

		CrScore crScore = crScoreService.getByRoomIdAndUser(studentScoreReq.getTrainingTeam(),
				studentScoreReq.getUser());
		CrScore newCrScore = getTeamScoreFromReq(studentScoreReq);

		if (crScore == null) {
			crScoreService.save(newCrScore);
		} else {
			newCrScore.setResourceId(crScore.getResourceId());
			crScoreService.updateById(newCrScore);

		}

		return new BaseRsp<>();
	}

	@ApiOperation("提交任务的人工评估")
	@PostMapping("/submit_evaluation")
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

	@ApiOperation("获取学员的评估提交记录")
	@GetMapping("/scene_mission_evaluations")
	public BaseRsp<List<MissionEvaluationRsp>>
			getMissionEvaluations(@Validated MissionEvaluationListReq missionEvaluationListReq) {
		MissionEvaluationQuery missionEvaluationQuery = BeanUtils.copy(missionEvaluationListReq,
				MissionEvaluationQuery.class);
		missionEvaluationQuery.setPer_page(missionEvaluationListReq.getPerPage());

		if (!missionEvaluationListReq.getIsShowEvaluated()) {
			missionEvaluationQuery.setEvaluateStatus(CrTaskSubmit.EvaluateStatus.WAIT.getCode());
		}
		if (CrTaskSubmit.EvaluateType.MACHINE_EVALUATION.getCode()
				.equals(missionEvaluationListReq.getEvaluateType())) {
			missionEvaluationQuery.setEvaluateType("MACHINE");
		}
		if (CrTaskSubmit.EvaluateType.MANUAL_EVALUATION.getCode()
				.equals(missionEvaluationListReq.getEvaluateType())) {
			missionEvaluationQuery.setEvaluateType("MANUAL");
		}

		teamRoomService.checkTeacherPermission(missionEvaluationListReq.getRoomId(),
				UserInfoContext.getUserContext().getUserId());

		IPage<MissionEvaluationContext> data = crTaskService
				.getMissionEvaluationList(missionEvaluationQuery);

		List<String> userIds = data.getRecords().stream()
				.map(MissionEvaluationContext::getSubmitUser).distinct()
				.collect(Collectors.toList());

		userIds.addAll(data.getRecords().stream().map(MissionEvaluationContext::getEvaluatorId)
				.distinct().collect(Collectors.toList()));
		Map<String, String> userInfoMap = remoteUserService.getIdNameMap(userIds);

		data.convert(missionEvaluationContext -> {
			MissionEvaluationRsp missionEvaluationRsp = BeanUtils.copy(missionEvaluationContext,
					MissionEvaluationRsp.class);

			MissionEvaluationRsp.SubmitInfo submitInfo = missionEvaluationRsp.new SubmitInfo();

			submitInfo.setCreateTime(missionEvaluationContext.getCreateTime());
			submitInfo.setUserAnswer(missionEvaluationContext.getAnswer());

			missionEvaluationRsp
					.setEvaluatorName(userInfoMap.get(missionEvaluationContext.getEvaluatorId()));
			missionEvaluationRsp.setEvaluator(missionEvaluationContext.getEvaluatorId());
			missionEvaluationRsp
					.setSubmitUserName(userInfoMap.get(missionEvaluationContext.getSubmitUser()));
			missionEvaluationRsp.setSubmitLogHash(missionEvaluationContext.getMissionHash());
			missionEvaluationRsp.setTotalScore(missionEvaluationContext.getMission().getScore());
			missionEvaluationRsp.setEvaluateTime(missionEvaluationContext.getEvaluateTime());
			missionEvaluationRsp.setSubmitInfo(submitInfo);
			missionEvaluationRsp.setMissionData(missionEvaluationContext.getMission());

			return missionEvaluationRsp;
		});

		return new BaseRsp(PageUtil.getListResult(data));
	}

	private CrScore getTeamScoreFromReq(StudentScoreReq studentScoreReq) {
		CrScore crScore = BeanUtils.copy(studentScoreReq, CrScore.class);

		crScore.setRoomId(studentScoreReq.getTrainingTeam());
		crScore.setUserId(studentScoreReq.getUser());
		crScore.setSkillScore(studentScoreReq.getAbilityScore());
		crScore.setSkillPercentage(studentScoreReq.getAbilityPercentage());
		crScore.setRoomId(studentScoreReq.getTrainingTeam());

		return crScore;
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
		MissionDynamicRsp missionDynamicRsp = new MissionDynamicRsp();
		TaskCounter taskCounter = crRoleService.getTaskCounter(resource_id, student_id);

		List<StatisticalInfo> statisticalInfoList = Lists.newArrayList();

		StatisticalInfo statisticalInfo = new StatisticalInfo();
		statisticalInfo.setStatistical_state(TaskCounter.TaskType.FINISHED.getCode());
		statisticalInfo.setValue(taskCounter.getFinished());
		statisticalInfoList.add(statisticalInfo);

		StatisticalInfo statisticalInfo1 = new StatisticalInfo();
		statisticalInfo1.setStatistical_state(TaskCounter.TaskType.RELEASED.getCode());
		statisticalInfo1.setValue(taskCounter.getReleased());
		statisticalInfoList.add(statisticalInfo1);

		StatisticalInfo statisticalInfo2 = new StatisticalInfo();
		statisticalInfo2.setStatistical_state(TaskCounter.TaskType.NOTRELEASED.getCode());
		statisticalInfo2.setValue(taskCounter.getNotReleased());
		statisticalInfoList.add(statisticalInfo2);

		List<DynamicData> dynamicDataList = missionService.getTaskLogByRoomIdAndUserId(resource_id,
				student_id);

		missionDynamicRsp.setDynamicData(dynamicDataList);
		missionDynamicRsp.setStatisticalInfo(statisticalInfoList);

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
		RoleQuery roleQuery = new RoleQuery(resource_id,
				CrRole.TeamRoleGroupCode.STUDENT.getCode());

		List<CrRole> crRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);

		MissionReport missionReport = new MissionReport();

		missionReport.setMissionsCategory(crScoreService.getMissionsCategory(resource_id));
		missionReport.setMissionsStatistics(
				crScoreService.getMissionsStatistics(resource_id, crRoleList));

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

		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(resource_id, user_hash);

		List<MissionSchemeObject<MissionContext>> missionSchemeObjectList = crRoleService
				.getBaseMissionByRole(crRole);

		List<MissionSchemeObject<TrainingRecordMissionRsp>> missionSchemeList = missionSchemeObjectList
				.stream().map(bo -> {
					MissionSchemeObject<TrainingRecordMissionRsp> schemeListMissionRsp = new MissionSchemeObject<>();
					schemeListMissionRsp.setStage(bo.getStage());
					schemeListMissionRsp.setDynamic(bo.getDynamic());

					List<TrainingRecordMissionRsp> trainingRecordMissionRsps = BeanUtils
							.deepCopyList(bo.getMissions(), TrainingRecordMissionRsp.class);

					trainingRecordMissionRsps.forEach(trainingRecordMissionRsp -> {

						CrTask crTask = crTaskService.getByUserIdAndMissionId(user_hash,
								trainingRecordMissionRsp.getResourceId());

						TrainingRecord trainingRecord = new TrainingRecord();
						if (crTask != null) {
							trainingRecord.setRecord(crTask.getUserAnswer());
							trainingRecord.setTime(crTask.getLastSubmitTime());
						}
						trainingRecordMissionRsp.setTrainingRecord(trainingRecord);

					});

					schemeListMissionRsp.setMissions(trainingRecordMissionRsps);

					return schemeListMissionRsp;
				}).collect(Collectors.toList());

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
		TrainingReport trainingReport = new TrainingReport();
		TeamRoomData teamRoomdata = teamRoomService.getTeamRoomDataByRoomId(resource_id);

		if (TeamRoom.TrainingStatusCode.UNOPENED.getCode()
				.equals(teamRoomdata.getTrainingStatus())) {
			return new BaseRsp<>(new TrainingReport());
		}

		RoleQuery roleQuery = new RoleQuery(resource_id,
				CrRole.TeamRoleGroupCode.STUDENT.getCode());

		List<CrRole> crRoleList = crRoleService.selectCrRoleByRoomId(roleQuery);

		List<String> userIds = Lists.newArrayList();
		for (CrRole crRole : crRoleList) {
			userIds.addAll(JsonUtil.toJsonObjectArray(crRole.getUserIds(), String.class));
		}

		UserPageContext userPageContext = new UserPageContext();
		userPageContext.setIds(String.join(",", userIds));
		List<UserInfo> userInfoList = remoteUserService.getLazyUserList(userPageContext);
		Map<String, UserInfo> userInfoMap = userInfoList.stream()
				.collect(Collectors.toMap(UserInfo::getUserId, bo -> bo));

		// 获取每个学员的任务信息
		List<CrScore> studentScoreList = crScoreService.selectByRoomIdAndUserIds(resource_id,
				userIds);
		Map<String, CrScore> studentScoreMap = studentScoreList.stream()
				.collect(Collectors.toMap(CrScore::getUserId, bo -> bo));

		// 获取每个学员已完成的任务数量
		List<CrTask> finishedTasks = crTaskService.getFinishedByRoomIdAndUserIds(resource_id,
				userIds);
		Map<String, Integer> studentTaskMap = Maps.newHashMap();
		for (CrTask crRole : finishedTasks) {
			Integer count = studentTaskMap.getOrDefault(crRole.getUserId(), 0) + 1;
			studentTaskMap.put(crRole.getUserId(), count);
		}
		List<UserReport> userReportList = Lists.newArrayList();

		// 遍历每个学员，设置任务统计结果
		for (CrRole crRole : crRoleList) {
			List<MissionStageScheme> missionStageSchemes = JsonUtil
					.toJsonObjectArray(crRole.getMissionScheme(), MissionStageScheme.class);
			Integer missionCount = 0;
			for (MissionStageScheme stage : missionStageSchemes) {
				missionCount += stage.getMissions().size();
			}
			List<String> userList = JsonUtil.toJsonObjectArray(crRole.getUserIds(), String.class);
			for (String userId : userList) {
				CrScore crScore = studentScoreMap.get(userId);
				UserReport userReport = new UserReport();
				userReport.setUserId(userId);
				userReport.setUserName(userInfoMap.get(userId).getName());
				userReport.setRoleName(crRole.getName());
				List<Integer> list = Lists.newArrayList();
				list.add(studentTaskMap.getOrDefault(userId, 0));
				list.add(missionCount);
				userReport.setMissionCompleted(list);
				userReport.setComment(crScore == null ? "" : crScore.getComment());
				userReport.setScore(crScore == null ? 0 : crScore.getFinalScore());
				userReportList.add(userReport);
			}

		}

		Date endTime = teamRoomdata.getSceneEndTime() == null ? new Date()
				: teamRoomdata.getSceneEndTime();
		Date startTime = teamRoomdata.getSceneStartTime() == null ? teamRoomdata.getStartTime()
				: teamRoomdata.getSceneStartTime();
		Integer timeSeconds = Math.toIntExact((endTime.getTime() - startTime.getTime()) / 1000);

		trainingReport.setSceneStartTime(startTime);
		trainingReport.setTeacherName(UserInfoContext.getUserContext().getUserName());
		trainingReport.setTrainingTime(timeSeconds);
		trainingReport.setUsers(userReportList);

		return new BaseRsp<>(trainingReport);
	}

}
