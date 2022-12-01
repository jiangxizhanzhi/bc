package com.cyberpeace.bs.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.constant.BsConstants;
import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.bs.dao.entity.UserModel;
import com.cyberpeace.bs.dao.entity.UserSimulator;
import com.cyberpeace.bs.dao.mapper.DeviceOccupationMapper;
import com.cyberpeace.bs.dao.mapper.UserModelMapper;
import com.cyberpeace.bs.dao.mapper.UserSimulatorMapper;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.UserModelService;
import com.cyberpeace.bs.service.UserSimulatorService;
import com.cyberpeace.bs.service.UserTypeService;
import com.cyberpeace.bs.service.bo.BehaviorStatistic;
import com.cyberpeace.bs.service.bo.BsTimerTask;
import com.cyberpeace.bs.service.bo.behavior.BehaviorSimple;
import com.cyberpeace.bs.service.bo.behavior.LcBehavior;
import com.cyberpeace.bs.service.bo.lc.LcBehaviorRspBo;
import com.cyberpeace.bs.service.bo.lc.StopTaskReq;
import com.cyberpeace.bs.service.bo.simulator.*;
import com.cyberpeace.bs.service.bo.statistics.BehaviorMeterBo;
import com.cyberpeace.bs.utils.BsUtil;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserSimulatorServiceImpl implements UserSimulatorService {

	private final Map<String, BsTimer> timerMap = Maps.newConcurrentMap();

	@Autowired
	UserSimulatorMapper userSimulatorMapper;

	@Autowired
	UserModelMapper userModelMapper;

	@Autowired
	UserModelService userModelService;

	@Autowired
	RemoteSceneService remoteSceneService;

	@Autowired
	UserTypeService userTypeService;

	@Autowired
	LcService lcService;

	@Autowired
	TransactionTemplate transactionTemplate;

	@Autowired
	DeviceOccupationMapper deviceOccupationMapper;


	@Override
	public List<UserSimulatorBo> listSimulator() {
		List<UserSimulator> simulators = userSimulatorMapper.selectAll();
		List<UserSimulatorBo> userSimulatorBos = Lists.newArrayList();
		simulators.forEach(simulator -> {
			UserSimulatorBo simulatorBo = BeanUtils.copy(simulator, UserSimulatorBo.class);
			BsUtil.setHabits(simulatorBo, simulator.getAccessHabit());
			userSimulatorBos.add(simulatorBo);
		});

		return userSimulatorBos;
	}

    @Override
    public ListRsp<UserSimulatorBo> pageSimulators(PageReqBase pageReqBase) {
        Page page = pageReqBase.toPageParam();
		IPage<UserSimulator> userSimulatorIPage = userSimulatorMapper.page(page, "");
		userSimulatorIPage.convert(simulator -> {
			UserSimulatorBo simulatorBo = BeanUtils.copy(simulator, UserSimulatorBo.class);
			BsUtil.setHabits(simulatorBo, simulator.getAccessHabit());
			return simulatorBo;
		});
		return PageUtil.getListResult(userSimulatorIPage);
    }

    @Override
	public void batchDelete(List<String> resourceIds) {
		if (CollectionUtils.isEmpty(resourceIds)) {
			return;
		}
		userSimulatorMapper.deleteBatchIds(resourceIds);
		batchRemoveTimer(resourceIds);
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
		log.info("create simulator request: {}", JsonUtil.toJsonString(addBo));
		String sceneId = addBo.getSceneId();
		List<String> userModelIds = addBo.getNodeList().stream()
				.map(NodeBo::getUserModelId).collect(Collectors.toList());
		List<UserModel> userModelList = userModelMapper.selectBatchIds(userModelIds);
		Map<String, UserModel> userModelMap = StreamApiUtils.toMap(userModelList, UserModel::getResourceId,
				userModel -> userModel);
		List<String> userTypeIds = userModelList.stream().map(UserModel::getUserTypeId).collect(Collectors.toList());
		Map<String, String> userTypeMap = userTypeService.getIdNameMap(userTypeIds);

		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				for (NodeBo node : addBo.getNodeList()) {
					String userModelId = node.getUserModelId();
					UserModel userModel = userModelMap.get(userModelId);
					if (userModel == null) {
						continue;
					}
					String userType = userTypeMap.get(userModel.getUserTypeId());
					UserSimulator simulator = createUserSimulator(userModel, node, sceneId,
							addBo.getSceneName(), userType);
					userSimulatorMapper.insert(simulator);
				}
			}
		});
	}

	@Override
	public Integer countSimulators() {
		return userSimulatorMapper.countAll();
	}

	@Override
	public Integer countActiveSimulators() {
		return userSimulatorMapper.countByStatus(UserSimulator.Status.RUNNING.getCode());
	}

	@Override
	public ListRsp<BehaviorMeterBo> pageBehaviorMeters(MeterPageReq pageReq) {
		Page page = pageReq.toPageParam();
		String sceneId = pageReq.getScene_id();
		IPage<UserSimulator> simulators = userSimulatorMapper.page(page, sceneId);
		simulators.convert(simulator ->{
			BehaviorMeterBo behaviorMeterBo = new BehaviorMeterBo();
			behaviorMeterBo.setId(simulator.getDeviceId());
			behaviorMeterBo.setIp(simulator.getDeviceIp());
			behaviorMeterBo.setName(simulator.getUserName() + "_" + simulator.getSceneId());
			behaviorMeterBo.setResourceId(simulator.getResourceId());
			behaviorMeterBo.setSceneName(simulator.getSceneName());
			behaviorMeterBo.setStatus(simulator.getStatus());
			return behaviorMeterBo;
		});
		return PageUtil.getListResult(simulators);
	}

	@Override
	public SimulatorDetail getDetail(String simulatorId) {
		UserSimulator userSimulator = getOrException(simulatorId);
		SimulatorDetail detail = BeanUtils.copy(userSimulator, SimulatorDetail.class);
		detail.setAccessHabit(JsonUtil.toJsonObjectArray(userSimulator.getAccessHabit(), String.class));
		List<BehaviorSimple> behaviors = BsUtil.parseBehaviorList(userSimulator.getBehaviorList());
		List<String> behaviorIds = behaviors.stream().map(BehaviorSimple::getId)
				.collect(Collectors.toList());
		if (behaviorIds.isEmpty()) {
			detail.setBehaviors(Lists.newArrayList());
		} else {
			List<LcBehaviorRspBo> lcBehaviorRspBos = lcService.getLcBehaviorListByIds(behaviorIds,
					false);
			List<LcBehavior> lcBehaviors = lcBehaviorRspBos.stream().map(this::createLcBehavior)
					.collect(Collectors.toList());
			detail.setBehaviors(lcBehaviors);
		}
		return detail;
	}

    @Override
    public void deleteBySceneId(String sceneId) {
        if (StringUtils.isEmpty(sceneId)) {
            throw new BusinessException(BsErrorCode.RESOURCE_ID_MISSING);
        }
        List<String> relatedSimulatorIds = userSimulatorMapper.getIdsBySceneId(sceneId);
		if(relatedSimulatorIds.isEmpty()){
			return;
		}
		// 删除仪表，解除实体设备占用
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				userSimulatorMapper.deleteBySceneId(sceneId);
				deviceOccupationMapper.releaseBySceneId(sceneId);
			}
		});
		// 删除定时任务
		batchRemoveTimer(relatedSimulatorIds);
    }

	@Override
	public List<SceneIdName> getSceneList() {
		return userSimulatorMapper.getSceneList();
	}

	private LcBehavior createLcBehavior(LcBehaviorRspBo rspBo) {
		LcBehavior behavior = new LcBehavior();
		behavior.setId(rspBo.getId());
		behavior.getApplication().add(rspBo.getApplication());
		behavior.setCreatorName(rspBo.getCreatorName());
		behavior.setName(rspBo.getFlowChartName());
		behavior.setAccessOs(rspBo.getDeviceType());
		behavior.setNetworkType(rspBo.getNetModel());
		behavior.setCreateTime(rspBo.getCreateTime());
		behavior.setTimeCost(BsUtil.s2Min(rspBo.getConsumeTime()));
		behavior.setStepCount(rspBo.getStepNum());
		behavior.getTag().add(rspBo.getTag());
		return behavior;
	}

	@Override
	public void startSimulator(String resourceId) {
		UserSimulator simulator = getOrException(resourceId);
		// 不能重复开始
		if (!simulator.getStatus().equals(UserSimulator.Status.NOT_STARTED.getCode())) {
			throw new BusinessException(BsErrorCode.ALREADY_STARTED);
		}
		start(simulator);
	}

	@Override
	public void batchStartSimulator(List<String> simulatorIds) {
		List<UserSimulator> simulatorList = userSimulatorMapper.selectBatchIds(simulatorIds);
		// 不加事务
		for (UserSimulator simulator : simulatorList) {
			if (!simulator.getStatus().equals(UserSimulator.Status.RUNNING.getCode())) {
				start(simulator);
			}
		}
	}

	private void start(UserSimulator simulator) {
		// create timer
		BsTimer timer = new BsTimer();
		List<BehaviorSimple> behaviorList = BsUtil.parseBehaviorList(simulator.getBehaviorList());
		int index = behaviorList.size();
		Map<String, String> behaviorVariableMap = BsUtil.getVariableMap(simulator.getBehaviorVariable());
		// get params

		for (BehaviorSimple behavior : behaviorList) {
			// 最后一个task index为0
			index--;
			BsTimerTask task = new BsTimerTask(simulator.getDeviceId(), simulator.getDeviceIp(),simulator.getIsEntity(),
					behavior.getId(), index, simulator.getResourceId(),
					behaviorVariableMap.getOrDefault(behavior.getId(), "[]"), timer);
			// 循环执行，间隔一天，跳过当天在当前时间之前的行为
			timer.scheduleAtFixedRate(task, BsUtil.getNextDate(behavior.getStartTime()),
					BsConstants.MILLI_IN_A_DAY);
		}
		timerMap.putIfAbsent(simulator.getResourceId(), timer);

		simulator.setStatus(UserSimulator.Status.RUNNING.getCode());
		userSimulatorMapper.updateById(simulator);
	}

	@Override
	public void stopSimulator(String resourceId) {
		UserSimulator simulator = getOrException(resourceId);
		stop(simulator);
	}

	@Override
	public void batchStopSimulator(List<String> simulatorIds) {
		List<UserSimulator> simulatorList = userSimulatorMapper.selectBatchIds(simulatorIds);
		// 不加事务
		for (UserSimulator simulator : simulatorList) {
			if (simulator.getStatus().equals(UserSimulator.Status.RUNNING.getCode())) {
				stop(simulator);
			}
		}
	}

	private void stop(UserSimulator simulator){
		// cancel timer
		BsTimer timer = timerMap.get(simulator.getResourceId());
		if (timer != null) {
			timer.cancel();
			timerMap.remove(simulator.getResourceId());
		}
		// todo stop running behavior
		// get latest behavior id
		List<BehaviorSimple> behaviorSimpleList = BsUtil
				.parseBehaviorList(simulator.getBehaviorList());
		if (!simulator.getExecutedIndex().equals(behaviorSimpleList.size())) {
			// stop latest executed behavior
			String latestBehaviorId = behaviorSimpleList
					.get(behaviorSimpleList.size() - simulator.getExecutedIndex() - 1).getId();
			try {
				StopTaskReq req = new StopTaskReq();
				req.setChartId(new Long(latestBehaviorId));
				req.setIsUsingIp(!simulator.getIsEntity());
				req.setDeviceId(new Integer(simulator.getDeviceId()));
				req.setIp(simulator.getDeviceIp());
				lcService.stopBehavior(req);
			} catch (Exception e) {
				log.warn("Failed to stop behavior [{}] of simulator [{}]: {}",
						latestBehaviorId, simulator.getResourceId(), e);
			}
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
	public void updateProcess(String simulatorId, Integer index) {
//		UserSimulator simulator = userSimulatorMapper.selectById(simulatorId);
//		simulator.setExecutedIndex(index);
		// if (index == 0) {
		// traffic.setStatus(UserSimulator.Status.STOPPED.getCode());
		// }
//		userSimulatorMapper.updateById(simulator);
		userSimulatorMapper.updateProcess(simulatorId, index);
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

	private UserSimulator createUserSimulator(UserModel userModel, NodeBo nodeBo, String sceneId,
			String sceneName, String userType) {
		UserSimulator simulator = new UserSimulator();
		BehaviorStatistic statistic = BsUtil.getBehaviorStatistic(userModel.getBehaviorList());
		simulator.setUserModelId(userModel.getResourceId());
		simulator.setUserName(userModel.getUserName());
		simulator.setUserTypeId(userModel.getUserTypeId());
		simulator.setUserType(userType);
		simulator.setStartTime(statistic.getStartTime());
		simulator.setEndTime(statistic.getEndTime());
		simulator.setAccessConfig(userModel.getAccessConfig());
		simulator.setAccessOs(userModel.getAccessOs());
		simulator.setBehaviorList(userModel.getBehaviorList());
		simulator.setBehaviorVariable(JsonUtil.arrayToString(nodeBo.getBehaviorConfig()));
		simulator.setBehaviorCount(statistic.getBehaviorCount());
		simulator.setAccessHabit(userModel.getAccessHabit());
		simulator.setBandwidthLimited(userModel.getBandwidthLimited());
		simulator.setBandwidth(userModel.getBandwidth());
		simulator.setStatus(UserSimulator.Status.NOT_STARTED.getCode());
		// todo index
		simulator.setExecutedIndex(statistic.getBehaviorCount());
		String deviceId = nodeBo.getNodeId();
		if (StringUtils.isBlank(deviceId)) {
			throw new BusinessException(BsErrorCode.INVALID_DEVICE_ID);
		}
		simulator.setDeviceId(deviceId);
		String deviceIp = BsUtil.getIp(nodeBo.getIpConfig());
		if (StringUtils.isBlank(deviceIp)) {
			throw new BusinessException(BsErrorCode.INVALID_DEVICE_IP);
		}
		simulator.setDeviceIp(deviceIp);
		simulator.setDeviceName(nodeBo.getNodeName());
		if(nodeBo.getIsEntity() == null){
			throw new BusinessException(BsErrorCode.ACTUAL_OR_VIRTUAL);
		}
		simulator.setIsEntity(nodeBo.getIsEntity());
		simulator.setSceneId(sceneId);
		simulator.setSceneName(sceneName);
		return simulator;
	}

	@Override
	public UserSimulator getOrException(String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			throw new BusinessException(BsErrorCode.RESOURCE_ID_MISSING);
		}
		UserSimulator simulator = userSimulatorMapper.selectById(resourceId);
		if (simulator == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		return simulator;
	}

	private UserSimulator getOrNull(String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			return null;
		}
		return userSimulatorMapper.selectById(resourceId);
	}


	private void batchRemoveTimer(Collection<String> simulatorIds){
		for (String resourceId : simulatorIds) {
			BsTimer timer = timerMap.get(resourceId);
			if (timer != null) {
				timer.cancel();
				timerMap.remove(resourceId);
			}
		}
	}
}
