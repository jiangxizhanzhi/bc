package com.cyberpeace.cr.teamtrain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionSchemeService;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleContext;
import com.cyberpeace.cr.teamtrain.controller.rsp.MissionDynamicRsp;
import com.cyberpeace.cr.teamtrain.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamScore;
import com.cyberpeace.cr.teamtrain.service.TeamDynamicEventService;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamScoreService;
import com.cyberpeace.cr.teamtrain.service.context.DynamicData;
import com.cyberpeace.cr.teamtrain.service.context.StatisticalInfo;
import com.cyberpeace.cr.teamtrain.service.context.TaskCounter;
import com.cyberpeace.cr.teamtrain.service.context.TrainingScore;
import com.google.common.collect.Lists;

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
	public BaseRsp<String> getTrainingMissionReport(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		CrRoleContext crRole = crRoleService.getRoles(resource_id);

		return null;
	}

}
