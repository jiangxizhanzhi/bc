package com.cyberpeace.cr.crbase.service.impl;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import com.cyberpeace.cr.crbase.dao.mapper.TrainRecordMapper;
import com.cyberpeace.cr.crbase.service.TrainRecordService;

/**
 * 训练记录Service实现
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
@Slf4j
public class TrainRecordServiceImpl extends ServiceImpl<TrainRecordMapper, TrainRecord> implements TrainRecordService {

	/**
	 * 训练记录Mapper
	 */
	@Autowired
	private TrainRecordMapper trainRecordMapper;

	/**
	 * 保存记录
	 *
	 * @param name               名称
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
	 * @param name               名称
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
	 * 更新额外日期
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
	 * 更新结束时间由房间id
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
	 * 获取由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code TrainRecord }
	 */
	@Override
	public TrainRecord getByRoomId(String roomId) {
		return trainRecordMapper.getByRoomId(roomId);
	}

	/**
	 * 获取时间成本
	 *
	 * @param roomId 房间id
	 * @return {@code String }
	 */
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

	/**
	 * 获取结肠时间成本
	 *
	 * @param roomId 房间id
	 * @return {@code String }
	 */
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

	/**
	 * 获取实体结束时间
	 *
	 * @param roomId 房间id
	 * @return {@code Date }
	 */
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

}
