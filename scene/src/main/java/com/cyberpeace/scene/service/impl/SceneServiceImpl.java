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
import com.cyberpeace.auth.service.bo.UserInfoContext;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
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
import com.cyberpeace.scene.job.SceneExecutor;
import com.cyberpeace.scene.job.SceneJob;
import com.cyberpeace.scene.service.*;
import com.cyberpeace.scene.service.bo.*;
import com.cyberpeace.scene.service.bo.ad.AdSceneStartReq;
import com.cyberpeace.scene.service.bo.res.CreateInstanceRes;
import com.cyberpeace.scene.util.SceneThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author :   SN
 * @date :   2023/12/20 15:21
 * @desc :
 **/
@Slf4j
@Service
public class SceneServiceImpl extends ServiceImpl<SceneMapper, SceneEntity> implements SceneService{

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
		String userId = UserInfoContext.getNewUserContext().getResourceId();
		SceneEntity instance = sceneMapper.selectByInstanceId(req.getInstanceId(), userId,
				SceneEnum.getCodes());
		if (!Objects.isNull(instance)) {
			throw new BusinessException(ErrorCode.SCENE_ALREADY_EXIST);
		}
		Topology topology = topologyService.formatTopology(req.getTopologyId());
		String topoMeta = JsonUtil.toJsonString(topology);
		log.info("topology：{}", topoMeta);
		if (topology == null) {
			throw new BusinessException(ErrorCode.UNKNOWN_SCENE);
		}
		// 创建场景
		SceneEntity scene = createScene(req.getTopologyId(), topoMeta, req.getInstanceId(), userId,
				req.getInstanceType(), req.getDestroyTime());
		// 提交创建场景任务
		executor.submit(new SceneExecutor(sceneJob, topology, scene));
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
		SceneEntity scene = createScene(req.getId(), topo.toJSONString(), instanceId, req.getContext().getAccessUserId(),
				req.getContext().getInstanceType(), req.getContext().getDestroyTime());
		executor.submit(new SceneExecutor(sceneJob, topology, scene));
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

