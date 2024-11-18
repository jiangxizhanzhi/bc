package com.cyberpeace.cr.crbase.service;

import java.util.Date;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;

/**
 * 训练使用记录
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see IService
 * @since 1.0.0
 */
public interface TrainRecordService extends IService<TrainRecord> {

	/**
	 * 记录训练时间
	 *
	 * @param name               名称
	 * @param businessTemplateId 业务模板id
	 * @param userId             用户id
	 * @param businessType       业务类型
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see Integer
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
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see String
	 * @see Integer
	 */
	void saveRecord(String name, String businessTemplateId, String roomId, String userId, Integer businessType);

	/**
	 * 更新拓展信息
	 *
	 * @param businessTemplateId 业务模板id
	 * @param userId             用户id
	 * @param businessType       业务类型
	 * @param extra              额外
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 * @see String
	 */
	void updateExtraDate(String businessTemplateId, String userId, Integer businessType, String extra);

	/**
	 * 更新结束时间
	 *
	 * @param businessTemplateId 业务模板id
	 * @param userId             用户id
	 * @param businessType       业务类型
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 */
	void updateEndDate(String businessTemplateId, String userId, Integer businessType);

	/**
	 * 重载
	 * {@link TrainRecordService#updateEndDate(String, String, Integer)}
	 * , 额外记录训练运行中生成的数据
	 *
	 * @param businessTemplateId 业务模板id
	 * @param userId             用户id
	 * @param businessType       业务类型
	 * @param extra              附加信息
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 * @see String
	 */
	void updateEndDate(String businessTemplateId, String userId, Integer businessType, String extra);

	/**
	 * 通过房间id更新结束时间
	 *
	 * @param roomId       房间id
	 * @param businessType 业务类型
	 * @throws
	 * @see String
	 * @see Integer
	 */
	void updateEndDate(String roomId, Integer businessType);

	/**
	 * 通过房间id，更新结束时间，
	 * <p>
	 * <p>
	 * 传入结束时间，避免数据库时间和靶场时间不一致
	 *
	 * @param roomId  房间id
	 * @param endTime 结束时间
	 * @throws
	 * @see String
	 * @see Date
	 */
	void updateEndTimeByRoomId(String roomId, Date endTime);

	/**
	 * 用于删除单兵训练模板时，批量更新训练记录
	 *
	 * @param businessTemplateId 业务模板id
	 * @throws
	 * @see String
	 */
	void updateEndDate(String businessTemplateId);

	/**
	 * 依据房间id获取训练记录
	 *
	 * @param roomId 房间id
	 * @return 训练记录
	 * @throws
	 * @see String
	 * @see TrainRecord
	 */
	TrainRecord getByRoomId(String roomId);

	/**
	 * 获取耗时
	 *
	 * @param roomId 房间id
	 * @return {@code String}
	 * @throws
	 * @see String
	 * @see String
	 */
	String getTimeCost(String roomId);

	/**
	 * 获取时间成本
	 *
	 * @param roomId 房间id
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 */
	String getColonTimeCost(String roomId);

	/**
	 * 获取实际结束时间
	 *
	 * @param roomId 房间id
	 * @return {@code Date}
	 * @throws
	 * @see String
	 * @see Date
	 */
	Date getActualEndTime(String roomId);
}
