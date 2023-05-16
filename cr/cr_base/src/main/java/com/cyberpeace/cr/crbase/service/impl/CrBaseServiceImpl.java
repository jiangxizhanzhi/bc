package com.cyberpeace.cr.crbase.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.mdparser.convert.TrainTypeConvert;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.*;
import com.cyberpeace.constant.SelectI18nName;
import com.cyberpeace.cr.crbase.constant.AssetType;
import com.cyberpeace.cr.crbase.controller.rsp.TrainStatsOverviewRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrConstantsConfig;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import com.cyberpeace.cr.crbase.dao.mapper.CrTaskMapper;
import com.cyberpeace.cr.crbase.dao.mapper.MissionMapper;
import com.cyberpeace.cr.crbase.dao.mapper.TrainRecordMapper;
import com.cyberpeace.cr.crbase.service.CrBaseService;
import com.cyberpeace.cr.crbase.service.CrConstantsService;
import com.cyberpeace.cr.crbase.service.CrEventStatistics;
import com.cyberpeace.cr.crbase.service.context.BarChartUnit;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.statistics.*;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CrBaseServiceImpl implements CrBaseService {

	@Autowired
	private CrTaskMapper crTaskMapper;

	@Autowired
	private MissionMapper missionMapper;

	@Autowired
	private CrConstantsService crConstantsService;

	@Autowired
	private TrainRecordMapper trainRecordMapper;

	public static Map<LocalScene.BusinessCode, CrEventStatistics> register = Maps.newHashMap();

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
	public List<BarChartUnit> getTrainingAssetAccount(String userId) {
		List<LocalScene.BusinessCode> codeList = Arrays.asList(LocalScene.BusinessCode.TEAM_TRAINING, LocalScene.BusinessCode.RED_VS_BLUE);
		List<CrRoleNode> crRoleNodeList = new ArrayList<>();
		for (LocalScene.BusinessCode businessCode: codeList) {
			CrEventStatistics crEventStatistics = register.get(businessCode);
			crRoleNodeList.addAll(crEventStatistics.getCrRoleNodeList(userId));
		}
		List<BarChartUnit> trainAssetAccount = new ArrayList<>();
		trainAssetAccount.add(new BarChartUnit("操作系统", Integer.parseInt(String.valueOf(
				crRoleNodeList.stream().filter(crRoleNode -> crRoleNode.getCategory().equals(AssetType.OPERATING_SYSTEM.getCode())).count()))));
		trainAssetAccount.add(new BarChartUnit("网络节点", Integer.parseInt(String.valueOf(
				crRoleNodeList.stream().filter(crRoleNode -> crRoleNode.getCategory().equals(AssetType.NETWORK_NODE.getCode())).count()))));
		trainAssetAccount.add(new BarChartUnit("业务节点", Integer.parseInt(String.valueOf(
				crRoleNodeList.stream().filter(crRoleNode -> crRoleNode.getCategory().equals(AssetType.BUSINESS_NODES.getCode())).count()))));
		trainAssetAccount.add(new BarChartUnit("安全节点", Integer.parseInt(String.valueOf(
				crRoleNodeList.stream().filter(crRoleNode -> crRoleNode.getCategory().equals(AssetType.SECURITY_NODE.getCode())).count()))));
		return trainAssetAccount;
	}

	@Override
	public List<BarChartUnit> getTrainingPeopleStatistics(String userId) {
		List<BarChartUnit> trainingPeopleStatistics = new ArrayList<>();
		List<LocalScene.BusinessCode> codeList = Arrays.asList(LocalScene.BusinessCode.RED_TRAINING, LocalScene.BusinessCode.BLUE_TRAINING, LocalScene.BusinessCode.RED_VS_BLUE);
		for (LocalScene.BusinessCode businessCode: codeList) {
			CrEventStatistics crEventStatistics = register.get(businessCode);
			crEventStatistics.getPeopleCount(userId, trainingPeopleStatistics);
		}
		return trainingPeopleStatistics;
	}

	@Override
	public List<BarChartUnit> getTrainingScore(String userId) {
		List<BarChartUnit> trainingStatsScore = new ArrayList<>();
		List<LocalScene.BusinessCode> codeList = Arrays.asList(LocalScene.BusinessCode.SINGLE_TRAINING, LocalScene.BusinessCode.TEAM_TRAINING, LocalScene.BusinessCode.RED_VS_BLUE);
		for (LocalScene.BusinessCode businessCode: codeList) {
			CrEventStatistics crEventStatistics = register.get(businessCode);
			crEventStatistics.getTrainingScore(userId, trainingStatsScore);
		}
		return trainingStatsScore;
	}

	@Override
	public ListRsp<TrainStatsOverviewRsp> getTrainingOverview(String userId, Page page) {
		List<TrainStatsOverviewRsp> trainStatsOverviewRspList = new ArrayList<>();
		List<LocalScene.BusinessCode> codeList = Arrays.asList(LocalScene.BusinessCode.SINGLE_TRAINING, LocalScene.BusinessCode.TEAM_TRAINING, LocalScene.BusinessCode.RED_VS_BLUE);
		for (LocalScene.BusinessCode businessCode: codeList) {
			CrEventStatistics crEventStatistics = register.get(businessCode);
			trainStatsOverviewRspList.addAll(crEventStatistics.getTrainingOverview(userId));
		}
		return PageUtil.getListResult(trainStatsOverviewRspList, page, trainStatsOverviewRspList.size());
	}

	@Override
	public HashMap<String, Integer> getScoreByAttackType(Integer businessType, String roomId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		HashMap<String, Integer> map = new HashMap<>();
		List<String> missionIdList = new ArrayList<>();
		if (businessType.equals(LocalScene.BusinessCode.SINGLE_TRAINING.getCode())) {
			LambdaQueryWrapper<TrainRecord> trainRecordLambdaQueryWrapper = new LambdaQueryWrapper<>();
			trainRecordLambdaQueryWrapper.eq(TrainRecord::getBusinessTemplateId, roomId);
			trainRecordLambdaQueryWrapper.eq(TrainRecord::getUserId, userId);
			List<TrainRecord> trainRecordList = trainRecordMapper.selectList(trainRecordLambdaQueryWrapper);
			if (trainRecordList.size() == 0) {
				return map;
			}
			Integer score = 0;
			JSONObject maxTrainExtraData = null;
			for (TrainRecord trainRecord: trainRecordList) {
				JSONObject trainExtraData = JsonUtil.toJsonObject(trainRecord.getExtra(), JSONObject.class);
				if (trainExtraData != null && trainExtraData.getInteger("score") != null && trainExtraData.getInteger("score") > score) {
					score = trainExtraData.getInteger("score");
					maxTrainExtraData = trainExtraData;
				}
			}
			if (maxTrainExtraData == null) {
				return map;
			}
			missionIdList = new ArrayList<>(maxTrainExtraData.getJSONObject("missions_record").keySet());
			List<Mission> missionList = missionMapper.selectBatchIds(missionIdList);
			Map<String, String> missionCategoryMap = StreamApiUtils.toMap(missionList, Mission::getResourceId, Mission::getCategoryId);
			List<CrConstantsConfig> constants = crConstantsService.getByType(CrConstantsConfig.ConstantsType.MISSION_CATEGORY.getCode());
			Map<String, String> categoryMap = StreamApiUtils.toMap(constants, CrConstantsConfig::getResourceId, SelectI18nName::selectI18nName);
			for (Mission mission: missionList) {
				String type = categoryMap.get(missionCategoryMap.get(mission.getResourceId()));
				if (map.containsKey(type)) {
					map.put(type, map.get(type) + mission.getScore());
				} else {
					map.put(type, score);
				}
			}
		}else {
			LambdaQueryWrapper<CrTask> crTaskLambdaQueryWrapper = new LambdaQueryWrapper<>();
			crTaskLambdaQueryWrapper.eq(CrTask::getUserId, userId);
			crTaskLambdaQueryWrapper.eq(CrTask::getRoomId, roomId);
			List<CrTask> crTaskList = crTaskMapper.selectList(crTaskLambdaQueryWrapper);
			if (crTaskList.size() == 0) {
				return map;
			}
			missionIdList = StreamApiUtils.map(crTaskList, CrTask::getMissionId)
													   .stream().distinct().collect(Collectors.toList());
			List<Mission> missionList = missionMapper.selectBatchIds(missionIdList);
			Map<String, String> missionCategoryMap = StreamApiUtils.toMap(missionList, Mission::getResourceId, Mission::getCategoryId);
			List<CrConstantsConfig> constants = crConstantsService.getByType(CrConstantsConfig.ConstantsType.MISSION_CATEGORY.getCode());
			Map<String, String> categoryMap = StreamApiUtils.toMap(constants, CrConstantsConfig::getResourceId, SelectI18nName::selectI18nName);
			for (CrTask crTask: crTaskList) {
				Integer score = crTask.getScore() == null ? 0 : crTask.getScore();
				String type = categoryMap.get(missionCategoryMap.get(crTask.getMissionId()));
				if (map.containsKey(type)) {
					map.put(type, map.get(type) + score);
				} else {
					map.put(type, score);
				}
			}
		}
		return map;
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
