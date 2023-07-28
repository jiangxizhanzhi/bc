package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.List;
import java.util.Map;

import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.service.CrEventStatistics;
import com.cyberpeace.cr.crbase.service.context.statistics.RoomRoleContext;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingData;
import com.cyberpeace.cr.redvsblue.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbRoomMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class RbTrainingStatistics extends CrEventStatistics {

	@Autowired
	CrRoleMapper crRoleMapper;

	@Autowired
	RbRoomMapper rbRoomMapper;

	@Override
	public BusinessCode getCode() {
		return BusinessCode.RED_VS_BLUE;
	}

	@Override
	public TrainingCount getTrainingCount(String userId) {
		List<String> roomIds = crRoleMapper.getRoomIdsByUserIdAndType(userId,
				BusinessType.RED_VS_BLUE.getCode());
		if (roomIds.isEmpty()) {
			return new TrainingCount();
		}
		return rbRoomMapper.getTrainingCount(roomIds, RbRoom.TrainingStatusCode.ENDED.getCode());
	}

	@Override
	public List<TrainingData> getRelatedTraining(String userId) {
		List<RoomRoleContext> roomRoleContexts = crRoleMapper.getRoomIdsAndRoleGroupByUserIdAndType(
				userId, BusinessType.RED_VS_BLUE.getCode());
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
			data.setAppId(BusinessCode.RED_VS_BLUE.getCode());
			data.setAppName(BusinessCode.RED_VS_BLUE.getDesc());
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
