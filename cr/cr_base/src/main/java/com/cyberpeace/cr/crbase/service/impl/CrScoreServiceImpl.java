package com.cyberpeace.cr.crbase.service.impl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cr.crbase.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.CrScoreMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskLogMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.service.*;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.mission.MissionCategory;
import com.cyberpeace.cr.crbase.service.context.mission.MissionsStatistics;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleMissionSchemeStage;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleSimple;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.crbase.service.context.score.*;
import com.cyberpeace.cr.crbase.service.context.task.TaskLogRsp;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.integratation.service.impl.MpEnvService;
import com.google.common.collect.Maps;

/**
 * 靶场得分Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Slf4j
@Service
public class CrScoreServiceImpl extends ServiceImpl<CrScoreMapper, CrScore> implements CrScoreService {

	/**
	 * 靶场得分Mapper
	 */
	@Autowired
	private CrScoreMapper crScoreMapper;

	/**
	 * 任务Service
	 */
	@Autowired
	private MissionService missionService;

	/**
	 * 靶场角色Service
	 */
	@Autowired
	private CrRoleService crRoleService;

	/**
	 * 靶场任务Service
	 */
	@Autowired
	private CrTaskService crTaskService;

	/**
	 * 靶场任务Mapper
	 */
	@Autowired
	private CrTaskMapper crTaskMapper;

	/**
	 * 远程用户Service
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 靶场常数Service
	 */
	@Autowired
	private CrConstantsService crConstantsService;

	/**
	 * 靶场任务日志Mapper
	 */
	@Autowired
	private CrTaskLogMapper crTaskLogMapper;

	/**
	 * 当地场景Service
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * 远程场景Service
	 */
	@Autowired
	private RemoteSceneService remoteSceneService;

	/**
	 * 中台环境Service
	 */
	@Autowired
	private MpEnvService mpEnvService;

	/**
	 * 获取由房间id和用户
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code CrScore }
	 */
	@Override
	public CrScore getByRoomIdAndUser(String roomId, String userId) {
		return crScoreMapper.getByRoomIdAndUser(roomId, userId);
	}

	/**
	 * 获取任务统计学
	 *
	 * @param roomId     房间id
	 * @param crRoleList 靶场角色列表
	 * @return {@code MissionsStatistics }
	 */
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

	/**
	 * 查询由房间id和用户id列表
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id列表
	 * @return {@code List<CrScore> }
	 */
	@Override
	public List<CrScore> selectByRoomIdAndUserIds(String roomId, List<String> userIds) {
		return crScoreMapper.selectByRoomIdAndUserIds(roomId, userIds);
	}

	/**
	 * 获取任务类别
	 *
	 * @param roomId     房间id
	 * @param crRoleList 靶场角色列表
	 * @return {@code Map<String, Integer> }
	 */
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

	/**
	 * 获取得分由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code List<CrScore> }
	 */
	@Override
	public List<CrScore> getScoreByRoomId(String roomId) {
		return crScoreMapper.selectByRoomId(roomId);
	}

	/**
	 * 获取得分列表响应
	 *
	 * @param roomId   房间id
	 * @param roleList 角色列表
	 * @return {@code List<StudentScoreListRsp> }
	 */
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

	/**
	 * 获取学员得分
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code StudentScoreRsp }
	 */
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

	/**
	 * 获取训练报告
	 *
	 * @param roomId        房间id
	 * @param roles         角色
	 * @param scoreTrendReq 得分趋势请求体
	 * @return {@code TrainReportContext }
	 */
	@Override
	public TrainReportContext getTrainReport(String roomId, ArrayList<Integer> roles,
			ScoreTrendReq scoreTrendReq) {

		TrainReportContext trainReportContext = new TrainReportContext();
		trainReportContext.initTimeList(scoreTrendReq);
		List<CrRole> crRoles = crRoleService.getRolesByRoomAndGroups(roomId, roles);
		// 获取用户id
		List<String> userIds = crRoles.stream().map(CrRole::getUserIdList)
				.flatMap(Collection::stream).collect(Collectors.toList());
		List<String> redUserIds = crRoles.stream()
				.filter(a -> CrRole.RbRoleGroup.RED.getCode().equals(a.getRoleGroup()))
				.map(CrRole::getUserIdList).flatMap(Collection::stream)
				.collect(Collectors.toList());
		List<String> blueUserIds = crRoles.stream()
				.filter(a -> CrRole.RbRoleGroup.BLUE.getCode().equals(a.getRoleGroup()))
				.map(CrRole::getUserIdList).flatMap(Collection::stream)
				.collect(Collectors.toList());
		Map<String, CrRole> userIdRoleMap = crRoles.stream()
				.map(crRole -> crRole.getUserIdList().stream().map(b -> new UserIdRole(b, crRole))
						.collect(Collectors.toList()))
				.flatMap(Collection::stream)
				.collect(Collectors.toMap(UserIdRole::getUserId, UserIdRole::getRole, (a, b) -> a));

		// 评分数据
		List<CrScore> crScores = this.selectByRoomIdAndUserIds(roomId, userIds);
		Map<String, CrScore> scoreMap = crScores.stream()
				.collect(Collectors.toMap(CrScore::getUserId, Function.identity(), (a, b) -> a));

		// 查询任务数据
		List<CrTask> taskList = crTaskService.getByRoomId(roomId);
		Map<String, List<CrTask>> taskMap = taskList.stream()
				.collect(Collectors.groupingBy(CrTask::getUserId));
		Set<String> missionIds = crRoles.stream().map(a->{
			CrRoleSimple crRoleSimple = new CrRoleSimple(a);
			return crRoleSimple.getMissionIds();
		}).flatMap(Collection::stream).collect(Collectors.toSet());
		List<Mission> missions = CollectionUtils.isEmpty(missionIds) ? new ArrayList<>()
				: missionService.listByIds(missionIds);
		Map<String, Mission> missionMap = missions.stream()
				.collect(Collectors.toMap(Mission::getResourceId, Function.identity()));
		// 获取人员技能评分
		List<UserSkillScoreContext> skillScoreList = getSkillScoreList(roomId, userIds, taskList,
				missionMap);
		Map<String, UserSkillScoreContext> skillScoreContextMap = skillScoreList.stream()
				.collect(Collectors.toMap(UserSkillScoreContext::getUserId, Function.identity(),
						(a, b) -> a));
		// 动态事件
		List<CrTaskLog> crTaskLogs = crTaskLogMapper.selectReleaseByRoomId(roomId);
		List<TaskLogRsp> taskLogRsps = BeanUtils.deepCopyList(crTaskLogs, TaskLogRsp.class);
		for (TaskLogRsp taskLogRsp : taskLogRsps) {
			if (missionMap.containsKey(taskLogRsp.getMissionId())) {
				taskLogRsp.setName(missionMap.get(taskLogRsp.getMissionId()).getTitle());
				taskLogRsp.setActionName(EnumUtils.getEnumDescByCode(taskLogRsp.getActionCode(),
						TaskLogRsp.ActionCode.class));
				taskLogRsp.setCreateTimeString(
						DateUtil.DateToString(taskLogRsp.getCreateTime(), "MM-dd HH:mm:ss"));
			}
		}
//		Map<String, List<TaskLogRsp>> taskLogMap = taskLogRsps.stream()
//				.collect(Collectors.groupingBy(TaskLogRsp::getUserId));

		List<StudentScoreTrend> scoreTrends = new ArrayList<>();
		List<GetScoreRsp> getScoreRsps = new ArrayList<>();
		List<UserData> userDataList = crRoleService.getUserDatas(userIds);
		List<UserRole> userRoles = new ArrayList<>();
		Map<String, List<MissionSchemeObject<MissionContext>>> missionSchemeMap = crRoleService.getCrRoleMissionByRoomIdAndCrRoles(roomId, crRoles);
		for (UserData userData : userDataList) {
			CrRole crRole = userIdRoleMap.getOrDefault(userData.getResourceId(), new CrRole());
			Integer type = crRole.getRoleGroup();
			String roleName = crRole.getName();
			Integer roleGroup = crRole.getRoleGroup();
			// 得分趋势
			List<CrTask> userTaskList = taskMap.getOrDefault(userData.getResourceId(),
					new ArrayList<>());
			List<String> taskIds = userTaskList.stream().map(CrTask::getResourceId).collect(Collectors.toList());
			List<CrTask> successList = userTaskList.stream().filter(CrTask::isSuccess)
					.collect(Collectors.toList());
			scoreTrends.add(new StudentScoreTrend(userData, successList, roleGroup,
					trainReportContext.getTimeList(),trainReportContext.getTimeStringList()));
			// 人员得分
			List<MissionSchemeObject<MissionContext>> userMissionList = missionSchemeMap.getOrDefault(userData.getResourceId(), new ArrayList<>());
			List<TaskLogRsp> logRsps = taskLogRsps.stream().filter(a -> taskIds.contains(a.getTaskId())).collect(Collectors.toList());
			getScoreRsps.add(new GetScoreRsp(userData, userTaskList, missionMap, scoreMap, crRole, skillScoreContextMap.get(userData.getResourceId()), userMissionList,
					logRsps));
			UserRole userRole = new UserRole();
			userRole.setName(userData.getRepName());
			userRole.setLogo(mpEnvService.getMpHost()+userData.getLogo());
			userRole.setType(type);
			userRole.setRoleName(roleName);
			userRoles.add(userRole);
		}

		//中台数据查询
		JSONArray attackTypeCount;
		JSONArray sceneTrend;
		LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
		String mpSceneId = localScene!=null ?localScene.getMpSceneId() : scoreTrendReq.getReplaySceneId();
		if (StringUtils.isNotBlank(mpSceneId)) {
			//中台攻击手法
			attackTypeCount= remoteSceneService.attackTypeCount(mpSceneId);
			//中台攻击趋势
			sceneTrend = remoteSceneService.sceneTrend(mpSceneId);
			trainReportContext.initMpData(attackTypeCount,sceneTrend);
		}


		getScoreRsps.sort(Comparator.comparing(GetScoreRsp::getSynthesisScore,
				Comparator.nullsFirst(Integer::compareTo).reversed()).thenComparing(GetScoreRsp::getName,Comparator.nullsLast(String::compareTo)));
		trainReportContext.initScoreTrends(scoreTrends);
		trainReportContext.setGetScoreRsps(getScoreRsps);
		userRoles.sort(Comparator.comparing(UserRole::getType));
		trainReportContext.setUserRoles(userRoles);
		// 任务分类图
		trainReportContext.setKindMap(dealCateMap(taskList, userIds));
		trainReportContext.setTaskKindCount(trainReportContext.getKindMap().size());
		trainReportContext.setRedMap(dealCateMap(taskList, redUserIds));
		trainReportContext.setBlueMap(dealCateMap(taskList, blueUserIds));


		return trainReportContext;
	}

	/**
	 * 获取技能得分列表
	 *
	 * @param roomId 房间id
	 * @param roles  角色
	 * @return {@code List<UserSkillScoreContext> }
	 */
	@Override
	public List<UserSkillScoreContext> getSkillScoreList(String roomId, ArrayList<Integer> roles) {
		List<CrTask> taskList = crTaskService.getByRoomId(roomId);
		Map<String, List<CrTask>> taskMap = taskList.stream()
				.collect(Collectors.groupingBy(CrTask::getUserId));
		Set<String> missionIds = taskList.stream().map(CrTask::getMissionId)
				.collect(Collectors.toSet());
		List<Mission> missions = CollectionUtils.isEmpty(missionIds) ? new ArrayList<>()
				: missionService.listByIds(missionIds);
		List<CrRole> crRoles = crRoleService.getRolesByRoomAndGroups(roomId, roles);
		// 获取用户id
		List<String> userIds = crRoles.stream().map(CrRole::getUserIdList)
				.flatMap(Collection::stream).collect(Collectors.toList());
		Map<String, Mission> missionMap = missions.stream()
				.collect(Collectors.toMap(Mission::getResourceId, Function.identity()));
		return getSkillScoreList(roomId, userIds, taskList, missionMap);
	}

	/**
	 * 获取技能得分列表
	 *
	 * @param roomId     房间id
	 * @param userIds    用户id列表
	 * @param taskList   任务列表
	 * @param missionMap 任务图
	 * @return {@code List<UserSkillScoreContext> }
	 */
	@Override
	public List<UserSkillScoreContext> getSkillScoreList(String roomId, List<String> userIds,
			List<CrTask> taskList, Map<String, Mission> missionMap) {
		Map<String, List<CrTask>> taskMap = taskList.stream()
				.collect(Collectors.groupingBy(CrTask::getUserId));
		List<RadarLabel> radarLabel = missionService.getRadarLabel();
		Map<Integer, RadarLabel> radarMap = radarLabel.stream()
				.collect(Collectors.toMap(RadarLabel::getResourceId, Function.identity()));
		List<UserSkillScoreContext> list = new ArrayList<>();
		for (String userId : userIds) {
			UserSkillScoreContext context = new UserSkillScoreContext();
			List<Integer> points = CrCommonUtil.getInitPoints();
			List<Integer> maxPoints = CrCommonUtil.getInitPoints();
			List<CrTask> userTaskList = taskMap.getOrDefault(userId,new ArrayList<>());
			for (CrTask crTask : userTaskList) {
				Mission mission = missionMap.get(crTask.getMissionId());
				List<Integer> mPoints = JsonUtil.toJsonObjectArray(mission.getSkillPoints(),
						Integer.class);
				if (!CollectionUtils.isEmpty(mPoints)) {
					for (int i = 0; i < mPoints.size(); i++) {
						if (crTask.isSuccess()) {
							points.set(i, mPoints.get(i) + points.get(i));
						}
						maxPoints.set(i, mPoints.get(i) + maxPoints.get(i));
					}
				}
			}
			context.setUserId(userId);
			List<SkillScoreContext> skillScoreContextList = new ArrayList<>();
			for (int i = 0; i < points.size(); i++) {
				SkillScoreContext skillScoreContext = new SkillScoreContext(
						radarMap.get(i+1).getLabel(), points.get(i),maxPoints.get(i), i);
				skillScoreContextList.add(skillScoreContext);
			}
			context.setSkillScoreContextList(skillScoreContextList);
			list.add(context);
		}
		return list;
	}

	/**
	 * Deal Cate 地图
	 *
	 * @param taskList 任务列表
	 * @param userIds  用户id列表
	 * @return {@code Map<String, Integer> }
	 */
	public Map<String, Integer> dealCateMap(List<CrTask> taskList, List<String> userIds) {
		Map<String, Integer> kindMap = new HashMap<>();

		Set<String> missionIds = taskList.stream().filter(a -> userIds.contains(a.getUserId()))
				.map(CrTask::getMissionId).collect(Collectors.toSet());
		List<Mission> missions = CollectionUtils.isEmpty(missionIds) ? new ArrayList<>()
				: missionService.listByIds(missionIds);

		Map<String, List<Mission>> categoryMap = missions.stream()
				.collect(Collectors.groupingBy(Mission::getCategoryId));
		List<CrConstantsConfig> configs = crConstantsService
				.getByType(CrConstantsConfig.ConstantsType.MISSION_CATEGORY.getCode());
		Map<String, CrConstantsConfig> configMap = configs.stream()
				.collect(Collectors.toMap(CrConstantsConfig::getResourceId, Function.identity()));
		for (Map.Entry<String, List<Mission>> entry : categoryMap.entrySet()) {
			CrConstantsConfig config = configMap.get(entry.getKey());
			if (config == null) {
				continue;
			}
			int size = entry.getValue() == null ? 0 : entry.getValue().size();
			kindMap.put(config.getCnName(), size);
		}
		return kindMap;
	}

	/**
	 * 获取训练记录
	 *
	 * @param crRoles    靶场角色
	 * @param taskList   任务列表
	 * @param missionMap 任务图
	 */
	public void getTrainRecord(List<CrRole> crRoles, List<CrTask> taskList,
			Map<String, Mission> missionMap) {
		Map<String, List<CrTask>> taskMap = taskList.stream()
				.collect(Collectors.groupingBy(CrTask::getUserId));
		for (CrRole crRole : crRoles) {
			List<MissionStageScheme> missionStageSchemes = crRole.getMissionStageSchemes();
			for (String userId : crRole.getUserIdList()) {
				List<CrTask> userTaskList = taskMap.get(userId);

			}
		}

	}
}
