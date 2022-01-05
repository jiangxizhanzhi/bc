package com.cyberpeace.cr.crbase.service;

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
	 * 更新结束时间
	 */
	void updateEndDate(String businessTemplateId, String userId, Integer businessType);

	/**
	 * 用于删除单兵训练模板时，批量更新训练记录
	 * @param businessTemplateId
	 */
	void updateEndDate(String businessTemplateId);
}
