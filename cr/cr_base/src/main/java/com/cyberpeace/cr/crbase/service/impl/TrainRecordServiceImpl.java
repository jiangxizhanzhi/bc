package com.cyberpeace.cr.crbase.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.cyberpeace.common.enums.BusinessType;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import com.cyberpeace.cr.crbase.dao.mapper.TrainRecordMapper;
import com.cyberpeace.cr.crbase.service.TrainRecordService;
import com.google.common.collect.Lists;
import com.cyberpeace.cr.crbase.service.context.training.TrainingTimeCost;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrainRecordServiceImpl extends ServiceImpl<TrainRecordMapper, TrainRecord> implements TrainRecordService {

	@Autowired
	private TrainRecordMapper trainRecordMapper;

	@Override
	public void saveRecord(String name, String businessTemplateId, String userId,
			Integer businessType) {
		saveRecord(name, businessTemplateId, null, userId, businessType);
	}

	@Override
	public void saveRecord(String name, String businessTemplateId, String roomId, String userId,
			Integer businessType) {

		TrainRecord trainRecord = new TrainRecord();
		trainRecord.setBusinessTemplateId(businessTemplateId);
		trainRecord.setRoomId(roomId);
		trainRecord.setRoomName(name);
		trainRecord.setUserId(userId);
		trainRecord.setBusinessType(businessType);
		trainRecord.setStartTime(new Date());

		save(trainRecord);
	}

	@Override
	public void saveRecord(String name, String btId, String roomId, String userId,
			Integer businessType, List<String> traineeIds) {
		TrainRecord trainRecord = new TrainRecord();
		trainRecord.setBusinessTemplateId(btId);
		trainRecord.setRoomId(roomId);
		trainRecord.setRoomName(name);
		trainRecord.setUserId(userId);
		trainRecord.setBusinessType(businessType);
		trainRecord.setStartTime(new Date());
		if (businessType.equals(BusinessType.TRAINING_SINGLE.getCode())) {
			traineeIds.add(userId);
		}
		trainRecord.setTrainee(JsonUtil.arrayToString(traineeIds));

		save(trainRecord);
	}

	@Override
	public void updateExtraDate(String businessTemplateId, String userId, Integer businessType,
			String extra) {
		trainRecordMapper.setExtra(businessTemplateId, userId, businessType, extra);
	}

	@Override
	public void updateEndTimeAndExtra(String roomId, String extra) {
		trainRecordMapper.setEndTimeAndExtra(roomId, new Date(), extra);
	}

	@Override
	public void updateEndTimeByRoomId(String roomId, Date endTime) {
		trainRecordMapper.updateEndTimeByRoomId(roomId, endTime);
	}

	@Override
	public TrainRecord getByRoomId(String roomId) {
		return trainRecordMapper.getByRoomId(roomId);
	}

	@Override
	public String getTimeCost(String roomId) {
		return TimeUtil.duration2String(getTimeCostLong(roomId).getTimeCost());
	}

    @Override
	public TrainingTimeCost getTimeCostLong(String roomId) {
		TrainingTimeCost timeCost = new TrainingTimeCost();
		TrainRecord trainRecord = trainRecordMapper.getByRoomId(roomId);
		if(null == trainRecord){
			return timeCost;
		}
		if (trainRecord.getEndTime() != null) {
			timeCost.setRunning(false);
			timeCost.setTimeCost(TimeUtil.getIntervalSeconds(trainRecord.getStartTime(),
					trainRecord.getEndTime()));
		} else {
			timeCost.setRunning(true);
			timeCost.setTimeCost(
					TimeUtil.getIntervalSeconds(trainRecord.getStartTime(), new Date()));
		}
		return timeCost;
    }

    @Override
	public String getColonTimeCost(String roomId) {
		TrainRecord trainRecord = trainRecordMapper.getByRoomId(roomId);
		if(null == trainRecord){
			return TimeUtil.duration2String(0L);
		}
		if (trainRecord.getEndTime() != null) {
			return TimeUtil.getColonTimeInterval(trainRecord.getStartTime(), trainRecord.getEndTime());
		} else {
			return TimeUtil.getColonTimeInterval(trainRecord.getStartTime(), new Date());
		}
	}

    @Override
    public Date getActualEndTime(String roomId) {
		TrainRecord trainRecord = this.getByRoomId(roomId);
		if(null != trainRecord && null != trainRecord.getEndTime()){
			return trainRecord.getEndTime();
		} else {
			log.error("training not existing or not ended: [{}]", roomId);
			return null;
		}
    }

	@Override
	public List<TrainRecord> getEndedByBtIdsAndUserId(List<String> btIds, String userId) {
		if (CollectionUtils.isEmpty(btIds)) {
			return Lists.newArrayList();
		}
		return trainRecordMapper.selectEndedByBtIdsAndUserId(btIds, userId);
	}

	@Override
	public Set<String> getFinishedBtIds(List<String> btIds, String userId) {

		// 该用户所有已结束的训练
		List<TrainRecord> recordList = getEndedByBtIdsAndUserId(btIds, userId);
		return recordList.stream().map(TrainRecord::getBusinessTemplateId)
				.collect(Collectors.toSet());
	}

}
