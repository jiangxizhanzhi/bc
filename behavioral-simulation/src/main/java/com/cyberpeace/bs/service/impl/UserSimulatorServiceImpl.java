package com.cyberpeace.bs.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.cyberpeace.bs.constant.BsConstants;
import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.bs.dao.entity.UserModel;
import com.cyberpeace.bs.dao.entity.UserSimulator;
import com.cyberpeace.bs.dao.mapper.DemoMapper;
import com.cyberpeace.bs.dao.mapper.UserModelMapper;
import com.cyberpeace.bs.dao.mapper.UserSimulatorMapper;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.UserSimulatorService;
import com.cyberpeace.bs.service.UserTypeService;
import com.cyberpeace.bs.service.bo.BehaviorStatistic;
import com.cyberpeace.bs.service.bo.demo.TaskDemo;
import com.cyberpeace.bs.service.bo.demo.TaskParam;
import com.cyberpeace.bs.service.bo.model.BehaviorSimple;
import com.cyberpeace.bs.service.bo.simulator.*;
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

	@Autowired
	DemoMapper demoMapper;

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
		UserSimulator simulator = createUserSimulator(userModel, addReq);
		userSimulatorMapper.insert(simulator);
	}

	@Override
	public void batchCreate(SimulatorAddBo addBo) {
		String sceneId = addBo.getSceneId();
		List<UserSimulator> simulatorList = Lists.newArrayList();
		for (NodeBo node : addBo.getNodeList()) {
			String userModelId = node.getUserModelId();
			if (StringUtils.isBlank(userModelId)) {
				throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
			}
			UserModel userModel = userModelMapper.selectById(userModelId);
			if (userModel == null) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
			}
			UserSimulator simulator = createUserSimulator(userModel, node, sceneId);
			simulatorList.add(simulator);
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				for (UserSimulator simulator : simulatorList) {
					userSimulatorMapper.insert(simulator);
				}
			}
		});
	}

	@Override
	public void startSimulator(String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		UserSimulator simulator = userSimulatorMapper.selectById(resourceId);
		if (simulator == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		// 不能重复开始
		if (!simulator.getStatus().equals(UserSimulator.Status.NOT_STARTED.getCode())) {
			throw new BusinessException(BsErrorCode.ALREADY_STARTED);
		}
		// create timer
		BsTimer timer = new BsTimer();
		List<BehaviorSimple> behaviorList = BsUtil.parseBehaviorList(simulator.getBehaviorList());
		int index = behaviorList.size();
		Map<String, String> behaviorVariableMap = BsUtil
				.getVariableMap("");
		// get params
		String deviceId = lcService.getDeviceId();
		TaskParam param = demoMapper.selectLatest();
		if (deviceId != null) {
			param.setDeviceId(deviceId);
			demoMapper.saveParam(deviceId, param.getEnvironmentId(), param.getProjectId());
		}

		for (BehaviorSimple behavior : behaviorList) {
			// 最后一个task index为0
			index--;
			TaskDemo task = new TaskDemo(simulator.getDeviceIp(), behavior.getId(),
					index, resourceId, behaviorVariableMap.getOrDefault(behavior.getId(), ""),
					timer, param);
			// 循环执行，间隔一天，跳过当天在当前时间之前的行为
			timer.scheduleAtFixedRate(task, BsUtil.getNextDate(behavior.getStartTime()),
					BsConstants.MILLI_IN_A_DAY);
		}
		timerMap.putIfAbsent(resourceId, timer);

		simulator.setStatus(UserSimulator.Status.RUNNING.getCode());
		userSimulatorMapper.updateById(simulator);
	}

	@Override
	public void stopSimulator(String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		UserSimulator simulator = userSimulatorMapper.selectById(resourceId);
		if (simulator == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		// cancel timer
		BsTimer timer = timerMap.get(resourceId);
		if (timer != null) {
			timer.cancel();
			timerMap.remove(resourceId);
		}
		// todo stop running behavior
		// get latest behavior id
		List<BehaviorSimple> behaviorSimpleList = BsUtil
				.parseBehaviorList(simulator.getBehaviorList());
		if (!simulator.getExecutedIndex().equals(behaviorSimpleList.size())) {
			// stop latest executed behavior
			String latestBehaviorId = behaviorSimpleList
					.get(behaviorSimpleList.size() - simulator.getExecutedIndex() - 1).getId();
			lcService.stopBehavior(simulator.getDeviceIp(), latestBehaviorId);
		}
		// update status
		simulator.setStatus(UserSimulator.Status.STOPPED.getCode());
		userSimulatorMapper.updateById(simulator);
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

	@Override
	public void saveParams(TaskParam taskParam) {
		demoMapper.saveParam(taskParam.getDeviceId(), taskParam.getEnvironmentId(),
				taskParam.getProjectId());
	}

	@Override
	public TaskParam getParam() {
		return demoMapper.selectLatest();
	}


	private UserSimulator createUserSimulator(UserModel userModel, UserSimulatorAddReq addReq) {
		UserSimulator simulator = new UserSimulator();
		BehaviorStatistic statistic = BsUtil.getBehaviorStatistic(userModel.getBehaviorList());
		simulator.setUserModelId(userModel.getResourceId());
		simulator.setUserName(userModel.getUserName());
		simulator.setUserTypeId(userModel.getUserTypeId());
		simulator.setUserType(userTypeService.getNameById(userModel.getUserTypeId()));
		simulator.setStartTime(statistic.getStartTime());
		simulator.setEndTime(statistic.getEndTime());
		simulator.setAccessConfig(userModel.getAccessConfig());
		simulator.setAccessOs(userModel.getAccessOs());
		simulator.setBehaviorList(userModel.getBehaviorList());
		simulator.setBehaviorVariable(addReq.getBehaviorVariables());
		simulator.setBehaviorCount(statistic.getBehaviorCount());
		simulator.setAccessHabit(userModel.getAccessHabit());
		simulator.setBandwidthLimited(userModel.getBandwidthLimited());
		simulator.setBandwidth(userModel.getBandwidth());
		simulator.setStatus(UserSimulator.Status.NOT_STARTED.getCode());
		// todo index
		simulator.setExecutedIndex(statistic.getBehaviorCount());
		simulator.setDeviceId(addReq.getDeviceId());
		simulator.setDeviceIp(addReq.getDeviceIp());
		simulator.setDeviceName(addReq.getDeviceName());
		simulator.setSceneId(addReq.getSceneId());
		return simulator;
	}

	private UserSimulator createUserSimulator(UserModel userModel, NodeBo nodeBo, String sceneId) {
		UserSimulator simulator = new UserSimulator();
		BehaviorStatistic statistic = BsUtil.getBehaviorStatistic(userModel.getBehaviorList());
		simulator.setUserModelId(userModel.getResourceId());
		simulator.setUserName(userModel.getUserName());
		simulator.setUserTypeId(userModel.getUserTypeId());
		simulator.setUserType(userTypeService.getNameById(userModel.getUserTypeId()));
		simulator.setStartTime(statistic.getStartTime());
		simulator.setEndTime(statistic.getEndTime());
		simulator.setAccessConfig(userModel.getAccessConfig());
		simulator.setAccessOs(userModel.getAccessOs());
		simulator.setBehaviorList(userModel.getBehaviorList());
		simulator.setBehaviorVariable("{}");
		simulator.setBehaviorCount(statistic.getBehaviorCount());
		simulator.setAccessHabit(userModel.getAccessHabit());
		simulator.setBandwidthLimited(userModel.getBandwidthLimited());
		simulator.setBandwidth(userModel.getBandwidth());
		simulator.setStatus(UserSimulator.Status.NOT_STARTED.getCode());
		// todo index
		simulator.setExecutedIndex(statistic.getBehaviorCount());
		simulator.setDeviceId(nodeBo.getNodeId());
		simulator.setDeviceIp("");
		simulator.setDeviceName(nodeBo.getNodeName());
		simulator.setSceneId(sceneId);
		return simulator;
	}
}
