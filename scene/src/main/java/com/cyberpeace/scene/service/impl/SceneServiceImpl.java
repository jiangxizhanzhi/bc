package com.cyberpeace.scene.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.auth.domain.SysUser;
import com.cyberpeace.auth.service.bo.UserInfoContext;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.common.util.*;
import com.cyberpeace.scene.constant.enums.*;
import com.cyberpeace.scene.controller.req.SceneStartReq;
import com.cyberpeace.scene.controller.rsp.ConnectionRsp;
import com.cyberpeace.scene.controller.rsp.SceneDataRsp;
import com.cyberpeace.scene.controller.rsp.SceneDetailRsp;
import com.cyberpeace.scene.dao.entity.NetworkEntity;
import com.cyberpeace.scene.dao.entity.NodeEntity;
import com.cyberpeace.scene.dao.entity.SceneEntity;
import com.cyberpeace.scene.dao.entity.TopologyEntity;
import com.cyberpeace.scene.dao.mapper.NodeMapper;
import com.cyberpeace.scene.dao.mapper.SceneMapper;
import com.cyberpeace.scene.dao.mapper.NetworkMapper;
import com.cyberpeace.scene.job.SceneExecutor;
import com.cyberpeace.scene.job.SceneJob;
import com.cyberpeace.scene.service.*;
import com.cyberpeace.scene.service.bo.*;
import com.cyberpeace.scene.service.bo.res.CreateInstanceRes;
import com.cyberpeace.scene.util.HttpUtil;
import com.cyberpeace.scene.util.SceneThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.*;

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
    private HostService hostService;

    @Autowired
    private ContainerService containerService;

    @Autowired
    private NodeMapper nodeMapper;

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
    public void init(){
        SCENE_DESTROY = Integer.parseInt(ConfigUtil.getContextProperty("scene.destroy"));
        MAX_SCENE_EVENT = Integer.parseInt(ConfigUtil.getContextProperty("scene.max.event"));
        MAX_USER_SCENE_EVENT = Integer.parseInt(ConfigUtil.getContextProperty("scene.max.user.event"));
    }

    @Override
    public BaseRsp startScene(SceneStartReq req) {
        String userId = UserInfoContext.getNewUserContext().getResourceId();
        SceneEntity instance = sceneMapper.selectByInstanceId(req.getInstanceId(), userId, SceneEnum.getCodes());
        if (!Objects.isNull(instance)){
            throw new BusinessException(ErrorCode.SCENE_ALREADY_EXIST);
        }
        Topology topology = topologyService.formatTopology(req.getTopologyId());
        log.info("topology：{}", topology);
        if (topology == null){
            throw new BusinessException(ErrorCode.UNKNOWN_SCENE);
        }
        //创建场景
        SceneEntity scene = createScene(req.getTopologyId(), req.getInstanceId(), UserInfoContext.getNewUserContext().getResourceId(),
                req.getInstanceType(), req.getDestroyTime());
        //提交创建场景任务
        executor.submit(new SceneExecutor(sceneJob, topology, scene));
        log.info("submit create scene mission, scene ：{}", scene);
        return new BaseRsp<>(scene);
    }

    private SceneEntity createScene(String topologyId, String instanceId, String userId, Integer instanceType, Integer destroy){
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
        sceneMapper.insert(scene);
        return scene;
    }

    /**
     * 验证场景并发数
     */
    @Override
    public void verify(){
        int sceneCount = sceneMapper.selectSceneCount(SceneEnum.getCodes(), null) + 1;
        //场景数超过最大值
        if (sceneCount > MAX_SCENE_EVENT){
            throw new BusinessException(ErrorCode.SCENE_EVENT_OVERFLOW);
        }
        SysUser userInfo = UserInfoContext.getNewUserContext();
        if (Objects.isNull(userInfo)){
            throw new BusinessException(ErrorCode.INVALID_SESSION);
        }
        int userSceneCount = sceneMapper.selectSceneCount(SceneEnum.getCodes(), userInfo.getResourceId()) + 1;
        //用户场景数超过最大值
        if (userSceneCount > MAX_USER_SCENE_EVENT){
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
            sceneMapper.updateSceneStatus(sceneId, SceneEnum.DELETED.getCode());
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
        SceneData sceneData = new SceneData();
        String topo;
        topo = scene.getMeta();
        Date currentTime = new Date();
        long remainTime = (scene.getDestroyTime().getTime() - currentTime.getTime())/1000;
        sceneData.setRemainingTime((int) remainTime);
        if (StringUtils.isNotBlank(topo)){
            Topology topology = JsonUtil.toJsonObject(topo, Topology.class);
            SceneDataRsp sceneDataRsp = new SceneDataRsp();
            BeanUtils.copyProperties(scene, sceneDataRsp);
            if (Objects.equals(SceneEnum.ACTIVE.getCode(), scene.getSceneStatus())){
                sceneDataRsp.setEvent(2);
                sceneDataRsp.setEventStatus(4);
            } else if (Objects.equals(SceneEnum.CREATE.getCode(), scene.getSceneStatus())) {
                sceneDataRsp.setEvent(2);
                sceneDataRsp.setEventStatus(2);
            }else if (Objects.equals(SceneEnum.DELETED.getCode(), scene.getSceneStatus())){
                sceneDataRsp.setEvent(9);
                sceneDataRsp.setEventStatus(4);
            }else if (Objects.equals(SceneEnum.ERROR.getCode(), scene.getSceneStatus())){
                sceneDataRsp.setEvent(2);
                sceneDataRsp.setEventStatus(5);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cpcloud", setPlugin(topology));
            sceneData.setTopology(topology);
            sceneData.setScene(scene);
            sceneData.setPlugins(jsonObject);
            Integer networkState = networkState(scene);
            Integer nodeStage = nodeStage(scene);
            List<WsSceneStage> wsSceneStages = stageList(networkState, nodeStage);
            Integer percentage = percentage(wsSceneStages);
            sceneDataRsp.setStageList(wsSceneStages);
            if (nodeStage >= networkState){
                sceneDataRsp.setStage(StageEnum.CREATE_INSTANCE.getStage());
                sceneDataRsp.setStageName(StageEnum.CREATE_INSTANCE.getStageName());
                sceneDataRsp.setStagePercentage(nodeStage);
                sceneDataRsp.setProgressMessage(StageEnum.CREATE_INSTANCE.getStageName());
                sceneDataRsp.setProgressStatus(2);
            }else {
                sceneDataRsp.setStage(StageEnum.CREATE_NETWORK.getStage());
                sceneDataRsp.setStageName(StageEnum.CREATE_NETWORK.getStageName());
                sceneDataRsp.setStagePercentage(networkState);
                sceneDataRsp.setProgressMessage(StageEnum.CREATE_NETWORK.getStageName());
                sceneDataRsp.setProgressStatus(2);
            }
            sceneDataRsp.setProgressPercentage(percentage);
            topology.setScene(sceneDataRsp);
        }
        return sceneData;
    }

    @Override
    public BaseRsp detail(String topologyId, String instanceId) {
        SceneData sceneData = getSceneData(instanceId);
        return new BaseRsp<>(new SceneDetailRsp(topologyId, sceneData));
    }

    private JSONObject setPlugin(Topology topology){
        JSONObject jsonObject = new JSONObject();
        if (Objects.isNull(topology)){
            return jsonObject;
        }
        JSONObject elementsJson = new JSONObject();
        JSONObject elementPortsJson = new JSONObject();
        List<TopologyElement> elements = topology.getElements();
        int sum = elements.stream().mapToInt(e -> e.getPorts().size()).sum();
        List<TopologyElement> newElements = new ArrayList<>();
        for (TopologyElement element : elements){
            JSONObject elementJson = new JSONObject();
            RoleEnum roleEnum = RoleEnum.getRoleEnum(element.getType());
            if (!Objects.isNull(roleEnum)){
                elementJson.put("role", roleEnum.getValue());
                elementJson.put("image_type", roleEnum.getImageType());
                elementJson.put("electric_port_number", sum);
            }
            elementsJson.put(element.getId(), elementJson);
            JSONObject address = getStatusEvent(element);
            for (ElementPort port : element.getPorts()){
                port.setIp(address.getString("ip"));
                port.setEvent(2);
                port.setEventStatus(address.getInteger("event_status"));
                JSONObject elementPortJson = new JSONObject();
                if (!CollectionUtils.isEmpty(port.getAccessModes())){
                    JSONArray accessModes = JSONArray.parseArray(port.getAccessModes().toString());
                    JSONArray newAccessModes = new JSONArray();
                    accessModes.forEach(iter -> {
                        JSONObject accessMode = JSONObject.parseObject(iter.toString());
                        accessMode.put("id", element.getNodeId());
                        accessMode.put("ip", address.getString("ip"));
                        accessMode.put("available", address.getBooleanValue("available"));
                        accessMode.put("type", element.getType());
                        newAccessModes.add(accessMode);
                    });
                    elementPortJson.put("access_modes", newAccessModes);
                }
                elementPortJson.put("ip", address.getString("ip"));
                elementPortJson.put("event_status", address.getInteger("event_status"));
                elementPortJson.put("event", 2);
                elementPortJson.put("resource_id", element.getNodeId());
                elementPortsJson.put(port.getId(), elementPortJson);
                newElements.add(element);
            }
        }
        jsonObject.put("elements", elementsJson);
        jsonObject.put("element_ports", elementPortsJson);
        topology.setElements(newElements);
        return jsonObject;
    }

    private JSONObject getStatusEvent(TopologyElement element){
        JSONObject jsonObject = new JSONObject();
        String ip = null;
        int eventStatus = 4;
        boolean available = false;
        NodeEntity node = nodeService.getOne(new LambdaQueryWrapper<NodeEntity>().eq(NodeEntity::getInstanceId, element.getNodeId()));
        if (Objects.nonNull(node)){
            ip = node.getIp();
            if (Objects.equals(SceneEnum.ACTIVE.getCode(), node.getStatus())){
                eventStatus = 4;
                available = true;
            }else if (Objects.equals(SceneEnum.ERROR.getCode(), node.getStatus())){
                eventStatus = 5;
            } else if (Objects.equals(SceneEnum.CREATE.getCode(), node.getStatus())) {
                eventStatus = 2;
            }
        }
        element.setEvent(2);
        element.setEventStatus(eventStatus);
        jsonObject.put("ip", ip);
        jsonObject.put("event_status", eventStatus);
        jsonObject.put("available", available);
        return jsonObject;
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
            for (SceneEntity scene : list){
                try {
                    if (Objects.equals(SceneEnum.CREATE.getCode(), scene.getSceneStatus())){
                        Integer networkState = networkState(scene);
                        Integer nodeStage = nodeStage(scene);
                        if (Objects.equals(100, networkState) && Objects.equals(100, nodeStage)){
                            log.info("scene start success");
                            scene.setSceneStatus(SceneEnum.ACTIVE.getCode());
                            updateById(scene);
                            pushScenePlugin(scene, networkState, nodeStage, 2, 4);
                        }
                    }else {
                        pushScenePlugin(scene, 0, 0, 2, 5);
                    }
                }catch (Exception e){
                    log.error("scene {} state error : {}", scene, e);
                }
            }
        }
    }

    private Integer networkState(SceneEntity scene){
        int count = 0;
        List<NetworkEntity> networks = networkService.list(new LambdaQueryWrapper<NetworkEntity>()
                .eq(NetworkEntity::getSid, scene.getResourceId())
                .eq(NetworkEntity::getNetworkStatus, SceneEnum.CREATE.getCode()));
        if (!CollectionUtils.isEmpty(networks)){
            for (NetworkEntity network : networks){
                NetworkInstance instance = networkService.getInstance(network.getUuid());
                if (Objects.isNull(instance)){
                    continue;
                }
                pushScenePlugin(scene, state(count, networks.size()), 0, 2, 2);
                if (Objects.equals(StatusEnum.NETWORK_ACTIVE.getName(), instance.getStatus())){
                    networkMapper.updateStatus(network.getUuid(), SceneEnum.ACTIVE.getCode());
                    count++;
                    pushScenePlugin(scene, state(count, networks.size()), 0, 2, 2);
                }
            }
        }else {
            return 100;
        }
        return state(count, networks.size());
    }


    private Integer nodeStage(SceneEntity scene) {
        List<NodeEntity> nodes = nodeService.list(new LambdaQueryWrapper<NodeEntity>()
                .eq(NodeEntity::getSid, scene.getResourceId())
                .eq(NodeEntity::getStatus, SceneEnum.CREATE.getCode()));
        int count = 0;
        if (!CollectionUtils.isEmpty(nodes)){
            for (NodeEntity node : nodes){
                Integer nodeStatus = SceneEnum.CREATE.getCode();
                String ip = null;
                Integer code = 2;
                TopologyElement element = getElement(node.getInstanceId(), scene.getMeta());
                pushScenePlugin(scene, state(count, nodes.size()), 0, 2, 2);
                pushElement(element, scene, 2);
                if (Objects.equals(ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getCode(), node.getType())){
                    ContainerItem instance = containerService.getInstance(node.getInstanceId());
                    if (Objects.nonNull(instance)){
                        code = 2;
                        if (StringUtils.equals(StatusEnum.ACTIVE.getName(), instance.getStatus())){
                            nodeStatus = SceneEnum.ACTIVE.getCode();
                            JSONObject addresses = instance.getAddresses();
                            ip = getIp(addresses);
                            count++;
                            code = 4;
                        }else if (StringUtils.equals(StatusEnum.ERROR.getName(), instance.getStatus())){
                            nodeStatus = SceneEnum.ERROR.getCode();
                            count++;
                            code = 5;
                        }
                        pushScenePlugin(scene, state(count, nodes.size()), 0, 2, 2);
                    }
                }
                if (Objects.equals(ImageTypeEnum.ELEMENT_TYPE_HOST.getCode(), node.getType())){
                    CreateInstanceRes instance = hostService.getInstance(node.getInstanceId());
                    if (Objects.nonNull(instance)){
                        if (StringUtils.equals(StatusEnum.ACTIVE.getName(), instance.getVmState())){
                            nodeStatus = SceneEnum.ACTIVE.getCode();
                            JSONObject addresses = instance.getAddresses();
                            ip = getIp(addresses);
                            count++;
                            code = 4;
                        }else if (StringUtils.equals(StatusEnum.ERROR.getName(), instance.getVmState())){
                            nodeStatus = SceneEnum.ERROR.getCode();
                            count++;
                            code = 5;
                        }
                        pushScenePlugin(scene, state(count, nodes.size()), 0, 2, 2);
                    }
                }
                node.setIp(ip);
                node.setStatus(nodeStatus);
                nodeService.updateById(node);
                pushElement(element, scene, code);
            }
        }else {
            return 100;
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
        webSocketHandler.pushMessage(messageContext, true);
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
            wsScene.setProgressMessage(StageEnum.CREATE_INSTANCE.getStageName());
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


    private List<WsSceneStage> stageList(Integer networkStage, Integer instanceStage){
        List<WsSceneStage> list = new ArrayList<>();
        // 初始状态
        list.add(new WsSceneStage(StageEnum.INIT.getStage(), StageEnum.INIT.getStageName(), StageEnum.INIT.getIcon(), 100));
        // 网络状态
        list.add(new WsSceneStage(StageEnum.CREATE_NETWORK.getStage(), StageEnum.CREATE_NETWORK.getStageName(), StageEnum.CREATE_NETWORK.getIcon(), networkStage));
        // 实例状态
        list.add(new WsSceneStage(StageEnum.CREATE_INSTANCE.getStage(), StageEnum.CREATE_INSTANCE.getStageName(), StageEnum.CREATE_INSTANCE.getIcon(), instanceStage));
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
            sceneData.setSceneElementPortPluginData(getElementPlugin(element));

            WsData data = new WsData();
            data.setType("scene_element");
            data.setData(sceneData);
            sendMsg(data, scene);
        }catch (Exception e){
            log.error("push message error : {}", e);
        }
    }

    private JSONObject getElementPlugin(TopologyElement element){
        if (Objects.nonNull(element)){
            JSONObject address = getStatusEvent(element);
            JSONObject elementPortsJson = new JSONObject();
            for (ElementPort port : element.getPorts()){
                port.setIp(address.getString("ip"));
                port.setEvent(2);
                port.setEventStatus(address.getInteger("event_status"));
                JSONObject elementPortJson = new JSONObject();
                if (!CollectionUtils.isEmpty(port.getAccessModes())){
                    JSONArray accessModes = JSONArray.parseArray(port.getAccessModes().toString());
                    JSONArray newAccessModes = new JSONArray();
                    accessModes.forEach(iter -> {
                        JSONObject accessMode = JSONObject.parseObject(iter.toString());
                        accessMode.put("id", element.getNodeId());
                        accessMode.put("ip", address.getString("ip"));
                        accessMode.put("available", address.getBooleanValue("available"));
                        accessMode.put("type", element.getType());
                        newAccessModes.add(accessMode);
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
}
