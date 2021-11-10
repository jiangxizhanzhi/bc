package com.cyberpeace.cr.crbase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.dao.entity.Task;
import com.cyberpeace.cr.crbase.dao.mapper.TaskMapper;
import com.cyberpeace.cr.crbase.service.TaskService;

@Service
public class TaskServiceImpl implements TaskService {

	@Autowired
	private TaskMapper taskMapper;

	@Override
	public void addTask(Task task) {
		taskMapper.insert(task);
	}

	@Override
	public Task getById(String taskId) {
		return taskMapper.selectById(taskId);
	}

	@Override
	public Task getByMissionAndUser(String missionId, String userId) {
		return taskMapper.selectByMissionIdAndUserId(missionId, userId);
	}
}
