package com.cyberpeace.cr.crbase.service.impl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.TrainReportContext;
import com.cyberpeace.cr.crbase.service.context.UserData;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.crbase.dao.mapper.CrScoreMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.service.context.TrainingSummary;
import com.cyberpeace.cr.crbase.service.context.mission.MissionCategory;
import com.cyberpeace.cr.crbase.service.context.mission.MissionsStatistics;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleMissionSchemeStage;
import com.cyberpeace.cr.crbase.service.context.score.*;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Maps;

/**
 *
 */
@Service
public class CrScoreServiceImpl extends ServiceImpl<CrScoreMapper, CrScore> implements CrScoreService {

	@Autowired
	private CrScoreMapper crScoreMapper;

	@Autowired
	private MissionService missionService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private CrTaskMapper crTaskMapper;

	@Autowired
	private RemoteUserService remoteUserService;
	@Autowired
	private CrConstantsService crConstantsService;
	@Override
	public CrScore getByRoomIdAndUser(String roomId, String userId) {
		return crScoreMapper.getByRoomIdAndUser(roomId, userId);
	}

	@Override
	public MissionsStatistics getMissionsStatistics(String roomId, List<CrRole> crRoleList) {
		MissionsStatistics missionsStatistics = new MissionsStatistics();

		Integer missionCount = 0;
		for (CrRole crRole : crRoleList) {
			Integer roleMissionCount = crRoleService.getMissionCount(crRole);
			List<String> userList = crRole.getUserIdList();
			missionCount += roleMissionCount * userList.size();
		}
		List<String> crRoleIds = StreamApiUtils.map(crRoleList, CrRole::getResourceId);

		missionsStatistics.setMissionCount(missionCount);
		missionsStatistics.setUnpublishedProportion(
				missionCount - crTaskService.countByRoomIdAndRoleIds(roomId, crRoleIds));
		missionsStatistics.setNotSubmitProportion(
				crTaskService.countUnsubmittedByRoomIdAndRoleIds(roomId, crRoleIds));
		missionsStatistics.setNotFinishProportion(
				crTaskService.countUnfinishedByRoomIdAndRoleIds(roomId, crRoleIds));
		missionsStatistics.setFinishProportion(
				crTaskService.countFinishedByRoomIdAndRoleIds(roomId, crRoleIds));
		return missionsStatistics;
	}

	@Override
	public List<CrScore> selectByRoomIdAndUserIds(String roomId, List<String> userIds) {
		return crScoreMapper.selectByRoomIdAndUserIds(roomId, userIds);
	}

	@Override
	public Map<String, Integer> getMissionsCategory(String roomId, List<CrRole> crRoleList) {
		Map<String, Integer> missionsCategory = Maps.newHashMap();

		List<Mission> missionList = missionService.batchGetByRoomAndCrRoles(roomId, crRoleList);

		List<MissionCategory> missionCategories = missionService.listCategories();

		Map<String, String> missionCategoryMap = missionCategories.stream()
				.collect(Collectors.toMap(MissionCategory::getResourceId, bo -> bo.getName()));

		for (Mission mission : missionList) {
			Integer count = missionsCategory
					.getOrDefault(missionCategoryMap.get(mission.getCategoryId()), 0) + 1;
			missionsCategory.put(missionCategoryMap.get(mission.getCategoryId()), count);
		}

		return missionsCategory;
	}

	@Override
	public List<CrScore> getScoreByRoomId(String roomId) {
		return crScoreMapper.selectByRoomId(roomId);
	}

