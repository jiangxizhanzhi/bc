package com.cyberpeace.cr.crbase.service.impl;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cyberpeace.cr.crbase.service.context.mission.TaskMissionWsData;
import com.cyberpeace.situation.constant.SituationGuideScheme;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.dao.entity.*;
import com.cyberpeace.cr.crbase.dao.mapper.*;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.IndustrySituationService;
import com.cyberpeace.cr.crbase.service.SituationGuideService;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.cyberpeace.cr.crbase.service.context.TaskMatrix;
import com.cyberpeace.cr.crbase.service.context.evaluation.*;
import com.cyberpeace.cr.crbase.service.context.mission.MissionWsIdentifier;
import com.cyberpeace.cr.crbase.service.context.mission.MissionWsUpdateStatusMessage;
import com.cyberpeace.cr.crbase.service.context.mission.TaskWsData;
import com.cyberpeace.cr.crbase.service.context.statistics.TaskStatisticsInfo;
import com.cyberpeace.cr.crbase.service.context.task.CrTaskRejectRsp;
import com.cyberpeace.cr.crbase.utils.CrCommonUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

/**
 * 靶场任务Service实现
 *
 * @author SN@date 2024/11/13
 */
@Service
@Slf4j
public class CrTaskServiceImpl extends ServiceImpl<CrTaskMapper, CrTask> implements CrTaskService {

	/**
	 * 评估状态自行动代号
	 */
	private final Map<Integer, Integer> evaluateStatusToActionCode = ImmutableMap.of(
			CrTaskSubmit.EvaluateStatus.SUCCESS.getCode(),
			CrTaskLog.ActionCode.MAN_SUCCESS.getCode(), CrTaskSubmit.EvaluateStatus.FAIL.getCode(),
			CrTaskLog.ActionCode.MAN_FAILURE.getCode(),
			CrTaskSubmit.EvaluateStatus.REJECT.getCode(),
			CrTaskLog.ActionCode.MAN_REJECT.getCode());

	/**
	 * 靶场任务Mapper
	 */
	@Autowired
	private CrTaskMapper crTaskMapper;

	/**
	 * 任务Mapper
	 */
	@Autowired
	private MissionMapper missionMapper;

	/**
	 * 靶场任务提交Mapper
	 */
	@Autowired
	private CrTaskSubmitMapper crTaskSubmitMapper;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 靶场任务日志Mapper
	 */
	@Autowired
	private CrTaskLogMapper crTaskLogMapper;

	/**
	 * WebSocket处理器
	 */
	@Autowired
	private MyWebSocketHandler webSocketHandler;

	/**
	 * 远程用户Service
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 工业态势Service
	 */
	@Autowired
	private IndustrySituationService industrySituationService;

	/**
	 * 态势导调历史Mapper
	 */
	@Autowired
	private SituationGuideHisMapper situationGuideHisMapper;

	/**
	 * 态势导调Service
	 */
	@Autowired
	private SituationGuideService situationGuideService;

	/**
	 * 获取由id
	 *
	 * @param taskId 任务id
	 * @return {@code CrTask }
	 */
	@Override
	public CrTask getById(String taskId) {
		return crTaskMapper.selectById(taskId);
	}

	/**
	 * 获取由任务和用户
	 *
	 * @param missionId 任务id
	 * @param userId    用户id
	 * @return {@code TaskContext }
	 */
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

