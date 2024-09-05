package com.cyberpeace.oj.course.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.oj.course.controller.req.TrainPlanAddReq;
import com.cyberpeace.oj.course.controller.req.TrainPlanReq;
import com.cyberpeace.oj.course.controller.req.TrainPlanUpdateReq;
import com.cyberpeace.oj.course.controller.res.TrainPlanRsp;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;
import com.cyberpeace.oj.course.service.bo.TrainPlanBo;

public interface TrainPlanService extends IService<TrainPlan> {

	/**
	 * 查询可访问的训练计划列表
	 * @param userInfo
	 * @return
	 */
	List<TrainPlanBo> trainPlanList(UserInfo userInfo);

	IPage<TrainPlanRsp> page(TrainPlanReq trainPlanReq, UserInfo userInfo);

	void checkNameRepeat(String name, String id, String userId);

    void reuse(TrainPlanUpdateReq reuseReq);

	TrainPlan getPlanById(String trainPlanId);
}
