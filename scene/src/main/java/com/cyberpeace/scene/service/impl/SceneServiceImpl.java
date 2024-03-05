package com.cyberpeace.scene.service.impl;

import java.io.File;
import java.util.*;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.common.util.*;
import com.cyberpeace.cs.service.SystemPropertyService;
import com.cyberpeace.scene.constant.ScenePropertyConstant;
import com.cyberpeace.scene.constant.enums.*;
import com.cyberpeace.scene.controller.req.SceneStartReq;
import com.cyberpeace.scene.controller.rsp.ConnectionRsp;
import com.cyberpeace.scene.controller.rsp.SceneDataRsp;
import com.cyberpeace.scene.controller.rsp.SceneDetailRsp;
import com.cyberpeace.scene.dao.entity.NetworkEntity;
import com.cyberpeace.scene.dao.entity.NodeEntity;
import com.cyberpeace.scene.dao.entity.SceneEntity;
import com.cyberpeace.scene.dao.mapper.NetworkMapper;
import com.cyberpeace.scene.dao.mapper.NodeMapper;
import com.cyberpeace.scene.dao.mapper.SceneMapper;
import com.cyberpeace.scene.job.SceneJob;
import com.cyberpeace.scene.service.*;
import com.cyberpeace.scene.service.bo.*;
import com.cyberpeace.scene.service.bo.ad.AdSceneStartReq;
import com.cyberpeace.scene.service.bo.req.CreateContainerReq;
import com.cyberpeace.scene.service.bo.req.CreateInstanceReq;
import com.cyberpeace.scene.service.bo.res.CreateContainersRes;
import com.cyberpeace.scene.service.bo.res.CreateInstanceRes;
import com.cyberpeace.scene.service.bo.scene.ElementStatistics;
import com.cyberpeace.scene.service.bo.scene.SceneProcessBo;
import com.cyberpeace.scene.util.SceneThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author :   SN
 * @date :   2023/12/20 15:21
 * @desc :
 **/
@Slf4j
@Service
public class SceneServiceImpl extends ServiceImpl<SceneMapper, SceneEntity> implements SceneService {

	@Autowired
	private TopologyService topologyService;

	@Autowired
	private SceneMapper sceneMapper;

	@Autowired
	@Lazy
	private SceneThreadPoolExecutor executor;

	@Lazy
	@Autowired
	private SceneJob sceneJob;

	@Autowired
	private NetworkService networkService;

	@Autowired
	private NetworkMapper networkMapper;

	@Autowired
	private NodeService nodeService;

	@Autowired
	private NodeMapper nodeMapper;

	@Autowired
	private HostService hostService;

	@Autowired
	private ContainerService containerService;

	@Autowired
	private SystemPropertyService systemPropertyService;

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	/**
	 * 场景自动销毁时间 单位:小时
	 */
	private int SCENE_DESTROY;

	/**
	 * 最大场景并发数
	 */
	private Integer MAX_SCENE_EVENT;

	/**
	 * 用户最大场景并发数
	 */
	private Integer MAX_USER_SCENE_EVENT;

	@PostConstruct
	public void init() {
		SCENE_DESTROY = systemPropertyService
				.getSystemPropertyInt(ScenePropertyConstant.SCENE_DESTROY_TIME, 2);
		MAX_SCENE_EVENT = systemPropertyService
				.getSystemPropertyInt(ScenePropertyConstant.MAX_SCENE_COUNT, 10);
		MAX_USER_SCENE_EVENT = systemPropertyService
				.getSystemPropertyInt(ScenePropertyConstant.MAX_SCENE_COUNT_PER_USER, 2);
	}

	@Override
	public BaseRsp startScene(SceneStartReq req) {
		Topology topology = topologyService.formatTopology(req.getTopologyId());
		log.info("topology：{}", JsonUtil.toJsonString(topology));
		if (topology == null) {
			throw new BusinessException(ErrorCode.UNKNOWN_SCENE);
		}
		// 创建场景
		SceneEntity scene = createScene(req.getTopologyId(), JsonUtil.toJsonString(topology),
				req.getInstanceId(), "", req.getInstanceType(), req.getDestroyTime());
		// 提交创建场景任务
		executor.submit(() -> startScene(topology, scene));
		log.info("submit create scene mission, scene ：{}", scene);
		return new BaseRsp<>(scene);
	}

