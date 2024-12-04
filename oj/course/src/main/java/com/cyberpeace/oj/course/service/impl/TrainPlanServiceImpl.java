package com.cyberpeace.oj.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.req.TrainPlanReq;
import com.cyberpeace.oj.course.controller.res.TrainPlanRsp;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.TrainPlanMapper;
import com.cyberpeace.oj.course.service.TrainPlanService;
import com.cyberpeace.oj.course.service.bo.TrainPlanBo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 训练计划Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see ServiceImpl
 * @see TrainPlanService
 * @since 1.0.0
 */
@Slf4j
@Service
public class TrainPlanServiceImpl extends ServiceImpl<TrainPlanMapper, TrainPlan> implements TrainPlanService {

	/**
	 * 训练计划Mapper
	 *
	 * @see TrainPlanMapper
	 */
	@Autowired
	private TrainPlanMapper trainPlanMapper;

	/**
	 * 远程用户Service
	 *
	 * @see RemoteUserService
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 课程Mapper
	 *
	 * @see CourseMapper
	 */
	@Autowired
	private CourseMapper courseMapper;

	/**
	 * 训练计划列表
	 *
	 * @param userInfo 用户信息
	 * @return {@code List<TrainPlanBo> }
	 * @throws
	 * @see UserInfo
	 * @see List<TrainPlanBo>
	 */
	@Override
	public List<TrainPlanBo> trainPlanList(UserInfo userInfo) {
		List<TrainPlan> trainPlans = userInfo.isSuperuser() ? trainPlanMapper.all()
				: trainPlanMapper.selectByUser(userInfo.getUserId());
		return StreamApiUtils.map(trainPlans, trainPlan -> entityToBo(trainPlan, userInfo));
	}

	/**
	 * 分页
	 *
	 * @param trainPlanReq 训练计划请求体
	 * @param userInfo     用户信息
	 * @return {@code IPage<TrainPlanRsp> }
	 * @throws
	 * @see TrainPlanReq
	 * @see UserInfo
	 * @see IPage<TrainPlanRsp>
	 */
	@Override
	public IPage<TrainPlanRsp> page(TrainPlanReq trainPlanReq, UserInfo userInfo) {

		Page page = trainPlanReq.toPageParam();
		List<String> courseIds = courseMapper.selectByAuth(AuthContextUtils.constructAuth(userInfo,
				false));
		IPage<TrainPlanRsp> result = trainPlanMapper.listTrainPlan(page, trainPlanReq.getPattern(), trainPlanReq.getOrganizationId(),
				userInfo.getUserId(), userInfo.isSuperuser(), userInfo.isAdmin(), courseIds);

		for (TrainPlanRsp trainPlanRsp : result.getRecords()) {
			UserInfo user = remoteUserService.getById(trainPlanRsp.getCreateUserId());
			if (null != user) {
				trainPlanRsp.setCreateUser( user.getName() );
			}
		}
		return result;
	}

	/**
	 * 检查名称重复
	 *
	 * @param name   名称
	 * @param id     id
	 * @param userId 用户id
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	@Override
	public void checkNameRepeat(String name, String id, String userId) {
		LambdaQueryWrapper<TrainPlan> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(TrainPlan::getName, name);
		queryWrapper.eq(TrainPlan::getCreateUserId, userId);

		if (StringUtils.isNotBlank(id)) {
			queryWrapper.ne(TrainPlan::getResourceId, id);
		}
		List<TrainPlan> assessModel = trainPlanMapper.selectList(queryWrapper);
		if (null != assessModel && assessModel.size()>0) {
			log.error("[{}] name repeat", StreamApiUtils.getColumn(assessModel, TrainPlan::getName).toString());
			throw new BusinessException(ErrorCode.NAME_DUPLICATE);
		}
	}

	/**
	 * 数据转换
	 *
	 * @param trainPlan 训练计划
	 * @param userInfo  用户信息
	 * @return {@code TrainPlanBo }
	 * @throws
	 * @see TrainPlan
	 * @see UserInfo
	 * @see TrainPlanBo
	 */
	private TrainPlanBo entityToBo(TrainPlan trainPlan, UserInfo userInfo) {
		TrainPlanBo planBo = BeanUtils.copy(trainPlan, TrainPlanBo.class);
		planBo.setTrainPlanName(trainPlan.getName());

		UserInfo user = remoteUserService.getById(trainPlan.getCreateUserId());
		if (null != user) {
			planBo.setUsername(userInfo.isSuperuser() ? user.getName() : CommonConstant.EMPTY_STRING);
		}
		return planBo;
	}
}
