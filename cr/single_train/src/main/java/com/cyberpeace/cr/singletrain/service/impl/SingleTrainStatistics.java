package com.cyberpeace.cr.singletrain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.dao.mapper.TrainRecordMapper;
import com.cyberpeace.cr.crbase.service.CrEventStatistics;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingCount;
import com.cyberpeace.cr.crbase.service.context.statistics.TrainingData;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleRoomMapper;
import com.cyberpeace.cr.singletrain.dao.mapper.SingleTemplateMapper;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.google.common.collect.Lists;

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
		List<SingleRoom> rooms = singleRoomMapper.selectUnfinishedByUserId(userId);

		for (SingleRoom room : rooms) {
			BusinessTemplate businessTemplate = businessTemplateMapper
					.selectById(room.getBusinessTemplateId());
			TrainingData data = BeanUtils.copy(businessTemplate, TrainingData.class);
			data.setAppId(LocalScene.BusinessCode.SINGLE_TRAINING.getCode());
			data.setAppName(LocalScene.BusinessCode.SINGLE_TRAINING.getDesc());
			data.setRoleGroup(CrCommonConstants.STUDENT_ROLE);
			data.setStartTime(room.getStartTime());

			SingleTemplate singleTemplate = singleTemplateMapper
					.getByBusinessId(businessTemplate.getResourceId());
			data.setDurationTime(singleTemplate.getDurationTime());

			dataList.add(data);
		}

		return dataList;
	}

}
