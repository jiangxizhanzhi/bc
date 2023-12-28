package com.cyberpeace.scene.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.auth.domain.SysUser;
import com.cyberpeace.auth.service.bo.UserInfoContext;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.scene.constant.enums.SceneEnum;
import com.cyberpeace.scene.dao.entity.SceneEntity;
import com.cyberpeace.scene.dao.entity.NetworkEntity;
import com.cyberpeace.scene.dao.mapper.SceneMapper;
import com.cyberpeace.scene.dao.mapper.NetworkMapper;
import com.cyberpeace.scene.service.NetworkService;
import com.cyberpeace.scene.service.SceneService;
import com.cyberpeace.scene.service.TopologyService;
import com.cyberpeace.scene.service.bo.Topology;
import com.cyberpeace.scene.service.bo.TopologyElement;
import com.cyberpeace.scene.service.bo.req.CreateContainerReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Objects;

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
    private NetworkService networkService;

    @Autowired
    private NetworkMapper networkMapper;
    
    @Autowired
    private SceneMapper sceneMapper;

    /**
     * 场景自动销毁时间 单位:小时
     */
    private int SCENE_DESTROY;

    /**
     * 默认网段
     */
    private String DEFAULT_CIDR;

    /**
     * 默认网关
     */
    private String DEFAULT_GATEWAY;

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
        DEFAULT_CIDR = ConfigUtil.getContextProperty("network.cidr");
        DEFAULT_GATEWAY = ConfigUtil.getContextProperty("network.gateway");
        MAX_SCENE_EVENT = Integer.parseInt(ConfigUtil.getContextProperty("scene.max.event"));
        MAX_USER_SCENE_EVENT = Integer.parseInt(ConfigUtil.getContextProperty("scene.max.user.event"));
    }

    @Override
    public BaseRsp startScene(String topologyId, String instanceId) {
        Topology topology = topologyService.formatTopology(topologyId);
        log.info("topology：{}", topology);
        if (topology == null){
            throw new BusinessException(ErrorCode.UNKNOWN_SCENE);
        }
        NetworkEntity network = createNetwork(topology);
        log.info("network:{}", network);
        return new BaseRsp<>();
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

    /**
     * 创建网络
     * @param topology  拓扑信息
     * @return  网络实体
     */
    private NetworkEntity createNetwork(Topology topology){
        String cidr = topology.getNetwork() != null ? topology.getNetwork().getCidr() : null;
        String gateway = topology.getNetwork() != null ? topology.getNetwork().getGateway() : null;
        String networkName = IDGeneratorUtil.getID();
        return networkService.createNetwork(networkName, cidr, gateway);
    }

    private void createContainer(TopologyElement element, CreateContainerReq req){

    }

    /**
     * 验证场景并发数
     */
    @Override
    public void verify(){
        int sceneCount = sceneMapper.selectSceneCount(SceneEnum.ACTIVE.getCode(), null);
        //场景数超过最大值
        if (sceneCount >= MAX_SCENE_EVENT){
            throw new BusinessException(ErrorCode.SCENE_EVENT_OVERFLOW);
        }
        UserInfo userInfo = UserInfoContext.getUserContext();
        if (Objects.isNull(userInfo)){
            throw new BusinessException(ErrorCode.INVALID_SESSION);
        }
        int userSceneCount = sceneMapper.selectSceneCount(SceneEnum.ACTIVE.getCode(), userInfo.getUserId()) + 1;
        //用户场景数超过最大值
        if (userSceneCount >= MAX_USER_SCENE_EVENT){
            throw new BusinessException(ErrorCode.USER_SCENE_EVENT_OVERFLOW);
        }
    }

}
