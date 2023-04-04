package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.service.context.statistics.TrainingAssetAccount;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingStatisticsAccount;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingStatisticsPage;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingStatisticsRsp;

import java.util.HashMap;

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
}
