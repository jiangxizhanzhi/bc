package com.cyberpeace.cr.redvsblue.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
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
import com.cyberpeace.integratation.service.RemoteSceneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUniteBusinessTask(NetworkTaskVO networkTaskVO) {
        CrUniteBusinessTask crUniteBusinessTask = new CrUniteBusinessTask();
        crUniteBusinessTask.setTaskId(networkTaskVO.getId());
        crUniteBusinessTask.setTaskName(networkTaskVO.getTaskName());
        crUniteBusinessTask.setTaskDescription(networkTaskVO.getTaskContent());
        crUniteBusinessTask.setTrainTarget(networkTaskVO.getTaskContent());
        crUniteBusinessTask.setStartTime(networkTaskVO.getTaskTime());

//        保存任务表
        getBaseMapper().insert(crUniteBusinessTask);
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
//        判断是否已有该任务的设备信息下发 已下发转到生成拓扑方法生成拓扑
        NetworkTopoVO networkTopoVO = retrieveNetworkTopologyByTaskId(businessTemplate.getUniteTaskId());
        if(networkTopoVO.getNodes() != null && networkTopoVO.getNodes().size()>0){
            sceneRelease(networkTopoVO);
        }
    }


    /**
     * （CrUniteBusinessNode）和链接（CrUniteBusinessLink），并将它们封装到 NetworkTopoVO 对象中返回
     * @param taskId
     * @return
     */
    private NetworkTopoVO retrieveNetworkTopologyByTaskId(String taskId) {
        NetworkTopoVO networkTopoVO = new NetworkTopoVO();
        networkTopoVO.setTask_id(taskId);
        QueryWrapper<CrUniteBusinessNode> nodeQueryWrapper = new QueryWrapper<>();
        nodeQueryWrapper.lambda().eq(CrUniteBusinessNode::getTaskId, taskId);
        List<CrUniteBusinessNode> nodes = crUniteBusinessNodeService.list(nodeQueryWrapper);
        QueryWrapper<CrUniteBusinessLink> linkQueryWrapper = new QueryWrapper<>();
        linkQueryWrapper.lambda().eq(CrUniteBusinessLink::getTaskId, taskId);
        List<CrUniteBusinessLink> links = crUniteBusinessLinkService.list();
        if(nodes.size()>0||links.size()>0){
            networkTopoVO.setNodes(nodes);
            networkTopoVO.setLinks(links);
        }
        return networkTopoVO;
    }

    @Lazy
    @Autowired
    private WebSocketClientService webSocketClientService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUniteBusinessTopo(NetworkTopoVO networkTopoVO) {
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
//        TODO 测试ws
        String message = "测试";
        webSocketClientService.sendMessage(message);
        sceneRelease(networkTopoVO);
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
                    businessTemplate.setTrainTarget(businessTemplate.getTrainTarget());
                    rbRoomService.create(rbRoomContext);
                }
            //        上报内容
                networkTopoVO = retrieveNetworkTopologyByTaskId(networkTopoVO.getTask_id());
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
    public String start(NetworkTopoVO networkTopoVO) {
        String taskId = networkTopoVO.getTask_id();
        RbRoom rbRoom = rbRoomService.getRbRoomByUniteTaskId(taskId);
        String sceneId = rbSceneService.startScene(rbRoom.getResourceId(), null);
        return sceneId;
    }

    @Override
    public Boolean stop(NetworkTopoVO networkTopoVO) {
        String taskId = networkTopoVO.getTask_id();
        RbRoom rbRoom = rbRoomService.getRbRoomByUniteTaskId(taskId);
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