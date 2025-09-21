package com.cyberpeace.cr.teamtrain.service.impl;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.DateTimeFormatUtil;
import com.cyberpeace.cr.teamtrain.controller.rsp.TrainQueueRsp;
import com.cyberpeace.cr.teamtrain.dao.entity.TrainQueue;
import com.cyberpeace.cr.teamtrain.dao.mapper.TrainQueueMapper;
import com.cyberpeace.integratation.service.RemoteSceneService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.context.role.CrRoleNodesData;
import com.cyberpeace.common.context.scene.ElementFlow;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.enums.SceneStatusEnum;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrSceneService;
import com.cyberpeace.cr.crbase.service.SceneFilterService;
import com.cyberpeace.cr.crbase.service.TrainRecordService;
import com.cyberpeace.cr.crbase.service.context.scene.Flow;
import com.cyberpeace.cr.crbase.service.context.scene.TeamSceneViewContext;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.service.TeamCommonDeviceService;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cr.teamtrain.service.TeamSceneService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.entity.LocalSceneMeta;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.scene.*;
import com.cyberpeace.integratation.model.scene.SceneStartContext;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * 团队场景Service实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Slf4j
@Service
public class TeamSceneServiceImpl implements TeamSceneService {

	private static final ReentrantLock DEVICE_QUEUE_LOCK = new ReentrantLock();

	private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newScheduledThreadPool(1);


	/**
	 * 团队房间Service
	 */
	@Autowired
	private TeamRoomService teamRoomService;

	/**
	 * 当地场景Service
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 靶场岗位Service
	 */
	@Autowired
	private CrRoleService crRoleService;

	/**
	 * 场景过滤器Service
	 */
	@Autowired
	private SceneFilterService sceneFilterService;

	/**
	 * 训练记录Service
	 */
	@Autowired
	private TrainRecordService trainRecordService;

	/**
	 * 团队常用装置Service
	 */
	@Autowired
	private TeamCommonDeviceService teamCommonDeviceService;

	/**
	 * 靶场场景Service
	 */
	@Autowired
	private CrSceneService crSceneService;

	@Autowired
	private TrainQueueMapper trainQueueMapper;

	@Autowired
	private RemoteSceneService remoteSceneService;


	@Override
	public List<TrainQueueRsp> getTrainQueueList(String roomId) {
		List<String> deviceIds = getEntityDeviceIds(roomId);
		return getTrainQueueListByDeviceIds(deviceIds);
	}

	@Override
	public void cancelTrainQueue(String roomId) {
		tryDeviceLock();
		try {
			trainQueueMapper.update(null, new LambdaUpdateWrapper<>(TrainQueue.class)
					.set(TrainQueue::getStatus, TrainQueue.Status.CANCELED)
					.eq(TrainQueue::getRoomId, roomId));
		} finally {
			DEVICE_QUEUE_LOCK.unlock();
		}
	}

	private List<String> getEntityDeviceIds(String roomId) {
		TeamRoom room = teamRoomService.getById(roomId);
		String topoId = room.getCopyedTopoId();
		if (StringUtils.isBlank(topoId)) {
			throw new BusinessException(ErrorCode.DIRECT_MSG_EXCEPTION, "当前训练数据异常，关联的拓扑数据无法获取");
		}
		return remoteSceneService.getSceneEntityDeviceIds(topoId);
	}

