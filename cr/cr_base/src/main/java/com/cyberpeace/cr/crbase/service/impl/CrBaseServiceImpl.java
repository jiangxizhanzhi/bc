package com.cyberpeace.cr.crbase.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
 * impl cr基础服务
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Service
@Slf4j
public class CrBaseServiceImpl implements CrBaseService {

	public static Map<LocalScene.BusinessCode, CrEventStatistics> register = Maps.newHashMap();

	/**
	 * 获取训练统计数据
	 *
	 * @param userId 用户id
	 * @param page   页面
	 * @return {@link TrainingStatisticsRsp}
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

		// 4. sort
		trainingDataList.sort(new TrainingComparator(page.getSort()));

		// 5.paginate
		IPage<TrainingData> trainingDataPage = new Page<>(page.getPage(), page.getPerPage(),
				trainingDataList.size());

		trainingDataPage.setRecords(ListUtil.pageList(trainingDataList, page.getPage(),
				page.getPerPage(), (int) trainingDataPage.getTotal()));

		ListRsp<TrainingData> trainingDataListRsp = PageUtil.getListResult(trainingDataPage);
		// 6. make rsp
		TrainingStatisticsRsp rsp = new TrainingStatisticsRsp();
		rsp.setCompleted(completeList);
		rsp.setIncomplete(incompleteList);
		rsp.setRelation(trainingDataListRsp);

		return rsp;
	}

}

/**
 * 训练比较器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
class TrainingComparator implements Comparator<TrainingData> {

	private final Boolean asc;

	public TrainingComparator(Boolean asc) {
		this.asc = asc;
	}

	/**
	 * 比较
	 *
	 * @param o1 o1群
	 * @param o2 o2
	 * @return int
	 */
	@Override
	public int compare(TrainingData o1, TrainingData o2) {
		if (asc) {
			return Long.compare(o1.getStartTime().getTime(), o2.getStartTime().getTime());
		} else {
			return Long.compare(o2.getStartTime().getTime(), o1.getStartTime().getTime());
		}
	}
}