	@Override
	public JSONObject startSceneByTopo(AdSceneStartReq req) {
		log.info("ad scene start req: {}", JsonUtil.toJsonString(req));
		String instanceId = req.getContext().getRaceId() + "_" + req.getContext().getQuestionId();
		SceneEntity instance = sceneMapper.selectByInstanceId(instanceId,
				req.getContext().getAccessUserId(), SceneEnum.getCodes());
		if (null != instance) {
			throw new BusinessException(ErrorCode.SCENE_ALREADY_EXIST);
		}
		JSONObject topo = req.getScene_config().getJSONObject(JsonKeyConstant.TOPOLOGY);
		Topology topology = topo.toJavaObject(Topology.class);
		if (topology == null) {
			throw new BusinessException(ErrorCode.UNKNOWN_SCENE);
		}
		// 创建场景

		SceneEntity scene = createScene(req.getId(), topo.toJSONString(), instanceId,
				req.getContext().getAccessUserId(), req.getContext().getInstanceType(),
				req.getContext().getDestroyTime());
		executor.submit(() -> startScene(topology, scene));
		// 生成返回数据
		JSONObject res = new JSONObject();
		res.put(JsonKeyConstant.SCENE_DATA, makeSceneData(scene));
		res.put(JsonKeyConstant.ID, scene.getResourceId());
		return res;
	}

	@Override
	public SceneData getSceneDataBySceneId(String sceneId) {
		SceneEntity scene = sceneMapper.selectByResourceId(sceneId);
		if (Objects.isNull(scene)) {
			return null;
		}
		return makeSceneData(scene);
	}

	@Override
	public List<String> getUndeletedSceneIdByUserId(String userId) {
		return sceneMapper.selectByUserId(userId, SceneEnum.DELETED.getCode());
	}

