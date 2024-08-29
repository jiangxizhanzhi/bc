package com.cyberpeace.oj.course.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.controller.req.TrainPlanReq;
import com.cyberpeace.oj.course.controller.req.TrainPlanUpdateReq;
import com.cyberpeace.oj.course.controller.res.TrainPlanRsp;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.dao.entity.TrainPlan;
import com.cyberpeace.oj.course.dao.mapper.ClassroomMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.TrainPlanMapper;
import com.cyberpeace.oj.course.service.ClassroomService;
import com.cyberpeace.oj.course.service.TrainPlanService;
import com.cyberpeace.oj.course.service.bo.TrainPlanBo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TrainPlanServiceImpl extends ServiceImpl<TrainPlanMapper, TrainPlan> implements TrainPlanService {

	@Autowired
	private TrainPlanMapper trainPlanMapper;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private ClassroomMapper classroomMapper;

	@Autowired
	private ClassroomService classroomService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public List<TrainPlanBo> trainPlanList(UserInfo userInfo) {
		List<TrainPlan> trainPlans = userInfo.isSuperuser() ? trainPlanMapper.all()
				: trainPlanMapper.selectByUser(userInfo.getUserId());
		return StreamApiUtils.map(trainPlans, trainPlan -> entityToBo(trainPlan, userInfo));
	}

	@Override
	public IPage<TrainPlanRsp> page(TrainPlanReq trainPlanReq, UserInfo userInfo) {

		Page page = trainPlanReq.toPageParam();
		List<String> courseIds = courseMapper.selectByAuth(AuthContextUtils.constructAuth(userInfo,
				false));
		IPage<TrainPlanRsp> result = trainPlanMapper.listTrainPlan(page, trainPlanReq.getPattern(), trainPlanReq.getOrganizationId(),
				userInfo.getUserId(), userInfo.isSuperuser(), userInfo.isAdmin(), courseIds);
		
		List<String> userIds = result.getRecords().stream().map(TrainPlanRsp::getCreateUserId).collect(Collectors.toList());
		Map<String, String> userNameMap = remoteUserService.getIdNameMap(userIds);
		for (TrainPlanRsp trainPlanRsp : result.getRecords()) {
			trainPlanRsp.setCreateUser(userNameMap.get(trainPlanRsp.getCreateUserId()));
		}
		return result;
	}

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

	@Override
	public void reuse(TrainPlanUpdateReq reuseReq) {
		// 校验name是否已存在
		this.checkNameRepeat(reuseReq.getName(), null,
				UserInfoContext.getUserContext().getUserId());
		TrainPlan trainPlan = BeanUtils.copy(reuseReq, TrainPlan.class);
		trainPlan.setResourceId(IDGeneratorUtil.getID());
		trainPlan.setCreateUserId(UserInfoContext.getUserContext().getUserId());
		// classrooms
		List<Classroom> classroomList = classroomMapper.selectByPlan(reuseReq.getResourceId());
		String userId = UserInfoContext.getUserContext().getUserId();
		for (Classroom classroom : classroomList) {
			classroom.setCreateUserId(userId);
			classroom.setId(null);
			classroom.setCreateTime(null);
			classroom.setUpdateTime(null);
			classroom.setResourceId(IDGeneratorUtil.getID());
			classroom.setTrainPlanId(trainPlan.getResourceId());
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				save(trainPlan);
				classroomService.saveBatch(classroomList);
			}
		});

	}

	/** 数据转换*/
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
