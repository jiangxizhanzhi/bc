package com.cyberpeace.scene.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.auth.domain.SysUser;
import com.cyberpeace.auth.service.bo.UserInfoContext;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.*;
import com.cyberpeace.scene.constant.enums.ImageTypeEnum;
import com.cyberpeace.scene.constant.enums.SceneEnum;
import com.cyberpeace.scene.controller.rsp.ConnectionRsp;
import com.cyberpeace.scene.controller.rsp.SceneDataRsp;
import com.cyberpeace.scene.controller.rsp.SceneDetailRsp;
import com.cyberpeace.scene.dao.entity.SceneEntity;
import com.cyberpeace.scene.dao.entity.TopologyEntity;
import com.cyberpeace.scene.dao.mapper.SceneMapper;
import com.cyberpeace.scene.dao.mapper.NetworkMapper;
import com.cyberpeace.scene.job.SceneExecutor;
import com.cyberpeace.scene.job.SceneJob;
import com.cyberpeace.scene.service.*;
import com.cyberpeace.scene.service.bo.SceneData;
import com.cyberpeace.scene.service.bo.Topology;
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
import java.util.Date;
import java.util.List;
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
    public BaseRsp startScene(String topologyId, String instanceId) {
        String userId = UserInfoContext.getNewUserContext().getResourceId();
        SceneEntity instance = sceneMapper.selectByInstanceId(instanceId, userId, SceneEnum.getCodes());
        if (!Objects.isNull(instance)){
            throw new BusinessException(ErrorCode.SCENE_ALREADY_EXIST);
        }
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
        scene.setSceneStatus(SceneEnum.CREATE.getCode());
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
        return new BaseRsp<>();
    }

    @Override
    public BaseRsp detail(String topologyId, String instanceId) {
        String userId = UserInfoContext.getNewUserContext().getResourceId();
        SceneEntity scene = sceneMapper.selectByInstanceId(instanceId, userId, SceneEnum.getCodes());
        SceneData sceneData = new SceneData();
        String topo;
        if (scene != null){
            topo = scene.getMeta();
        }else {
            TopologyEntity topology = topologyService.selectTopology(topologyId);
            topo = topology.getTopology();
        }
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
            topology.setScene(sceneDataRsp);
            sceneData.setTopology(topology);
            sceneData.setScene(scene);
        }
        return new BaseRsp<>(new SceneDetailRsp(topologyId, sceneData));
    }

    @Override
    public BaseRsp console(String nodeId, Integer type) {
        ConnectionRsp connectionRsp = new ConnectionRsp();
        if (Objects.equals(ImageTypeEnum.ELEMENT_TYPE_HOST.getCode(), type)){
            connectionRsp.setConnectionUrl(hostService.console(nodeId).getUrl());
        } else if (Objects.equals(ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getCode(), type)) {
            connectionRsp.setConnectionUrl(containerService.console(nodeId).getUrl());
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

}
