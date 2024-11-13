package com.cyberpeace.cr.crbase.controller;

import java.sql.Timestamp;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.context.MissionStageScheme;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.MissionEvaluationListReq;
import com.cyberpeace.cr.crbase.controller.req.StudentScoreReq;
import com.cyberpeace.cr.crbase.controller.rsp.MissionDynamicRsp;
import com.cyberpeace.cr.crbase.controller.rsp.MissionEvaluationRsp;
import com.cyberpeace.cr.crbase.controller.rsp.TrainingRecordMissionRsp;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.mission.MissionReport;
import com.cyberpeace.cr.crbase.service.context.score.StudentScoreRsp;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

/**
 * 靶场得分Controller
 *
 * @author SN
 * @date 2024/11/12
 */
@Api(tags = "团队训练评分")
@RestController
@RequestMapping("/cr/training_team/training_team")
@Slf4j
public class CrScoreController {

	/**
	 * 靶场岗位Service
	 */
	@Autowired
	private CrRoleService crRoleService;

	/**
	 * 靶场任务Service
	 */
	@Autowired
	private CrTaskService crTaskService;

	/**
	 * 靶场得分Service
	 */
	@Autowired
	private CrScoreService crScoreService;

	/**
	 * 远程用户Service
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 任务Service
	 */
	@Autowired
	private MissionService missionService;

	/**
	 * 训练记录Service
	 */
	@Autowired
	private TrainRecordService trainRecordService;


	/**
	 * 获取学员得分
	 *
	 * @param resource_id 资源id
	 * @param user_hash   用户id
	 * @return {@code StudentScoreRsp }
	 */
	public StudentScoreRsp getStudentScore(String resource_id, String user_hash) {

		List<TaskContext> taskList = crTaskService.getByRoomIdAndUser(resource_id, user_hash);

		Integer getScoreObjective = 0;

		Integer getScoreSubjective = 0;

		Integer sumScoreObjective = 0;

		Integer sumScoreSubjective = 0;

		TrainingSummary trainingSummary = new TrainingSummary();

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

		trainingSummary.setSumScoreSubjective(sumScoreSubjective);
		trainingSummary.setSumScoreObjective(sumScoreObjective);
		trainingSummary.setGetScoreSubjective(getScoreSubjective);
		trainingSummary.setGetScoreObjective(getScoreObjective);

		CrScore crScore = crScoreService.getByRoomIdAndUser(resource_id, user_hash);
		StudentScoreRsp studentScoreRsp = new StudentScoreRsp();
		if (crScore != null) {
			studentScoreRsp = BeanUtils.copy(crScore, StudentScoreRsp.class);
			studentScoreRsp.setAbilityScore(
					JsonUtil.toJsonObjectArray(crScore.getSkillScore(), Integer.class));
			studentScoreRsp.setAbilityPercentage(crScore.getSkillPercentage());
		}

		studentScoreRsp.setTrainingSummary(trainingSummary);

		return studentScoreRsp;
	}

	/**
	 * 更新学员得分
	 *
	 * @param studentScoreReq 学员得分请求体
	 */
	public void updateStudentScore(StudentScoreReq studentScoreReq) {

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


		CrScore crScore = crScoreService.getByRoomIdAndUser(studentScoreReq.getRoomId(),
				studentScoreReq.getUser());
		CrScore newCrScore = getTeamScoreFromReq(studentScoreReq);

		if (crScore == null) {
			crScoreService.save(newCrScore);
		} else {
			newCrScore.setResourceId(crScore.getResourceId());
			crScoreService.updateById(newCrScore);

		}

	}

