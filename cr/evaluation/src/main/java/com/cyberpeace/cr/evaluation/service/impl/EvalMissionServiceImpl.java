package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.evaluation.controller.req.ManualExecutionReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalMissionTemplate;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalMissionTemplateLogMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalMissionTemplateMapper;
import com.cyberpeace.cr.evaluation.service.EvalMissionService;
import com.cyberpeace.integratation.model.scene.MissionStartContext;
import com.cyberpeace.integratation.service.RemoteSceneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class EvalMissionServiceImpl extends ServiceImpl<EvalMissionTemplateMapper, EvalMissionTemplate> implements EvalMissionService {

    @Autowired
    private RemoteSceneService remoteSceneService;

    @Autowired
    private EvalMissionTemplateLogMapper evalMissionTemplateLogMapper;

    @Async
    @Override
    public String executeCommand(ManualExecutionReq manualExecutionReq, EvalTask evalTask, String execUser, String taskId) {
        MissionStartContext missionStartContext = new MissionStartContext();
        missionStartContext.setCommand(manualExecutionReq.getCommand());
        missionStartContext.setNode_id(manualExecutionReq.getNodeId());
        missionStartContext.setExec_user(execUser);
        missionStartContext.setId(evalTask.getMpSceneId());
        String output = remoteSceneService.startMission(missionStartContext);

        // 日志记录
        evalMissionTemplateLogMapper.updateMissionResultAndMissionIdAndTaskId(manualExecutionReq.getMissionId(), output, taskId);
        return output;
    }
}
