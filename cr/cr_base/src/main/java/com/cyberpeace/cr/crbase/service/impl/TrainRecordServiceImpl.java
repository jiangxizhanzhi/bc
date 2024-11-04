package com.cyberpeace.cr.crbase.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import com.cyberpeace.cr.crbase.dao.mapper.TrainRecordMapper;
import com.cyberpeace.cr.crbase.service.TrainRecordService;
import com.google.common.collect.Maps;

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
	public void updateEndDate(String businessTemplateId) {
		trainRecordMapper.batchEnd(businessTemplateId);
	}

	@Override
	public TrainRecord getByRoomId(String roomId) {
		return trainRecordMapper.getByRoomId(roomId);
	}

	@Override
	public Map<String, Boolean> getSubmittedMap(List<String> templateIds, String userId) {
		Map<String, Boolean> res = Maps.newHashMap();
		if (templateIds.isEmpty()) {
			return res;
		}
		LambdaQueryWrapper<TrainRecord> wrapper = new LambdaQueryWrapper<>();
		wrapper.eq(TrainRecord::getUserId, userId);
		wrapper.in(TrainRecord::getBusinessTemplateId, templateIds);
		List<TrainRecord> recordList = trainRecordMapper.selectList(wrapper);
		for (TrainRecord trainRecord : recordList) {
			res.putIfAbsent(trainRecord.getBusinessTemplateId(), false);
			if (StringUtils.isNotBlank(trainRecord.getExtra())) {
				res.put(trainRecord.getBusinessTemplateId(), true);
			}
		}
		return res;
	}

}
