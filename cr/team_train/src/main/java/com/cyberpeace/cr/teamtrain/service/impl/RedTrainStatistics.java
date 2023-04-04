package com.cyberpeace.cr.teamtrain.service.impl;

import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingPeopleStatistics;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cs.dao.entity.LocalScene;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedTrainStatistics extends TeamTrainStatistics {

	@Autowired
	TeamRoomMapper teamRoomMapper;

	@Autowired
	CrRoleMapper crRoleMapper;

	@Override
	public LocalScene.BusinessCode getCode() {
		return LocalScene.BusinessCode.RED_TRAINING;
	}

	@Override
	public TrainingCount getTrainingCount(String userId) {
		List<String> roomIds = crRoleMapper.getRoomIdsByUserIdAndType(userId,
				BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
		if (roomIds.isEmpty()) {
			return new TrainingCount();
		}
		return teamRoomMapper.getTrainingCount(roomIds,
				TeamRoom.TrainingStatusCode.ENDED.getCode(), LocalScene.BusinessCode.RED_TRAINING.getCode());
	}

	@Override
	public void getPeopleCount(String userId, TrainingPeopleStatistics trainingPeopleStatistics) {
		getPeopleCountByCode(userId, trainingPeopleStatistics, TeamRoom.TrainCategory.RED.getCode());
	}
}
