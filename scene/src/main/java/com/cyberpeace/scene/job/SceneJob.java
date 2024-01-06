package com.cyberpeace.scene.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.cs.service.context.WsSceneReportBO;
import com.cyberpeace.scene.constant.enums.ImageTypeEnum;
import com.cyberpeace.scene.constant.enums.SceneEnum;
import com.cyberpeace.scene.constant.enums.StageEnum;
import com.cyberpeace.scene.constant.enums.StatusEnum;
import com.cyberpeace.scene.dao.entity.NetworkEntity;
import com.cyberpeace.scene.dao.entity.NodeEntity;
import com.cyberpeace.scene.dao.entity.SceneEntity;
import com.cyberpeace.scene.dao.mapper.SceneMapper;
import com.cyberpeace.scene.service.*;
import com.cyberpeace.scene.service.bo.*;
import com.cyberpeace.scene.service.bo.req.CreateContainerReq;
import com.cyberpeace.scene.service.bo.req.CreateInstanceReq;
import com.cyberpeace.scene.service.bo.res.CreateContainersRes;
import com.cyberpeace.scene.service.bo.res.CreateInstanceRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;

import java.util.*;

/**
 * @author :   SN
 * @date :   2023/12/27 11:05
 * @desc :
 **/
@Slf4j
public class SceneJob {

    /**
     * 场景服务
     */
    SceneService sceneService;
    /**
     * 容器服务
     */
    ContainerService containerService;
    /**
     * 网络服务
     */
    NetworkService networkService;

    /**
     * 虚拟机服务
     */
    HostService hostService;

    /**
     * 拓扑服务
     */
    TopologyService topologyService;

    /**
     * 节点服务
     */
    NodeService nodeService;

    public void setSceneService(SceneService sceneService) {
        this.sceneService = sceneService;
    }

    public void setContainerService(ContainerService containerService) {
        this.containerService = containerService;
    }

    public void setNetworkService(NetworkService networkService) {
        this.networkService = networkService;
    }

    public void setHostService(HostService hostService) {
        this.hostService = hostService;
    }

    public void setTopologyService(TopologyService topologyService) {
        this.topologyService = topologyService;
    }

    public void setNodeService(NodeService nodeService){
        this.nodeService = nodeService;
    }

    public SceneJob(){}

