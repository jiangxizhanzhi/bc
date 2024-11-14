package com.cyberpeace.oj.course.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.oj.course.controller.req.TrainPlanReq;
import com.cyberpeace.oj.course.controller.res.TrainPlanRsp;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;
import com.cyberpeace.oj.course.service.bo.TrainPlanBo;

/**
 * 训练计划Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface TrainPlanService extends IService<TrainPlan> {

	/**
	 * 查询可访问的训练计划列表
	 *
	 * @param userInfo
	 * @return
	 */
	List<TrainPlanBo> trainPlanList(UserInfo userInfo);

	/**
	 * 分页
	 *
	 * @param trainPlanReq 训练计划请求体
	 * @param userInfo     用户信息
	 * @return {@code IPage<TrainPlanRsp> }
	 */
	IPage<TrainPlanRsp> page(TrainPlanReq trainPlanReq, UserInfo userInfo);

	/**
	 * 检查名称重复
	 *
	 * @param name   名称
	 * @param id     id
	 * @param userId 用户id
	 */
	void checkNameRepeat(String name, String id, String userId);
}
