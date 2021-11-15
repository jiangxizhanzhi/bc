package com.cyberpeace.cr.teamtrain.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionSchemeService;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleContext;
import com.cyberpeace.cr.crbase.service.context.role.RoleData;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.teamtrain.controller.req.StudentScoreReq;
import com.cyberpeace.cr.teamtrain.controller.rsp.MissionDynamicRsp;
import com.cyberpeace.cr.teamtrain.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoomData;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamScore;
import com.cyberpeace.cr.teamtrain.service.TeamDynamicEventService;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamScoreService;
import com.cyberpeace.cr.teamtrain.service.context.*;
import com.cyberpeace.integratation.model.UserPageContext;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "团队训练评分")
@RestController
@RequestMapping("/cr/training_team/training_team")
public class TeamTrainScoreController {

	@Autowired
	TeamRoomService teamRoomService;

	@Autowired
	CrRoleService crRoleService;

	@Autowired
	CrTaskService crTaskService;

	@Autowired
	BusinessTemplateService businessTemplateService;

	@Autowired
	TeamScoreService teamScoreService;

	@Autowired
	TeamDynamicEventService teamDynamicEventService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	MissionSchemeService missionSchemeService;

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
		// studentScoreRsp.setTraining_percentage();

		for (TaskContext task : taskList) {
			Mission mission = task.getMission();
			// mission.is_objective_mission
			getScoreObjective += task.getScore();
			sumScoreObjective += mission.getScore();

			sumScoreSubjective += task.getScore();
			sumScoreSubjective += mission.getScore();

		}

		TeamScore teamScore = teamScoreService.getByRoomIdAndUser(resource_id, user_hash);

		StudentScoreRsp studentScoreRsp = BeanUtils.copy(teamScore, StudentScoreRsp.class);
		studentScoreRsp.setAbilityScore(
				JsonUtil.toJsonObjectArray(teamScore.getSkillScore(), Integer.class));
		studentScoreRsp.setTrainingScore(trainingScore);

