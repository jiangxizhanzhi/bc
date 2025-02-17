package com.cyberpeace.cr.evaluation.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.evaluation.controller.req.DeviceCommandExecReq;
import com.cyberpeace.cr.evaluation.controller.req.DeviceCommandModelReq;
import com.cyberpeace.cr.evaluation.controller.rsp.CrRbTargetCommandLogResp;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
     *
     * @param resourceId
     * @param category
     * @param name
     * @return
     */
    @Override
    public JSONArray getDeviceList(String resourceId, Integer category, String name) {
        //根据模板id去查询
        EvalTemplate evalTemplate = evalTemplateMapper.selectByResourceId(resourceId);
        if (Objects.isNull(evalTemplate)) {
            log.error("Param exception:{}", resourceId);
            throw new BusinessException("getDeviceList Param exception:{}", resourceId);
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
        if (Objects.isNull(evalTemplate)) {
            log.error("Param exception:{}", resourceId);
            throw new BusinessException("getDeviceList Param exception:{}", resourceId);
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
        log.setCommandText(req.getExecCmds() != null ? req.getExecCmds().toString() : "");
        log.setCommandTemplates(req.getCommandTemplates() != null ? req.getCommandTemplates().toString() : "");
        log.setCommandTemplateIds(req.getCommandTemplateIds() != null ? req.getCommandTemplateIds().toString() : "");
        log.setCommandParameters(req.getCommandParameters() != null ? req.getCommandParameters().toString() : "");
        //存储执行结果
        if (jsonObject != null) {
            if (jsonObject.get("code") != null && jsonObject.get("code").equals("MP-000000")) {
                flag = true;
                String result = jsonObject.get("data") != null ? jsonObject.get("data").toString() : "";
                log.setStatus(1);
                log.setResult(result);
            }
        }
        if (!flag) {
            log.setStatus(0);
            log.setResult(jsonObject != null ? String.valueOf(jsonObject) : "");
        }
        evalDeviceMapper.insert(log);
    }

    @Override
    public List<CrRbTargetCommandLogResp> execCmdByAgentRusult(String id, String nodeId) {
        // 获取日志记录
        List<CrRbTargetCommandLog> crRbTargetCommandLogs = evalDeviceMapper.execCmdByAgentRusult(id, nodeId);

        // 结果集合
        List<CrRbTargetCommandLogResp> result = new ArrayList<>();

        // 将 commandTemplateId 关联的命令模板提前查询一次，避免循环内查询
        Set<String> allCommandTemplateIds = crRbTargetCommandLogs.stream()
                .filter(log -> StringUtils.isNotEmpty(log.getCommandTemplateIds()))
                .flatMap(log -> JSON.parseArray(log.getCommandTemplateIds(), String.class).stream())
                .collect(Collectors.toSet());

        if (!allCommandTemplateIds.isEmpty()) {
            DeviceCommandModelReq deviceCommandModelReq = new DeviceCommandModelReq();
            deviceCommandModelReq.setIds(new ArrayList<>(allCommandTemplateIds));
            List<CrRbTargetCommandTemplates> commandTemplates = evalDeviceMapper.getDeviceCommandModels(deviceCommandModelReq);

            // 将命令模板按 ID 映射，减少查找时间
            Map<String, String> commandTemplateMap = commandTemplates.stream()
                    .collect(Collectors.toMap(CrRbTargetCommandTemplates::getId, CrRbTargetCommandTemplates::getCommandName));

            // 遍历日志，填充响应对象
            for (CrRbTargetCommandLog log : crRbTargetCommandLogs) {
                CrRbTargetCommandLogResp crRbTargetCommandLogResp = new CrRbTargetCommandLogResp();

                // 直接复制属性
                BeanUtils.copyProperties(log, crRbTargetCommandLogResp);

                String commandTemplateIds = log.getCommandTemplateIds();
                Set<String> commandNames = new HashSet<>();

                // 如果 commandTemplateIds 不为空，解析命令模板 ID 并获取命令名称
                if (StringUtils.isNotEmpty(commandTemplateIds)) {
                    List<String> commandTemplateIdList = JSON.parseArray(commandTemplateIds, String.class);
                    for (String templateId : commandTemplateIdList) {
                        String commandName = commandTemplateMap.get(templateId);
                        if (StringUtils.isNotEmpty(commandName)) {
                            commandNames.add(commandName);
                        }
                    }
                }

                crRbTargetCommandLogResp.setCommandTemplateNames(commandNames);
                result.add(crRbTargetCommandLogResp);
            }
        }

        return result;
    }


}
