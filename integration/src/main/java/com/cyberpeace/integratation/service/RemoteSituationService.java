package com.cyberpeace.integratation.service;

import com.cyberpeace.integratation.model.situation.*;

import java.util.List;


public interface RemoteSituationService {

	/**
	 * 根据任务获取科目list
	 * @param taskIds 任务id
	 * @return
	 */
	List<SituationSubject> getSubjectByTasks(List<String> taskIds);

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

	/**
	 * 获取分靶场详情
	 * @return SubRangeInfoContext
	 */
	SubRangeInfoContext getSubRangeInfo(String subRangeId);


	/**
	 * 获取LG中心详情
	 * @return CenterInfoContext
	 */
	CenterInfoContext getCenterInfo(String centerId);

	/**
	 * 获取平台列表
	 * @return List<PlatformContext>
	 */
	List<PlatformContext> getPlatformList(String taskId, String subjectId, String subRangeId);

}
