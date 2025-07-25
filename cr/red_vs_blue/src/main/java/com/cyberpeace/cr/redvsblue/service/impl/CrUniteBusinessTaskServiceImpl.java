package com.cyberpeace.cr.redvsblue.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.crbase.service.context.scene.RbSceneViewContext;
import com.cyberpeace.cr.redvsblue.controller.req.UniteAttackReq;
import com.cyberpeace.cr.redvsblue.controller.req.UniteFlowAttackReq;
import com.cyberpeace.cr.redvsblue.dao.entity.CrUniteBusinessLink;
import com.cyberpeace.cr.redvsblue.dao.entity.CrUniteBusinessNode;
import com.cyberpeace.cr.redvsblue.dao.entity.CrUniteBusinessTask;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.dao.mapper.CrUniteBusinessTaskMapper;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTemplateMapper;
import com.cyberpeace.cr.redvsblue.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.*;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomContext;
import com.cyberpeace.cr.redvsblue.vo.CrUniteBusinessTaskVO;
import com.cyberpeace.cr.redvsblue.vo.NetworkTaskVO;
import com.cyberpeace.cr.redvsblue.vo.NetworkTopoVO;
import com.cyberpeace.cr.redvsblue.ws.WebSocketClientService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author SN
 */
@Service
@Slf4j
public class CrUniteBusinessTaskServiceImpl extends ServiceImpl<CrUniteBusinessTaskMapper, CrUniteBusinessTask>
        implements CrUniteBusinessTaskService {

    @Autowired
    private RbTemplateMapper rbTemplateMapper;
    @Autowired
    private BusinessTemplateMapper businessTemplateMapper;
    @Autowired
    private RemoteSceneService remoteSceneService;
    @Autowired
    private CrUniteBusinessNodeService crUniteBusinessNodeService;
    @Autowired
    private CrUniteBusinessLinkService crUniteBusinessLinkService;
    @Autowired
    private RbRoomService rbRoomService;
    @Autowired
    private RbSceneService rbSceneService;
    @Autowired
    private LocalSceneService localSceneService;
    @Lazy
    @Autowired
    private WebSocketClientService webSocketClientService;

    //调用预处理方法
    @KafkaListener  (topics = "topic_network")
    public void geojsonProcess(byte[] message) {
        try {
            log.info("训练任务开始接收");
            ObjectMapper objectMapper = new ObjectMapper();
            NetworkTaskVO networkTaskVO = objectMapper.readValue(message, NetworkTaskVO.class);
            String jsonString = JSONObject.toJSONString(networkTaskVO);
            log.info("接收到训练任务信息: "+jsonString);
            saveUniteBusinessTask(networkTaskVO);
        } catch (Exception e){
            log.error("接收训练任务  ===> 发生异常:", e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUniteBusinessTask(NetworkTaskVO networkTaskVO) {
        log.info("开始保存训练任务");
        // 校验任务id是否已存在
        String taskId = networkTaskVO.getId();
        CrUniteBusinessTask existingTask = getBaseMapper().selectById(taskId);
        if (existingTask != null) {
            log.warn("训练任务ID已存在，不进行新增操作: {}", taskId);
            return;
        }
        CrUniteBusinessTask crUniteBusinessTask = new CrUniteBusinessTask();
        crUniteBusinessTask.setTaskId(networkTaskVO.getId());
        crUniteBusinessTask.setTaskName(networkTaskVO.getTaskName());
        crUniteBusinessTask.setTaskDescription(networkTaskVO.getTaskContent());
        crUniteBusinessTask.setTrainTarget(networkTaskVO.getTaskContent());
        crUniteBusinessTask.setStartTime(networkTaskVO.getStartDt());

//        保存任务表
        getBaseMapper().insert(crUniteBusinessTask);
        log.info("保存训练任务记录");
//        保存任务模板 type 6
        BusinessTemplate businessTemplate = new BusinessTemplate();
        businessTemplate.setUniteTaskId(crUniteBusinessTask.getTaskId());
        businessTemplate.setBusinessType(BusinessType.UNITE_TRAIN.getCode());
        businessTemplate.setName(crUniteBusinessTask.getTaskName());
        businessTemplate.setDescription(crUniteBusinessTask.getTaskDescription());
        businessTemplate.setTrainTarget(crUniteBusinessTask.getTrainTarget());
        businessTemplate.setStartTime(crUniteBusinessTask.getStartTime());
        businessTemplate.setEndTime(crUniteBusinessTask.getEndTime());

//        避免保存时报错
        businessTemplate.setLocalSceneId("");
        businessTemplate.setTopoId("");
        businessTemplate.setDifficulty(1);
        businessTemplate.setCreateUserId("3f655d87112f4ae094bbaf70830e20aa");
        businessTemplateMapper.insert(businessTemplate);
        log.info("保存训练任务-联合训练模板");
//        判断是否已有该任务的设备信息下发 已下发转到生成拓扑方法生成拓扑
        NetworkTopoVO networkTopoVO = retrieveNetworkTopologyByTaskId(businessTemplate.getUniteTaskId(),null);
        if(networkTopoVO.getNodes() != null && networkTopoVO.getNodes().size()>0){
            log.info("保存训练任务-生成拓扑");
            sceneRelease(networkTopoVO);
        }
    }

    /**
     * （CrUniteBusinessNode）和链接（CrUniteBusinessLink），并将它们封装到 NetworkTopoVO 对象中返回
     * @param taskId
     * @return
     */
    @Override
    public NetworkTopoVO retrieveNetworkTopologyByTaskId(String taskId, String topoId) {
        NetworkTopoVO networkTopoVO = new NetworkTopoVO();
        networkTopoVO.setTask_id(taskId);
        QueryWrapper<CrUniteBusinessNode> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.lambda().eq(CrUniteBusinessNode::getTaskId, taskId);
        List<CrUniteBusinessNode> nodes = crUniteBusinessNodeService.list(nodeQueryWrapper);
        QueryWrapper<CrUniteBusinessLink> linkQueryWrapper = new QueryWrapper<>();
        linkQueryWrapper.lambda().eq(CrUniteBusinessLink::getTaskId, taskId);
        List<CrUniteBusinessLink> links = crUniteBusinessLinkService.list();
//        组装工具信息
        HashMap<String, JSONArray> attackToolsMap = new HashMap<>();
        HashMap<String, Boolean> isAttackerMap = new HashMap<>();
        if(StringUtils.isNotBlank(topoId)){
            JSONObject topoData = localSceneService.getTopoData(topoId)
                    .getJSONObject(CrCommonConstants.TOPO_DATA_KEY);
            JSONArray jsonArray = topoData.getJSONObject("topology").getJSONArray("elements");
            Map<String, String> uniteIdMap = jsonArray.toJavaList(JSONObject.class)
                    .stream()
                    .filter(jsonObject -> StringUtils.isNotBlank(jsonObject.getString("unite_number_id")))
                    .collect(Collectors.toMap(o -> o.getString("id"), o -> o.getString("unite_number_id")));
            JSONObject jsonObject = topoData.getJSONObject("plugins").getJSONObject("cpcloud").getJSONObject("elements");
            jsonObject.entrySet().forEach(entry -> {
                String key = entry.getKey();
                JSONObject value = (JSONObject) entry.getValue();
                String uniteId = uniteIdMap.get(key);
                if(uniteId!=null){
                    attackToolsMap.put(uniteId,value.getJSONArray("attack_tools"));
                    isAttackerMap.put(uniteId,value.getBoolean("is_attacker"));
                }
            });
        }
        nodes.forEach(node -> {
            String numberId = node.getNumberId();
            JSONArray jsonArray = attackToolsMap.get(numberId);
            node.setAttackTools(jsonArray);
            node.setIsAttacker(isAttackerMap.get(numberId));
        });

        if(nodes.size()>0||links.size()>0){
            networkTopoVO.setNodes(nodes);
            networkTopoVO.setLinks(links);
        }
        JSONObject jsonObject = new JSONObject();
        JSONObject dataJsonObject = new JSONObject();
        dataJsonObject.put("data", networkTopoVO);
        jsonObject.put("Data", dataJsonObject.toJSONString());
        jsonObject.put("Function", "NetworkTopoData");
        jsonObject.put("ReceiveObjectName", "");
        jsonObject.put("SendObjectName", "");
//        发送构建数据
        String jsonString = jsonObject.toJSONString();

        webSocketClientService.sendMessage(jsonString);
        return networkTopoVO;
    }

    @Override
    public Boolean attack(UniteAttackReq uniteAttackReq) {
//        场景id 源节点id 目标节点id 攻击id 工具参数
        String taskId = uniteAttackReq.getTaskId();
        RbRoom room = rbRoomService.getRbRoomByUniteTaskId(taskId);
        RbSceneViewContext rbSceneViewContext = rbSceneService.viewScene(room.getResourceId(), null);
        JSONObject sceneData = rbSceneViewContext.getSceneData();
        List<JSONObject> elements = sceneData.getJSONObject("topology")
                .getJSONArray("elements")
                .toJavaList(JSONObject.class);
        Map<String, String> map = elements.stream()
                .filter(element -> StringUtils.isNotBlank(element.getString("unite_number_id")))
                .collect(Collectors.toMap(o -> o.getString("unite_number_id"), o -> o.getString("id")));
        JSONObject pluginElements = sceneData.getJSONObject("plugins")
                .getJSONObject("cpcloud")
                .getJSONObject("elements");
        String sourceNode = map.get(uniteAttackReq.getAttackerId());
        String targetNode = map.get(uniteAttackReq.getTargetId());
        if(sourceNode!=null&&targetNode!=null){
            JSONArray attackTools = pluginElements.getJSONObject(map.get(uniteAttackReq.getTargetId()))
                    .getJSONArray("attack_tools");
            if(null!=attackTools && attackTools.size()>0){
                Map<String, JSONArray> paramsMap = attackTools.toJavaList(JSONObject.class)
                        .stream().collect(Collectors.toMap(o -> o.getString("id"), o -> o.getJSONArray("params")));
                JSONArray jsonArray = paramsMap.get(uniteAttackReq.getToolId());
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",rbSceneViewContext.getMpSceneId());
                jsonObject.put("source_node",sourceNode);
                jsonObject.put("target_node",targetNode);
                jsonObject.put("tool_id",uniteAttackReq.getToolId());
                jsonObject.put("params",jsonArray);
                jsonObject.put("task_id",uniteAttackReq.getTaskId());
                jsonObject.put("target_id",uniteAttackReq.getTargetId());
                jsonObject.put("attack_starttime",uniteAttackReq.getAttackStarttime());
                jsonObject.put("attack_endtime",uniteAttackReq.getAttackEndtime());
                String jsonString = jsonObject.toJSONString();
                remoteSceneService.attack(jsonString);
            }
        }
        return null;
    }

    @Override
    public Boolean attack(String jsonString) {
        log.info("接收到攻击指令：{}", jsonString);
        UniteAttackReq uniteAttackReq = JSON.parseObject(jsonString, JSONObject.class).getJSONObject("data").toJavaObject(UniteAttackReq.class);
        attack(uniteAttackReq);
        return true;
    }

    @Override
    public void flowStart(String data) {
        log.info("接受到流量攻击指令：{}",data);
        UniteFlowAttackReq uniteFlowAttackReq = JSON.parseObject(data, JSONObject.class).getJSONObject("data").toJavaObject(UniteFlowAttackReq.class);
        flowStart(uniteFlowAttackReq);
    }
    @Override
    public void flowStart(UniteFlowAttackReq uniteFlowAttackReq) {
        String taskId = uniteFlowAttackReq.getTaskId();
        RbRoom room = rbRoomService.getRbRoomByUniteTaskId(taskId);
        RbSceneViewContext rbSceneViewContext = rbSceneService.viewScene(room.getResourceId(), null);
        JSONObject sceneData = rbSceneViewContext.getSceneData();
        List<JSONObject> elements = sceneData.getJSONObject("topology")
                .getJSONArray("elements")
                .toJavaList(JSONObject.class);
        Map<String, String> map = elements.stream()
                .filter(element -> StringUtils.isNotBlank(element.getString("unite_number_id")))
                .collect(Collectors.toMap(o -> o.getString("unite_number_id"), o -> o.getString("id")));
        JSONObject pluginElements = sceneData.getJSONObject("plugins")
                .getJSONObject("cpcloud")
                .getJSONObject("elements");
        String sourceNode = map.get(uniteFlowAttackReq.getAttackerId());
        String targetNode = map.get(uniteFlowAttackReq.getTargetId());
        if(sourceNode!=null&&targetNode!=null){
            JSONArray attackTools = pluginElements.getJSONObject(map.get(uniteFlowAttackReq.getTargetId()))
                    .getJSONArray("attack_tools");
            if(null!=attackTools && attackTools.size()>0){
                Map<String, JSONArray> paramsMap = attackTools.toJavaList(JSONObject.class)
                        .stream().collect(Collectors.toMap(o -> o.getString("id"), o -> o.getJSONArray("params")));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",rbSceneViewContext.getMpSceneId());
                jsonObject.put("source_node",sourceNode);
                jsonObject.put("target_node",targetNode);
                jsonObject.put("task_id",uniteFlowAttackReq.getTaskId());
                jsonObject.put("target_id",uniteFlowAttackReq.getTargetId());
                jsonObject.put("attacker_id",uniteFlowAttackReq.getAttackerId());
                jsonObject.put("size",uniteFlowAttackReq.getSize());
                jsonObject.put("type",uniteFlowAttackReq.getType());
                String jsonString = jsonObject.toJSONString();
                remoteSceneService.flowStart(jsonString);
            }
        }
    }

    @Override
    @Scheduled(fixedRate = 5000)
    public void linkStatus() {
//        2. 拿到场景信息、需要判断的链路信息发送mp请求
        QueryWrapper<RbRoom> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
//                .eq(RbRoom::getBusinessType,BusinessType.UNITE_TRAIN.getCode())
                .isNotNull(RbRoom::getTaskId)
                .ne(RbRoom::getLocalSceneId,"")
                .isNotNull(RbRoom::getLocalSceneId);
//        启动/正在启动的联合训练场景，在mp中再判断是否真正启动
        List<RbRoom> list = rbRoomService.list(queryWrapper);
        List<String> localSceneIds = list.stream().map(RbRoom::getLocalSceneId).collect(Collectors.toList());
//        mp场景数据
        if (localSceneIds.size() == 0) return;
        Map<String, LocalScene> LocalSceneMap = localSceneService.listByIds(localSceneIds)
                .stream()
                .collect(Collectors.toMap(LocalScene::getResourceId, localScene -> localScene, (a, b) -> a));
        List<String> taskIds = list.stream().map(RbRoom::getTaskId).collect(Collectors.toList());
        LambdaQueryWrapper<CrUniteBusinessLink> linkLambdaQueryWrapper = new QueryWrapper<CrUniteBusinessLink>().lambda();
        linkLambdaQueryWrapper.in(CrUniteBusinessLink::getTaskId, taskIds);
        List<CrUniteBusinessLink> links = crUniteBusinessLinkService.list(linkLambdaQueryWrapper);
//        数实下发的链路信息
        Map<String, List<CrUniteBusinessLink>> linkMap = links.stream().collect(Collectors.groupingBy(CrUniteBusinessLink::getTaskId));

        ArrayList<JSONObject> jsonObjects = new ArrayList<>();
        for (RbRoom rbRoom : list) {
            JSONObject jsonObject = new JSONObject();
//        查 任务id 链路id 设备id
            String taskId = rbRoom.getTaskId();
            String localSceneId = rbRoom.getLocalSceneId();
            LocalScene localScene = LocalSceneMap.get(localSceneId);
            String mpSceneId = localScene.getMpSceneId();
            List<CrUniteBusinessLink> linksByTask = linkMap.get(taskId);
            if(linksByTask==null){
                continue;
            }
            jsonObject.put("taskId",taskId);
            jsonObject.put("mpSceneId",mpSceneId);
            jsonObject.put("links",linksByTask);
            jsonObjects.add(jsonObject);
        }
//        发送mp处理
//        3. mp处理信息后，包装数据发送320
        remoteSceneService.uniteLinkStatus(jsonObjects.toString());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUniteBusinessTopo(String jsonString) {
//       TODO 处理多层data  处理中文乱码
        log.info("接收到任务设备拓扑：{}", jsonString);
        JSONObject jsonObject = JSON.parseObject(jsonString);
        NetworkTopoVO networkTopoVO = JSON.parseObject(jsonObject.getString("data"), NetworkTopoVO.class);
        saveUniteBusinessTopo(networkTopoVO);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUniteBusinessTopo(NetworkTopoVO networkTopoVO) {
        LambdaQueryWrapper<CrUniteBusinessNode> nodeLambdaQueryWrapper = new QueryWrapper<CrUniteBusinessNode>().lambda().eq(CrUniteBusinessNode::getTaskId, networkTopoVO.getTask_id());
        List<CrUniteBusinessNode> crUniteBusinessNodeList = crUniteBusinessNodeService.list(nodeLambdaQueryWrapper);
        LambdaQueryWrapper<CrUniteBusinessLink> linkLambdaQueryWrapper = new QueryWrapper<CrUniteBusinessLink>().lambda().eq(CrUniteBusinessLink::getTaskId, networkTopoVO.getTask_id());
        List<CrUniteBusinessLink> crUniteBusinessLinkList = crUniteBusinessLinkService.list(linkLambdaQueryWrapper);
        if (crUniteBusinessNodeList.size() == 0 && crUniteBusinessLinkList.size() == 0) {
//        保存设备信息
            List<CrUniteBusinessNode> nodes = networkTopoVO.getNodes();
            nodes.forEach(node -> {
                node.setTaskId(networkTopoVO.getTask_id());
            });
            crUniteBusinessNodeService.saveBatch(nodes);
//        保存链路信息
            List<CrUniteBusinessLink> links = networkTopoVO.getLinks();
            links.forEach(link -> {
                link.setTaskId(networkTopoVO.getTask_id());
            });
            crUniteBusinessLinkService.saveBatch(links);
            sceneRelease(networkTopoVO);
        }
    }

    @Override
    public CrUniteBusinessTaskVO getTaskById(String taskId) {
        QueryWrapper<CrUniteBusinessTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(CrUniteBusinessTask::getTaskId, taskId);
        CrUniteBusinessTask crUniteBusinessTask = getBaseMapper().selectOne(queryWrapper);
        CrUniteBusinessTaskVO crUniteBusinessTaskVO = BeanUtils.copy(crUniteBusinessTask, CrUniteBusinessTaskVO.class);
        LambdaQueryWrapper<CrUniteBusinessNode> nodeLambdaQueryWrapper =
                new QueryWrapper<CrUniteBusinessNode>().lambda().eq(CrUniteBusinessNode::getTaskId, taskId);
        List<CrUniteBusinessNode> nodes = crUniteBusinessNodeService.list(nodeLambdaQueryWrapper);
        crUniteBusinessTaskVO.setNodes(nodes);
        LambdaQueryWrapper<CrUniteBusinessLink> linkLambdaQueryWrapper =
                new QueryWrapper<CrUniteBusinessLink>().lambda().eq(CrUniteBusinessLink::getTaskId, taskId);
        List<CrUniteBusinessLink> links = crUniteBusinessLinkService.list(linkLambdaQueryWrapper);
        crUniteBusinessTaskVO.setLinks(links);
        return crUniteBusinessTaskVO;
    }

//    TODO 临时给数实测试用
    @Scheduled(fixedRate = 5000)
    public void reportBuildResult1() {
        NetworkTopoVO networkTopoVO = new NetworkTopoVO();
        networkTopoVO.setTask_id("193623183164072302");
        LambdaQueryWrapper<BusinessTemplate> queryWrapper = new QueryWrapper<BusinessTemplate>().lambda()
                .eq(BusinessTemplate::getUniteTaskId,networkTopoVO.getTask_id());
        List<BusinessTemplate> businessTemplates = businessTemplateMapper.selectList(queryWrapper);
        for (BusinessTemplate businessTemplate : businessTemplates) {
            retrieveNetworkTopologyByTaskId(networkTopoVO.getTask_id(), businessTemplate.getTopoId());
        }
    }

    @Override
    public NetworkTopoVO reportBuildResult(NetworkTopoVO networkTopoVO) {
//        创建拓扑
        LambdaQueryWrapper<BusinessTemplate> queryWrapper = new QueryWrapper<BusinessTemplate>().lambda()
                .eq(BusinessTemplate::getUniteTaskId,networkTopoVO.getTask_id());
        List<BusinessTemplate> businessTemplates = businessTemplateMapper.selectList(queryWrapper);
        if(businessTemplates.size()>0){
            BusinessTemplate businessTemplate = businessTemplates.get(0);
//            判断是否已上报
            if(!businessTemplate.getReportStatus()) {
                RbRoomContext rbRoomContext = new RbRoomContext();
                LambdaQueryWrapper<RbTemplate> rbTemplateQueryWrapper = new QueryWrapper<RbTemplate>().lambda().eq(RbTemplate::getBusinessTemplateId, businessTemplate.getResourceId());
                List<RbTemplate> rbTemplates = rbTemplateMapper.selectList(rbTemplateQueryWrapper);
                if (rbTemplates.size() > 0) {
                    String resourceId = rbTemplates.get(0).getResourceId();
                    rbRoomContext.setTemplate(resourceId);
                    rbRoomContext.setName(businessTemplate.getName());
                    rbRoomContext.setDescription(businessTemplate.getDescription());
                    rbRoomContext.setBusinessType(businessTemplate.getBusinessType());
                    rbRoomContext.setStartTime(businessTemplate.getStartTime());
                    rbRoomContext.setEndTime(businessTemplate.getStartTime());
                    rbRoomContext.setEnableRecording(false);
                    rbRoomContext.setSupportPartialStart(false);
                    rbRoomContext.setCategory(1);
                    rbRoomContext.setDifficult(1);
                    rbRoomContext.setPreSkill(new ArrayList<>());
                    rbRoomContext.setTrainSkill(new ArrayList<>());
                    rbRoomContext.setTrainTarget(businessTemplate.getTrainTarget());
                    rbRoomContext.setTaskId(networkTopoVO.getTask_id());
                    rbRoomService.create(rbRoomContext);
                }
            //        上报内容
                networkTopoVO = retrieveNetworkTopologyByTaskId(networkTopoVO.getTask_id(), businessTemplate.getTopoId());
                businessTemplate.setReportStatus(true);
                businessTemplateMapper.updateById(businessTemplate);
                return networkTopoVO;
            }else {
                throw new BusinessException(ErrorCode.UNITE_REPORT);
            }
        }
        return null;
    }


    @Override
    public void start(String jsonString) {
        log.info("unite start scene begin");
        JSONObject jsonObject = JSON.parseObject(jsonString);
        NetworkTopoVO networkTopoVO = JSON.parseObject(jsonObject.getString("data"), NetworkTopoVO.class);
        log.info("unite start scene taskId:{}",networkTopoVO.getTask_id());
        start(networkTopoVO);
        log.info("unite start scene end");
    }

    @Override
    public String start(NetworkTopoVO networkTopoVO) {
        String taskId = networkTopoVO.getTask_id();
        RbRoom rbRoom = rbRoomService.getRbRoomByUniteTaskId(taskId);
        log.info("unite start scene roomId:{}",rbRoom.getResourceId());
        String sceneId = rbSceneService.startScene(rbRoom.getResourceId(), null);
        return sceneId;
    }

    @Override
    public void stop(String jsonString) {
        log.info("unite stop scene begin");
        JSONObject jsonObject = JSON.parseObject(jsonString);
        NetworkTopoVO networkTopoVO = JSON.parseObject(jsonObject.getString("data"), NetworkTopoVO.class);
        log.info("unite stop scene taskId:{}",networkTopoVO.getTask_id());
        stop(networkTopoVO);
        log.info("unite stop scene end");
    }

    @Override
    public Boolean stop(NetworkTopoVO networkTopoVO) {
        String taskId = networkTopoVO.getTask_id();
        RbRoom rbRoom = rbRoomService.getRbRoomByUniteTaskId(taskId);
        log.info("unite stop scene roomId:{}",rbRoom.getResourceId());
        Boolean result = rbSceneService.stopScene(rbRoom.getResourceId());
        return result;
    }

    /**
     * 请求mp生成拓扑
     * @param networkTopoVO
     */
    private void sceneRelease(NetworkTopoVO networkTopoVO) {
        //        生成拓扑
        String jsonString = JSON.toJSONString(networkTopoVO);
        JSONObject scene = remoteSceneService.sceneRelease(jsonString);
        String id = scene.getString("id");

        QueryWrapper<BusinessTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BusinessTemplate::getUniteTaskId, networkTopoVO.getTask_id());
        BusinessTemplate businessTemplate = businessTemplateMapper.selectOne(queryWrapper);
        businessTemplate.setTopoId(id);
        businessTemplateMapper.updateById(businessTemplate);

        RbTemplate rbTemplate = new RbTemplate();
        rbTemplate.setBusinessTemplateId(businessTemplate.getResourceId());
        rbTemplate.setPreSkill("[]");
        rbTemplate.setTrainSkill("[]");
        rbTemplate.setRoleConfig("{}");
        rbTemplate.setSubType(1);
        rbTemplate.setDurationTime(4);

        rbTemplateMapper.insert(rbTemplate);
    }
}