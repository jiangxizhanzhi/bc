package com.cyberpeace.scene.service.impl;

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
import com.cyberpeace.scene.dao.mapper.SceneMapper;
import com.cyberpeace.scene.dao.mapper.NetworkMapper;
import com.cyberpeace.scene.job.SceneExecutor;
import com.cyberpeace.scene.job.SceneJob;
import com.cyberpeace.scene.service.NetworkService;
import com.cyberpeace.scene.service.SceneService;
import com.cyberpeace.scene.service.TopologyService;
import com.cyberpeace.scene.service.bo.Topology;
import com.cyberpeace.scene.util.SceneThreadPoolExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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

    @Autowired
    @Lazy
    private SceneThreadPoolExecutor executor;

    @Lazy
    @Autowired
    private SceneJob sceneJob;

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
        //创建场景
        SceneEntity scene = createScene(topologyId, instanceId, UserInfoContext.getNewUserContext().getResourceId());
        //提交创建场景任务
        executor.submit(new SceneExecutor(sceneJob, topology, scene));
        log.info("submit create scene mission, scene ：{}", scene);
        return new BaseRsp<>(scene);
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
        sceneMapper.insert(scene);
        return scene;
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
        SysUser userInfo = UserInfoContext.getNewUserContext();
        if (Objects.isNull(userInfo)){
            throw new BusinessException(ErrorCode.INVALID_SESSION);
        }
        int userSceneCount = sceneMapper.selectSceneCount(SceneEnum.ACTIVE.getCode(), userInfo.getResourceId()) + 1;
        //用户场景数超过最大值
        if (userSceneCount >= MAX_USER_SCENE_EVENT){
            throw new BusinessException(ErrorCode.USER_SCENE_EVENT_OVERFLOW);
        }
    }

}
