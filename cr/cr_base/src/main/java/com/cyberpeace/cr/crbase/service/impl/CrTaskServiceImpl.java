package com.cyberpeace.cr.crbase.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.context.TaskContext;

@Service
public class CrTaskServiceImpl extends ServiceImpl<CrTaskMapper, CrTask> implements CrTaskService {

	@Autowired
	private CrTaskMapper crTaskMapper;

	@Autowired
	private MissionService missionService;

	@Override
	public void addTask(CrTask crTask) {
		crTaskMapper.insert(crTask);
	}

	@Override
	public CrTask getById(String taskId) {
		return crTaskMapper.selectById(taskId);
	}

	@Override
	public TaskContext getByMissionAndUser(String missionId, String userId) {
		CrTask task = crTaskMapper.selectByMissionIdAndUserId(missionId, userId);
		TaskContext taskContext = BeanUtils.copy(task, TaskContext.class);
		taskContext.setMission(missionService.getById(task.getMissionId()));

		return taskContext;
	}

	@Override
	public void submitMissions(String roomId, String missionId, String answer) {

	}

	@Override
	public List<TaskContext> getByRoomIdAndUser(String roomId, String userId) {
		return new ArrayList<>();
	}
}