	private List<TrainQueueRsp> getTrainQueueListByDeviceIds(List<String> deviceIds) {
		if (CollectionUtils.isEmpty(deviceIds)) {
			return Collections.emptyList();
		} else {
			List<TrainQueue> trainQueues = trainQueueMapper.selectList(new LambdaQueryWrapper<>(TrainQueue.class)
					.in(TrainQueue::getStatus, TrainQueue.Status.RUNNING, TrainQueue.Status.WAITING)
					.in(TrainQueue::getEntityDeviceId, deviceIds).orderByAsc(TrainQueue::getApplyTime));
			if (CollectionUtils.isEmpty(trainQueues)) {
				return Collections.emptyList();
			} else {
				Map<String, List<TrainQueue>> trainQueueMap = trainQueues.stream().collect(Collectors.groupingBy(TrainQueue::getRoomId));
				List<TrainQueueRsp> trainQueueRsps = new ArrayList<>();
				trainQueueMap.forEach((roomKey, trainQueuesValue) -> {
					TrainQueueRsp trainQueueRsp = new TrainQueueRsp();
					trainQueueRsp.setRoomId(roomKey);
					trainQueueRsp.setRoomName(trainQueuesValue.get(0).getRoomName());
					trainQueueRsp.setStatus(trainQueuesValue.get(0).getStatus());
					trainQueueRsp.setEntityDeviceIds(trainQueuesValue.stream().map(TrainQueue::getEntityDeviceId).collect(Collectors.toList()));
					trainQueueRsp.setApplyTime(DateTimeFormatUtil.DATE_FORMATTER.format(trainQueuesValue.get(0).getApplyTime()));

					trainQueueRsps.add(trainQueueRsp);
				});

				return trainQueueRsps;
			}
		}
	}


