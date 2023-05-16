package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.service.context.statistics.TrainingRecordRsp;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingStatisticsPage;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingStatisticsRsp;

public interface CrBaseService {

	/**
	 * 获取训练统计结果
	 * @param userId 用户id
	 * @param page 分页查询体
	 * @return 统计结果
	 */
	TrainingStatisticsRsp getTrainingStatistics(String userId, TrainingStatisticsPage page);

    TrainingRecordRsp getTrainingRecords(String userId);
}
