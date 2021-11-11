package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;

public interface CrTaskSubmitService extends IService<CrTaskSubmit> {

	CrTaskSubmit addCrTaskSubmit(CrTaskSubmit crTaskSubmit);

	CrTaskSubmit create(CrTask task, String answer, String answerExtension);

	CrTaskSubmit getById(String taskSubmitId);

	List<CrTaskSubmit> getByRoom(String roomId);

	// CrTaskSubmit updateWhenEvaluate(CrTaskSubmit crTaskSubmit, String
	// evaluatorId, Integer score,
	// String detail, Integer reason, Integer evaluateStatus);

	CrTaskSubmit getOrderByEvaluateTimeASC(String taskId);

	CrTaskSubmit getLastByTask(String taskId);

	// CrTaskSubmit getById(String submitId);

	List<CrTaskSubmit> getByUserIds(List<String> userIds);

	// ??
	List<CrTaskSubmit> getSubmitsByUserStatusOrderByEvaluateTime();

	// ?
	List<CrTaskSubmit> getSubmitsByParamsOrderByEvaluateTime();

	// getEvaluationCountByRoom();
	//
	// List<CrTaskSubmit> getRejectedByTask(String taskId);
	//
	// getRejectedInfo();
	//
	// getSubmitInfo();

}