	private SceneData makeSceneData(SceneEntity scene) {
		SceneData sceneData = new SceneData();
		String topo;
		topo = scene.getMeta();
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
			Integer networkState = networkState(scene, false);
			Integer nodeStage = nodeStage(networkState, scene, false);
			List<WsSceneStage> wsSceneStages = stageList(networkState, nodeStage);
			Integer percentage = percentage(wsSceneStages);
			sceneDataRsp.setStageList(wsSceneStages);
			if (nodeStage >= networkState) {
				sceneDataRsp.setStage(StageEnum.CREATE_INSTANCE.getStage());
				sceneDataRsp.setStageName(StageEnum.CREATE_INSTANCE.getStageName());
				sceneDataRsp.setStagePercentage(nodeStage);
				sceneDataRsp.setProcessMessage(StageEnum.CREATE_INSTANCE.getStageName());
				sceneDataRsp.setProgressStatus(2);
			} else {
				sceneDataRsp.setStage(StageEnum.CREATE_NETWORK.getStage());
				sceneDataRsp.setStageName(StageEnum.CREATE_NETWORK.getStageName());
				sceneDataRsp.setStagePercentage(networkState);
				sceneDataRsp.setProcessMessage(StageEnum.CREATE_NETWORK.getStageName());
				sceneDataRsp.setProgressStatus(2);
			}
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

    private SceneEntity createScene(String topologyId,String topoMeta, String instanceId, String userId, Integer instanceType, Integer destroy){
        SceneEntity scene = new SceneEntity();
        scene.setResourceId(IDGeneratorUtil.getID());
        scene.setSceneName(IDGeneratorUtil.getID());
        Date startTime = new Date();
        scene.setStartTime(startTime);
        Date destroyTime;
        if (Objects.isNull(destroy)){
            destroyTime = TimeUtil.addHours(startTime, SCENE_DESTROY);
        }else {
            destroyTime = TimeUtil.addHours(startTime, destroy);
        }
        scene.setDestroyTime(destroyTime);
        scene.setInstanceType(instanceType);
        scene.setInstanceId(instanceId);
        scene.setTopologyId(topologyId);
        scene.setUserId(userId);
        scene.setSceneTtl(SCENE_DESTROY);
        scene.setSceneStatus(SceneEnum.INIT.getCode());
		scene.setMeta(topoMeta);
        sceneMapper.insert(scene);
        return scene;
    }

	/**
	 * 验证场景并发数
	 * @param userId
	 */
	@Override
	public void verify(String userId) {
		int sceneCount = sceneMapper.selectSceneCount(SceneEnum.getCodes(), null) + 1;
		// 场景数超过最大值
		if (sceneCount > MAX_SCENE_EVENT) {
			throw new BusinessException(ErrorCode.SCENE_EVENT_OVERFLOW);
		}

		int userSceneCount = sceneMapper.selectSceneCount(SceneEnum.getCodes(), userId) + 1;
		// 用户场景数超过最大值
		if (userSceneCount > MAX_USER_SCENE_EVENT) {
			throw new BusinessException(ErrorCode.USER_SCENE_EVENT_OVERFLOW);
		}
	}

    @Override
    public BaseRsp destroyScene(String sceneId) {
        SceneEntity scene = sceneMapper.selectByResourceId(sceneId);
        if (Objects.isNull(scene)){
            throw new BusinessException(ErrorCode.UNKNOWN_SCENE);
        }
        try {
            //删除节点
            nodeService.delete(sceneId);
            //删除网络
            List<String> networkIds = networkMapper.selectBySceneId(sceneId);
            if (!CollectionUtils.isEmpty(networkIds)){
                networkIds.forEach(iter -> {
                    networkService.delete(iter);
                    networkMapper.deleteByResourceId(iter);
                });
            }
//            sceneMapper.updateSceneStatus(sceneId, SceneEnum.DELETED.getCode());
			sceneMapper.deleteByResourceId(sceneId);
        }catch (Exception e){
            log.error("delete scene:[{}], error:{}", sceneId, e);
            throw new BusinessException(ErrorCode.SCENE_DESTROY_FAILED);
        }

        return new BaseRsp<>();
    }

    @Override
    public JSONObject getSceneJsonObject(String instanceId) {
        SceneData sceneData = getSceneData(instanceId);
        if (sceneData == null) {
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("scene", sceneData);
        return jsonObject;
    }


    private SceneData getSceneData(String instanceId){
        String userId = UserInfoContext.getNewUserContext().getResourceId();
        SceneEntity scene = sceneMapper.selectByInstanceId(instanceId, userId, SceneEnum.getCodes());
        if (Objects.isNull(scene)){
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
        if (Objects.equals(ImageTypeEnum.ELEMENT_TYPE_HOST.getCode(), type)){
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
        if (!repairFile.exists()){
            return;
        }
        log.info("Start run one click repair scene");
        List<SceneEntity> list = list(new LambdaQueryWrapper<SceneEntity>().in(SceneEntity::getSceneStatus, SceneEnum.getCodes()));
        if (!CollectionUtils.isEmpty(list)){
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
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(scene -> {
                destroyScene(scene.getResourceId());
                log.info("Destroy scene : {}", scene.getResourceId());
            });
        }
    }

    @Override
    public void sceneState() {
        // 获取运行中的场景
        List<SceneEntity> list = list(new LambdaQueryWrapper<SceneEntity>()
                .in(SceneEntity::getSceneStatus, Arrays.asList(SceneEnum.CREATE.getCode(), SceneEnum.ERROR.getCode())));
        if (!CollectionUtils.isEmpty(list)){
            log.warn("scene status is creating and error count is {}", list.size());
            for (SceneEntity scene : list){
                try {
                    if (Objects.equals(SceneEnum.CREATE.getCode(), scene.getSceneStatus())){
                        Integer networkState = networkState(scene, true);
						Integer nodeStage = nodeStage(networkState, scene, true);
                        if (Objects.equals(100, networkState) && Objects.equals(100, nodeStage)){
                            log.info("scene start success");
                            scene.setSceneStatus(SceneEnum.ACTIVE.getCode());
                            updateById(scene);
                            pushScenePlugin(scene, networkState, nodeStage, 2, 4);
                            pushCreationComplete(scene);
                        }
                    }else {
                        if (Boolean.TRUE.equals(DateUtil.dateComparison(scene.getCreateTime(), scene.getUpdateTime(), 15))) {
							// 更新修改时间
							updateById(scene);
                            // 场景错误状态超过15分钟不在推送，进度可以从业务接口中获取
							Integer networkState = networkState(scene, false);
							Integer nodeStage = nodeStage(networkState, scene, false);
                            pushScenePlugin(scene, networkState, nodeStage, 2, 5);
                        }
                    }
                }catch (Exception e){
                    log.error("scene {} state error : {}", scene, e);
                }
            }
        }
    }

	private Integer networkState(SceneEntity scene, boolean pushWs) {
		int count = 0;
		List<NetworkEntity> networks = networkService.list(new LambdaQueryWrapper<NetworkEntity>()
				.eq(NetworkEntity::getSid, scene.getResourceId())
				.eq(NetworkEntity::getNetworkStatus, SceneEnum.CREATE.getCode()));
		if (!CollectionUtils.isEmpty(networks)) {
			for (NetworkEntity network : networks) {
				NetworkInstance instance = networkService.getInstance(network.getUuid());
				if (Objects.isNull(instance)) {
					log.warn("scene {} network node not found is {}", scene.getResourceId(),
							network.getUuid());
					continue;
				}
				if (pushWs) {
					pushScenePlugin(scene, state(count, networks.size()), 0, 2, 2);
				}
				if (Objects.equals(StatusEnum.NETWORK_ACTIVE.getName(), instance.getStatus())) {
					networkMapper.updateStatus(network.getUuid(), SceneEnum.ACTIVE.getCode());
					count++;
					if (pushWs) {
						pushScenePlugin(scene, state(count, networks.size()), 0, 2, 2);
					}
				}
			}
		} else {
			return 0;
		}
		return state(count, networks.size());
	}


	private Integer nodeStage(Integer networkState, SceneEntity scene, boolean pushWs) {
		if (networkState == 0) {
			// 先启动网络，网络不存在，则节点一定没有启动
			return 0;
		}
		List<NodeEntity> nodes = nodeService.list(
				new LambdaQueryWrapper<NodeEntity>().eq(NodeEntity::getSid, scene.getResourceId())
						.eq(NodeEntity::getStatus, SceneEnum.CREATE.getCode()));
		int count = 0;
		if (!CollectionUtils.isEmpty(nodes)) {
			for (NodeEntity node : nodes) {
				Integer nodeStatus = SceneEnum.CREATE.getCode();
				String ip = null;
				int code = 2;
				TopologyElement element = getElement(node.getInstanceId(), scene.getMeta());

				if (pushWs) {
					// ? todo
					pushScenePlugin(scene, networkState, state(count, nodes.size()), 2, 2);
					pushElement(element, scene, 2);
				}
				if (Objects.equals(ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getCode(),
						node.getType())) {
					ContainerItem instance = containerService.getInstance(node.getInstanceId());
					if (Objects.nonNull(instance)) {
						if (StringUtils.equals(StatusEnum.ACTIVE.getName(), instance.getStatus())) {
							nodeStatus = SceneEnum.ACTIVE.getCode();
							JSONObject addresses = instance.getAddresses();
							ip = getIp(addresses);
							count++;
							code = 4;
						} else if (StringUtils.equals(StatusEnum.ERROR.getName(),
								instance.getStatus())) {
							nodeStatus = SceneEnum.ERROR.getCode();
							count++;
							code = 5;
						} else if (StringUtils.equals(StatusEnum.CREATING.getName(),
								instance.getStatus())
								&& !Boolean.TRUE.equals(DateUtil.dateComparison(
										node.getCreateTime(), node.getUpdateTime(), 10))) {
							// 节点创建时间超过10分钟，默认创建失败
							log.error("scene {} container_node creating long time is {}",
									scene.getResourceId(), node.getInstanceId());
							nodeStatus = SceneEnum.ERROR.getCode();
							count++;
							code = 5;
						}
						if (pushWs) {
							pushScenePlugin(scene, networkState, state(count, nodes.size()), 2, 2);
						}

					}
				}
				if (Objects.equals(ImageTypeEnum.ELEMENT_TYPE_HOST.getCode(), node.getType())) {
					CreateInstanceRes instance = hostService.getInstance(node.getInstanceId());
					if (Objects.nonNull(instance)) {
						if (StringUtils.equals(StatusEnum.ACTIVE.getName(),
								instance.getVmState())) {
							nodeStatus = SceneEnum.ACTIVE.getCode();
							JSONObject addresses = instance.getAddresses();
							ip = getIp(addresses);
							count++;
							code = 4;
						} else if (StringUtils.equals(StatusEnum.ERROR.getName(),
								instance.getVmState())) {
							nodeStatus = SceneEnum.ERROR.getCode();
							count++;
							code = 5;
						} else if (StringUtils.equals(StatusEnum.CREATING.getName(),
								instance.getVmState())
								&& !Boolean.TRUE.equals(DateUtil.dateComparison(
										node.getCreateTime(), node.getUpdateTime(), 10))) {
							log.error("scene {} vm_node creating long time is {}",
									scene.getResourceId(), node.getInstanceId());
							// 节点创建时间超过10分钟，默认创建失败
							nodeStatus = SceneEnum.ERROR.getCode();
							count++;
							code = 5;
						}
						if (pushWs) {
							pushScenePlugin(scene, state(count, nodes.size()), 0, 2, 2);
						}

					}
				}
				node.setIp(ip);
				node.setStatus(nodeStatus);
				nodeService.updateById(node);
				if (pushWs) {
					pushElement(element, scene, code);
				}
			}
		} else {
			return 0;
		}
		return state(count, nodes.size());
	}

    private TopologyElement getElement(String instanceId, String meta){
        Topology topology = JsonUtil.toJsonObject(meta, Topology.class);
        if (Objects.nonNull(topology)){
            List<TopologyElement> elements = topology.getElements();
            if (!CollectionUtils.isEmpty(elements)){
                for (TopologyElement element : elements){
                    if (StringUtils.equals(element.getNodeId(), instanceId)){
                        return element;
                    }
                }
            }
        }
        return null;
    }

    private String getIp(JSONObject addresses){
        String ip = null;
        if (Objects.isNull(addresses)){
            return ip;
        }
        Set<Map.Entry<String, Object>> entries = addresses.entrySet();
        for (Map.Entry<String, Object> entry : entries){
            JSONObject address = JSONObject.parseObject(entry.getValue().toString());
            if (address.containsKey("ip_address")){
                ip = address.getString("ip_address");
            }
        }
        return ip;
    }

    private Integer state(Integer dividend, Integer divisor){
        return (int)(((double) dividend/divisor) * 100);
    }

    private void sendMsg(WsData data, SceneEntity scene) {

        WsSceneReportCodeData codeData = new WsSceneReportCodeData(1, data);
        String sceneContent = JsonUtil.toJsonString(codeData, PropertyNamingStrategy.SnakeCase);

        MyWebSocketHandler webSocketHandler = SpringContextUtil.getBeanByClass(MyWebSocketHandler.class);
        SceneWsIdentifier identifier = new SceneWsIdentifier();
        identifier.setBusinessId(scene.getInstanceId());
        identifier.setUserId(scene.getUserId());
        identifier.setPath(WsPath.CR_SCENE);

        WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
                .builder().data(sceneContent).messageIdentifier(identifier).build();
        log.info("push Message ===>data:{}", JSON.toJSONString(data));
        // 只有单人场景，没有共享场景
        webSocketHandler.pushMessage(messageContext, false);
    }
    private void pushScenePlugin(SceneEntity scene, Integer networkStage, Integer instanceStage, Integer processStatus, Integer eventStatus){
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
        }catch (Exception e){
            log.error("push message error : {}", e);
        }
    }

    private Integer percentage(List<WsSceneStage> wsSceneStages){
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

    private void pushElement(TopologyElement element, SceneEntity scene, Integer code){
        try {
            WsSceneData sceneData = new WsSceneData();
            sceneData.setSceneId(scene.getResourceId());
            if(Objects.nonNull(element)){
                element.setEvent(2);
                element.setEventStatus(code);
            }
            sceneData.setSceneElement(element);
            JSONObject cpcloud = new JSONObject();
            cpcloud.put("cpcloud", getElementPlugin(element));
            sceneData.setSceneElementPortPluginData(cpcloud);
            sceneData.setSceneElementPluginData(makeElementPluginData(element));

            WsData data = new WsData();
            data.setType("scene_element");
            data.setData(sceneData);
            sendMsg(data, scene);
        }catch (Exception e){
            log.error("push message error : {}", e);
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

    private JSONObject getElementPlugin(TopologyElement element){
        if (Objects.nonNull(element)){
			JSONObject address = topologyService.getStatusEvent(element);
            JSONObject elementPortsJson = new JSONObject();
            for (ElementPort port : element.getPorts()){
                port.setIp(address.getString("ip"));
                port.setEvent(2);
                port.setEventStatus(address.getInteger("event_status"));
                JSONObject elementPortJson = new JSONObject();
                if (!CollectionUtils.isEmpty(port.getAccessModes())){
                    JSONArray newAccessModes = new JSONArray();
					port.getAccessModes().forEach(iter -> {
						iter.put("id", element.getNodeId());
						iter.put("ip", address.getString("ip"));
						iter.put("available", address.getBooleanValue("available"));
						iter.put("type", element.getType());
						// 端口映射
						if (null != iter.getJSONObject(JsonKeyConstant.PORT_MAPPING)) {
							PortMapping portMapping = iter
									.getJSONObject(JsonKeyConstant.PORT_MAPPING)
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
                elementPortJson.put("ip", address.getString("ip"));
                elementPortJson.put("event_status", address.getInteger("event_status"));
                elementPortJson.put("event", 2);
                elementPortJson.put("resource_id", element.getNodeId());
                elementPortsJson.put(port.getId(), elementPortJson);
            }
            return elementPortsJson;
        }
        return null;
    }

    private void pushCreationComplete(SceneEntity scene){
        JSONObject message = new JSONObject();
        message.put(JsonKeyConstant.CODE, CommonConstant.SCENE_CREATE_FINISH);
        SceneWsIdentifier identifier = new SceneWsIdentifier();
        identifier.setBusinessId(scene.getInstanceId());
        identifier.setUserId(scene.getUserId());
        identifier.setPath(WsPath.CR_SCENE);
        WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
                .builder().data(message).messageIdentifier(identifier).build();
        myWebSocketHandler.pushMessage(messageContext, false);
    }
}
