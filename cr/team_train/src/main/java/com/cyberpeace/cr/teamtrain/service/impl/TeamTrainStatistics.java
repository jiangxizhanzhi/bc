package com.cyberpeace.cr.teamtrain.service.impl;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.controller.rsp.TrainStatsOverviewRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.service.CrEventStatistics;
import com.cyberpeace.cr.crbase.service.context.BarChartUnit;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.statistics.*;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TeamTrainStatistics extends CrEventStatistics {

	@Autowired
	TeamRoomMapper teamRoomMapper;

	@Autowired
	CrRoleMapper crRoleMapper;

	@Autowired
	CrTaskMapper crTaskMapper;

	@Override
	public LocalScene.BusinessCode getCode() {
		return LocalScene.BusinessCode.TEAM_TRAINING;
	}

	@Override
	public TrainingCount getTrainingCount(String userId) {
		List<String> roomIds = crRoleMapper.getRoomIdsByUserIdAndType(userId,
				BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
		if (roomIds.isEmpty()) {
			return new TrainingCount();
		}
		return teamRoomMapper.getTrainingCount(roomIds,
				TeamRoom.TrainingStatusCode.ENDED.getCode(), null);
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

	@Override
	public List<CrRoleNode> getCrRoleNodeList(String userId) {
		List<CrRole> crRoleList = crRoleMapper.getByUserIdAndCode(userId, BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
		List<CrRoleNode> crRoleNodeList = new ArrayList<>();
		crRoleList.forEach(crRole -> {
			if (crRole.getRoleGroup().equals(CrRole.TeamRoleGroup.STUDENT.getCode())) {
				crRoleNodeList.addAll(crRole.getNodeData().getAssetNodes());
			}
		});
		return crRoleNodeList;
	}

	public void getPeopleCountByCode(String userId, List<BarChartUnit> trainingPeopleStatistics, Integer code) {
		List<String> roomIds = crRoleMapper.getRoomIdsByUserIdAndType(userId,
				BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
		if (roomIds.size() == 0 && code.equals(TeamRoom.TrainCategory.BLUE.getCode())) {
			trainingPeopleStatistics.add(new BarChartUnit("蓝队训练", 0));
			return;
		} else if (roomIds.size() == 0 && code.equals(TeamRoom.TrainCategory.RED.getCode())) {
			trainingPeopleStatistics.add(new BarChartUnit("红队训练", 0));
			return;
		}
		List<TeamRoom> teamRoomList = teamRoomMapper.selectBatchIds(roomIds);
		roomIds = teamRoomList.stream()
							  .filter(teamRoom -> teamRoom.getTrainCategory().equals(code))
							  .map(TeamRoom::getResourceId)
							  .collect(Collectors.toList());
		if (roomIds.size() == 0 && code.equals(TeamRoom.TrainCategory.BLUE.getCode())) {
			trainingPeopleStatistics.add(new BarChartUnit("蓝队训练", 0));
			return;
		} else if (roomIds.size() == 0 && code.equals(TeamRoom.TrainCategory.RED.getCode())){
			trainingPeopleStatistics.add(new BarChartUnit("红队训练", 0));
			return;
		}
		List<CrRole> crRoleList = crRoleMapper.getByRoomIds(roomIds);
		Integer count = 0;
		for (CrRole crRole : crRoleList) {
			count += crRole.getUserIdList().size();
		}
		if (code.equals(TeamRoom.TrainCategory.BLUE.getCode())) {
			trainingPeopleStatistics.add(new BarChartUnit("蓝队训练", count));
		} else {
			trainingPeopleStatistics.add(new BarChartUnit("红队训练", count));
		}
	}

	@Override
	public void getTrainingScore(String userId, List<BarChartUnit> trainingStatsScore) {
		List<CrTask> crTaskList = crTaskMapper.selectByUserIdAndBusinessType(userId, BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
		List<String> roomIds = StreamApiUtils.map(crTaskList, CrTask::getRoomId);
		if (roomIds.size() == 0) {
			trainingStatsScore.add(new BarChartUnit("红队训练", 0));
			trainingStatsScore.add(new BarChartUnit("蓝队训练", 0));
			return;
		}
		List<TeamRoom> teamRoomList = teamRoomMapper.selectBatchIds(roomIds);
		Map<String, Integer> teamRoomHashMap = StreamApiUtils.toMap(teamRoomList, TeamRoom::getResourceId, TeamRoom::getTrainingStatus);
		Integer redScore = 0;
		Integer blueScore = 0;
		for (CrTask crTask: crTaskList) {
			Integer score = crTask.getScore() == null ? 0 : crTask.getScore();
			if (teamRoomHashMap.get(crTask.getRoomId()) == null) {
				continue;
			}
			if (teamRoomHashMap.get(crTask.getRoomId()).equals(TeamRoom.TrainCategory.RED.getCode())) {
				redScore += score;
			} else {
				blueScore += score;
			}
		}
		trainingStatsScore.add(new BarChartUnit("红队训练", redScore));
		trainingStatsScore.add(new BarChartUnit("蓝队训练", blueScore));
	}

	@Override
	public List<TrainStatsOverviewRsp> getTrainingOverview(String userId) {
		List<RoomRoleContext> roomRoleContexts = crRoleMapper.getRoomIdsAndRoleGroupByUserIdAndType(
				userId, BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
		if (roomRoleContexts.isEmpty()) {
			return Lists.newArrayList();
		}
		List<String> roomIds = new ArrayList<>();
		Map<String, Integer> roleGroupMap = Maps.newHashMap();
		for (RoomRoleContext roomRoleContext : roomRoleContexts) {
			roomIds.add(roomRoleContext.getRoomId());
			roleGroupMap.put(roomRoleContext.getRoomId(), roomRoleContext.getRoleGroup());
		}
		List<TrainStatsOverviewRsp> related = Lists.newArrayList();
		// 查询全部
		List<TeamRoom> rooms = teamRoomMapper.selectBatchIds(roomIds);
		List<CrRole> crRoleList = crRoleMapper.getByRoomIds(roomIds);
		for (TeamRoom room : rooms) {
			TrainStatsOverviewRsp data = BeanUtils.copy(room, TrainStatsOverviewRsp.class);
			data.setRoomId(room.getResourceId());
			data.setBusinessType(room.getTrainCategory());
			if (room.getTrainCategory() == 1) {
				data.setBusinessTypeName(LocalScene.BusinessCode.RED_TRAINING.getDesc());
			} else {
				data.setBusinessTypeName(LocalScene.BusinessCode.BLUE_TRAINING.getDesc());
			}
			// 计算持续时长
			Integer durationTime = (int) ((room.getEndTime().getTime()
					- room.getStartTime().getTime()) / (60 * 60 * 1000));
			data.setDurationTime(durationTime);
			data.setStatus(room.getTrainingStatus());
			Integer score = 0;
			List<CrTask> crTaskList = crTaskMapper.selectByRoomIdAndUserId(room.getResourceId(), userId);
			for (CrTask crTask: crTaskList) {
				score += crTask.getScore() == null ? 0 : crTask.getScore();
			}
			data.setScore(score);

			Integer peopleCount = 0;
			List<CrRole> crRoles = StreamApiUtils.filter(crRoleList, crRole -> crRole.getRoomId().equals(room.getResourceId()));
			for (CrRole crRole : crRoles) {
				peopleCount += crRole.getUserIdList().size();
			}
			data.setUserCount(peopleCount);
			data.setRoleGroup(roleGroupMap.get(room.getResourceId()));
			related.add(data);
		}
		return related;
	}
}
