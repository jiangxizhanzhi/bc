package com.cyberpeace.cr.crbase.service.impl;

import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.mdparser.convert.TrainTypeConvert;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.AssetType;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.integratation.service.RemoteTopoService;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	RemoteTopoService remoteTopoService;

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

	@Override
	public TrainingStatisticsAccount getTrainingStatisticsAccount(String userId) {
		List<TrainingCountRsp> completeList = Lists.newArrayList();
		List<TrainingCountRsp> incompleteList = Lists.newArrayList();
		List<LocalScene.BusinessCode> codeList = Arrays.asList(LocalScene.BusinessCode.SINGLE_TRAINING
				, LocalScene.BusinessCode.RED_TRAINING, LocalScene.BusinessCode.BLUE_TRAINING, LocalScene.BusinessCode.RED_VS_BLUE);
		Integer sum = 0;
		for (LocalScene.BusinessCode businessCode: codeList ) {
			CrEventStatistics statistics = register.get(businessCode);
			TrainingCount trainingCount = statistics.getTrainingCount(userId);
			if (trainingCount == null){
				trainingCount = new TrainingCount();
			}
			completeList.add(new TrainingCountRsp(businessCode.getCode(), businessCode.getDesc(),
					trainingCount.getCompleted()));
			incompleteList.add(new TrainingCountRsp(businessCode.getCode(), businessCode.getDesc(),
					trainingCount.getUncompleted()));
			sum += trainingCount.getCompleted() + trainingCount.getUncompleted();
		}
		TrainingStatisticsAccount trainingStatisticsAccount = new TrainingStatisticsAccount();
		trainingStatisticsAccount.setCompleted(completeList);
		trainingStatisticsAccount.setIncomplete(incompleteList);
		trainingStatisticsAccount.setCount(sum);
		return trainingStatisticsAccount;
	}

	@Override
	public HashMap<String, Integer> getTrainingAssetAccount(String userId) {
		HashMap<String, Integer> trainAssetAccount = new HashMap<>();
		List<LocalScene.BusinessCode> codeList = Arrays.asList(LocalScene.BusinessCode.TEAM_TRAINING, LocalScene.BusinessCode.RED_VS_BLUE);
		List<String> topoIds = new ArrayList<>();
		for (LocalScene.BusinessCode businessCode: codeList) {
			CrEventStatistics crEventStatistics = register.get(businessCode);
			topoIds.addAll(crEventStatistics.getTopoIds(userId));
		}
		for (String topoId: topoIds) {
			JSONObject elements = remoteTopoService.getTopoById(topoId).getJSONObject("scene_config").getJSONObject("plugins").getJSONObject("cpcloud").getJSONObject("elements");
			System.out.println(123);
			elements.values().forEach(element -> {
				JSONObject elementJson = (JSONObject)JSONObject.toJSON(element);
				Integer category = elementJson.getInteger("category");
				String type = EnumUtils.getEnumDescByCode(category, AssetType.class);
				Integer count = trainAssetAccount.get(type);
				if (count == null){
					trainAssetAccount.put(type, 1);
				}else {
					trainAssetAccount.put(type, ++count);
				}
			});
		}
		return trainAssetAccount;
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
