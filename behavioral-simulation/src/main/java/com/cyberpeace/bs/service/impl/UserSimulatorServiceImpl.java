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
import com.cyberpeace.bs.service.bo.lc.*;
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

/**
 * 用户仪表Service实现
 *
 * @author SN
 * @date 2023/05/08
 */
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
		// 获取外部ip段，用于从多网卡节点的多个ip中筛选出可与ocr服务通信的ip
		EXTERNAL_IP_SEGMENT = SystemPropUtil.getProp("EXTERNAL_IP_SEGMENT");
		try {
			// 重启程序后恢复正在运行的仪表的定时任务
			recoverTimerTask();
		} catch (Exception e) {
			log.error("failed to recover timer task");
		}
	}

	@Override
	public void batchCreate(SimulatorAddBo addBo) {
		// 日志记录仪表创建请求
		String reqString = JsonUtil.toJsonString(addBo, PropertyNamingStrategy.SnakeCase);
		log.info("create simulator request: {}", reqString);
		try {
			// 上报请求数据
			flowReportService.reportCreation(reqString);
		} catch (Exception e) {
			log.error("failed to report creation: ", e);
		}
		// 创建仪表实体
		SimulatorAddResult result = createSimulatorBatch(addBo);
		// save
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// 保存仪表实体数据
				for (UserSimulator simulator : result.getSimulatorList()) {
					userSimulatorMapper.insert(simulator);
				}
				// 保存用户实例数据
				if (!result.getUserInstanceList().isEmpty()) {
					userInstanceMapper.insertUserInstanceBatch(result.getUserInstanceList());
				}
			}
		});

	}

	/**
	 * 批量创建仪表
	 *
	 * @param addBo 新增请求体
	 * @return {@code SimulatorAddResult}
	 */
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

	/**
	 * 创建仪表
	 *
	 * @param userInstanceList 用户实例列表
	 * @param nodeBo           节点数据
	 * @param sceneId          场景id
	 * @param sceneName        场景名称
	 * @return {@code UserSimulator}
	 */
	private UserSimulator createSimulator(List<UserInstance> userInstanceList, NodeBo nodeBo,
			String sceneId, String sceneName) {
		UserSimulator simulator = new UserSimulator();
		// 操作系统类型
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
		simulator.setYwId(nodeBo.getYwId());
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

	/**
	 * 创建用户实例列表
	 *
	 * @param userModels   用户模型
	 * @param userModelMap 用户模型映射map
	 * @param userTypeMap  用户类型map
	 * @return {@code List<UserInstance>}
	 */
	private List<UserInstance> createUserInstanceList(List<UserInstanceAddBo> userModels,
			Map<String, UserModel> userModelMap, Map<String, String> userTypeMap) {
		List<UserInstance> instanceList = Lists.newArrayList();
		// 判断用户模型列表是否为空
		if (CollectionUtils.isEmpty(userModels)) {
			return instanceList;
		}
		String userModelId;
		UserModel userModel;
		// 循环用户模型，生成用户实例
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
		// 统计仪表数量
		return userSimulatorMapper.countAll();
	}

	@Override
	public Integer countActiveSimulators() {
		// 统计状态为运行中的仪表数量
		return userSimulatorMapper.countByStatus(UserSimulator.Status.RUNNING.getCode());
	}

	@Override
	public ListRsp<BehaviorMeterBo> pageBehaviorMeters(MeterPageReq pageReq) {
		// 生成分页器
		Page page = pageReq.toPageParam();
		String sceneId = pageReq.getScene_id();
		// 查询指定场景中的行为仪表
		IPage<UserSimulator> simulators = userSimulatorMapper.page(page, sceneId);
		simulators.convert(simulator -> {
			BehaviorMeterBo behaviorMeterBo = new BehaviorMeterBo();
			behaviorMeterBo.setId(simulator.getDeviceId());
			behaviorMeterBo.setIp(simulator.getDeviceIp());
			// 设置仪表名称为统一格式
			behaviorMeterBo.setName("用户行为仪表" + "_" + simulator.getResourceId());
			behaviorMeterBo.setResourceId(simulator.getResourceId());
			// 场景名称
			behaviorMeterBo.setSceneName(simulator.getSceneName());
			behaviorMeterBo.setStatus(simulator.getStatus());
			return behaviorMeterBo;
		});
		return PageUtil.getListResult(simulators);
	}

	@Override
	public SimulatorDetail getDetail(String simulatorId) {
		// 查询仪表
		UserSimulator userSimulator = getOrException(simulatorId);
		SimulatorDetail detail = BeanUtils.copy(userSimulator, SimulatorDetail.class);
		// 设置访问习惯
		detail.setAccessHabit(
				JsonUtil.toJsonObjectArray(userSimulator.getAccessHabit(), String.class));
		// 设置用户类型列表
		detail.setUserTypes(JsonUtil.toJsonObjectArray(userSimulator.getUserTypes(), String.class));
		// collect behavior ids and usernames
		List<UserInstance> instanceList = userInstanceMapper.getBatchBySimulatorId(simulatorId);
		Map<String, List<String>> userBehaviorMap = Maps.newHashMap();
		Map<String, String> userNameMap = Maps.newHashMap();
		Set<String> behaviorIdSet = Sets.newHashSet();
		for (UserInstance instance : instanceList) {
			// 收集行为id
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
					// 行为不存在，跳过
					if (behavior == null) {
						continue;
					}
					BehaviorInstance behaviorInstance = BeanUtils.deepCopy(behavior,
							BehaviorInstance.class);
					behaviorInstance.setUserName(userName);
					behaviorInstance.setUserModelId(userModelId);
					detail.getBehaviors().add(behaviorInstance);
				}
			}
		}

		return detail;
	}

	@Override
	public void deleteBySceneId(List<String> sceneIdList) {
		// 日志记录配管的请求
		log.info("Delete simulators by scene_id: {}", sceneIdList);
		// 查询关联仪表
		List<String> relatedSimulatorIds = userSimulatorMapper.getIdsBySceneIdList(sceneIdList);
		// 无关联仪表，直接返回
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
		// 查询当前存在仪表所保存的关联场景信息
		return userSimulatorMapper.getSceneList();
	}

	/**
	 * 创建远程行为
	 *
	 * @param rspBo 响应数据
	 * @return {@code LcBehavior}
	 */
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
		if (simulator.getStatus().equals(UserSimulator.Status.RUNNING.getCode())) {
			throw new BusinessException(BsErrorCode.ALREADY_STARTED);
		}
		start(simulator);
	}

	@Override
	public void batchStartSimulator(List<String> simulatorIds) {
		// 查询仪表列表
		List<UserSimulator> simulatorList = userSimulatorMapper.selectBatchIds(simulatorIds);
		// 只选中一个仪表时，按对单个仪表操作的方式抛错
		if (simulatorIds.size() == 1) {
			if (simulatorList.isEmpty()) {
				// 未指定仪表id
				throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
			}
			// 仪表已在运行
			if (simulatorList.get(0).getStatus().equals(UserSimulator.Status.RUNNING.getCode())) {
				throw new BusinessException(BsErrorCode.ALREADY_STARTED);
			}
		}
		// 不加事务
		for (UserSimulator simulator : simulatorList) {
			// 依次启动选中的仪表
			if (!simulator.getStatus().equals(UserSimulator.Status.RUNNING.getCode())) {
				start(simulator);
			}
		}
	}

	/**
	 * 启动行为模拟仪表
	 *
	 * @param simulator 仪表
	 */
	private void start(UserSimulator simulator) {
		// 查询用户实例
		List<UserInstance> userInstanceList = userInstanceMapper
				.getBatchBySimulatorId(simulator.getResourceId());
		// 启动定时任务前，先删除可能已存在的任务
		removeTimer(simulator.getResourceId());
		// 创建定时任务
		createTimerTask(simulator, userInstanceList);
		// 更新仪表状态
		simulator.setStatus(UserSimulator.Status.RUNNING.getCode());
		userSimulatorMapper.updateById(simulator);
	}

	/**
	 * 创建定时任务
	 *
	 * @param simulator        仪表
	 * @param userInstanceList 用户实例列表
	 */
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
		// 查询仪表
		UserSimulator simulator = getOrException(resourceId);
		// 停止仪表运行
		stop(simulator);
	}

	@Override
	public void batchStopSimulator(List<String> simulatorIds) {
		// 查询指定的仪表
		List<UserSimulator> simulatorList = userSimulatorMapper.selectBatchIds(simulatorIds);
		// 不加事务
		for (UserSimulator simulator : simulatorList) {
			if (simulator.getStatus().equals(UserSimulator.Status.RUNNING.getCode())) {
				// 依次关闭仪表
				stop(simulator);
			}
		}
	}

	/**
	 * 停止仪表运行
	 *
	 * @param simulator 仪表
	 */
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
				// 根据是否使用ip识别设备，设置不同的设备id
				if (Boolean.TRUE.equals(req.getIsUsingIp())) {
					req.setDeviceId(new Integer(simulator.getYwId()));
				} else {
					req.setDeviceId(
							new Integer(lcService.getDeviceIdByUuid(simulator.getDeviceId())));
				}
				// req.setIp(simulator.getDeviceIp());
				lcService.stopBehavior(req);
			} catch (Exception e) {
				// 出错时日志记录错误
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
		// 查询仪表
		UserSimulator simulator = getOrException(simulatorId);
		// 当前日期
		String dateNow = LocalDate.now().toString();
		// 如果不是当天执行的第一个任务，则执行数加1
		if (dateNow.equals(simulator.getLastExeDate())) {
			simulator.setExeCount(simulator.getExeCount() + 1);
		} else {
			// 如果是当天执行的第一个任务，则将最后执行日期设置为今天
			simulator.setLastExeDate(dateNow);
			// 重置执行计数
			simulator.setExeCount(1);
		}
		// 设置最后执行行为id
		simulator.setLastExeId(behaviorId);
		// 更新仪表
		userSimulatorMapper.updateById(simulator);
	}

	@Override
	public UserSimulator getOrException(String resourceId) {
		// id不存在或为空字符串
		if (StringUtils.isBlank(resourceId)) {
			throw new BusinessException(BsErrorCode.RESOURCE_ID_MISSING);
		}
		// 查询仪表
		UserSimulator simulator = userSimulatorMapper.selectById(resourceId);
		// 查询不到数据，报错
		if (simulator == null) {
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}
		// 返回仪表数据
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
		// 获取关联场景列表
		List<SceneIdName> sceneList = userSimulatorMapper.getSceneList();
		// 解析id并收集
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
		if (StringUtils.isBlank(newSimulator.getDeviceIp())) {
			newSimulator.setDeviceIp(simulator.getDeviceIp());
		}
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
				// 如果用户实例存在，则插入数据库
				if (!result.getUserInstanceList().isEmpty()) {
					userInstanceMapper.insertUserInstanceBatch(result.getUserInstanceList());
				}
			}
		});
	}

	@Override
	public List<SimulatorTimer> showTimers() {
		List<SimulatorTimer> res = Lists.newArrayList();
		// 遍历定时任务执行器集合
		timerMap.forEach((simulatorId, timers) -> {
			SimulatorTimer simulatorTimer = new SimulatorTimer(simulatorId);
			// 遍历执行器中用户模型
			timers.forEach((userModelId, timer) -> simulatorTimer.addUser(userModelId,
					timer.getUserName()));
			res.add(simulatorTimer);
		});
		return res;
	}

	@Override
	public Integer getBandwidthSum() {
		// 远程返回数据单位为bps
		Integer bytePerSecond = 0;
		// 尝试获取远程数据
		try {
			bytePerSecond = lcService.getBandwidthSum();
		} catch (RemoteServiceException e) {
			// 远程错误
			log.error("remote error: ", e);
		} catch (Exception e1) {
			// 其他错误
			log.error("failed to get bandwidth sum: ", e1);
		}
		// 单位转为mbps
		return BsUtil.divide(bytePerSecond, 128 * 1024);
	}

	@Override
	public void deleteByNodeId(List<String> nodeIds) {
		// 查询节点id关联的仪表的id
		List<String> simulatorIds = userSimulatorMapper.getIdsByDeviceIds(nodeIds);
		// 删除关联仪表
		if (!simulatorIds.isEmpty()) {
			deleteSimulatorByIds(simulatorIds);
		}
	}

	@Override
	public void startSingle(String simulatorId, String userModelId, String behaviorId) {
		// 查询仪表
		UserSimulator simulator = getOrException(simulatorId);
		// get user instance
		UserInstance instance = userInstanceMapper.getBySimulatorIdAndUserModelId(simulatorId, userModelId);
		// 组装任务下发请求体
		ReleaseTaskReq taskReq = new ReleaseTaskReq();
		// 区分实体设备和虚拟设备
		if (Boolean.TRUE.equals(simulator.getIsEntity())) {
			taskReq.setDeviceId(simulator.getYwId());
		} else {
			taskReq.setDeviceId(lcService.getDeviceIdByUuid(simulator.getDeviceId()));
		}
		taskReq.setIp(simulator.getDeviceIp());
		taskReq.setFlowChart(behaviorId);
		// 设置行为变量
		Map<String, String> behaviorVariableMap = BsUtil
				.getVariableMap(instance.getBehaviorVariable());
		String variableJson = behaviorVariableMap.get(behaviorId);
		if (StringUtils.isNotBlank(variableJson)) {
			List<VariableBo> variableBos = JsonUtil.toJsonObjectArray(variableJson, VariableBo.class);
			variableBos.forEach(
					variableBo -> taskReq.addVariable(variableBo.getName(), variableBo.getValue()));
		}
		// 下发行为任务
		lcService.startBehavior(taskReq);
	}

	@Override
	public OnlineStatusRsp getOnlineStatus(List<String> simulatorIds) {
		List<UserSimulator> simulatorList = this.userSimulatorMapper.selectBatchIds(simulatorIds);
		OnlineStatusRsp rsp = new OnlineStatusRsp();
		for (UserSimulator simulator : simulatorList) {
			LcDevice device = this.getLcDevice(simulator);
			if (null == device.getStatus()
					|| BsConstants.LcDeviceStatus.OFFLINE.equals(device.getStatus())) {
				rsp.getOffline().add(simulator.getResourceId());
			} else {
				rsp.getOnline().add(simulator.getResourceId());
			}
		}
		return rsp;
	}

	private void deleteSimulatorByIds(Collection<String> simulatorIds) {
		if (CollectionUtils.isEmpty(simulatorIds)) {
			return;
		}
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				// 删除仪表
				userSimulatorMapper.deleteBatchIds(simulatorIds);
				// 删除用户实例
				userInstanceMapper.deleteBySimulatorIds(simulatorIds);
				// 删除定时任务
				batchRemoveTimer(simulatorIds);
			}
		});
	}

	/**
	 * 通过节点id查询仪表
	 *
	 * @param nodeId 节点id
	 * @return {@code UserSimulator}
	 */
	private UserSimulator getByNodeId(String nodeId) {
		// 非法的节点id
		if (StringUtils.isBlank(nodeId)) {
			throw new BusinessException(BsErrorCode.NODE_ID_MISSING);
		}
		// 查询
		UserSimulator simulator = userSimulatorMapper.selectByNodeId(nodeId);
		// 不存在节点相关仪表
		if (simulator == null) {
			throw new BusinessException(BsErrorCode.SIMULATOR_NOT_FOUND);
		}
		// 返回数据
		return simulator;
	}

	/**
	 * 批量删除定时任务执行器
	 *
	 * @param simulatorIds 仪表id列表
	 */
	private void batchRemoveTimer(Collection<String> simulatorIds) {
		for (String simulatorId : simulatorIds) {
			// 删除
			removeTimer(simulatorId);
		}
	}

	/**
	 * 删除定时任务执行器
	 *
	 * @param simulatorId 仪表id
	 */
	private void removeTimer(String simulatorId) {
		// 获取指定的执行器
		Map<String, BsTimer> timers = timerMap.get(simulatorId);
		if (timers != null) {
			for (BsTimer timer : timers.values()) {
				// 取消全部定时任务
				timer.cancel();
			}
		}
		// 从map中删除执行器
		timerMap.remove(simulatorId);
	}

	/**
	 * 获取ip
	 * @param ipBoList ip列表
	 * @return
	 */
	private String getIp(List<IpBo> ipBoList) {
		// 未提供ip
		if (CollectionUtils.isEmpty(ipBoList)) {
			return CommonConstant.EMPTY_STRING;
		}
		// 只提供一个ip的情况
		if (ipBoList.size() == 1) {
			return ipBoList.get(0).getIp();
		}
		// 必须配置外部网段
		if (StringUtils.isBlank(EXTERNAL_IP_SEGMENT)) {
			throw new BusinessException(BsErrorCode.SEGMENT_NOT_CONFIGURED);
		}
		String ip;
		// 循环匹配ip是否在指定网段
		for (IpBo ipBo : ipBoList) {
			ip = ipBo.getIp();
			if (BsUtil.isInRange(ip, EXTERNAL_IP_SEGMENT)) {
				// 返回匹配到的第一个
				return ip;
			}
		}
		log.error("No valid external ip found.");
		// 未匹配到有效ip，返回空白字符串
		return CommonConstant.EMPTY_STRING;
	}

	/**
	 * 恢复定时任务
	 */
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

	/**
	 * 获取远程设备,返回有可能为空对象
	 *
	 * @param simulator 仪表
	 * @return {@code LcDevice}
	 */
	private LcDevice getLcDevice(UserSimulator simulator) {
		if (Boolean.TRUE.equals(simulator.getIsEntity())) {
			return lcService.getDeviceOrEmptyById(simulator.getYwId());
		} else {
			return lcService.getDeviceOrEmptyByUuid(simulator.getDeviceId());
		}
	}
}
