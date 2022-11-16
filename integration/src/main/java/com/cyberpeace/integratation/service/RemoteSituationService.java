package com.cyberpeace.integratation.service;

import com.cyberpeace.integratation.model.situation.SituationPlan;
import com.cyberpeace.integratation.model.situation.SituationSubject;
import com.cyberpeace.integratation.model.situation.SituationTask;

import java.util.List;


public interface RemoteSituationService {

	/**
	 * 根据任务获取科目list
	 * @param taskId 任务id
	 * @return
	 */
	List<SituationSubject> getSubjectByTask(String taskId);

	/**
	 * 根据任务类型获取任务list
	 * @param type 任务类型
	 * @param resourceId taskId
	 * @return
	 */
	List<SituationTask> getTasks(String type, String resourceId);


	/**
	 * 获取所有想定方案
	 * @return
	 */
	List<SituationPlan> getPlans();



}