		return new BaseRsp<>();
	}

	@ApiOperation("提交学员评分")
	@PostMapping("/student_score")
	public BaseRsp<StudentScoreRsp> updateStudentScore(@ApiParam(name = "studentScoreReq",
			value = "学员评分") @RequestBody StudentScoreReq studentScoreReq) {

		teamRoomService.checkRoomOpening(studentScoreReq.getTrainingTeam());

		Integer trainingPercentage = Integer
				.parseInt(studentScoreReq.getTrainingPercentage().replace("%", ""));
		if (trainingPercentage < TeamScore.ScoreValue.MIN_PERCENTAGE.getCode()
				|| trainingPercentage > TeamScore.ScoreValue.MAX_PERCENTAGE.getCode()) {
			throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
		}
		Integer abilityPercentage = Integer
				.parseInt(studentScoreReq.getAbilityPercentage().replace("%", ""));
		if (abilityPercentage < TeamScore.ScoreValue.MIN_PERCENTAGE.getCode()
				|| abilityPercentage > TeamScore.ScoreValue.MAX_PERCENTAGE.getCode()) {
			throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
		}
		if (abilityPercentage + trainingPercentage != TeamScore.ScoreValue.SUM_OF_PERCENTAGE_INT
				.getCode()) {
			throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);

		}

		List<Integer> abilityScoreList = JsonUtil
				.toJsonObjectArray(studentScoreReq.getAbilityScore(), Integer.class);

		if (abilityScoreList.size() != TeamScore.ScoreValue.ABILITY_SCORE_NUM.getCode()) {
			throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
		}
		IntSummaryStatistics stats = abilityScoreList.stream().mapToInt((x) -> x)
				.summaryStatistics();
		if (stats.getMax() > TeamScore.ScoreValue.MAX_ABILITY_SCORE_VALUE.getCode()
				|| stats.getMin() < TeamScore.ScoreValue.MIN_ABILITY_SCORE_VALUE.getCode()) {
			throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
		}

		if (studentScoreReq.getComment().length() > TeamScore.ScoreValue.MAX_COMMENT_LEN
				.getCode()) {
			throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
		}

		TeamScore teamScore = teamScoreService.getByRoomIdAndUser(studentScoreReq.getTrainingTeam(),
				studentScoreReq.getUser());
		TeamScore newTeamScore = getTeamScoreFromReq(studentScoreReq);

		if (teamScore == null) {
			teamScoreService.save(newTeamScore);
		} else {
			newTeamScore.setResourceId(teamScore.getResourceId());
			teamScoreService.updateById(newTeamScore);

		}

		return new BaseRsp<>();
	}

	private TeamScore getTeamScoreFromReq(StudentScoreReq studentScoreReq) {
		TeamScore teamScore = BeanUtils.copy(studentScoreReq, TeamScore.class);
		teamScore.setTeamInstanceId(studentScoreReq.getTrainingTeam());
		teamScore.setUserId(studentScoreReq.getUser());
		teamScore.setSkillScore(studentScoreReq.getAbilityScore());
		teamScore.setSkillPercentage(studentScoreReq.getAbilityPercentage());
		teamScore.setTeamInstanceId(studentScoreReq.getTrainingTeam());

		return teamScore;
	}

	/**
	 * 获取动态事件
	 *
	 * @return
	 */
	@ApiOperation("获取动态事件")
	@GetMapping("/get_user_mission_dynamic")
	public BaseRsp<MissionDynamicRsp> getUserMissionDynamic(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id,
			@ApiParam(name = "student_id", value = "用户id", required = true) String student_id) {
		MissionDynamicRsp missionDynamicRsp = new MissionDynamicRsp();
		TaskCounter taskCounter = teamScoreService.getTaskCounter(resource_id, student_id);

		List<StatisticalInfo> statisticalInfoList = Lists.newArrayList();

		StatisticalInfo statisticalInfo = new StatisticalInfo();
		statisticalInfo.setStatistical_state(TaskCounter.TaskType.FINISHED.getCode());
		statisticalInfo.setValue(taskCounter.getFinished());
		statisticalInfoList.add(statisticalInfo);

		statisticalInfo.setStatistical_state(TaskCounter.TaskType.RELEASED.getCode());
		statisticalInfo.setValue(taskCounter.getReleased());
		statisticalInfoList.add(statisticalInfo);

		statisticalInfo.setStatistical_state(TaskCounter.TaskType.NOTRELEASED.getCode());
		statisticalInfo.setValue(taskCounter.getNotReleased());
		statisticalInfoList.add(statisticalInfo);

		// getByRoomIdAndUserId(resource_id, student_id);
		// todo 获取动态事件日志

		List<DynamicData> s = null;
		missionDynamicRsp.setDynamicData(s);

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
		MissionReport missionReport = new MissionReport();
		Map<String, Integer> missions_category = Maps.newHashMap();
		CrRoleContext crRole = crRoleService.getRoles(resource_id);
		List<String> missionIds = Lists.newArrayList();

		for (RoleData roleData : crRole.getRoles()) {
			for (MissionSchemeObject missions : roleData.getMissionsList()) {
				for (MissionContext missionContext : missions.getMissions()) {
					Integer count = missions_category.getOrDefault(missionContext.getCategoryId(),
							0) + 1;
					missions_category.put(missionContext.getCategoryId(), count);
				}

			}
		}
		missionReport.setMissionsCategory(missions_category);
		missionReport.setMissionsStatistics(teamScoreService.getMissionsStatistics(resource_id));

		return new BaseRsp<>(missionReport);
	}

	/**
	 * 获取训练报告 - 任务信息统计
	 *
	 * @return
	 */
	@ApiOperation("获取用户训练记录")
	@GetMapping("/get_training_record")
	public BaseRsp<List<MissionSchemeObject>> getTrainingRecord(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id,
			@ApiParam(name = "user_hash", value = "用户id", required = true) String user_hash) {
		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(resource_id, user_hash);

		List<MissionSchemeObject> missionSchemeObjectList = crRoleService
				.getBaseMissionByRole(crRole);

		missionSchemeObjectList.forEach(missionSchemeObject -> {

			List<MissionContext> missionContextList = new ArrayList<>();
			missionSchemeObject.getMissions().forEach(mission -> {
				MissionContext newMission = new MissionContext();
				newMission.setResourceId(mission.getResourceId());
				newMission.setTitle(mission.getTitle());
				newMission.setDescription(mission.getDescription());
				CrTask crTask = crTaskService.getByUserIdAndMissionId(user_hash,
						mission.getResourceId());

				TrainingRecord trainingRecord = new TrainingRecord();
				if (crTask != null) {
					trainingRecord.setRecord(crTask.getUserAnswer());
					trainingRecord.setTime(crTask.getLastSubmitTime());
				}
				newMission.setTrainingRecord(trainingRecord);
				missionContextList.add(newMission);

			});
			missionSchemeObject.setMissions(missionContextList);

		});
		return new BaseRsp<>(missionSchemeObjectList);
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
		TeamRoomData teamRoomdata = teamRoomService.getTeamRoomRspByRoomId(resource_id);
		if (null == teamRoomdata.getSceneStartTime()) {
			return new BaseRsp<>(new TrainingReport());
		}

		RoleQuery roleQuery = new RoleQuery();
		roleQuery.setRoomId(resource_id);
		roleQuery.setRoleGroup(CrRole.RoleGroupCodeInteger.STUDENT.getValue());

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
		List<TeamScore> studentScoreList = teamScoreService.selectByRoomIdAndUserIds(resource_id,
				userIds);
		Map<String, TeamScore> studentScoreMap = studentScoreList.stream()
				.collect(Collectors.toMap(TeamScore::getUserId, bo -> bo));

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
				TeamScore teamScore = studentScoreMap.get(userId);
				UserReport userReport = new UserReport();
				userReport.setUserId(userId);
				userReport.setUserName(userInfoMap.get(userId).getName());
				userReport.setRoleName(crRole.getName());
				List<Integer> list = Lists.newArrayList();
				list.add(studentTaskMap.getOrDefault(userId, 0));
				list.add(missionCount);
				userReport.setMissionCompleted(list);
				userReport.setComment(teamScore == null ? "" : teamScore.getComment());
				userReport.setScore(teamScore == null ? 0 : teamScore.getFinalScore());
				userReportList.add(userReport);
			}

		}

		trainingReport.setSceneStartTime(teamRoomdata.getSceneStartTime());
		Date endTime = teamRoomdata.getEndTime() == null ? new Date()
				: teamRoomdata.getSceneStartTime();
		Integer timeSeconds = Math.toIntExact(
				(endTime.getTime() - teamRoomdata.getSceneStartTime().getTime()) / 1000);
		trainingReport.setTeacherName(UserInfoContext.getUserContext().getUserName());
		trainingReport.setTrainingTime(timeSeconds);
		trainingReport.setUsers(userReportList);

		return new BaseRsp<>(trainingReport);
	}

}