	@Override
	public void destroySceneBatch(List<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return;
		}
		for (String id : ids) {
			executor.submit(() -> {
				destroySceneIgnoreException(id);
			});
		}
	}

	@Override
	public void destroySceneByRaceId(String raceId) {
		if(StringUtils.isBlank(raceId)){
			return;
		}
		List<String> sceneIdList = sceneMapper.selectByRaceId(raceId);
		destroySceneBatch(sceneIdList);
	}

	/**
	 * 验证场景并发数
	 * @param userId
	 */
	@Override
	public void verify(String userId) {
		int sceneCount = sceneMapper.countAll() + 1;

		// 场景数超过最大值
		if (sceneCount > MAX_SCENE_EVENT) {
			throw new BusinessException(ErrorCode.SCENE_EVENT_OVERFLOW, MAX_SCENE_EVENT);
		}

		int userSceneCount = sceneMapper.countByUserId(userId) + 1;
		// 用户场景数超过最大值
		if (userSceneCount > MAX_USER_SCENE_EVENT) {
			throw new BusinessException(ErrorCode.USER_SCENE_EVENT_OVERFLOW, MAX_USER_SCENE_EVENT);
		}
	}

	@Override
	public void destroyScene(String sceneId) {
		SceneEntity scene = sceneMapper.selectByResourceId(sceneId);
		if (Objects.isNull(scene)) {
			return;
		}
		try {
			// 删除节点
			nodeService.delete(sceneId);
			// 删除网络
			List<NetworkEntity> networks = networkMapper.selectBySceneId(sceneId);
			if (!CollectionUtils.isEmpty(networks)) {
				networks.forEach(iter -> {
					if (StringUtils.isNotBlank(iter.getUuid())) {
						networkService.delete(iter.getUuid());
					}
					networkMapper.deleteByResourceId(iter.getResourceId());
				});
			}
			sceneMapper.deleteByResourceId(sceneId);
		} catch (Exception e) {
			log.error("delete scene:[{}], error:{}", sceneId, e);
			throw new BusinessException(ErrorCode.SCENE_DESTROY_FAILED);
		}
	}

	/**
	 * 最终一定删除scene，保证用户可以起新场景
	 * 用于异步接口
	 * destroy场景ignoreexception
	 *
	 * @param sceneId 场景id
	 */
	private void destroySceneIgnoreException(String sceneId){
		try {
			// 删除节点
			nodeService.delete(sceneId);
			// 删除网络
			List<NetworkEntity> networks = networkMapper.selectBySceneId(sceneId);
			if (!CollectionUtils.isEmpty(networks)) {
				networks.forEach(iter -> {
					if (StringUtils.isNotBlank(iter.getUuid())) {
						networkService.delete(iter.getUuid());
					}
					networkMapper.deleteByResourceId(iter.getResourceId());
				});
			}
		} catch (Exception e) {
			log.error("failed to delete network or node of scene:[{}]", sceneId, e);
		} finally {
			sceneMapper.deleteByResourceId(sceneId);
		}
	}

	@Override
	public JSONObject getSceneJsonObject(String instanceId) {
		SceneData sceneData = getSceneData(instanceId);
		if (sceneData == null) {
			return null;
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("scene", sceneData);
		jsonObject.put(JsonKeyConstant.REMAIN_TIME, sceneData.getRemainingTime());
		return jsonObject;
	}

	private SceneData getSceneData(String instanceId) {
		String userId = UserInfoContext.getUserContext().getUserId();
		SceneEntity scene = sceneMapper.selectByInstanceId(instanceId, userId,
				SceneEnum.getCodes());
		if (Objects.isNull(scene)) {
			return null;
		}
		return makeSceneData(scene);
	}

	@Override
	public BaseRsp detail(String topologyId, String instanceId) {
		SceneData sceneData = getSceneData(instanceId);
		return new BaseRsp<>(new SceneDetailRsp(topologyId, sceneData));
	}

	@Override
	public BaseRsp console(String nodeId, Integer type) {
		ConnectionRsp connectionRsp = new ConnectionRsp();
		if (Objects.equals(ImageTypeEnum.ELEMENT_TYPE_HOST.getCode(), type)) {
			connectionRsp.setConnectionUrl(hostService.console(nodeId).getUrl());
		} else if (Objects.equals(ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getCode(), type)) {
			connectionRsp.setConnectionUrl(containerService.console(nodeId));
		}
		connectionRsp.setProtocol("console");
		connectionRsp.setNodeId(nodeId);
		return new BaseRsp<>(connectionRsp);
	}

	@Override
	public void repair(String filePath) {
		File repairFile = new File(filePath);
		if (!repairFile.exists()) {
			return;
		}
		log.info("Start run one click repair scene");
		List<SceneEntity> list = list(new LambdaQueryWrapper<SceneEntity>()
				.in(SceneEntity::getSceneStatus, SceneEnum.getCodes()));
		if (!CollectionUtils.isEmpty(list)) {
			list.forEach(scene -> destroyScene(scene.getResourceId()));
		}
		FileUtil.deleteDir(repairFile);
		log.info("Run one click repair scene end");
	}

	@Override
	public void autoDestroy() {
		List<SceneEntity> list = list(new LambdaQueryWrapper<SceneEntity>()
				.in(SceneEntity::getSceneStatus, SceneEnum.getCodes())
				.lt(SceneEntity::getDestroyTime, new Date()));
		if (!CollectionUtils.isEmpty(list)) {
			list.forEach(scene -> {
				destroyScene(scene.getResourceId());
				log.info("Destroy scene : {}", scene.getResourceId());
			});
		}
	}

	private void startScene(Topology topology, SceneEntity scene) {
		// update status
		sceneMapper.updateSceneStatus(scene.getResourceId(), SceneEnum.CREATE.getCode());
		log.info("start scene : {}", scene.getResourceId());
		if (null == topology || CollectionUtils.isEmpty(topology.getElements())) {
			return;
		}
		// 获取拓扑元素
		ElementStatistics elements = this.getElementStatistics(topology);
		try {
			TopologyElement networkElement = new TopologyElement();
			// 获取网络信息
			if (!elements.getNodes().isEmpty()) {
				networkElement = elements.getNetworks().get(0);
			}
			NetworkEntity network;
			this.pushScenePlugin(scene, 0, 0, StageEnum.CREATE_NETWORK,
					SceneEventStatusEnum.ONGOING.getCode(), 2);
			try {
				// 创建网络
				network = createNetwork(networkElement, scene.getResourceId());
			} catch (Exception e) {
				this.pushScenePlugin(scene, 100, 0, StageEnum.CREATE_NETWORK,
						SceneEventStatusEnum.ERROR.getCode(), 5);
				throw e;
			}
			this.pushScenePlugin(scene, 100, 0, StageEnum.CREATE_INSTANCE,
					SceneEventStatusEnum.ONGOING.getCode(), 2);

			// node count
			int count = 0;
			int size = elements.getNodes().size();

			// 创建虚拟机和容器
			for (TopologyElement element : elements.getNodes()) {
				this.pushElement(element, scene, SceneEventStatusEnum.ONGOING.getCode(), null);
				count++;
				NodeEntity nodeEntity;
				try {
					nodeEntity = createNode(element, scene, network);
					element.setNodeId(nodeEntity.getInstanceId());
					sceneMapper.updateMeta(scene.getResourceId(), JsonUtil.toJsonString(topology));
				} catch (Exception e) {
					this.pushElement(element, scene, SceneEventStatusEnum.ERROR.getCode(), null);
					this.pushScenePlugin(scene, 100, state(count, size), StageEnum.CREATE_INSTANCE,
							SceneEventStatusEnum.ERROR.getCode(), 5);
					throw e;
				}
				this.pushElement(element, scene, SceneEventStatusEnum.FINISH.getCode(), nodeEntity.getIp());
				this.pushScenePlugin(scene, 100, state(count, size), StageEnum.CREATE_INSTANCE,
						SceneEventStatusEnum.ONGOING.getCode(), 2);
			}
		} catch (Exception e) {
			log.error("Scene start failed,scene:{}, error:{}", scene.getSceneName(), e.getMessage(),
					e);
			sceneMapper.updateSceneStatus(scene.getResourceId(), SceneEnum.ERROR.getCode());
			throw new BusinessException(ErrorCode.FAILED_TO_CREATE_SCENE, e.getMessage());
		}
		sceneMapper.updateSceneStatus(scene.getResourceId(), SceneEnum.ACTIVE.getCode());
		this.pushScenePlugin(scene, 100, 100, StageEnum.CREATE_INSTANCE,
				SceneEventStatusEnum.FINISH.getCode(), 4);
		log.info("start scene end : {}", scene.getResourceId());
		this.pushCreationComplete(scene);
	}

	/**
	 * 创建网络
	 * @param element  元素信息
	 * @return  网络实体
	 */
	private NetworkEntity createNetwork(TopologyElement element, String sceneId) {
		String cidr = element.getNetwork() != null ? element.getNetwork().getCidr() : null;
		String gateway = element.getNetwork() != null ? element.getNetwork().getGateway() : null;
		String networkName = IDGeneratorUtil.getID();
		return networkService.create(networkName, cidr, gateway, sceneId, element.getId());
	}

	private NodeEntity createNode(TopologyElement element, SceneEntity scene,
			NetworkEntity network) {
		// 1. save node entity
		NodeEntity entity = new NodeEntity();
		entity.setResourceId(IDGeneratorUtil.getID());
		entity.setSid(scene.getResourceId());
		entity.setType(element.getType());
		entity.setStatus(SceneEnum.CREATE.getCode());
		entity.setNid(element.getId());
		nodeService.save(entity);

		// 2.create node in cp cloud
		try {
			if (element.getType().equals(ImageTypeEnum.ELEMENT_TYPE_HOST.getCode())) {
				// 创建虚拟机
				CreateInstanceRes rsp = hostService.create(new CreateInstanceReq(element, network));
				entity.setInstanceId(rsp.getUuid());
				entity.setName(rsp.getInstanceName());
				entity.setTid(rsp.getImage());
				entity.setStatus(SceneEnum.ACTIVE.getCode());
				entity.setIp(getIp(rsp.getAddresses()));
				// update topo
				setPortMapping(element, rsp.getNets());

			} else {
				// container
				CreateContainersRes rsp = containerService
						.create(new CreateContainerReq(element, network));
				entity.setInstanceId(rsp.getUuid());
				entity.setName(rsp.getName());
				entity.setTid(rsp.getImage());
				entity.setStatus(SceneEnum.ACTIVE.getCode());
				entity.setIp(getIp(rsp.getAddresses()));
				// update topo
				setPortMapping(element, rsp.getNets());
			}
		} catch (Exception e) {
			entity.setStatus(SceneEnum.ERROR.getCode());
			throw e;
		} finally {
			// 3.update entity
			nodeService.updateById(entity);
		}
		return entity;
	}

	/**
	 * 保存端口映射数据到meta
	 * 创建终端的接口保证access_modes顺序和个数与请求的一致
	 *
	 * @param element
	 * @param nets
	 */
	private void setPortMapping(TopologyElement element, List<Net> nets) {
		if (CollectionUtils.isEmpty(element.getPorts()) || CollectionUtils.isEmpty(nets)
				|| CollectionUtils.isEmpty(nets.get(0).getAccess_modes())) {
			return;
		}
		int length = nets.get(0).getAccess_modes().size();
		for (int i = 0; i < length; i++) {
			JSONObject mode = element.getPorts().get(0).getAccessModes().get(i);
			NetAccessMode netAccessMode = nets.get(0).getAccess_modes().get(i);
			mode.put(JsonKeyConstant.PORT_MAPPING, JsonUtil.java2JsonObject(netAccessMode.getPort_mapping()));
		}
	}

	private SceneData makeSceneData(SceneEntity scene) {
		SceneData sceneData = new SceneData();
		String topo = "";
		Date currentTime = new Date();
		long remainTime = (scene.getDestroyTime().getTime() - currentTime.getTime()) / 1000;
		sceneData.setRemainingTime((int) remainTime);
		if (StringUtils.isNotBlank(topo)) {
			Topology topology = JsonUtil.toJsonObject(topo, Topology.class);
			SceneDataRsp sceneDataRsp = new SceneDataRsp();
			BeanUtils.copyProperties(scene, sceneDataRsp);
			if (Objects.equals(SceneEnum.ACTIVE.getCode(), scene.getSceneStatus())) {
				sceneDataRsp.setEvent(2);
				sceneDataRsp.setEventStatus(4);
			} else if (Objects.equals(SceneEnum.CREATE.getCode(), scene.getSceneStatus())) {
				sceneDataRsp.setEvent(2);
				sceneDataRsp.setEventStatus(2);
			} else if (Objects.equals(SceneEnum.DELETED.getCode(), scene.getSceneStatus())) {
				sceneDataRsp.setEvent(9);
				sceneDataRsp.setEventStatus(4);
			} else if (Objects.equals(SceneEnum.ERROR.getCode(), scene.getSceneStatus())) {
				sceneDataRsp.setEvent(2);
				sceneDataRsp.setEventStatus(5);
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("cpcloud", topologyService.makePlugins(topology));
			sceneData.setTopology(topology);
			sceneData.setScene(scene);
			sceneData.setPlugins(jsonObject);
			SceneProcessBo processBo = getSceneProcess(scene, topology);
			List<WsSceneStage> wsSceneStages = stageList(processBo.getNetworkState(),
					processBo.getNodeState());
			Integer percentage = percentage(wsSceneStages);
			sceneDataRsp.setStageList(wsSceneStages);

			sceneDataRsp.setStage(processBo.getStageEnum().getStage());
			sceneDataRsp.setStageName(processBo.getStageEnum().getStageName());
			sceneDataRsp.setStagePercentage(processBo.getStagePercentage());
			sceneDataRsp.setProcessMessage(processBo.getStageEnum().getStageName());
			sceneDataRsp.setProgressStatus(2);

			sceneDataRsp.setProgressPercentage(percentage);
			topology.setScene(sceneDataRsp);
			setPortIp(topology, scene.getResourceId());
		}
		return sceneData;
	}

	private void setPortIp(Topology topology, String sceneId) {
		List<NodeEntity> nodeEntityList = nodeMapper.selectBySid(sceneId);
		if (CollectionUtils.isEmpty(nodeEntityList)) {
			return;
		}
		Map<String, NodeEntity> nodeMap = StreamApiUtils.toMap(nodeEntityList, NodeEntity::getNid,
				n -> n);
		for (TopologyElement element : topology.getElements()) {
			NodeEntity node = nodeMap.get(element.getId());
			if (null == node) {
				continue;
			}
			for (ElementPort port : element.getPorts()) {
				port.setIp(node.getIp());
			}
		}
	}

	private SceneEntity createScene(String topologyId, String topoJson,String instanceId, String userId,
			Integer instanceType, Integer destroy) {
		SceneEntity scene = new SceneEntity();
		scene.setResourceId(IDGeneratorUtil.getID());
		scene.setSceneName(IDGeneratorUtil.getID());
		Date startTime = new Date();
		scene.setStartTime(startTime);
		Date destroyTime;
		if (Objects.isNull(destroy)) {
			destroyTime = TimeUtil.addHours(startTime, SCENE_DESTROY);
		} else {
			destroyTime = TimeUtil.addHours(startTime, destroy);
		}
		scene.setDestroyTime(destroyTime);
		scene.setSceneTtl(SCENE_DESTROY);
		scene.setSceneStatus(SceneEnum.INIT.getCode());
		sceneMapper.insert(scene);
		return scene;
	}

	private SceneProcessBo getSceneProcess(SceneEntity scene, Topology topology) {
		SceneProcessBo bo = new SceneProcessBo();
		int networkCount = 0;
		// 其实应该只有一个
		List<NetworkEntity> networks = networkService.list(new LambdaQueryWrapper<NetworkEntity>()
				.eq(NetworkEntity::getSid, scene.getResourceId()));
		if (networks.isEmpty()) {
			bo.setStageEnum(StageEnum.INIT);
			return bo;
		}
		// 网络是否启动完成
		boolean networkFlag = true;
		for (NetworkEntity network : networks) {
			if (network.getNetworkStatus().equals(SceneEnum.CREATE.getCode())) {
				networkFlag = false;
			} else if (network.getNetworkStatus().equals(SceneEnum.ERROR.getCode())) {
				networkCount++;
				networkFlag = false;
			} else {
				networkCount++;
			}
		}
		// 1. 网络进度
		bo.setNetworkState(state(networkCount, networks.size()));
		if (!networkFlag) {
			bo.setStageEnum(StageEnum.CREATE_NETWORK);
			bo.setStagePercentage(bo.getNetworkState());
			return bo;
		} else {
			bo.setStageEnum(StageEnum.CREATE_INSTANCE);
		}
		// 2. 虚拟机和容器进度
		int nodeCount = 0;
		int nodeSize = getElementStatistics(topology).getNodes().size();
		List<NodeEntity> nodes = nodeService.list(
				new LambdaQueryWrapper<NodeEntity>().eq(NodeEntity::getSid, scene.getResourceId()));
		if (nodes.isEmpty()) {
			if (nodeSize > 0) {
				bo.setNodeState(0);
			} else {
				bo.setNodeState(100);
			}
			bo.setStagePercentage(bo.getNodeState());
			return bo;
		}
		for (NodeEntity node : nodes) {
			if (!node.getStatus().equals(SceneEnum.CREATE.getCode())) {
				// 成功和错误都算创建了
				nodeCount++;
			}
		}
		bo.setNodeState(state(nodeCount, nodeSize));
		bo.setStagePercentage(bo.getNodeState());
		return bo;
	}

	private Integer networkState(SceneEntity scene) {
		int count = 0;
		// 其实应该只有一个
		List<NetworkEntity> networks = networkService.list(new LambdaQueryWrapper<NetworkEntity>()
				.eq(NetworkEntity::getSid, scene.getResourceId()));
		if (networks.isEmpty()) {
			return 0;
		}
		for (NetworkEntity network : networks) {
			if (!network.getNetworkStatus().equals(SceneEnum.CREATE.getCode())) {
				// 成功或失败都算进度
				count++;
			}
		}
		return state(count, networks.size());
	}

	private Integer nodeState(SceneEntity scene, Integer size) {
		List<NodeEntity> nodes = nodeService.list(
				new LambdaQueryWrapper<NodeEntity>().eq(NodeEntity::getSid, scene.getResourceId()));
		int count = 0;
		if (nodes.isEmpty()) {
			// 不考虑没有节点的场景的情况，至少有一个节点
			return 0;
		}
		for (NodeEntity node : nodes) {
			if (!node.getStatus().equals(SceneEnum.CREATE.getCode())) {
				// 成功和错误都算创建了
				count++;
			}
		}
		return state(count, size);
	}


	private TopologyElement getElement(String instanceId, String meta) {
		Topology topology = JsonUtil.toJsonObject(meta, Topology.class);
		if (Objects.nonNull(topology)) {
			List<TopologyElement> elements = topology.getElements();
			if (!CollectionUtils.isEmpty(elements)) {
				for (TopologyElement element : elements) {
					if (StringUtils.equals(element.getNodeId(), instanceId)) {
						return element;
					}
				}
			}
		}
		return null;
	}

	private String getIp(JSONObject addresses) {
		String ip = null;
		if (Objects.isNull(addresses)) {
			return null;
		}
		Set<Map.Entry<String, Object>> entries = addresses.entrySet();
		for (Map.Entry<String, Object> entry : entries) {
			JSONObject address = JSON.parseObject(entry.getValue().toString());
			if (address.containsKey("ip_address")) {
				ip = address.getString("ip_address");
			}
		}
		return ip;
	}

	private Integer state(Integer dividend, Integer divisor) {
		return MathUtils.roundDivide(dividend * 100, divisor);
	}

	private void sendMsg(WsData data, SceneEntity scene) {

		WsSceneReportCodeData codeData = new WsSceneReportCodeData(1, data);
		String sceneContent = JsonUtil.toJsonString(codeData, PropertyNamingStrategy.SnakeCase);

		MyWebSocketHandler webSocketHandler = SpringContextUtil
				.getBeanByClass(MyWebSocketHandler.class);
		SceneWsIdentifier identifier = new SceneWsIdentifier();
		identifier.setPath(WsPath.CR_SCENE);

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().data(sceneContent).messageIdentifier(identifier).build();
		log.info("push Message ===>data:{}", JSON.toJSONString(data));
		// 只有单人场景，没有共享场景
		webSocketHandler.pushMessage(messageContext, false);
	}

	private void pushScenePlugin(SceneEntity scene, Integer networkStage, Integer instanceStage,
			StageEnum stageEnum, Integer eventStatus, Integer processStatus) {
		try {
			WsSceneData sceneData = new WsSceneData();
			sceneData.setSceneId(scene.getResourceId());
			WsScene wsScene = new WsScene();
			wsScene.setCreateConsumeSeconds(0);
			wsScene.setCreateTime(scene.getCreateTime());
			wsScene.setStage(stageEnum.getStage());
			List<WsSceneStage> wsSceneStages = stageList(networkStage, instanceStage);
			Integer percentage = percentage(wsSceneStages);
			wsScene.setStageList(wsSceneStages);
			wsScene.setStageName(stageEnum.getStageName());
			wsScene.setStagePercentage(percentage);
			wsScene.setProgressPercentage(percentage);
			wsScene.setProcess(stageEnum.getStage());
			wsScene.setProcessMessage(stageEnum.getStageName());
			wsScene.setProgressStatus(processStatus);
			wsScene.setEvent(2);
			wsScene.setEventStatus(eventStatus);
			sceneData.setScene(wsScene);

			WsData data = new WsData();
			data.setData(sceneData);
			data.setType("scene");
			// 推送消息
			sendMsg(data, scene);
		} catch (Exception e) {
			log.error("push message error :", e);
		}
	}

	private void pushScenePlugin(SceneEntity scene, Integer networkStage, Integer instanceStage,
			Integer processStatus, Integer eventStatus) {
		try {
			WsSceneData sceneData = new WsSceneData();
			sceneData.setSceneId(scene.getResourceId());
			WsScene wsScene = new WsScene();
			wsScene.setCreateConsumeSeconds(0);
			wsScene.setCreateTime(scene.getCreateTime());
			wsScene.setStage(StageEnum.CREATE_INSTANCE.getStage());
			List<WsSceneStage> wsSceneStages = stageList(networkStage, instanceStage);
			Integer percentage = percentage(wsSceneStages);
			wsScene.setStageList(wsSceneStages);
			wsScene.setStageName(StageEnum.CREATE_INSTANCE.getStageName());
			wsScene.setStagePercentage(percentage);
			wsScene.setProgressPercentage(percentage);
			wsScene.setProcess(StageEnum.CREATE_INSTANCE.getStage());
			wsScene.setProcessMessage(StageEnum.CREATE_INSTANCE.getStageName());
			wsScene.setProgressStatus(processStatus);
			wsScene.setEvent(2);
			wsScene.setEventStatus(eventStatus);
			sceneData.setScene(wsScene);

			WsData data = new WsData();
			data.setData(sceneData);
			data.setType("scene");
			// 推送消息
			sendMsg(data, scene);
		} catch (Exception e) {
			log.error("push message error : {}", e);
		}
	}

	private Integer percentage(List<WsSceneStage> wsSceneStages) {
		int sum = wsSceneStages.stream().mapToInt(WsSceneStage::getStagePercentage).sum();
		int percentage = state(sum, wsSceneStages.size() * 100);
		log.info("sum:{}, size:{}, state:{}", sum, wsSceneStages.size() * 100, percentage);
		return percentage;
	}

	private List<WsSceneStage> stageList(Integer networkStage, Integer instanceStage) {
		List<WsSceneStage> list = new ArrayList<>();
		// 初始状态
		list.add(new WsSceneStage(StageEnum.INIT.getStage(), StageEnum.INIT.getStageName(),
				StageEnum.INIT.getIcon(), 100));
		// 网络状态
		list.add(new WsSceneStage(StageEnum.CREATE_NETWORK.getStage(),
				StageEnum.CREATE_NETWORK.getStageName(), StageEnum.CREATE_NETWORK.getIcon(),
				networkStage));
		// 实例状态
		list.add(new WsSceneStage(StageEnum.CREATE_INSTANCE.getStage(),
				StageEnum.CREATE_INSTANCE.getStageName(), StageEnum.CREATE_INSTANCE.getIcon(),
				instanceStage));
		return list;
	}

	private void pushElement(TopologyElement origin, SceneEntity scene, Integer code, String ip) {
		try {
			TopologyElement element = JsonUtil.copy(origin,TopologyElement.class);
			WsSceneData sceneData = new WsSceneData();
			sceneData.setSceneId(scene.getResourceId());
			element.setEvent(2);
			element.setEventStatus(code);
			sceneData.setSceneElement(element);
			JSONObject cpcloud = new JSONObject();
			cpcloud.put("cpcloud", getElementPlugin(element, ip));
			sceneData.setSceneElementPortPluginData(cpcloud);
			sceneData.setSceneElementPluginData(makeElementPluginData(element));

			WsData data = new WsData();
			data.setType("scene_element");
			data.setData(sceneData);
			sendMsg(data, scene);
		} catch (Exception e) {
			log.error("push message error :", e);
		}
	}

	private JSONObject makeElementPluginData(TopologyElement element) {
		JSONObject elementJson = new JSONObject();
		Integer portNum = element.getPorts() == null ? 0 : element.getPorts().size();
		RoleEnum roleEnum = RoleEnum.getRoleEnum(element.getType());
		if (!Objects.isNull(roleEnum)) {
			elementJson.put("role", roleEnum.getValue());
			elementJson.put("image_type", roleEnum.getImageType());
			elementJson.put("electric_port_number", portNum);
		}
		JSONObject cpcloud = new JSONObject();
		cpcloud.put(JsonKeyConstant.CP_CLOUD, elementJson);
		return cpcloud;
	}

	private JSONObject getElementPlugin(TopologyElement element, String ip) {
		Boolean available = element.getEventStatus().equals(SceneEventStatusEnum.FINISH.getCode());

		JSONObject elementPortsJson = new JSONObject();
		for (ElementPort port : element.getPorts()) {
			port.setIp(ip);
			port.setEvent(2);
			port.setEventStatus(element.getEventStatus());
			JSONObject elementPortJson = new JSONObject();
			if (!CollectionUtils.isEmpty(port.getAccessModes())) {
				JSONArray newAccessModes = new JSONArray();
				port.getAccessModes().forEach(iter -> {
					iter.put("id", element.getNodeId());
					iter.put("ip", ip);
					iter.put("available", available);
					iter.put("type", element.getType());
					// 端口映射
					if (null != iter.getJSONObject(JsonKeyConstant.PORT_MAPPING)) {
						PortMapping portMapping = iter.getJSONObject(JsonKeyConstant.PORT_MAPPING)
								.toJavaObject(PortMapping.class);
						iter.put(JsonKeyConstant.ACCESS_IP, portMapping.getHost());
						iter.put(JsonKeyConstant.ACCESS_PORT, portMapping.getHost_port());
					}
					// 去掉rdp、ssh登录按钮
					String protocol = iter.getString(JsonKeyConstant.PROTOCOL);
					if ("ssh".equals(protocol) || "rdp".equals(protocol)) {
						iter.put(JsonKeyConstant.PROXY, false);
					}
					newAccessModes.add(iter);
				});
				elementPortJson.put("access_modes", newAccessModes);
			}
			elementPortJson.put("ip", ip);
			elementPortJson.put("event_status", element.getEventStatus());
			elementPortJson.put("event", 2);
			elementPortJson.put("resource_id", element.getNodeId());
			elementPortsJson.put(port.getId(), elementPortJson);
		}
		return elementPortsJson;
	}

	private void pushCreationComplete(SceneEntity scene) {
		JSONObject message = new JSONObject();
		message.put(JsonKeyConstant.CODE, CommonConstant.SCENE_CREATE_FINISH);
		SceneWsIdentifier identifier = new SceneWsIdentifier();
		identifier.setPath(WsPath.CR_SCENE);
		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().data(message).messageIdentifier(identifier).build();
		myWebSocketHandler.pushMessage(messageContext, false);
	}

	private ElementStatistics getElementStatistics(Topology topology) {
		ElementStatistics res = new ElementStatistics();
		for (TopologyElement element : topology.getElements()) {
			if (ImageTypeEnum.ELEMENT_TYPE_INTERNET.getCode().equals(element.getType())) {
				res.getNetworks().add(element);
			} else if (ImageTypeEnum.ELEMENT_TYPE_HOST.getCode().equals(element.getType())
					|| ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getCode().equals(element.getType())) {
				res.getNodes().add(element);
			}
		}
		return res;
	}
}
