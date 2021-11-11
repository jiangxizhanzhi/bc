package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.dao.entity.Task;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.TaskContext;

@Service
public class CrTaskServiceImpl implements CrTaskService {

	@Autowired
	private MissionService missionService;

	@Autowired
	private CrTaskMapper crTaskMapper;

	@Override
	public void addTask(Task task) {
		crTaskMapper.insert(task);
	}

	@Override
	public Task getById(String taskId) {
		return crTaskMapper.selectById(taskId);
	}

	@Override
	public TaskContext getByMissionAndUser(String missionId, String userId) {
		Task task = crTaskMapper.selectByMissionIdAndUserId(missionId, userId);
		TaskContext taskContext = BeanUtils.copy(task, TaskContext.class);
		taskContext.setMission(missionService.getById(task.getMissionId()));

		return taskContext;
	}

	@Override
	public void submitMissions(String roomId, String missionId, String answer) {

	}

	@Override
	public List<TaskContext> getByRoomIdAndUser(String roomId, String userId) {
		return null;
	}
}
