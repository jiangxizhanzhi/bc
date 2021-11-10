package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.dao.entity.Task;

public interface CrTaskService {

	void addTask(Task task);

	Task getById(String taskId);

	/**
	 * 根据mission和user获取task
	 * @param missionId mission_id
	 * @param userId user_id
	 * @return Task 实例
	 */
	Task getByMissionAndUser(String missionId, String userId);
}
