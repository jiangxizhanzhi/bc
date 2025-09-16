package com.cyberpeace.cr.crbase.service;

import java.util.Date;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import com.cyberpeace.cr.crbase.service.context.training.TrainingTimeCost;

/**
 * 训练使用记录
 *
 * @author SN
 * @date 2024/11/13
 */
public interface TrainRecordService extends IService<TrainRecord> {

	/**
	 * 记录训练时间
	 *
	 * @param name               名称
	 * @param businessTemplateId 业务模板id
	 * @param userId             用户id
	 * @param businessType       业务类型
	 */
	void saveRecord(String name, String businessTemplateId, String userId, Integer businessType);

	/**
	 * 记录训练时间
	 *
	 * @param name               训练实例名称
	 * @param businessTemplateId 业务模板id
	 * @param roomId             训练实例id
	 * @param userId             用户id
	 * @param businessType       业务类型
	 */
	void saveRecord(String name, String businessTemplateId, String roomId, String userId, Integer businessType);

	/**
	 * 更新拓展信息
	 *
	 * @param businessTemplateId 业务模板id
	 * @param userId             用户id
	 * @param businessType       业务类型
	 * @param extra              额外
	 */
	void updateExtraDate(String businessTemplateId, String userId, Integer businessType, String extra);

	/**
	 * 单兵训练用于更新trainRecord endTime
	 *
	 * @param roomId
	 * @param extra  额外
	 */
	void updateEndTimeAndExtra(String roomId, String extra);

	/**
	 * 通过房间id，更新结束时间，
	 * 传入结束时间，避免数据库时间和靶场时间不一致
	 *
	 * @param roomId  房间id
	 * @param endTime 结束时间
	 */
	void updateEndTimeByRoomId(String roomId, Date endTime);

	/**
	 * 依据房间id获取训练记录
	 *
	 * @param roomId 房间id
	 * @return 训练记录
	 */
	TrainRecord getByRoomId(String roomId);

	/**
	 * 获取训练时长
	 *
	 * @param roomId 房间id
	 * @return {@code String}
	 */
	String getTimeCost(String roomId);

	int getTimeCostHours(String roomId);

	/**
	 * 获取训练时长，long型
	 *
	 * @param roomId 房间id
	 * @return {@code Long}
	 */
	TrainingTimeCost getTimeCostLong(String roomId);

	/**
	 * 获取结肠时间成本
	 *
	 * @param roomId 房间id
	 * @return {@code String }
	 */
	String getColonTimeCost(String roomId);

	/**
	 * 获取实际结束时间
	 *
	 * @param roomId 房间id
	 * @return {@code Date}
	 */
	Date getActualEndTime(String roomId);
}
