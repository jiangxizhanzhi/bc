package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.service.context.statistics.*;

public interface CrBaseService {

	/**
	 * 获取训练统计结果
	 * @param userId 用户id
	 * @param page 分页查询体
	 * @return 统计结果
	 */
	TrainingStatisticsRsp getTrainingStatistics(String userId, TrainingStatisticsPage page);

	TrainingStatisticsAccount getTrainingStatisticsAccount(String userId);

	TrainingAssetAccount getTrainingAssetAccount(String userId);

	TrainingPeopleStatistics getTrainingPeopleStatistics(String userId);

	TrainingStatsScore getTrainingScore(String userId);
}