    /**
     * 启动场景
     */
    public void start(Topology topology, SceneEntity scene) {
        log.info("start scene : {}", scene.getSceneName());
        //获取拓扑元素
        Map<String, List<TopologyElement>> topologyMap = topologyService.getTopologyElementMap(topology);
        SceneMapper sceneMapper = SpringContextUtil.getBeanByClass(SceneMapper.class);
        if(Objects.nonNull(topologyMap)){
            try{
                int count = 0;
                int size = topologyMap.values().stream().mapToInt(List::size).sum();
                TopologyElement networkElement = new TopologyElement();
                // 获取网络信息
                if (topologyMap.containsKey(ImageTypeEnum.ELEMENT_TYPE_INTERNET.getName())){
                    List<TopologyElement> topologyElements = topologyMap.get(ImageTypeEnum.ELEMENT_TYPE_INTERNET.getName());
                    networkElement = topologyElements.get(0);
                }
                //推送进度
                pushScenePlugin(scene, 25, 0, 2);
                //创建网络
                NetworkEntity network = createNetwork(networkElement, scene.getResourceId());
                count++;
                pushElement(networkElement, scene, SceneEnum.CREATE.getName());
                pushScenePlugin(scene, 100, 0, 4);
                List<Net> nets = Lists.newArrayList();
                nets.add(new Net(network.getUuid()));

                // 创建虚拟机
                if (topologyMap.containsKey(ImageTypeEnum.ELEMENT_TYPE_HOST.getName())){
                    List<TopologyElement> topologyElements = topologyMap.get(ImageTypeEnum.ELEMENT_TYPE_HOST.getName());
                    for (TopologyElement hostElement : topologyElements){
                        //创建虚拟机
                        CreateInstanceRes res = hostService.create(new CreateInstanceReq(hostElement.getName(), null, nets));
                        NodeEntity node = new NodeEntity(IDGeneratorUtil.getID(), res.getUuid(), res.getInstanceName(), null,
                                null, null, res.getCpu(), res.getMemory(), res.getDisk(), res.getImage(), scene.getResourceId(), ImageTypeEnum.ELEMENT_TYPE_HOST.getCode(),
                                SceneEnum.CREATE.getCode(), res.getAddresses().toJSONString(), scene.getUserId(), hostElement.getId());
                        hostElement.setNodeId(res.getUuid());
                        //保存节点信息
                        nodeService.save(node);
                        count++;
                        pushElement(hostElement, scene, SceneEnum.CREATE.getName());
                        pushScenePlugin(scene, 100, MathUtils.divide((double)size, (double)count, 2).intValue() * 100, 2);
                    }
                }
                // 创建容器
                if (topologyMap.containsKey(ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getName())) {
                    List<TopologyElement> topologyElements = topologyMap.get(ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getName());
                    for (TopologyElement containerElement : topologyElements){
                        //创建容器
                        CreateContainersRes res = containerService.create(new CreateContainerReq(containerElement.getName(), null, nets));
                        NodeEntity node = new NodeEntity(IDGeneratorUtil.getID(), res.getUuid(), res.getName(), null,
                                null, null, res.getCpu(), res.getMemory(), res.getDisk(), res.getImage(), scene.getResourceId(), ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getCode(),
                                SceneEnum.CREATE.getCode(), null, scene.getUserId(), containerElement.getId());
                        containerElement.setNodeId(res.getUuid());
                        //保存节点信息
                        nodeService.save(node);
                        count++;
                        pushElement(containerElement, scene, SceneEnum.ACTIVE.getName());
                        pushScenePlugin(scene, 100, MathUtils.divide((double)size, (double)count, 2).intValue() * 100, 2);
                    }
                }
                sceneMapper.updateMeta(scene.getResourceId(), JsonUtil.toJsonString(topology));
                //更新场景状态为运行中
                sceneMapper.updateSceneStatus(scene.getResourceId(), SceneEnum.ACTIVE.getCode());
                pushScenePlugin(scene, 100, 100, 2);
            }catch (Exception e){
                log.error("Scene start failed,scene:{}, error:{}", scene.getSceneName(), e);
                //更新场景状态为错误
                sceneMapper.updateSceneStatus(scene.getResourceId(), SceneEnum.ERROR.getCode());
                pushScenePlugin(scene, 0, 0, 5);
                throw new BusinessException(e.getMessage());
            }
        }
        log.error("start scene end : {}", scene.getSceneName());
    }

    /**
     * 创建网络
     * @param element  元素信息
     * @return  网络实体
     */
    private NetworkEntity createNetwork(TopologyElement element, String sceneId){
        String cidr = element.getNetwork() != null ? element.getNetwork().getCidr() : null;
        String gateway = element.getNetwork() != null ? element.getNetwork().getGateway() : null;
        String networkName = IDGeneratorUtil.getID();
        return networkService.create(networkName, cidr, gateway, sceneId, element.getId());
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
    private void pushScenePlugin(SceneEntity scene, Integer networkStage, Integer instanceStage, Integer processStatus){
        try {
            WsSceneData sceneData = new WsSceneData();
            sceneData.setSceneId(scene.getResourceId());
            WsScene wsScene = new WsScene();
            wsScene.setCreateConsumeSeconds(0);
            wsScene.setCreateTime(scene.getCreateTime());
            wsScene.setStage(StageEnum.CREATE_INSTANCE.getStage());
            List<WsSceneStage> wsSceneStages = stageList(networkStage, instanceStage);
            int sum = wsSceneStages.stream().mapToInt(WsSceneStage::getStagePercentage).sum();
            wsScene.setStageList(wsSceneStages);
            wsScene.setStageName(StageEnum.CREATE_INSTANCE.getStageName());
            int percentage = MathUtils.divide((double) (wsSceneStages.size() * 100), (double) sum, 2).intValue();
            wsScene.setStagePercentage(percentage);
            wsScene.setProcessPercentage(percentage);
            wsScene.setProcess(StageEnum.CREATE_INSTANCE.getStage());
            wsScene.setProcessMessage(StageEnum.CREATE_INSTANCE.getStageName());
            wsScene.setProcessStatus(processStatus);
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

    private void pushElement(TopologyElement element, SceneEntity scene, String code){
        try {
            WsSceneData sceneData = new WsSceneData();
            sceneData.setSceneId(scene.getResourceId());
            element.setEvent(2);
            element.setEventStatus(2);
            sceneData.setSceneElement(element);

            WsData data = new WsData();
            data.setType("scene_element");
            data.setData(sceneData);
            sendMsg(data, scene);
        }catch (Exception e){
            log.error("push message error : {}", e);
        }
    }

}
