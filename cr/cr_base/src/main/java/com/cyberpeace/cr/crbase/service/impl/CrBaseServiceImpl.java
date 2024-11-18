package com.cyberpeace.cr.crbase.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.cyberpeace.common.mdparser.convert.TrainTypeConvert;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.ListUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.service.CrBaseService;
import com.cyberpeace.cr.crbase.service.CrEventStatistics;
import com.cyberpeace.cr.crbase.service.context.statistics.*;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 靶场基础Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see CrBaseService
 * @since 1.0.0
 */
@Service
@Slf4j
public class CrBaseServiceImpl implements CrBaseService {

	/**
	 * 注册
	 *
	 * @see Map<BusinessCode, CrEventStatistics>
	 */
	public static Map<LocalScene.BusinessCode, CrEventStatistics> register = Maps.newHashMap();

	/**
	 * 获取训练统计学
	 *
	 * @param userId 用户id
	 * @param page   分页
	 * @return {@code TrainingStatisticsRsp }
	 * @throws
	 * @see String
	 * @see TrainingStatisticsPage
	 * @see TrainingStatisticsRsp
	 */
	@Override
	public TrainingStatisticsRsp getTrainingStatistics(String userId, TrainingStatisticsPage page) {
		// 1. define variables
		List<TrainingCountRsp> completeList = Lists.newArrayList();
		List<TrainingCountRsp> incompleteList = Lists.newArrayList();
		List<TrainingData> trainingDataList = Lists.newArrayList();
		// 2. get training count
		for (Map.Entry<LocalScene.BusinessCode, CrEventStatistics> entry : register.entrySet()) {
			LocalScene.BusinessCode businessCode = entry.getKey();
			CrEventStatistics statistics = entry.getValue();

			TrainingCount trainingCount = statistics.getTrainingCount(userId);
			completeList.add(new TrainingCountRsp(businessCode.getCode(), businessCode.getDesc(),
					trainingCount.getCompleted()));
			incompleteList.add(new TrainingCountRsp(businessCode.getCode(), businessCode.getDesc(),
					trainingCount.getUncompleted()));
		}

		// 3. get related training list
		for (CrEventStatistics statistics : register.values()) {
			trainingDataList.addAll(statistics.getRelatedTraining(userId));
		}

		// 4. subType statistics。

		Map<Integer, List<TrainingData>> trainingDataMap = StreamApiUtils.groupBy(trainingDataList,TrainingData::getSubType);
		List<Integer> trainTypes = EnumUtils.getListCode(TrainTypeConvert.class);
		Map<Integer, Integer> trainingMap = Maps.newHashMap();

		for (Integer trainType : trainTypes) {
			Integer num = trainingDataMap.getOrDefault(trainType,Lists.newArrayList()).size();
			trainingMap.put(trainType,num);
		}
		//去掉已结束的单边训练类型统计
		trainingDataList = StreamApiUtils.filter(trainingDataList,
				s ->   !s.getAppId().equals(LocalScene.BusinessCode.SINGLE_TRAINING.getCode())  || !s.getState().equals(2) );

		// 5. sort
		trainingDataList.sort(new TrainingComparator(page.getSort()));

		// 6.paginate
		IPage<TrainingData> trainingDataPage = new Page<>(page.getPage(), page.getPerPage(),
				trainingDataList.size());

		trainingDataPage.setRecords(ListUtil.pageList(trainingDataList, page.getPage(),
				page.getPerPage(), (int) trainingDataPage.getTotal()));

		ListRsp<TrainingData> trainingDataListRsp = PageUtil.getListResult(trainingDataPage);




		// 7. make rsp
		TrainingStatisticsRsp rsp = new TrainingStatisticsRsp();
		rsp.setCompleted(completeList);
		rsp.setIncomplete(incompleteList);
		rsp.setRelation(trainingDataListRsp);
		rsp.setTrainingTypeMap(trainingMap);


		return rsp;
	}

}

/**
 * 训练比较仪
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Comparator
 * @since 1.0.0
 */
class TrainingComparator implements Comparator<TrainingData> {

	/**
	 * ASC
	 *
	 * @see Boolean
	 */
	private final Boolean asc;

	/**
	 * 训练比较仪
	 *
	 * @param asc ASC
	 * @throws
	 * @see Boolean
	 */
	public TrainingComparator(Boolean asc) {
		this.asc = asc;
	}

	/**
	 * 比较
	 *
	 * @param o1 o1
	 * @param o2 O2
	 * @return int
	 * @throws
	 * @see TrainingData
	 * @see TrainingData
	 * @see int
	 */
	@Override
	public int compare(TrainingData o1, TrainingData o2) {
		if (asc) {
			return Long.compare(o1.getStartTime()
								  .getTime(), o2.getStartTime()
												.getTime());
		} else {
			return Long.compare(o2.getStartTime()
								  .getTime(), o1.getStartTime()
												.getTime());
		}
	}
}
