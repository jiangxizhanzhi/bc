package com.cyberpeace.cr.redvsblue.service.impl;

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
import com.cyberpeace.cr.crbase.service.context.statistics.RoomRoleContext;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingData;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingDateCount;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RbTrainingStatistics extends CrEventStatistics {

	@Autowired
	CrRoleMapper crRoleMapper;

	@Autowired
	RbRoomMapper rbRoomMapper;

	@Autowired
	CrTaskMapper crTaskMapper;

	@Override
	public LocalScene.BusinessCode getCode() {
		return LocalScene.BusinessCode.RED_VS_BLUE;
	}

	@Override
	public List<TrainingDateCount> getTrainingCountByDate(String userId) {
		List<String> roomIds = crRoleMapper.getRoomIdsByUserIdAndType(userId,
				BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
		if (roomIds.isEmpty()) {
			return new ArrayList<>();
		}
		return rbRoomMapper.getTrainingCountByDate(roomIds ,RbRoom.TrainingStatusCode.ENDED.getCode());
	}

	@Override
	public List<TrainingDateCount> getAllTrainingCountByDate() {
		return rbRoomMapper.getTrainingCountByDate(new ArrayList<>(),RbRoom.TrainingStatusCode.ENDED.getCode());
	}


	@Override
	public TrainingCount getTrainingCount(String userId) {
		List<String> roomIds = crRoleMapper.getRoomIdsByUserIdAndType(userId,
				BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
		if (roomIds.isEmpty()) {
			return new TrainingCount();
		}
		return rbRoomMapper.getTrainingCount(roomIds, RbRoom.TrainingStatusCode.ENDED.getCode());
	}

	@Override
	public List<TrainingData> getRelatedTraining(String userId) {
		List<RoomRoleContext> roomRoleContexts = crRoleMapper.getRoomIdsAndRoleGroupByUserIdAndType(
				userId, BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
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
		// 获取未结束的
		List<RbRoom> rooms = rbRoomMapper.selectBatchIds(roomIds);
		for (RbRoom room : rooms) {

			TrainingData data = BeanUtils.copy(room, TrainingData.class);
			data.setAppId(LocalScene.BusinessCode.RED_VS_BLUE.getCode());
			data.setAppName(LocalScene.BusinessCode.RED_VS_BLUE.getDesc());
			// 计算持续时长
			Integer durationTime = (int) ((room.getEndTime().getTime()
					- room.getStartTime().getTime()) / (60 * 60 * 1000));
			data.setDurationTime(durationTime);
			data.setState(getState(room.getTrainingStatus()));
			data.setRoleGroup(roleGroupMap.get(room.getResourceId()));

			related.add(data);
		}
		return related;
	}

	@Override
	public List<CrRoleNode> getCrRoleNodeList(String userId) {
		List<CrRole> crRoleList = crRoleMapper.getByUserIdAndCode(userId, BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
		List<CrRoleNode> crRoleNodeList = new ArrayList<>();
		crRoleList.forEach(crRole -> {
			if (crRole.getRoleGroup().equals(CrRole.RbRoleGroup.RED.getCode())
					|| crRole.getRoleGroup().equals(CrRole.RbRoleGroup.BLUE.getCode())) {
				crRoleNodeList.addAll(crRole.getNodeData().getAssetNodes());
			}
		});
		return crRoleNodeList;
	}

	@Override
	public void getPeopleCount(String userId, List<BarChartUnit> trainingPeopleStatistics) {
		List<String> roomIds = crRoleMapper.getRoomIdsByUserIdAndType(userId,
				BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
		if (roomIds.size() == 0) {
			trainingPeopleStatistics.add(new BarChartUnit("红蓝对抗", 0));
			return;
		}
		List<CrRole> crRoleList = crRoleMapper.getByRoomIds(roomIds);
		Integer count = 0;
		for (CrRole crRole : crRoleList) {
			count += crRole.getUserIdList().size();
		}
		trainingPeopleStatistics.add(new BarChartUnit("红蓝对抗", count));
	}

	@Override
	public void getTrainingScore(String userId, List<BarChartUnit> trainingStatsScore) {
		List<CrTask> crTaskList = crTaskMapper.selectByUserIdAndBusinessType(userId, BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
		Integer sum = 0;
		List<String> roomIds = StreamApiUtils.map(crTaskList, CrTask::getRoomId);
		if (roomIds.size() == 0) {
			trainingStatsScore.add(new BarChartUnit("红蓝对抗", sum));
			return;
		}
		List<RbRoom> rbRoomList = rbRoomMapper.selectBatchIds(roomIds);
		Map<String, RbRoom> rbRoomMap = StreamApiUtils.toMap(rbRoomList, RbRoom::getResourceId, rbRoom -> rbRoom);
		for (CrTask crTask: crTaskList) {
			if (rbRoomMap.get(crTask.getRoomId()) == null) {
				continue;
			}
			Integer score = crTask.getScore() == null ? 0 : crTask.getScore();
			sum += score;
		}
		trainingStatsScore.add(new BarChartUnit("红蓝对抗", sum));
	}

	@Override
	public List<TrainStatsOverviewRsp> getTrainingOverview(String userId) {
		List<RoomRoleContext> roomRoleContexts = crRoleMapper.getRoomIdsAndRoleGroupByUserIdAndType(
				userId, BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
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
		// 获取未结束的
		List<RbRoom> rooms = rbRoomMapper.selectBatchIds(roomIds);
		List<CrRole> crRoleList = crRoleMapper.getByRoomIds(roomIds);
		for (RbRoom room : rooms) {

			TrainStatsOverviewRsp data = BeanUtils.copy(room, TrainStatsOverviewRsp.class);
			data.setRoomId(room.getResourceId());
			data.setBusinessType(LocalScene.BusinessCode.RED_VS_BLUE.getCode());
			data.setBusinessTypeName(LocalScene.BusinessCode.RED_VS_BLUE.getDesc());
			// 计算持续时长
			Integer durationTime = (int) ((room.getEndTime().getTime()
					- room.getStartTime().getTime()) / (60 * 60 * 1000));
			data.setDurationTime(durationTime);
			data.setMinute((int)((room.getEndTime().getTime()
					- room.getStartTime().getTime()) / (60 * 1000)) % 60);
			data.setStatus(getState(room.getTrainingStatus()));
			List<CrRole> crRoles = StreamApiUtils.filter(crRoleList, crRole -> crRole.getRoomId().equals(room.getResourceId()));
			Integer peopleCount = 0;
			for (CrRole crRole : crRoles) {
				peopleCount += crRole.getUserIdList().size();
			}
			data.setUserCount(peopleCount);

			Integer score = 0;
			List<CrTask> crTaskList = crTaskMapper.selectByRoomIdAndUserId(room.getResourceId(), userId);
			for (CrTask crTask: crTaskList) {
				score += crTask.getScore() == null ? 0 : crTask.getScore();
			}
			data.setScore(score);
			data.setRoleGroup(roleGroupMap.get(room.getResourceId()));
			related.add(data);
		}
		return related;
	}

	private Integer getState(Integer trainingStatus) {
		switch (trainingStatus) {
			case 4:
				return 0;
			case 2:
				return 1;
			case 0:
				return 2;
			case 1:
				return 1;
			case 3:
				return 1;
			default:
				return trainingStatus;
		}
	}
}
