package com.cyberpeace.cr.crbase.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import com.cyberpeace.cr.crbase.dao.mapper.TrainRecordMapper;
import com.cyberpeace.cr.crbase.service.TrainRecordService;
import com.google.common.collect.Lists;

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
	public void saveRecord(String name, String btId, String roomId, String userId,
			Integer businessType, List<String> traineeIds) {
		TrainRecord trainRecord = new TrainRecord();
		trainRecord.setBusinessTemplateId(btId);
		trainRecord.setRoomId(roomId);
		trainRecord.setRoomName(name);
		trainRecord.setUserId(userId);
		trainRecord.setBusinessType(businessType);
		trainRecord.setStartTime(new Date());
		if (businessType.equals(BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode())) {
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
