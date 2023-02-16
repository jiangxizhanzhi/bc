package com.cyberpeace.bs.service.impl;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.bs.constant.BsConstants;
import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.bs.dao.entity.UserInstance;
import com.cyberpeace.bs.dao.entity.UserModel;
import com.cyberpeace.bs.dao.entity.UserSimulator;
import com.cyberpeace.bs.dao.mapper.DeviceOccupationMapper;
import com.cyberpeace.bs.dao.mapper.UserInstanceMapper;
import com.cyberpeace.bs.dao.mapper.UserModelMapper;
import com.cyberpeace.bs.dao.mapper.UserSimulatorMapper;
import com.cyberpeace.bs.service.FlowReportService;
import com.cyberpeace.bs.service.LcService;
import com.cyberpeace.bs.service.UserSimulatorService;
import com.cyberpeace.bs.service.UserTypeService;
import com.cyberpeace.bs.service.bo.BehaviorStatistic;
import com.cyberpeace.bs.service.bo.BsTimerTask;
import com.cyberpeace.bs.service.bo.behavior.BehaviorInstance;
import com.cyberpeace.bs.service.bo.behavior.BehaviorSimple;
import com.cyberpeace.bs.service.bo.behavior.LcBehavior;
import com.cyberpeace.bs.service.bo.lc.LcBehaviorRspBo;
import com.cyberpeace.bs.service.bo.lc.LcMonitorRsp;
import com.cyberpeace.bs.service.bo.lc.StopTaskReq;
import com.cyberpeace.bs.service.bo.meter.SimulatorTimer;
import com.cyberpeace.bs.service.bo.monitor.MonitorDataRes;
import com.cyberpeace.bs.service.bo.monitor.MonitorSimulatorDetail;
import com.cyberpeace.bs.service.bo.simulator.*;
import com.cyberpeace.bs.service.bo.statistics.BehaviorMeterBo;
import com.cyberpeace.bs.utils.BsUtil;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.*;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserSimulatorServiceImpl implements UserSimulatorService {

	private final Map<String, Map<String, BsTimer>> timerMap = Maps.newConcurrentMap();

	@Autowired
	private UserSimulatorMapper userSimulatorMapper;

	@Autowired
	private UserModelMapper userModelMapper;

	@Autowired
	private UserTypeService userTypeService;

	@Autowired
	private LcService lcService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private DeviceOccupationMapper deviceOccupationMapper;

	@Autowired
	private UserInstanceMapper userInstanceMapper;

	@Autowired
	private FlowReportService flowReportService;

	private String EXTERNAL_IP_SEGMENT;

	@PostConstruct
	public void init() {
		EXTERNAL_IP_SEGMENT = SystemPropUtil.getProp("EXTERNAL_IP_SEGMENT");
		// 重启程序后恢复正在运行的仪表的定时任务
		recoverTimerTask();
	}

	@Override
	public void batchCreate(SimulatorAddBo addBo) {
		String reqString = JsonUtil.toJsonString(addBo, PropertyNamingStrategy.SnakeCase);
		log.info("create simulator request: {}", reqString);
		try {
			flowReportService.reportCreation(reqString);
		} catch (Exception e) {
			log.error("failed to report creation: ", e);
		}
		SimulatorAddResult result = createSimulatorBatch(addBo);
		// save
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				for (UserSimulator simulator : result.getSimulatorList()) {
					userSimulatorMapper.insert(simulator);
				}
				if (!result.getUserInstanceList().isEmpty()) {
					userInstanceMapper.insertUserInstanceBatch(result.getUserInstanceList());
				}
			}
		});

	}

	private SimulatorAddResult createSimulatorBatch(SimulatorAddBo addBo) {
		List<String> userModelIds = Lists.newArrayList();
		// collect user model ids
		for (NodeBo nodeBo : addBo.getNodeList()) {
			List<UserInstanceAddBo> userModelBos = nodeBo.getUserModels();
			if (userModelBos != null) {
				for (UserInstanceAddBo userInstanceAddBo : userModelBos) {
					userModelIds.add(userInstanceAddBo.getUserModelId());
				}
			}
		}
		// get user models
		List<UserModel> userModelList = Lists.newArrayList();
		if (!userModelIds.isEmpty()) {
			userModelList = userModelMapper.selectBatchIds(userModelIds);
		}
		Map<String, UserModel> userModelMap = StreamApiUtils.toMap(userModelList,
				UserModel::getResourceId, userModel -> userModel);
		// get user types
		List<String> userTypeIds = userModelList.stream().map(UserModel::getUserTypeId)
				.collect(Collectors.toList());
		Map<String, String> userTypeMap = userTypeService.getIdNameMap(userTypeIds);
		// create res
		SimulatorAddResult result = new SimulatorAddResult();
		for (NodeBo nodeBo : addBo.getNodeList()) {
			List<UserInstance> userInstanceList = createUserInstanceList(nodeBo.getUserModels(),
					userModelMap, userTypeMap);
			UserSimulator simulator = createSimulator(userInstanceList, nodeBo, addBo.getSceneId(),
					addBo.getSceneName());
			// check ip
			if (StringUtils.isBlank(simulator.getDeviceIp())) {
				throw new BusinessException(BsErrorCode.INVALID_DEVICE_IP);
			}
			result.getUserInstanceList().addAll(userInstanceList);
			result.getSimulatorList().add(simulator);
			result.getUserInstanceMap().put(simulator.getResourceId(), userInstanceList);
		}
		return result;
	}

	private UserSimulator createSimulator(List<UserInstance> userInstanceList, NodeBo nodeBo,
			String sceneId, String sceneName) {
		UserSimulator simulator = new UserSimulator();
		simulator.setAccessOs(nodeBo.getType());

		Set<String> habits = Sets.newHashSet();
		simulator.setBandwidthLimited(false);
		for (UserInstance instance : userInstanceList) {
			// get start time and end time
			if (simulator.getStartTime() == null
					|| simulator.getStartTime().isAfter(instance.getStartTime())) {
				simulator.setStartTime(instance.getStartTime());
			}
			if (simulator.getEndTime() == null
					|| simulator.getEndTime().isBefore(instance.getEndTime())) {
				simulator.setEndTime(instance.getEndTime());
			}
			// collect access habit
			habits.addAll(BsUtil.parseListOrEmpty(instance.getAccessHabit()));
			// bandwidth
			if (instance.getBandwidthLimited()) {
				simulator.setBandwidthLimited(true);
				if (simulator.getBandwidth() == null
						|| simulator.getBandwidth() > instance.getBandwidth()) {
					simulator.setBandwidth(instance.getBandwidth());
				}
			}

		}
		// get user types
		Set<String> userTypes = userInstanceList.stream().map(UserInstance::getUserType)
				.collect(Collectors.toSet());
		simulator.setUserTypes(JsonUtil.toJsonString(userTypes));
		// user count
		simulator.setUserCount(userInstanceList.size());
		// behavior count
		simulator.setBehaviorCount(
				userInstanceList.stream().mapToInt(UserInstance::getBehaviorCount).sum());
		// access habit
		simulator.setAccessHabit(JsonUtil.toJsonString(habits));
		// status
		simulator.setStatus(UserSimulator.Status.NOT_STARTED.getCode());
		// device
		simulator.setDeviceId(nodeBo.getNodeId());
		simulator.setDeviceName(nodeBo.getNodeName());
		simulator.setDeviceIp(getIp(nodeBo.getIpConfig()));
		simulator.setIsEntity(nodeBo.getIsEntity());
		simulator.setSceneId(sceneId);
		simulator.setSceneName(sceneName);
		// set id, 用节点id作为仪表id
		simulator.setResourceId(nodeBo.getNodeId().replaceAll("-", ""));
		userInstanceList
				.forEach(instance -> instance.setUserSimulatorId(simulator.getResourceId()));
		return simulator;
	}

	private List<UserInstance> createUserInstanceList(List<UserInstanceAddBo> userModels,
			Map<String, UserModel> userModelMap, Map<String, String> userTypeMap) {
		List<UserInstance> instanceList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(userModels)) {
			return instanceList;
		}
		String userModelId;
		UserModel userModel;
		for (UserInstanceAddBo instanceAddBo : userModels) {
			userModelId = instanceAddBo.getUserModelId();
			userModel = userModelMap.get(userModelId);
			if (userModel == null) {
				continue;
			}
			// build user instance
			UserInstance instance = new UserInstance();
			instance.setUserName(userModel.getUserName());
			instance.setUserTypeId(userModel.getUserTypeId());
			instance.setUserType(
					userTypeMap.getOrDefault(instance.getUserTypeId(), StringUtils.EMPTY));
			BehaviorStatistic statistic = BsUtil.getBehaviorStatistic(userModel.getBehaviorList());
			instance.setStartTime(statistic.getStartTime());
			instance.setEndTime(statistic.getEndTime());
			instance.setAccessConfig(userModel.getAccessConfig());
			instance.setBehaviorList(userModel.getBehaviorList());
			instance.setBehaviorVariable(JsonUtil.toJsonString(instanceAddBo.getBehaviorConfig()));
			instance.setBehaviorCount(statistic.getBehaviorCount());
			instance.setAccessHabit(userModel.getAccessHabit());
			instance.setBandwidthLimited(userModel.getBandwidthLimited());
			instance.setBandwidth(userModel.getBandwidth());
			instance.setUserModelId(userModelId);

			instanceList.add(instance);
		}
		return instanceList;
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
		simulators.convert(simulator -> {
			BehaviorMeterBo behaviorMeterBo = new BehaviorMeterBo();
			behaviorMeterBo.setId(simulator.getDeviceId());
			behaviorMeterBo.setIp(simulator.getDeviceIp());
			behaviorMeterBo.setName("用户行为仪表" + "_" + simulator.getResourceId());
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
		detail.setAccessHabit(
				JsonUtil.toJsonObjectArray(userSimulator.getAccessHabit(), String.class));
		detail.setUserTypes(JsonUtil.toJsonObjectArray(userSimulator.getUserTypes(), String.class));
		// collect behavior ids and usernames
		List<UserInstance> instanceList = userInstanceMapper.getBatchBySimulatorId(simulatorId);
		Map<String, List<String>> userBehaviorMap = Maps.newHashMap();
		Map<String, String> userNameMap = Maps.newHashMap();
		Set<String> behaviorIdSet = Sets.newHashSet();
		for (UserInstance instance : instanceList) {
			List<String> behaviorIds = BsUtil.parseBehaviorList(instance.getBehaviorList()).stream()
					.map(BehaviorSimple::getId).collect(Collectors.toList());
			userBehaviorMap.put(instance.getUserModelId(), behaviorIds);
			userNameMap.put(instance.getUserModelId(), instance.getUserName());
			behaviorIdSet.addAll(behaviorIds);
		}

		detail.setBehaviors(Lists.newArrayList());
		// get behaviors
		if (!behaviorIdSet.isEmpty()) {
			List<LcBehaviorRspBo> lcBehaviorRspBos = lcService.getLcBehaviorListByIds(behaviorIdSet,
					false);
			List<LcBehavior> lcBehaviors = lcBehaviorRspBos.stream().map(this::createLcBehavior)
					.collect(Collectors.toList());
			Map<String, LcBehavior> behaviorMap = StreamApiUtils.toMap(lcBehaviors,
					LcBehavior::getId, instance -> instance);
			// 添加仪表包含的用户的行为
			for (UserInstance instance : instanceList) {
				String userModelId = instance.getUserModelId();
				List<String> behaviorIds = userBehaviorMap.get(userModelId);
				String userName = userNameMap.get(userModelId);
				for (String behaviorId : behaviorIds) {
					LcBehavior behavior = behaviorMap.get(behaviorId);
					if (behavior == null) {
						continue;
					}
					BehaviorInstance behaviorInstance = BeanUtils.deepCopy(behavior,
							BehaviorInstance.class);
					behaviorInstance.setUserName(userName);
					detail.getBehaviors().add(behaviorInstance);
				}
			}
		}

		return detail;
	}

	@Override
	public void deleteBySceneId(List<String> sceneIdList) {
		log.info("Delete simulators by scene_id: {}", sceneIdList);
		List<String> relatedSimulatorIds = userSimulatorMapper.getIdsBySceneIdList(sceneIdList);
		if (relatedSimulatorIds.isEmpty()) {
			return;
		}
		// 删除仪表，解除实体设备占用
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				userSimulatorMapper.deleteBySceneIdList(sceneIdList);
				userInstanceMapper.deleteBySimulatorIds(relatedSimulatorIds);
				deviceOccupationMapper.releaseBySceneIdList(sceneIdList);
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
		if (simulatorIds.size() == 1) {
			if (simulatorList.isEmpty()) {
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
			}
			if (simulatorList.get(0).getStatus().equals(UserSimulator.Status.RUNNING.getCode())) {
				throw new BusinessException(BsErrorCode.ALREADY_STARTED);
			}
		}
		// 不加事务
		for (UserSimulator simulator : simulatorList) {
			if (!simulator.getStatus().equals(UserSimulator.Status.RUNNING.getCode())) {
				start(simulator);
			}
		}
	}

	private void start(UserSimulator simulator) {
		List<UserInstance> userInstanceList = userInstanceMapper
				.getBatchBySimulatorId(simulator.getResourceId());
		// 启动定时任务前，先删除可能已存在的任务
		removeTimer(simulator.getResourceId());
		createTimerTask(simulator, userInstanceList);
		simulator.setStatus(UserSimulator.Status.RUNNING.getCode());
		userSimulatorMapper.updateById(simulator);
	}

	private void createTimerTask(UserSimulator simulator, List<UserInstance> userInstanceList) {
		timerMap.putIfAbsent(simulator.getResourceId(), Maps.newHashMap());
		// 一个用户一个timer
		for (UserInstance instance : userInstanceList) {
			// create timer
			BsTimer timer = new BsTimer();
			timer.setUserName(instance.getUserName());
			List<BehaviorSimple> behaviorList = BsUtil
					.parseBehaviorList(instance.getBehaviorList());
			// get params
			Map<String, String> behaviorVariableMap = BsUtil
					.getVariableMap(instance.getBehaviorVariable());

			for (BehaviorSimple behavior : behaviorList) {
				// 最后一个task index为0
				BsTimerTask task = new BsTimerTask(simulator, behavior.getId(), behaviorVariableMap
						.getOrDefault(behavior.getId(), BsConstants.EMPTY_LIST_JSON));
				// 循环执行，间隔一天，跳过当天在当前时间之前的行为
				timer.scheduleAtFixedRate(task, BsUtil.getNextDate(behavior.getStartTime()),
						BsConstants.MILLI_IN_A_DAY);
			}
			timerMap.get(simulator.getResourceId()).put(instance.getUserModelId(), timer);
		}
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

	private void stop(UserSimulator simulator) {
		// cancel timer
		removeTimer(simulator.getResourceId());
		// get latest behavior id
		String latestBehaviorId = simulator.getLastExeId();
		if (latestBehaviorId != null) {
			// stop latest executed behavior
			try {
				StopTaskReq req = new StopTaskReq();
				req.setChartId(new Long(latestBehaviorId));
				req.setIsUsingIp(!simulator.getIsEntity());
				if (Boolean.TRUE.equals(req.getIsUsingIp())) {
					req.setDeviceId(new Integer(BsConstants.DEFAULT_DEVICE_ID));
				} else {
					req.setDeviceId(new Integer(simulator.getDeviceId()));
				}
				req.setIp(simulator.getDeviceIp());
				lcService.stopBehavior(req);
			} catch (Exception e) {
				log.warn("Failed to stop behavior [{}] of simulator [{}]: {}", latestBehaviorId,
						simulator.getResourceId(), e);
			}
		}
		// update status
		simulator.setStatus(UserSimulator.Status.STOPPED.getCode());
		userSimulatorMapper.updateById(simulator);
	}

	@Override
	public void updateProcess(String simulatorId, String behaviorId) {
		UserSimulator simulator = getOrException(simulatorId);
		String dateNow = LocalDate.now().toString();
		if (dateNow.equals(simulator.getLastExeDate())) {
			simulator.setExeCount(simulator.getExeCount() + 1);
		} else {
			simulator.setLastExeDate(dateNow);
			simulator.setExeCount(1);
		}
		simulator.setLastExeId(behaviorId);
		userSimulatorMapper.updateById(simulator);
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

	@Override
	public MonitorDataRes getMonitorData(String resourceId, Integer duration) {
		// 统计8小时
		int hours = 8;
		if (null == duration || duration <= 0 || duration > 8) {
			duration = 8;
		}
		// get meter
		UserSimulator simulator = getOrException(resourceId);
		MonitorDataRes res = new MonitorDataRes();
		// get detail
		res.setDetail(new MonitorSimulatorDetail());
		res.getDetail().load(simulator);
		// task percentage

		if (LocalDate.now().toString().equals(simulator.getLastExeDate())) {
			res.calculateExecuted(simulator.getBehaviorCount(), simulator.getExeCount());
		} else {
			res.calculateExecuted(simulator.getBehaviorCount(), 0);
		}
		// get lc monitor data, 固定为8小时
		long timestamp = System.currentTimeMillis() - hours * BsConstants.MILLI_IN_A_HOUR;
		LcMonitorRsp lcMonitorRsp = lcService.getDeviceMonitorData(simulator.getDeviceId(),
				simulator.getDeviceIp(), simulator.getIsEntity(), timestamp);
		// res init time, 先获取实际的开始时间
		long startTimeStamp = timestamp + (hours - duration) * BsConstants.MILLI_IN_A_HOUR;
		res.init(startTimeStamp, duration);
		// bandwidth & packet
		res.getBandwidthAndPacket().setBandwidth(
				BsUtil.fill(lcMonitorRsp.getBandwidth(), BsConstants.MINUTE_HALF_HOUR, 0));
		res.getBandwidthAndPacket()
				.setPacket(BsUtil.fill(lcMonitorRsp.getPacket(), BsConstants.MINUTE_HALF_HOUR, 0));
		// connection
		res.getConnection().setConnection(
				BsUtil.fill(lcMonitorRsp.getConnection(), BsConstants.MINUTE_HALF_HOUR, 0));
		res.getConnection().setConnected(
				BsUtil.fill(lcMonitorRsp.getConnected(), BsConstants.MINUTE_HALF_HOUR, 0));
		// activity
		res.getActivity().setYStep(BsUtil.fill(lcMonitorRsp.getStep(), duration, 0));
		// session
		res.getSession().setYSession(BsUtil.fill(lcMonitorRsp.getSession(), duration * 2, 0));

		// report
		try {
			flowReportService.reportMonitorData(res);
		} catch (Exception e) {
			log.error("failed to report monitor data:", e);
		}
		return res;
	}

	@Override
	public List<String> getSceneIdList() {
		List<SceneIdName> sceneList = userSimulatorMapper.getSceneList();
		return sceneList.stream().map(SceneIdName::getSceneId).collect(Collectors.toList());
	}

	@Override
	public void modifySimulator(SimulatorModifyBo modifyBo) {
		String nodeId = modifyBo.getNodeId();
		UserSimulator simulator = getByNodeId(nodeId);

		log.info("modify simulator request: {}",
				JsonUtil.toJsonString(modifyBo, PropertyNamingStrategy.SnakeCase));
		List<String> userModelIds = Lists.newArrayList();
		// collect user model ids
		for (UserInstanceAddBo userInstanceAddBo : modifyBo.getUserModels()) {
			userModelIds.add(userInstanceAddBo.getUserModelId());
		}
		// get user models
		List<UserModel> userModelList = Lists.newArrayList();
		if (!userModelIds.isEmpty()) {
			userModelList = userModelMapper.selectBatchIds(userModelIds);
		}

		Map<String, UserModel> userModelMap = StreamApiUtils.toMap(userModelList,
				UserModel::getResourceId, userModel -> userModel);
		// get user types
		List<String> userTypeIds = userModelList.stream().map(UserModel::getUserTypeId)
				.collect(Collectors.toList());
		Map<String, String> userTypeMap = userTypeService.getIdNameMap(userTypeIds);
		// create user instances
		List<UserInstance> userInstanceList = createUserInstanceList(modifyBo.getUserModels(),
				userModelMap, userTypeMap);
		// create new Simulator
		NodeBo nodeBo = BeanUtils.copy(modifyBo, NodeBo.class);
		UserSimulator newSimulator = createSimulator(userInstanceList, nodeBo,
				simulator.getSceneId(), simulator.getSceneName());
		// deviceId, deviceName, isEntity, lastExeId, lastExeDate,
		// exeCount属性都是null
		// 继承运行状态
		newSimulator.setStatus(simulator.getStatus());
		newSimulator.setDeviceIp(simulator.getDeviceIp());

		// save
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// 只会更新不为null的字段
				userSimulatorMapper.updateById(newSimulator);
				// 更新user_instance
				userInstanceMapper.deleteBySimulatorId(newSimulator.getResourceId());
				if (!userInstanceList.isEmpty()) {
					userInstanceMapper.insertUserInstanceBatch(userInstanceList);
				}
				// 处理定时任务
				if (simulator.getStatus().equals(UserSimulator.Status.RUNNING.getCode())) {
					// 清除原有的定时任务，再重新生成此次请求包含的
					removeTimer(simulator.getResourceId());
					try {
						createTimerTask(newSimulator, userInstanceList);
					} catch (Exception e) {
						// 重新启动失败则状态设为停止
						newSimulator.setStatus(UserSimulator.Status.STOPPED.getCode());
						log.error("failed to start simulator after modification", e);
						userSimulatorMapper.updateById(newSimulator);
					}
				}
			}
		});
	}

	@Override
	public void updateBatch(SimulatorAddBo updateBo) {
		log.info("update simulator request: {}",
				JsonUtil.toJsonString(updateBo, PropertyNamingStrategy.SnakeCase));
		String sceneId = updateBo.getSceneId();
		// 查询是否有和该场景相关的仪表
		List<UserSimulator> simulatorList = userSimulatorMapper.selectBatchBySceneId(sceneId);
		Map<String, UserSimulator> oldSimulatorMap = StreamApiUtils.toMap(simulatorList,
				UserSimulator::getResourceId, simulator -> simulator);
		List<String> oldSimulatorIds = simulatorList.stream().map(UserSimulator::getResourceId)
				.collect(Collectors.toList());
		// 创建新的仪表
		SimulatorAddResult result = createSimulatorBatch(updateBo);
		// 继承旧仪表的执行状态
		for (UserSimulator simulator : result.getSimulatorList()) {
			if (oldSimulatorMap.containsKey(simulator.getResourceId())) {
				UserSimulator old = oldSimulatorMap.get(simulator.getResourceId());
				simulator.setStatus(old.getStatus());
				simulator.setLastExeId(old.getLastExeId());
				simulator.setLastExeDate(old.getLastExeDate());
				simulator.setExeCount(old.getExeCount());
			}
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// 删除旧的仪表和定时任务
				deleteSimulatorByIds(oldSimulatorIds);
				// save simulators
				for (UserSimulator simulator : result.getSimulatorList()) {
					userSimulatorMapper.insert(simulator);
					// 创建定时任务
					if (simulator.getStatus().equals(UserSimulator.Status.RUNNING.getCode())) {
						try {
							createTimerTask(simulator,
									result.getUserInstanceMap().get(simulator.getResourceId()));
						} catch (Exception e) {
							// 重新启动失败则状态设为停止
							simulator.setStatus(UserSimulator.Status.STOPPED.getCode());
							log.error("failed to start simulator {} after modification: {}",
									simulator.getResourceId(), e);
							userSimulatorMapper.updateById(simulator);
						}
					}
				}
				if (!result.getUserInstanceList().isEmpty()) {
					userInstanceMapper.insertUserInstanceBatch(result.getUserInstanceList());
				}
			}
		});
	}

	@Override
	public List<SimulatorTimer> showTimers() {
		List<SimulatorTimer> res = Lists.newArrayList();
		timerMap.forEach((simulatorId, timers) -> {
			SimulatorTimer simulatorTimer = new SimulatorTimer(simulatorId);
			timers.forEach((userModelId, timer) -> simulatorTimer.addUser(userModelId,
					timer.getUserName()));
			res.add(simulatorTimer);
		});
		return res;
	}

	@Override
	public Integer getBandwidthSum() {
		Integer bytePerSecond = 0;
		try {
			bytePerSecond = lcService.getBandwidthSum();
		} catch (RemoteServiceException e) {
			log.error("remote error: ", e);
		} catch (Exception e1) {
			log.error("failed to get bandwidth sum: ", e1);
		}
		// 单位 mbps
		return BsUtil.divide(bytePerSecond, 128 * 1024);
	}

	@Override
	public void deleteByNodeId(List<String> nodeIds) {
		// List<String> simulatorIds = nodeIds.stream().map(nodeId ->
		// nodeId.replaceAll("-", ""))
		// .collect(Collectors.toList());
		List<String> simulatorIds = userSimulatorMapper.getIdsByDeviceIds(nodeIds);
		if (!simulatorIds.isEmpty()) {
			deleteSimulatorByIds(simulatorIds);
		}
	}

	private void deleteSimulatorByIds(Collection<String> simulatorIds) {
		if (CollectionUtils.isEmpty(simulatorIds)) {
			return;
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				userSimulatorMapper.deleteBatchIds(simulatorIds);
				userInstanceMapper.deleteBySimulatorIds(simulatorIds);
				// 删除定时任务
				batchRemoveTimer(simulatorIds);
			}
		});
	}

	private UserSimulator getByNodeId(String nodeId) {
		if (StringUtils.isBlank(nodeId)) {
			throw new BusinessException(BsErrorCode.NODE_ID_MISSING);
		}
		UserSimulator simulator = userSimulatorMapper.selectByNodeId(nodeId);
		if (simulator == null) {
			throw new BusinessException(BsErrorCode.SIMULATOR_NOT_FOUND);
		}
		return simulator;
	}

	private UserSimulator getOrNull(String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			return null;
		}
		return userSimulatorMapper.selectById(resourceId);
	}

	private void batchRemoveTimer(Collection<String> simulatorIds) {
		for (String simulatorId : simulatorIds) {
			removeTimer(simulatorId);
		}
	}

	private void removeTimer(String simulatorId) {
		Map<String, BsTimer> timers = timerMap.get(simulatorId);
		if (timers != null) {
			for (BsTimer timer : timers.values()) {
				timer.cancel();
			}
		}
		timerMap.remove(simulatorId);
	}

	/**
	 * 获取ip
	 * @param ipBoList
	 * @return
	 */
	private String getIp(List<IpBo> ipBoList) {
		if (CollectionUtils.isEmpty(ipBoList)) {
			return CommonConstant.EMPTY_STRING;
		}
		if (ipBoList.size() == 1) {
			return ipBoList.get(0).getIp();
		}
		if (StringUtils.isBlank(EXTERNAL_IP_SEGMENT)) {
			throw new BusinessException(BsErrorCode.SEGMENT_NOT_CONFIGURED);
		}
		String ip;
		for (IpBo ipBo : ipBoList) {
			ip = ipBo.getIp();
			if (BsUtil.isInRange(ip, EXTERNAL_IP_SEGMENT)) {
				return ip;
			}
		}
		log.error("No valid external ip found.");
		return CommonConstant.EMPTY_STRING;
	}

	private void recoverTimerTask() {
		// 获取正在运行的仪表
		List<UserSimulator> activeSimulators = userSimulatorMapper
				.selectBatchByStatus(UserSimulator.Status.RUNNING.getCode());
		if (activeSimulators.isEmpty()) {
			return;
		}
		// 获取仪表对应的用户
		List<String> simulatorIds = activeSimulators.stream().map(UserSimulator::getResourceId)
				.collect(Collectors.toList());
		List<UserInstance> userInstanceList = userInstanceMapper
				.getBatchBySimulatorIds(simulatorIds);
		if (userInstanceList.isEmpty()) {
			return;
		}
		Map<String, List<UserInstance>> instanceMap = Maps.newHashMap();
		for (UserInstance instance : userInstanceList) {
			String simulatorId = instance.getUserSimulatorId();
			instanceMap.putIfAbsent(simulatorId, Lists.newArrayList());
			instanceMap.get(simulatorId).add(instance);
		}
		// 创建定时任务
		for (UserSimulator simulator : activeSimulators) {

			createTimerTask(simulator,
					instanceMap.getOrDefault(simulator.getResourceId(), Lists.newArrayList()));
		}
	}
}