	/**
	 * 批量提交任务
	 *
	 * @param roomId     房间id
	 * @param missionIds 任务id列表
	 * @param answer     答案
	 * @return {@code Integer }
	 */
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
				log.error("Mission not exist! key = [{}]", key);
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
				if (isCorrect(val, mission.getAnswer(), mission.getType())) {
					crTask.setScore(mission.getScore());
					crTask.setStatus(CrTaskSubmit.EvaluateStatus.SUCCESS.getCode());
				} else {
					crTask.setScore(0);
					crTask.setStatus(CrTaskSubmit.EvaluateStatus.FAIL.getCode());
				}
			} else {
				// 主观题
				crTask.setScore(0);
				crTask.setStatus(CrTaskSubmit.EvaluateStatus.WAIT.getCode());
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

	/**
	 * 获取任务上下文由房间id和用户
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id列表
	 * @return {@code List<TaskContext> }
	 */
	@Override
	public List<TaskContext> getTaskContextByRoomIdAndUsers(String roomId, List<String> userIds) {
		List<CrTask> taskList = crTaskMapper.selectByRoomIdAndUserIds(roomId, userIds);

		return getTaskContext(taskList);
	}

	/**
	 * 获取任务上下文
	 *
	 * @param taskList 任务列表
	 * @return {@code List<TaskContext> }
	 */
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

	/**
	 * 获取由房间id和用户
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code List<TaskContext> }
	 */
	@Override
	public List<TaskContext> getByRoomIdAndUser(String roomId, String userId) {
		List<CrTask> taskList = crTaskMapper.selectByRoomIdAndUserId(roomId, userId);

		return getTaskContext(taskList);
	}

	/**
	 * 启动任务
	 *
	 * @param task 任务
	 */
	@Override
	public void startTask(CrTask task) {
		task.setStatus(CrTask.TaskStatus.STARTED.getCode());
		task.setReleaseTime(new Date());
		crTaskMapper.updateById(task);
	}

	/**
	 * 获取由用户id和任务id
	 *
	 * @param userId    用户id
	 * @param missionId 任务id
	 * @return {@code CrTask }
	 */
	@Override
	public CrTask getByUserIdAndMissionId(String userId, String missionId) {

		return crTaskMapper.selectByUserIdAndMissionId(userId, missionId);
	}

	/**
	 * 获取由房间id和用户id
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code List<CrTask> }
	 */
	@Override
	public List<CrTask> getByRoomIdAndUserId(String roomId, String userId) {
		return crTaskMapper.selectByRoomIdAndUserId(roomId, userId);
	}

	/**
	 * 计数由房间id和用户id
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code Integer }
	 */
	@Override
	public Integer countByRoomIdAndUserId(String roomId, String userId) {
		return crTaskMapper.countByRoomIdAndUserId(roomId, userId);
	}

	/**
	 * 获取完成
	 *
	 * @param roomId 房间id
	 * @return {@code List<CrTask> }
	 */
	@Override
	public List<CrTask> getFinished(String roomId) {
		return crTaskMapper.selectByRoomIdAndStatusList(roomId,
				CrTask.TaskStatus.FINISHED_STATUS_LIST);
	}

	/**
	 * 计数完成
	 *
	 * @param roomId 房间id
	 * @return {@code Integer }
	 */
	@Override
	public Integer countFinished(String roomId) {
		return crTaskMapper.countByRoomIdAndStatusList(roomId,
				CrTask.TaskStatus.FINISHED_STATUS_LIST);
	}

	/**
	 * 计数未提交
	 *
	 * @param roomId 房间id
	 * @return {@code Integer }
	 */
	@Override
	public Integer countUnsubmitted(String roomId) {
		return crTaskMapper.countByRoomIdAndStatusList(roomId,
				CrTask.TaskStatus.UNSUBMITTED_STATUS_LIST);
	}

	/**
	 * 计数未完成
	 *
	 * @param roomId 房间id
	 * @return {@code Integer }
	 */
	@Override
	public Integer countUnfinished(String roomId) {
		return crTaskMapper.countByRoomIdAndStatusList(roomId,
				CrTask.TaskStatus.UNFINISHED_STATUS_LIST);
	}

	/**
	 * 计数完成由房间id和用户id
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code Integer }
	 */
	@Override
	public Integer countFinishedByRoomIdAndUserId(String roomId, String userId) {
		return crTaskMapper.countByRoomIdAndUserIdAndStatusList(roomId, userId,
				CrTask.TaskStatus.FINISHED_STATUS_LIST);
	}

	/**
	 * 获取完成由房间id和用户id
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code List<CrTask> }
	 */
	@Override
	public List<CrTask> getFinishedByRoomIdAndUserId(String roomId, String userId) {
		return crTaskMapper.selectByRoomIdAndUserIdAndStatusList(roomId, userId,
				CrTask.TaskStatus.FINISHED_STATUS_LIST);
	}

	/**
	 * 获取完成由房间id和用户id列表
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id列表
	 * @return {@code List<CrTask> }
	 */
	@Override
	public List<CrTask> getFinishedByRoomIdAndUserIds(String roomId, List<String> userIds) {
		return crTaskMapper.selectByRoomIdAndUserIdsAndStatusList(roomId, userIds,
				CrTask.TaskStatus.FINISHED_STATUS_LIST);
	}

	/**
	 * 获取由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code List<CrTask> }
	 */
	@Override
	public List<CrTask> getByRoomId(String roomId) {
		return crTaskMapper.selectByRoomId(roomId);
	}

	/**
	 * 获取由岗位
	 *
	 * @param roleId 岗位id
	 * @return {@code List<CrTask> }
	 */
	@Override
	public List<CrTask> getByRole(String roleId) {
		return crTaskMapper.selectByRole(roleId);
	}

	/**
	 * 获取由角色
	 *
	 * @param roleIds 岗位id列表
	 * @return {@code List<CrTask> }
	 */
	@Override
	public List<CrTask> getByRoles(List<String> roleIds) {
		return crTaskMapper.selectByRoles(roleIds);
	}

	/**
	 * 获取由任务
	 *
	 * @param roomId     房间id
	 * @param missionIds 任务id列表
	 * @return {@code List<CrTask> }
	 */
	@Override
	public List<CrTask> getByMissions(String roomId, List<String> missionIds) {
		return crTaskMapper.selectByRoomIdAndMissionIds(roomId, missionIds);
	}

	/**
	 * 获取报道由用户id列表和任务
	 *
	 * @param roomId     房间id
	 * @param userIds    用户id列表
	 * @param missionIds 任务id列表
	 * @return {@code List<CrTask> }
	 */
	@Override
	public List<CrTask> getReportedByUserIdsAndMissions(String roomId, List<String> userIds,
			List<String> missionIds) {
		return crTaskMapper.selectReportedByUserIdsAndMissionIds(roomId, userIds, missionIds);
	}

	/**
	 * 计数未报告由用户
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id列表
	 * @return {@code Integer }
	 */
	@Override
	public Integer countUnreportedByUsers(String roomId, List<String> userIds) {
		return crTaskMapper.countUnreportedByRoomIdAndUserIds(roomId, userIds);
	}

	/**
	 * 是否已下发由任务
	 *
	 * @param missionId 任务id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean isReleasedByMission(String missionId) {
		return crTaskMapper.selectByMissionId(missionId) != null;
	}

	/**
	 * 获取id列表由用户id列表
	 *
	 * @param roomId  房间id
	 * @param userIds 用户id列表
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> getIdsByUserIds(String roomId, List<String> userIds) {
		return crTaskMapper.selectIdsByRoomIdAndUserIds(roomId, userIds);
	}

	/**
	 * 获取id列表由自选用户id
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code List<String> }
	 */
	@Override
	public List<String> getIdsByOptionalUserId(String roomId, String userId) {
		return crTaskMapper.selectIdsByRoomIdAndUserId(roomId, userId);
	}

	/**
	 * 是否正确
	 *
	 * @param userAnswer    用户答案
	 * @param missionAnswer 任务答案
	 * @param missionType   任务类型
	 * @return {@code Boolean }
	 */
	private Boolean isCorrect(String userAnswer, String missionAnswer, Integer missionType) {
		if (missionType.equals(MissionBase.MissionType.MULTIPLE_CHOICE.getCode())) {
			List<String> userChoices = JsonUtil.toJsonObjectArray(userAnswer, String.class);
			Set<String> userChoiceSet = Sets.newHashSet(userChoices);
			List<String> choices = JsonUtil.toJsonObjectArray(missionAnswer, String.class);
			Set<String> choiceSet = Sets.newHashSet(choices);
			return userChoiceSet.equals(choiceSet);
		} else if (missionType.equals(MissionBase.MissionType.FILE.getCode())) {
			return userAnswer.equalsIgnoreCase(missionAnswer);
		} else {
			return userAnswer.equals(missionAnswer);
		}
	}

	/**
	 * 提交答案
	 *
	 * @param missionId       任务id
	 * @param answer          答案
	 * @param userId          用户id
	 * @param answerExtension 答案外延
	 */
	@Override
	public void submitAnswer(String missionId, String answer, String userId,
							 String answerExtension){
		submitAnswer(missionId,answer,userId,answerExtension,"");
	}

	/**
	 * 提交答案
	 *
	 * @param missionId       任务id
	 * @param answer          答案
	 * @param userId          用户id
	 * @param answerExtension 答案外延
	 * @param remark          备注
	 */
	@Override
	public void submitAnswer(String missionId, String answer, String userId,
			String answerExtension,String remark) {
		Mission mission = missionMapper.selectById(missionId);
		if (mission == null) {
			log.error("mission does not exist, id=[{}]", missionId);
			throw new BusinessException(CrBusinessErrorCode.MISSION_NOT_EXIST);
		}

		// 多选题答案校验
		if (mission.getType().equals(MissionBase.MissionType.MULTIPLE_CHOICE.getCode())
				&& answer.equals(CommonConstant.EMPTY_LIST_JSON)) {
			log.error("answer of multi choice question cant be empty");
			throw new BusinessException(CrBusinessErrorCode.ANSWER_NOT_NULLABLE);
		}

		CrTask task = crTaskMapper.selectByUserIdAndMissionId(userId, missionId);
		if (task == null) {
			log.error("mission is not released, id = [{}]",missionId);
			throw new BusinessException(CrBusinessErrorCode.MISSION_UNRELEASED);
		}

		// 校验该task是否可提交
		CrTaskSubmit lastSubmit = crTaskSubmitMapper.selectLastByTaskId(task.getResourceId());
		if (lastSubmit != null && !lastSubmit.getEvaluateStatus()
				.equals(CrTaskSubmit.EvaluateStatus.REJECT.getCode())) {
			log.error("task is finished, taskId=[{}]", task.getResourceId());
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
			if (isCorrect(answer, mission.getAnswer(), mission.getType())) {
				task.setScore(mission.getScore());
				task.setStatus(CrTask.TaskStatus.SUCCESS.getCode());
				evaluateLog.setActionCode(CrTaskLog.ActionCode.ROBOT_SUCCESS.getCode());
			} else {
				task.setScore(0);
				task.setStatus(CrTask.TaskStatus.FAIL.getCode());
				evaluateLog.setActionCode(CrTaskLog.ActionCode.ROBOT_FAILURE.getCode());
			}
		} else {
			// 主观题
			task.setScore(0);
			task.setStatus(CrTask.TaskStatus.WAIT.getCode());
		}

		task.setUserAnswer(answer);
		task.setLastSubmitTime(new Date());
		task.setRemark(remark);

		crTaskSubmit.setScore(task.getScore());
		crTaskSubmit.setEvaluateStatus(task.getStatus());

		//任务导调(任务导调已触发guideHis为null)
		SituationGuideHistory guideHis = situationGuideService.getGuideHisByMissionId(missionId, task.getResourceId());
		//是否触发态势导调动效和页面刷新
		final Boolean[] pushDataFlag = {false};
		if (Objects.nonNull(guideHis) && StringUtils.isNotBlank(guideHis.getSceneId())) {
			boolean missionSubmitFlag = SituationGuideScheme.ConditionType.MISSION_SUBMIT.getCode().equals(guideHis.getTriggerCondition());
			boolean missionSuccessFlag = SituationGuideScheme.ConditionType.MISSION_SUCCESS.getCode().equals(guideHis.getTriggerCondition()) && CrTaskSubmit.EvaluateStatus.SUCCESS.getCode().equals(task.getStatus());
			pushDataFlag[0] = missionSubmitFlag || missionSuccessFlag;
		}

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
				if (Boolean.TRUE.equals(pushDataFlag[0])) {
					situationGuideHisMapper.insert(guideHis);
				}
			}
		});

		// 自动评估状态为正确，则触发行业态势，
		if (StringUtils.isNotBlank(task.getMissionTemplateId())
				&& task.getStatus().equals(CrTask.TaskStatus.SUCCESS.getCode())) {
			industrySituationService.pushSituationWs(task.getRoomId(), task.getMissionTemplateId());
		}
		if (Boolean.TRUE.equals(pushDataFlag[0])){
			situationGuideService.pushDataToRoomByGuide(null, guideHis);
			this.pushGuideRefreshWs(task.getRoomId());
		}
	}

	/**
	 * 提交评估
	 *
	 * @param taskSubmitId   任务提交id
	 * @param score          得分
	 * @param detail         详情
	 * @param reason         原因
	 * @param evaluateStatus 评估状态
	 * @param currentUserId  当前用户id
	 */
	@Override
	public void submitEvaluation(String taskSubmitId, Integer score, String detail, Integer reason,
			Integer evaluateStatus, String currentUserId) {
		// check permission??
		CrTaskSubmit taskSubmit = crTaskSubmitMapper.selectById(taskSubmitId);
		if (taskSubmit == null) {
			log.error("task_submit not found, id=[{}]", taskSubmitId);
			throw new BusinessException(CrBusinessErrorCode.MISSION_SUBMIT_NOT_FOUND);
		}
		if (taskSubmit.getEvaluateStatus().equals(CrTaskSubmit.EvaluateStatus.REJECT.getCode())) {
			log.error("task has been rejected, id=[{}]", taskSubmitId);
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
			log.error("score out of range:[{}]", score);
			throw new BusinessException(CrBusinessErrorCode.SCORE_RANGE_ERROR);
		}

		taskSubmit.setScore(score);
		taskSubmit.setDetail(detail);
		taskSubmit.setReason(reason);
		taskSubmit.setEvaluateStatus(evaluateStatus);
		taskSubmit.setEvaluatorId(currentUserId);
		taskSubmit.setEvaluateTime(new Date());

		Integer oldStatus = task.getStatus();
		task.setStatus(evaluateStatus);
		task.setScore(score);

		CrTaskLog evaluationLog = CrTaskLog.builder().roomId(taskSubmit.getRoomId())
				.missionId(task.getMissionId())
				.actionCode(evaluateStatusToActionCode.get(evaluateStatus)).userId(currentUserId)
				.build();

		//任务导调(任务导调已触发guideHis为null)
		SituationGuideHistory guideHis = situationGuideService.getGuideHisByMissionId(task.getMissionId(), task.getResourceId());
		//是否新增态势导调和动效
		final Boolean[] pushDataFlag = {false};
		if (Objects.nonNull(guideHis) && StringUtils.isNotBlank(guideHis.getSceneId())
				&& SituationGuideScheme.ConditionType.MISSION_SUCCESS.getCode().equals(guideHis.getTriggerCondition())
				&& CrTaskSubmit.EvaluateStatus.SUCCESS.getCode().equals(evaluateStatus)) {
			pushDataFlag[0] = true;
		}

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				crTaskMapper.updateById(task);
				crTaskSubmitMapper.updateById(taskSubmit);
				evaluationLog.setTaskId(task.getResourceId());
				crTaskLogMapper.insert(evaluationLog);

				//插入态势导调历史记录
				if (Boolean.TRUE.equals(pushDataFlag[0])){
					situationGuideHisMapper.insert(guideHis);
				}
			}
		});

		// 评估触发行业态势，原状态不为正确，评估为正确则触发
		if (StringUtils.isNotBlank(task.getMissionTemplateId())
				&& !oldStatus.equals(CrTask.TaskStatus.SUCCESS.getCode())
				&& evaluateStatus.equals(CrTask.TaskStatus.SUCCESS.getCode())) {
			industrySituationService.pushSituationWs(task.getRoomId(), task.getMissionTemplateId());
		}

		if (Boolean.TRUE.equals(pushDataFlag[0])){
			situationGuideService.pushDataToRoomByGuide(null, guideHis);
			this.pushGuideRefreshWs(task.getRoomId());
		}
	}

	/**
	 * 获取红蓝对抗任务评估列表
	 *
	 * @param missionEvaluationQuery 任务评估查询
	 * @return {@code IPage<MissionEvaluationContext> }
	 */
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

	/**
	 * 获取命令提交由任务
	 *
	 * @param resourceId 资源id
	 * @return {@code List<CrTaskSubmit> }
	 */
	@Override
	public List<CrTaskSubmit> getOrderedSubmitsByTask(String resourceId) {
		return crTaskSubmitMapper.selectOrderedSubmitsByTask(resourceId);
	}

	/**
	 * 设置任务
	 *
	 * @param data 数据
	 * @return {@code IPage<MissionEvaluationContext> }
	 */
	private IPage<MissionEvaluationContext> setMission(IPage<MissionEvaluationContext> data) {
		List<String> missionIds = data.getRecords().stream()
				.map(MissionEvaluationContext::getMissionHash).distinct()
				.collect(Collectors.toList());

		List<Mission> missions = Lists.newArrayList();
		if (missionIds.size() > 0) {
			missions = missionMapper.batchSelect(missionIds);
		}
		Map<String, Mission> missionMap = StreamApiUtils.toMap(missions, Mission::getResourceId,
				pc -> pc);

		data.convert(missionEvaluationContext -> {
			missionEvaluationContext
					.setMission(missionMap.get(missionEvaluationContext.getMissionHash()));

			return missionEvaluationContext;
		});
		return data;
	}

	/**
	 * 获取任务评估列表
	 *
	 * @param missionEvaluationQuery 任务评估查询
	 * @return {@code IPage<MissionEvaluationContext> }
	 */
	@Override
	public IPage<MissionEvaluationContext>
			getMissionEvaluationList(MissionEvaluationQuery missionEvaluationQuery) {
		missionEvaluationQuery.setSort("-evaluate_time");

		Page page = missionEvaluationQuery.toPageParam();
		IPage<MissionEvaluationContext> data = crTaskSubmitMapper.listCrTaskSubmit(page,
				missionEvaluationQuery);

		return setMission(data);
	}

	/**
	 * 推送任务WS
	 *
	 * @param businessId 业务id
	 * @param code       代号
	 */
	@Override
	public void pushMissionWS(String businessId, Integer code) {

		TaskWsData data = new TaskWsData(code);
		sendWsData(data, businessId);
	}

	@Override
	public void pushMissionDataWS(TaskMissionWsData taskMissionWsData) {
		sendWsData(taskMissionWsData, taskMissionWsData.getRoomId());
	}

	/**
	 * 推送任务WS自用户
	 *
	 * @param businessId 业务id
	 * @param userIds    用户id列表
	 * @param code       代号
	 */
	@Override
	public void pushMissionWsToUsers(String businessId, List<String> userIds, Integer code) {
		TaskWsData data = new TaskWsData(code);
		sendWsData2Users(data, businessId, userIds);
	}

	/**
	 * 推送状态WS
	 *
	 * @param businessId    业务id
	 * @param runningStatus 运行状态
	 */
	@Override
	public void pushStatusWs(String businessId, Integer runningStatus) {
		MissionWsUpdateStatusMessage message = new MissionWsUpdateStatusMessage(runningStatus);
		sendWsData(message, businessId);
	}

	/**
	 * 发送ws数据
	 * 多用于推送更新消息
	 * 包括任务下发、场景暂停
	 *
	 * @param data       数据
	 * @param businessId 业务标识
	 */
	private <T> void sendWsData(T data, String businessId) {
		MissionWsIdentifier identifier = new MissionWsIdentifier();
		identifier.setBusinessId(businessId);
		identifier.setUserId(UserInfoContext.getUserContext().getUserId());
		identifier.setPath(WsPath.CR_MISSION);

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().data(data).messageIdentifier(identifier).build();
		webSocketHandler.pushMessage(messageContext, true);
	}

	/**
	 * 向 WS Data2 用户发送
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 * @param userIds    用户id列表
	 */
	private <T> void sendWsData2Users(T data, String businessId, List<String> userIds) {
		MissionWsIdentifier identifier = new MissionWsIdentifier();
		identifier.setBusinessId(businessId);
		identifier.setUserId(UserInfoContext.getUserContext().getUserId());
		identifier.setPath(WsPath.CR_MISSION);

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().data(data).messageIdentifier(identifier).build();
		webSocketHandler.pushMessageToUsers(messageContext, userIds);
	}

	/**
	 * 评判目标
	 *
	 * @param userAnswer    用户答案
	 * @param missionAnswer 任务答案
	 * @param missionType   任务类型
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean judgeObjective(String userAnswer, String missionAnswer, Integer missionType) {
		return isCorrect(userAnswer, missionAnswer, missionType);
	}

	/**
	 * 计数未完成由房间id和岗位id列表
	 *
	 * @param roomId    房间id
	 * @param crRoleIds 靶场岗位id列表
	 * @return {@code Integer }
	 */
	@Override
	public Integer countUnfinishedByRoomIdAndRoleIds(String roomId, List<String> crRoleIds) {
		return crTaskMapper.countByRoomIdAndRoleIdsAndStatusList(roomId, crRoleIds,
				CrTask.TaskStatus.UNFINISHED_STATUS_LIST);
	}

	/**
	 * 计数未提交由房间id和岗位id列表
	 *
	 * @param roomId    房间id
	 * @param crRoleIds 靶场岗位id列表
	 * @return {@code Integer }
	 */
	@Override
	public Integer countUnsubmittedByRoomIdAndRoleIds(String roomId, List<String> crRoleIds) {
		return crTaskMapper.countByRoomIdAndRoleIdsAndStatusList(roomId, crRoleIds,
				CrTask.TaskStatus.UNSUBMITTED_STATUS_LIST);
	}

	/**
	 * 计数完成由房间id和岗位id列表
	 *
	 * @param roomId    房间id
	 * @param crRoleIds 靶场岗位id列表
	 * @return {@code Integer }
	 */
	@Override
	public Integer countFinishedByRoomIdAndRoleIds(String roomId, List<String> crRoleIds) {
		return crTaskMapper.countByRoomIdAndRoleIdsAndStatusList(roomId, crRoleIds,
				CrTask.TaskStatus.FINISHED_STATUS_LIST);
	}

	/**
	 * 计数由房间id和岗位id列表
	 *
	 * @param roomId    房间id
	 * @param crRoleIds 靶场岗位id列表
	 * @return {@code Integer }
	 */
	@Override
	public Integer countByRoomIdAndRoleIds(String roomId, List<String> crRoleIds) {
		return crTaskMapper.countByRoomIdAndRoleIds(roomId, crRoleIds);
	}

	/**
	 * 获取评估列表
	 *
	 * @param req 请求体
	 * @return {@code List<EvaluationListRsp> }
	 */
	@Override
	public List<EvaluationListRsp> getEvaluationList(EvaluationListReq req) {
		// get task_submit list
		List<EvaluationListBo> boList = crTaskSubmitMapper.list(req.getRoom_id(), req.getRole_id(),
				req.getUser_id(), req.getStatus());
		List<EvaluationListRsp> rspList = BeanUtils.deepCopyList(boList, EvaluationListRsp.class);
		List<String> userIds = rspList.stream().map(EvaluationListBo::getSubmitUser)
				.collect(Collectors.toList());
		// get user info
		Map<String, UserInfo> userInfoMap = remoteUserService.getIdUserInfoMap(userIds);
		for (EvaluationListRsp rsp : rspList) {
			UserInfo userInfo = userInfoMap.get(rsp.getSubmitUser());
			if (userInfo == null) {
				continue;
			}
			rsp.setSubmitUserAvatar(userInfo.getAvatarUrl());
			rsp.setSubmitUserName(userInfo.getName());
		}
		rspList.sort(new EvaluationListRsp.EvaluationListComparator());
		return rspList;
	}

	/**
	 * 获取评估由id
	 *
	 * @param taskSubmitId 任务提交id
	 * @return {@code EvaluationMissionRsp }
	 */
	@Override
	public EvaluationMissionRsp getEvaluationById(String taskSubmitId) {
		EvaluationMissionRsp rsp = new EvaluationMissionRsp();
		EvaluationMissionBo bo = crTaskSubmitMapper.getMissionBoById(taskSubmitId);
		if (null == bo) {
			return rsp;
		}
		rsp.setResourceId(bo.getResourceId());
		rsp.setTitle(bo.getTitle());
		rsp.setDescription(bo.getDescription());
		rsp.setType(bo.getType());
		rsp.setOption(CrCommonUtil.parseOption(bo.getOption()));
		if (bo.getType().equals(MissionBase.MissionType.MULTIPLE_CHOICE.getCode())) {
			rsp.setAnswer(CrCommonUtil.handleMultiAnswer(bo.getAnswer()));
			rsp.setUserAnswer(CrCommonUtil.handleMultiAnswer(bo.getUserAnswer()));
		} else {
			rsp.setAnswer(bo.getAnswer());
			rsp.setUserAnswer(bo.getUserAnswer());
		}
		rsp.setSkillPoints(JsonUtil.toJsonObjectArray(bo.getSkillPoints(), Integer.class));
		rsp.setWriteUp(bo.getWriteUp());
		rsp.setScore(bo.getScore());
		return rsp;
	}

	/**
	 * 获取任务矩阵排除时间成本
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code TaskMatrix }
	 */
	@Override
	public TaskMatrix getTaskMatrixExcludeTimeCost(String roomId,String userId) {
		List<CrTask> list = this.getByRoomIdAndUserId(roomId,userId);
		List<String> missionIds = list.stream().map(CrTask::getMissionId).collect(Collectors.toList());
		List<Mission> missions = CollectionUtils.isEmpty(missionIds)?new ArrayList<>(): missionMapper.selectBatchIds(missionIds);
		Map<String, Mission> missionMap = missions.stream().collect(Collectors.toMap(Mission::getResourceId, Function.identity()));
		return new TaskMatrix().init(list,missionMap);
	}

	/**
	 * 获取任务统计学信息由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code TaskStatisticsInfo }
	 */
	@Override
	public TaskStatisticsInfo getTaskStatisticsInfoByRoomId(String roomId) {
		List<CrTaskSubmit> taskSubmitList = crTaskSubmitMapper.selectByRoomId(roomId);
		long success = taskSubmitList.stream().filter(task -> task.getEvaluateStatus()
				.equals(CrTaskSubmit.EvaluateStatus.SUCCESS.getCode())).count();
		long fail = taskSubmitList.stream().filter(
				task -> task.getEvaluateStatus().equals(CrTaskSubmit.EvaluateStatus.FAIL.getCode()))
				.count();
		long reject = taskSubmitList.stream().filter(task -> task.getEvaluateStatus()
				.equals(CrTaskSubmit.EvaluateStatus.REJECT.getCode())).count();
		long wait = taskSubmitList.stream().filter(
				task -> task.getEvaluateStatus().equals(CrTaskSubmit.EvaluateStatus.WAIT.getCode()))
				.count();
		int submitted = taskSubmitList.size();

		TaskStatisticsInfo info = new TaskStatisticsInfo();
		info.setSuccessMissionsCount((int) success);
		info.setFailMissionsCount((int) fail);
		info.setRejectMissionsCount((int) reject);
		info.setWaitMissionsCount((int) wait);
		info.setSubmitMissionCount(submitted);
		return info;
	}

	/**
	 * 获取任务回复由任务id
	 *
	 * @param taskId 任务id
	 * @return {@code List<CrTaskRejectRsp> }
	 */
	@Override
	public List<CrTaskRejectRsp> getTaskRejctsByTaskId(String taskId) {
		List<CrTaskRejectRsp> rejectLogs = new ArrayList<>();
		//评估处理
		List<CrTaskSubmit> taskSubmits = this
				.getOrderedSubmitsByTask(taskId);

		// 获取评估人名字
		List<String> userIds = taskSubmits.stream().map(CrTaskSubmit::getEvaluatorId).distinct()
				.collect(Collectors.toList());
		Map<String, String> userInfoMap = remoteUserService.getIdNameMap(userIds);

		// 未提交过
		if (taskSubmits.size() != 0) {
			for (CrTaskSubmit taskSubmit : taskSubmits) {
				if (taskSubmit.getEvaluateStatus()
						.equals(CrTaskSubmit.EvaluateStatus.REJECT.getCode())) {
					CrTaskRejectRsp crTaskReject = new CrTaskRejectRsp();

					crTaskReject.setRejectTime(taskSubmit.getUpdateTime());
					crTaskReject.setRejectReason(taskSubmit.getReason());
					crTaskReject.setReferee(userInfoMap.get(taskSubmit.getEvaluatorId()));

					rejectLogs.add(crTaskReject);
				}
			}
		}

		return rejectLogs;
	}

	/**
	 * 提交任务
	 *
	 * @param roomId    房间id
	 * @param missionId 任务id
	 * @param answer    答案
	 * @param remark    备注
	 */
	@Override
	public void submitMissions(String roomId, String missionId, String answer, String remark) {
		submitAnswer(missionId, answer, UserInfoContext.getUserContext().getUserId(), "",remark);
	}

	/**
	 * 推送导调刷新WS
	 *
	 * @param roomId 房间id
	 */
	private void pushGuideRefreshWs(String roomId){
		this.pushMissionWS(roomId, MissionWsCode.SITUATION_GUIDE);
	}

}