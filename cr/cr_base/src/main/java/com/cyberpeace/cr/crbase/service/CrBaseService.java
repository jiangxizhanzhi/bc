package com.cyberpeace.cr.crbase.service;

import com.cyberpeace.cr.crbase.service.context.statistics.TrainingStatisticsPage;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingStatisticsRsp;

public interface CrBaseService {

	/**
	 * @param userId
	 * @param page
	 * @return
	 */
	TrainingStatisticsRsp getTrainingStatistics(String userId, TrainingStatisticsPage page);

}
