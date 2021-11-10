package com.cyberpeace.cr.crbase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.cr.crbase.dao.entity.Task;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.service.CrTaskService;

@Service
public class CrTaskServiceImpl implements CrTaskService {

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
	public Task getByMissionAndUser(String missionId, String userId) {
		return crTaskMapper.selectByMissionIdAndUserId(missionId, userId);
	}
}
