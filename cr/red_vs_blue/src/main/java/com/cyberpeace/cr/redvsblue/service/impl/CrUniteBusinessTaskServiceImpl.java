package com.cyberpeace.cr.redvsblue.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.enums.BusinessType;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.mapper.BusinessTemplateMapper;
import com.cyberpeace.cr.redvsblue.dao.entity.CrUniteBusinessLink;
import com.cyberpeace.cr.redvsblue.dao.entity.CrUniteBusinessNode;
import com.cyberpeace.cr.redvsblue.dao.entity.CrUniteBusinessTask;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTemplate;
import com.cyberpeace.cr.redvsblue.dao.mapper.CrUniteBusinessTaskMapper;
import com.cyberpeace.cr.redvsblue.dao.mapper.RbTemplateMapper;
import com.cyberpeace.cr.redvsblue.service.CrUniteBusinessLinkService;
import com.cyberpeace.cr.redvsblue.service.CrUniteBusinessNodeService;
import com.cyberpeace.cr.redvsblue.service.CrUniteBusinessTaskService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.context.RbRoomContext;
import com.cyberpeace.cr.redvsblue.vo.CrUniteBusinessTaskVO;
import com.cyberpeace.cr.redvsblue.vo.NetworkTaskVO;
import com.cyberpeace.cr.redvsblue.vo.NetworkTopoVO;
import com.cyberpeace.integratation.service.RemoteSceneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        RbTemplate rbTemplate = new RbTemplate();
        rbTemplate.setBusinessTemplateId(businessTemplate.getResourceId());
        rbTemplate.setPreSkill("[]");
        rbTemplate.setTrainSkill("[]");
        rbTemplate.setRoleConfig("{}");
        rbTemplate.setSubType(1);
        rbTemplate.setDurationTime(4);
        rbTemplateMapper.insert(rbTemplate);
//        判断是否已有该任务的设备信息下发 已下发转到生成拓扑方法生成拓扑
        NetworkTopoVO networkTopoVO = extracted(businessTemplate.getUniteTaskId());

        sceneRelease(networkTopoVO);
    }

//    TODO 改名
    private NetworkTopoVO extracted(String taskId) {
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

    @Autowired
    private RbRoomService rbRoomService;
    @Override
    public NetworkTopoVO saveUniteBusinessT(NetworkTopoVO networkTopoVO) {
//        创建拓扑
        LambdaQueryWrapper<BusinessTemplate> queryWrapper = new QueryWrapper<BusinessTemplate>().lambda()
                .eq(BusinessTemplate::getUniteTaskId,networkTopoVO.getTask_id());
        List<BusinessTemplate> businessTemplates = businessTemplateMapper.selectList(queryWrapper);
        if(businessTemplates.size()>0){
//            TODO 判断是否已上报
            BusinessTemplate businessTemplate = businessTemplates.get(0);
            RbRoomContext rbRoomContext = new RbRoomContext();
            LambdaQueryWrapper<RbTemplate> rbTemplateQueryWrapper = new QueryWrapper<RbTemplate>().lambda().eq(RbTemplate::getBusinessTemplateId, businessTemplate.getResourceId());
            List<RbTemplate> rbTemplates = rbTemplateMapper.selectList(rbTemplateQueryWrapper);
            if(rbTemplates.size()>0){
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
                rbRoomService.create(rbRoomContext);
            }
//        上报内容
            networkTopoVO = extracted(networkTopoVO.getTask_id());
            return networkTopoVO;
        }
        return null;
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
        UpdateWrapper<BusinessTemplate> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(BusinessTemplate::getUniteTaskId, networkTopoVO.getTask_id());
        BusinessTemplate businessTemplate = new BusinessTemplate();
        businessTemplate.setTopoId(id);
        businessTemplateMapper.update(businessTemplate, updateWrapper);
    }
}