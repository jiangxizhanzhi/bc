package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;
import java.util.Map;

import com.cyberpeace.cr.crbase.dao.mapper.TrainRecordMapper;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingDateCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.service.CrEventStatistics;
import com.cyberpeace.cr.crbase.service.context.statistics.RoomRoleContext;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingData;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class TeamTrainStatistics extends CrEventStatistics {

	@Autowired
	TrainRecordMapper trainRecordMapper;

	@Autowired
	TeamRoomMapper teamRoomMapper;

	@Autowired
	CrRoleMapper crRoleMapper;

	@Override
	public LocalScene.BusinessCode getCode() {
		return LocalScene.BusinessCode.TEAM_TRAINING;
	}

	@Override
	public List<TrainingDateCount> getTrainingCountByDate(String userId) {
		return trainRecordMapper.countFinishedByDate(userId,
				BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
	}

	@Override
	public List<TrainingDateCount> getAllTrainingCountByDate() {
		return trainRecordMapper.countFinishedByDate(null,
				BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
	}

	@Override
	public TrainingCount getTrainingCount(String userId) {
		List<String> roomIds = crRoleMapper.getRoomIdsByUserIdAndType(userId,
				BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
		if (roomIds.isEmpty()) {
			return new TrainingCount();
		}
		return teamRoomMapper.getTrainingCount(roomIds,
				TeamRoom.TrainingStatusCode.ENDED.getCode());
	}

	@Override
	public List<TrainingData> getRelatedTraining(String userId) {
		List<RoomRoleContext> roomRoleContexts = crRoleMapper.getRoomIdsAndRoleGroupByUserIdAndType(
				userId, BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
		if (roomRoleContexts.isEmpty()) {
			return Lists.newArrayList();
		}
		List<String> roomIds = Lists.newArrayList();
		Map<String, Integer> roleGroupMap = Maps.newHashMap();
		for (RoomRoleContext roomRoleContext : roomRoleContexts) {
			roomIds.add(roomRoleContext.getRoomId());
			roleGroupMap.put(roomRoleContext.getRoomId(), roomRoleContext.getRoleGroup());
		}
		List<TrainingData> related = Lists.newArrayList();
		// 查询全部
		List<TeamRoom> rooms = teamRoomMapper.selectBatchIds(roomIds);

		for (TeamRoom room : rooms) {
			TrainingData data = BeanUtils.copy(room, TrainingData.class);
			data.setAppId(LocalScene.BusinessCode.TEAM_TRAINING.getCode());
			data.setAppName(LocalScene.BusinessCode.TEAM_TRAINING.getDesc());
			// 计算持续时长
			Integer durationTime = (int) ((room.getEndTime().getTime()
					- room.getStartTime().getTime()) / (60 * 60 * 1000));
			data.setDurationTime(durationTime);
			data.setState(room.getTrainingStatus());
			data.setRoleGroup(roleGroupMap.get(room.getResourceId()));

			related.add(data);
		}
		return related;
	}
}
