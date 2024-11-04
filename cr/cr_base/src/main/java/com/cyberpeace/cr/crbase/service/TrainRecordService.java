package com.cyberpeace.cr.crbase.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;

import java.util.List;
import java.util.Map;

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
	 * 用于删除单兵训练模板时，批量更新训练记录
	 * @param businessTemplateId
	 */
	void updateEndDate(String businessTemplateId);

	TrainRecord getByRoomId(String roomId);

	Map<String, Boolean> getSubmittedMap(List<String> templateIds, String userId);
}