	/**
	 * 获取任务评估
	 *
	 * @param missionEvaluationListReq 任务评估列表请求体
	 * @return {@code BaseRsp<List<MissionEvaluationRsp>> }
	 */
	public BaseRsp<List<MissionEvaluationRsp>>
			getMissionEvaluations(MissionEvaluationListReq missionEvaluationListReq) {

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

	/**
	 * 获取团队得分从请求体
	 *
	 * @param studentScoreReq 学员得分请求体
	 * @return {@code CrScore }
	 */
	private CrScore getTeamScoreFromReq(StudentScoreReq studentScoreReq) {
		CrScore crScore = BeanUtils.copy(studentScoreReq, CrScore.class);

		crScore.setRoomId(studentScoreReq.getRoomId());
		crScore.setUserId(studentScoreReq.getUser());
		crScore.setSkillScore(studentScoreReq.getAbilityScore());
		crScore.setSkillPercentage(studentScoreReq.getAbilityPercentage());
		crScore.setRoomId(studentScoreReq.getRoomId());

		return crScore;
	}

	/**
	 * 获取用户任务动态
	 *
	 * @param resource_id 资源id
	 * @param student_id  学员id
	 * @return {@code MissionDynamicRsp }
	 */
	public MissionDynamicRsp getUserMissionDynamic(String resource_id, String student_id) {
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

		MissionDynamicRsp missionDynamicRsp = new MissionDynamicRsp();

		missionDynamicRsp.setDynamicData(dynamicDataList);
		missionDynamicRsp.setStatisticalInfo(statisticalInfoList);

		return missionDynamicRsp;
	}

	/**
	 * 获取训练任务报告
	 *
	 * @param resource_id 资源id
	 * @param crRoleList  靶场岗位列表
	 * @return {@code MissionReport }
	 */
	public MissionReport getTrainingMissionReport(String resource_id, List<CrRole> crRoleList) {

		MissionReport missionReport = new MissionReport();

		missionReport
				.setMissionsCategory(crScoreService.getMissionsCategory(resource_id, crRoleList));
		missionReport.setMissionsStatistics(
				crScoreService.getMissionsStatistics(resource_id, crRoleList));

		return missionReport;
	}

	/**
	 * 获取训练记录
	 *
	 * @param resource_id 资源id
	 * @param user_hash   用户id
	 * @return {@code List<MissionSchemeObject<TrainingRecordMissionRsp>> }
	 */
	public List<MissionSchemeObject<TrainingRecordMissionRsp>> getTrainingRecord(String resource_id,
			String user_hash) {

		CrRole crRole = crRoleService.getCrRoleByRoomIdAndUserId(resource_id, user_hash);
		TrainRecord trainRecord = trainRecordService.getByRoomId(crRole.getRoomId());
		JSONObject trainRecordMessage = getTrainRecordMessage(trainRecord);
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
							trainingRecordMissionRsp.setTraininStatus(crTask.getStatus());
							if (crTask.getBusinessType()
									  .equals(BusinessType.TRAINING_SINGLE.getCode())){
								if (trainRecordMessage != null && trainRecordMessage.get(crTask.getMissionId()) != null) {
									String time = ((JSONObject) trainRecordMessage.get(crTask.getMissionId())).get("time").toString();
									if (StringUtils.isNotEmpty(time)) {
										trainingRecord.setTime(new Timestamp(Long.parseLong(time)));
									}
								}
							}else {
								trainingRecord.setTime(crTask.getLastSubmitTime());
							}
						} else {
							trainingRecordMissionRsp
									.setTraininStatus(CrTask.TaskStatus.UNRELEASED.getCode());

						}
						trainingRecordMissionRsp.setTrainingRecord(trainingRecord);

					});

					schemeListMissionRsp.setMissions(trainingRecordMissionRsps);

					return schemeListMissionRsp;
				}).collect(Collectors.toList());

		return missionSchemeList;
	}

	/**
	 * 获取训练报告
	 *
	 * @param resource_id 资源id
	 * @param crRoleList  靶场岗位列表
	 * @return {@code List<UserReport> }
	 */
	public List<UserReport> getTrainingReport(String resource_id, List<CrRole> crRoleList) {

		List<String> userIds = Lists.newArrayList();

		for (CrRole crRole : crRoleList) {
			userIds.addAll(crRole.getUserIdList());
		}

		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);
		Set<String> userIdSet = Sets.newHashSet(userIds);
		if (userIdSet.size() != userInfoMap.size()) {
			log.error("User info missing! userIds = [{}]", StringUtils.join(userIds, ","));
			throw new BusinessException(ErrorCode.USER_INFO_MISSING);
		}
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
			int missionCount = 0;
			for (MissionStageScheme stage : crRole.getMissionStageSchemes()) {
				missionCount += stage.getMissions().size();
			}
			List<String> userList = crRole.getUserIdList();
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

		return userReportList;
	}

	/**
	 * 获取模板i18n
	 *
	 * @return {@code JSONObject }
	 */
	public JSONObject getTemplateI18n() {
		JSONObject jsonObject = new JSONObject();

		for (TrainingReport.TemplateI18n enumData : TrainingReport.TemplateI18n.values()) {
			jsonObject.put(enumData.name(), enumData.getMsg());
		}

		return jsonObject;
	}

	/**
	 * 获取标题
	 *
	 * @param name 名称
	 * @return {@code String }
	 */
	public String getTitle(String name) {

		if (!CommonContext.isChinese()) {
			return "Cyber Exercise Report of " + name;
		} else {
			return name + "训练的训练报告";
		}
	}

	/**
	 * 获取训练记录消息
	 *
	 * @param trainRecord 训练记录
	 * @return {@code JSONObject }
	 */
	private JSONObject getTrainRecordMessage(TrainRecord trainRecord) {
		if (trainRecord == null) {
			return new JSONObject();
		}
		String extra = trainRecord.getExtra();
		JSONObject missionsRecord = new JSONObject();
		if (StringUtils.isNotEmpty(extra)) {
			JSONObject jsonObject;
			jsonObject = JSON.parseObject(extra);
			if (jsonObject != null) {
				missionsRecord = (JSONObject) jsonObject.get("missions_record");
			}
		}
		return missionsRecord;
	}
}