	/**
	 * 启动场景
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code String }
	 */
	@Override
	public String startScene(String roomId, String userId) {
		// 1、权限校验，学员是不可以启动场景的
		teamRoomService.checkTeacherPermission(roomId, userId);

		TeamRoom room = teamRoomService.getById(roomId);

		// 2、如果已经有启动的场景，不能重复启动; 如果有脏数据，清除脏数据
		LocalScene localSceneExisted = localSceneService.getByBusinessInstanceId(roomId);
		if (localSceneExisted != null) {
			if (StringUtils.isBlank(localSceneExisted.getMpSceneId())) {
				clearLocal(room);
			} else {
				log.error("MpSceneId is null,MpSceneId =[{}]", localSceneExisted.getMpSceneId());
				throw new BusinessException(CrBusinessErrorCode.SCENE_START_REPEAT);
			}
		}


		tryDeviceLock();
		try {
			// 检查是否存在实体设备，以及实体设备排队
			List<String> deviceIds = getEntityDeviceIds(roomId);
			List<TrainQueueRsp> trainQueueRsps = getTrainQueueListByDeviceIds(deviceIds);
			if (!CollectionUtils.isEmpty(trainQueueRsps)) {
				if (trainQueueRsps.get(0).getRoomId().equals(roomId) && trainQueueRsps.get(0).getStatus() == TrainQueue.Status.RUNNING) {
					log.info("当前训练排队获取到实体资源，可以开始启动");
				} else {
					List<TrainQueueRsp> currentRoomTrainQueueRsps = trainQueueRsps.stream().filter(trainQueueRsp -> trainQueueRsp.getRoomId().equals(roomId))
							.collect(Collectors.toList());

					// 当前队列中有当前room
					if (!CollectionUtils.isEmpty(currentRoomTrainQueueRsps)) {
						//重复排队，异常
						if (currentRoomTrainQueueRsps.size() > 1) {
							throw new BusinessException(ErrorCode.DIRECT_MSG_EXCEPTION, "训练启动队列数据异常，当前序列重复排队");
						} else {
							// 在等待了，接着等
							if (currentRoomTrainQueueRsps.get(0).getStatus() == TrainQueue.Status.WAITING) {
								throw new BusinessException(ErrorCode.DIRECT_MSG_EXCEPTION, "当前训练所需实体设备正在被占用中，系统会在资源释放后再启动当前训练");
							} else {
								// 处于运行状态，但是不是第一个。scan有问题
								throw new BusinessException(ErrorCode.DIRECT_MSG_EXCEPTION, "当前训练队列状态异常，请联系管理员处理");
							}
						}
					} else {
						//队列中没有当前room，第一次启动，资源被占用，进行排队
						for (String deviceId : deviceIds) {
							TrainQueue trainQueue = new TrainQueue();
							trainQueue.setApplyTime(LocalDateTime.now());
							trainQueue.setStatus(TrainQueue.Status.WAITING);
							trainQueue.setRoomId(roomId);
							trainQueue.setOperatorUserId(userId);
							trainQueue.setCreateTime(new Date());
							trainQueue.setUpdateTime(new Date());
							trainQueue.setOperatorUserId(userId);
							trainQueue.setRoomName(room.getName());
							trainQueue.setEntityDeviceId(deviceId);

							trainQueueMapper.insert(trainQueue);
						}
						throw new BusinessException(ErrorCode.DIRECT_MSG_EXCEPTION, "当前训练所需实体设备正在被占用中，已加入启动等待队列中，资源释放后会自动启动");
					}

				}

			} else {
				//插入运行中
				for (String deviceId : deviceIds) {
					TrainQueue trainQueue = new TrainQueue();
					trainQueue.setApplyTime(LocalDateTime.now());
					trainQueue.setStatus(TrainQueue.Status.RUNNING);
					trainQueue.setRoomId(roomId);
					trainQueue.setOperatorUserId(userId);
					trainQueue.setCreateTime(new Date());
					trainQueue.setUpdateTime(new Date());
					trainQueue.setOperatorUserId(userId);
					trainQueue.setRoomName(room.getName());
					trainQueue.setEntityDeviceId(deviceId);

					trainQueueMapper.insert(trainQueue);
				}
			}
		} finally {
			DEVICE_QUEUE_LOCK.unlock();
		}


		try {


			// 3、构建本地scene对象
			LocalScene localScene = createLocalScene(BusinessCode.TEAM_TRAINING.getCode(),
					room, room.getCopyedTopoId());
			String sceneId = localSceneService.startScene(localScene);

			try {
				// 4、启动mp场景
				// 5、更新本地数据库数据
				transactionTemplate.execute(new TransactionCallbackWithoutResult() {

					@Override
					protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
						// 更新表数据
						localScene.setMpSceneId(sceneId);
						localScene.setSceneStatus(SceneStatusEnum.START.getCode());
						localScene.setStartTime(new Date());
						room.setLocalSceneId(localScene.getResourceId());

						// 执行表更新
						localSceneService.save(localScene);
						teamRoomService.updateById(room);
					}
				});
				// 5.1 本地保存数据错误，删除已启动的远程场景
			} catch (Exception e) {
				localSceneService.stopScene(localScene);
				log.error("Failed to save local data:", e);
				throw new BusinessException(CrBusinessErrorCode.SAVE_LOCAL_DATA_FAILURE);
			}

			return sceneId;
		} catch (Exception e) {
			log.error("Failed to start scene:", e);
			tryDeviceLock();
			try {
				trainQueueMapper.update(null, new LambdaUpdateWrapper<TrainQueue>().set(TrainQueue::getStatus, TrainQueue.Status.ERROR)
						.eq(TrainQueue::getRoomId, roomId));
			} finally {
				DEVICE_QUEUE_LOCK.unlock();
			}
			throw new BusinessException(CrBusinessErrorCode.SAVE_LOCAL_DATA_FAILURE);
		}
	}

	private void tryDeviceLock() {
		try {
			boolean lockFlag = DEVICE_QUEUE_LOCK.tryLock(60, TimeUnit.SECONDS);
			if (!lockFlag) {
				log.error("获取锁失败");
				throw new BusinessException(ErrorCode.DIRECT_MSG_EXCEPTION, "当前训练启动频繁，请稍后再试");
			}
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
			throw new BusinessException(ErrorCode.DIRECT_MSG_EXCEPTION, "当前训练启动频繁，请稍后再试");
		}
	}


	/**
	 * 按照deviceId获取当前所有队列
	 *
	 * 扫描所有的数据 wait和running记录。对于每个room来说，还需要有device到room的映射。
	 *
	 *
	 * 判断范围，每个device， apply_time最新的room, 做排序。判断当前room是否可以启动，
	 */
	private void scanTrainQueueAndStartTrain() {
		List<TrainQueue> trainQueues = trainQueueMapper.selectList(new LambdaQueryWrapper<>(TrainQueue.class)
				.in(TrainQueue::getStatus, TrainQueue.Status.RUNNING, TrainQueue.Status.WAITING)
				.orderByAsc(TrainQueue::getApplyTime));

		Map<String, Object> deviceLockMap = new HashMap<>();
		Map<String, List<TrainQueue>> deviceIdToTrainQueueMap = trainQueues.stream().collect(Collectors.groupingBy(TrainQueue::getEntityDeviceId));
		Set<String> scanTrains = new HashSet<>();
		for (List<TrainQueue> value : deviceIdToTrainQueueMap.values()) {
			if (TrainQueue.Status.WAITING == value.get(0).getStatus()) {
				scanTrains.add(value.get(0).getRoomId());
			} else {
				deviceLockMap.put(value.get(0).getEntityDeviceId(), new Object());
			}
		}



		Map<String, List<TrainQueue>> trainQueueMap = trainQueues.stream().collect(Collectors.groupingBy(TrainQueue::getRoomId));
		List<TrainQueueRsp> trainQueueRsps = new ArrayList<>();
		trainQueueMap.forEach((roomKey, trainQueuesValue) -> {
			if (scanTrains.contains(roomKey)) {
				TrainQueueRsp trainQueueRsp = new TrainQueueRsp();
				trainQueueRsp.setRoomId(roomKey);
				trainQueueRsp.setRoomName(trainQueuesValue.get(0).getRoomName());
				trainQueueRsp.setStatus(trainQueuesValue.get(0).getStatus());
				trainQueueRsp.setEntityDeviceIds(trainQueuesValue.stream().map(TrainQueue::getEntityDeviceId).collect(Collectors.toList()));
				trainQueueRsp.setApplyTime(DateTimeFormatUtil.DATE_FORMATTER.format(trainQueuesValue.get(0).getApplyTime()));

				trainQueueRsps.add(trainQueueRsp);
			}
		});
		Collections.sort(trainQueueRsps, Comparator.comparing(TrainQueueRsp::getApplyTime));
		for (TrainQueueRsp trainQueueRsp : trainQueueRsps) {
			int lockCount = trainQueueRsp.getEntityDeviceIds().size();
			for (String entityDeviceId : trainQueueRsp.getEntityDeviceIds()) {
				if (Objects.nonNull(deviceLockMap.get(entityDeviceId))) {
					lockCount--;
				}
			}
			if (lockCount == trainQueueRsp.getEntityDeviceIds().size()) {
				for (String entityDeviceId : trainQueueRsp.getEntityDeviceIds()) {
					deviceLockMap.put(entityDeviceId, new Object());
				}
				trainQueueMapper.update(null, new LambdaUpdateWrapper<TrainQueue>().set(TrainQueue::getStatus, TrainQueue.Status.RUNNING)
						.eq(TrainQueue::getRoomId, trainQueueRsp.getRoomId()).eq(TrainQueue::getStatus, TrainQueue.Status.WAITING));

				startScene(trainQueueRsp.getRoomId(), UserInfoContext.getUserContext().getUserId());
			}

		}



	}

	/**
	 * 视图场景
	 *
	 * @param roomId 房间id
	 * @param roleId 岗位id
	 * @return {@code TeamSceneViewContext }
	 */
	@Override
	public TeamSceneViewContext viewScene(String roomId, String roleId) {
		TeamSceneViewContext ret = new TeamSceneViewContext();
		ret.setRoomId(roomId);

		TeamRoom room = teamRoomService.getById(roomId);

		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、获取可见节点&鉴权， null代表所有节点可见
		List<CrRole> roleList = crRoleService.getRolesByRoomId(roomId);
		CrRole selfRole = CrRole.findRoleByUserId(roleList, userId);
		if (null == selfRole) {
			log.error("user [{}] has no permission to access team training [{}]", userId, roomId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
		CrRoleNodesData nodes = getNodes(selfRole, roleList, roleId);

		// 2、尝试获取启动的远程场景
		JSONObject sceneData = getMpSceneData(room);
		if (sceneData != null) {
			sceneFilterService.filterVisibleTopo(sceneData, nodes);
			ret.setSceneData(sceneData);
			ret.setLocalSceneId(room.getLocalSceneId());
			ret.setMpSceneId(sceneData.getString(CrCommonConstants.MP_SCENE_ID_KEY));

			// 4. 场景角色资产配置，用于动态编辑,
			ret.setRoleList(crSceneService.getNodeConfigList(roleList));
			return ret;
		}
		// 3、 远程场景不存在，返回topo数据
		JSONObject topoData = localSceneService.getTopoData(room.getCopyedTopoId())
				.getJSONObject(CrCommonConstants.TOPO_DATA_KEY);
		sceneFilterService.filterVisibleTopo(topoData, nodes);
		ret.setTopoId(room.getCopyedTopoId());
		ret.setTopoData(topoData);

		return ret;
	}

	/**
	 * 停止场景
	 *
	 * @param roomId 房间id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean stopScene(String roomId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、只有教员可以操作场景
		teamRoomService.checkTeacherPermission(roomId, userId);

		TeamRoom teamRoom = teamRoomService.getById(roomId);

		// 2、如果场景已经停止，返回成功
		if (StringUtils.isBlank(teamRoom.getLocalSceneId())) {
			return true;
		}
		LocalScene localScene = localSceneService.getById(teamRoom.getLocalSceneId());

		Boolean stopResult = localSceneService.stopScene(localScene);



		if (stopResult) {
			// 记录复盘sceneId,
			// 是否需要更新replay_scene_id。训练未结束，更新；训练已结束，场景开始时间在训练结束之前才更新。
			// 只有训练已结束且场景开启时间在训练结束之后，才不更新
			SCHEDULED_EXECUTOR_SERVICE.schedule(() -> {
				tryDeviceLock();
				try {
					trainQueueMapper.update(null, new LambdaUpdateWrapper<TrainQueue>().set(TrainQueue::getStatus, TrainQueue.Status.FINISHED)
							.eq(TrainQueue::getRoomId, roomId));

					scanTrainQueueAndStartTrain();
				} finally {
					DEVICE_QUEUE_LOCK.unlock();
				}
			}, 60L, TimeUnit.SECONDS);


			if (!(teamRoom.isEnded() && localScene.getCreateTime()
					.after(trainRecordService.getActualEndTime(roomId)))) {
				teamRoom.setReplaySceneId(localScene.getMpSceneId());

				//该训练过程中的场景+1
				List<String> sceneIds = teamRoom.getSceneIds();
				sceneIds.add(localScene.getMpSceneId());
				teamRoom.setSceneIds(sceneIds);
			}
			transactionTemplate.execute(new TransactionCallbackWithoutResult() {

				@Override
				protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
					// 清除localScene
					clearLocal(teamRoom);
					// 恢复创建房间时的角色资产配置
					crRoleService.recoverNodesConfig(roomId);
					// 删除常用资产
					teamCommonDeviceService.delByRoomId(roomId);
				}
			});
			crSceneService.sendSceneStopMessage(roomId);
		}

		return stopResult;
	}

	/**
	 * 清除当地
	 *
	 * @param room 房间
	 */
	@Override
	public void clearLocal(TeamRoom room) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				String localSceneId = room.getLocalSceneId();
				room.setLocalSceneId("");

				// 清除业务数据、localscene表中数据
				localSceneService.removeById(localSceneId);
				teamRoomService.updateById(room);
			}
		});
	}

	/**
	 * 暂停场景
	 *
	 * @param roomId 房间id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean pauseScene(String roomId) {

		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、只有教员可以操作场景
		teamRoomService.checkTeacherPermission(roomId, userId);

		TeamRoom teamRoom = teamRoomService.getById(roomId);

		// 2、如果场景不存在
		if (StringUtils.isBlank(teamRoom.getLocalSceneId())) {
			log.error("LocalSceneId is null,LocalSceneId =[{}]", teamRoom.getLocalSceneId());
			throw new BusinessException(CrBusinessErrorCode.LOCAL_SCENE_NOT_EXIST);
		}
		// 3、 发送mp暂停请求
		return localSceneService.pauseScene(teamRoom.getLocalSceneId());
	}

	/**
	 * 取消暂停场景
	 *
	 * @param roomId 房间id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean unpauseScene(String roomId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、只有教员可以操作场景
		teamRoomService.checkTeacherPermission(roomId, userId);

		TeamRoom teamRoom = teamRoomService.getById(roomId);

		// 2、如果场景不存在
		if (StringUtils.isBlank(teamRoom.getLocalSceneId())) {
			log.error("LocalSceneId is null,LocalSceneId =[{}]", teamRoom.getLocalSceneId());
			throw new BusinessException(CrBusinessErrorCode.LOCAL_SCENE_NOT_EXIST);
		}
		// 3、 发送mp恢复暂停请求
		return localSceneService.unpauseScene(teamRoom.getLocalSceneId());
	}

	/**
	 * 获取流量列表
	 *
	 * @param roomId 房间id
	 * @return {@code List<Flow> }
	 */
	@Override
	public List<Flow> getFlowList(String roomId) {
		// 1.创建变量
		TeamRoom room = teamRoomService.getById(roomId);
		String userId = UserInfoContext.getUserContext().getUserId();
		List<Flow> flows = Lists.newArrayList();
		Cpflow cpflow = null;
		Map<String, String> elementsMap = Maps.newHashMap();

		// 2. 获取topo
		LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
		// 2.1 场景已启动
		if (localScene != null) {
			// 2.1.1 请求sceneData
			JSONObject sceneData = localSceneService.getSceneData(localScene, userId);
			// 2.1.2 转换JsonObject为SceneData对象
			SceneData data = sceneData.toJavaObject(SceneDataImpl.class);
			// 2.1.3 获取流量数据
			cpflow = data.getPlugins().getCpflow();
			// 2.1.4 生成节点数据map
			List<SceneElement> elements = data.getTopology().getElements();
			for (SceneElement element : elements) {
				elementsMap.put(element.getId(), element.getName());
			}
		} else {
			// 2.2 场景未启动
			// 2.2.1 请求topoData
			JSONObject topoData = localSceneService.getTopoData(room.getCopyedTopoId());
			TopoData data = topoData.getJSONObject(CrCommonConstants.TOPO_DATA_KEY)
					.toJavaObject(TopoData.class);
			cpflow = data.getPlugins().getCpflow();

			List<TopoElement> elements = data.getTopology().getElements();
			for (TopoElement element : elements) {
				elementsMap.put(element.getId(), element.getName());
			}
		}
		// 3. 没有流量，返回空列表
		if (cpflow == null) {
			return flows;
		}
		// 4. 获取每个节点的流量，整合成列表
		for (Map.Entry<String, ElementFlowList> entry : cpflow.getElements().entrySet()) {
			String nodeId = entry.getKey();
			String nodeName = elementsMap.get(nodeId);

			for (ElementFlow flow : entry.getValue().getFlows()) {
				Flow flowData = BeanUtils.copy(flow, Flow.class);
				// 4.1 添加节点数据
				flowData.setNodeId(nodeId);
				flowData.setNodeName(nodeName);
				flows.add(flowData);
			}
		}

		// 5. 返回流量数据列表
		return flows;
	}

	/**
	 * 获取资产列表
	 *
	 * @param roomId   房间id
	 * @param category 类别
	 * @param name     名称
	 * @return {@code JSONArray }
	 */
	@Override
	public JSONArray getAssetList(String roomId, Integer category, String name) {
		TeamRoom room = teamRoomService.getOrExceptionById(roomId);
		String localSceneId = room.getLocalSceneId();
		if (StringUtils.isNotBlank(localSceneId)) {
			return localSceneService.getAssetListByInstanceId(roomId, category, name, true);
		} else {
			String topoId = room.getCopyedTopoId();
			return localSceneService.getAssetListByTopoId(topoId, category, name);
		}
	}

	/**
	 * 获取节点统计学
	 *
	 * @param roomId 房间id
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject getNodeStatistics(String roomId) {
		TeamRoom room = teamRoomService.getOrExceptionById(roomId);
		String localSceneId = room.getLocalSceneId();
		if (StringUtils.isNotBlank(localSceneId)) {
			return localSceneService.getNodeStatistics(localSceneId);
		} else {
			String topoId = room.getCopyedTopoId();
			return localSceneService.getNodeStatisticsByTopoId(topoId);
		}
	}


	/**
	 * 获取节点
	 *
	 * @param selfRole 自我岗位
	 * @param roleList 岗位列表
	 * @param roleId   岗位id
	 * @return {@code CrRoleNodesData }
	 */
	private CrRoleNodesData getNodes(CrRole selfRole, List<CrRole> roleList, String roleId) {

		// 1、如果是学员，直接返回
		if (selfRole.isTeamStudent()) {
			return selfRole.getNodeData();
		}

		// 2、教员查看自己的节点，不需要传roleId
		if (StringUtils.isBlank(roleId)) {
			return null;
		}

		// 3、教员查看学员角色的战争迷雾
		// 3.1、查找学员的role
		CrRole fakeRole = CrRole.findRoleByRoleId(roleList, roleId);
		// 3.2、返回学员role的可见节点
		if (null != fakeRole) {
			return fakeRole.getNodeData();
		}

		return null;
	}

	/**
	 * 创建当地场景
	 *
	 * @param businessCode 业务代号
	 * @param teamRoom     团队会议室
	 * @param topoId       拓扑id
	 * @return {@code LocalScene }
	 */
	private LocalScene createLocalScene(Integer businessCode, TeamRoom teamRoom, String topoId) {
		LocalScene localScene = new LocalScene();

		localScene.setBusinessCode(businessCode);
		localScene.setUserId(UserInfoContext.getUserContext().getUserId());
		localScene.setBusinessInstanceId(teamRoom.getResourceId());
		localScene.setTopoId(topoId);
		localScene.setResourceId(IDGeneratorUtil.getID());
		localScene.setSceneStatus(SceneStatusEnum.INIT.getCode());
		localScene.setDestroyMode(LocalScene.DestroyMode.MANUAL.getCode());
		localScene.setStartTime(new Date());
		localScene.setSceneAttr(SceneStartContext.SceneAttrEnum.TEAM.getCode());
		localScene.setSceneName(teamRoom.getName());
		localScene.setMeta(JSON.toJSONString(
				LocalSceneMeta.builder().appResourceId(teamRoom.getResourceId()).build()));
		localScene.setRecord(teamRoom.getEnableRecording());

		return localScene;
	}

	/**
	 * 获取中台场景数据
	 *
	 * @param room 房间
	 * @return {@code JSONObject }
	 */
	private JSONObject getMpSceneData(TeamRoom room) {
		String localSceneId = room.getLocalSceneId();
		// localSceneId字段为空
		if (StringUtils.isBlank(localSceneId)) {
			return null;
		}
		// localScene不存在，localSceneId字段是脏数据
		LocalScene localScene = localSceneService.getById(localSceneId);
		if (null == localScene) {
			room.setLocalSceneId("");
			teamRoomService.updateById(room);
			return null;
		}
		// localScene 的 mpSceneId为空，localScene是脏数据
		String mpSceneId = localScene.getMpSceneId();
		if (StringUtils.isBlank(mpSceneId)) {
			clearLocal(room);
			return null;
		}
		// 请求远程场景数据
		JSONObject sceneData = localSceneService.getSceneData(mpSceneId,
				UserInfoContext.getUserContext().getUserId());
		// 场景数据为空，清除本地脏数据
		if (null == sceneData) {
			log.info("roomId=[{}] local has sceneId=[{}], remote is null", room.getResourceId(),
					mpSceneId);
			clearLocal(room);
			return null;
		}
		// 场景实际已被删除，清除本地脏数据
		SceneData data = sceneData.toJavaObject(SceneDataImpl.class);
		if (data.isStopped()) {
			clearLocal(room);
			return null;
		}
		sceneData.put(CrCommonConstants.MP_SCENE_ID_KEY, mpSceneId);
		return sceneData;
	}
}
