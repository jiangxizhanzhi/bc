package com.cyberpeace.cr.crbase.service.impl;

import java.util.*;

import com.cyberpeace.common.mdparser.convert.TrainTypeConvert;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.StreamApiUtils;
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

@Service
@Slf4j
public class CrBaseServiceImpl implements CrBaseService {

	public static Map<LocalScene.BusinessCode, CrEventStatistics> register = Maps.newHashMap();

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

	/**
	 * @param userId
	 * @return 各类训练总和
	 */
	@Override
	public TrainingRecordRsp getTrainingRecords(String userId) {
		TrainingRecordRsp trainingRecordRsp = new TrainingRecordRsp();
		List<String> dates = new ArrayList<>();

		List<TrainingDateCount> res = new ArrayList<>();
		Map<String, TrainingDateCount> map = new HashMap<>();

		for (Map.Entry<LocalScene.BusinessCode, CrEventStatistics> entry : register.entrySet()) {
			CrEventStatistics statistics = entry.getValue();

			List<TrainingDateCount> trainingCountByDate = statistics.getTrainingCountByDate(userId);
			Map<String, TrainingDateCount> dataPersonMap = StreamApiUtils.toMap(trainingCountByDate, TrainingDateCount::getDate, tmp -> tmp);
			List<TrainingDateCount> allTrainingCountByDate = statistics.getAllTrainingCountByDate();
			for(TrainingDateCount tmp:allTrainingCountByDate){
				String date = tmp.getDate();
				if(!dates.contains(date)){
					dates.add(date);
					TrainingDateCount current = new TrainingDateCount();
					current.setDate(date);
					current.setAll(tmp.getCompleted());
					if(dataPersonMap.containsKey(date)){
						TrainingDateCount trainingDateCount = dataPersonMap.get(date);
						current.setCompleted(trainingDateCount.getCompleted());
					}
					res.add(current);
					map.put(date, current);
				} else {
					TrainingDateCount trainingDateCount = map.get(date);
					Integer originalAll = trainingDateCount.getAll();
					trainingDateCount.setAll(originalAll + tmp.getCompleted());
					if(dataPersonMap.containsKey(date)){
						TrainingDateCount personCount = dataPersonMap.get(date);
						Integer originalPersonCompleted = personCount.getCompleted();
						Integer personCompleted = trainingDateCount.getCompleted();
						trainingDateCount.setCompleted(personCompleted + originalPersonCompleted);
					}
					res.set(res.indexOf(trainingDateCount), trainingDateCount);
				}
			}
		}
		res.sort(Comparator.comparing(TrainingDateCount::getDate));
		dates = StreamApiUtils.getColumn(res, TrainingDateCount::getDate);
		List<Integer> person = StreamApiUtils.getColumn(res, TrainingDateCount::getCompleted);
		List<Integer> all = StreamApiUtils.getColumn(res, TrainingDateCount::getAll);
		if(dates.isEmpty()){
			return null;
		}
		trainingRecordRsp.setAll(all);
		trainingRecordRsp.setDate(dates);
		trainingRecordRsp.setPerson(person);
		return trainingRecordRsp;
	}

}

class TrainingComparator implements Comparator<TrainingData> {

	private final Boolean asc;

	public TrainingComparator(Boolean asc) {
		this.asc = asc;
	}

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
