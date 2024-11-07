package com.cyberpeace.cr.singletrain.service.impl;

import java.util.List;

import com.cyberpeace.cr.singletrain.service.bo.SingleRoomData;
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

/**
 * 单训练统计学
 *
 * @author SN
 * @date 2024/11/07
 */
@Service
public class SingleTrainStatistics extends CrEventStatistics {

	/**
	 * 训练记录Mapper
	 */
	@Autowired
	TrainRecordMapper trainRecordMapper;

	/**
	 * 单房间Mapper
	 */
	@Autowired
	SingleRoomMapper singleRoomMapper;

	/**
	 * 业务模板Mapper
	 */
	@Autowired
	BusinessTemplateMapper businessTemplateMapper;

	/**
	 * 单模板Mapper
	 */
	@Autowired
	SingleTemplateMapper singleTemplateMapper;

	/**
	 * 获取代号
	 *
	 * @return {@code LocalScene.BusinessCode }
	 */
	@Override
	public LocalScene.BusinessCode getCode() {
		return LocalScene.BusinessCode.SINGLE_TRAINING;
	}

	/**
	 * 获取训练计数
	 *
	 * @param userId 用户id
	 * @return {@code TrainingCount }
	 */
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

	/**
	 * 获取相关训练
	 *
	 * @param userId 用户id
	 * @return {@code List<TrainingData> }
	 */
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

}
