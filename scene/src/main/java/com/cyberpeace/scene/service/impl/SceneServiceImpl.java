package com.cyberpeace.scene.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.scene.dao.entity.SceneEntity;
import com.cyberpeace.scene.dao.mapper.SceneMapper;
import com.cyberpeace.scene.service.CpCloudService;
import com.cyberpeace.scene.service.SceneService;
import com.cyberpeace.scene.service.TopologyService;
import com.cyberpeace.scene.service.bo.NetworkInfo;
import com.cyberpeace.scene.service.bo.Topology;
import com.cyberpeace.scene.service.bo.TopologyElement;
import com.cyberpeace.scene.service.bo.req.CreateContainerReq;
import com.cyberpeace.scene.service.bo.req.CreateNetworkReq;
import com.cyberpeace.scene.service.bo.res.CreateNetworkRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

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
    private CpCloudService cpCloudService;

    /**
     * 场景自动销毁时间 单位:小时
     */
    private int SCENE_DESTROY;

    /**
     * 云平台地址
     */
    private String CP_HOST;

    /**
     * 网络接口
     */
    private String NETWORK;

    /**
     * 容器接口
     */
    private String CONTAINERS;

    @PostConstruct
    public void init(){
        SCENE_DESTROY = Integer.parseInt(ConfigUtil.getContextProperty("scene.destroy"));
        CP_HOST = ConfigUtil.getContextProperty("cp.host");
        NETWORK = ConfigUtil.getContextProperty("cp.network");
        CONTAINERS = ConfigUtil.getContextProperty("cp.containers");
    }

    @Override
    public SceneEntity startScene(String topologyId, String instanceId) {
        Topology topology = topologyService.formatTopology(topologyId);
        log.info("topology：{}", topology);
        if (topology == null){
            throw new BusinessException(ErrorCode.UNKNOWN_SCENE);
        }
        UserInfo userInfo = UserInfoContext.getUserContext();

        return null;
    }

    private SceneEntity createScene(String topologyId, String instanceId, String userId){
        SceneEntity scene = new SceneEntity();
        scene.setResourceId(IDGeneratorUtil.getID());
        scene.setSceneName(IDGeneratorUtil.getID());
        Date startTime = new Date();
        scene.setStartTime(startTime);
        Date destroyTime = TimeUtil.addHours(startTime, SCENE_DESTROY);
        scene.setDestroyTime(destroyTime);
        scene.setInstanceId(instanceId);
        scene.setTopologyId(topologyId);
        scene.setUserId(userId);
        scene.setSceneTtl(SCENE_DESTROY);
        save(scene);
        return scene;
    }

    private CreateNetworkRes createNetwork(Topology topology){
        CreateNetworkReq req = new CreateNetworkReq();
        String networkName = IDGeneratorUtil.getID();
        if (topology.getNetwork() != null){
            req.setCidr(topology.getNetwork().getCidr());
            req.setGateway(topology.getNetwork().getGateway());
        }else {
            log.info("Network information is empty, default configuration will be used");
        }
        req.setName(networkName);
        return cpCloudService.createNetwork(req);
    }

    private void createContainer(TopologyElement element, CreateContainerReq req){

    }

}
