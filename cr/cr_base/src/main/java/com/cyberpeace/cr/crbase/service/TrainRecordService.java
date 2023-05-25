package com.cyberpeace.cr.crbase.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

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

	/**
	 * 记录训练时间
	 * @param name 训练实例名称
	 * @param businessTemplateId 业务模板id
	 * @param roomId 训练实例id
	 * @param userId 用户id
	 * @param businessType 业务类型
	 */
	void saveRecord(String name, String businessTemplateId, String roomId, String userId, Integer businessType);

	/**
	 * 保存记录
	 *
	 * @param name         名称
	 * @param btId         bt id
	 * @param roomId       房间id
	 * @param userId       用户id
	 * @param businessType 业务类型
	 * @param traineeIds   学员id列表
	 */
	void saveRecord(String name, String btId, String roomId, String userId, Integer businessType,
			List<String> traineeIds);

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
	 * @param businessTemplateId 业务模板id
	 * @param userId 用户id
	 * @param businessType 业务类型
	 * @param extra 附加信息
	 */
	void updateEndDate(String businessTemplateId, String userId, Integer businessType, String extra);

	/**
	 * 通过房间id更新结束时间
	 * @param roomId 房间id
	 * @param businessType 业务类型
	 */
	void updateEndDate(String roomId, Integer businessType);

	/**
	 * 通过房间id，更新结束时间，
	 * 传入结束时间，避免数据库时间和靶场时间不一致
	 * @param roomId 房间id
	 * @param endTime 结束时间
	 */
	void updateEndTimeByRoomId(String roomId, Date endTime);

	/**
	 * 用于删除单兵训练模板时，批量更新训练记录
	 * @param businessTemplateId
	 */
	void updateEndDate(String businessTemplateId);

	/**
	 * 依据房间id获取训练记录
	 * @param roomId 房间id
	 * @return 训练记录
	 */
	TrainRecord getByRoomId(String roomId);

	/**
	 * 通过业务模板id列表和用户id获取
	 *
	 * @param btIds  业务模板id列表
	 * @param userId 用户id
	 * @return {@code List<TrainRecord>}
	 */
	List<TrainRecord> getEndedByBtIdsAndUserId(List<String> btIds, String userId);

	/**
	 * 获取某用户所有训练完成的模板id set
	 *
	 * @param btIds  模板id列表
	 * @param userId 用户id
	 * @return {@code Set<String>}
	 */
	Set<String> getFinishedBtIds(List<String> btIds, String userId);
}
