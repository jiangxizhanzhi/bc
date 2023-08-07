package com.cyberpeace.cr.crbase.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.MissionFlagQuery;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.*;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.mission.MissionWsIdentifier;
import com.cyberpeace.cr.crbase.service.context.mission.MissionWsUpdateStatusMessage;
import com.cyberpeace.cr.crbase.service.context.mission.TaskWsData;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author SN
 */
@Service
@Slf4j
public class CrTaskServiceImpl extends ServiceImpl<CrTaskMapper, CrTask> implements CrTaskService {

	private final Map<Integer, Integer> evaluateStatusToActionCode = ImmutableMap.of(
			CrTaskSubmit.EvaluateStatus.SUCCESS.getCode(),
			CrTaskLog.ActionCode.MAN_SUCCESS.getCode(), CrTaskSubmit.EvaluateStatus.FAIL.getCode(),
			CrTaskLog.ActionCode.MAN_FAILURE.getCode(),
			CrTaskSubmit.EvaluateStatus.REJECT.getCode(),
			CrTaskLog.ActionCode.MAN_REJECT.getCode());
	@Autowired
	private CrTaskMapper crTaskMapper;
	@Autowired
	private CrFlagTaskMapper crFlagTaskMapper;
	@Autowired
	private MissionMapper missionMapper;
	@Autowired
	private CrTaskSubmitMapper crTaskSubmitMapper;
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private CrTaskLogMapper crTaskLogMapper;
	@Autowired
	private MyWebSocketHandler webSocketHandler;
	@Autowired
	private CrTaskSituationHandler crTaskSituationHandler;

	@Override
	public CrTask getById(String taskId) {
		return crTaskMapper.selectById(taskId);
	}

	@Override
	public TaskContext getByMissionAndUser(String missionId, String userId) {
		CrTask task = crTaskMapper.selectByUserIdAndMissionId(userId, missionId);
		if (null == task) {
			log.error("user‘s mission does not release！userId=[{}] missionId=[{}]", userId,
					missionId);
			throw new BusinessException(CrBusinessErrorCode.TASK_NOT_EXIST_ERROR);
		}
		TaskContext taskContext = BeanUtils.copy(task, TaskContext.class);
		taskContext.setMission(missionMapper.selectById(task.getMissionId()));

		return taskContext;
	}

	@Override
	public void submitMissions(String roomId, String missionId, String answer) {
		submitAnswer(missionId, answer, UserInfoContext.getUserContext().getUserId(), "");
	}

