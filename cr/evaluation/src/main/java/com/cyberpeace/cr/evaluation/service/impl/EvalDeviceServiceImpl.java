package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.evaluation.controller.req.DeviceCommandExecReq;
import com.cyberpeace.cr.evaluation.controller.req.DeviceCommandModelReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalDeviceMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTemplateMapper;
import com.cyberpeace.cr.evaluation.entity.CrRbTargetCommandLog;
import com.cyberpeace.cr.evaluation.entity.CrRbTargetCommandParameters;
import com.cyberpeace.cr.evaluation.entity.CrRbTargetCommandTemplates;
import com.cyberpeace.cr.evaluation.service.EvalDeviceService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class EvalDeviceServiceImpl implements EvalDeviceService {
    @Autowired
    private EvalTemplateMapper evalTemplateMapper;

    @Autowired
    private EvalDeviceMapper evalDeviceMapper;

    @Autowired
    private LocalSceneService localSceneService;

    @Autowired
    private LocalSceneMapper localSceneMapper;

    @Autowired
    private RemoteSceneService remoteSceneService;

    /**
     * 通过模板id查询设备清单
     * @param resourceId
     * @param category
     * @param name
     * @return
     */
    @Override
    public JSONArray getDeviceList(String resourceId, Integer category, String name) {
        //根据模板id去查询
        EvalTemplate evalTemplate = evalTemplateMapper.selectByResourceId(resourceId);
        if (Objects.isNull(evalTemplate)){
            log.error("Param exception:{}",resourceId);
            throw new BusinessException("getDeviceList Param exception:{}",resourceId);
        }
        LocalScene localScene = localSceneMapper.selectLocalSceneBytemplateId(evalTemplate.getResourceId());
        if (Objects.nonNull(localScene)) {
            return localSceneService.getAssetListByInstanceId(localScene.getBusinessInstanceId(), category, name, true);
        } else {
            return localSceneService.getAssetListByTopoId(evalTemplate.getTopoId(), category, name);
        }
    }

    @Override
    public JSONObject getDeviceStatistics(String resourceId) {
        //根据模板id查询
        EvalTemplate evalTemplate = evalTemplateMapper.selectByResourceId(resourceId);
        if (Objects.isNull(evalTemplate)){
            log.error("Param exception:{}",resourceId);
            throw new BusinessException("getDeviceList Param exception:{}",resourceId);
        }
        //根据模板id去查询场景ID
        LocalScene localScene = localSceneMapper.selectLocalSceneBytemplateId(resourceId);
        if (Objects.nonNull(localScene)) {
            return localSceneService.getNodeStatistics(localScene.getResourceId());
        } else {
            return localSceneService.getNodeStatisticsByTopoId(evalTemplate.getTopoId());
        }
    }

    @Override
    public List<CrRbTargetCommandTemplates> getDeviceCommandModels(DeviceCommandModelReq req) {
        return evalDeviceMapper.getDeviceCommandModels(req);
    }

    @Override
    public List<CrRbTargetCommandParameters> getDeviceCommandParameters(String id) {
        return evalDeviceMapper.getDeviceCommandParameters(id);
    }

    @Override
    public void execCmdByAgent(DeviceCommandExecReq req) {
        JSONObject jsonObject = remoteSceneService.execCmdByAgent(req.getNodeId(), req.getId(), req.getExecCmds(), req.getProtocol());
        Boolean flag = false;
        CrRbTargetCommandLog log = new CrRbTargetCommandLog();
        log.setNodeId(req.getNodeId());
        log.setSceneId(req.getId());
        log.setProtocol(req.getProtocol());
        log.setId(UUID.randomUUID().toString());
        log.setCommandUser(UserInfoContext.getUserContext().getUserId());
        log.setCommandText(req.getExecCmds().toString());
        //存储执行结果
        if(jsonObject != null){
            if(jsonObject.get("code") != null && jsonObject.get("code").equals("MP-000000")){
                flag = true;
                String result = jsonObject.get("data") != null ? jsonObject.get("data").toString() : "";
                log.setStatus(1);
                log.setResult(result);
            }
        }
        if(!flag){
            log.setStatus(0);
            log.setResult(jsonObject != null ? String.valueOf(jsonObject) : "");
        }
        evalDeviceMapper.insert(log);
    }


}
