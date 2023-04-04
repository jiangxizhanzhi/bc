package com.cyberpeace.cr.crbase.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.mdparser.convert.TrainTypeConvert;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.ListUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.AssetType;
import com.cyberpeace.cr.crbase.service.CrBaseService;
import com.cyberpeace.cr.crbase.service.CrEventStatistics;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.statistics.*;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.stereotype.Service;

import java.util.*;

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
	public TrainingAssetAccount getTrainingAssetAccount(String userId) {
		TrainingAssetAccount trainAssetAccount = new TrainingAssetAccount();
		List<LocalScene.BusinessCode> codeList = Arrays.asList(LocalScene.BusinessCode.TEAM_TRAINING, LocalScene.BusinessCode.RED_VS_BLUE);
		List<CrRoleNode> crRoleNodeList = new ArrayList<>();
		for (LocalScene.BusinessCode businessCode: codeList) {
			CrEventStatistics crEventStatistics = register.get(businessCode);
			crRoleNodeList.addAll(crEventStatistics.getCrRoleNodeList(userId));
		}
		Integer operatingSystemCount = Integer.parseInt(String.valueOf(
				crRoleNodeList.stream().filter(crRoleNode -> crRoleNode.getCategory().equals(AssetType.OPERATING_SYSTEM.getCode())).count()));
		Integer businessNodesCount = Integer.parseInt(String.valueOf(
				crRoleNodeList.stream().filter(crRoleNode -> crRoleNode.getCategory().equals(AssetType.BUSINESS_NODES.getCode())).count()));
		Integer networkNodeCount = Integer.parseInt(String.valueOf(
				crRoleNodeList.stream().filter(crRoleNode -> crRoleNode.getCategory().equals(AssetType.NETWORK_NODE.getCode())).count()));
		Integer securityNodeCount = Integer.parseInt(String.valueOf(
				crRoleNodeList.stream().filter(crRoleNode -> crRoleNode.getCategory().equals(AssetType.SECURITY_NODE.getCode())).count()));
		trainAssetAccount.setOperatingSystemCount(operatingSystemCount);
		trainAssetAccount.setBusinessNodesCount(businessNodesCount);
		trainAssetAccount.setNetworkNodeCount(networkNodeCount);
		trainAssetAccount.setSecurityNodeCount(securityNodeCount);
		trainAssetAccount.setCount(operatingSystemCount + businessNodesCount + networkNodeCount + securityNodeCount);
		return trainAssetAccount;
	}

	@Override
	public TrainingPeopleStatistics getTrainingPeopleStatistics(String userId) {
		TrainingPeopleStatistics trainingPeopleStatistics = new TrainingPeopleStatistics();
		List<LocalScene.BusinessCode> codeList = Arrays.asList(LocalScene.BusinessCode.RED_TRAINING, LocalScene.BusinessCode.BLUE_TRAINING, LocalScene.BusinessCode.RED_VS_BLUE);
		for (LocalScene.BusinessCode businessCode: codeList) {
			CrEventStatistics crEventStatistics = register.get(businessCode);
			crEventStatistics.getPeopleCount(userId, trainingPeopleStatistics);
		}
		trainingPeopleStatistics.setCount(trainingPeopleStatistics.getBlueCount() + trainingPeopleStatistics.getRedCount() + trainingPeopleStatistics.getRvbCount());
		return trainingPeopleStatistics;
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
