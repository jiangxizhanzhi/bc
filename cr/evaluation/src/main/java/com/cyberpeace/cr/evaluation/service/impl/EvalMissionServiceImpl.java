package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.evaluation.controller.req.ManualExecutionReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalMissionTemplate;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
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

    @Async
    @Override
    public String executeCommand(ManualExecutionReq manualExecutionReq, EvalTask evalTask) {
        MissionStartContext missionStartContext = new MissionStartContext();
        missionStartContext.setCommand(manualExecutionReq.getCommand());
        missionStartContext.setNode_id(manualExecutionReq.getNodeId());
        UserInfo userContext = UserInfoContext.getUserContext();

        missionStartContext.setExec_user(userContext.getUserId());
        missionStartContext.setId(evalTask.getMpSceneId());
        String output = remoteSceneService.startMission(missionStartContext);
        return output;
    }
}
