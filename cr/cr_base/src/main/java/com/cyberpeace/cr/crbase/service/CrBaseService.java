package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.service.context.statistics.TrainingStatisticsPage;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingStatisticsRsp;

/**
 * 靶场基础Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public interface CrBaseService {

	/**
	 * 获取训练统计结果
	 *
	 * @param userId 用户id
	 * @param page   分页查询体
	 * @return 统计结果
	 * @throws
	 * @see String
	 * @see TrainingStatisticsPage
	 * @see TrainingStatisticsRsp
	 */
	TrainingStatisticsRsp getTrainingStatistics(String userId, TrainingStatisticsPage page);

}
