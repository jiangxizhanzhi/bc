package com.cyberpeace.cr.crbase.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.controller.rsp.TrainStatsOverviewRsp;
import com.cyberpeace.cr.crbase.service.context.BarChartUnit;
import com.cyberpeace.cr.crbase.service.context.statistics.*;

import java.util.HashMap;
import java.util.List;

public interface CrBaseService {

	/**
	 * 获取训练统计结果
	 * @param userId 用户id
	 * @param page 分页查询体
	 * @return 统计结果
	 */
	TrainingStatisticsRsp getTrainingStatistics(String userId, TrainingStatisticsPage page);

	TrainingStatisticsAccount getTrainingStatisticsAccount(String userId);

	List<BarChartUnit> getTrainingAssetAccount(String userId);

	List<BarChartUnit> getTrainingPeopleStatistics(String userId);

	List<BarChartUnit> getTrainingScore(String userId);

	ListRsp<TrainStatsOverviewRsp> getTrainingOverview(String userId, Page page);

	HashMap<String, Integer> getScoreByAttackType(Integer businessType, String roomId);

    TrainingRecordRsp getTrainingRecords(String userId);
}
