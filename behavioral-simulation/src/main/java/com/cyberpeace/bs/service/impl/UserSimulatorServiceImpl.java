package com.cyberpeace.bs.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.cyberpeace.bs.constant.BsConstants;
import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.bs.dao.entity.UserModel;
import com.cyberpeace.bs.dao.entity.UserSimulator;
import com.cyberpeace.bs.dao.mapper.UserModelMapper;
import com.cyberpeace.bs.dao.mapper.UserSimulatorMapper;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.UserSimulatorService;
import com.cyberpeace.bs.service.UserTypeService;
import com.cyberpeace.bs.service.bo.BehaviorStatistic;
import com.cyberpeace.bs.service.bo.TrafficTimerTask;
import com.cyberpeace.bs.service.bo.model.BehaviorSimple;
import com.cyberpeace.bs.service.bo.simulator.BsTimer;
import com.cyberpeace.bs.service.bo.simulator.UserSimulatorAddReq;
import com.cyberpeace.bs.service.bo.simulator.UserSimulatorBo;
import com.cyberpeace.bs.utils.BsUtil;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserSimulatorServiceImpl implements UserSimulatorService {

	private final Map<String, BsTimer> timerMap = Maps.newConcurrentMap();

	@Autowired
	UserSimulatorMapper userSimulatorMapper;

	@Autowired
	UserModelMapper userModelMapper;

	@Autowired
	RemoteSceneService remoteSceneService;

	@Autowired
	UserTypeService userTypeService;

	@Autowired
	LcService lcService;

	@Autowired
	TransactionTemplate transactionTemplate;

	@Override
	public List<UserSimulatorBo> listSimulator() {
		List<UserSimulator> traffics = userSimulatorMapper.selectAll();
		List<UserSimulatorBo> trafficBos = Lists.newArrayList();
		traffics.forEach(traffic -> {
			UserSimulatorBo trafficBo = BeanUtils.copy(traffic, UserSimulatorBo.class);
			BsUtil.setHabits(trafficBo, traffic.getAccessHabit());
			trafficBos.add(trafficBo);
		});

		return trafficBos;
	}

	@Override
	public void batchDelete(List<String> resourceIds) {
		if (CollectionUtils.isEmpty(resourceIds)) {
			return;
		}
		userSimulatorMapper.deleteBatchIds(resourceIds);
	}

	@Override
	public void create(UserSimulatorAddReq addReq) {
		String userModelId = addReq.getUserModelId();
		if (StringUtils.isBlank(userModelId)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		UserModel userModel = userModelMapper.selectById(userModelId);
		if (userModel == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		UserSimulator traffic = createUserTraffic(userModel, addReq);
		userSimulatorMapper.insert(traffic);
	}

	@Override
	public void startSimulator(String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		UserSimulator traffic = userSimulatorMapper.selectById(resourceId);
		if (traffic == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		// 不能重复开始
		if (!traffic.getStatus().equals(UserSimulator.Status.NOT_STARTED.getCode())) {
			throw new BusinessException(BsErrorCode.ALREADY_STARTED);
		}
		// create timer
		BsTimer timer = new BsTimer();
		List<BehaviorSimple> behaviorList = BsUtil.parseBehaviorList(traffic.getBehaviorList());
		int index = behaviorList.size();
		Map<String, String> behaviorVariableMap = BsUtil
				.getVariableMap(traffic.getBehaviorVariable());
		for (BehaviorSimple behavior : behaviorList) {
			// 最后一个task index为0
			index--;
			TrafficTimerTask task = new TrafficTimerTask(traffic.getDeviceIp(), behavior.getId(),
					index, resourceId, behaviorVariableMap.getOrDefault(behavior.getId(), ""),
					timer);
			// 循环执行，间隔一天，跳过当天在当前时间之前的行为
			timer.scheduleAtFixedRate(task, BsUtil.getNextDate(behavior.getStartTime()),
					BsConstants.MILLI_IN_A_DAY);
		}
		timerMap.putIfAbsent(resourceId, timer);

		traffic.setStatus(UserSimulator.Status.RUNNING.getCode());
		userSimulatorMapper.updateById(traffic);
	}

	@Override
	public void stopSimulator(String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		UserSimulator traffic = userSimulatorMapper.selectById(resourceId);
		if (traffic == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		// cancel timer
		BsTimer timer = timerMap.get(resourceId);
		timer.cancel();
		timerMap.remove(resourceId);
		// todo stop running behavior
		// get latest behavior id
		List<BehaviorSimple> behaviorSimpleList = BsUtil
				.parseBehaviorList(traffic.getBehaviorList());
		if (!traffic.getExecutedIndex().equals(behaviorSimpleList.size())) {
			// stop latest executed behavior
			String latestBehaviorId = behaviorSimpleList
					.get(behaviorSimpleList.size() - traffic.getExecutedIndex() - 1).getId();
			lcService.stopBehavior(traffic.getDeviceIp(), latestBehaviorId);
		}
		// update status
		traffic.setStatus(UserSimulator.Status.STOPPED.getCode());
	}

	@Override
	public Map<String, BsTimer> getTimerMap() {
		return timerMap;
	}

	@Override
	public void updateProcess(String trafficId, Integer index) {
		UserSimulator traffic = userSimulatorMapper.selectById(trafficId);
		traffic.setExecutedIndex(index);
		if (index == 0) {
			traffic.setStatus(UserSimulator.Status.STOPPED.getCode());
		}
		userSimulatorMapper.updateById(traffic);
	}

	private UserSimulator createUserTraffic(UserModel userModel, UserSimulatorAddReq addReq) {
		UserSimulator traffic = new UserSimulator();
		BehaviorStatistic statistic = BsUtil.getBehaviorStatistic(userModel.getBehaviorList());
		traffic.setUserModelId(userModel.getResourceId());
		traffic.setUserName(userModel.getUserName());
		traffic.setUserTypeId(userModel.getUserTypeId());
		traffic.setUserType(userTypeService.getNameById(userModel.getUserTypeId()));
		traffic.setStartTime(statistic.getStartTime());
		traffic.setEndTime(statistic.getEndTime());
		traffic.setAccessConfig(userModel.getAccessConfig());
		traffic.setAccessOs(userModel.getAccessOs());
		traffic.setBehaviorList(userModel.getBehaviorList());
		traffic.setBehaviorVariable(addReq.getBehaviorVariables());
		traffic.setBehaviorCount(statistic.getBehaviorCount());
		traffic.setAccessHabit(userModel.getAccessHabit());
		traffic.setBandwidthLimited(userModel.getBandwidthLimited());
		traffic.setBandwidth(userModel.getBandwidth());
		traffic.setStatus(UserSimulator.Status.NOT_STARTED.getCode());
		// todo index
		traffic.setExecutedIndex(statistic.getBehaviorCount());
		traffic.setDeviceId(addReq.getDeviceId());
		traffic.setDeviceIp(addReq.getDeviceIp());
		traffic.setDeviceName(addReq.getDeviceName());
		return traffic;
	}
}