	@Override
	public List<StudentScoreListRsp> getScoreListRsp(String roomId, List<CrRole> roleList) {

		// get score
		List<CrScore> scoreList = getScoreByRoomId(roomId);
		// 按评分时间倒序
		scoreList.sort(Comparator.comparing(CrScore::getUpdateTime).reversed());
		Set<String> scoredUsers = scoreList.stream().map(CrScore::getUserId)
				.collect(Collectors.toSet());

		// get users
		List<StudentScoreListRsp> rspList = Lists.newArrayList();
		List<String> userIdList = Lists.newArrayList();
		for (CrRole role : roleList) {
			List<String> userIds = JsonUtil.toJsonObjectArray(role.getUserIds(), String.class);
			for (String userId : userIds) {
				StudentScoreListRsp rsp = new StudentScoreListRsp();
				rsp.setUserId(userId);
				rsp.setRoleId(role.getResourceId());
				rsp.setRoleName(role.getName());
				rsp.setRoleGroup(role.getRoleGroup());
				rspList.add(rsp);
				userIdList.add(userId);
			}
		}

		// set user info
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIdList);
		for (StudentScoreListRsp rsp : rspList) {
			UserInfo userInfo = userInfoMap.get(rsp.getUserId());
			if (null != userInfo) {
				rsp.setUserAvatar(userInfo.getAvatarUrl());
				rsp.setName(userInfo.getName());
			}
		}
		// 未评分的在前
		List<StudentScoreListRsp> res = Lists.newArrayList();
		for (StudentScoreListRsp rsp : rspList) {
			String userId = rsp.getUserId();
			if (!scoredUsers.contains(userId)) {
				rsp.setScored(false);
				res.add(rsp);
			}
		}
		Map<String, StudentScoreListRsp> rspMap = StreamApiUtils.toMap(rspList,
				StudentScoreListRsp::getUserId, rsp -> rsp);
		// 按排过序的crScore列表顺序插入结果
		for (CrScore score : scoreList) {
			StudentScoreListRsp rsp = rspMap.get(score.getUserId());
			if (null != rsp) {
				rsp.setScore(score.getFinalScore());
				rsp.setScoreTime(score.getUpdateTime());
				rsp.setScored(true);
				res.add(rsp);
			}
		}
		return res;
	}

	@Override
	public StudentScoreRsp getStudentScore(String roomId, String userId) {
		// get task score
		List<TaskScoreContext> taskList = crTaskMapper.selectScoreContextByRoomIdAndUserId(roomId,
				userId);
		Map<String, TaskScoreContext> scoreContextMap = StreamApiUtils.toMap(taskList,
				TaskScoreContext::getMissionId, context -> context);

		// get role
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		// get stages
		List<CrRoleMissionSchemeStage> stageList = JsonUtil
				.toJsonObjectArray(role.getMissionScheme(), CrRoleMissionSchemeStage.class);
		TrainingScore trainingScore = new TrainingScore();
		TrainingSummary trainingSummary = new TrainingSummary();

		for (CrRoleMissionSchemeStage stage : stageList) {
			StageNameScore stageNameScore = new StageNameScore();
			stageNameScore.setName(stage.getStage());
			for (String missionId : stage.getMissions()) {
				TaskScoreContext context = scoreContextMap.get(missionId);
				if (null == context) {
					// 任务未下发
					continue;
				}
				NameScore missionScore = new NameScore();
				missionScore.setName(context.getMissionTitle());
				// 未提交、未评估分数设为0，
				if (null != context.getTaskScore()) {
					missionScore.setScore(context.getTaskScore());
					// 统计得分
					trainingScore.addScore(context.getTaskScore());
					trainingSummary.addScore(context.getTaskScore(), context.getMissionType());
				}
				// 统计总分
				trainingScore.addTotal(context.getMissionScore());
				trainingSummary.addTotal(context.getMissionScore(), context.getMissionType());

				stageNameScore.getMissions().add(missionScore);
			}
			if (!stageNameScore.getMissions().isEmpty()) {
				stageNameScore.sum();
				trainingScore.getStages().add(stageNameScore);
			}
		}

		// get score
		CrScore crScore = getByRoomIdAndUser(roomId, userId);
		StudentScoreRsp rsp = new StudentScoreRsp();
		if (crScore != null) {
			// 已被评分过
			rsp.setAbilityPercentage(crScore.getSkillPercentage());
			rsp.setAbilityScore(JsonUtil.toJsonObjectArray(crScore.getSkillScore(), Integer.class));
			rsp.setComment(crScore.getComment());
			rsp.setFinalScore(crScore.getFinalScore());
			rsp.setTrainingPercentage(crScore.getTrainingPercentage());
		}
		// 任务统计
		TaskStatistics taskStatistics = new TaskStatistics();
		long successful = taskList.stream().filter(TaskScoreContext::isSuccessful).count();
		long failed = taskList.stream().filter(TaskScoreContext::isFailed).count();
		long notEvaluated = taskList.stream().filter(TaskScoreContext::isNotEvaluated).count();
		taskStatistics.setSuccessful((int) successful);
		taskStatistics.setFailed((int) failed);
		taskStatistics.setNotEvaluated((int) notEvaluated);
		taskStatistics.setMissionCount(taskList.size());
		taskStatistics.calculate();

		// 组装返回
		rsp.setTrainingSummary(trainingSummary);
		rsp.setTaskStatistics(taskStatistics);
		rsp.setTrainingScore(trainingScore);

		return rsp;
	}

	@Override
	public TrainReportContext getTrainReport(String roomId, ArrayList<Integer> roles,ScoreTrendReq scoreTrendReq) {
		TrainReportContext trainReportContext = new TrainReportContext();
		trainReportContext.initTimeList(scoreTrendReq);
		List<CrRole> crRoles = crRoleService.getRolesByRoomAndGroups(roomId, roles);
		List<String> userIds = crRoles.stream().map(CrRole::getUserIdList)
				.flatMap(Collection::stream).collect(Collectors.toList());
		Map<String, CrRole> userIdRoleMap = crRoles.stream()
				.map(crRole -> crRole.getUserIdList().stream().map(b -> new UserIdRole(b, crRole))
						.collect(Collectors.toList()))
				.flatMap(Collection::stream)
				.collect(Collectors.toMap(UserIdRole::getUserId, UserIdRole::getRole, (a, b) -> a));

		// 评分数据
		List<CrScore> crScores = this.selectByRoomIdAndUserIds(roomId, userIds);
		Map<String, CrScore> scoreMap = crScores.stream()
				.collect(Collectors.toMap(CrScore::getUserId, Function.identity(), (a, b) -> a));

		List<CrTask> taskList = crTaskService.getByRoomId(roomId);
		Map<String, List<CrTask>> taskMap = taskList.stream()
				.collect(Collectors.groupingBy(CrTask::getUserId));
		Set<String> missionIds = taskList.stream().map(CrTask::getMissionId)
				.collect(Collectors.toSet());
		List<Mission> missions = CollectionUtils.isEmpty(missionIds)?new ArrayList<>() : missionService.listByIds(missionIds);
		Map<String, Mission> missionMap = missions.stream()
				.collect(Collectors.toMap(Mission::getResourceId, Function.identity()));

		Map<String, List<Mission>> categoryMap = missions.stream()
				.collect(Collectors.groupingBy(Mission::getCategoryId));
		List<CrConstantsConfig> configs = crConstantsService
				.getByType(CrConstantsConfig.ConstantsType.MISSION_CATEGORY.getCode());
		Map<String, CrConstantsConfig> configMap = configs.stream()
				.collect(Collectors.toMap(CrConstantsConfig::getResourceId, Function.identity()));

		List<StudentScoreTrend> scoreTrends = new ArrayList<>();
		List<GetScoreRsp> getScoreRsps = new ArrayList<>();
		Map<String, Integer> kindMap = new HashMap<>();
		List<UserData> userDataList = crRoleService.getUserDatas(userIds);
		List<UserRole>  userRoles = new ArrayList<>();
		for (UserData userData : userDataList) {
			//构建趋势
			List<CrTask> userTaskList = taskMap.getOrDefault(userData.getResourceId(),
					new ArrayList<>());
			List<CrTask> successList = userTaskList.stream().filter(CrTask::isSuccess)
					.collect(Collectors.toList());
			scoreTrends.add(new StudentScoreTrend(userData, successList,
					userIdRoleMap.getOrDefault(userData.getResourceId(),new CrRole()).getRoleGroup(),trainReportContext.getTimeList()));
			//人员得分
			getScoreRsps.add(new GetScoreRsp(userData, userTaskList, missionMap, scoreMap));
			UserRole userRole = new UserRole();
			userRole.setName(userData.getRepName());
			userRole.setLogo(userData.getLogo());
			userRole.setType(userIdRoleMap.getOrDefault(userData.getResourceId(),new CrRole()).getRoleGroup());
			userRole.setRoleName(userIdRoleMap.getOrDefault(userData.getResourceId(),new CrRole()).getName());
			userRoles.add(userRole);
		}
		for (Map.Entry<String, List<Mission>> entry : categoryMap.entrySet()) {
			CrConstantsConfig config = configMap.get(entry.getKey());
			if (config == null)
				continue;
			int size = entry.getValue() == null ? 0 : entry.getValue().size();
			kindMap.put(config.getCnName(), size);
		}

		trainReportContext.initScoreTrends(scoreTrends);
		trainReportContext.setGetScoreRsps(getScoreRsps);
		trainReportContext.setKindMap(kindMap);
		trainReportContext.setUserRoles(userRoles);
		return trainReportContext;
	}
}
