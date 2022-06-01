package com.cyberpeace.oj.course.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;
import com.cyberpeace.oj.course.dao.mapper.TrainPlanMapper;
import com.cyberpeace.oj.course.service.TrainPlanService;
import com.cyberpeace.oj.course.service.bo.TrainPlanBo;

@Service
public class TrainPlanServiceImpl implements TrainPlanService {

	@Autowired
	private TrainPlanMapper trainPlanMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public List<TrainPlanBo> trainPlanList(UserInfo userInfo) {
		List<TrainPlan> trainPlans = userInfo.isSuperuser() ? trainPlanMapper.all()
				: trainPlanMapper.selectByUser(userInfo.getUserId());
		return StreamApiUtils.map(trainPlans, trainPlan -> entityToBo(trainPlan, userInfo));
	}

	/** 数据转换*/
	private TrainPlanBo entityToBo(TrainPlan trainPlan, UserInfo userInfo) {
		TrainPlanBo planBo = BeanUtils.copy(trainPlan, TrainPlanBo.class);
		planBo.setTrainPlanName(trainPlan.getName());

		UserInfo user = remoteUserService.getById(trainPlan.getCreateUserId());
		planBo.setUsername(userInfo.isSuperuser() ? user.getName() : CommonConstant.EMPTY_STRING);
		return planBo;
	}
}
