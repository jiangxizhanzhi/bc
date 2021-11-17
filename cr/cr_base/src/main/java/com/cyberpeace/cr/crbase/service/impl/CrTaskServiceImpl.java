package com.cyberpeace.cr.crbase.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskLog;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskLogMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskSubmitMapper;
import com.cyberpeace.cr.crbase.dao.mapper.MissionMapper;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import com.cyberpeace.cr.crbase.service.context.TaskContext;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

/**
 * @author SN
 */
@Service
public class CrTaskServiceImpl extends ServiceImpl<CrTaskMapper, CrTask> implements CrTaskService {

	@Autowired
	private CrTaskMapper crTaskMapper;

	@Autowired
	private MissionMapper missionMapper;

	@Autowired
	private CrTaskSubmitMapper crTaskSubmitMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private CrTaskLogMapper crTaskLogMapper;

	@Override
	public CrTask getById(String taskId) {
		return crTaskMapper.selectById(taskId);
	}

	@Override
	public TaskContext getByMissionAndUser(String missionId, String userId) {
		CrTask task = crTaskMapper.selectByUserIdAndMissionId(userId, missionId);
		if (null == task) {
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
	public List<TaskContext> getByRoomIdAndUser(String roomId, String userId) {
		List<CrTask> taskList = crTaskMapper.selectByRoomIdAndUserId(roomId, userId);
		List<String> missionIds = new ArrayList<>();

		for (CrTask task : taskList) {
			missionIds.add(task.getMissionId());
		}
		List<Mission> missionList = missionMapper.batchSelect(missionIds);
		Map<String, Mission> missionMap = Maps.newHashMap();
		for (Mission mission : missionList) {
			missionMap.put(mission.getResourceId(), mission);
		}

		List<TaskContext> taskContextList = new ArrayList<>();
		for (CrTask task : taskList) {
			TaskContext taskContext = BeanUtils.copy(task, TaskContext.class);
			taskContext.setMission(missionMap.get(task.getMissionId()));
			taskContextList.add(taskContext);
		}

		return taskContextList;
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

	private Boolean isCorrect(String userAnswer, String missionAnswer, Integer missionType) {
		// xiantoulan
		return userAnswer.equals(missionAnswer);
	}

	@Override
	public void submitAnswer(String missionId, String answer, String userId,
			String answerExtension) {
		Mission mission = missionMapper.selectById(missionId);
		if (mission == null) {
			throw new BusinessException(CrBusinessErrorCode.MISSION_NOT_EXIST);
		}

		CrTask task = crTaskMapper.selectByUserIdAndMissionId(userId, missionId);
		if (task == null) {
			throw new BusinessException(CrBusinessErrorCode.MISSION_UNRELEASED);
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
		crTaskSubmit.setAnswerExtension(answerExtension);
		crTaskSubmit.setResourceId(IDGeneratorUtil.getID());

		// 创建提交log
		CrTaskLog submitLog = createTaskLog(task.getRoomId(), missionId,
				CrTaskLog.ActionCode.SUBMIT.getCode(), userId);

		// 创建可能存在的评估log
		CrTaskLog evaluateLog = createTaskLog(task.getRoomId(), missionId, null, null);;

		if (mission.isObjective()) {
			// 客观题，可机评
			if (isCorrect(answer, mission.getAnswer(), mission.getType())) {
				task.setScore(mission.getScore());
				task.setStatus(CrTaskSubmit.EvaluateStatus.SUCCESS.getCode());
				evaluateLog.setActionCode(CrTaskLog.ActionCode.ROBOT_SUCCESS.getCode());
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

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				crTaskMapper.updateById(task);
				crTaskSubmitMapper.insert(crTaskSubmit);
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

		CrTaskLog evaluationLog = createTaskLog(taskSubmit.getRoomId(), task.getMissionId(),
				evaluateStatusToActionCode.get(evaluateStatus), currentUserId);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				crTaskMapper.updateById(task);
				crTaskSubmitMapper.updateById(taskSubmit);
				crTaskLogMapper.insert(evaluationLog);
			}
		});

	}

	@Override
	public IPage<MissionEvaluationContext>
			getMissionEvaluationList(MissionEvaluationQuery missionEvaluationQuery) {
		missionEvaluationQuery.setSort("-evaluate_time");
		Page page = missionEvaluationQuery.toPageParam();
		IPage<CrTaskSubmit> data = crTaskSubmitMapper.listCrTaskSubmit(page,
				missionEvaluationQuery);

		List<String> taskIds = data.getRecords().stream().map(CrTaskSubmit::getTaskId)
				.collect(Collectors.toList());

		return null;
	}

	private CrTaskLog createTaskLog(String roomId, String missionId, Integer actionCode,
			String userId) {
		CrTaskLog crTaskLog = new CrTaskLog();
		crTaskLog.setRoomId(roomId);
		crTaskLog.setMissionId(missionId);
		crTaskLog.setActionCode(actionCode);
		crTaskLog.setUserId(userId);

		return crTaskLog;
	}

	private final Map<Integer, Integer> evaluateStatusToActionCode = ImmutableMap.of(
			CrTaskSubmit.EvaluateStatus.SUCCESS.getCode(),
			CrTaskLog.ActionCode.MAN_SUCCESS.getCode(), CrTaskSubmit.EvaluateStatus.FAIL.getCode(),
			CrTaskLog.ActionCode.MAN_FAILURE.getCode(),
			CrTaskSubmit.EvaluateStatus.REJECT.getCode(),
			CrTaskLog.ActionCode.MAN_REJECT.getCode());

}