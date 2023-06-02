package com.cyberpeace.cr.crbase.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import com.cyberpeace.cr.crbase.dao.mapper.TrainRecordMapper;
import com.cyberpeace.cr.crbase.service.TrainRecordService;

@Service
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
	public void updateExtraDate(String businessTemplateId, String userId, Integer businessType,
			String extra) {
		trainRecordMapper.setExtra(businessTemplateId, userId, businessType, extra);
	}

	@Override
	public void updateEndDate(String businessTemplateId, String userId, Integer businessType) {
		trainRecordMapper.setEndTime(businessTemplateId, userId, businessType, null);
	}

	@Override
	public void updateEndDate(String businessTemplateId, String userId, Integer businessType,
			String extra) {
		trainRecordMapper.setEndTime(businessTemplateId, userId, businessType, extra);
	}

	@Override
	public void updateEndDate(String roomId, Integer businessType) {
		trainRecordMapper.setEndTimeByRoomId(roomId, businessType, null);
	}

	@Override
	public void updateEndTimeByRoomId(String roomId, Date endTime) {
		trainRecordMapper.updateEndTimeByRoomId(roomId, endTime);
	}

	@Override
	public void updateEndDate(String businessTemplateId) {
		trainRecordMapper.batchEnd(businessTemplateId);
	}

	@Override
	public TrainRecord getByRoomId(String roomId) {
		return trainRecordMapper.getByRoomId(roomId);
	}

	@Override
	public String getTimeCost(String roomId) {
		TrainRecord trainRecord = trainRecordMapper.getByRoomId(roomId);
		if(null == trainRecord){
			return TimeUtil.duration2String(0L);
		}
		if (trainRecord.getEndTime() != null) {
			return TimeUtil.getTimeInterval(trainRecord.getStartTime(), trainRecord.getEndTime());
		} else {
			return TimeUtil.getTimeInterval(trainRecord.getStartTime(), new Date());
		}
	}

}
