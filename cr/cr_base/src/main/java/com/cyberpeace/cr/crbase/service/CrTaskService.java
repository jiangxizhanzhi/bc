package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.service.context.TaskContext;

public interface CrTaskService {

	void addTask(CrTask task);

	CrTask getById(String taskId);

	/**
	 * 根据mission和user获取task
	 * @param missionId mission_id
	 * @param userId user_id
	 * @return Task 实例
	 */
	TaskContext getByMissionAndUser(String missionId, String userId);

	/** 提交任务答案
	 * @param roomId
	 * @param missionId
	 * @param answer
	 */
	void submitMissions(String roomId, String missionId, String answer);

	/**
	 * 根据roomId和user获取task
	 * @param roomId 
	 * @param userId 
	 * @return List<Task>  实例列表
	 */
	List<TaskContext> getByRoomIdAndUser(String roomId, String userId);
}
