package com.cyberpeace.cr.evaluation.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.evaluation.controller.req.DeviceCommandExecReq;
import com.cyberpeace.cr.evaluation.controller.req.DeviceCommandModelReq;
import com.cyberpeace.cr.evaluation.entity.CrRbTargetCommandLog;
import com.cyberpeace.cr.evaluation.entity.CrRbTargetCommandParameters;
import com.cyberpeace.cr.evaluation.entity.CrRbTargetCommandTemplates;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface EvalDeviceService {
    JSONArray getDeviceList(String resourceId, Integer category, String name);

    JSONObject getDeviceStatistics(String resourceId);

    List<CrRbTargetCommandTemplates> getDeviceCommandModels(DeviceCommandModelReq req);

    List<CrRbTargetCommandParameters> getDeviceCommandParameters(String id);

    void execCmdByAgent(DeviceCommandExecReq req);

    List<CrRbTargetCommandLog> execCmdByAgentRusult(String id, String nodeId);
}
