package com.cyberpeace.oj.course.service;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.oj.course.service.bo.TrainPlanBo;

import java.util.List;

public interface TrainPlanService {

	/**
	 * 查询可访问的训练计划列表
	 * @param userInfo
	 * @return
	 */
	List<TrainPlanBo> trainPlanList(UserInfo userInfo);

}
