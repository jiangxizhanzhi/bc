package com.cyberpeace.cr.singletrain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.controller.rsp.TrainStatsOverviewRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.TrainRecord;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.dao.mapper.TrainRecordMapper;
import com.cyberpeace.cr.crbase.service.CrEventStatistics;
import com.cyberpeace.cr.crbase.service.context.BarChartUnit;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingData;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleRoomMapper;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleTemplateMapper;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SingleTrainStatistics extends CrEventStatistics {

	@Autowired
	TrainRecordMapper trainRecordMapper;

	@Autowired
	SingleRoomMapper singleRoomMapper;

	@Autowired
	BusinessTemplateMapper businessTemplateMapper;

	@Autowired
	SingleTemplateMapper singleTemplateMapper;

	@Override
	public LocalScene.BusinessCode getCode() {
		return LocalScene.BusinessCode.SINGLE_TRAINING;
	}

	@Override
	public TrainingCount getTrainingCount(String userId) {
		Integer completed = trainRecordMapper.countFinishedByUserIdAndType(userId,
				BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode());
		Integer uncompleted = singleRoomMapper.countUnfinishedByUserId(userId);
		TrainingCount count = new TrainingCount();
		count.setCompleted(completed);
		count.setUncompleted(uncompleted);

		return count;
	}

	@Override
	public List<TrainingData> getRelatedTraining(String userId) {

		List<TrainingData> dataList = Lists.newArrayList();
		List<SingleRoom> rooms = singleRoomMapper.selectAllByUserId(userId,
				BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode());

		for (SingleRoom room : rooms) {
			BusinessTemplate businessTemplate = businessTemplateMapper
					.selectById(room.getBusinessTemplateId());
			TrainingData data = BeanUtils.copy(businessTemplate, TrainingData.class);
			data.setAppId(LocalScene.BusinessCode.SINGLE_TRAINING.getCode());
			data.setAppName(LocalScene.BusinessCode.SINGLE_TRAINING.getDesc());
			data.setRoleGroup(CrCommonConstants.STUDENT_ROLE);
			//StartTime存在说明已开始，不存在是未开始（不会查到）；EndTime存在说明已完成；
			data.setState(room.getStartTime()==null ? 0 : 1);
			data.setState(room.getEndTime()==null ? data.getState() : 2);

			data.setStartTime(room.getStartTime()==null ? room.getCreateTime() : room.getStartTime());

			SingleTemplate singleTemplate = singleTemplateMapper
					.getByBusinessId(businessTemplate.getResourceId());
			data.setDurationTime(singleTemplate.getDurationTime());
			data.setSubType(singleTemplate.getSubType());

			dataList.add(data);
		}

		return dataList;
	}

	@Override
	public void getTrainingScore(String userId, List<BarChartUnit> trainingStatsScore) {
		LambdaQueryWrapper<TrainRecord> trainRecordLambdaQueryWrapper = new LambdaQueryWrapper<>();
		trainRecordLambdaQueryWrapper.eq(TrainRecord::getUserId, userId);
		List<TrainRecord> trainRecordList = trainRecordMapper.selectList(trainRecordLambdaQueryWrapper);
		Integer sum = 0;
		List<String> templateIds = StreamApiUtils.map(trainRecordList, TrainRecord::getBusinessTemplateId);
		if (templateIds.size() == 0) {
			trainingStatsScore.add(new BarChartUnit("单兵训练", sum));
			return;
		}
		List<BusinessTemplate> businessTemplateList = businessTemplateMapper.selectBatchIds(templateIds);
		Map<String, BusinessTemplate> businessTemplateMap = StreamApiUtils.toMap(businessTemplateList, BusinessTemplate::getResourceId, businessTemplate -> businessTemplate);
		for (TrainRecord trainRecord: trainRecordList) {
			JSONObject trainExtraData = JsonUtil.toJsonObject(trainRecord.getExtra(), JSONObject.class);
			if (businessTemplateMap.get(trainRecord.getBusinessTemplateId()) != null
					&& trainExtraData != null && trainExtraData.getInteger("score") != null) {
				sum += trainExtraData.getInteger("score");
			}
		}
		trainingStatsScore.add(new BarChartUnit("单兵训练", sum));
	}

	@Override
	public List<TrainStatsOverviewRsp> getTrainingOverview(String userId) {
		List<TrainStatsOverviewRsp> dataList = Lists.newArrayList();
		List<SingleRoom> rooms = singleRoomMapper.selectAllByUserId(userId,
				BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode());

		for (SingleRoom room : rooms) {
			if (room.getEndTime() != null) {
				continue;
			}
			BusinessTemplate businessTemplate = businessTemplateMapper
					.selectById(room.getBusinessTemplateId());
			TrainStatsOverviewRsp data = BeanUtils.copy(businessTemplate, TrainStatsOverviewRsp.class);
			data.setRoomId(room.getBusinessTemplateId());
			data.setBusinessType(LocalScene.BusinessCode.SINGLE_TRAINING.getCode());
			data.setBusinessTypeName(LocalScene.BusinessCode.SINGLE_TRAINING.getDesc());
			//StartTime存在说明已开始，不存在是未开始（不会查到）；EndTime存在说明已完成；
			data.setStatus(room.getStartTime()==null ? 0 : 1);
			data.setStatus(room.getEndTime()==null ? data.getStatus() : 2);

			data.setStartTime(room.getStartTime()==null ? room.getCreateTime() : room.getStartTime());

			SingleTemplate singleTemplate = singleTemplateMapper
					.getByBusinessId(businessTemplate.getResourceId());
			data.setDurationTime(singleTemplate.getDurationTime());
			data.setUserCount(null);
			data.setRoleGroup(CrCommonConstants.STUDENT_ROLE);
			LambdaQueryWrapper<TrainRecord> trainRecordLambdaQueryWrapper = new LambdaQueryWrapper<>();
			trainRecordLambdaQueryWrapper.eq(TrainRecord::getBusinessTemplateId, room.getBusinessTemplateId());
			trainRecordLambdaQueryWrapper.eq(TrainRecord::getUserId, userId);
			List<TrainRecord> trainRecordList = trainRecordMapper.selectList(trainRecordLambdaQueryWrapper);
			Integer score = 0;
			for (TrainRecord trainRecord: trainRecordList) {
				JSONObject trainExtraData = JsonUtil.toJsonObject(trainRecord.getExtra(), JSONObject.class);
				if (trainExtraData != null && trainExtraData.getInteger("score") != null) {
					score = trainExtraData.getInteger("score") > score ? trainExtraData.getInteger("score") : score;
				}
			}
			data.setScore(score);
			dataList.add(data);
		}
		return dataList;
	}

}
