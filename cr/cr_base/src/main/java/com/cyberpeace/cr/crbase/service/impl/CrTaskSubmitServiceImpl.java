package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskSubmitMapper;
import com.cyberpeace.cr.crbase.dao.mapper.MissionMapper;
import com.cyberpeace.cr.crbase.service.CrTaskSubmitService;

public class CrTaskSubmitServiceImpl extends ServiceImpl<CrTaskSubmitMapper, CrTaskSubmit> implements CrTaskSubmitService {

	@Autowired
	private CrTaskSubmitMapper crTaskSubmitMapper;

	@Autowired
	private MissionMapper missionMapper;

	@Autowired
	private CrTaskMapper crTaskMapper;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public CrTaskSubmit addCrTaskSubmit(CrTaskSubmit crTaskSubmit) {
		crTaskSubmitMapper.insert(crTaskSubmit);
		return crTaskSubmitMapper.selectById(crTaskSubmit.getResourceId());
	}

	@Override
	public CrTaskSubmit create(CrTask task, String answer, String answerExtension) {
		CrTaskSubmit lastSubmit = crTaskSubmitMapper.selectLastByTaskId(task.getResourceId());
		if (lastSubmit != null && lastSubmit.getEvaluateStatus()
				.equals(CrTaskSubmit.EvaluateStatus.REJECT.getCode())) {
			throw new RuntimeException("not allowed to be submitted");
		}

		Mission mission = missionMapper.selectById(task.getMissionId());

		CrTaskSubmit crTaskSubmit = new CrTaskSubmit();
		crTaskSubmit.setTaskId(task.getResourceId());
		crTaskSubmit.setRoomId(task.getRoomId());
		crTaskSubmit.setMissionScore(mission.getScore());
		crTaskSubmit.setAnswer(answer);
		crTaskSubmit.setAnswerExtension(answerExtension);
		crTaskSubmit.setResourceId(IDGeneratorUtil.getID());

		if (Mission.MissionType.getOBJECTIVE_TYPE_LIST().contains(mission.getType())) {
			if (isCorrect(answer, mission.getAnswer(), mission.getType())) {
				task.setScore(mission.getScore());
				task.setStatus(CrTaskSubmit.EvaluateStatus.SUCCESS.getCode());
			} else {
				task.setScore(0);
				task.setStatus(CrTaskSubmit.EvaluateStatus.FAIL.getCode());
			}
		} else {
			task.setScore(0);
			task.setStatus(CrTaskSubmit.EvaluateStatus.WAIT.getCode());
		}

		task.setUserAnswer(answer);

		CrTaskSubmit execute = transactionTemplate.execute(new TransactionCallback<CrTaskSubmit>() {

			@Override
			public CrTaskSubmit doInTransaction(TransactionStatus transactionStatus) {
				crTaskMapper.updateById(task);
				crTaskSubmitMapper.insert(crTaskSubmit);
				// todo
				return crTaskSubmit;
			}
		});

		return execute;

	}

	private boolean isCorrect(String userAnswer, String missionAnswer, Integer missionType) {
		// xiantoulan
		return userAnswer.equals(missionAnswer);
	}

	@Override
	public CrTaskSubmit getById(String taskSubmitId) {
		return crTaskSubmitMapper.selectById(taskSubmitId);
	}

	@Override
	public List<CrTaskSubmit> getByRoom(String roomId) {
		return crTaskSubmitMapper.selectByRoomId(roomId);
	}

	// @Override
	// public CrTaskSubmit updateWhenEvaluate(CrTaskSubmit crTaskSubmit, String
	// evaluatorId,
	// Integer score, String detail, Integer reason, Integer evaluateStatus) {
	// crTaskSubmit.setEvaluatorId(evaluatorId);
	// crTaskSubmit.setScore(score);
	// crTaskSubmit.setDetail(detail);
	// crTaskSubmit.setReason(reason);
	// crTaskSubmit.setEvaluateStatus(evaluateStatus);
	// crTaskSubmitMapper.updateById(crTaskSubmit);
	//
	// return crTaskSubmitMapper.selectById(crTaskSubmit.getResourceId());
	// }

	@Override
	public CrTaskSubmit getOrderByEvaluateTimeASC(String taskId) {
		return null;
	}

	@Override
	public CrTaskSubmit getLastByTask(String taskId) {
		return crTaskSubmitMapper.selectLastByTaskId(taskId);
	}

	@Override
	public List<CrTaskSubmit> getByUserIds(List<String> userIds) {
		return null;
	}

	@Override
	public List<CrTaskSubmit> getSubmitsByUserStatusOrderByEvaluateTime() {
		return null;
	}

	@Override
	public List<CrTaskSubmit> getSubmitsByParamsOrderByEvaluateTime() {
		return null;
	}

	// @Override
	// public Dictionary getEvaluationCountByRoom() {
	// return null;
	// }
	//
	// @Override
	// public List<CrTaskSubmit> getRejectedByTask(String taskId) {
	// return null;
	// }
	//
	// @Override
	// public Map getRejectedInfo() {
	// return null;
	// }
	//
	// @Override
	// public Map getSubmitInfo() {
	// return null;
	// }
}