	@Override
	public Integer batchSubmitMissions(String roomId, List<String> missionIds,
			Map<String, String> answer) {
		if (missionIds.size() == 0) {
			return 0;
		}

		Map<String, Mission> missionMap = StreamApiUtils.toMap(
				missionMapper.selectBatchIds(missionIds), MissionBase::getResourceId,
				mission -> mission);
		Map<String, CrTask> crTaskMap = StreamApiUtils.toMap(
				lambdaQuery().in(CrTask::getMissionId, missionIds).list(), CrTask::getMissionId,
				crTask -> crTask);

		Date currentDate = new Date();
		List<CrTaskSubmit> pendingSaveSubmit = Lists.newArrayList();
		List<CrTask> pendingUpdateTask = Lists.newArrayList();
		List<Integer> missionScore = Lists.newArrayList();

		answer.forEach((key, val) -> {
			Mission mission = missionMap.get(key);
			CrTask crTask = crTaskMap.get(key);
			if (mission == null || crTask == null) {
				throw new BusinessException(CrBusinessErrorCode.MISSION_NOT_EXIST);
			}

			CrTaskSubmit crTaskSubmit = new CrTaskSubmit();
			crTaskSubmit.setTaskId(crTask.getResourceId());
			crTaskSubmit.setRoomId(crTask.getRoomId());
			crTaskSubmit.setMissionScore(mission.getScore());
			crTaskSubmit.setAnswer(val);
			crTaskSubmit.setEvaluateTime(currentDate);
			crTaskSubmit.setAnswerExtension("");
			crTaskSubmit.setResourceId(IDGeneratorUtil.getID());

			if (mission.isObjective()) {
				// 客观题，可机评
				if (isCorrect(val, mission.getAnswer(), mission.getType(),crTask.getResourceId())) {
					crTask.setScore(mission.getScore());
					crTask.setStatus(CrTaskSubmit.EvaluateStatus.SUCCESS.getCode());
				} else {
					crTask.setScore(0);
					crTask.setStatus(CrTaskSubmit.EvaluateStatus.FAIL.getCode());
				}
			} else {
				// 主观题 攻击和防御可机评
				Integer score = 0;
				Integer status = CrTaskSubmit.EvaluateStatus.WAIT.getCode();
				if (MissionBase.MissionType.ATTACK.getCode().equals(mission.getType()) || MissionBase.MissionType.DEFENCE.getCode().equals(mission.getType())) {
					List<String> missionAnswerList = Arrays.asList( mission.getAnswer().split("[|]]"));
					if (missionAnswerList.contains(val)) {
						score = mission.getScore();
						status = CrTaskSubmit.EvaluateStatus.SUCCESS.getCode();
					}
				}
				crTask.setScore(score);
				crTask.setStatus(status);
			}

			crTask.setUserAnswer(val);
			crTask.setLastSubmitTime(currentDate);

			crTaskSubmit.setScore(crTask.getScore());
			crTaskSubmit.setEvaluateStatus(crTask.getStatus());

			missionScore.add(crTask.getScore());
			pendingUpdateTask.add(crTask);
			pendingSaveSubmit.add(crTaskSubmit);
		});

		// 数据写入
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				updateBatchById(pendingUpdateTask);
				pendingSaveSubmit.forEach(obj -> crTaskSubmitMapper.insert(obj));
			}
		});

		return missionScore.stream().mapToInt(item -> item).sum();

	}

	@Override
	public List<TaskContext> getTaskContextByRoomId(String roomId) {
		List<CrTask> taskList = crTaskMapper.selectByRoomId(roomId);

		return getTaskContext(taskList);

	}

	@Override
	public List<TaskContext> getTaskContextByRoomIdAndUsers(String roomId, List<String> userIds) {
		List<CrTask> taskList = crTaskMapper.selectByRoomIdAndUserIds(roomId, userIds);

		return getTaskContext(taskList);
	}

	private List<TaskContext> getTaskContext(List<CrTask> taskList) {
		List<TaskContext> taskContextList = new ArrayList<>();

		List<String> missionIds = new ArrayList<>();

		for (CrTask task : taskList) {
			missionIds.add(task.getMissionId());
		}
		if (missionIds.size() > 0) {
			List<Mission> missionList = missionMapper.batchSelect(missionIds);
			Map<String, Mission> missionMap = Maps.newHashMap();
			for (Mission mission : missionList) {
				missionMap.put(mission.getResourceId(), mission);
			}

			for (CrTask task : taskList) {
				TaskContext taskContext = BeanUtils.copy(task, TaskContext.class);

				taskContext.setMission(missionMap.get(task.getMissionId()));
				taskContextList.add(taskContext);
			}
		}

		return taskContextList;
	}

	@Override
	public List<TaskContext> getByRoomIdAndUser(String roomId, String userId) {
		List<CrTask> taskList = crTaskMapper.selectByRoomIdAndUserId(roomId, userId);

		return getTaskContext(taskList);
	}

	@Override
	public void startTask(CrTask task) {
		task.setStatus(CrTask.TaskStatus.STARTED.getCode());
		task.setReleaseTime(new Date());
		crTaskMapper.updateById(task);
	}

	@Override
	public CrTask getByUserIdAndMissionId(String userId, String missionId) {

		return crTaskMapper.selectByUserIdAndMissionId(userId, missionId);
	}

	@Override
	public List<CrTask> getByRoomIdAndUserId(String roomId, String userId) {
		return crTaskMapper.selectByRoomIdAndUserId(roomId, userId);
	}

	@Override
	public Integer countByRoomIdAndUserId(String roomId, String userId) {
		return crTaskMapper.countByRoomIdAndUserId(roomId, userId);
	}

	@Override
	public List<CrTask> getFinished(String roomId) {
		return crTaskMapper.selectByRoomIdAndStatusList(roomId,
				CrTask.TaskStatus.FINISHED_STATUS_LIST);
	}

	@Override
	public Integer countFinished(String roomId) {
		return crTaskMapper.countByRoomIdAndStatusList(roomId,
				CrTask.TaskStatus.FINISHED_STATUS_LIST);
	}

	@Override
	public Integer countUnsubmitted(String roomId) {
		return crTaskMapper.countByRoomIdAndStatusList(roomId,
				CrTask.TaskStatus.UNSUBMITTED_STATUS_LIST);
	}

	@Override
	public Integer countUnfinished(String roomId) {
		return crTaskMapper.countByRoomIdAndStatusList(roomId,
				CrTask.TaskStatus.UNFINISHED_STATUS_LIST);
	}

	@Override
	public Integer countFinishedByRoomIdAndUserId(String roomId, String userId) {
		return crTaskMapper.countByRoomIdAndUserIdAndStatusList(roomId, userId,
				CrTask.TaskStatus.FINISHED_STATUS_LIST);
	}

	@Override
	public List<CrTask> getFinishedByRoomIdAndUserId(String roomId, String userId) {
		return crTaskMapper.selectByRoomIdAndUserIdAndStatusList(roomId, userId,
				CrTask.TaskStatus.FINISHED_STATUS_LIST);
	}

	@Override
	public List<CrTask> getFinishedByRoomIdAndUserIds(String roomId, List<String> userIds) {
		return crTaskMapper.selectByRoomIdAndUserIdsAndStatusList(roomId, userIds,
				CrTask.TaskStatus.FINISHED_STATUS_LIST);
	}

	@Override
	public List<CrTask> getByRoomId(String roomId) {
		return crTaskMapper.selectByRoomId(roomId);
	}

	@Override
	public List<CrTask> getByRole(String roleId) {
		return crTaskMapper.selectByRole(roleId);
	}

	@Override
	public List<CrTask> getByRoles(List<String> roleIds) {
		return crTaskMapper.selectByRoles(roleIds);
	}

	@Override
	public List<CrTask> getByMissions(String roomId, List<String> missionIds) {
		return crTaskMapper.selectByRoomIdAndMissionIds(roomId, missionIds);
	}

	@Override
	public List<CrTask> getReportedByUserIdsAndMissions(String roomId, List<String> userIds,
			List<String> missionIds) {
		return crTaskMapper.selectReportedByUserIdsAndMissionIds(roomId, userIds, missionIds);
	}

	@Override
	public Integer countUnreportedByUsers(String roomId, List<String> userIds) {
		return crTaskMapper.countUnreportedByRoomIdAndUserIds(roomId, userIds);
	}

	@Override
	public Boolean isReleasedByMission(String missionId) {
		return crTaskMapper.selectByMissionId(missionId) != null;
	}

	@Override
	public List<String> getIdsByUserIds(String roomId, List<String> userIds) {
		return crTaskMapper.selectIdsByRoomIdAndUserIds(roomId, userIds);
	}

	@Override
	public List<String> getIdsByOptionalUserId(String roomId, String userId) {
		return crTaskMapper.selectIdsByRoomIdAndUserId(roomId, userId);
	}

	private Boolean isCorrect(String userAnswer, String missionAnswer, Integer missionType, String taskId) {
		if (missionType.equals(MissionBase.MissionType.MULTIPLE_CHOICE.getCode())) {
			List<String> userChoices = JsonUtil.toJsonObjectArray(userAnswer, String.class);
			Set<String> userChoiceSet = Sets.newHashSet(userChoices);
			List<String> choices = JsonUtil.toJsonObjectArray(missionAnswer, String.class);
			Set<String> choiceSet = Sets.newHashSet(choices);
			return userChoiceSet.equals(choiceSet);
		} else if (missionType.equals(MissionBase.MissionType.FILE.getCode())) {
			return userAnswer.equalsIgnoreCase(missionAnswer);
		}else if (missionType.equals(MissionBase.MissionType.FLAG.getCode())) {
			MissionFlagQuery missionFlagQuery = JSON.parseObject(missionAnswer, MissionFlagQuery.class);
			if (MissionFlagQuery.FlagType.STATIC_FLAG.getCode().equals(missionFlagQuery.getFlagType())) {
				return userAnswer.equals(missionFlagQuery.getFlagValue());
			}else {
				LambdaQueryWrapper<CrFlagTask> queryWrapper = new LambdaQueryWrapper<>();
				queryWrapper.eq(CrFlagTask::getTaskId,taskId);
				CrFlagTask flagTask = crFlagTaskMapper.selectOne(queryWrapper);
				return userAnswer.equals(flagTask.getFlagValue());
			}
		} else {
			return userAnswer.equals(missionAnswer);
		}
	}

	@Override
	public void submitAnswer(String missionId, String answer, String userId,
			String answerExtension) {
		Mission mission = missionMapper.selectById(missionId);
		if (mission == null) {
			throw new BusinessException(CrBusinessErrorCode.MISSION_NOT_EXIST);
		}

		// 多选题答案校验
		if (mission.getType().equals(MissionBase.MissionType.MULTIPLE_CHOICE.getCode())) {
			if (answer.equals(CommonConstant.EMPTY_LIST_JSON)) {
				throw new BusinessException(CrBusinessErrorCode.ANSWER_NOT_NULLABLE);
			}
		}

		CrTask task = crTaskMapper.selectByUserIdAndMissionId(userId, missionId);
		if (task == null) {
			throw new BusinessException(CrBusinessErrorCode.MISSION_UNRELEASED);
		}

		// 验证是否为结束状态，无法提交
		if (task.getStatus().intValue() == CrTask.TaskStatus.CLOSE.getCode()) {
			throw new BusinessException(CrBusinessErrorCode.TASK_IS_CLOSED);
		}

		// 校验该task是否可提交
		CrTaskSubmit lastSubmit = crTaskSubmitMapper.selectLastByTaskId(task.getResourceId());
		if (lastSubmit != null && !lastSubmit.getEvaluateStatus()
				.equals(CrTaskSubmit.EvaluateStatus.REJECT.getCode())) {
			throw new BusinessException(CrBusinessErrorCode.CANNOT_SUBMIT_FINISHED_TASK);
		}

		// 创建 taskSubmit
		CrTaskSubmit crTaskSubmit = new CrTaskSubmit();
		crTaskSubmit.setTaskId(task.getResourceId());
		crTaskSubmit.setRoomId(task.getRoomId());
		crTaskSubmit.setMissionScore(mission.getScore());
		crTaskSubmit.setAnswer(answer);
		crTaskSubmit.setEvaluateTime(new Date());

		crTaskSubmit.setAnswerExtension(answerExtension);
		crTaskSubmit.setResourceId(IDGeneratorUtil.getID());

		// 创建提交log
		CrTaskLog submitLog = CrTaskLog.builder().roomId(task.getRoomId()).missionId(missionId)
				.actionCode(CrTaskLog.ActionCode.SUBMIT.getCode()).userId(userId).build();

		// 创建可能存在的评估log
		CrTaskLog evaluateLog = CrTaskLog.builder().roomId(task.getRoomId()).missionId(missionId)
				.build();

		if (mission.isObjective()) {
			// 客观题，可机评
			if (isCorrect(answer, mission.getAnswer(), mission.getType(),task.getResourceId())) {
				task.setScore(mission.getScore());
				task.setStatus(CrTaskSubmit.EvaluateStatus.SUCCESS.getCode());
				evaluateLog.setActionCode(CrTaskLog.ActionCode.ROBOT_SUCCESS.getCode());
				judgeTaskAndPushMissionToSituation(missionId, mission.getScore());
			} else {
				task.setScore(0);
				task.setStatus(CrTaskSubmit.EvaluateStatus.FAIL.getCode());
				evaluateLog.setActionCode(CrTaskLog.ActionCode.ROBOT_FAILURE.getCode());
			}
		} else {
			// 主观题
			task.setScore(0);
			task.setStatus(CrTaskSubmit.EvaluateStatus.WAIT.getCode());
		}

		task.setUserAnswer(answer);
		task.setLastSubmitTime(new Date());

		crTaskSubmit.setScore(task.getScore());
		crTaskSubmit.setEvaluateStatus(task.getStatus());

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				crTaskMapper.updateById(task);
				crTaskSubmitMapper.insert(crTaskSubmit);
				submitLog.setTaskId(task.getResourceId());
				crTaskLogMapper.insert(submitLog);
				if (mission.isObjective()) {
					crTaskLogMapper.insert(evaluateLog);
				}
			}
		});
	}

	@Override
	public void submitEvaluation(String taskSubmitId, Integer score, String detail, Integer reason,
			Integer evaluateStatus, String currentUserId) {
		// check permission??
		CrTaskSubmit taskSubmit = crTaskSubmitMapper.selectById(taskSubmitId);
		if (taskSubmit == null) {
			throw new BusinessException(CrBusinessErrorCode.MISSION_SUBMIT_NOT_FOUND);
		}
		if (taskSubmit.getEvaluateStatus().equals(CrTaskSubmit.EvaluateStatus.REJECT.getCode())) {
			throw new BusinessException(CrBusinessErrorCode.TASK_REJECTED);
		}

		CrTask task = crTaskMapper.selectById(taskSubmit.getTaskId());
		if (task == null) {
			log.error("task does not exist！roleId=[{}]", taskSubmit.getTaskId());
			throw new BusinessException(CrBusinessErrorCode.TASK_NOT_EXIST_ERROR);
		}

		// 驳回??
		if (score == null) {
			score = 0;
		}
		// 校验分数
		if (taskSubmit.getMissionScore() < score || score < 0) {
			throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
		}

		taskSubmit.setScore(score);
		taskSubmit.setDetail(detail);
		taskSubmit.setReason(reason);
		taskSubmit.setEvaluateStatus(evaluateStatus);
		taskSubmit.setEvaluatorId(currentUserId);
		taskSubmit.setEvaluateTime(new Date());

		task.setStatus(evaluateStatus);
		task.setScore(score);

		CrTaskLog evaluationLog = CrTaskLog.builder().roomId(taskSubmit.getRoomId())
				.missionId(task.getMissionId())
				.actionCode(evaluateStatusToActionCode.get(evaluateStatus)).userId(currentUserId)
				.build();

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				crTaskMapper.updateById(task);
				crTaskSubmitMapper.updateById(taskSubmit);
				evaluationLog.setTaskId(task.getResourceId());
				crTaskLogMapper.insert(evaluationLog);
			}
		});

	}

	@Override
	public IPage<MissionEvaluationContext>
			getRbMissionEvaluationList(MissionEvaluationQuery missionEvaluationQuery) {
		missionEvaluationQuery.setSort("-evaluate_time");
		missionEvaluationQuery.setPer_page(10000);

		Page page = missionEvaluationQuery.toPageParam();
		IPage<MissionEvaluationContext> data = crTaskSubmitMapper.listRbCrTaskSubmit(page,
				missionEvaluationQuery);

		return setMission(data);
	}

	@Override
	public List<CrTaskSubmit> getOrderedSubmitsByTask(String resourceId) {
		return crTaskSubmitMapper.selectOrderedSubmitsByTask(resourceId);
	}

	private IPage<MissionEvaluationContext> setMission(IPage<MissionEvaluationContext> data) {
		List<String> missionIds = data.getRecords().stream()
				.map(MissionEvaluationContext::getMissionHash).distinct()
				.collect(Collectors.toList());

		//获取一血, 二血, 三血及额外分数
		Map<String, MissionEvaluationContext> rankMap = getRankContextMap(data);
		//获取动态任务对应flagValue
		LambdaQueryWrapper<CrFlagTask> flagTaskWrapper = new LambdaQueryWrapper<>();
		flagTaskWrapper.in(CrFlagTask::getMissionId,missionIds);
		List<CrFlagTask> flagTaskList = crFlagTaskMapper.selectList(flagTaskWrapper);
		Map<String, String> taskFlagValueMap = StreamApiUtils.toMap(flagTaskList, CrFlagTask::getTaskId, CrFlagTask::getFlagValue);

		List<Mission> missions = Lists.newArrayList();
		if (missionIds.size() > 0) {
			missions = missionMapper.batchSelect(missionIds);
		}

		Map<String, Mission> missionMap = StreamApiUtils.toMap(missions, Mission::getResourceId,pc -> pc);
		data.convert(missionEvaluationContext -> {

			MissionEvaluationContext rankContext = rankMap.get(missionEvaluationContext.getResourceId());
			missionEvaluationContext.setRank(rankContext.getRank());
			missionEvaluationContext.setRankScore(rankContext.getRankScore());

			Mission mission = missionMap.get(missionEvaluationContext.getMissionHash());
			//任务答案 flag类型:静态类型取flagValue,动态根据任务查对应flagValue
			if (MissionBase.MissionType.FLAG.getCode().equals(mission.getType())) {
				MissionFlagQuery missionFlagQuery = JSON.parseObject(mission.getAnswer(), MissionFlagQuery.class);
				if (MissionFlagQuery.FlagType.STATIC_FLAG.getCode().equals(missionFlagQuery.getFlagType())) {
					mission.setAnswer(missionFlagQuery.getFlagValue());
				} else {
					mission.setAnswer(taskFlagValueMap.get(missionEvaluationContext.getResourceId()));
				}
			}
			missionEvaluationContext.setMission(mission);
			return missionEvaluationContext;
		});
		return data;
	}

	/**
	 * 获取一血, 二血, 三血及额外分数
	 */
	private Map<String, MissionEvaluationContext> getRankContextMap(IPage<MissionEvaluationContext> data) {

		LambdaQueryWrapper<CrTaskSubmit> rankQueryWrapper = new LambdaQueryWrapper<>();
		rankQueryWrapper.eq(CrTaskSubmit::getRoomId, data.getRecords().get(0).getRoomId());
		rankQueryWrapper.orderByAsc(CrTaskSubmit::getCreateTime);
		List<CrTaskSubmit> crTaskSubmits = crTaskSubmitMapper.selectList(rankQueryWrapper);

		Map<String, MissionEvaluationContext> rankMap = new HashMap<>();
		List<String> rankList = Arrays.asList("一血", "二血", "三血");
		List<Double> rankScoreList = Arrays.asList(0.2, 0.1, 0.05);
		for (int i = 0; i < crTaskSubmits.size(); i++) {
			CrTaskSubmit crTaskSubmit = crTaskSubmits.get(i);
			MissionEvaluationContext rankContext = new MissionEvaluationContext();
			rankContext.setResourceId(crTaskSubmit.getResourceId());
			if (i < 3) {
				BigDecimal rankScore = BigDecimal.valueOf(crTaskSubmit.getMissionScore() * rankScoreList.get(i))
						.setScale(0, BigDecimal.ROUND_HALF_UP);
				rankContext.setRankScore(Integer.parseInt(rankScore.toString()));
				rankContext.setRank(rankList.get(i));
			} else {
				rankContext.setRankScore(0);
				rankContext.setRank("否");
			}
			rankMap.put(crTaskSubmit.getResourceId(), rankContext);
		}
		return rankMap;
	}

	@Override
	public IPage<MissionEvaluationContext>
			getMissionEvaluationList(MissionEvaluationQuery missionEvaluationQuery) {
		missionEvaluationQuery.setSort("-evaluate_time");

		Page page = missionEvaluationQuery.toPageParam();
		IPage<MissionEvaluationContext> data = crTaskSubmitMapper.listCrTaskSubmit(page,
				missionEvaluationQuery);

		return setMission(data);
	}

	@Override
	public void pushMissionWS(String businessId, Integer code) {

		TaskWsData data = new TaskWsData(code);
		sendWsData(data, businessId);
	}

	@Override
	public void pushStatusWs(String businessId, Integer runningStatus) {
		MissionWsUpdateStatusMessage message = new MissionWsUpdateStatusMessage(runningStatus);
		sendWsData(message, businessId);
	}

	private <T> void sendWsData(T data, String businessId) {
		MissionWsIdentifier identifier = new MissionWsIdentifier();
		identifier.setBusinessId(businessId);
		identifier.setUserId(UserInfoContext.getUserContext().getUserId());
		identifier.setPath(WsPath.CR_MISSION);

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().data(data).messageIdentifier(identifier).build();
		webSocketHandler.pushMessage(messageContext, true);
	}

	@Override
	public Integer countUnfinishedByRoomIdAndRoleIds(String roomId, List<String> crRoleIds) {
		return crTaskMapper.countByRoomIdAndRoleIdsAndStatusList(roomId, crRoleIds,
				CrTask.TaskStatus.UNFINISHED_STATUS_LIST);
	}

	@Override
	public Integer countUnsubmittedByRoomIdAndRoleIds(String roomId, List<String> crRoleIds) {
		return crTaskMapper.countByRoomIdAndRoleIdsAndStatusList(roomId, crRoleIds,
				CrTask.TaskStatus.UNSUBMITTED_STATUS_LIST);
	}

	@Override
	public Integer countFinishedByRoomIdAndRoleIds(String roomId, List<String> crRoleIds) {
		return crTaskMapper.countByRoomIdAndRoleIdsAndStatusList(roomId, crRoleIds,
				CrTask.TaskStatus.FINISHED_STATUS_LIST);
	}

	@Override
	public Integer countEndedByRoomIdAndRoleIds(String roomId, List<String> crRoleIds) {
		return lambdaQuery()
				.eq(CrTask::getRoomId, roomId)
				.in(CrTask::getRoleId, crRoleIds)
				.eq(CrTask::getStatus, CrTask.TaskStatus.CLOSE.getCode())
				.count();
	}

	@Override
	public Integer countByRoomIdAndRoleIds(String roomId, List<String> crRoleIds) {
		return crTaskMapper.countByRoomIdAndRoleIds(roomId, crRoleIds);
	}

	@Override
	public Boolean missionClose(String missionId, CrRole crRole) {
		Mission mission = missionMapper.selectById(missionId);
		if (mission == null) {
			throw new BusinessException(CrBusinessErrorCode.MISSION_NOT_EXIST);
		}

		List<CrTask> crTaskList = lambdaQuery()
				.eq(CrTask::getRoomId, mission.getRoomId())
				.eq(CrTask::getRoleId, crRole.getResourceId())
				.eq(CrTask::getMissionId, mission.getResourceId())
				.in(CrTask::getStatus, CrTask.TaskStatus.ONGOING_STATUS_LIST)
				.list();

		// 任务未下发
		if (crTaskList.isEmpty()) {
			crTaskList = lambdaQuery()
					.eq(CrTask::getRoomId, mission.getRoomId())
					.eq(CrTask::getRoleId, crRole.getResourceId())
					.eq(CrTask::getMissionId, mission.getResourceId())
					.list();
			if (!crTaskList.isEmpty() && crTaskList.stream().filter(CrTask::isFinished).count() == crTaskList.size()) {
				throw new BusinessException(CrBusinessErrorCode.CANNOT_END_FINISHED_TASK);
			}

			throw new BusinessException(CrBusinessErrorCode.MISSION_UNRELEASED);
		}

		crTaskList.forEach(crTask -> crTask.setStatus(CrTask.TaskStatus.CLOSE.getCode()));

		return updateBatchById(crTaskList);
	}

	@Override
	public void judgeTaskAndPushMissionToSituation(String missionId, Integer score) {
		//判断该任务有没有被同角色下的其他用户提交过或曾经成功过
		List<CrTaskLog> crTaskLogList = crTaskLogMapper.getByMissionIdAndUserId(missionId);
		Boolean success = false;
		for (CrTaskLog crTaskLog: crTaskLogList) {
			if (crTaskLog.getActionCode().equals(CrTaskLog.ActionCode.MAN_SUCCESS.getCode()) ||
					crTaskLog.getActionCode().equals(CrTaskLog.ActionCode.ROBOT_SUCCESS.getCode())) {
				success = true;
			}
		}
		if (!success) {
			crTaskSituationHandler.chooseTypeAndPushMissionToSituation(missionId, score);
		}
	}
}