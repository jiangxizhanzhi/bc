package com.cyberpeace.scene.job;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.compress.utils.Lists;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.scene.constant.enums.ImageTypeEnum;
import com.cyberpeace.scene.constant.enums.SceneEnum;
import com.cyberpeace.scene.dao.entity.NetworkEntity;
import com.cyberpeace.scene.dao.entity.NodeEntity;
import com.cyberpeace.scene.dao.entity.SceneEntity;
import com.cyberpeace.scene.service.*;
import com.cyberpeace.scene.service.bo.Net;
import com.cyberpeace.scene.service.bo.Topology;
import com.cyberpeace.scene.service.bo.TopologyElement;
import com.cyberpeace.scene.service.bo.req.CreateContainerReq;
import com.cyberpeace.scene.service.bo.req.CreateInstanceReq;
import com.cyberpeace.scene.service.bo.res.CreateContainersRes;
import com.cyberpeace.scene.service.bo.res.CreateInstanceRes;

import lombok.extern.slf4j.Slf4j;

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
                //创建网络
                NetworkEntity network = createNetwork(networkElement, scene.getResourceId());
                count++;
                List<Net> nets = Lists.newArrayList();
                nets.add(new Net(network.getUuid()));

                // 创建虚拟机
                if (topologyMap.containsKey(ImageTypeEnum.ELEMENT_TYPE_HOST.getName())){
                    List<TopologyElement> topologyElements = topologyMap.get(ImageTypeEnum.ELEMENT_TYPE_HOST.getName());
                    for (TopologyElement hostElement : topologyElements){
                        //创建虚拟机
						CreateInstanceRes res = hostService
								.create(new CreateInstanceReq(hostElement, network));
                        NodeEntity node = new NodeEntity(IDGeneratorUtil.getID(), res.getUuid(), res.getInstanceName(), null,
                                null, null, res.getCpu(), res.getMemory(), res.getDisk(), res.getImage(), scene.getResourceId(), ImageTypeEnum.ELEMENT_TYPE_HOST.getCode(),
                                SceneEnum.CREATE.getCode(), res.getAddresses().toJSONString(), scene.getUserId(), hostElement.getId());
                        hostElement.setNodeId(res.getUuid());
                        //保存节点信息
                        nodeService.save(node);
                        count++;
                    }
                }
                // 创建容器
                if (topologyMap.containsKey(ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getName())) {
                    List<TopologyElement> topologyElements = topologyMap.get(ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getName());
                    for (TopologyElement containerElement : topologyElements){
                        //创建容器
						CreateContainersRes res = containerService
								.create(new CreateContainerReq(containerElement, network));
                        NodeEntity node = new NodeEntity(IDGeneratorUtil.getID(), res.getUuid(), res.getName(), null,
                                null, null, res.getCpu(), res.getMemory(), res.getDisk(), res.getImage(), scene.getResourceId(), ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getCode(),
                                SceneEnum.CREATE.getCode(), null, scene.getUserId(), containerElement.getId());
                        containerElement.setNodeId(res.getUuid());
                        //保存节点信息
                        nodeService.save(node);
                        count++;
                    }
                }
                Thread.sleep(5 * 1000);
                scene.setSceneStatus(SceneEnum.CREATE.getCode());
            }catch (Exception e){
                log.error("Scene start failed,scene:{}, error:{}", scene.getSceneName(), e);
                scene.setSceneStatus(SceneEnum.ERROR.getCode());
                throw new BusinessException(e.getMessage());
            }finally {
                scene.setMeta(JsonUtil.toJsonString(topology));
                sceneService.updateById(scene);
            }
        }
        log.info("start scene end : {}", scene.getSceneName());
    }

    private Integer state(Integer dividend, Integer divisor, Integer scale){
        Double divide = MathUtils.divide((double) dividend, (double) divisor, scale);
        return divide.intValue() * 100;
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

}
