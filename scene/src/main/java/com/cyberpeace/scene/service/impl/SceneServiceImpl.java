package com.cyberpeace.scene.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.cyberpeace.scene.constant.enums.RoleEnum;
import com.cyberpeace.scene.constant.enums.SceneEnum;
import com.cyberpeace.scene.constant.enums.StatusEnum;
import com.cyberpeace.scene.controller.req.SceneStartReq;
import com.cyberpeace.scene.controller.rsp.ConnectionRsp;
import com.cyberpeace.scene.controller.rsp.SceneDataRsp;
import com.cyberpeace.scene.controller.rsp.SceneDetailRsp;
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
        if (Objects.isNull(scene)){
            return new BaseRsp<>();
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
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("cpcloud", setPlugin(topology));
                sceneData.setTopology(topology);
                sceneData.setScene(scene);
                sceneData.setPlugins(jsonObject);
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
        }
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
                    accessModes.forEach(iter -> {
                        JSONObject accessMode = JSONObject.parseObject(iter.toString());
                        accessMode.put("id", element.getNodeId());
                    });
                    elementPortJson.put("access_modes", accessModes);
                }
                elementPortJson.put("ip", address.getString("ip"));
                elementPortJson.put("event_status", address.getInteger("event_status"));
                elementPortJson.put("event", 2);
                elementPortJson.put("resource_id", element.getNodeId());
                elementPortsJson.put(port.getId(), elementPortJson);
            }
        }
        jsonObject.put("elements", elementsJson);
        jsonObject.put("element_ports", elementPortsJson);
        return jsonObject;
    }

    private JSONObject getStatusEvent(TopologyElement element){
        JSONObject jsonObject = new JSONObject();
        String ip = null;
        Integer eventStatus = 4;
        if (Objects.equals(ImageTypeEnum.ELEMENT_TYPE_HOST.getCode(), element.getType())){
            CreateInstanceRes instance = hostService.getInstance(element.getNodeId());
            if (Objects.nonNull(instance)){
                if (StringUtils.equals(StatusEnum.ACTIVE.getName(), instance.getVmState())){
                    eventStatus = 4;
                    JSONObject addresses = instance.getAddresses();
                    ip = getIp(addresses);
                }else if (StringUtils.equals(StatusEnum.ERROR.getName(), instance.getVmState())){
                    eventStatus = 5;
                } else if (StringUtils.equals(StatusEnum.CREATING.getName(), instance.getVmState())) {
                    eventStatus = 2;
                }
            }
        }
        if (Objects.equals(ImageTypeEnum.ELEMENT_TYPE_CONTAINER.getCode(), element.getType())){
            ContainerItem instance = containerService.getInstance(element.getNodeId());
            if (Objects.nonNull(instance)){
                if (StringUtils.equals(StatusEnum.ACTIVE.getName(), instance.getStatus())){
                    eventStatus = 4;
                    JSONObject addresses = instance.getAddresses();
                    ip = getIp(addresses);
                }else if (StringUtils.equals(StatusEnum.ERROR.getName(), instance.getStatus())){
                    eventStatus = 5;
                } else if (StringUtils.equals(StatusEnum.CREATING.getName(), instance.getStatus())) {
                    eventStatus = 2;
                }
            }
        }
        if (StringUtils.isNotBlank(ip)){
            nodeMapper.updateByInstanceId(element.getNodeId(), SceneEnum.ACTIVE.getCode());
        }else if (Objects.equals(5, eventStatus)){
            nodeMapper.updateByInstanceId(element.getNodeId(), SceneEnum.ERROR.getCode());
        }
        jsonObject.put("ip", ip);
        jsonObject.put("event_status", eventStatus);
        return jsonObject;
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

}
