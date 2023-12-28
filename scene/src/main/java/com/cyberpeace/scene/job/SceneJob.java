package com.cyberpeace.scene.job;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.IDGeneratorUtil;
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
import com.cyberpeace.scene.service.bo.res.CreateContainersRes;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
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
                List<Net> nets = Lists.newArrayList();
                nets.add(new Net(network.getUuid()));
                // 创建虚拟机
                if (topologyMap.containsKey(ImageTypeEnum.ELEMENT_TYPE_HOST.getName())){
                    TopologyElement hostElement = topologyMap.get(ImageTypeEnum.ELEMENT_TYPE_HOST.getName());
                }
                // 创建容器
                if (topologyMap.containsKey(ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getName())) {
                    TopologyElement containerElement = topologyMap.get(ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getName());
                    //创建容器
                    CreateContainersRes res = containerService.create(new CreateContainerReq(containerElement.getName(), null, nets));
                    NodeEntity node = new NodeEntity(IDGeneratorUtil.getID(), res.getUuid(), res.getName(), null,
                            null, null, res.getCpu(), res.getMemory(), res.getDisk(), res.getImage(), scene.getResourceId(), ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getCode(),
                            SceneEnum.ACTIVE.getCode(), res.getAddresses().toJSONString(), scene.getUserId());
                    //保存节点信息
                    nodeService.save(node);
                }
            }catch (Exception e){
                log.error("Scene start failed,scene:{}, error:{}", scene.getSceneName(), e.getMessage());
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
    private NetworkEntity createNetwork(TopologyElement element){
        String cidr = element.getNetwork() != null ? element.getNetwork().getCidr() : null;
        String gateway = element.getNetwork() != null ? element.getNetwork().getGateway() : null;
        String networkName = IDGeneratorUtil.getID();
        return networkService.createNetwork(networkName, cidr, gateway);
    }

}
