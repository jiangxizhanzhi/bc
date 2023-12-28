package com.cyberpeace.scene.job;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.scene.constant.enums.ImageTypeEnum;
import com.cyberpeace.scene.dao.entity.NetworkEntity;
import com.cyberpeace.scene.dao.entity.SceneEntity;
import com.cyberpeace.scene.service.*;
import com.cyberpeace.scene.service.bo.Topology;
import com.cyberpeace.scene.service.bo.TopologyElement;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;

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

    public SceneJob(){}

    /**
     * 启动场景
     */
    public void start(Topology topology, SceneEntity scene) {
        //获取拓扑元素
        Map<String, TopologyElement> topologyMap = topologyService.getTopologyElementMap(topology);
        if(Objects.nonNull(topologyMap)){
            try{
                TopologyElement networkElement = new TopologyElement();
                // 获取网络信息
                if (topologyMap.containsKey(ImageTypeEnum.ELEMENT_TYPE_INTERNET.getName())){
                    networkElement = topologyMap.get(ImageTypeEnum.ELEMENT_TYPE_INTERNET.getName());
                }
                //创建网络
                NetworkEntity network = createNetwork(networkElement);
                // 创建虚拟机
                if (topologyMap.containsKey(ImageTypeEnum.ELEMENT_TYPE_HOST.getName())){
                    TopologyElement hostElement = topologyMap.get(ImageTypeEnum.ELEMENT_TYPE_HOST.getName());
                }
                // 创建容器
                if (topologyMap.containsKey(ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getName())) {
                    TopologyElement containerElement = topologyMap.get(ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getName());
                }
            }catch (Exception e){
                log.error("Scene start failed,scene:{}, error:{}", scene.getSceneName(), e.getMessage());
                throw new BusinessException(e.getMessage());
            }
        }
    }

    /**
     * 创建网络
     * @param element  元素信息
     * @return  网络实体
     */
    private NetworkEntity createNetwork(TopologyElement element){
        String cidr = element.getNetwork() != null ? element.getNetwork().getCidr() : null;
        String gateway = element.getNetwork() != null ? element.getNetwork().getGateway() : null;
        String networkName = IDGeneratorUtil.getID();
        return networkService.createNetwork(networkName, cidr, gateway);
    }
}
