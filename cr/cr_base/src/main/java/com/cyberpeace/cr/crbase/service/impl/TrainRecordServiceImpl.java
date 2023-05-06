package com.cyberpeace.cr.crbase.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import com.cyberpeace.cr.crbase.dao.mapper.TrainRecordMapper;
import com.cyberpeace.cr.crbase.service.TrainRecordService;

/**
 * 训练记录服务impl
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Service
public class TrainRecordServiceImpl extends ServiceImpl<TrainRecordMapper, TrainRecord> implements TrainRecordService {

	@Autowired
	private TrainRecordMapper trainRecordMapper;

	/**
	 * 保存记录
	 *
	 * @param name               名字
	 * @param businessTemplateId 业务模板id
	 * @param userId             用户id
	 * @param businessType       业务类型
	 */
	@Override
	public void saveRecord(String name, String businessTemplateId, String userId,
			Integer businessType) {
		saveRecord(name, businessTemplateId, null, userId, businessType);
	}

	/**
	 * 保存记录
	 *
	 * @param name               名字
	 * @param businessTemplateId 业务模板id
	 * @param roomId             房间id
	 * @param userId             用户id
	 * @param businessType       业务类型
	 */
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

	/**
	 * 更新日期
	 *
	 * @param businessTemplateId 业务模板id
	 * @param userId             用户id
	 * @param businessType       业务类型
	 * @param extra              额外
	 */
	@Override
	public void updateExtraDate(String businessTemplateId, String userId, Integer businessType,
			String extra) {
		trainRecordMapper.setExtra(businessTemplateId, userId, businessType, extra);
	}

	/**
	 * 更新结束日期
	 *
	 * @param businessTemplateId 业务模板id
	 * @param userId             用户id
	 * @param businessType       业务类型
	 */
	@Override
	public void updateEndDate(String businessTemplateId, String userId, Integer businessType) {
		trainRecordMapper.setEndTime(businessTemplateId, userId, businessType, null);
	}

	/**
	 * 更新结束日期
	 *
	 * @param businessTemplateId 业务模板id
	 * @param userId             用户id
	 * @param businessType       业务类型
	 * @param extra              额外
	 */
	@Override
	public void updateEndDate(String businessTemplateId, String userId, Integer businessType,
			String extra) {
		trainRecordMapper.setEndTime(businessTemplateId, userId, businessType, extra);
	}

	/**
	 * 更新结束日期
	 *
	 * @param roomId       房间id
	 * @param businessType 业务类型
	 */
	@Override
	public void updateEndDate(String roomId, Integer businessType) {
		trainRecordMapper.setEndTimeByRoomId(roomId, businessType, null);
	}

	/**
	 * 由房间id更新结束时间
	 *
	 * @param roomId  房间id
	 * @param endTime 结束时间
	 */
	@Override
	public void updateEndTimeByRoomId(String roomId, Date endTime) {
		trainRecordMapper.updateEndTimeByRoomId(roomId, endTime);
	}

	/**
	 * 更新结束日期
	 *
	 * @param businessTemplateId 业务模板id
	 */
	@Override
	public void updateEndDate(String businessTemplateId) {
		trainRecordMapper.batchEnd(businessTemplateId);
	}

	/**
	 * 获取通过房间id
	 *
	 * @param roomId 房间id
	 * @return {@link TrainRecord}
	 */
	@Override
	public TrainRecord getByRoomId(String roomId) {
		return trainRecordMapper.getByRoomId(roomId);
	}

}
