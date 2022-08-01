package com.cyberpeace.cr.crbase.service;

import java.util.Date;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;

/**
 * 训练使用记录
 */
public interface TrainRecordService extends IService<TrainRecord> {

	/**
	 * 记录训练时间
	 */
	void saveRecord(String name, String businessTemplateId, String userId, Integer businessType);

	void saveRecord(String name, String businessTemplateId, String roomId, String userId, Integer businessType);

	/**
	 * 更新拓展信息
	 */
	void updateExtraDate(String businessTemplateId, String userId, Integer businessType, String extra);

	/**
	 * 更新结束时间
	 */
	void updateEndDate(String businessTemplateId, String userId, Integer businessType);

	/**
	 * 重载 {@link TrainRecordService#updateEndDate(String, String, Integer)}, 额外记录训练运行中生成的数据
	 */
	void updateEndDate(String businessTemplateId, String userId, Integer businessType, String extra);

	/**
	 * 通过房间id更新结束时间
	 */
	void updateEndDate(String roomId, Integer businessType);

	/**
	 * 通过房间id，更新结束时间，
	 * 传入结束时间，避免数据库时间和靶场时间不一致
	 * @param roomId
	 * @param endTime
	 */
	void updateEndTimeByRoomId(String roomId, Date endTime);

	/**
	 * 用于删除单兵训练模板时，批量更新训练记录
	 * @param businessTemplateId
	 */
	void updateEndDate(String businessTemplateId);

	TrainRecord getByRoomId(String roomId);
}
