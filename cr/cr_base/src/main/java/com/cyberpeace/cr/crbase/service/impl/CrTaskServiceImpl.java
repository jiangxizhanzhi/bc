package com.cyberpeace.cr.crbase.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.dao.mapper.MissionMapper;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.TaskContext;

@Service
public class CrTaskServiceImpl extends ServiceImpl<CrTaskMapper, CrTask> implements CrTaskService {

	@Autowired
	private CrTaskMapper crTaskMapper;

	@Autowired
	private MissionMapper missionMapper;

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
		CrTask task = crTaskMapper.selectByUserIdAndMissionId(userId, missionId);
		TaskContext taskContext = BeanUtils.copy(task, TaskContext.class);
		taskContext.setMission(missionMapper.selectById(task.getMissionId()));

		return taskContext;
	}

	@Override
	public void submitMissions(String roomId, String missionId, String answer) {

	}

	@Override
	public List<TaskContext> getByRoomIdAndUser(String roomId, String userId) {
		return new ArrayList<>();
	}

	@Override
	public void startTask(CrTask task) {
		task.setStatus(CrTask.TaskStatus.STARTED.getCode());
		crTaskMapper.updateById(task);
	}

	@Override
	public void updateWhenSubmit(CrTask task, String answer, Integer score, Integer taskStatus) {
		task.setUserAnswer(answer);
		task.setScore(score);
		task.setStatus(taskStatus);

		crTaskMapper.updateById(task);
	}

	@Override
	public void updateWhenEvaluate(CrTask task, Integer score, Integer taskStatus) {
		task.setScore(score);
		task.setStatus(taskStatus);

		crTaskMapper.updateById(task);
	}

	@Override
	public CrTask getByUserIdAndMissionId(String userId, String missionId) {
		return crTaskMapper.selectByUserIdAndMissionId(userId, missionId);
	}

	@Override
	public List<CrTask> getByRoomIdAndUserId(String roomId, String userId) {
		return crTaskMapper.selectByRoomIdAndUserId(roomId, userId);
	}

	@Override
	public Integer countByRoomIdAndUserId(String roomId, String userId) {
		return crTaskMapper.countByRoomIdAndUserId(roomId, userId);
	}

	@Override
	public List<CrTask> getFinished(String roomId) {
		return crTaskMapper.selectByRoomIdAndStatusList(roomId,
				CrTask.TaskStatus.FINISHED_STATUS_LIST);
	}

	@Override
	public Integer countFinished(String roomId) {
		return crTaskMapper.countByRoomIdAndStatusList(roomId,
				CrTask.TaskStatus.FINISHED_STATUS_LIST);
	}

	@Override
	public Integer countNotSubmitted(String roomId) {
		return crTaskMapper.countByRoomIdAndStatusList(roomId,
				CrTask.TaskStatus.NOT_SUBMITTED_STATUS_LIST);
	}

	@Override
	public Integer countNotFinished(String roomId) {
		return crTaskMapper.countByRoomIdAndStatusList(roomId,
				CrTask.TaskStatus.UNFINISHED_STATUS_LIST);
	}

	@Override
	public Integer countFinishedByRoomIdAndUserId(String roomId, String userId) {
		return crTaskMapper.countByRoomIdAndUserIdAndStatusList(roomId, userId,
				CrTask.TaskStatus.FINISHED_STATUS_LIST);
	}

	@Override
	public List<CrTask> getFinishedByRoomIdAndUserId(String roomId, String userId) {
		return crTaskMapper.selectByRoomIdAndUserIdAndStatusList(roomId, userId,
				CrTask.TaskStatus.FINISHED_STATUS_LIST);
	}

	@Override
	public List<CrTask> getByRoomId(String roomId) {
		return crTaskMapper.selectByRoomId(roomId);
	}

	@Override
	public List<CrTask> getByRole(String roleId) {
		return crTaskMapper.selectByRole(roleId);
	}

	@Override
	public List<CrTask> getByRoles(List<String> roleIds) {
		return crTaskMapper.selectByRoles(roleIds);
	}

	@Override
	public List<CrTask> getByMissions(String roomId, List<String> missionIds) {
		return crTaskMapper.selectByRoomIdAndMissionIds(roomId, missionIds);
	}

	@Override
	public List<CrTask> getReportedByUserIdsAndMissions(String roomId, List<String> userIds,
			List<String> missionIds) {
		return crTaskMapper.selectReportedByUserIdsAndMissionIds(roomId, userIds, missionIds);
	}

	@Override
	public Integer countUnreportedByUsers(String roomId, List<String> userIds) {
		return crTaskMapper.countUnreportedByRoomIdAndUserIds(roomId, userIds);
	}

	@Override
	public boolean isReleasedByMission(String missionId) {
		return crTaskMapper.selectByMissionId(missionId) != null;
	}

	@Override
	public List<String> getIdsByUserIds(String roomId, List<String> userIds) {
		return crTaskMapper.selectIdsByRoomIdAndUserIds(roomId, userIds);
	}

	@Override
	public List<String> getIdsByOptionalUserId(String roomId, String userId) {
		return crTaskMapper.selectIdsByRoomIdAndUserId(roomId, userId);
	}

	@Override
	public Boolean updateTask(CrTask task) {
		if (StringUtils.isBlank(task.getResourceId())) {
			return false;
		}

		crTaskMapper.updateById(task);

		return true;
	}
}
