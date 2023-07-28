package com.cyberpeace.cr.singletrain.service.impl;

import java.util.List;

import com.cyberpeace.common.enums.BusinessCode;
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
	public BusinessCode getCode() {
		return BusinessCode.SINGLE_TRAINING;
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
			data.setAppId(BusinessCode.SINGLE_TRAINING.getCode());
			data.setAppName(BusinessCode.SINGLE_TRAINING.getDesc());
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

}
